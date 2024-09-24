package com.pfm.internal.audit.report.quarterly.util;

import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.pfm.service.model.Pfm_Qr_IARScrutiny;
import com.daily.pfm.service.service.Pfm_Qr_IARScrutinyLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = NPSUserPre_Populate_scrutinydata.class)
public class NPSUserPre_Populate_scrutinydata {
	
	private final Log _log = LogFactoryUtil.getLog(NPSUserPre_Populate_scrutinydata.class);
	
	public void pre_populate_scrutiny_data(HttpServletRequest renderRequest) {
		
		
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			long userId = themeDisplay.getUserId();
			
			User user = UserLocalServiceUtil.getUser(userId);
			
			_log.info("user mail address : "+user.getEmailAddress());
			
			Pfm_Qr_IARScrutiny pfm_Qr_IARScrutiny = null;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			long reportUploadLogId = (long)renderRequest.getAttribute("reportUploadLogId");
			
			List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
			/*
			 * if(user.getEmailAddress().trim().equalsIgnoreCase("hdfc-maker@pfm.com")) {
			 * reportUploadLogs = getReportLogList("HDFC"); } else
			 * if(user.getEmailAddress().trim().equalsIgnoreCase("sbi-maker@pfm.com")) {
			 * reportUploadLogs = getReportLogList("SBI"); } else
			 * if(user.getEmailAddress().trim().equalsIgnoreCase("uti-maker@pfm.com")) {
			 * reportUploadLogs = getReportLogList("UTI"); } else
			 * if(user.getEmailAddress().trim().equalsIgnoreCase("lic-maker@pfm.com")) {
			 * reportUploadLogs = getReportLogList("LIC"); } else
			 * if(user.getEmailAddress().trim().equalsIgnoreCase("kotak-maker@pfm.com")) {
			 * reportUploadLogs = getReportLogList("Kotak"); } else
			 * if(user.getEmailAddress().trim().equalsIgnoreCase("abs-maker@pfm.com")) {
			 * reportUploadLogs = getReportLogList("ABS"); } else
			 * if(user.getEmailAddress().trim().equalsIgnoreCase("icici-maker@pfm.com")) {
			 * reportUploadLogs = getReportLogList("ICICI"); }
			 */
			
			if(Validator.isNotNull(reportUploadLogId) && reportUploadLogId >0) {
				/* ReportUploadLog reportUploadLog = reportUploadLogs.get(0); */
				
            	ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil.getReportUploadLog(reportUploadLogId);

				
				List<Pfm_Qr_IARScrutiny> pfm_Qr_IARScrutinyList = Pfm_Qr_IARScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
				
				if(Validator.isNotNull(pfm_Qr_IARScrutinyList) && pfm_Qr_IARScrutinyList.size() > 0) {
					pfm_Qr_IARScrutiny = pfm_Qr_IARScrutinyList.get(pfm_Qr_IARScrutinyList.size()-1);
					_log.info("pfm_Qr_IARScrutiny--------------"+pfm_Qr_IARScrutiny);
				}
				
				
				if(null != pfm_Qr_IARScrutiny) {
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				}
				renderRequest.setAttribute("pfm_Qr_IARScrutiny", pfm_Qr_IARScrutiny);
				
			}
			
			
		} catch (Exception e) {
			 _log.error(e);
		}
	}
	
	private List<ReportUploadLog> getReportLogList(String intermediaryname){
		List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
		
		try {
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(157, Boolean.FALSE).stream().filter(itr -> itr.getIntermediaryname().trim().equalsIgnoreCase(intermediaryname)).collect(Collectors.toList());
		} catch (Exception e) {
			_log.error(e);
		}
		
		return reportUploadLogs;
		
	}
}
