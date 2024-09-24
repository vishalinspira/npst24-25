package AnnexureForCustodian.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;

import java.io.File;
import java.util.Date;

import javax.portlet.ResourceRequest;

public class DocumentUploadUtil {
	
	private static final Log _log = LogFactoryUtil.getLog(DocumentUploadUtil.class);
	
	public static long getFileentryId(ResourceRequest resourceRequest, long GroupId, File file, String file_name, String mimeType, String description) {
		
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
	
	public static Folder getFolder(long groupId) {

		Folder folder = null;
		
		try {
			folder = DLAppServiceUtil.getFolder(groupId, PARENT_FOLDER_ID, "Quarterly");

		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		
		return folder;

	}

}
