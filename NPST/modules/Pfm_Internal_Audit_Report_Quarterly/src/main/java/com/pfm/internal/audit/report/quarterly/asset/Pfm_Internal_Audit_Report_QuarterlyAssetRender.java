package com.pfm.internal.audit.report.quarterly.asset;

import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_ReportSoap;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny;
import com.daily.pfm.service.model.Pfm_Qr_IARScrutiny;
import com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil;
import com.daily.pfm.service.service.Pfm_Qr_IARScrutinyLocalServiceUtil;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.pfm.internal.audit.report.quarterly.util.PMFIARUtil;
import com.pfm.internal.audit.report.quarterly.util.PfmIarNonNps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.WorkFlowTaskUtil;


public class Pfm_Internal_Audit_Report_QuarterlyAssetRender extends BaseJSPAssetRenderer<Pfm_Qr_Internal_Audit_Report> {
	
private Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report;
	
	public Pfm_Internal_Audit_Report_QuarterlyAssetRender(Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report) {
		this.pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_Report;
	}

	@Override
	public Pfm_Qr_Internal_Audit_Report getAssetObject() {

		return pfm_Qr_Internal_Audit_Report;
	}

	@Override
	public long getGroupId() {
		
		return userLocalService.fetchUser(pfm_Qr_Internal_Audit_Report.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		
		return pfm_Qr_Internal_Audit_Report.getCreatedby();
	}

	@Override
	public String getUserName() {
		
		return userLocalService.fetchUser(pfm_Qr_Internal_Audit_Report.getCreatedby()).getFullName();
	}

	@Override
	public String getUuid() {
		
		return pfm_Qr_Internal_Audit_Report.getUuid();
	}

	@Override
	public String getClassName() {
		
		return Pfm_Qr_Internal_Audit_Report.class.getName();
	}

	@Override
	public long getClassPK() {
		
		return pfm_Qr_Internal_Audit_Report.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(pfm_Qr_Internal_Audit_Report.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+pfm_Qr_Internal_Audit_Report.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(pfm_Qr_Internal_Audit_Report.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String annex_comp_certificate = "";
		String extracts_board_pdf = "";
		String annex_comp_certificate_i = "";
		String annex_comp_certificate_ii = "";
		String annex_comp_certificate_iii = "";
		
		boolean isNonNPSUser = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("pfm_Qr_Internal_Audit_Report---"+pfm_Qr_Internal_Audit_Report);
			if(pfm_Qr_Internal_Audit_Report!=null) {
				Long reportUploadLogId = pfm_Qr_Internal_Audit_Report.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
						annex_comp_certificate = PMFIARUtil.getDocumentURL(pfm_Qr_Internal_Audit_Report.getAnnex_comp_certificate());
						extracts_board_pdf = PMFIARUtil.getDocumentURL(pfm_Qr_Internal_Audit_Report.getExtracts_board_pdf());
						annex_comp_certificate_i = PMFIARUtil.getDocumentURL(pfm_Qr_Internal_Audit_Report.getAnnex_comp_certificate_i());
						annex_comp_certificate_ii = PMFIARUtil.getDocumentURL(pfm_Qr_Internal_Audit_Report.getAnnex_comp_certificate_ii());
						annex_comp_certificate_iii = PMFIARUtil.getDocumentURL(pfm_Qr_Internal_Audit_Report.getAnnex_comp_certificate_iii());
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(pfm_Qr_Internal_Audit_Report.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(pfm_Qr_Internal_Audit_Report.getCreatedon());
				try {
					List<Pfm_Qr_IARScrutiny> iarScrutinies=Pfm_Qr_IARScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					Pfm_Qr_IARScrutiny pfm_Qr_IARScrutiny=null;
					
					if(iarScrutinies.size()>0) {
					 pfm_Qr_IARScrutiny=iarScrutinies.get(iarScrutinies.size()-1);
					httpServletRequest.setAttribute("pfm_Qr_IARScrutiny", pfm_Qr_IARScrutiny);
					}
					else {
						pfm_Qr_IARScrutiny=Pfm_Qr_IARScrutinyLocalServiceUtil.createPfm_Qr_IARScrutiny(0);
						
						_log.info("line 91"+pfm_Qr_IARScrutiny);
					}
				}catch (Exception e) {
					_log.error(e);
				}
			}
			
			PfmIarNonNps pfmIarNonNps = new PfmIarNonNps();
			isNonNPSUser = pfmIarNonNps.isNonNpsUser(themeDisplay.getUserId());

			WorkflowTask workflowTask= WorkFlowTaskUtil.getWorkFlowtask(Pfm_Qr_Internal_Audit_Report.class.getName(), pfm_Qr_Internal_Audit_Report.getReportUploadLogId(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			boolean isAssignable= WorkFlowTaskUtil.isAssignable(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowTaskId());
			 boolean isSelfAsignee=WorkFlowTaskUtil.isSelfAsignee(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowInstanceId());
				httpServletRequest.setAttribute("isSelfAsignee", isSelfAsignee);	
				httpServletRequest.setAttribute("isAssignable", isAssignable);
			 _log.info("isAssignable "+isAssignable+"  taskid:  "+workflowTask.getWorkflowTaskId());
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("pfm_Qr_Internal_Audit_Report", pfm_Qr_Internal_Audit_Report);
		httpServletRequest.setAttribute("annex_comp_certificate", annex_comp_certificate);
		httpServletRequest.setAttribute("extracts_board_pdf", extracts_board_pdf);
		httpServletRequest.setAttribute("annex_comp_certificate_i", annex_comp_certificate_i);
		httpServletRequest.setAttribute("annex_comp_certificate_ii", annex_comp_certificate_ii);
		httpServletRequest.setAttribute("annex_comp_certificate_iii", annex_comp_certificate_iii);
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/scrutiny.jsp";
	}
	
	@Reference
	UserLocalService userLocalService;
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	
	
	Log _log = LogFactoryUtil.getLog(Pfm_Internal_Audit_Report_QuarterlyAssetRender.class);

}
