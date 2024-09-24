package com.asp.file.upload.resources;

import com.asp.file.upload.constants.asp_file_uploadPortletKeys;
import com.asp.file.upload.util.Asp_File_Upload_Excel_util;
import com.asp.file.upload.util.ValidateSheetName;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.service.ReportUploadLogCRAAMLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.daily.pfm.service.model.Asp_report;
import com.daily.pfm.service.service.Asp_reportLocalServiceUtil;
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
import com.liferay.portal.kernel.search.ParseException;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
				"javax.portlet.name=" + asp_file_uploadPortletKeys.ASP_FILE_UPLOAD,
				"mvc.command.name=/saveAspReport/data",
		},
		service = MVCResourceCommand.class
		)
public class SaveAsp_File_Upload implements MVCResourceCommand{
	private static Log _log  = LogFactoryUtil.getLog(SaveAsp_File_Upload.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		//_log.info("SaveInvestmentManagement.serveResource()");
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
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		_log.info("report upload log id string ---> "+reportUploadLogId);
		
		//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		_log.info("reportUploadLogId ---> " + reportUploadLogId);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("fileList", JSONFactoryUtil.createJSONArray());
		
		resultJsonObject = saveAspExcel(uploadPortletRequest, themeDisplay, userId, 
				resourceRequest, reportUploadLogId, statusByUserName, serviceContext,
				resultJsonObject);
		
		 if(!resultJsonObject.getBoolean("status")) {
			 return resultJsonObject; 
			 }
//		 else {
//		 resultJsonObject = saveNpsTrust(uploadPortletRequest, themeDisplay, userId,
//		 resourceRequest, reportUploadLogId, statusByUserName, serviceContext);
//		 }
		
		resultJsonObject.put("status", true);
		if(!resultJsonObject.getBoolean("status")) {
			return resultJsonObject;
		}else {
			resultJsonObject = pdfUpload(uploadPortletRequest, themeDisplay, resourceRequest, resultJsonObject);
		}
		_log.info("result status--->"+resultJsonObject.getBoolean("status"));
		
		String fileList = resultJsonObject.getJSONArray("fileList").toString();
		long fileEntryId = resultJsonObject.getLong("fileEntryId");
		_log.info("file entry id--->"+fileEntryId);
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		updateReportLog(userId, fileEntryId , true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks, fileList );
		return resultJsonObject;
	}
	
	public JSONObject saveAspExcel(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, 
			long userId, ResourceRequest resourceRequest, long reportUploadLogId,
			String statusByUserName, ServiceContext serviceContext, 
			JSONObject resultJsonObject) {
		
		
		String fileName = uploadPortletRequest.getFileName("AspReportExcel");
		_log.info("file name--->"+fileName);

		File file = uploadPortletRequest.getFile("AspReportExcel");
		_log.info("file object--->"+file);

		String mimeType = uploadPortletRequest.getContentType("AspReportExcel");
		_log.info("type of file--->"+mimeType);

		String title = fileName;
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
																																																																																																				
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray asp_reportJsonArray= JSONFactoryUtil.createJSONArray();
		List<Asp_report> asp_reports= new ArrayList<Asp_report>();
		
		
		
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;

		/**
		 * Create error excel
		 */
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		boolean isexcelhaserror = false;
		boolean isNoError = false;
		
		
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
			}
			else {
				
				resultJsonObject = Asp_File_Upload_Excel_util.saveAsp_reportSheet(file, workbook, userId, 
						errorArray, xx, isexcelhaserror, asp_reportJsonArray, asp_reports, decimalFormat, 
						reportUploadLogId,resultJsonObject);
				
				if(!resultJsonObject.getBoolean("status")) {
					return resultJsonObject;
				}
				_log.info("isexcelhaserror  "+isexcelhaserror);
				_log.info("asp_reports  "+asp_reports);
				if (!isexcelhaserror) {
					try {
						Asp_reportLocalServiceUtil.addAsp_reports(asp_reports);
					} catch (Exception e1) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						return resultJsonObject;
					}
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest, "AspReportExcel");
					JSONObject imfFileEntryJO = JSONFactoryUtil.createJSONObject();
					imfFileEntryJO.put("Name", "Asp Report Excel");
					imfFileEntryJO.put("fileEntryId", fileEntryId);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						
						
