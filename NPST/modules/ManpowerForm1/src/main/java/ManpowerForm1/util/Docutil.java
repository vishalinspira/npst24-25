package ManpowerForm1.util;


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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.ResourceRequest;

public class Docutil {
	private static Log _log = LogFactoryUtil.getLog(Docutil.class);

	public static long addDocument(ResourceRequest resourceRequest, String formMp) {
		
		Date date = new Date();
		
		long fileEntryId = 0;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		File file = uploadPortletRequest.getFile(formMp);
		
        String filename =  date.getTime() + "_" + uploadPortletRequest.getFileName(formMp);
        
        String title = filename;
        
        String description = "Intimation of Change in Key Personnel of the Pension Fund Section1";
        
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
	
	public static List<Long> addDocuments(ResourceRequest resourceRequest, String formMp) {
		
		Date date = new Date();
		
		List<Long> fileEntryIds = new ArrayList<Long>();
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		File files[] = uploadPortletRequest.getFiles(formMp);
		
        String filenames[] =  uploadPortletRequest.getFileNames(formMp);
        for (int i = 0; i < filenames.length; i++) {
        	String filename = date.getTime() + "_" + filenames[i];
        	String title = filename;
            
            String description = "Intimation of Change in Key Personnel of the Pension Fund Section1";
            
            String mimeType = MimeTypesUtil.getContentType(files[i]);
            
            long repositoryId = themeDisplay.getScopeGroupId();
            
    		try {
    			FileEntry fileEntry = null;
    				
    			try {

    				Folder folder = getFolder(themeDisplay);
    				
    				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), resourceRequest);
    				
    				if(null != folder) {

    					fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), filename, mimeType, title, description, "", files[i], serviceContext);
    					
    					fileEntryIds.add(fileEntry.getFileEntryId());
    				}

    			} catch (Exception e) {
    				_log.error(e.getMessage(), e);
    			}	
    				
    			
    		} catch (Exception e) {
    			_log.error("Error in storing " + filename + " ::::: " + e.getMessage());
    		}
		}
        
        
        return fileEntryIds;
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
        
        String description = "Manpower Form 1";
        
        if(pdfORNot.equalsIgnoreCase("pdf")) {
        	filename = StringPool.BLANK; title = StringPool.BLANK; description = StringPool.BLANK;
			
        	filename = date.getTime()+"_"+"Manpower_Form_1_File"+".pdf";
        	_log.info("filename ::::::::::::::::::::::: "+filename);
        	
			title = filename;
			_log.info("title ::::::::::::::::::::::: "+title);
			
			description = "Manpower Form 1 PDF";
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
