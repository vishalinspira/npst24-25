package Cut_Annual_Audit_Report.Util;

import com.daily.average.service.model.CustAnnualReportscrutiny;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.CustAnnualReportscrutinyLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(88, Boolean.FALSE);
			
			_log.info("reportUploadLogs :::::::: "+reportUploadLogs.toString());
			
			if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
				ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
				long reportUploadLogId = reportUploadLog.getReportUploadLogId();
				CustAnnualReportscrutiny custAnnualScrutiny = null;
				//CustAnnualReportscrutiny custAnnualScrutiny = CustAnnualReportscrutinyLocalServiceUtil.findCustAnnualReportscrutinyByReportUplaodLogId(reportUploadLogId).stream().findFirst().orElse(null);
				List<CustAnnualReportscrutiny> custAnnualScrutinyList = CustAnnualReportscrutinyLocalServiceUtil.findCustAnnualReportscrutinyByReportUplaodLogId(reportUploadLogId);
				if(Validator.isNotNull(custAnnualScrutinyList) && custAnnualScrutinyList.size() > 0) {
					custAnnualScrutiny = custAnnualScrutinyList.get(custAnnualScrutinyList.size()-1);
				}
				
				if(null != custAnnualScrutiny) {
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				}
				renderRequest.setAttribute("custAnnualScrutiny", custAnnualScrutiny);
				
				//_log.info("custAnnualScrutiny :::::::: "+ custAnnualScrutiny == null ? "null" : custAnnualScrutiny.toString());
			}
		} catch (Exception e) {
			 _log.error(e);
		}
	}
	
	

}
