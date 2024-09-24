package com.nps.master.admin.web.mvc;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.Validator;
import com.nps.master.admin.web.constants.NpsMasterAdminWebPortletKeys;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.springframework.scheduling.support.CronSequenceGenerator;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NpsMasterAdminWebPortletKeys.NPSMASTERADMINWEB,
				"mvc.command.name=" + NpsMasterAdminWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class CheckReportsMVCResourceCommand extends BaseMVCResourceCommand {

	private static final Log LOG = LogFactoryUtil.getLog(CheckReportsMVCResourceCommand.class);
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		//List<ReportMaster> reportMasterList = ReportMasterLocalServiceUtil.getReportMasters(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		
		//for(ReportMaster rm : reportMasterList) {
		
			
			//CronSequenceGenerator generator = new CronSequenceGenerator(rm.getDueDate());
			//Date nextRunDate = generator.next(new Date());
			
//			System.err.println((rm.getReportMasterId() > 9 ? rm.getReportMasterId() : "0" + rm.getReportMasterId())  + " ::::: " + nextRunDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		List<ReportUploadLog> reportUploadLogs = ReportUploadLogLocalServiceUtil.getReportUploadLogs(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		for (ReportUploadLog reportUploadLog : reportUploadLogs) {
			ReportMaster rm = ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLog.getReportMasterId());
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			if(Validator.isNotNull(reportUploadLog) && Validator.isNotNull(rm) && reportUploadLog.getReportDate().getTime() > dateFormat.parse("31-03-2023").getTime()) {
				jsonObject.put("reportUploadLogId", reportUploadLog.getReportUploadLogId());
				jsonObject.put("reportName", rm.getReportName());
				jsonObject.put("isUploaded", reportUploadLog.getUploaded_i()==0 ? "Uploaded" : "Pending");
				jsonObject.put("reportDueDate", dateFormat.format(reportUploadLog.getReportDate()));
				jsonObject.put("reportType", rm.getReportType());
				jsonObject.put("department", rm.getDepartment());
				jsonObject.put("intermediaryName", (Validator.isNotNull(rm.getCra()))?rm.getCra():reportUploadLog.getIntermediaryname());
				
				jsonArray.put(jsonObject);
			}
			
		}
		
		//getDateWithoutTime(new Date());
		
		PrintWriter out = null;
		try {
			out = resourceResponse.getWriter();
			out.println(jsonArray.toString());
			out.flush();
		} catch(Exception e) {
			LOG.error(e.getMessage());
		} finally {
			out.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public Date getDateWithoutTime(Date date) {
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		
		System.err.println("Date Testing ::::: " + date);
		
		return date;
	}

}
