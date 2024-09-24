package com.detailedauditreportform.scrutiny;

import com.daily.average.service.model.DAR;
import com.daily.average.service.service.DARLocalService;
import com.daily.average.service.service.DARScrutinyLocalService;
import com.detailedauditreportform.constants.DetailedAuditReportFormPortletKeys;
import com.detailedauditreportform.util.DARNonNPSUserUtil;
import com.detailedauditreportform.util.DARUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.runtime.exception.IssuerException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { 
		"javax.portlet.name=" + DetailedAuditReportFormPortletKeys.DETAILEDAUDITREPORTFORM,
		"mvc.command.name=" + DetailedAuditReportFormPortletKeys.darFormScrutiny, 
		}, 
service = MVCResourceCommand.class)

public class DetailedAuditReportScrutiny extends BaseMVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(DetailedAuditReportScrutiny.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("Serve Resource method 2");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean isError = false;
		Boolean isNonNPSUser = ParamUtil.getBoolean(resourceRequest, "isNonNPSUser");
		_log.info("isNonNPSUser========"+isNonNPSUser);
		DARNonNPSUserUtil darNonNPSUserUtil= new DARNonNPSUserUtil();
		isNonNPSUser = darNonNPSUserUtil.isNonNpsUser(themeDisplay.getUserId());
		if(isNonNPSUser) {
			updateDAR(resourceRequest);
		}else {
			
			String dar_pdf_rem_1 = ParamUtil.getString(resourceRequest, "dar_pdf_rem_1");
			String dar_pdf_rem_2 = ParamUtil.getString(resourceRequest, "dar_pdf_rem_2");
			String dar_pdf_rem_3 = ParamUtil.getString(resourceRequest, "dar_pdf_rem_3");
			String dar_pdf_rem_4 = ParamUtil.getString(resourceRequest, "dar_pdf_rem_4");
			String dar_pdf_rem_5 = ParamUtil.getString(resourceRequest, "dar_pdf_rem_5");
			
			long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
			
			_log.info("dar_pdf_rem_1: " + dar_pdf_rem_1 + " dar_pdf_rem_2: " + dar_pdf_rem_2);
			
			JSONObject jsonObject = DARUtil.getloopingContexOfDetailedAuditReport();
			
			JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
			
			for(String key : jsonObject.keySet()) {
				
				JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
				long size = 0;
				
				//1to5
				if(key.equalsIgnoreCase("Obligations and Authorisations")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "obligations_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Code of Conduct")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "conduct_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Management Fees")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "fees_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Audit")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "audit_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Cyber Security, Information Technology & Infrastructure")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "security_nps_rem_", size);
				}
				
				//6to10
				if(key.equalsIgnoreCase("Foreign Holding")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "foreign_holding_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Legal Proceedings")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "legal_proceedings_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Informed")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "informed_nps_rem_", size);
				}
				if(key.equalsIgnoreCase("Other Matters")) {
					size = jsonObject.getLong(key);	
					jsonObject_three = getJsonData(resourceRequest, "other_matters_nps_rem_", size);
				}
				
				//In end
				if(Validator.isNotNull(jsonObject_three)) {
					//data save
					_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
					jsonObject_two.put(key, jsonObject_three);
				}
				
			}
			
			try {
				darScrutinyLocalService.addDARScrutiny(themeDisplay.getUser().getScreenName(), 0, 
						themeDisplay.getUserId(), dar_pdf_rem_1, dar_pdf_rem_2,  jsonObject_two.toString(),
						new Date(), themeDisplay.getUserId(), reportUploadLogId,
						dar_pdf_rem_3, dar_pdf_rem_4, dar_pdf_rem_5);
			} catch (Exception e) {
				isError = true;
				_log.error("Exception in Persisting the table data one  : "+e.getMessage());
			}
		}
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("Success");
			}else {
				writer.print("error");
			}
			
		} catch (IOException e) {
			_log.error(e);
		}
		
		
		
	}
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String auditorComments, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String auditor = ParamUtil.getString(resourceRequest, auditorComments+String.valueOf(i));
			
			jsonObject.put(auditorComments+i, auditor);
		}
		
		return jsonObject;
		
	}
	
	/**
	 * Update DAR
	 * @param resourceRequest
	 */
	private void updateDAR(ResourceRequest resourceRequest) {
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		JSONObject jsonObject = DARUtil.getloopingContexOfDetailedAuditReport();
		
		JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
		
		for(String key : jsonObject.keySet()) {
			
			JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
			long size = 0;
			
			//1to5
			if(key.equalsIgnoreCase("Obligations and Authorisations")) {
				size = jsonObject.getLong(key);	
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "obligations_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "obligations_management_rem_", size));
						
			}
			if(key.equalsIgnoreCase("Code of Conduct")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "conduct_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "conduct_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "conduct_management_rem_", size));
			}
			if(key.equalsIgnoreCase("Management Fees")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "fees_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "fees_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "fees_management_rem_", size));
			}
			if(key.equalsIgnoreCase("Audit")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "audit_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "audit_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "audit_management_rem_", size));
			}
			if(key.equalsIgnoreCase("Cyber Security, Information Technology & Infrastructure")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "security_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "security_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "security_management_rem_", size));
			}
			
			//6to10
			if(key.equalsIgnoreCase("Foreign Holding")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "foreign_holding_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "foreign_holding_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "foreign_holding_management_rem_", size));
			}
			if(key.equalsIgnoreCase("Legal Proceedings")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "legal_proceedings_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "legal_proceedings_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "legal_proceedings_management_rem_", size));
			}
			if(key.equalsIgnoreCase("Informed")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "informed_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "informed_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "informed_management_rem_", size));
			}
			if(key.equalsIgnoreCase("Other Matters")) {
				size = jsonObject.getLong(key);	
				//jsonObject_three = getJsonData(resourceRequest, "other_matters_auditor_rem_", size);
				jsonObject_three.put("auditor", getJsonData(resourceRequest, "other_matters_auditor_rem_", size));
				jsonObject_three.put("management", getJsonData(resourceRequest, "other_matters_management_rem_", size));
			}
			
			//In end
			if(Validator.isNotNull(jsonObject_three)) {
				//data save
				_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
				jsonObject_two.put(key, jsonObject_three);
			}
			
		}
		
		try {
			DAR dar = darLocalService.fetchDAR(reportUploadLogId);
			
			dar.setDar_details(jsonObject_two.toString());
			
			darLocalService.updateDAR(dar);
		} catch (Exception e) {
			_log.error(e);
		}
		
	}
	
	@Reference
	DARScrutinyLocalService darScrutinyLocalService;
	
	@Reference
	DARLocalService darLocalService;

}