						fileList.put(imfFileEntryJO);
						resultJsonObject.put("fileList", fileList);
						//updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
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
	
	
public JSONObject pdfUpload(UploadPortletRequest uploadPortletRequest,ThemeDisplay themeDisplay, ResourceRequest resourceRequest, JSONObject resultJsonObject) {
		
		Long userId = themeDisplay.getUserId();
		 
		//UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		//JSONArray filelist =JSONFactoryUtil.createJSONArray();
		long fileid_i = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_i");
		long fileid_ii = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_ii");
		long fileid_iii = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_iii");
		long fileid_iv = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_iv");
		long fileid_v = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_v");
		long fileid_vi = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_vi");
		long fileid_vii = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_vii");
		long fileid_viii = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_viii");
		long fileid_ix = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_ix");
		long fileid_x = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_x");
		long fileid_xi = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_xi");
		long fileid_xii = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_xii");
		
		Long fileid_xiii = 0l;
		fileid_xiii = uploadFILETOFOLDER(themeDisplay, resourceRequest,"AspReportPdf_xiii");
		
		
		JSONObject uafd_i = JSONFactoryUtil.createJSONObject();
		uafd_i.put("Name", "Bajaj Allianz Asp Report");
		uafd_i.put("fileEntryId", fileid_i);
		
		JSONObject uafd_ii = JSONFactoryUtil.createJSONObject();
		uafd_ii.put("Name", "Canara HSBC ASP Report");
		uafd_ii.put("fileEntryId", fileid_ii);
		
		JSONObject uafd_iii = JSONFactoryUtil.createJSONObject();
		uafd_iii.put("Name", "Edelweiss Tokio ASP Report");
		uafd_iii.put("fileEntryId", fileid_iii);
		
		JSONObject uafd_iv = JSONFactoryUtil.createJSONObject();
		uafd_iv.put("Name", "HDFC Asp Report");
		uafd_iv.put("fileEntryId", fileid_iv);
		
		JSONObject uafd_v = JSONFactoryUtil.createJSONObject();
		uafd_v.put("Name", "ICICI Prudential Asp Report");
		uafd_v.put("fileEntryId", fileid_v);
		
		JSONObject uafd_vi = JSONFactoryUtil.createJSONObject();
		uafd_vi.put("Name", "IndiaFirst Asp Report");
		uafd_vi.put("fileEntryId", fileid_vi);
		
		JSONObject uafd_vii = JSONFactoryUtil.createJSONObject();
		uafd_vii.put("Name", "Kotak Mahindra Asp Report");
		uafd_vii.put("fileEntryId", fileid_vii);
		
		JSONObject uafd_viii = JSONFactoryUtil.createJSONObject();
		uafd_viii.put("Name", "LIC Asp Report");
		uafd_viii.put("fileEntryId", fileid_viii);
		
		JSONObject uafd_ix = JSONFactoryUtil.createJSONObject();
		uafd_ix.put("Name", "Max Asp Report");
		uafd_ix.put("fileEntryId", fileid_ix);
		
		JSONObject uafd_x = JSONFactoryUtil.createJSONObject();
		uafd_x.put("Name", "SBI Asp Report");
		uafd_x.put("fileEntryId", fileid_x);
		
		JSONObject uafd_xi = JSONFactoryUtil.createJSONObject();
		uafd_xi.put("Name", "Star Union Dai-ichi Asp Report");
		uafd_xi.put("fileEntryId", fileid_xi);
		
		JSONObject uafd_xii = JSONFactoryUtil.createJSONObject();
		uafd_xii.put("Name", "TATA AIA Asp Report");
		uafd_xii.put("fileEntryId", fileid_xii);
		
		
		
		
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		//updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks,filelist.toString());
		
		//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		JSONArray fileList = resultJsonObject.getJSONArray("fileList");
		fileList.put(uafd_i);
		fileList.put(uafd_ii);
		fileList.put(uafd_iii);
		fileList.put(uafd_iv);
		fileList.put(uafd_v);
		fileList.put(uafd_vi);
		fileList.put(uafd_vii);
		fileList.put(uafd_viii);
		fileList.put(uafd_ix);
		fileList.put(uafd_x);
		fileList.put(uafd_xi);
		fileList.put(uafd_xii);
		
		
		resultJsonObject.put("fileList", fileList);
		
		resultJsonObject.put("status", true);
		resultJsonObject.put("fileEntryId", fileid_xii);
		return resultJsonObject;
	}
	
	
	
	public static String formatDate(Date dateIn, String format) throws ParseException {
        String strDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(dateIn);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date d1 = sdf3.parse(formattedDate);

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            strDate = formatter.format(d1);
        } catch (Exception e) {
             _log.error(e);
        }
        return strDate;
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
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest, String document) {
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime() + uploadPortletRequest.getFileName(document);

		File file = uploadPortletRequest.getFile(document);

		String mimeType = uploadPortletRequest.getContentType(document);

		String title = fileName;

		String description = "";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogCRAAMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);
		
		//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

	
	
	
}
