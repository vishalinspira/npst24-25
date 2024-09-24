package com.internal.audit.report.pfm.asset;

import com.daily.average.service.model.HDFCInternal_Audit_Report;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.internal.audit.report.pfm.util.DocumentUploadUtil;
import com.internal.audit.report.pfm.util.HDFC_Internal_audit_report_PFM_Util;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

public class InternalAuditReportAssetRender extends BaseJSPAssetRenderer<HDFCInternal_Audit_Report>{
	
	Log _log = LogFactoryUtil.getLog(InternalAuditReportAssetRender.class);
	
	private HDFCInternal_Audit_Report auditreport;
	
	public InternalAuditReportAssetRender(HDFCInternal_Audit_Report auditreport) {
		this.auditreport = auditreport;
	}

	@Override
	public HDFCInternal_Audit_Report getAssetObject() {
		return auditreport;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(auditreport.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		return auditreport.getCreatedby();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(auditreport.getCreatedby()).getFirstName();
	}

	@Override
	public String getUuid() {
		return auditreport.getUuid();
	}

	@Override
	public String getClassName() {
		return HDFCInternal_Audit_Report.class.getName();
	}

	@Override
	public long getClassPK() {
		return auditreport.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(auditreport.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+auditreport.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(auditreport.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String certificate_pdf_file_id = "";
		String auditor_pdf_file_id = "";
		String AnnexureForIARCircular_pdf_file_id = "";
		boolean isNonNPSUser = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			if(auditreport!=null) {
				Long reportUploadLogId = auditreport.getPrimaryKey();
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						certificate_pdf_file_id = DocumentUploadUtil.getDocumentURL(auditreport.getCertificate_pdf_file_id());
						auditor_pdf_file_id = DocumentUploadUtil.getDocumentURL(auditreport.getAuditor_pdf_file_id());
						AnnexureForIARCircular_pdf_file_id = DocumentUploadUtil.getDocumentURL(auditreport.getFileEntryId());
						httpServletRequest.setAttribute("reportMasterId", auditreport.getReportMasterId());
						httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(auditreport.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(auditreport.getCreatedon());
				isNonNPSUser = HDFC_Internal_audit_report_PFM_Util.isNonNpsUser(themeDisplay.getUserId());
				
			}
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("auditreport", auditreport);
		httpServletRequest.setAttribute("certificate_pdf_file_url", certificate_pdf_file_id);
		httpServletRequest.setAttribute("auditor_pdf_file_url", auditor_pdf_file_id);
		httpServletRequest.setAttribute("AnnexureForIARCircular_pdf_file_url", AnnexureForIARCircular_pdf_file_id);
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/hdfc_internal_audit_workflow_abstract_view.jsp";
	}
	
	
	@Reference
	UserLocalService userLocalService;

}
