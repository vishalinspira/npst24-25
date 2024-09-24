package com.detailedauditreportform.resource;

import com.daily.average.service.model.DAR;
import com.daily.average.service.service.DARLocalService;
import com.detailedauditreportform.constants.DetailedAuditReportFormPortletKeys;
import com.detailedauditreportform.util.DARUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { 
		"javax.portlet.name=" + DetailedAuditReportFormPortletKeys.DETAILEDAUDITREPORTFORM,
		"mvc.command.name=" + DetailedAuditReportFormPortletKeys.darForm, 
		}, 
service = MVCResourceCommand.class)

public class DetailedAuditReport extends BaseMVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(DetailedAuditReport.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("Serve Resource method");
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean isError = false;
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date_1 = ParamUtil.getDate(resourceRequest, "date_1", dateFormat);
		Date date_2 = ParamUtil.getDate(resourceRequest, "date_2", dateFormat);
		
		_log.info("date_1: " + date_1 + " date_2: "  + date_2);
		
		long darFile_1 = DARUtil.addDocuments(resourceRequest, "darFile_1", StringPool.BLANK);
		long darFile_2 = DARUtil.addDocuments(resourceRequest, "darFile_2", StringPool.BLANK);
		long darFile_3 = DARUtil.addDocuments(resourceRequest, "darFile_3", StringPool.BLANK);
		long darFile_4 = DARUtil.addDocuments(resourceRequest, "darFile_4", StringPool.BLANK);
		long darFile_5 = DARUtil.addDocuments(resourceRequest, "darFile_5", StringPool.BLANK);
		
		_log.info("darFile_1: " + darFile_1 + " darFile_2: "  + darFile_2);
		
		long pdf_file_fileEntryId = 0l;
		try {
			pdf_file_fileEntryId = DARUtil.addDocuments(resourceRequest, "Detailed_Audit_Report_pdf_file", "pdf");
			_log.info("dar_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(), e);
		}
		
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
			
			boolean reupload = false;
			if(Validator.isNotNull(dar)) {
				_log.info(dar);
				reupload = true;
			}
			
			darLocalService.addIAR(date_1, date_2, darFile_1, darFile_2, 
					jsonObject_two.toString(), new Date(), themeDisplay.getUserId(), 
					reportUploadLogId, darFile_3, darFile_4, darFile_5);
			
			
			if(dar != null && reupload) {
				_log.info("in update status ::::::::::::::: "+dar.toString());
				darLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
					themeDisplay.getUserId(), statusByUserName, 
					new Date(), serviceContext, "", false);
			}else {
				_log.info("else dar");
				darLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						themeDisplay.getUserId(), statusByUserName, 
						new Date(), serviceContext, "", true);
			}
			
		} catch (Exception e) {
			isError = true;
			_log.error("Exception in Persisting the table data  : "+e.getMessage(),e);
		}
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("Success");
			}
			
		} catch (IOException e) {
			_log.error(e);
		}
		
	}
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String auditorComments, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String auditor = ParamUtil.getString(resourceRequest, "obligations_auditor_rem_"+String.valueOf(i));
			
			jsonObject.put(auditorComments+i, auditor);
		}
		
		return jsonObject;
		
	}	
	
	@Reference
	DARLocalService darLocalService;
		
}
