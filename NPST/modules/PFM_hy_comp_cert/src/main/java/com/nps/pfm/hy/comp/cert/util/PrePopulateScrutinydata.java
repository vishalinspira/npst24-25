package com.nps.pfm.hy.comp.cert.util;

import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny;
import com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil;
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

@Component(immediate = true, service = PrePopulateScrutinydata.class)
public class PrePopulateScrutinydata {
	
private final Log _log = LogFactoryUtil.getLog(PrePopulateScrutinydata.class);
	
	public void pre_populate_scrutiny_data(HttpServletRequest renderRequest) {
		
		
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			long userId = themeDisplay.getUserId();
			
			User user = UserLocalServiceUtil.getUser(userId);
			
			_log.info("user mail address : "+user.getEmailAddress());
			
			PFM_hy_comp_cert_Scrutiny  pfm_hy_comp_cert_Scrutiny = null;
			
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

            	
				List<PFM_hy_comp_cert_Scrutiny> pfm_hy_comp_cert_ScrutinyList = PFM_hy_comp_cert_ScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
				
				if(Validator.isNotNull(pfm_hy_comp_cert_ScrutinyList) && pfm_hy_comp_cert_ScrutinyList.size() > 0) {
					pfm_hy_comp_cert_Scrutiny = pfm_hy_comp_cert_ScrutinyList.get(pfm_hy_comp_cert_ScrutinyList.size()-1);
					_log.info("pfm_hy_comp_cert_Scrutiny--------------"+pfm_hy_comp_cert_Scrutiny);
				}
				
				
				if(null != pfm_hy_comp_cert_Scrutiny) {
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				}
				renderRequest.setAttribute("pfm_hy_comp_cert_Scrutiny", pfm_hy_comp_cert_Scrutiny);
				
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

