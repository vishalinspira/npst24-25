package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.ReportUploadLogNPST;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogNPSTLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSTUserUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.constants.NameMappingConstants;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_SUB_PENDING_REVIEW_REPORTS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class FetchSubPendingForReviewMVCResourceCommand   extends BaseMVCResourceCommand {
	private static final Log LOG = LogFactoryUtil.getLog(FetchSubPendingForReviewMVCResourceCommand.class);
	//private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		LOG.info("Calling FetchSubPendingForReviewMVCResourceCommand");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		//User user = PortalUtil.getUser(resourceRequest);
		Company company = PortalUtil.getCompany(resourceRequest);
        long companyId = company.getCompanyId();
        
		JSONObject jsonPendingDocs = getSubPendingForReview(companyId,resourceRequest, themeDisplay);
		try {
			resourceResponse.getWriter().println(jsonPendingDocs);
			resourceResponse.getWriter().flush();
		} catch (Exception e) {
			LOG.error("Error Sending Feedbacks" + e.getMessage());
		}
		
	}
	
	public JSONObject getSubPendingForReview(long companyId, ResourceRequest resourceRequest, ThemeDisplay themeDisplay) {
		JSONObject pendingReports = JSONFactoryUtil.createJSONObject();
		Boolean isCompleted = false;
		String department = ParamUtil.getString(resourceRequest, "department", NPSCompany.NPST);
		List<WorkflowTask> assigneToRole = new ArrayList<>();
		try {
			Role amRole = RoleLocalServiceUtil.getRole(companyId, 
					department.equalsIgnoreCase(NPSCompany.NPST)? NPSTRoleConstants.NPST_AM:"NPSTAM-"+department);
			List<WorkflowTask> assigneToAmRole =  WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, amRole.getRoleId(), isCompleted, 
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			assigneToRole.addAll(assigneToAmRole);
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		try {
			Role mgrRole = RoleLocalServiceUtil.getRole(companyId,"NPSTMGR-"+department);
			List<WorkflowTask> assigneToMgrRole =  WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, mgrRole.getRoleId(), isCompleted, 
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			assigneToRole.addAll(assigneToMgrRole);
		} catch (PortalException e) {
			LOG.error("Exception on getting role : "+e.getMessage());
		}
		try {
			for (WorkflowTask itr : assigneToRole) {
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				String entryClassName = String.valueOf(maps.get("entryClassName"));
				
				LOG.info("Class Name : "+entryClassName);
				LOG.info("Class PK : "+applicationId);
				
				OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
				String comment = npstUserUtil.getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
				String intermediaryName = StringPool.BLANK;
				String reportDate = StringPool.BLANK;
				String workflowContext = StringPool.BLANK;
				long reportUploadLogId = 0;
				
				ReportMaster reportMaster = null;
				DLFileEntry dlFileEntry = null;
				if(department.equalsIgnoreCase(NPSCompany.NPST)) {
					if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
						ReportUploadLogMaker reportUploadLogMaker  = ReportUploadLogMakerLocalServiceUtil.getReportUploadLogMaker(applicationId);
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogMaker.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogMaker.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogMaker.getReportDate());
							workflowContext = reportUploadLogMaker.getWorkflowContext();
							reportUploadLogId = reportUploadLogMaker.getReportUploadLogId();
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
						ReportUploadLogSupervisor reportUploadLogSupervisor  = ReportUploadLogSupervisorLocalServiceUtil.getReportUploadLogSupervisor(applicationId);
						if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogSupervisor.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogSupervisor.getReportDate());
							workflowContext = reportUploadLogSupervisor.getWorkflowContext();
							reportUploadLogId = reportUploadLogSupervisor.getReportUploadLogId();
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
						ReportUploadLogNPST reportUploadLogNPST  = ReportUploadLogNPSTLocalServiceUtil.getReportUploadLogNPST(applicationId);
						if(reportUploadLogNPST != null && reportUploadLogNPST.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNPST.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogNPST.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogNPST.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogNPST.getReportDate());
							workflowContext = reportUploadLogNPST.getWorkflowContext();
							reportUploadLogId = reportUploadLogNPST.getReportUploadLogId();
						}
					}
				}else if(department.equalsIgnoreCase(NPSCompany.PFM)) {
				
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
						ReportUploadLogPFM reportUploadLogPFM  = reportUploadLogPFMLocalService.getReportUploadLogPFM(applicationId);
						LOG.info("reportUploadLogPFM : "+reportUploadLogPFM);
						if(reportUploadLogPFM != null && reportUploadLogPFM.getFileEntryId()!=0) {
							LOG.info("File Entry Id : "+reportUploadLogPFM.getFileEntryId()+" Master id: "+reportUploadLogPFM.getReportMasterId());
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFM.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFM.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogPFM.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogPFM.getReportDate());
							workflowContext = reportUploadLogPFM.getWorkflowContext();
							reportUploadLogId = reportUploadLogPFM.getReportUploadLogId();
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
						ReportUploadLogPFMAM reportUploadLogPFMAM  = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(applicationId);
						if(reportUploadLogPFMAM != null && reportUploadLogPFMAM.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAM.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMAM.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogPFMAM.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogPFMAM.getReportDate());
							workflowContext = reportUploadLogPFMAM.getWorkflowContext();
							reportUploadLogId = reportUploadLogPFMAM.getReportUploadLogId();
						}
					}
					
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
						ReportUploadLogPFMCRA reportUploadLogPFMCRA  = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(applicationId);
						if(reportUploadLogPFMCRA != null && reportUploadLogPFMCRA.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCRA.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCRA.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogPFMCRA.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogPFMCRA.getReportDate());
							workflowContext = reportUploadLogPFMCRA.getWorkflowContext();
							reportUploadLogId = reportUploadLogPFMCRA.getReportUploadLogId();
						}
					}
				}else if(department.equalsIgnoreCase(NPSCompany.CRA)) {
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
						ReportUploadLogCRA reportUploadLogCRA  = reportUploadLogCRALocalService.getReportUploadLogCRA(applicationId);
						if(reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCRA.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCRA.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogCRA.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogCRA.getReportDate());
							workflowContext = reportUploadLogCRA.getWorkflowContext();
							reportUploadLogId = reportUploadLogCRA.getReportUploadLogId();
						}
					}
				}else if(department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
						ReportUploadLogGrievances reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
						if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievances.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogGrievances.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogGrievances.getReportDate());
							workflowContext = reportUploadLogGrievances.getWorkflowContext();
							reportUploadLogId = reportUploadLogGrievances.getReportUploadLogId();
						}
					}
				}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
						ReportUploadLogCustodian reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
						if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustodian.getReportMasterId());
							if(Validator.isNull(comment) || comment.equalsIgnoreCase("")) {
								comment = reportUploadLogCustodian.getRemarks();
							}
							reportDate = dateFormat.format(reportUploadLogCustodian.getReportDate());
							workflowContext = reportUploadLogCustodian.getWorkflowContext();
							reportUploadLogId = reportUploadLogCustodian.getReportUploadLogId();
						}
					}
				}
				
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				
				if(reportMaster != null) {
					LOG.info("reportMaster.getReportType() ::: "+reportMaster.getReportType());
					JSONArray jsonArray = pendingReports.getJSONArray(reportMaster.getReportType());
					if(Validator.isNull(jsonArray)) {
						jsonArray = JSONFactoryUtil.createJSONArray();
					}
					
					try {
						ReportUploadLog reportUploadLog = reportUploadLogLocalService.getReportUploadLog(reportUploadLogId);
						intermediaryName = reportUploadLog.getIntermediaryname();
					} catch (PortalException e) {
						LOG.error("Exception on fetching report upload log : "+e.getMessage(),e);
					}
					
					jsonObject.put("fileName", dlFileEntry.getFileName());
					jsonObject.put("userName", reportMaster.getUploaderRole().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
					jsonObject.put("createDate", dateFormat.format(dlFileEntry.getCreateDate()));
					jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
					jsonObject.put("status", WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase());
					jsonObject.put("moduleName", reportMaster.getReportName());
					jsonObject.put("workflowTaskId", itr.getWorkflowTaskId());
					jsonObject.put("remarks", comment);
					jsonObject.put("dueDate", reportDate);
					jsonObject.put("workflowContext", workflowContext);
					jsonObject.put("department", department);
		
					String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
					if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
						intermediaryName=intrmedName;
					}

					jsonObject.put("intermediaryName", intermediaryName);
					
					jsonArray.put(jsonObject);
					
					pendingReports.put(reportMaster.getReportType(), jsonArray);
					
				}
			}
		} catch (NumberFormatException e) {
			LOG.error(e);
		} catch (Exception e) {
			LOG.error(e);
		}
		return pendingReports;
	}
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private DLFileEntryLocalService dlFileEntryLocalService;
	
	@Reference
	private NPSTUserUtil npstUserUtil;
	
	@Reference
	private ReportUploadLogPFMLocalService reportUploadLogPFMLocalService;
	
	@Reference
	private ReportUploadLogPFMAMLocalService reportUploadLogPFMAMLocalService;
	
	@Reference
	private ReportUploadLogPFMCRALocalService reportUploadLogPFMCRALocalService;
	
	@Reference
	private ReportUploadLogCRALocalService reportUploadLogCRALocalService;
	
	@Reference
	private ReportUploadLogGrievancesLocalService reportUploadLogGrievancesLocalService;
	
	@Reference
	private ReportUploadLogCustodianLocalService reportUploadLogCustodianLocalService;
}
