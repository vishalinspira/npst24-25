package com.compliance.report.custodian.util;

import com.daily.average.service.model.CustodianCompFormScrutiny;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.CustodianCompFormScrutinyLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class NPSUserPrepopulateScrutinyData {
	
private static Log _log = LogFactoryUtil.getLog(NPSUserPrepopulateScrutinyData.class);
	
	public void prePopulateScrutinyData(HttpServletRequest renderRequest) {
		
		_log.info("hey im here -------------------------------------------------------------------------------------------------------------------");
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(88, Boolean.TRUE);
			long reportUploadLogId = (long)renderRequest.getAttribute("reportUploadLogId");
			_log.info("reportUploadLogID:::::::::::" + renderRequest.getAttribute("reportUploadLogId"));
			_log.info("reportUploadLogs :::::::: "+reportUploadLogs.toString());
			
			if(Validator.isNotNull(reportUploadLogId) && reportUploadLogId >0) {
				//ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
				//long reportUploadLogId = reportUploadLog.getReportUploadLogId();
				
				ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil.getReportUploadLog(reportUploadLogId);
				
				CustodianCompFormScrutiny custodianCompFormScrutiny = CustodianCompFormScrutinyLocalServiceUtil.fetchCustodianCompFormScrutinyByReportUploadLogId(reportUploadLogId);
						
				//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(null != custodianCompFormScrutiny) {
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate",  dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				}
				renderRequest.setAttribute("custodianCompFormScrutiny", custodianCompFormScrutiny);
			}	
			
		}catch (Exception e) {
			 _log.error(e);
		}
	}
}
