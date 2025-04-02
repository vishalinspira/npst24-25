package com.quarterlysubmissionofforms.resources;

import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.pfm.service.model.CashFlow;
import com.daily.pfm.service.model.Form3RepByDirAndKP;
import com.daily.pfm.service.model.QuarterlySubCompForms;
import com.daily.pfm.service.model.QuarterlySubCompForms_2;
import com.daily.pfm.service.model.QuarterlySubCompForms_3;
import com.daily.pfm.service.model.QuarterlySubCompForms_4;
import com.daily.pfm.service.model.QuarterlySubCompForms_5;
import com.daily.pfm.service.model.QuarterlySubForm1A;
import com.daily.pfm.service.service.CashFlowLocalServiceUtil;
import com.daily.pfm.service.service.Form3RepByDirAndKPLocalServiceUtil;
import com.daily.pfm.service.service.QuarterlySubCompFormsLocalServiceUtil;
import com.daily.pfm.service.service.QuarterlySubCompForms_2LocalServiceUtil;
import com.daily.pfm.service.service.QuarterlySubCompForms_3LocalServiceUtil;
import com.daily.pfm.service.service.QuarterlySubCompForms_4LocalServiceUtil;
import com.daily.pfm.service.service.QuarterlySubCompForms_5LocalServiceUtil;
import com.daily.pfm.service.service.QuarterlySubForm1ALocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.quarterlysubmissionofforms.constants.QuarterlySubmissionOfFormsAsPerPFRDARegualtionsPortletKeys;
import com.quarterlysubmissionofforms.util.ParseCashFlow;
import com.quarterlysubmissionofforms.util.ParseForm1A;
import com.quarterlysubmissionofforms.util.ParseForm1b;
import com.quarterlysubmissionofforms.util.ParseForm2;
import com.quarterlysubmissionofforms.util.ParseForm3RepByKPAndDirector;
import com.quarterlysubmissionofforms.util.ParseForm3Scheme;
import com.quarterlysubmissionofforms.util.ParseForm4;
import com.quarterlysubmissionofforms.util.ParseForm5;
import com.quarterlysubmissionofforms.util.ValidateFileName;
import com.quarterlysubmissionofforms.util.ValidateSheetName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + QuarterlySubmissionOfFormsAsPerPFRDARegualtionsPortletKeys.QUARTERLYSUBMISSIONOFFORMSASPERPFRDAREGUALTIONS,
		"mvc.command.name=" + QuarterlySubmissionOfFormsAsPerPFRDARegualtionsPortletKeys.quarterlySubmissionOfForms,
		}, 
service = MVCResourceCommand.class)

