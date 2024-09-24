package AnnualComplaincereport_Form.asset;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.AnnualCompCertificateScrutiny;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.AnnualCompCertificateScrutinyLocalServiceUtil;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

import AnnualComplaincereport_Form.util.AnnualComplaincereport_FormDocumentUtil;
import AnnualComplaincereport_Form.util.AnnualComplianceReportUtil;
import nps.common.service.util.WorkFlowTaskUtil;

public class AnnualComplaincereport_FormAssetRender extends BaseJSPAssetRenderer<AnnualCompCertificate>{

private final AnnualCompCertificate annualCompCertificate;
	
	@Reference
	UserLocalService userLocalService;
	
	public AnnualComplaincereport_FormAssetRender(AnnualCompCertificate annualCompCertificate) {
		this.annualCompCertificate = annualCompCertificate;
	}

	@Override
	public AnnualCompCertificate getAssetObject() {
		return annualCompCertificate;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(annualCompCertificate.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		return annualCompCertificate.getCreatedby();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(annualCompCertificate.getCreatedby()).getFullName();
	}

	@Override
	public String getUuid() {
		return annualCompCertificate.getUuid();
	}

	@Override
	public String getClassName() {
		return AnnualCompCertificate.class.getName();
	}

	@Override
	public long getClassPK() {
		return annualCompCertificate.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(annualCompCertificate.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+annualCompCertificate.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(annualCompCertificate.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String Annexurei="";
		String Annexureii="";
		String Annexureiii="";
		String Annexureiv="";
		String Annexurev="";
		boolean isNonNPSUser = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			//_log.info("annualCompCertificate---"+annualCompCertificate);
			if(annualCompCertificate!=null) {
				Long reportUploadLogId = annualCompCertificate.getPrimaryKey();
				httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						Annexurei=AnnualComplaincereport_FormDocumentUtil.getDocumentURL(annualCompCertificate.getAnnexurei());
						Annexureii=AnnualComplaincereport_FormDocumentUtil.getDocumentURL(annualCompCertificate.getAnnexureii());
						Annexureiii=AnnualComplaincereport_FormDocumentUtil.getDocumentURL(annualCompCertificate.getAnnexureiii());
						Annexureiv=AnnualComplaincereport_FormDocumentUtil.getDocumentURL(annualCompCertificate.getAnnexureiv());
						Annexurev=AnnualComplaincereport_FormDocumentUtil.getDocumentURL(annualCompCertificate.getAnnexurev());
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(annualCompCertificate.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(annualCompCertificate.getCreatedate());
				try {
					List<AnnualCompCertificateScrutiny>	annualCompCertificateScrutinies=AnnualCompCertificateScrutinyLocalServiceUtil.findAnnualCompCertificateScrutinyByReportUplaodLogId(reportUploadLogId);
					AnnualCompCertificateScrutiny annualCompScrutiny=annualCompCertificateScrutinies.get(annualCompCertificateScrutinies.size()-1);
					httpServletRequest.setAttribute("annualCompScrutiny", annualCompScrutiny);
				}catch (Exception e) {
					_log.error(e);
				}
			}
			AnnualComplianceReportUtil annualComplianceReportUtil= new AnnualComplianceReportUtil();
			 isNonNPSUser = annualComplianceReportUtil.isNonNpsUser(themeDisplay.getUserId());
			 WorkflowTask workflowTask= WorkFlowTaskUtil.getWorkFlowtask(AnnualCompCertificate.class.getName(), annualCompCertificate.getReportUploadLogId(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), themeDisplay.getUserId());	
			 boolean isAssignable= WorkFlowTaskUtil.isAssignable(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowTaskId());
				boolean isSelfAsignee=WorkFlowTaskUtil.isSelfAsignee(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowInstanceId());
				httpServletRequest.setAttribute("isSelfAsignee", isSelfAsignee);
				httpServletRequest.setAttribute("isAssignable", isAssignable);
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("annualCompCertificate", annualCompCertificate);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("Annexurei", Annexurei);
		httpServletRequest.setAttribute("Annexureii", Annexureii);
		httpServletRequest.setAttribute("Annexureiii", Annexureiii);
		httpServletRequest.setAttribute("Annexureiv", Annexureiv);
		httpServletRequest.setAttribute("Annexurev", Annexurev);
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/annual_comp_certificate_workflow_abstract_view.jsp";
	} 
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(AnnualComplaincereport_FormAssetRender.class);

}