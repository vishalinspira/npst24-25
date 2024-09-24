package com.monthly.compcertificate.asset;

import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnCompCertificateScrutiny;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.MnCompCertificateScrutinyLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.monthly.compcertificate.util.CompCertificateUtil;
import com.monthly.compcertificate.util.MonthlyCompCertificateUtil;

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


public class MonthlyCompCertificateAssetRender extends BaseJSPAssetRenderer<MnCompCertificate> {
	
	private final MnCompCertificate mnCompCertificate;
	
	@Reference
	UserLocalService userLocalService; 
	
	public MonthlyCompCertificateAssetRender(MnCompCertificate mnCompCertificate) {
		this.mnCompCertificate = mnCompCertificate;
	}

	@Override
	public MnCompCertificate getAssetObject() {
		return mnCompCertificate;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(mnCompCertificate.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		return mnCompCertificate.getCreatedby();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(mnCompCertificate.getCreatedby()).getFullName();		
	}

	@Override
	public String getUuid() {
		return mnCompCertificate.getUuid();
	}

	@Override
	public String getClassName() {
		return MnCompCertificate.class.getName();
	}

	@Override
	public long getClassPK() {
		return mnCompCertificate.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(mnCompCertificate.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+mnCompCertificate.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(mnCompCertificate.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String Annexure_a_iURL="";
		String Annexure_a_iiURL="";
		String Annexure_bURL="";
		String Annexure_cURL="";
		String Annexure_dURL="";
		String Annexure_eURL="";
		String Annexure_fURL="";
		String Annexure_gURL="";
		String Annexure_hURL="";
		boolean isNonNPSUser = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.debug("mnCompCertificate---"+mnCompCertificate);
			if(mnCompCertificate!=null) {
				Long reportUploadLogId = mnCompCertificate.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
			    httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						Annexure_a_iURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_a_i());
						Annexure_a_iiURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_a_ii());
						Annexure_bURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_b());
						Annexure_cURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_c());
						Annexure_dURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_d());
						Annexure_eURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_e());
						Annexure_fURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_f());
						Annexure_gURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_g());
						Annexure_hURL=CompCertificateUtil.getDocumentURL(mnCompCertificate.getAnnexure_h());
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(mnCompCertificate.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(mnCompCertificate.getCreatedon());
				try {
					List<MnCompCertificateScrutiny>	compCertificateScrutinies=MnCompCertificateScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					MnCompCertificateScrutiny mnCompCertificateScrutiny=compCertificateScrutinies.get(compCertificateScrutinies.size()-1);
					httpServletRequest.setAttribute("mnCompCertificateScrutiny", mnCompCertificateScrutiny);
				}catch (Exception e) {
					_log.error(e);
				}
			}
			MonthlyCompCertificateUtil  monthlyCompCertificateUtil= new MonthlyCompCertificateUtil();
			isNonNPSUser = monthlyCompCertificateUtil.isNonNpsUser(themeDisplay.getUserId());
			WorkflowTask workflowTask= WorkFlowTaskUtil.getWorkFlowtask(MnCompCertificate.class.getName(), mnCompCertificate.getReportUploadLogId(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
			boolean isAssignable= WorkFlowTaskUtil.isAssignable(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowTaskId());
			boolean isSelfAsignee=WorkFlowTaskUtil.isSelfAsignee(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowInstanceId());
			httpServletRequest.setAttribute("isSelfAsignee", isSelfAsignee);
			httpServletRequest.setAttribute("isAssignable", isAssignable);
			_log.info("isSelfAsignee:   "+isSelfAsignee);
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("mnCompCertificate", mnCompCertificate);
		httpServletRequest.setAttribute("Annexure_a_iURL", Annexure_a_iURL);
	    httpServletRequest.setAttribute("Annexure_a_iiURL", Annexure_a_iiURL);
	    httpServletRequest.setAttribute("Annexure_bURL", Annexure_bURL);
	    httpServletRequest.setAttribute("Annexure_cURL", Annexure_cURL);
	    httpServletRequest.setAttribute("Annexure_dURL", Annexure_dURL);
	    httpServletRequest.setAttribute("Annexure_eURL", Annexure_eURL);
	    httpServletRequest.setAttribute("Annexure_fURL", Annexure_fURL);
	    httpServletRequest.setAttribute("Annexure_gURL", Annexure_gURL);
	    httpServletRequest.setAttribute("Annexure_hURL", Annexure_hURL);
	    httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/monthly_comp_certificate_workflow_abstract_view.jsp";
	}
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(MonthlyCompCertificateAssetRender.class);

}
