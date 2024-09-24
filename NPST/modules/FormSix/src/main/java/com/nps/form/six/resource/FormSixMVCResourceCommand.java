package com.nps.form.six.resource;

import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.nps.form.six.constants.FormSixPortletKeys;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;


@Component(
		immediate = true,
		property = { 
				"javax.portlet.name=" + FormSixPortletKeys.FORMSIX,
				"mvc.command.name=" + FormSixPortletKeys.FORMSIX_SAVE_FILE,
		}, 
		service = MVCResourceCommand.class
	)
public class FormSixMVCResourceCommand extends BaseMVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(FormSixMVCResourceCommand.class.getName());

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("FormSixMVCResourceCommand serve Resource method start ::: ");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		JSONObject resultJsonObject = uploadFileInDocumentAndMedia(themeDisplay, resourceRequest);
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			
			writer.print(resultJsonObject.toString());
		} catch (IOException e) {
			_log.error(e);
		}
		
		_log.info("FormSixMVCResourceCommand serve Resource method end ::: ");
		
	}
	
	private JSONObject uploadFileInDocumentAndMedia(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		boolean status = false;
		
		Long userId = themeDisplay.getUserId();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error(e.getMessage(), e);
		}
        
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		String fileName = uploadPortletRequest.getFileName("FormSixReportFile");
		_log.info("::: FileName : "+fileName);

		File file = uploadPortletRequest.getFile("FormSixReportFile");

		String mimeType = MimeTypesUtil.getContentType(file);

		String title = fileName;
	
		Long fileEntryId = 0l;
			
		fileEntryId = getFileentryId(resourceRequest, themeDisplay.getScopeGroupId(), file, fileName, mimeType, title, "");
			
		try {
			String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
			
			if(fileEntryId != 0) {
				updateReportLog ( userId, fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks );
				
				status = true;
			}
			
		} catch (Exception e) {
			_log.error("Exception while updating report log :: "+e.getMessage(), e);
			
			status = false;
		}
		
		resultJsonObject.put("status", status);
		
		return resultJsonObject;
	}
	
	
	private long getFileentryId(ResourceRequest resourceRequest, long GroupId, File file, String file_name, String mimeType, String title, String description) {
		
		long fileentryId = 0l;
		
		Date date = new Date();

		String fileName = date.getTime() + file_name;

		long repositoryId = GroupId;
	
		try {

			Folder folder = getFolder(GroupId);
			
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), resourceRequest);
			
			if(null != folder) {

				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, fileName, description, "", file, serviceContext);
				
				fileentryId = fileEntry.getFileEntryId();
			}

		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		
		return fileentryId;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	
	private Folder getFolder(long groupId) {

		Folder folder = null;
		
		try {
			folder = DLAppServiceUtil.getFolder(groupId, PARENT_FOLDER_ID, "Quarterly");

		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		
		return folder;

	}
	
	private void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks ) {
		
		Date createDate = new Date();
		
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);
	}
 
}
