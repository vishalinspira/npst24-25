package nps.pfm.am.reportuploadlog.asset;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
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

public class NpsPfmAmReportUploadLogAssetRender extends BaseJSPAssetRenderer<ReportUploadLogPFMAM> {
	private final ReportUploadLogPFMAM reportUploadLogPFMAM;
	
	@Reference
    UserLocalService userLocalService; 
	
	public NpsPfmAmReportUploadLogAssetRender(ReportUploadLogPFMAM reportUploadLogPFMAM) {
		this.reportUploadLogPFMAM = reportUploadLogPFMAM;
	}

	@Override
	public ReportUploadLogPFMAM getAssetObject() {
		return reportUploadLogPFMAM;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(reportUploadLogPFMAM.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return reportUploadLogPFMAM.getCreatedBy();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(reportUploadLogPFMAM.getCreatedBy()).getFullName();		
	}

	@Override
	public String getUuid() {
		return reportUploadLogPFMAM.getUuid();
	}

	@Override
	public String getClassName() {
		return ReportUploadLogPFMAM.class.getName();
	}

	@Override
	public long getClassPK() {
		return reportUploadLogPFMAM.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogPFMAM.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+reportUploadLogPFMAM.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogPFMAM.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("reportUploadLogPFMAM---"+reportUploadLogPFMAM);
			if(reportUploadLogPFMAM!=null) {
				Long reportUploadLogId = reportUploadLogPFMAM.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMAM.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(reportUploadLogPFMAM.getCreateDate());
			}
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		
		return "/asset/report_upload_workflow_abstract_view.jsp";
	}
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(NpsPfmAmReportUploadLogAssetRender.class);
}