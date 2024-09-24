package com.npa.development.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.util.Date;

import javax.portlet.ResourceRequest;
public class NPADevlopmentUtil {
	
public static long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest, String pdfORNot,File file) {
		
		Date date = new Date();
		//UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		//String fileName = date.getTime()+"_"+uploadPortletRequest.getFileName(param);
		String fileName = date.getTime()+"_"+file.getName();
		
		String title = fileName;
		
		String description = "";
		
		if(pdfORNot.equalsIgnoreCase("pdf")) {
			fileName = StringPool.BLANK; title = StringPool.BLANK; description = StringPool.BLANK;
			
			fileName = date.getTime()+"_"+"DevelopmentInDefaultSecurities"+".pdf";
			
			title = fileName;
			
			description = "Development In Default Securities PDF";
		}

		//File file = uploadPortletRequest.getFile(param);

		String mimeType =  MimeTypesUtil.getContentType(file);

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();

		} catch (Exception e) {
			 _log.error(e);
			
		}
		return 0;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

	public static Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;
		
	

	}
	
	
	public static String getDocumentURL(long documentId) {
		 String documentURL = StringPool.BLANK;
		    if(Validator.isNotNull(documentId)) {
		    	long fileEntryId = documentId;
		    	DLFileEntry dlFileEntry = null;
		    	try {
		    		dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId);
				} catch (Exception e) {
					 _log.error(e);
				}
		    	if(Validator.isNotNull(dlFileEntry)) {
		    		documentURL = "/documents/"+dlFileEntry.getRepositoryId()+"/"+dlFileEntry.getFolderId()+"/"+dlFileEntry.getTitle();
		    	}
		    }
		    return documentURL;
		}
	static	Log _log =LogFactoryUtil.getLog(NPADevlopmentUtil.class);

}
