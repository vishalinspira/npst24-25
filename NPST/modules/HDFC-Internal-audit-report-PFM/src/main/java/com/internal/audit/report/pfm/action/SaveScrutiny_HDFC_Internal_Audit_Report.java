package com.internal.audit.report.pfm.action;

import com.daily.average.service.service.HDFCInternal_Audit_Report_ScrutinyLocalService;
import com.internal.audit.report.pfm.constants.HDFCInternalAuditReportPFMPortletKeys;
import com.internal.audit.report.pfm.util.HDFC_Internal_audit_report_PFM_Util;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + HDFCInternalAuditReportPFMPortletKeys.HDFCINTERNALAUDITREPORTPFM,
				"mvc.command.name=save/HDFC-Internal-audit-report-scrutiny/pfm"
		},
		service = MVCResourceCommand.class
)
public class SaveScrutiny_HDFC_Internal_Audit_Report extends BaseMVCResourceCommand{
	
	private static final Log _log = LogFactoryUtil.getLog(SaveScrutiny_HDFC_Internal_Audit_Report.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("SCRUTINY HDFC-Internal-audit-report PFM started ::::");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean isError = false;
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		JSONObject jsonObject = HDFC_Internal_audit_report_PFM_Util.getloopingContexOfInternalAuditReport();
		
		try {
			JSONObject formContext = JSONFactoryUtil.createJSONObject();
			
			for(String key : jsonObject.keySet()) {
				
				JSONObject jsonObject_ref = JSONFactoryUtil.createJSONObject();
				long size = 0;
				
				if(key.equalsIgnoreCase("Board Meeting")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "boardMeeting_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Investment Operational Manual/Procedure")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Operational_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Investment Committee")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Investment_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Investment Policy")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "InvestmentPolicy_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Risk Management Committee")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "RiskManagement_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Risk Management Policy")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "RiskManagementPolicy_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Pattern of Investment")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "PatternofInvestment_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Front office dealing procedure")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Frontoffice_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Back Office Procedure")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Backoffice_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Exposure & Prudential Norms")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Exposure_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Custodian Controls")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "CustodianControls_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Verification of Other investments")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Otherinvestments_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Units Accounting")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "UnitsAccounting_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Investment Bank accounts")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "BankReconciliation_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Appointment of Brokers")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Brokers_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Non-Performing Investments")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Non-Performing_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Inter Scheme Transfer")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "InterSchemeTransfer_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Investment Deals verification")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Dealsverification_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Accounting")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "AccountingStandards_auditor_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("NAV")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Nav_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Disclosure")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Disclosure_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Periodical returns to Authority/ Trust")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Periodical_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Compliances")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Compliances_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Internal Audit/ PFM Audit/ Scheme Audit")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "IPFMS_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Marketing & distribution")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "Marketing&Distribution_auditor_management_remark_", size);
				}
				
				if(key.equalsIgnoreCase("Other regulatory compliances")) {
					size = jsonObject.getLong(key);	
					jsonObject_ref = getHDFCJsonData(resourceRequest, "OtherRegulatory_auditor_management_remark_", size);
				}
				
				
				
				//After json data is collected
				if(Validator.isNotNull(jsonObject_ref)) {
					//Map to json object and store it as Object.toString in db
					formContext.put(key, jsonObject_ref);
				}
				
			} //loop ends here
			
			String certificate_pdf_file_remarks = ParamUtil.getString(resourceRequest, "certificate_pdf_remarks");
			String auditor_pdf_file_remarks = ParamUtil.getString(resourceRequest, "auditor_pdf_remarks");
			String AnnexureForIARCircular_pdf_file_remarks = ParamUtil.getString(resourceRequest, "AnnexureForIARCircular_remarks");
			
			hdfcinternal_Audit_Report_ScrutinyLocalService.saveHDFCInternal_Audit_Report_Scrutiny(statusByUserName, 0, themeDisplay.getUserId(), 
					formContext.toString(), certificate_pdf_file_remarks, auditor_pdf_file_remarks, AnnexureForIARCircular_pdf_file_remarks, new Date(), themeDisplay.getUserId(), reportUploadLogId);
			
		} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(), e);
		}
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("Success");
			}
			
		} catch (IOException e) {
			_log.error(e);
		}
		
		_log.info(" SCRUTINY HDFC-Internal-audit-report PFM ended ::::");
		
	}
	
	private JSONObject getHDFCJsonData(ResourceRequest resourceRequest, String management_remarks, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		
		for(int i=1; i<=size; i++) {
			
			String management_remark = ParamUtil.getString(resourceRequest, management_remarks+String.valueOf(i));

			jsonObject.put(management_remarks+i, management_remark);
		}
		
		return jsonObject;
		
	}
	
	@Reference
	HDFCInternal_Audit_Report_ScrutinyLocalService hdfcinternal_Audit_Report_ScrutinyLocalService;

}
