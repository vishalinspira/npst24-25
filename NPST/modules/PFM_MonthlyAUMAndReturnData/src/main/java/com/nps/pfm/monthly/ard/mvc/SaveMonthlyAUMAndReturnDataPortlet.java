package com.nps.pfm.monthly.ard.mvc;

import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalServiceUtil;
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
import com.nps.pfm.monthly.ard.constants.PFMMonthlyAUMAndReturnDataPortletKeys;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PFMMonthlyAUMAndReturnDataPortletKeys.PFMMONTHLYAUMANDRETURNDATA,
				"mvc.command.name=/savepmar/data",
		},
		service = MVCResourceCommand.class
		)
public class SaveMonthlyAUMAndReturnDataPortlet implements MVCResourceCommand{
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
		
		JSONArray filelist =JSONFactoryUtil.createJSONArray();
		Long fileEntryId = 0l;
		
		fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
		
		filelist.put(fileEntryId);
		
		String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
		updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks,filelist.toString());
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		return resultJsonObject;
	}
	
	
	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime() + uploadPortletRequest.getFileName("unaditedfinancial");

		File file = uploadPortletRequest.getFile("unaditedfinancial");

		String mimeType = uploadPortletRequest.getContentType("unaditedfinancial");

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Half Yearly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		ReportUploadLogPFMAMPFRDALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks, fileList);
		
	}
	
	Log _log = LogFactoryUtil.getLog(getClass());

}
