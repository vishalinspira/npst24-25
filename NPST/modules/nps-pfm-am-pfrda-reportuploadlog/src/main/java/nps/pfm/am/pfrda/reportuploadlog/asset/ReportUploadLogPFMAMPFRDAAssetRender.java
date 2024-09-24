package nps.pfm.am.pfrda.reportuploadlog.asset;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
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

public class ReportUploadLogPFMAMPFRDAAssetRender extends BaseJSPAssetRenderer<ReportUploadLogPFMAMPFRDA> {
	private final ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA;
	
	@Reference
    UserLocalService userLocalService;
	
	public ReportUploadLogPFMAMPFRDAAssetRender(ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA) {
		this.reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDA;
	}
	
	@Override
	public ReportUploadLogPFMAMPFRDA getAssetObject() {
		return reportUploadLogPFMAMPFRDA;
	}
	
	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(reportUploadLogPFMAMPFRDA.getCreatedBy()).getGroupId();
	}
	
	@Override
	public long getUserId() {
		return userLocalService.fetchUser(reportUploadLogPFMAMPFRDA.getCreatedBy()).getUserId();
	}
	
	@Override
	public String getUserName() {
		return userLocalService.fetchUser(reportUploadLogPFMAMPFRDA.getCreatedBy()).getFullName();		
	}
	
	@Override
	public String getUuid() {
		return reportUploadLogPFMAMPFRDA.getUuid();
	}
	
	@Override
	public String getClassName() {
		return ReportUploadLogPFMAMPFRDA.class.getName();
	}
	
	@Override
	public long getClassPK() {
		return reportUploadLogPFMAMPFRDA.getPrimaryKey();
	}
	
	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+reportUploadLogPFMAMPFRDA.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			if(reportUploadLogPFMAMPFRDA!=null) {
				Long reportUploadLogId = reportUploadLogPFMAMPFRDA.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
				ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
				reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				uploadDate = dateFormat.format(reportUploadLogPFMAMPFRDA.getCreateDate());
			}
		} catch(Exception e) {
			 _log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		
		return "/asset/npst_workflow_abstract_view.jsp";
	}

	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogPFMAMPFRDAAssetRender.class);
}
