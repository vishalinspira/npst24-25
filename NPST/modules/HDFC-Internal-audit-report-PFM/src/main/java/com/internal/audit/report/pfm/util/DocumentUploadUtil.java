package com.internal.audit.report.pfm.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import java.util.Iterator;

import javax.portlet.ResourceRequest;

public class DocumentUploadUtil {
	
	private static final Log _log = LogFactoryUtil.getLog(DocumentUploadUtil.class);
	
	public static long getFileentryId(ResourceRequest resourceRequest, String name, String pdfORNot) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long fileentryId = 0l;
		
		Date date = new Date();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		File file = uploadPortletRequest.getFile(name);
		
		String filename = date.getTime() + "_" + uploadPortletRequest.getFileName(name);
		
		String title = filename;
        
        String description = "Custodian Internal Audit Report";
		
		if(pdfORNot.equalsIgnoreCase("pdf")) {
			filename = StringPool.BLANK; title = StringPool.BLANK; description = StringPool.BLANK;
			
			filename = date.getTime()+"_"+"PFM_Internal_Audit_Report"+".pdf";
			
			title = filename;
			
			description = "PFM Internal Audit Report PDF";
		}
		
		String mimeType = MimeTypesUtil.getContentType(file);
		
		try {
			FileEntry fileEntry = null;
				
			try {

				Folder folder = getFolder(themeDisplay);
				
				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), resourceRequest);
				
				if(null != folder) {
					
					fileEntry = DLAppServiceUtil.addFileEntry(themeDisplay.getScopeGroupId(), folder.getFolderId(), filename, 
							mimeType, title, description, "", file, serviceContext);
					
					fileentryId = fileEntry.getFileEntryId();
				}

			} catch (Exception e) {
				_log.error(e.getMessage(), e);
			}	
				
			
		} catch (Exception e) {
			_log.error("Error in storing " + filename + " ::::: " + e.getMessage());
		}
		
		return fileentryId;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	
	public static Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	
	public static String getDocumentURL(long documentId) {
		 String documentURL = StringPool.BLANK;
		    if(Validator.isNotNull(documentId) && documentId != 0) {
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
	
	public static JSONObject merge(JSONObject jsonObject1, JSONObject jsonObject2)throws JSONException {
		
		  if (jsonObject1 == null) {
		    return JSONFactoryUtil.createJSONObject(jsonObject2.toString());
		  }
		  
		  if (jsonObject2 == null) {
		    return JSONFactoryUtil.createJSONObject(jsonObject1.toString());
		  }
		  
		  JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonObject1.toString());
		  
		  Iterator<String> iterator = jsonObject2.keys();
		  
		  while (iterator.hasNext()) {
			  
		    String key = iterator.next();
		    jsonObject.put(key, jsonObject2.get(key));
		  }
		  
		  return jsonObject;
		}


}
