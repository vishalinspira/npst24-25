package com.custodian.iar.util;

import com.daily.average.service.model.CustIARScrutiny;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.CustIARScrutinyLocalServiceUtil;
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
				
				CustIARScrutiny custIARScrutiny = null;
				
				List<CustIARScrutiny> custIARScrutinyList = CustIARScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
				
				if(Validator.isNotNull(custIARScrutinyList) && custIARScrutinyList.size() > 0) {
					
					custIARScrutiny = custIARScrutinyList.get(custIARScrutinyList.size()-1);
				}
				if(null != custIARScrutiny) {
					
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
					
				}
				renderRequest.setAttribute("custIARScrutiny", custIARScrutiny);
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
	}

}
