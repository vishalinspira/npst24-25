package com.nps.manPower.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
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

public class DocumentUtil {
	
private static Log _log = LogFactoryUtil.getLog(DocumentUtil.class);

	public static long addDocuments(ResourceRequest resourceRequest, String formName) {
		
		Date date = new Date();
		
		long fileEntryId = 0;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		File file = uploadPortletRequest.getFile(formName);
		
        String filename =  date.getTime() + "_" + uploadPortletRequest.getFileName(formName);
        
        String title = filename;
        
        String description = "Any Change in the interests of Directors of the Pension Fund  Form 3";
     
        String mimeType = MimeTypesUtil.getContentType(file);
        
        long repositoryId = themeDisplay.getScopeGroupId();
        
		try {
			FileEntry fileEntry = null;
				
			try {

				Folder folder = getFolder(themeDisplay);
				
				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), resourceRequest);
				
				if(null != folder) {

					fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), filename, mimeType, title, description, "", file, serviceContext);
					
					fileEntryId = fileEntry.getFileEntryId();
				}

			} catch (Exception e) {
				_log.error(e.getMessage(), e);
			}	
				
			
		} catch (Exception e) {
			_log.error("Error in storing " + filename + " ::::: " + e.getMessage());
		}
        
        return fileEntryId;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	
	public static Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	@SuppressWarnings("deprecation")
	public static long addDocuments(ResourceRequest resourceRequest, String vwFieldName, String pdfORNot) {
		
		Date date =new Date();
		
		long fileEntryId = 0;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		ServiceContext serviceContext = null;
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		File file = uploadPortletRequest.getFile(vwFieldName);
		
        String filename = date.getTime()+ "_" +uploadPortletRequest.getFileName(vwFieldName);
        
        String title = filename;
        
        String description = "Manpower Form 2";
        
        if(pdfORNot.equalsIgnoreCase("pdf")) {
        	filename = StringPool.BLANK; title = StringPool.BLANK; description = StringPool.BLANK;
			
        	filename = date.getTime()+"_"+"Manpower_Form_2_File"+".pdf";
        	_log.info("filename ::::::::::::::::::::::: "+filename);
        	
			title = filename;
			_log.info("title ::::::::::::::::::::::: "+title);
			
			description = "Manpower Form 2 PDF";
		}
        
        String mimeType = MimeTypesUtil.getContentType(file);
        
		try {
			FileEntry fileEntry = null;
			serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), resourceRequest);
			
			Folder folder = getFolder(themeDisplay);
			long folder_id = Validator.isNotNull(folder) ? folder.getFolderId() : DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			_log.info("folder_id " + folder_id);
				
			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, filename);
				if(Validator.isNotNull(fileEntry)) {
					fileEntry = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), folder_id, filename, 
								mimeType, title, description, "", file, serviceContext);
				}
			} catch (Exception e) {
				fileEntry = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), folder_id, filename, 
							mimeType, title, description, "", file, serviceContext);
			}		
				
			fileEntryId = fileEntry.getFileEntryId();
			
		} catch (Exception e) {
			_log.error("Error in storing " + filename + " ::::: " + e.getMessage());
		}
        
        return fileEntryId;
	}


}
