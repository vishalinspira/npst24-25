package com.annual.proxyvotingreports.resource;

import com.annual.proxyvotingreports.constants.AnnualProxyVotingReportsPortletKeys;
import com.annual.proxyvotingreports.util.ParseAnnualProxyPfmIII;
import com.annual.proxyvotingreports.util.ValidateSheetName;
import com.daily.average.service.model.AnnualProxyPfmIII;
import com.daily.average.service.service.AnnualProxyPfmIIILocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.IOException;
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
		"javax.portlet.name=" + AnnualProxyVotingReportsPortletKeys.ANNUALPROXYVOTINGREPORTS,
		"mvc.command.name=" + AnnualProxyVotingReportsPortletKeys.annualProxyVotingDocumentUpload,
		}, 
service = MVCResourceCommand.class)

public class AnnualProxyVotingReports implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(AnnualProxyVotingReports.class.getName());

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		
		return false;
	}
	
	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
        _log.info("fileUpload:::::::::::::::::::::::::::");
		
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		//Unable to parse upload request: java.io.EOFException
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
			
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//_log.info("line 104"+reportUploadLogIdString); // Getting null here
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		long reportUploadLogId=ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		_log.info("reportUploadLogId: "+reportUploadLogId);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
			
		resultJsonObject = parseExcel(themeDisplay, resourceRequest);
		
		if(resultJsonObject.getBoolean("status")) {
			Long excelFileEntryId = 0l;
			excelFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "annualProxyReportExcelFile");
			Long pdfFileEntryId = 0l;
			pdfFileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "annualProxyReportPDFFile");
			
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			
			JSONObject annualProxyReportPDFJO = JSONFactoryUtil.createJSONObject();
			annualProxyReportPDFJO.put("Name", "Annual Proxy Voting Reports PDF");
			annualProxyReportPDFJO.put("fileEntryId", pdfFileEntryId);
			jsonArray.put(annualProxyReportPDFJO);
			
			JSONObject annualProxyReportExcelJO = JSONFactoryUtil.createJSONObject();
			annualProxyReportExcelJO.put("Name", "Annual Evoting Summary Excel");
			annualProxyReportExcelJO.put("fileEntryId", excelFileEntryId);
			jsonArray.put(annualProxyReportExcelJO);
			
			try {
				String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
			
				updateReportLog ( userId, pdfFileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, 
						serviceContext,remarks, jsonArray.toString());
				//resultJsonObject.put("pdfURL",  pdfTable(annextureXas, themeDisplay, resourceRequest));
			} catch (Exception e) {
				 _log.error(e);
			}
			resultJsonObject.put("status", true);
		}
		return resultJsonObject;
	}
	
	public JSONObject parseExcel(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
	/*	String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
	*/	
		//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		//long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		//String fileName = uploadPortletRequest.getFileName("annualProxyReportExcelFile");

		File file = uploadPortletRequest.getFile("annualProxyReportExcelFile");
		_log.info("file: "+file);

		//String mimeType = uploadPortletRequest.getContentType("annualProxyReportExcelFile");

		//String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
	/*	JSONArray annualProxyPfmIJsonArray= JSONFactoryUtil.createJSONArray();
		List<AnnualProxyPfmI> annualProxyPfmIs= new ArrayList<AnnualProxyPfmI>();
		
		JSONArray annualProxyPfmIIJsonArray= JSONFactoryUtil.createJSONArray();
		List<AnnualProxyPfmII> annualProxyPfmIIs= new ArrayList<AnnualProxyPfmII>();
	*/	 _log.info("line 178");
		JSONArray annualProxyPfmIIIJsonArray= JSONFactoryUtil.createJSONArray();
		List<AnnualProxyPfmIII> annualProxyPfmIIIs= new ArrayList<AnnualProxyPfmIII>();
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		 _log.info("line 184"+resultJsonObject.getBoolean("status"));
		if(resultJsonObject.getBoolean("status")) {
			_log.info("Line 186");
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
			
			List<String> errorList = ValidateSheetName.ValidateExcelSheetName(file, workbook);
			
			if (errorList.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				StringBuilder sb = new StringBuilder();
				for(String L : errorList) {
					if(errorList.size() == 1) {
						sb.append(L);
					}else {
						sb.append(L);
						sb.append(",");
					}
				}
				
				String errorString = sb.toString();
				resultJsonObject.put("errorSheetNameList", errorString);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				// return
				return resultJsonObject;
				
			}else {
		   	
				//parsing sheetone in util class
				
			/*	resultJsonObject = ParseAnnualProxyPfmI.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, annualProxyPfmIs, annualProxyPfmIJsonArray, decimalFormat,reportUploadLogId, resultJsonObject);
				if(!resultJsonObject.getBoolean("status")) {
					return resultJsonObject;
				}
				resultJsonObject = ParseAnnualProxyPfmII.saveSheetTwo(file, workbook, userId, errorArray, xx, isexcelhaserror, annualProxyPfmIIs, annualProxyPfmIIJsonArray, decimalFormat,reportUploadLogId,resultJsonObject);
				if(!resultJsonObject.getBoolean("status")) {
					return resultJsonObject;
				} */
				_log.info("Line 237");
				resultJsonObject = ParseAnnualProxyPfmIII.saveSheetThree(file, workbook, userId, errorArray, xx, isexcelhaserror, annualProxyPfmIIIs, annualProxyPfmIIIJsonArray, decimalFormat,reportUploadLogId, resultJsonObject);
				if(!resultJsonObject.getBoolean("status")) {
					return resultJsonObject;
				}
			//	_log.info(annualProxyPfmIJsonArray.toString());
				
				if (!isexcelhaserror) {
					
					try {
					//	AnnualProxyPfmILocalServiceUtil.addAnnualProxyPfmIs(annualProxyPfmIs);
					//	AnnualProxyPfmIILocalServiceUtil.addAnnualProxyPfmIIs(annualProxyPfmIIs);
						AnnualProxyPfmIIILocalServiceUtil.addAnnualProxyPfmIIIs(annualProxyPfmIIIs);
					} catch (Exception e) {
						_log.error(e);
						resultJsonObject.put("status", false);
						resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						return resultJsonObject;
					}
					
					
						/*
						 * Long fileEntryId = 0l; fileEntryId = uploadFILETOFOLDER(themeDisplay,
						 * resourceRequest); try { errorExcel.close(); String remarks =
						 * ParamUtil.getString(uploadPortletRequest, "remarks"); updateReportLog (
						 * userId, fileEntryId, true, reportUploadLogId,
						 * WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
						 * //resultJsonObject.put("pdfURL", pdfTable(statusDayss, themeDisplay,
						 * resourceRequest)); } catch (Exception e) { _log.error(e); }
						 */
					resultJsonObject.put("status", true);
				} else {
						/*
						 * String downloadUrl = null; try {
						 * 
						 * OutputStream stream = null;
						 * 
						 * File errortempfile = File.createTempFile("error", "xlsx"); stream = new
						 * FileOutputStream(errortempfile);
						 * 
						 * errorExcel.write(stream);
						 * 
						 * String filePath = errortempfile.getPath(); String filename =
						 * errortempfile.getName(); stream.close(); errorExcel.close(); downloadUrl =
						 * fileUpload(themeDisplay, filePath, filename, resourceRequest); } catch
						 * (Exception e) { _log.error(e); } resultJsonObject.put("downloadUrl",
						 * downloadUrl);
						 */
					resultJsonObject.put("status", false);
				}
			}
		}
		return resultJsonObject;
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Annually");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, 
			long reportUploadLogId, int status, String statusByUserName, 
			ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog2(createDate, createdBy,
		fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, 
		serviceContext,remarks,fileList);
//		(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}
