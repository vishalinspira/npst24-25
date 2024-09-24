package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.daily.average.service.service.MnCompCertificateLocalServiceUtil;
import com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.NameMappingConstants;
import nps.common.service.util.WorkFlowTaskUtil;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_PFM_APPROVED_FORMS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class FetchPFMApprovedFormsMVCResource extends BaseMVCResourceCommand {
	private static final Log log = LogFactoryUtil.getLog(FetchPFMApprovedFormsMVCResource.class);
	//static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		// TODO Auto-generated method stub
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PrintWriter writer=resourceResponse.getWriter();
		writer.println(getApprovedForms(themeDisplay));
		writer.flush();
	}


	
	
	public JSONArray getApprovedForms(ThemeDisplay themeDisplay) throws NoSuchMethodException, SecurityException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray=getApprovedQuarterlyForms(jsonArray, themeDisplay);
		jsonArray=getMonthlyComplianceForms(jsonArray, themeDisplay);
		jsonArray=getAnnuallyComplianceForms(jsonArray, themeDisplay);
		jsonArray=getHyComplianceForms(jsonArray, themeDisplay);

		return jsonArray;	
	}
	
	private  JSONArray getAnnuallyComplianceForms(JSONArray jsonArray,ThemeDisplay themeDisplay) {
		List<AnnualCompCertificate>anCompCertificates=AnnualCompCertificateLocalServiceUtil.getAnnualCompCertificates(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		log.info("anCompCertificate size : "+anCompCertificates.size());
		for(AnnualCompCertificate anCompCertificate:anCompCertificates) {
			try {
				log.info("AnnualCompCertificate reportupload logId : "+anCompCertificate.getReportUploadLogId());
				JSONObject jsonObject=getWorkflowRecord(AnnualCompCertificate.class.getName(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), anCompCertificate.getReportUploadLogId());
				if(Validator.isNotNull(jsonObject)) {
					jsonArray.put(jsonObject);	
				}
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.debug("JSON Array :  "+jsonArray.toString());
		return jsonArray;
	}
	
	
	private  JSONArray getHyComplianceForms(JSONArray jsonArray,ThemeDisplay themeDisplay) {
		List<PFM_hy_comp_cert>hyCompCerts=PFM_hy_comp_certLocalServiceUtil.getPFM_hy_comp_certs(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		log.info("PFM_hy_comp_cert size : "+hyCompCerts.size());
		for(PFM_hy_comp_cert hyCompCert:hyCompCerts) {
			try {
				log.info("PFM_hy_comp_cert reportupload logId : "+hyCompCert.getReportUploadLogId());
				JSONObject jsonObject=getWorkflowRecord(PFM_hy_comp_cert.class.getName(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), hyCompCert.getReportUploadLogId());
				if(Validator.isNotNull(jsonObject)) {
					jsonArray.put(jsonObject);	
				}
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.debug("JSON Array :  "+jsonArray.toString());
		return jsonArray;
	}
	
	
	private  JSONArray getMonthlyComplianceForms(JSONArray jsonArray,ThemeDisplay themeDisplay) {
		List<MnCompCertificate>mnCompCertificates=MnCompCertificateLocalServiceUtil.getMnCompCertificates(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		log.info("mnCompCertificate size : "+mnCompCertificates.size());
		for(MnCompCertificate mnCompCertificate:mnCompCertificates) {
			try {
				log.info("mnCompCertificate reportupload logId : "+mnCompCertificate.getReportUploadLogId());
				JSONObject jsonObject=getWorkflowRecord(MnCompCertificate.class.getName(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), mnCompCertificate.getReportUploadLogId());
				if(Validator.isNotNull(jsonObject)) {
					jsonArray.put(jsonObject);	
				}
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.debug("JSON Array :  "+jsonArray.toString());
		return jsonArray;
	}
	
	private  JSONArray getApprovedQuarterlyForms(JSONArray jsonArray,ThemeDisplay themeDisplay)  {
		List<InputQuarterlyInterval>inputQuarterlyIntervals=InputQuarterlyIntervalLocalServiceUtil.getInputQuarterlyIntervals(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		log.info("inputQuarterlyIntervals size : "+inputQuarterlyIntervals.size());
		for(InputQuarterlyInterval inputQuarterlyInterval:inputQuarterlyIntervals) {
			try {
				log.info("inputQuarterlyInterval reportupload logId : "+inputQuarterlyInterval.getReportUploadLogId());
				JSONObject jsonObject=getWorkflowRecord(InputQuarterlyInterval.class.getName(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), inputQuarterlyInterval.getReportUploadLogId());
				if(Validator.isNotNull(jsonObject)) {
					jsonArray.put(jsonObject);	
				}
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.debug("JSON Array :  "+jsonArray.toString());
		return jsonArray;
	}
	
	
	
	/**
	 * 
	 * @param ermInformation
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws PortalException 
	 */
	public  static JSONObject getWorkflowRecord(String className,long companyId,long groupId,long reportUploadlogId) throws PortalException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		
		List<Integer> logTypes_assign = new ArrayList<Integer>();
		logTypes_assign.add(WorkflowLog.TASK_ASSIGN);
		logTypes_assign.add(WorkflowLog.TASK_COMPLETION);
		List<WorkflowLog> workflowLogs_assign=WorkFlowTaskUtil.getWorkflowLogs(companyId, groupId, className,reportUploadlogId,logTypes_assign);
		
		if (workflowLogs_assign.size() > 0) {
			workflowLogs_assign=workflowLogs_assign.stream().sorted(Comparator.comparing(WorkflowLog::getCreateDate).reversed()).collect(Collectors.toList());
		}		
		WorkflowTask workflowTask=WorkflowTaskManagerUtil.getWorkflowTask(companyId, workflowLogs_assign.get(0).getWorkflowTaskId());
		List<KaleoLog> kaleoLogsOne=WorkFlowTaskUtil.getKaleoLogs(companyId, workflowTask.getWorkflowInstanceId(), "TASK_COMPLETION");

		String remark=WorkFlowTaskUtil.getComment(kaleoLogsOne);
		if(workflowTask.isCompleted()) {
			//ReportUploadLogPFM reportUploadLogPFM=null;
			ReportUploadLog reportUploadLog = null;
			List<ReportUploadFileLog> reportUploadFileLogs=null;
			ReportMaster reportMaster =null;
			try {
				reportUploadLog = ReportUploadLogLocalServiceUtil.fetchReportUploadLog(reportUploadlogId);
				reportUploadFileLogs=ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadlogId);
				reportMaster= ReportMasterLocalServiceUtil.getReportMaster(reportUploadLog.getReportMasterId());
				DLFileEntry dlFileEntry  =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadFileLogs.get(reportUploadFileLogs.size()-1).getFileEntryId());
				if(dlFileEntry!=null) {
					jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
					jsonObject.put("userName", dlFileEntry.getUserName());
					jsonObject.put("createDate", dateFormat.format(dlFileEntry.getCreateDate()));
				}else {
					jsonObject.put("createDate","");
					jsonObject.put("url", "");
					jsonObject.put("userName", "");
				}
			} catch (Exception e) {
				log.info("Exception in getting reportUploadLog:::" + e.getMessage());
			}
			jsonObject.put("dueDate", dateFormat.format(reportUploadLog.getReportDate()));
			jsonObject.put("fileName", reportMaster.getReportName());
			jsonObject.put("reportUploadLogId",reportUploadlogId);
			jsonObject.put("reportMasterId", reportMaster.getReportMasterId());
			jsonObject.put("status", "Approved");
			
			jsonObject.put("dueDate", dateFormat.format(reportUploadLog.getReportDate()));
			
			jsonObject.put("workflowInstanceId", workflowTask.getWorkflowInstanceId());
			String mediaryName=reportUploadLog.getIntermediaryname();
			String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(mediaryName);
			if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
				mediaryName=intrmedName;
			}

			jsonObject.put("intermediaryName", mediaryName);
			//jsonObject.put("intermediaryName", reportUploadLog.getIntermediaryname());
			
			jsonObject.put("remarks", remark);
		}else {
			return null;
		}
		return jsonObject;
	}

}
