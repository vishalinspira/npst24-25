package com.custodian.iar.resource;

import com.custodian.iar.constants.CustodianInternalAuditReportPortletKeys;
import com.custodian.iar.util.CustodianIARUtil;
import com.daily.average.service.model.CustIAR;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.CustIARLocalService;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
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
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { 
		"javax.portlet.name=" + CustodianInternalAuditReportPortletKeys.CUSTODIANINTERNALAUDITREPORT,
		"mvc.command.name=" + CustodianInternalAuditReportPortletKeys.custodianIAR, 
		}, 
service = MVCResourceCommand.class)

public class CustodianInternalAuditReport extends BaseMVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(CustodianInternalAuditReport.class);
	
	@Override
	public void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
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
		
		long iarFile = CustodianIARUtil.addDocuments(resourceRequest, "iarFile", StringPool.BLANK);
		
		long pdf_file_fileEntryId = 0l;
		try {
			_log.info("pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
			pdf_file_fileEntryId = CustodianIARUtil.addDocuments(resourceRequest, "Custodian_Internal_Audit_Report_pdf_file", "pdf");
			_log.info("Custodian_Internal_Audit_Report_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(), e);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date_1 = ParamUtil.getDate(resourceRequest, "date_1", dateFormat);
		Date date_2 = ParamUtil.getDate(resourceRequest, "date_2", dateFormat);
		
		String name = ParamUtil.getString(resourceRequest, "employeeName");
		String designation = ParamUtil.getString(resourceRequest, "designation");
		String date_3 = ParamUtil.getString(resourceRequest, "date_3");
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info(iarFile);
		_log.info(date_1 + " " + date_2);
		_log.info(name + " " + designation + " " + date_3);
		
		JSONObject jsonObject = CustodianIARUtil.getloopingContexOfCustodianInternalAuditReport();
		
		JSONObject jsonObject_two = JSONFactoryUtil.createJSONObject();
		
		for(String key : jsonObject.keySet()) {
			
			JSONObject jsonObject_three = JSONFactoryUtil.createJSONObject();
			long size = 0;
			
			//1to5
			if(key.equalsIgnoreCase("Board Meeting")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "boardMeeting_Observe_", "boardMeeting_Res_", size);
			}
			if(key.equalsIgnoreCase("Operational Manual/Procedure")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "operational_Observe_", "operational_Res_", size);
			}
			if(key.equalsIgnoreCase("Risk Management Policy")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "risk_Observe_", "risk_Res_", size);
			}
			if(key.equalsIgnoreCase("Risk Management Committee")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "riskCommitee_Observe_", "riskCommitee_Res_", size);
			}
			if(key.equalsIgnoreCase("Adequacy of Infrastructure")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "infra_Observe_", "infra_Res_", size);
			}
			
			//6to10
			if(key.equalsIgnoreCase("Settlement Processing")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "processing_Observe_", "processing_Res_", size);
			}
			if(key.equalsIgnoreCase("Scheme wise reconciliation of Holdings for each Pension Funds")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "holdings_Observe_", "holdings_Res_", size);
			}
			if(key.equalsIgnoreCase("Dealing Procedure (Front office) for deals executed by Pension funds")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "deals_Observe_", "deals_Res_", size);
			}
			if(key.equalsIgnoreCase("Back office Procedure")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "office_Observe_", "office_Res_", size);
			}
			if(key.equalsIgnoreCase("Safe Keeping")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "safe_Observe_", "safe_Res_", size);
			}
			
			//11to15
			if(key.equalsIgnoreCase("Asset Servicing")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "asset_Observe_", "asset_Res_", size);
			}
			if(key.equalsIgnoreCase("Reporting to Pension Funds/NPS Trust")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "reportingToPF_Observe_", "reportingToPF_Res_", size);
			}
			if(key.equalsIgnoreCase("Reporting Pension Funds / NPS Trust")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "reportingToNps_Observe_", "reportingToNps_Res_", size);
			}
			if(key.equalsIgnoreCase("Accounting")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "accounting_Observe_", "accounting_Res_", size);
			}
			if(key.equalsIgnoreCase("Valuation of Asset Under Custody (AUC)")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "auc_Observe_", "auc_Res_", size);
			}
			
			//16to19
			if(key.equalsIgnoreCase("Disclosure")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "disclosure_Observe_", "disclosure_Res_", size);
			}
			if(key.equalsIgnoreCase("Periodical returns to Authority/ NPS Trust")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "periodical_Observe_", "periodical_Res_", size);
			}
			if(key.equalsIgnoreCase("Compliances")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "compliances_Observe_", "compliances_Res_", size);
			}
			if(key.equalsIgnoreCase("Internal Audit/ Custodian/ Scheme Audit")) {
				size = jsonObject.getLong(key);	
				jsonObject_three = getJsonData(resourceRequest, "internalAudit_Observe_", "internalAudit_Res_", size);
			}
			
			//In end
			if(Validator.isNotNull(jsonObject_three)) {
				//data save
//				_log.info("* name : " +key + " " + " value : "+jsonObject_three.toString());
				jsonObject_two.put(key, jsonObject_three);
			}
			
		}
		//for loop ends here
		
		JSONArray jsonArray= JSONFactoryUtil.createJSONArray();
		
		try {
			DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(pdf_file_fileEntryId);
			JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
			jsonObj.put("Name", entry.getName());
			jsonObj.put("fileEntryId", entry.getFileEntryId());
			jsonArray.put(jsonObj);
		}catch (Exception e) {}
		
		try {
			DLFileEntry entry= DLFileEntryLocalServiceUtil.getDLFileEntry(iarFile);
			JSONObject jsonObj = JSONFactoryUtil.createJSONObject();
			jsonObj.put("Name", entry.getName());
			jsonObj.put("fileEntryId", entry.getFileEntryId());
			jsonArray.put(jsonObj);
		}catch (Exception e) {}
		
		
		try {
			CustIAR custIAR = custIARLocalService.fetchCustIAR(reportUploadLogId);
			
			boolean reupload = false;
			if(Validator.isNotNull(custIAR)) {
				_log.info(custIAR);
				reupload = true;
			}
			
			custIARLocalService.addIAR(iarFile, date_1, date_2, 
					jsonObject_two.toString(), name, designation, 
					date_3, new Date(), themeDisplay.getUserId(), reportUploadLogId);
			
			
			if(custIAR != null && reupload) {
				_log.info("in update status ::::::::::::::: "+custIAR.toString());
				custIARLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
					themeDisplay.getUserId(), statusByUserName, 
					new Date(), serviceContext, "", false);
			}else {
				_log.info("else custIAR");
				custIARLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						themeDisplay.getUserId(), statusByUserName, 
						new Date(), serviceContext, "", true);
			}
			
			try {
				List<ReportUploadFileLog> fileLog= ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
				ReportUploadFileLog reportFileLog=fileLog.get(fileLog.size()-1);
				reportFileLog.setFileList(jsonArray.toString());
				ReportUploadFileLogLocalServiceUtil.updateReportUploadFileLog(reportFileLog);
			}catch (Exception e) {
				_log.error("error while updating the file list");
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
	
	private JSONObject getJsonData(ResourceRequest resourceRequest,String observations, String managementResponse, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		for(int i=1; i<=size; i++) {
			String observe = ParamUtil.getString(resourceRequest, observations+String.valueOf(i));
			String response = ParamUtil.getString(resourceRequest, managementResponse+String.valueOf(i));
			
			jsonObject.put(observations+i, observe);
			jsonObject.put(managementResponse+i, response);
		}
		
		return jsonObject;
		
	}
	
	@Reference
	CustIARLocalService custIARLocalService;
	

}
