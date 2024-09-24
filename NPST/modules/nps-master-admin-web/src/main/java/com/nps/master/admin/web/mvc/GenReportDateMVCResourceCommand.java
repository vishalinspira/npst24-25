package com.nps.master.admin.web.mvc;

import com.daily.average.service.model.IntermediaryList;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.nps.master.admin.web.constants.NpsMasterAdminWebPortletKeys;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.springframework.scheduling.support.CronSequenceGenerator;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NpsMasterAdminWebPortletKeys.NPSMASTERADMINWEB,
				"mvc.command.name=" + NpsMasterAdminWebPortletKeys.GEN_REPORT_DATE_MVC_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class GenReportDateMVCResourceCommand extends BaseMVCResourceCommand {
	Log LOG = LogFactoryUtil.getLog(getClass());

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date reportDate = ParamUtil.getDate(resourceRequest, "reportDate", dateFormat);
		LOG.info("reportDate---"+reportDate);
		LOG.info("reportDate---"+ParamUtil.getString(resourceRequest, "reportDate"));
		//
		Map<String, String[]> parms =  resourceRequest.getParameterMap();
		for (Map.Entry<String,String[]> entry : parms.entrySet()) {
			LOG.info(entry.getKey()+"---"+entry.getValue());
			
		}
		createNewReportLogs(reportDate);
		jsonObject.put("status", true);
		PrintWriter out = null;
		try {
			out = resourceResponse.getWriter();
			out.println(jsonObject.toString());
			out.flush();
		} catch(Exception e) {
			LOG.error(e.getMessage());
		} finally {
			out.close();
		}
	}
	public void createNewReportLogs(Date  reportDate) {
		LOG.info("Running Scheduler to Create Report Logs");
		
		List<ReportMaster> reportMasterList = ReportMasterLocalServiceUtil.getReportMasters(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		
		for(ReportMaster rm : reportMasterList) {
			try {
			CronSequenceGenerator generator = new CronSequenceGenerator(rm.getDueDate());
			Date nextRunDate = generator.next(reportDate);
			Date today = reportDate;
			Boolean checkMonth = false;
			if (rm.getReportType().equalsIgnoreCase("Monthly")){
				Calendar todayCal = Calendar.getInstance();
				todayCal.setTime(today);
				int currentMonth = todayCal.get(Calendar.MONTH);
				int currentYear = todayCal.get(Calendar.YEAR);
				
				Calendar reportCal = Calendar.getInstance();
				reportCal.setTime(nextRunDate);
				int reportMonth = reportCal.get(Calendar.MONTH);
				int reportYear = reportCal.get(Calendar.YEAR);
				LOG.info(rm+"  date "+nextRunDate +"(currentMonth>reportMonth)"+(currentMonth>=reportMonth)+"currentMonth"+currentMonth);
				checkMonth = currentMonth>=reportMonth &&  (currentYear == reportYear);
			}else if(rm.getReportType().equalsIgnoreCase("Quarterly") || rm.getReportType().equalsIgnoreCase("Annually") || rm.getReportType().equalsIgnoreCase("Half-Yearly")){
				Calendar todayCal = Calendar.getInstance();
				todayCal.setTime(today);
				int currentMonth = todayCal.get(Calendar.MONTH);
				int currentYear = todayCal.get(Calendar.YEAR);
				
				Calendar reportCal = Calendar.getInstance();
				reportCal.setTime(nextRunDate);
				int reportMonth = reportCal.get(Calendar.MONTH);
				int reportYear = reportCal.get(Calendar.YEAR);
				checkMonth = (currentMonth>=reportMonth) &&  (currentYear == reportYear);
				LOG.info("currentMonth : "+ currentMonth+"  reportMonth: "+reportMonth+ "current year: "+currentYear+"  report Year :"+reportYear+"  checkMonth: "+checkMonth +" reportmasterid : "+rm.getReportMasterId());
			}else {
				checkMonth = true;
			}
			if(checkMonth) {
				LOG.info(rm+"  date "+nextRunDate);
				List<IntermediaryList> intermediaryLists = IntermediaryListLocalServiceUtil.fetchIntermediaryListByTypeId(rm.getIntermediarytype());
				if(Validator.isNotNull(intermediaryLists) && intermediaryLists.size() >0) {
					for(IntermediaryList intermediaryList:intermediaryLists) {
						ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndReportDateAndIntermediaryid(rm.getReportMasterId(), nextRunDate, intermediaryList.getId());
						if(Validator.isNull(reportUploadLog)) {
							ReportUploadLogLocalServiceUtil.addReportUploadLogWithIntermediary(rm.getReportMasterId(), nextRunDate, null, 0L, 0L, false,intermediaryList.getIntermediaryname(),intermediaryList.getId());
						}
					}
				}else {
					ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndReportDate(rm.getReportMasterId(), nextRunDate);
					
					if(Validator.isNull(reportUploadLog)) {
						ReportUploadLogLocalServiceUtil.addReportUploadLog(rm.getReportMasterId(), nextRunDate, null, 0L, 0L, false);
					}
				}
//			LOG.info((rm.getReportMasterId() > 9 ? rm.getReportMasterId() : "0" + rm.getReportMasterId())  + " ::::: " + nextRunDate);
			
			}
		}catch (Exception e) {
			LOG.error("error while generating due date:  "+e.getMessage());
		}
		}
	}
}
