package com.nps.reportuploadlog.asset;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLogMaker;
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

import nps.common.service.constants.NameMappingConstants;

public class NpsMakerReportUploadLogAssetRender extends BaseJSPAssetRenderer<ReportUploadLogMaker> {
	private final ReportUploadLogMaker reportUploadLogMaker;
	
	@Reference
    UserLocalService userLocalService; 
	
	public NpsMakerReportUploadLogAssetRender(ReportUploadLogMaker reportUploadLogMaker) {
		this.reportUploadLogMaker = reportUploadLogMaker;
	}

	@Override
	public ReportUploadLogMaker getAssetObject() {
		return reportUploadLogMaker;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(reportUploadLogMaker.getCreatedBy()).getGroupId();
	}

	@Override
	public long getUserId() {
		return reportUploadLogMaker.getCreatedBy();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(reportUploadLogMaker.getCreatedBy()).getFullName();		
	}

	@Override
	public String getUuid() {
		return reportUploadLogMaker.getUuid();
	}

	@Override
	public String getClassName() {
		return ReportUploadLogMaker.class.getName();
	}

	@Override
	public long getClassPK() {
		return reportUploadLogMaker.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogMaker.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+reportUploadLogMaker.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLogMaker.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("reportUploadLogMaker---"+reportUploadLogMaker);
			if(reportUploadLogMaker!=null) {
				Long reportUploadLogId = reportUploadLogMaker.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogMaker.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
					_log.info("before report name: "+reportName+"  reportmaster cra: "+reportMaster.getCra());
					reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
				_log.info("report name: "+reportName);
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(reportUploadLogMaker.getCreateDate());
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
	
	Log _log = LogFactoryUtil.getLog(NpsMakerReportUploadLogAssetRender.class);
}
