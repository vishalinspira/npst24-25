package com.monthly.pfmForm.mvc;

import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.pfm.service.model.OverallStatusOfStateGovt;
import com.daily.pfm.service.model.OverallStatusSectorWise;
import com.daily.pfm.service.model.StatusOfAtalPensionScheme;
import com.daily.pfm.service.model.StatusOfCentralGovernment;
import com.daily.pfm.service.model.StatusOfCorporateSector;
import com.daily.pfm.service.model.StatusOfIndividualSubscriber;
import com.daily.pfm.service.model.StatusOfNPSLite;
import com.daily.pfm.service.service.OverallStatusOfStateGovtLocalServiceUtil;
import com.daily.pfm.service.service.OverallStatusSectorWiseLocalServiceUtil;
import com.daily.pfm.service.service.StatusOfAtalPensionSchemeLocalServiceUtil;
import com.daily.pfm.service.service.StatusOfCentralGovernmentLocalServiceUtil;
import com.daily.pfm.service.service.StatusOfCorporateSectorLocalServiceUtil;
import com.daily.pfm.service.service.StatusOfIndividualSubscriberLocalServiceUtil;
import com.daily.pfm.service.service.StatusOfNPSLiteLocalServiceUtil;
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

import com.mothly.pfmForm.constants.MonthlyPFMForm1to8PortletKeys;
import com.monthly.pfmForm.util.OverallStatusOfSector;
import com.monthly.pfmForm.util.OverallStatusStateGovernment;
import com.monthly.pfmForm.util.StatusAtalPensionScheme;
import com.monthly.pfmForm.util.StatusCorporateSector;
import com.monthly.pfmForm.util.StatusIndividualSubscriber;
import com.monthly.pfmForm.util.StatusNPSLite;
import com.monthly.pfmForm.util.StatusOfCenGovt;
import com.monthly.pfmForm.util.ValiateSheetName;

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


@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + MonthlyPFMForm1to8PortletKeys.MONTHLYPFMFORM1TO8,
			"mvc.command.name="+MonthlyPFMForm1to8PortletKeys.SAVE_MOTHLY_PFM_FORM_1TO8	
		},
		service = MVCResourceCommand.class
		)

