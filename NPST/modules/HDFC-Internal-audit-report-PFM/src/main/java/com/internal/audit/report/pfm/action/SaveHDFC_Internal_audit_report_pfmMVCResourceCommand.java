package com.internal.audit.report.pfm.action;

import com.daily.average.service.model.HDFCInternal_Audit_Report;
import com.daily.average.service.service.HDFCInternal_Audit_ReportLocalService;
import com.internal.audit.report.pfm.constants.HDFCInternalAuditReportPFMPortletKeys;
import com.internal.audit.report.pfm.util.DocumentUploadUtil;
import com.internal.audit.report.pfm.util.HDFC_Internal_audit_report_PFM_Util;
import com.liferay.petra.string.StringPool;
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

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + HDFCInternalAuditReportPFMPortletKeys.HDFCINTERNALAUDITREPORTPFM,
				"mvc.command.name=save/HDFC-Internal-audit-report/pfm"
		},
		service = MVCResourceCommand.class
)
public class SaveHDFC_Internal_audit_report_pfmMVCResourceCommand extends BaseMVCResourceCommand{
	
	private static final Log _log = LogFactoryUtil.getLog(SaveHDFC_Internal_audit_report_pfmMVCResourceCommand.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("HDFC-Internal-audit-report PFM started ::::");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean isError = false;
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = ParamUtil.getDate(resourceRequest, "form_date", dateFormat);
		String pension_fund_name = ParamUtil.getString(resourceRequest, "companies");
		String period_from = ParamUtil.getString(resourceRequest, "period_from");
		String period_to = ParamUtil.getString(resourceRequest, "period_to");
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		JSONObject jsonObject = HDFC_Internal_audit_report_PFM_Util.getloopingContexOfInternalAuditReport();
		
		JSONObject formContext = JSONFactoryUtil.createJSONObject();
		
		for(String key : jsonObject.keySet()) {
			
			JSONObject jsonObject_ref = JSONFactoryUtil.createJSONObject();
			long size = 0;
			
			if(key.equalsIgnoreCase("Board Meeting")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "boardMeeting_sample_", "boardMeeting_complied_", "boardMeeting_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Investment Operational Manual/Procedure")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Operational_sample_", "Operational_complied_", "Operational_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Investment Committee")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Investment_sample_", "Investment_complied_", "Investment_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Investment Policy")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "InvestmentPolicy_sample_", "InvestmentPolicy_complied_", "InvestmentPolicy_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Risk Management Committee")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "RiskManagement_sample_", "RiskManagement_complied_", "RiskManagement_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Risk Management Policy")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "RiskManagementPolicy_sample_", "RiskManagementPolicy_complied_", "RiskManagementPolicy_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Pattern of Investment")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "PatternofInvestment_sample_", "PatternofInvestment_complied_", "PatternofInvestment_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Front office dealing procedure")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Frontoffice_sample_", "Frontoffice_complied_", "Frontoffice_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Back Office Procedure")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Backoffice_sample_", "Backoffice_complied_", "Backoffice_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Exposure & Prudential Norms")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Exposure_sample_", "Exposure_complied_", "Exposure_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Custodian Controls")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "CustodianControls_sample_", "CustodianControls_complied_", "CustodianControls_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Verification of Other investments")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Otherinvestments_sample_", "Otherinvestments_complied_", "Otherinvestments_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Units Accounting")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "UnitsAccounting_sample_", "UnitsAccounting_complied_", "UnitsAccounting_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Investment Bank accounts")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "BankReconciliation_sample_", "BankReconciliation_complied_", "BankReconciliation_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Appointment of Brokers")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Brokers_sample_", "Brokers_complied_", "Brokers_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Non-Performing Investments")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Non-Performing_sample_", "Non-Performing_complied_", "Non-Performing_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Inter Scheme Transfer")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "InterSchemeTransfer_sample_", "InterSchemeTransfer_complied_", "InterSchemeTransfer_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Investment Deals verification")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Dealsverification_sample_", "Dealsverification_complied_", "Dealsverification_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Accounting")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "AccountingStandards_sample_", "AccountingStandards_complied_", "AccountingStandards_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("NAV")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Nav_sample_", "Nav_complied_", "Nav_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Disclosure")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Disclosure_sample_", "Disclosure_complied_", "Disclosure_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Periodical returns to Authority/ Trust")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Periodical_sample_", "Periodical_complied_", "Periodical_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Compliances")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Compliances_sample_", "Compliances_complied_", "Compliances_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Internal Audit/ PFM Audit/ Scheme Audit")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "IPFMS_sample_", "IPFMS_complied_", "IPFMS_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Marketing & distribution")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "Marketing&Distribution_sample_", "Marketing&Distribution_complied_", "Marketing&Distribution_auditor_comment_", size);
			}
			
			if(key.equalsIgnoreCase("Other regulatory compliances")) {
				size = jsonObject.getLong(key);	
				jsonObject_ref = getHDFCJsonData(resourceRequest, "OtherRegulatory_sample_", "OtherRegulatory_complied_", "OtherRegulatory_auditor_comment_", size);
			}
			
			
			
			//After json data is collected
			if(Validator.isNotNull(jsonObject_ref)) {
				//Map to json object and store it as Object.toString in db
				formContext.put(key, jsonObject_ref);
			}
			
		} //loop ends here
		
		
		//get file entry ids
		long certificate_pdf_file_id = DocumentUploadUtil.getFileentryId(resourceRequest, "certificate_pdf", StringPool.BLANK);
		_log.info("************************* certificate_pdf_file_id *********************** "+certificate_pdf_file_id);
		long auditor_pdf_file_id = DocumentUploadUtil.getFileentryId(resourceRequest, "auditor_pdf", StringPool.BLANK);
		_log.info("************************* auditor_pdf_file_id *********************** "+auditor_pdf_file_id);
		
		long intrnlAdtRprt_pdf_file_id = DocumentUploadUtil.getFileentryId(resourceRequest, "intrnlAdtRprt_pdf", StringPool.BLANK);
		_log.info("************************* intrnlAdtRprt_pdf_file_id *********************** "+intrnlAdtRprt_pdf_file_id);
		long extrctBrdMinAprvng_pdf_file_id = DocumentUploadUtil.getFileentryId(resourceRequest, "extrctBrdMinAprvng_pdf", StringPool.BLANK);
		_log.info("************************* extrctBrdMinAprvng_pdf_file_id *********************** "+extrctBrdMinAprvng_pdf_file_id);
		
		long annexureForIARCircular_pdf_file_id = DocumentUploadUtil.getFileentryId(resourceRequest, "AnnexureForIARCircular", StringPool.BLANK);
		_log.info("************************* annexureForIARCircular_pdf_file_id *********************** "+annexureForIARCircular_pdf_file_id);
		long pdf_file_fileEntryId = 0l;
		try {
			pdf_file_fileEntryId = DocumentUploadUtil.getFileentryId(resourceRequest, "qr_iar_pdf_file", "pdf");
			_log.info("iar_pdf_file -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(), e);
		}
		
		
	///////////////////
		//persist the data here
		//hdfcinternal_Audit_ReportLocalService.addHDFCInternal_Audit_Report(reportUploadLogId, date, period_from, period_to, pension_fund_name, certificate_pdf_file_id, auditor_pdf_file_id, AnnexureForIARCircular_pdf_file_id, formContext.toString());
	///////////////////	
		
		ServiceContext serviceContext = null;
		
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
			HDFCInternal_Audit_Report hdfcinternal_Audit_Report = hdfcinternal_Audit_ReportLocalService.fetchHDFCInternal_Audit_Report(reportUploadLogId);
			
			boolean reupload = false;
			if(Validator.isNotNull(hdfcinternal_Audit_Report)) {
				_log.info(hdfcinternal_Audit_Report);
				reupload = true;
			}
			
			hdfcinternal_Audit_ReportLocalService.addHDFCInternal_Audit_Report(reportUploadLogId, date, period_from, period_to, 
					certificate_pdf_file_id, auditor_pdf_file_id,intrnlAdtRprt_pdf_file_id, annexureForIARCircular_pdf_file_id, extrctBrdMinAprvng_pdf_file_id, formContext.toString(), pension_fund_name);
			
			if(hdfcinternal_Audit_Report != null && reupload) {
				_log.info("in update status ::::::::::::::: "+hdfcinternal_Audit_Report.toString());
				hdfcinternal_Audit_ReportLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						themeDisplay.getUserId(), statusByUserName, new Date(), serviceContext, "", false);
			}else {
				_log.info("else hdfcIAR");
				hdfcinternal_Audit_ReportLocalService.updateReportUploadLog(new Date(), themeDisplay.getUserId(), 
						pdf_file_fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, 
						themeDisplay.getUserId(), statusByUserName, new Date(), serviceContext, "", true);
			}
			
		
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
		
		_log.info("HDFC-Internal-audit-report PFM ended ::::");
		
	}
	
	private JSONObject getHDFCJsonData(ResourceRequest resourceRequest, String sample_comments, String complied_yesORno, String auditors_comments, long size) {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		
		for(int i=1; i<=size; i++) {
			
			String sample = ParamUtil.getString(resourceRequest, sample_comments+String.valueOf(i));
			String complied = ParamUtil.getString(resourceRequest, complied_yesORno+String.valueOf(i));
			String audit_commet = ParamUtil.getString(resourceRequest, auditors_comments+String.valueOf(i));
			
			jsonObject.put(sample_comments+i, sample);
			jsonObject.put(complied_yesORno+i, complied);
			jsonObject.put(auditors_comments+i, audit_commet);
		}
		
		return jsonObject;
		
	}
	
	@Reference
	HDFCInternal_Audit_ReportLocalService hdfcinternal_Audit_ReportLocalService;

}
