package AnnualComplaincereport_Form.util;

import com.daily.average.service.model.AnnualCompCertificateScrutiny;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.AnnualCompCertificateScrutinyLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
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

@Component(immediate = true, service = Pre_Populate_scrutinydata.class)

public class Pre_Populate_scrutinydata {
	
	private final Log _log = LogFactoryUtil.getLog(Pre_Populate_scrutinydata.class);
	
public void pre_populate_scrutiny_data(HttpServletRequest renderRequest) {
		
	
	
	try {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long userId = themeDisplay.getUserId();
		
		User user = UserLocalServiceUtil.getUser(userId);
		
		_log.info("user mail address : "+user.getEmailAddress());
		
		AnnualCompCertificateScrutiny annualCompScrutiny = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();

		if(user.getEmailAddress().trim().equalsIgnoreCase("hdfc-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("HDFC");
		}
		else if(user.getEmailAddress().trim().equalsIgnoreCase("sbi-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("SBI");
		}
		else if(user.getEmailAddress().trim().equalsIgnoreCase("uti-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("UTI");
		}
		else if(user.getEmailAddress().trim().equalsIgnoreCase("lic-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("LIC");
		}
		else if(user.getEmailAddress().trim().equalsIgnoreCase("kotak-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("Kotak");
		}
		else if(user.getEmailAddress().trim().equalsIgnoreCase("abs-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("ABS");
		}
		else if(user.getEmailAddress().trim().equalsIgnoreCase("icici-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("ICICI");
		}else if(user.getEmailAddress().trim().equalsIgnoreCase("dsp-maker@pfm.com")) {
			reportUploadLogs = getReportLogList("DSP");
		}
		
		
		if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
			ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
			long reportUploadLogId = reportUploadLog.getReportUploadLogId();
			
			 //annualCompScrutiny = AnnualCompCertificateScrutinyLocalServiceUtil.findAnnualCompCertificateScrutinyByReportUplaodLogId(reportUploadLogId).stream().findFirst().orElse(null);
			List<AnnualCompCertificateScrutiny> annualCompScrutinyList = null;
			try {
				annualCompScrutinyList = AnnualCompCertificateScrutinyLocalServiceUtil.findAnnualCompCertificateScrutinyByReportUplaodLogId(reportUploadLogId);
			} catch (Exception e) {
				_log.error("Exception in getting qtrstewardshipscrutinyList::" + e.getMessage());
			}
			
			if(Validator.isNotNull(annualCompScrutinyList) && annualCompScrutinyList.size() > 0) {
				annualCompScrutiny = annualCompScrutinyList.get(annualCompScrutinyList.size()-1);
			}
			if(null != annualCompScrutiny) {
				renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
				renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
				renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
			}
			renderRequest.setAttribute("annualCompScrutiny", annualCompScrutiny);
			
		}
		
		
	} catch (Exception e) {
		 _log.error(e);
	}
}

private List<ReportUploadLog> getReportLogList(String intermediaryname){
	List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
	
	try {
		reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(79, Boolean.FALSE).stream().filter(itr -> itr.getIntermediaryname().trim().equalsIgnoreCase(intermediaryname)).collect(Collectors.toList());
	} catch (Exception e) {
		_log.error(e);
	}
	return reportUploadLogs;
	
}

}