public class QuarterlySubmissionOfForms implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(QuarterlySubmissionOfForms.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("QuarterlySubmissionOfForms   serveResource =======>    ");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			String result=resultJsonObject.toString().replaceAll("\n", " ");
			_log.info("final result  : "+result);
			writer.write(result);
		} catch (IOException e) {
			 _log.error(e);
		}
		
		return false;
	}
	@SuppressWarnings("deprecation")
	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		List<String> fileNames=new ArrayList<String>();
		fileNames.add(uploadPortletRequest.getFileName("quarterlySubFormXlsx"));
		fileNames.add(uploadPortletRequest.getFileName("quarterlyCompFormXlsx"));
		fileNames.add(uploadPortletRequest.getFileName("quarterlySubFormPdf"));
		fileNames.add(uploadPortletRequest.getFileName("form3ByKpPdf"));
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(ValidateFileName.validatefileNames(fileNames)) {
		resultJsonObject.put("fileList", JSONFactoryUtil.createJSONArray());
		
	//   comment for enable disable options
	//	resultJsonObject = saveForm1A(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
		resultJsonObject.put("status", true);
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = saveQuarterlyCompForms(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
		}
		
//   comment for enable disable options 
//		if(!resultJsonObject.getBoolean("status")) {
//			return resultJsonObject;
//		}else {
//			resultJsonObject = saveForm3RepByDirAndKP(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
//					
//		}
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = pdfFileUpload(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
					
		}
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = pdfFileUpload1(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
					
		}
		
//   comment for enable disable options
		
//		if(!resultJsonObject.getBoolean("status")) {
//			return resultJsonObject;
//		}else {
//			resultJsonObject = pdfFileUpload3(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
//					
//		}
		
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = pdfFileUpload2(uploadPortletRequest, themeDisplay, userId, resourceRequest, reportUploadLogId, statusByUserName, serviceContext, resultJsonObject);
					
		}
		_log.info("resultJsonObject****"+resultJsonObject);
		String fileList = resultJsonObject.getJSONArray("fileList").toString();
		//long fileEntryId = resultJsonObject.getLong("fileEntryId");
		long fileEntryId=resultJsonObject.getJSONArray("fileList").getJSONObject(0).getLong("fileEntryId");
		_log.info("file entry id: " +fileEntryId);
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		updateReportLog(userId, fileEntryId , true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks, fileList);
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename.");
			return resultJsonObject;
		}
		return resultJsonObject;
	}
	
	@SuppressWarnings("deprecation")
	public JSONObject saveForm1A(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		File form_1A_File = uploadPortletRequest.getFile("quarterlySubFormXlsx");

		resultJsonObject = excelValidationAn10Api.validateExcelFile(form_1A_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {
			_log.info("Inside resultJsonObject");
			

			// Form 1A
			JSONArray form1AJsonArray = JSONFactoryUtil.createJSONArray();
			List<QuarterlySubForm1A> form1AList = new ArrayList<QuarterlySubForm1A>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;
			
			
			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(form_1A_File, workbook, getSheetNameForForm1A());

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

				resultJsonObject = ParseForm1A.saveSheetForm1A(form_1A_File, workbook, userId, errorArray, xx, isexcelhaserror,
						form1AJsonArray, form1AList, reportUploadLogId, resultJsonObject);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				if (!isexcelhaserror) {

					try {
						QuarterlySubForm1ALocalServiceUtil.addQuarterlySubForm1A(form1AList);
					} catch (Exception e1) {
						 _log.error(e1);
						resultJsonObject.put("status", false);
						resultJsonObject.put("msg",  CommonParser.fileExceptionMsg);
						return resultJsonObject;
					}
					
					Long form1AFileEntryId = 0l;										   
					form1AFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "quarterlySubFormXlsx");
					_log.info("form1AFileEntryId: " + form1AFileEntryId);
					JSONObject form1AFileEntryJO = JSONFactoryUtil.createJSONObject();
					form1AFileEntryJO.put("Name", "Form 1 - Report of transaction in securities by KP");
					form1AFileEntryJO.put("fileEntryId", form1AFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						//_log.info("fileList---------"+fileList);
						fileList.put(form1AFileEntryJO);
						_log.info("fileList: " + fileList.toString());
						resultJsonObject.put("fileList", fileList);
						resultJsonObject.put("fileEntryId", form1AFileEntryId);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	@SuppressWarnings("deprecation")
	public JSONObject pdfFileUpload(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
        _log.info("pdfFileUpload:::::::::::::::::::::::::::");
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		Long pdfFileEntryId = 0l;
		pdfFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "quarterlySubFormPdf");
		_log.info("pdfFileEntryId: " + pdfFileEntryId);
		JSONObject pdfFileEntryJO = JSONFactoryUtil.createJSONObject();
		pdfFileEntryJO.put("Name", "Form 1 - Report of transaction in securities by KP PDF");
		pdfFileEntryJO.put("fileEntryId", pdfFileEntryId);
		
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		//_log.info("fileList---------"+fileList);
		fileList.put(pdfFileEntryJO);
		_log.info("fileList: " + fileList.toString());
		resultJsonObject.put("fileList", fileList);
		
		resultJsonObject.put("status", true);
		
		return resultJsonObject;
	}
	@SuppressWarnings("deprecation")
	public JSONObject pdfFileUpload1(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
        _log.info("pdfFileUpload:::::::::::::::::::::::::::");
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		Long pdfFileEntryId = 0l;
		pdfFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "form6Portfolio");
		_log.info("pdfFileEntryId: " + pdfFileEntryId);
		JSONObject pdfFileEntryJO = JSONFactoryUtil.createJSONObject();
		pdfFileEntryJO.put("Name", "Form 4: Overview of portfolio positioning");
		pdfFileEntryJO.put("fileEntryId", pdfFileEntryId);
		
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		//_log.info("fileList---------"+fileList);
		fileList.put(pdfFileEntryJO);
		_log.info("fileList: " + fileList.toString());
		resultJsonObject.put("fileList", fileList);
		
		resultJsonObject.put("status", true);
		
		return resultJsonObject;
	}
	
	@SuppressWarnings("deprecation")
	public JSONObject pdfFileUpload2(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
        _log.info("pdfFileUpload:::::::::::::::::::::::::::");
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		Long pdfFileEntryId = 0l;
		pdfFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "form3ByKpPdf");
		_log.info("pdfFileEntryId: " + pdfFileEntryId);
		JSONObject pdfFileEntryJO = JSONFactoryUtil.createJSONObject();
		pdfFileEntryJO.put("Name", "Form 3 Report by Director and KP.pdf");
		pdfFileEntryJO.put("fileEntryId", pdfFileEntryId);
		
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		//_log.info("fileList---------"+fileList);
		fileList.put(pdfFileEntryJO);
		_log.info("fileList: " + fileList.toString());
		resultJsonObject.put("fileList", fileList);
		
		resultJsonObject.put("status", true);
		
		return resultJsonObject;
	}
	
	
	@SuppressWarnings("deprecation")
	public JSONObject pdfFileUpload3(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
        _log.info("pdfFileUpload:::::::::::::::::::::::::::");
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		
		Long pdfFileEntryId = 0l;
		pdfFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "annexureb");
		_log.info("pdfFileEntryId: " + pdfFileEntryId);
		JSONObject pdfFileEntryJO = JSONFactoryUtil.createJSONObject();
		pdfFileEntryJO.put("Name", "Annexure B");
		pdfFileEntryJO.put("fileEntryId", pdfFileEntryId);
		
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		//_log.info("fileList---------"+fileList);
		fileList.put(pdfFileEntryJO);
		_log.info("fileList: " + fileList.toString());
		resultJsonObject.put("fileList", fileList);
		
		resultJsonObject.put("status", true);
		
		return resultJsonObject;
	}

	
	@SuppressWarnings("deprecation")
	public JSONObject saveQuarterlyCompForms(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		File qtr_Comp_Form_File = uploadPortletRequest.getFile("quarterlyCompFormXlsx");
	
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(qtr_Comp_Form_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {
			_log.info("Inside resultJsonObject");
			

			// Form 1B
			JSONArray form1BJsonArray = JSONFactoryUtil.createJSONArray();
			List<QuarterlySubCompForms> form1BList = new ArrayList<QuarterlySubCompForms>();
			
			// Form 2
			JSONArray form2JsonArray = JSONFactoryUtil.createJSONArray();
			List<QuarterlySubCompForms_2> form2List = new ArrayList<QuarterlySubCompForms_2>();
			
			// CashFlow
			JSONArray cashFlowJsonArray = JSONFactoryUtil.createJSONArray();
			List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
			
			// Form 3
			JSONArray form3JsonArray = JSONFactoryUtil.createJSONArray();
			List<QuarterlySubCompForms_3> form3List = new ArrayList<QuarterlySubCompForms_3>();
			
			// Form 4
			JSONArray form4JsonArray = JSONFactoryUtil.createJSONArray();
			List<QuarterlySubCompForms_4> form4List = new ArrayList<QuarterlySubCompForms_4>();
			
			// Form 5
			JSONArray form5JsonArray = JSONFactoryUtil.createJSONArray();
			List<QuarterlySubCompForms_5> form5List = new ArrayList<QuarterlySubCompForms_5>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;
			
			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(qtr_Comp_Form_File, workbook, getSheetNameForQuarterlySubCompForms());

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

			
				resultJsonObject = ParseForm1b.saveSheetForm1b(qtr_Comp_Form_File, workbook, userId, errorArray, xx, isexcelhaserror, form1BJsonArray, form1BList, reportUploadLogId,resultJsonObject);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = ParseForm2.saveSheetForm2(qtr_Comp_Form_File, workbook, userId, errorArray, xx, isexcelhaserror, form2JsonArray, form2List, reportUploadLogId,resultJsonObject);
//				if(!resultJsonObject.getBoolean("status"))
//					return resultJsonObject;
				//resultJsonObject = ParseForm3Scheme.saveSheetForm3(qtr_Comp_Form_File, workbook, userId, errorArray, xx, isexcelhaserror, form3JsonArray, form3List, reportUploadLogId,resultJsonObject);
//				if(!resultJsonObject.getBoolean("status"))
//					return resultJsonObject;
//				resultJsonObject = ParseForm4.saveSheetForm4(qtr_Comp_Form_File, workbook, userId, errorArray, xx, isexcelhaserror, form4JsonArray, form4List, reportUploadLogId,resultJsonObject);
//				if(!resultJsonObject.getBoolean("status"))
//					return resultJsonObject;
//				resultJsonObject = ParseForm5.saveSheetForm5(qtr_Comp_Form_File, workbook, userId, errorArray, xx, isexcelhaserror, form5JsonArray, form5List, reportUploadLogId,resultJsonObject);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = ParseCashFlow.saveSheetCashFlow(qtr_Comp_Form_File, workbook, userId, errorArray, xx, isexcelhaserror, cashFlowJsonArray, cashFlowList, reportUploadLogId,resultJsonObject);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				_log.info("cashFlowList----"+cashFlowList);
				_log.info("isexcelhaserror ::::::::::"+isexcelhaserror);
				if (!isexcelhaserror) {

					try {
						QuarterlySubCompFormsLocalServiceUtil.addQuarterlySubCompForms(form1BList);
						QuarterlySubCompForms_2LocalServiceUtil.addQuarterlySubCompForms_2(form2List);
						CashFlowLocalServiceUtil.addCashFlow(cashFlowList);
//						QuarterlySubCompForms_3LocalServiceUtil.addQuarterlySubCompForms_3(form3List);
//						QuarterlySubCompForms_4LocalServiceUtil.addQuarterlySubCompForms_4(form4List);
//						QuarterlySubCompForms_5LocalServiceUtil.addQuarterlySubCompForms_5(form5List);
					} catch (Exception e) {
						_log.error(e);
						resultJsonObject.put("status", false);
						resultJsonObject.put("msg",  CommonParser.fileExceptionMsg);
						return resultJsonObject;
					}
					
					Long qtrCompFormFileEntryId = 0l;
					qtrCompFormFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "quarterlyCompFormXlsx");
					_log.info("qtrCompFormFileEntryId: " + qtrCompFormFileEntryId);
					JSONObject qtrCompFormFileEntryJO = JSONFactoryUtil.createJSONObject();
					qtrCompFormFileEntryJO.put("Name", "Form 2 and 3 - Quarterly Compliance Forms");
					qtrCompFormFileEntryJO.put("fileEntryId", qtrCompFormFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
						//_log.info("fileList---------"+fileList);
						fileList.put(qtrCompFormFileEntryJO);
						_log.info("fileList: " + fileList.toString());
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	@SuppressWarnings("deprecation")
	public JSONObject saveForm3RepByDirAndKP(UploadPortletRequest uploadPortletRequest, ThemeDisplay themeDisplay, long userId,
			ResourceRequest resourceRequest, long reportUploadLogId, String statusByUserName,
			ServiceContext serviceContext, JSONObject resultJsonObject) {
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		File form3RepByDirAndKP_File = uploadPortletRequest.getFile("form3ByKpXlsx");

		resultJsonObject = excelValidationAn10Api.validateExcelFile(form3RepByDirAndKP_File, resourceRequest);
		if (resultJsonObject.getBoolean("status")) {
			_log.info("Inside resultJsonObject");
			

			// Form 1A
			JSONArray form3RepByDirAndKPJsonArray = JSONFactoryUtil.createJSONArray();
			List<Form3RepByDirAndKP> form3RepByDirAndKPList = new ArrayList<Form3RepByDirAndKP>();

			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;

			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Sr.No");

			boolean isexcelhaserror = false;
			
			// sheet validation
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(form3RepByDirAndKP_File, workbook, getSheetNameForForm3());

			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror", true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				return resultJsonObject;
			} else {

//				ParseForm1A.saveSheetForm1A(form_1A_File, workbook, userId, errorArray, xx, isexcelhaserror,
//						decimalFormat, form1AJsonArray, form1AList, reportUploadLogId);
				resultJsonObject =ParseForm3RepByKPAndDirector.saveSheetForm3Rep(form3RepByDirAndKP_File, workbook, userId, errorArray, xx, isexcelhaserror, form3RepByDirAndKPJsonArray, form3RepByDirAndKPList, reportUploadLogId,resultJsonObject);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				if (!isexcelhaserror) {

					try {
						Form3RepByDirAndKPLocalServiceUtil.addForm3RepByDirAndKP(form3RepByDirAndKPList);
					} catch (Exception e) {
						_log.error(e);
						resultJsonObject.put("status", false);
						resultJsonObject.put("msg",  CommonParser.fileExceptionMsg);
						return resultJsonObject;
					}
					
					Long form3RepByDirAndKPFileEntryId = 0l;
					form3RepByDirAndKPFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "form3ByKpXlsx");
					_log.info("form3RepByDirAndKPFileEntryId: " + form3RepByDirAndKPFileEntryId);
					JSONObject form3RepByDirAndKPFileEntryJO = JSONFactoryUtil.createJSONObject();
					form3RepByDirAndKPFileEntryJO.put("Name", "Form 3 Report by Director and KP");
					form3RepByDirAndKPFileEntryJO.put("fileEntryId", form3RepByDirAndKPFileEntryId);
					try {
						errorExcel.close();
						try {
							serviceContext = ServiceContextFactory.getInstance(resourceRequest);
						} catch (PortalException e) {
							 _log.error(e);
						}
					//	_log.info("fileList---------"+fileList);
						fileList.put(form3RepByDirAndKPFileEntryJO);
						_log.info("fileList: " + fileList.toString());
						resultJsonObject.put("fileList", fileList);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {

						OutputStream stream = null;

						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
						
						errorExcel.write(stream);
						
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}

		}
		return resultJsonObject;
	}
	
	@SuppressWarnings("deprecation")
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest) {

		File file = new File(filepath);
		String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String title = filename + ".xlsx";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, 0, title, mimeType, title, description, "",
					file, serviceContext);
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			 _log.error(e);
		}
		return downloadUrl;
	}
	
	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest, String documentName) {
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName =date.getTime()+ uploadPortletRequest.getFileName(documentName);

		File file = uploadPortletRequest.getFile(documentName);

		String mimeType = uploadPortletRequest.getContentType(documentName);

		String title = fileName;

		String description = documentName;

		long repositoryId = themeDisplay.getScopeGroupId();
	
		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();

		} catch (Exception e) {

			_log.info(e.getMessage());

			 _log.error(e);
		}
		return 0;
	}
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public List<String> getSheetNameForForm1A() {
		
		List<String> sheetNamefileList = new ArrayList<>();
		sheetNamefileList.add("Form_1");
//		sheetNamefileList.add("Form 1A");
//		sheetNamefileList.add("Form 1A_2");
//		sheetNamefileList.add("Form 1A_3");
//		sheetNamefileList.add("Form 1A_4");
//		sheetNamefileList.add("Form 1A_5");
//		sheetNamefileList.add("Form 1A_6");
//		sheetNamefileList.add("Form 1A_7");
//		sheetNamefileList.add("Form 1A_8");
//		sheetNamefileList.add("Form 1A_9");
//		sheetNamefileList.add("Form 1A_10");
		
		return sheetNamefileList;
		
	}
	
	public List<String> getSheetNameForQuarterlySubCompForms() {
		
		List<String> sheetNamefileList = new ArrayList<>();
		
		
		sheetNamefileList.add("Form_2");
		//sheetNamefileList.add("FORM_3 -Scheme -TAX-T2");
		sheetNamefileList.add("Form 3_ Other_schemes");
		sheetNamefileList.add("FORM_3 - Scheme - A - Tier- I");
		sheetNamefileList.add("FORM_3 - Scheme - E - Tier - I");
		sheetNamefileList.add("FORM_3 - Scheme - E - Tier - II");
		sheetNamefileList.add("FORM_3 - Scheme - C - Tier - I");
		sheetNamefileList.add("FORM_3 - Scheme - C - Tier - II");
		sheetNamefileList.add("FORM_3 - Scheme - G - Tier - I");
		sheetNamefileList.add("FORM_3 - Scheme - G - Tier - II");
		sheetNamefileList.add("FORM_3 - Scheme - CG");
		sheetNamefileList.add("FORM_3 - Scheme - SG");
		sheetNamefileList.add("FORM_3 - Scheme -Corp- CG");
		sheetNamefileList.add("FORM_3 - Scheme -APY");
		sheetNamefileList.add("FORM_3 - Scheme - NPS Lite");
		sheetNamefileList.add("Cashflow");
		
//		sheetNamefileList.add("FORM 3 Scheme - A - Tier -  I");
//		sheetNamefileList.add("FORM 3 Scheme -TAX-T2");
//		sheetNamefileList.add("FORM 3 Scheme - E - Tier - I");
//		sheetNamefileList.add("FORM 3 Scheme - E - Tier - II");
//		sheetNamefileList.add("FORM 3 Scheme - C - Tier - I");
//		sheetNamefileList.add("FORM 3 Scheme - C - Tier - II");
//		sheetNamefileList.add("FORM 3 Scheme - G - Tier - I");
//		sheetNamefileList.add("FORM 3 Scheme - G - Tier - II");
//		sheetNamefileList.add("FORM 3 Scheme - CG");
//		sheetNamefileList.add("FORM 3 Scheme - SG");
//		sheetNamefileList.add("FORM 3 Scheme - Corp CG");
//		sheetNamefileList.add("FORM 3 Scheme - APY");
//		sheetNamefileList.add("FORM 3 Scheme - NPS Lite");
//		sheetNamefileList.add("Form 4");
//		sheetNamefileList.add("Form 5");
		

		
//		sheetNamefileList.add("Form 1B");
//		sheetNamefileList.add("FORM 2 -Scheme -TAX-T2");
//		sheetNamefileList.add("FORM 2 - Scheme - A - Tier- I");
//		sheetNamefileList.add("FORM 2 - Scheme - E - Tier - I");
//		sheetNamefileList.add("FORM 2 - Scheme - E - Tier - II");
//		sheetNamefileList.add("FORM 2 - Scheme - C - Tier - I");
//		sheetNamefileList.add("FORM 2 - Scheme - C - Tier - II");
//		sheetNamefileList.add("FORM 2 - Scheme - G - Tier - I");
//		sheetNamefileList.add("FORM 2 - Scheme - G - Tier - II");
//		sheetNamefileList.add("FORM 2 - Scheme - CG");
//		sheetNamefileList.add("FORM 2 - Scheme - SG");
//		sheetNamefileList.add("FORM 2 - Scheme -Corp- CG");
//		sheetNamefileList.add("FORM 2 - Scheme -APY");
//		sheetNamefileList.add("FORM 2 - Scheme - NPS Lite");
//
//		sheetNamefileList.add("Cashflow");
//		sheetNamefileList.add("FORM 3 Scheme - A - Tier -  I");
//		sheetNamefileList.add("FORM 3 Scheme -TAX-T2");
//		sheetNamefileList.add("FORM 3 Scheme - E - Tier - I");
//		sheetNamefileList.add("FORM 3 Scheme - E - Tier - II");
//		sheetNamefileList.add("FORM 3 Scheme - C - Tier - I");
//		sheetNamefileList.add("FORM 3 Scheme - C - Tier - II");
//		sheetNamefileList.add("FORM 3 Scheme - G - Tier - I");
//		sheetNamefileList.add("FORM 3 Scheme - G - Tier - II");
//		sheetNamefileList.add("FORM 3 Scheme - CG");
//		sheetNamefileList.add("FORM 3 Scheme - SG");
//		sheetNamefileList.add("FORM 3 Scheme - Corp CG");
//		sheetNamefileList.add("FORM 3 Scheme - APY");
//		sheetNamefileList.add("FORM 3 Scheme - NPS Lite");
//		sheetNamefileList.add("Form 4");
//		sheetNamefileList.add("Form 5");
		

		
		return sheetNamefileList;
		
	}
	
	public List<String> getSheetNameForForm3() {
		
		List<String> sheetNamefileList = new ArrayList<>();
		sheetNamefileList.add("Investment During the Period");
		sheetNamefileList.add("Self Dealing or Front Running");
		
		return sheetNamefileList;
		
	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog2(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks, fileList);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	_log.info("update report log end ---------");
	}
	
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

}
