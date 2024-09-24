package com.compliance.report.custodian.resource;

import com.daily.average.service.model.CustIARScrutiny;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.CustodianCompFormScrutiny;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.CustIARScrutinyLocalServiceUtil;
import com.daily.average.service.service.CustodianCompFormLocalServiceUtil;
import com.daily.average.service.service.CustodianCompFormScrutinyLocalService;
import com.daily.average.service.service.CustodianCompFormScrutinyLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(84, Boolean.FALSE);
			
			_log.info("reportUploadLogs :::::::: "+reportUploadLogs.toString());
			
			if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
				ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
				long reportUploadLogId = reportUploadLog.getReportUploadLogId();
				
				CustodianCompFormScrutiny custodianCompFormScrutiny = CustodianCompFormScrutinyLocalServiceUtil.fetchCustodianCompFormScrutinyByReportUploadLogId(reportUploadLogId);
						
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(null != custodianCompFormScrutiny) {
					renderRequest.setAttribute("reportMasterId", reportUploadLog.getReportMasterId());
					renderRequest.setAttribute("reportDate",  dateFormat.format(reportUploadLog.getReportDate()));
					renderRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				}
				renderRequest.setAttribute("custodianCompFormScrutiny", custodianCompFormScrutiny);
				
			}
		} catch (Exception e) {
			 _log.error(e);
		}
	}
	
	public JSONObject pre_populate_maker_data(HttpServletRequest renderRequest) {
		
		JSONObject obj = JSONFactoryUtil.createJSONObject();
		try {
			
			List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
			reportUploadLogs = ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(84, Boolean.FALSE);
			
			_log.info("reportUploadLogs :::::::: "+reportUploadLogs.toString());
			
			if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
				ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
				long reportUploadLogId = reportUploadLog.getReportUploadLogId();
				
				CustodianCompForm custodianCompForm = CustodianCompFormLocalServiceUtil.fetchCustodianCompForm(reportUploadLogId);

				if(null != custodianCompForm) {
					obj.put("prev_remarks_i_i", custodianCompForm.getRemarks_i_i());
					obj.put("prev_remarks_i_ii", custodianCompForm.getRemarks_i_ii());
					obj.put("prev_remarks_ii", custodianCompForm.getRemarks_ii());
					obj.put("prev_remarks_iii", custodianCompForm.getRemarks_iii());
					obj.put("prev_remarks_iv", custodianCompForm.getRemarks_iv());
					obj.put("prev_remarks_v", custodianCompForm.getRemarks_v());
					obj.put("prev_remarks_vi", custodianCompForm.getRemarks_vi());
					obj.put("prev_remarks_vii", custodianCompForm.getRemarks_vii());
					obj.put("prev_remarks_viii", custodianCompForm.getRemarks_viii());
					obj.put("prev_remarks_ix", custodianCompForm.getRemarks_xi());
					obj.put("prev_remarks_x", custodianCompForm.getRemarks_x());
					obj.put("prev_remarks_xi", custodianCompForm.getRemarks_xi());
					obj.put("prev_remarks_xii", custodianCompForm.getRemarks_xii());
					obj.put("prev_remarks_xiii", custodianCompForm.getRemarks_xiii());
				}
				
			}
		} catch (Exception e) {
			 _log.error(e);
		}
		return obj;
	}
	Log _log = LogFactoryUtil.getLog(getClass());

}
