package Annexure16InternalAuditReport.resource;

import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Annexure16InternalAuditReport.constants.Annexure16InternalAuditReportPortletKeys;

@Component(property = { 
		"javax.portlet.name=" + Annexure16InternalAuditReportPortletKeys.ANNEXURE16INTERNALAUDITREPORT,
		"mvc.command.name=" + Annexure16InternalAuditReportPortletKeys.ANNEXURE16INTERNAL
		}, 
service = MVCResourceCommand.class)

public class Annexure16InternalAuditReportMVCResource implements MVCResourceCommand{
	private final Log _log = LogFactoryUtil.getLog(Annexure16InternalAuditReportMVCResource.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.debug("Calling resouce to save supervisor uploaded reports....");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			_log.error("Exception on writting the success message :: "+e.getMessage(), e);
		}
		return false;
	}

	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		_log.debug("Upload the file ......");
		
		long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = portal.getUploadPortletRequest(resourceRequest);
		
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error("Exception on fetching servicecontext object :: "+e.getMessage(), e);
		}
        
		long reportUploadLogId = ParamUtil.getLong(uploadPortletRequest, "reportUploadLogId");
		_log.info("reportUploadLogId : "+reportUploadLogId);
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		Long fileEntryId = 0l;
		fileEntryId = uploadFileToFolder(themeDisplay, uploadPortletRequest, resourceRequest);
		try {
			String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
			updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
		} catch (Exception e) {
			_log.error("Exception on uploading the report log : "+e.getMessage(),e);
		}
		resultJsonObject.put("status", true);
		
		return resultJsonObject;
	}
	
	public long uploadFileToFolder(ThemeDisplay themeDisplay, UploadPortletRequest uploadPortletRequest, ResourceRequest resourceRequest) {
		
		Date date = new Date();

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("annexurexVIFile");

		File file = uploadPortletRequest.getFile("annexurexVIFile");

		String mimeType = uploadPortletRequest.getContentType("annexurexVIFile");

		String title = fileName;

		String description = "annexurexVIFile";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = dlAppService.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();

		} catch (Exception e) {
			_log.error("Exception on uploading file : "+e.getMessage(), e);
		}
		return 0;
	}
	
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = dlAppService.getFolder(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Periodically");

		} catch (Exception e) {
			_log.error("Exception on getting folder : "+e.getMessage(), e);
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		reportUploadLogSupervisorLocalService.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}

	@Reference
	private DLAppService dlAppService;
	
	@Reference
	private ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;
	
	@Reference
	private Portal portal;
}
