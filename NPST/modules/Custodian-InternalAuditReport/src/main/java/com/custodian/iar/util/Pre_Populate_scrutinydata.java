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

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = Pre_Populate_scrutinydata.class)
public class Pre_Populate_scrutinydata {
	
	public void pre_populate_scrutiny_data(HttpServletRequest renderRequest) {
		
		try {
			
			List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(85, Boolean.FALSE);
			
			if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
				
				ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
				
				long reportUploadLogId = reportUploadLog.getReportUploadLogId();
				
				CustIARScrutiny custIARScrutiny = null;
				
				List<CustIARScrutiny> custIARScrutinyList = CustIARScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
				
				if(Validator.isNotNull(custIARScrutinyList) && custIARScrutinyList.size() > 0) {
					
					custIARScrutiny = custIARScrutinyList.get(custIARScrutinyList.size()-1);
				}
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(null != custIARScrutiny) {
					
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
					
				}
				renderRequest.setAttribute("custIARScrutiny", custIARScrutiny);
				
			}
		} catch (Exception e) {
			 _log.error(e);
		}
		
	}
	Log _log =LogFactoryUtil.getLog(getClass());
	

}
