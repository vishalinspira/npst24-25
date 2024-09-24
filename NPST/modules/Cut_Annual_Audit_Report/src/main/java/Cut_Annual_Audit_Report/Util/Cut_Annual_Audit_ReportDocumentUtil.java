package Cut_Annual_Audit_Report.Util;

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


public class Cut_Annual_Audit_ReportDocumentUtil {

private static Log _log = LogFactoryUtil.getLog(Cut_Annual_Audit_ReportDocumentUtil.class);
	
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
        
        String description = "Custodian Annual Audit Report";
        
        if(pdfORNot.equalsIgnoreCase("pdf")) {
        	filename = StringPool.BLANK; title = StringPool.BLANK; description = StringPool.BLANK;
			
        	filename = date.getTime()+"_"+"Custodian Annual Audit Report"+".pdf";
        	_log.info("filename ::::::::::::::::::::::: "+filename);
        	
			title = filename;
			_log.info("title ::::::::::::::::::::::: "+title);
			
			description = "Custodian Annual Audit Report PDF";
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
	
	public static final String emptyJsonScrutinyRemarksdata = "{\"Custodian\":{\"custodiane_rem_4\":\"\",\"custodiane_rem_3\":\"\",\"custodiane_rem_2\":\"\",\"custodiane_rem_1\":\"\",\"custodiane_rem_5\":\"\"},\"Record maintenance\":{\"recordh_rem_2\":\"\",\"recordh_rem_1\":\"\",\"recordh_rem_4\":\"\",\"recordh_rem_3\":\"\"},\"Custodian Billing\":{\"billingd_rem_2\":\"\",\"billingd_rem_1\":\"\"},\"Governance Structure\":{\"governancek_rem_3\":\"\",\"governancek_rem_4\":\"\",\"governancek_rem_1\":\"\",\"governancek_rem_2\":\"\"},\"Regulatory parameters\":{\"regulatory_rem_7\":\"\",\"regulatory_rem_6\":\"\",\"regulatory_rem_5\":\"\",\"regulatory_rem_4\":\"\",\"regulatory_rem_9\":\"\",\"regulatory_rem_8\":\"\",\"regulatory_rem_3\":\"\",\"regulatory_rem_2\":\"\",\"regulatory_rem_1\":\"\"},\"Infrastructure\":{\"infrastructuref_rem_9\":\"\",\"infrastructuref_rem_11\":\"\",\"infrastructuref_rem_10\":\"\",\"infrastructuref_rem_12\":\"\",\"infrastructuref_rem_1\":\"\",\"infrastructuref_rem_2\":\"\",\"infrastructuref_rem_3\":\"\",\"infrastructuref_rem_4\":\"\",\"infrastructuref_rem_5\":\"\",\"infrastructuref_rem_6\":\"\",\"infrastructuref_rem_7\":\"\",\"infrastructuref_rem_8\":\"\"},\"Operational parameters\":{\"operational_rem_5\":\"\",\"operational_rem_17\":\"\",\"operational_rem_4\":\"\",\"operational_rem_18\":\"\",\"operational_rem_3\":\"\",\"operational_rem_19\":\"\",\"operational_rem_2\":\"\",\"operational_rem_9\":\"\",\"operational_rem_8\":\"\",\"operational_rem_7\":\"\",\"operational_rem_6\":\"\",\"operational_rem_20\":\"\",\"operational_rem_10\":\"\",\"operational_rem_21\":\"\",\"operational_rem_11\":\"\",\"operational_rem_22\":\"\",\"operational_rem_12\":\"\",\"operational_rem_13\":\"\",\"operational_rem_14\":\"\",\"operational_rem_15\":\"\",\"operational_rem_16\":\"\",\"operational_rem_1\":\"\"},\"Grievance Redressal\":{\"grievancei_rem_2\":\"\",\"grievancei_rem_1\":\"\"},\"Protection\":{\"protectiong_rem_4\":\"\",\"protectiong_rem_5\":\"\",\"protectiong_rem_2\":\"\",\"protectiong_rem_3\":\"\",\"protectiong_rem_1\":\"\",\"protectiong_rem_6\":\"\"},\"Other parameters\":{\"otherj_rem_2\":\"\",\"otherj_rem_1\":\"\"},\"Timely Reporting\":{\"timely_rem_13\":\"\",\"timely_rem_1\":\"\",\"timely_rem_10\":\"\",\"timely_rem_12\":\"\",\"timely_rem_11\":\"\",\"timely_rem_6\":\"\",\"timely_rem_7\":\"\",\"timely_rem_8\":\"\",\"timely_rem_9\":\"\",\"timely_rem_2\":\"\",\"timely_rem_3\":\"\",\"timely_rem_4\":\"\",\"timely_rem_5\":\"\"}}";

}
