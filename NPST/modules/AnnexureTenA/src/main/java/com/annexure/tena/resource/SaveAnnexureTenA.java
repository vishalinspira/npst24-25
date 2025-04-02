package com.annexure.tena.resource;


import com.annexure.tena.constants.AnnexureTenAPortletKeys;
import com.annexure.tena.util.ParseSummary;
import com.annexure.tena.util.ParseTenFive;
import com.annexure.tena.util.ParseTenFour;
import com.annexure.tena.util.ParseTenThree;
import com.annexure.tena.util.ParseTenTwo;
import com.annexure.tena.util.ValidateFileName;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.daily.pfm.service.model.AnnexTena;
import com.daily.pfm.service.model.AnnexTenaI;
import com.daily.pfm.service.model.AnnexTenaII;
import com.daily.pfm.service.model.AnnexTenaIII;
import com.daily.pfm.service.model.AnnexTenaIV;
import com.daily.pfm.service.model.AnnexTenaSummery;
import com.daily.pfm.service.service.AnnexTenaIIILocalServiceUtil;
import com.daily.pfm.service.service.AnnexTenaIILocalServiceUtil;
import com.daily.pfm.service.service.AnnexTenaILocalServiceUtil;
import com.daily.pfm.service.service.AnnexTenaIVLocalServiceUtil;
import com.daily.pfm.service.service.AnnexTenaSummeryLocalServiceUtil;
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

import nps.common.service.constants.NameMappingConstants;
import nps.email.api.api.ExcelValidationAn10Api;


@Component(property = { "javax.portlet.name=" + AnnexureTenAPortletKeys.ANNEXURETENA,
"mvc.command.name=/annextena/save" }, service = MVCResourceCommand.class)
public class SaveAnnexureTenA implements MVCResourceCommand{
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
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
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		String cra = "";
		if(reportMasterId == 11) {
			cra = "NSDL";
		} else if(reportMasterId == 23){
			cra = "Kfintech";
		} else if(reportMasterId == 128){
			cra = "CAMS";
		}
		
		String fileName = uploadPortletRequest.getFileName("annextenaFile");

		File file = uploadPortletRequest.getFile("annextenaFile");

		String mimeType = uploadPortletRequest.getContentType("annextenaFile");

		String title = fileName;
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(ValidateFileName.isValidfile(fileName)) {
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			
			int dataRowNum = 5;
			
			
			
			JSONArray annexTenaIJsonArray= JSONFactoryUtil.createJSONArray();
			List<AnnexTenaI> annexTenaIs= new ArrayList<AnnexTenaI>();
			
			JSONArray annexTenaIIJsonArray= JSONFactoryUtil.createJSONArray();
			List<AnnexTenaII> annexTenaIIs= new ArrayList<AnnexTenaII>();
			
			JSONArray annexTenaIIIJsonArray= JSONFactoryUtil.createJSONArray();
			List<AnnexTenaIII> annexTenaIIIs= new ArrayList<AnnexTenaIII>();
			
			JSONArray annexTenaIVJsonArray= JSONFactoryUtil.createJSONArray();
			List<AnnexTenaIV> annexTenaIVs= new ArrayList<AnnexTenaIV>();
			
			JSONArray annextenasummaryArray= JSONFactoryUtil.createJSONArray();
			List<AnnexTenaSummery> annexTenaSummeryList= new ArrayList<AnnexTenaSummery>();
			
			
			
			
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
			
			//parsing sheetone in util class
			JSONArray errorSheetNameList = JSONFactoryUtil.createJSONArray();
			
			//resultJsonObject = ParseTenOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, annexTenaJsonArray, annexTenas, resultJsonObject, errorSheetNameList);
			resultJsonObject = ParseSummary.saveSheetSummary(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, annextenasummaryArray, annexTenaSummeryList, resultJsonObject, errorSheetNameList,reportUploadLogId,cra);
			resultJsonObject = ParseTenTwo.saveSheetTwo(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, annexTenaIJsonArray, annexTenaIs, resultJsonObject, errorSheetNameList,reportUploadLogId,cra);
			resultJsonObject = ParseTenThree.saveSheetThree(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, annexTenaIIJsonArray, annexTenaIIs, resultJsonObject, errorSheetNameList,reportUploadLogId,cra);
			resultJsonObject = ParseTenFour.saveSheetFour(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, annexTenaIIIs, annexTenaIIIJsonArray, resultJsonObject, errorSheetNameList,reportUploadLogId,cra);
			resultJsonObject = ParseTenFive.saveSheetFive(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, annexTenaIVJsonArray, annexTenaIVs, resultJsonObject, errorSheetNameList, reportUploadLogId,cra);
			
			
			
			if(resultJsonObject.getBoolean("status")) {
			if (!isexcelhaserror) {
				
				
				
				AnnexTenaILocalServiceUtil.addannexTenaIs(annexTenaIs);
				_log.info("annexTenaI datacount"+annexTenaIs.size());
				
				AnnexTenaIILocalServiceUtil.addannexTenaIIs(annexTenaIIs);
				_log.info("annexTenaII datacount"+annexTenaIIs.size());
				
				AnnexTenaIIILocalServiceUtil.addannexTenaIIIs(annexTenaIIIs);
				_log.info("annexTenaIII datacount"+annexTenaIIIs.size());
				
				AnnexTenaIVLocalServiceUtil.addannexTenaIVs(annexTenaIVs);
				_log.info("annexTenaIV datacount"+annexTenaIVs.size());
				
				AnnexTenaSummeryLocalServiceUtil.addAnnexTenaSummery(annexTenaSummeryList);
				_log.info("annexTenaSummery datacount"+annexTenaSummeryList.size());
			
				
				
				Long fileEntryId = 0l;
				fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest,cra);
				try {
					errorExcel.close();
					
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
					//resultJsonObject.put("pdfURL",  pdfTable(statusDayss, themeDisplay, resourceRequest));
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
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename.");
			return resultJsonObject;
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
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest,String cra) {
		String craName=cra.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
_log.info("cra name: "+cra+  " cra old name:  "+cra);
		String fileName = date.getTime()+ " Withdrawal MIS "+craName;

		File file = uploadPortletRequest.getFile("annextenaFile");

		String mimeType = uploadPortletRequest.getContentType("annextenaFile");

		String title = date.getTime()+ " Withdrawal MIS "+craName;

		String description = fileName;

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry  fileEntry=DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}


