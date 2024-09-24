package nps.cust.am.pfrda.reportuploadlog.asset;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLogCustAMPFRDA;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;


public class ReportUploadLogCustAMPFRDAAssetRender extends BaseJSPAssetRenderer<ReportUploadLogCustAMPFRDA> {
	private final ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA;
	
	@Reference
    UserLocalService userLocalService;
	
	public ReportUploadLogCustAMPFRDAAssetRender(ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA) {
		this.reportUploadLogCustAMPFRDA = reportUploadLogCustAMPFRDA;
	}
	
	@Override
	public ReportUploadLogCustAMPFRDA getAssetObject() {
		return reportUploadLogCustAMPFRDA;
	}
	
	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(reportUploadLogCustAMPFRDA.getCreatedBy()).getGroupId();
	}
	
	@Override
	public long getUserId() {
		return userLocalService.fetchUser(reportUploadLogCustAMPFRDA.getCreatedBy()).getUserId();
	}
	
	@Override
	public String getUserName() {
		return userLocalService.fetchUser(reportUploadLogCustAMPFRDA.getCreatedBy()).getFullName();		
	}
	
	@Override
	public String getUuid() {
		return reportUploadLogCustAMPFRDA.getUuid();
	}
	
	@Override
	public String getClassName() {
		return ReportUploadLogCustAMPFRDA.class.getName();
	}
	
	@Override
	public long getClassPK() {
		return reportUploadLogCustAMPFRDA.getPrimaryKey();
	}
	
	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogCustAMPFRDA.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+reportUploadLogCustAMPFRDA.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogCustAMPFRDA.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			if(reportUploadLogCustAMPFRDA!=null) {
				Long reportUploadLogId = reportUploadLogCustAMPFRDA.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
				ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogCustAMPFRDA.getReportMasterId());
				reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				uploadDate = dateFormat.format(reportUploadLogCustAMPFRDA.getCreateDate());
			}
		} catch(Exception e) {
			 _log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		
		return "/asset/npst_workflow_abstract_view.jsp";
	}

	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogCustAMPFRDAAssetRender.class);
}
