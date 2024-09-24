package com.supervisor.log.asset;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
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



public class NpsSuperviorReportUploadLogAssetRender extends BaseJSPAssetRenderer<ReportUploadLogSupervisor>{

	private final ReportUploadLogSupervisor reportUploadLogSupervisor;
	
	@Reference
    UserLocalService userLocalService; 
	
	public NpsSuperviorReportUploadLogAssetRender(ReportUploadLogSupervisor reportUploadLogSupervisor) {
		this.reportUploadLogSupervisor = reportUploadLogSupervisor;
	}
	
	@Override
	public ReportUploadLogSupervisor getAssetObject() {
		return reportUploadLogSupervisor;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(reportUploadLogSupervisor.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return userLocalService.fetchUser(reportUploadLogSupervisor.getCreatedBy()).getUserId();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(reportUploadLogSupervisor.getCreatedBy()).getFullName();
	}

	@Override
	public String getUuid() {
		return reportUploadLogSupervisor.getUuid();
	}

	@Override
	public String getClassName() {
		return ReportUploadLogSupervisor.class.getName();
	}

	@Override
	public long getClassPK() {
		return reportUploadLogSupervisor.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogSupervisor.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+reportUploadLogSupervisor.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogSupervisor.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			long reportUploadLogId = reportUploadLogSupervisor.getReportUploadLogId();
			ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil.fetchReportUploadLog(reportUploadLogId);
			reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
			
			ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLog.getReportMasterId());
			reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
			uploadDate = dateFormat.format(reportUploadLog.getCreateDate());
		} catch(Exception e) {
			 _log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		
		return "/asset/supervisor_report_upload_workflow_abstract_view.jsp";
	}
	
	private static final Log _log = LogFactoryUtil.getLog(NpsSuperviorReportUploadLogAssetRender.class);

}
