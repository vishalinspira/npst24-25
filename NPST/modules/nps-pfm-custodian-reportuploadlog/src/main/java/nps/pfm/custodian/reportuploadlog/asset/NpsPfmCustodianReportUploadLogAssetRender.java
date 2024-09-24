package nps.pfm.custodian.reportuploadlog.asset;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLogPFMCustodian;
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


public class NpsPfmCustodianReportUploadLogAssetRender extends BaseJSPAssetRenderer<ReportUploadLogPFMCustodian> {
private final ReportUploadLogPFMCustodian reportUploadLogPFMCustodian;
	
	@Reference
    UserLocalService userLocalService; 
	
	public NpsPfmCustodianReportUploadLogAssetRender(ReportUploadLogPFMCustodian reportUploadLogPFMCustodian) {
		this.reportUploadLogPFMCustodian = reportUploadLogPFMCustodian;
	}

	@Override
	public ReportUploadLogPFMCustodian getAssetObject() {
		return reportUploadLogPFMCustodian;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(reportUploadLogPFMCustodian.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return reportUploadLogPFMCustodian.getCreatedBy();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(reportUploadLogPFMCustodian.getCreatedBy()).getFullName();		
	}

	@Override
	public String getUuid() {
		return reportUploadLogPFMCustodian.getUuid();
	}

	@Override
	public String getClassName() {
		return ReportUploadLogPFMCustodian.class.getName();
	}

	@Override
	public long getClassPK() {
		return reportUploadLogPFMCustodian.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+reportUploadLogPFMCustodian.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("reportUploadLogPFMCustodian---"+reportUploadLogPFMCustodian);
			if(reportUploadLogPFMCustodian!=null) {
				Long reportUploadLogId = reportUploadLogPFMCustodian.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(reportUploadLogPFMCustodian.getCreateDate());
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
	
	Log _log = LogFactoryUtil.getLog(NpsPfmCustodianReportUploadLogAssetRender.class);
}
