package com.cra.report.upload.log.asset;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLogCRA;
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

public class NpsCraReportUploadlogAssetRender  extends BaseJSPAssetRenderer<ReportUploadLogCRA>{
private final ReportUploadLogCRA reportUploadLogCra;
	
	@Reference
    UserLocalService userLocalService; 
	
	public NpsCraReportUploadlogAssetRender (ReportUploadLogCRA reportUploadLogCra) {
		this.reportUploadLogCra = reportUploadLogCra;
	}

	@Override
	public ReportUploadLogCRA getAssetObject() {
		return reportUploadLogCra;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(reportUploadLogCra.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return reportUploadLogCra.getCreatedBy();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(reportUploadLogCra.getCreatedBy()).getFullName();		
	}

	@Override
	public String getUuid() {
		return reportUploadLogCra.getUuid();
	}

	@Override
	public String getClassName() {
		return ReportUploadLogCRA.class.getName();
	}

	@Override
	public long getClassPK() {
		return reportUploadLogCra.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogCra.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+reportUploadLogCra.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogCra.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		_log.info("------------in getJspPath");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("reportUploadLogGrievances---"+reportUploadLogCra);
			if(reportUploadLogCra!=null) {
				Long reportUploadLogId = reportUploadLogCra.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
				
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogCra.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					 _log.error(e);
				}
				uploadDate = dateFormat.format(reportUploadLogCra.getCreateDate());
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
	
	Log _log = LogFactoryUtil.getLog(NpsCraReportUploadlogAssetRender.class);
}
