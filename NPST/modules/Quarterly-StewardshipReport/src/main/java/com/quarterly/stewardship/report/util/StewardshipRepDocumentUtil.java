package com.quarterly.stewardship.report.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
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
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.util.Date;

import javax.portlet.ResourceRequest;

public class StewardshipRepDocumentUtil {
	
	private static Log _log = LogFactoryUtil.getLog(StewardshipRepDocumentUtil.class);
	
	@SuppressWarnings("deprecation")
	public static long addDocuments(ResourceRequest resourceRequest, String vwFieldName, String pdfORNot) {
		
		Date date =new Date();
		
		long fileEntryId = 0;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		ServiceContext serviceContext = null;
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		File file = uploadPortletRequest.getFile(vwFieldName);
        String filename =  date.getTime() + "_" + uploadPortletRequest.getFileName(vwFieldName);
        
        String title = filename;
        
        String description = "Quarterly compliance certificate";
        
        if(pdfORNot.equalsIgnoreCase("pdf")) {
        	filename = StringPool.BLANK; title = StringPool.BLANK; description = StringPool.BLANK;
			
        	filename = date.getTime()+"_"+"Quarterly compliance certificate"+".pdf";
			
			title = filename;
			
			description = "Quarterly Compliance Certificate PDF";
		}
        String mimeType = MimeTypesUtil.getContentType(file);
        long repositoryId = themeDisplay.getScopeGroupId();
        
		try {
			FileEntry fileEntry = null;
		/*	Folder folder = getFolder(themeDisplay);
			serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), resourceRequest);
				
			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, filename);
				if(Validator.isNotNull(fileEntry)) {
					fileEntry = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, filename, 
								mimeType, filename, "", "", file, serviceContext);
				}
			} catch (Exception e) {
				fileEntry = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, filename, 
							mimeType, filename, "", "", file, serviceContext);
			}		
				
			fileEntryId = fileEntry.getFileEntryId();
		*/
			try {

				Folder folder = getFolder(themeDisplay);
				
				 serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), resourceRequest);
				
				if(null != folder) {

					fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), filename, mimeType, title, description, "", file, serviceContext);
					
					fileEntryId = fileEntry.getFileEntryId();
				}
		} catch (Exception e) {
			_log.error("Error in storing " + filename + " ::::: " + e.getMessage());
		}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}	
			
        return fileEntryId;
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
					_log.error("Exception in getting dlFileEntry::" + e.getMessage());
				}
		    	if(Validator.isNotNull(dlFileEntry)) {
		    		documentURL = "/documents/"+dlFileEntry.getRepositoryId()+"/"+dlFileEntry.getFolderId()+"/"+dlFileEntry.getTitle();
		    	}
		    }
		    return documentURL;
		}

}
