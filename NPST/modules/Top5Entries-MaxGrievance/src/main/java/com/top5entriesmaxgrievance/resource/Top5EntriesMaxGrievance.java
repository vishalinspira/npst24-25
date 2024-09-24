package com.top5entriesmaxgrievance.resource;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalServiceUtil;
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
import com.top5entriesmaxgrievance.constants.Top5EntriesMaxGrievancePortletKeys;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { 
		"javax.portlet.name=" + Top5EntriesMaxGrievancePortletKeys.TOP5ENTRIESMAXGRIEVANCE,
		"mvc.command.name=" + Top5EntriesMaxGrievancePortletKeys.top5Entries,
		},
service = MVCResourceCommand.class)
public class Top5EntriesMaxGrievance implements MVCResourceCommand{
	private static Log _log = LogFactoryUtil.getLog(Top5EntriesMaxGrievance.class.getName());

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
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
        
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
//		String fileName = uploadPortletRequest.getFileName("top5Entries-MaxGrievance");
//		
//		_log.info("fileName::::::::::::::::::::::"+fileName);
//
//		File file = uploadPortletRequest.getFile("top5Entries-MaxGrievance");
//
//		String mimeType = uploadPortletRequest.getContentType("top5Entries-MaxGrievance");
//
//		String title = fileName;
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
			Long fileEntryId = 0l;
			fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest ,"top5Entries-MaxGrievancePDFFile");
			Long excel_1_fileEntryId = 0l;
			excel_1_fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest ,"top5Entries-MaxGrievanceXlssFile1");
			Long excel_2_fileEntryId = 0l;
			excel_2_fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest ,"top5Entries-MaxGrievanceXlssFile2");
			Long excel_3_fileEntryId = 0l;
			excel_3_fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest ,"top5Entries-MaxGrievanceXlssFile3");
			
			
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			jsonArray.put(fileEntryId);
			jsonArray.put(excel_1_fileEntryId);
			jsonArray.put(excel_2_fileEntryId);
			jsonArray.put(excel_3_fileEntryId);

			try {
				String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
				updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks, jsonArray.toString());
				resultJsonObject.put("status", true);
				//resultJsonObject.put("pdfURL",  pdfTable(annextureXas, themeDisplay, resourceRequest));
			} catch (Exception e) {
				 _log.error(e);
			}
			
		return resultJsonObject;
	}
	
	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest,String documentName) {
		
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogGrievAMPFRDALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks,fileList);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
}