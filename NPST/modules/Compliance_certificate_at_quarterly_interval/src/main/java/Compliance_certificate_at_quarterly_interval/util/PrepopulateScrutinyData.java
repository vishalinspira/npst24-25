package Compliance_certificate_at_quarterly_interval.util;

import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ScrutinyInputQuarterlyInterval;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.average.service.service.ScrutinyInputQuarterlyIntervalLocalServiceUtil;
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

@Component(immediate = true, service = PrepopulateScrutinyData.class)
public class PrepopulateScrutinyData {

	private static Log _log = LogFactoryUtil.getLog(PrepopulateScrutinyData.class);
	
	public void prePopulateScrutinyData(HttpServletRequest renderRequest) {
		
		_log.info("hey im here -------------------------------------------------------------------------------------------------------------------");
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(56, Boolean.FALSE);
			
			_log.info("reportUploadLogs :::::::: "+reportUploadLogs.toString());
			
			if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
				ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
				long reportUploadLogId = reportUploadLog.getReportUploadLogId();
				ScrutinyInputQuarterlyInterval scrutinyInputQuarterlyInterval = null;
				//CustAnnualReportscrutiny custAnnualScrutiny = CustAnnualReportscrutinyLocalServiceUtil.findCustAnnualReportscrutinyByReportUplaodLogId(reportUploadLogId).stream().findFirst().orElse(null);
				List<ScrutinyInputQuarterlyInterval> scrutinyInputQuarterlyIntervals =  ScrutinyInputQuarterlyIntervalLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
				//List<CustAnnualReportscrutiny> custAnnualScrutinyList = CustAnnualReportscrutinyLocalServiceUtil.findCustAnnualReportscrutinyByReportUplaodLogId(reportUploadLogId);
				if(Validator.isNotNull(scrutinyInputQuarterlyIntervals) && scrutinyInputQuarterlyIntervals.size() > 0) {
					scrutinyInputQuarterlyInterval = scrutinyInputQuarterlyIntervals.get(scrutinyInputQuarterlyIntervals.size()-1);
				}
				
				if(null != scrutinyInputQuarterlyInterval) {
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				}
				renderRequest.setAttribute("scrutinyInputQuarterlyInterval", scrutinyInputQuarterlyInterval);
				
				_log.info("scrutinyInputQuarterlyInterval :::::::: "+ scrutinyInputQuarterlyInterval == null ? "null" : scrutinyInputQuarterlyInterval.toString());
			}
		} catch (Exception e) {
			 _log.error(e);
		}
	}
	
	public void pre_populate_scrutiny_data(HttpServletRequest renderRequest) {
		
		
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			long userId = themeDisplay.getUserId();
			
			User user = UserLocalServiceUtil.getUser(userId);
			
			_log.info("user mail address : "+user.getEmailAddress());
			
			List<ScrutinyInputQuarterlyInterval> scrutinyInputQuarterlyIntervals = null;
			
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
				
				scrutinyInputQuarterlyIntervals = ScrutinyInputQuarterlyIntervalLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
				if(null != scrutinyInputQuarterlyIntervals && scrutinyInputQuarterlyIntervals.size()>0) {
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
					renderRequest.setAttribute("scrutinyInputQuarterlyInterval", scrutinyInputQuarterlyIntervals.get(scrutinyInputQuarterlyIntervals.size()-1));
				}
				
				
			}
			
			
		} catch (Exception e) {
			 _log.error(e);
		}
	}
	
	private List<ReportUploadLog> getReportLogList(String intermediaryname){
		List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
		
		try {
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(56, Boolean.FALSE).stream().filter(itr -> itr.getIntermediaryname().trim().equalsIgnoreCase(intermediaryname)).collect(Collectors.toList());
		} catch (Exception e) {
			_log.error(e);
		}
		
		return reportUploadLogs;
		
	}
}