public class PFMReportUploadMVCResouce implements MVCResourceCommand {

	
	private static Log _log = LogFactoryUtil.getLog(PFMReportUploadMVCResouce.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		// TODO Auto-generated method stub
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
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String cra = IntermediaryListLocalServiceUtil.fetchIntermediaryByReportLogId(reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("AllSectorFile");
		_log.info("file name--->"+fileName);
		
		File file = uploadPortletRequest.getFile("AllSectorFile");

		String mimeType = uploadPortletRequest.getContentType("AllSectorFile");
		_log.info("type of file--->"+mimeType);

		String title = fileName;
		_log.info("title--->"+title);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1
		JSONArray form1JsonArray = JSONFactoryUtil.createJSONArray();
		List<OverallStatusOfStateGovt> form1List = new ArrayList<OverallStatusOfStateGovt>();
		
		//Form 2
		JSONArray form2JsonArray = JSONFactoryUtil.createJSONArray();
		List<OverallStatusSectorWise> form2List = new ArrayList<OverallStatusSectorWise>();
		
		//Form 3
		JSONArray form3JsonArray = JSONFactoryUtil.createJSONArray();
		List<StatusOfAtalPensionScheme> form3AtalList = new ArrayList<StatusOfAtalPensionScheme>();
		
		//Form 4
		JSONArray form4JsonArray = JSONFactoryUtil.createJSONArray();
		List<StatusOfCentralGovernment> form4List = new ArrayList<StatusOfCentralGovernment>();
		
		JSONArray for5JsonArray = JSONFactoryUtil.createJSONArray();
		List<StatusOfCorporateSector> form5CorpList = new ArrayList<StatusOfCorporateSector>();
		
		JSONArray form6JsonArray = JSONFactoryUtil.createJSONArray();
		List<StatusOfIndividualSubscriber> form6IndiSubsList = new ArrayList<StatusOfIndividualSubscriber>();
		
		JSONArray form7JsonArray = JSONFactoryUtil.createJSONArray();
		List<StatusOfNPSLite> form7NPSLiteList = new ArrayList<StatusOfNPSLite>();
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
		
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
			
			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		//	XSSFRow xxx = xx.createRow(1);
			/*
			 * XSSFCell rowNumCel = xxx.createCell(1); rowNumCel.setCellValue("RowNum");
			 * XSSFCell fileNamecell = xxx.createCell(2);
			 * fileNamecell.setCellValue("Pension Fund");
			 */
			
			boolean isexcelhaserror = false;
			
			//sheet validation
			List<String> errorlist = ValiateSheetName.validateExcelSheetName(file, workbook);
			_log.info("error list size---->"+errorlist.size());
			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					 _log.error(e);
				}
				// return
				return resultJsonObject;
			}else {
				
				resultJsonObject = OverallStatusStateGovernment.saveStateGovernmentStatus(file, workbook, userId, errorArray, xx, isexcelhaserror, form1JsonArray, form1List, decimalFormat, reportUploadLogId,cra);
				if(!resultJsonObject.getBoolean("status")) {
					_log.info("kuch toh gadbad hai.....");
					return resultJsonObject;
				}
				else {
					try {
						OverallStatusOfStateGovtLocalServiceUtil.addOverallStatusOfStateGovtForm(form1List);
					} catch (Exception e) {
						_log.error(e);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}
				}
				resultJsonObject = OverallStatusOfSector.saveSectorStatus(file, workbook, userId, errorArray, xx, isexcelhaserror, form2JsonArray, form2List, decimalFormat, reportUploadLogId, cra);
				if(!resultJsonObject.getBoolean("status")) {
					_log.info("kuch toh gadbad hai.....");
					return resultJsonObject;
				}
				else {
					try {
						OverallStatusSectorWiseLocalServiceUtil.addOverallStatusSectorWiseForm(form2List);
					} catch (Exception e) {
						_log.error(e);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}
				}
				resultJsonObject = StatusOfCenGovt.saveCentralGovrnmentStatus(file, workbook, userId, errorArray, xx, isexcelhaserror, form4JsonArray, form4List, decimalFormat, reportUploadLogId, cra);
				if(!resultJsonObject.getBoolean("status")) {
					_log.info("kuch toh gadbad hai.....");
					return resultJsonObject;
				}
				else {
					try {
						StatusOfCentralGovernmentLocalServiceUtil.addOverallStatusOfCentralGovernmentForm(form4List);
					} catch (Exception e) {
						_log.error(e);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}
				}
				resultJsonObject = StatusIndividualSubscriber.saveIndividualSubscriberStatus(file, workbook, userId, errorArray, xx, isexcelhaserror, form6JsonArray, form6IndiSubsList, decimalFormat, reportUploadLogId, cra);
				if(!resultJsonObject.getBoolean("status")) {
					_log.info("kuch toh gadbad hai.....");
					return resultJsonObject;
				}
				else {
					try {
						StatusOfIndividualSubscriberLocalServiceUtil.addOverallStatusOfIndividualSubscriberForm(form6IndiSubsList);
					} catch (Exception e) {
						_log.error(e);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}
				}
				  resultJsonObject = StatusAtalPensionScheme.saveAtalPensionSchemeSubscriberStatus(file, workbook, userId, errorArray, xx, isexcelhaserror, form3JsonArray, form3AtalList, decimalFormat, reportUploadLogId, cra); 
				  if(!resultJsonObject.getBoolean("status"))	{
					  _log.info("kuch toh gadbad hai....."); 
					  return resultJsonObject; 
					  }
				  else {
						try {
							StatusOfAtalPensionSchemeLocalServiceUtil.addOverallStatusOfAtalPensionSchemeForm(form3AtalList);
						} catch (Exception e) {
							_log.error(e);
							 resultJsonObject.put("status", false);
							 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
							 return resultJsonObject;
						}
					} 
				  resultJsonObject = StatusCorporateSector.saveCorporateStatus(file, workbook, userId, errorArray, xx, isexcelhaserror, for5JsonArray, form5CorpList, decimalFormat, reportUploadLogId, cra); 
				  if(!resultJsonObject.getBoolean("status")){
					  _log.info("kuch toh gadbad hai.....");
					  return resultJsonObject; 
				}
				  else {
						try {
							StatusOfCorporateSectorLocalServiceUtil.addOverallStatusOfCorporateSectorForm(form5CorpList);
						} catch (Exception e) {
							_log.error(e);
							 resultJsonObject.put("status", false);
							 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
							 return resultJsonObject;
						}
					}
				  resultJsonObject = StatusNPSLite.saveNPSLiteStatus(file, workbook, userId, errorArray, xx, isexcelhaserror, form7JsonArray, form7NPSLiteList, decimalFormat, reportUploadLogId, cra); 
				  if(!resultJsonObject.getBoolean("status"))  {
					  _log.info("kuch toh gadbad hai.....");
					  return resultJsonObject; 
					  }
				  else {
						try {
							StatusOfNPSLiteLocalServiceUtil.addOverallStatusOfNPSLiteForm(form7NPSLiteList);
						} catch (Exception e) {
							_log.error(e);
							 resultJsonObject.put("status", false);
							 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
							 return resultJsonObject;
						}
					}
				
				if (!isexcelhaserror) {
					
					/*try {
						_log.info("size of form list----->"+form1List.size());
						OverallStatusOfStateGovtLocalServiceUtil.addOverallStatusOfStateGovtForm(form1List);
						OverallStatusSectorWiseLocalServiceUtil.addOverallStatusSectorWiseForm(form2List);
						StatusOfCentralGovernmentLocalServiceUtil.addOverallStatusOfCentralGovernmentForm(form4List);
						StatusOfIndividualSubscriberLocalServiceUtil.addOverallStatusOfIndividualSubscriberForm(form6IndiSubsList);
						StatusOfAtalPensionSchemeLocalServiceUtil.addOverallStatusOfAtalPensionSchemeForm(form3AtalList);
						StatusOfCorporateSectorLocalServiceUtil.addOverallStatusOfCorporateSectorForm(form5CorpList);
						StatusOfNPSLiteLocalServiceUtil.addOverallStatusOfNPSLiteForm(form7NPSLiteList);
					} catch (Exception e1) {
						
						_log.error(e1);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}*/
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} 
				if(isexcelhaserror) {
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
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Date date =new Date();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime() + uploadPortletRequest.getFileName("AllSectorFile");

		File file = uploadPortletRequest.getFile("AllSectorFile");

		String mimeType = uploadPortletRequest.getContentType("AllSectorFile");

		String title = fileName;

		String description = "AllSectorFile";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getPrimaryKey();

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "PFM");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogPFMCRALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);
		//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	/*
	 * @Reference OverallStatusOfStateGovt _overallStatusOfStateGOvernment;
	 */
}
