package com.npa.development.asset;

import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
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

public class NpaDevelopmentAssetRender extends BaseJSPAssetRenderer<MnNpaDevelopment> {
	
	private MnNpaDevelopment npaDevelopment;
	
	public NpaDevelopmentAssetRender(MnNpaDevelopment npaDevelopment) {
		this.npaDevelopment = npaDevelopment;
	}

	@Override
	public MnNpaDevelopment getAssetObject() {

		return npaDevelopment;
	}

	@Override
	public long getGroupId() {
		
		return userLocalService.fetchUser(npaDevelopment.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		
		return npaDevelopment.getCreatedby();
	}

	@Override
	public String getUserName() {
		
		return userLocalService.fetchUser(npaDevelopment.getCreatedby()).getFullName();
	}

	@Override
	public String getUuid() {
		
		return npaDevelopment.getUuid();
	}

	@Override
	public String getClassName() {
		
		return MnNpaDevelopment.class.getName();
	}

	@Override
	public long getClassPK() {
		
		return npaDevelopment.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(npaDevelopment.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+npaDevelopment.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(npaDevelopment.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("npaDevelopment---"+npaDevelopment);
			if(npaDevelopment!=null) {
				Long reportUploadLogId = npaDevelopment.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(npaDevelopment.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(npaDevelopment.getCreatedon());
			}
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("npaDevelopment", npaDevelopment);
		
		return "/asset/npaDevelopment_workflow_abstract_view.jsp";
	}
	
	@Reference
	UserLocalService userLocalService;
	
	Log _log = LogFactoryUtil.getLog(NpaDevelopmentAssetRender.class);

}
