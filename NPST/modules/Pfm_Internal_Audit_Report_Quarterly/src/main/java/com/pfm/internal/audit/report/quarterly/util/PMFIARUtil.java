package com.pfm.internal.audit.report.quarterly.util;

import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalServiceUtil;
import com.daily.pfm.service.model.Pfm_Qr_IARScrutiny;
import com.daily.pfm.service.service.Pfm_Qr_IARScrutinyLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
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
import java.util.List;

import javax.portlet.ResourceRequest;

public class PMFIARUtil {
	private static Log _log = LogFactoryUtil.getLog(PMFIARUtil.class);
	public static JSONObject getloopingContexOfDetailedAuditReport() {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("1", 5);
		jsonObject.put("2", 7);
		jsonObject.put("3", 5);
		jsonObject.put("4", 8);
		jsonObject.put("5", 4);
		
		jsonObject.put("6", 13);
		jsonObject.put("7", 9);
		jsonObject.put("8", 4);
		jsonObject.put("9", 4);
		
		jsonObject.put("10", 6);
		jsonObject.put("11", 2);
		jsonObject.put("12", 2);
		jsonObject.put("13", 3);
		
		jsonObject.put("14", 3);
		jsonObject.put("15", 2);
		jsonObject.put("16", 3);
		jsonObject.put("17", 2);
		
		jsonObject.put("18", 11);
		jsonObject.put("19", 6);
		jsonObject.put("20", 5);
		jsonObject.put("21", 5);
		
		jsonObject.put("22", 4);
		jsonObject.put("23", 5);
		jsonObject.put("24", 1);
		jsonObject.put("25", 1);
		
		return jsonObject;
	}
	

	
	public static JSONObject getIAR_JSON_data(long reportUploadlogId) {
		
		Pfm_Qr_Internal_Audit_Report pfm_qr_iar= Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.fetchPfm_Qr_Internal_Audit_Report(reportUploadlogId);
	
		//Pfm_Qr_IARScrutiny qr_IARScrutiny=Pfm_Qr_IARScrutinyLocalServiceUtil.fetchPfm_Qr_IARScrutiny(reportUploadlogId);
		List<Pfm_Qr_IARScrutiny> qr_IARScrutinyIarScrutinies=Pfm_Qr_IARScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadlogId);
		
		Pfm_Qr_IARScrutiny qr_IARScrutiny=null;
		
		if(qr_IARScrutinyIarScrutinies.size()>0) {
			
		 qr_IARScrutiny=qr_IARScrutinyIarScrutinies.get(qr_IARScrutinyIarScrutinies.size()-1);
		
		}
		else {
			qr_IARScrutiny=Pfm_Qr_IARScrutinyLocalServiceUtil.createPfm_Qr_IARScrutiny(0);
			
		}
	
		JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
		
		JSONObject keyDetails=getloopingContexOfDetailedAuditReport();
		
		try{
			if(Validator.isNotNull(pfm_qr_iar)) {
		
			JSONObject iarDetails=JSONFactoryUtil.createJSONObject(pfm_qr_iar.getMaker_comment_details());

			JSONObject iarScrutinyDetails=null;
			if(Validator.isNotNull(iarScrutinyDetails)) {
				try {
					if(Validator.isNotNull(qr_IARScrutiny)) {
				iarScrutinyDetails=JSONFactoryUtil.createJSONObject(qr_IARScrutiny.getNps_comments());
					}
					}catch (Exception e) {
					_log.error("Error while parsing iarScrutinyDetails: "+e.getMessage());
				}
			}
			
			for(String key : keyDetails.keySet()) {
				try {
				long size = keyDetails.getLong(key);
				JSONObject observations=iarDetails.getJSONObject("observations_"+key+"_");
				
				JSONObject managemants=iarDetails.getJSONObject("management_"+key+"_");
				JSONObject npst_remarks=null;
				if(Validator.isNotNull(iarScrutinyDetails)) {
				try {
				// npst_remarks=iarScrutinyDetails.getJSONObject(key+"_nps_rem_");
				
					 npst_remarks=iarScrutinyDetails.getJSONObject(key);
				}catch (Exception e) {
					_log.error("Error while parsing iarScrutinyDetails: "+e.getMessage());
				}
				}	
				for(int i=1;i<=size;i++) {
					try {
					if(Validator.isNotNull(observations)) {
					jsonObject.put("observations_"+key+"_"+i, observations.get("observations_"+key+"_"+i));
					}if(Validator.isNotNull(managemants)) {
					jsonObject.put("management_"+key+"_"+i, managemants.get("management_"+key+"_"+i));
					}if(Validator.isNotNull(npst_remarks)) {
						
					jsonObject.put(key+"_nps_rem_"+i, npst_remarks.get(key+"_nps_rem_"+i));
					}
					else {
					      jsonObject.put(key + "_nps_rem_" + i, ""); 
					}
				}catch (Exception e) {
					_log.error(e.getMessage());
				}
				}
		
				}catch (Exception e) {
					_log.error(e.getMessage());
				}
			}
			}else {
				return null;
			}
			}catch (Exception e) {
			_log.error(e.getMessage());
		}
	
		return jsonObject;
		
		
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
	
	public static long addDocuments(ResourceRequest resourceRequest, String vwFieldName, String pdfORNot) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		Date date = new Date();
		
		long fileEntryId = 0;
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		File file = uploadPortletRequest.getFile(vwFieldName);
		if(Validator.isNotNull(file) && file.length() > 0) {
	        String filename =  date.getTime() + "_" + uploadPortletRequest.getFileName(vwFieldName);
	        
	        String title = filename;
	        
	        String description = "Detailed Audit Report";
	        
	        if(pdfORNot.equalsIgnoreCase("pdf")) {
				filename = StringPool.BLANK; title = StringPool.BLANK; description = StringPool.BLANK;
				
				filename = date.getTime()+"_"+"Detailed_Audit_Report"+".pdf";
				
				title = filename;
				
				description = "Detailed Audit Report PDF";
			}
	        
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
		}
        
        return fileEntryId;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	
	public static Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Annually");

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
}
