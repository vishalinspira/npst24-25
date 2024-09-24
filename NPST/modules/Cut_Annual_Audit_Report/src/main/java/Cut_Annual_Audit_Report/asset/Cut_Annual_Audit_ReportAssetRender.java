package Cut_Annual_Audit_Report.asset;

import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
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
import com.liferay.portal.kernel.util.ParamUtil;
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

import Cut_Annual_Audit_Report.Util.Cut_Annual_Audit_ReportDocumentUtil;
import Cut_Annual_Audit_Report.Util.Cut_Annual_Audit_Report_Util;


public class Cut_Annual_Audit_ReportAssetRender extends BaseJSPAssetRenderer<CustAnnualAuditReport>{

private final CustAnnualAuditReport custAnnualAuditReport;
	
	@Reference
	UserLocalService userLocalService;
	
	public Cut_Annual_Audit_ReportAssetRender(CustAnnualAuditReport custAnnualAuditReport) {
		this.custAnnualAuditReport = custAnnualAuditReport;
	}
	
	@Override
	public CustAnnualAuditReport getAssetObject() {
		return custAnnualAuditReport;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(custAnnualAuditReport.getCreatedby()).getGroupId();
 	}

	@Override
	public long getUserId() {
		return custAnnualAuditReport.getCreatedby();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(custAnnualAuditReport.getCreatedby()).getFullName();
	}

	@Override
	public String getUuid() {
		return custAnnualAuditReport.getUuid();
	}

	@Override
	public String getClassName() {
		return CustAnnualAuditReport.class.getName();
	}

	@Override
	public long getClassPK() {
		return custAnnualAuditReport.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(custAnnualAuditReport.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+custAnnualAuditReport.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(custAnnualAuditReport.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String Audit_pdf="";
		boolean isNonNPSUser = false;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("custAnnualAuditReport---"+custAnnualAuditReport);
			if(custAnnualAuditReport!=null) {
				Long reportUploadLogId = custAnnualAuditReport.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null) {
						httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
						httpServletRequest.setAttribute("reportMasterId", custAnnualAuditReport.getReportMasterId());
						try {
							reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
							Audit_pdf=Cut_Annual_Audit_ReportDocumentUtil.getDocumentURL(custAnnualAuditReport.getAudit_pdf_fileentry_id());
						} catch (Exception e) {
							_log.error(e);
						}
						
					try {
						ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(custAnnualAuditReport.getReportMasterId());
						reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
					} catch (Exception e) {
						_log.error("Exception on getting report file name : "+e.getMessage());
					}
				}
				uploadDate = dateFormat.format(custAnnualAuditReport.getCreatedon());
			}
			Cut_Annual_Audit_Report_Util cut_annual_audit_report_util = new Cut_Annual_Audit_Report_Util();
			isNonNPSUser = cut_annual_audit_report_util.isNonNpsUser(themeDisplay.getUserId());
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("custAnnualAuditReport", custAnnualAuditReport);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("Audit_pdf", Audit_pdf);
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/cut_annual_audit_report_workflow_abstract_view.jsp";
	}
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(Cut_Annual_Audit_ReportAssetRender.class);

}
