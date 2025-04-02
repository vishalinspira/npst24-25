package com.annexure.viia.resource;


import com.annexure.viia.constants.AnnexureVIIaPortletKeys;
import com.annexure.viia.util.ParseSheetOne;
import com.annexure.viia.util.ParseSheetTwo;
import com.annexure.viia.util.ValidateFileName;
import com.daily.average.service.model.AnnexviiaKfintech;
import com.daily.average.service.model.AnnexviiaNsdl;
import com.daily.average.service.service.AnnexviiaKfintechLocalServiceUtil;
import com.daily.average.service.service.AnnexviiaNsdlLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
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

import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { "javax.portlet.name=" + AnnexureVIIaPortletKeys.ANNEXUREVIIA,
"mvc.command.name=/annexureviia/save" }, service = MVCResourceCommand.class)
public class SaveAnnexureVIIa implements MVCResourceCommand{
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
		
		String fileName = uploadPortletRequest.getFileName("annexureviiaFile");

		File file = uploadPortletRequest.getFile("annexureviiaFile");

		String mimeType = uploadPortletRequest.getContentType("annexureviiaFile");

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
				
				JSONArray annexviiaNsdlJsonArray= JSONFactoryUtil.createJSONArray();
				List<AnnexviiaNsdl> annexviiaNsdls= new ArrayList<AnnexviiaNsdl>();
				
				JSONArray annexviiaKfintechJsonArray= JSONFactoryUtil.createJSONArray();
				List<AnnexviiaKfintech> annexviiaKfintechs= new ArrayList<AnnexviiaKfintech>();
				
				//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
				
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
				
				resultJsonObject = ParseSheetOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, annexviiaNsdlJsonArray, annexviiaNsdls, decimalFormat, resultJsonObject, errorSheetNameList,reportUploadLogId);
				resultJsonObject = ParseSheetTwo.saveSheetTwo(file, workbook, errorArray, xx, isexcelhaserror, userId, annexviiaKfintechs, annexviiaKfintechJsonArray, decimalFormat, resultJsonObject, errorSheetNameList,reportUploadLogId);
				
				
				_log.info(annexviiaNsdlJsonArray.toString());
				if(resultJsonObject.getBoolean("status")) {
					if (!isexcelhaserror) {
						
						
						AnnexviiaNsdlLocalServiceUtil.addAnnexviiaNsdl(annexviiaNsdls);
						_log.info("annexviiaNsdls datacount"+annexviiaNsdls.size());
						
						AnnexviiaKfintechLocalServiceUtil.addAnnexviiaKfintechs(annexviiaKfintechs);
						_log.info("annexviiaKfintechs datacount"+annexviiaKfintechs.size());
						
						
						Long fileEntryId = 0l;
						fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
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
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("annexureviiaFile");

		File file = uploadPortletRequest.getFile("annexureviiaFile");

		String mimeType = uploadPortletRequest.getContentType("annexureviiaFile");

		String title = fileName;

		String description = "D-Remit returns monthly MIS";

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


