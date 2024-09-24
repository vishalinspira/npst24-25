package com.nps.report.status.util;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.model.CustIAR;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.DAR;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogCustAMPFRDA;
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.ReportUploadLogGrievAMPFRDA;
import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.model.ReportUploadLogPFMCustodian;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.AnnualCompCertificateLocalService;
import com.daily.average.service.service.CustAnnualAuditReportLocalService;
import com.daily.average.service.service.CustIARLocalService;
import com.daily.average.service.service.CustodianCompFormLocalService;
import com.daily.average.service.service.DARLocalService;
import com.daily.average.service.service.InputQuarterlyIntervalLocalService;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalService;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalServiceUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.util.CommonRoleService;

@Component(immediate = true, service = NPSTUserUtil.class)
public class NPSTUserUtil {
	
	private static final Log LOG = LogFactoryUtil.getLog(NPSTUserUtil.class);
	
	//private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
	
	public JSONArray getPendingForReviewList(PortletRequest portletRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long assigneeUserId = themeDisplay.getUserId();
		boolean isCompleted = false;
		
		Company company = PortalUtil.getCompany(portletRequest);
        long companyId = company.getCompanyId();
		
        String folderName = ParamUtil.getString(portletRequest, "durationType", "Daily");
        
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		
		List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId, assigneeUserId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		
		for (WorkflowTask itr : assigneToMe) {
			String url = getWorkflowUrl(portletRequest, themeDisplay);
			Map<String, Serializable> maps = itr.getOptionalAttributes();
			long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
			String entryClassName = String.valueOf(maps.get("entryClassName"));
			ReportUploadLogMaker reportUploadLogMaker = null;
			ReportUploadLogSupervisor reportUploadLogSupervisor = null;
			ReportMaster reportMaster = null;
			DLFileEntry dlFileEntry = null;
			
			if(entryClassName.equalsIgnoreCase("com.daily.average.service.model.ReportUploadLogMaker")) {
				reportUploadLogMaker  = ReportUploadLogMakerLocalServiceUtil.getReportUploadLogMaker(applicationId);
				if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId() != 0) {
					dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
					reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogMaker.getReportMasterId());
				}
			} else if(entryClassName.equalsIgnoreCase("com.daily.average.service.model.ReportUploadLogSupervisor")) {
				reportUploadLogSupervisor  = ReportUploadLogSupervisorLocalServiceUtil.getReportUploadLogSupervisor(applicationId);
				if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId() != 0) {
					dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
					reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
				}
			}
			
			OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
			List<Integer> logTypesOne = new ArrayList<>();
			logTypesOne.add(KaleoLogUtil.convert("TASK_COMPLETION"));
			List<KaleoLog> kaleoLogsOne = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesOne, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
			
			List<Integer> logTypesTwo = new ArrayList<>();
			logTypesTwo.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
			List<KaleoLog> kaleoLogsTwo = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesTwo, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
			
			String comment = StringPool.BLANK;
			if(kaleoLogsOne.size() > 0) {
				comment = kaleoLogsOne.get(0).getComment();
			}
			
			String userName = "NA";
			if(kaleoLogsTwo.size() >= 2) {
				if(kaleoLogsTwo.get(1).getCurrentAssigneeClassName().equals("com.liferay.portal.kernel.model.User")) {
					userName = kaleoLogsTwo.get(1).getUserName();
				} else if(kaleoLogsTwo.size() >= 3 && kaleoLogsTwo.get(2).getCurrentAssigneeClassName().equals("com.liferay.portal.kernel.model.User")) {
					userName = kaleoLogsTwo.get(2).getUserName();
				}
			}
			
			if(userName.equals("NA") && !kaleoLogsTwo.isEmpty()) {
				userName = kaleoLogsTwo.get(0).getUserName();
			}
			
			ReportUploadLog reportUploadLog = null;
			String statusKey = null;
			try {
				reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
				statusKey = reportUploadLog.getStatus_();
			} catch (Exception e) {
				LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
			}
			
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			if(reportUploadLogMaker != null && reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
				jsonObject.put("fileName", dlFileEntry.getFileName());
				jsonObject.put("userName", userName);
				jsonObject.put("createDate", DATE_FORMAT.format(dlFileEntry.getCreateDate()));
				jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
				jsonObject.put("dueDate", DATE_FORMAT.format(dlFileEntry.getCreateDate()));
				jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
				jsonObject.put("moduleName", reportMaster.getReportName());
				jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
				jsonObject.put("remarks", comment);
				jsonArray.put(jsonObject);
			} else if(reportUploadLogSupervisor != null && reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
				jsonObject.put("fileName", dlFileEntry.getFileName());
				jsonObject.put("userName", userName);
				jsonObject.put("createDate", DATE_FORMAT.format(dlFileEntry.getCreateDate()));
				jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
				jsonObject.put("dueDate", DATE_FORMAT.format(dlFileEntry.getCreateDate()));
				jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
				jsonObject.put("moduleName", reportMaster.getReportName());
				jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
				jsonObject.put("remarks", comment);
				jsonArray.put(jsonObject);
			}
			
		}
		
		return jsonArray;
	}
	
	public String getIntermediaryName(ThemeDisplay themeDisplay) {
		String intermediaryName = StringPool.BLANK;
		List<Role> roles = RoleLocalServiceUtil.getUserRoles(themeDisplay.getUserId());
		for(Role role:roles) {
			if(role.getName().equalsIgnoreCase("SBI") || role.getName().equalsIgnoreCase("HDFC") || role.getName().equalsIgnoreCase("UTI") ||
					role.getName().equalsIgnoreCase("LIC") || role.getName().equalsIgnoreCase("KOTAK") || role.getName().equalsIgnoreCase("ABS") ||
					role.getName().equalsIgnoreCase("ICICI") || role.getName().equalsIgnoreCase("NCRA") || role.getName().equalsIgnoreCase("KCRA") || role.getName().equalsIgnoreCase("CAMS") ||
					role.getName().equalsIgnoreCase("TATA") || role.getName().equalsIgnoreCase("MLP") || role.getName().equalsIgnoreCase("APF") || role.getName().equalsIgnoreCase("DSP")
					) {
				intermediaryName = role.getName();
			}
		}
		return intermediaryName;
	}

	public Set<WorkflowTask> getWorkflowTaskSubmmitedToNPS(long companyId, boolean isCompleted, String companyName, boolean isPFMNonNpsUser){
		Set<WorkflowTask> workflowTasks = new HashSet<>();
		Role amRole = null;
		Role dgmRole = null;
		Role gmRole = null;
		Role mgrRole = null;
		Role faRole = null;
		try {
			if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
				amRole = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.NPST_AM);
				dgmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.NPST_DGM);
				gmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.NPST_GM);
			}else if(companyName.equalsIgnoreCase(NPSCompany.PFM) || (companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN) && isPFMNonNpsUser)) {
				amRole = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.PFM_AM);
				dgmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.PFM_DGM);
				gmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.PFM_GM);
				mgrRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.PFM_MGR);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				amRole = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.CRA_AM);
				dgmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CRA_DGM);
				gmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CRA_GM);
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				amRole = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.GRIEVANCES_AM);
				dgmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.GRIEVANCES_DGM);
				gmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.GRIEVANCES_GM);
				mgrRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.GRIEVANCES_MGR);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				amRole = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.CUSTODIAN_AM);
				dgmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_DGM);
				gmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_GM);
				mgrRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_MGR);
				//faRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_FA);
			}
			
			if(Validator.isNotNull(amRole)) {
				List<WorkflowTask> assigneToMe1 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, amRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe1);
			}
			
			if(Validator.isNotNull(dgmRole)) {
				List<WorkflowTask> assigneToMe2 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, dgmRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe2);
			}
			
			if(Validator.isNotNull(gmRole)) {
				List<WorkflowTask> assigneToMe3 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, gmRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe3);
			}
			
			if(Validator.isNotNull(mgrRole)) {
				List<WorkflowTask> assigneToMe3 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, mgrRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe3);
			}
			
			if(Validator.isNotNull(faRole)) {
				List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, faRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe);
			}
			
		}catch (Exception e) {
			LOG.error("Exception : "+e.getMessage(),e);
		}
		
		return workflowTasks;
	}
	public Set<WorkflowTask> getWorkflowTaskSubmmitedToNPS(long companyId, boolean isCompleted, boolean isPFMNonNpsUser){
		return getWorkflowTaskSubmmitedToNPS(companyId, isCompleted, NPSCompany.NPST, isPFMNonNpsUser);
	}
	
	public JSONArray getNPSTUsersTasks(PortletRequest portletRequest, boolean isMakerOrChecker) throws Exception {
		LOG.info("getNPSTUsersTasks calling  ");
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isCompleted = false;
        long companyId = themeDisplay.getCompanyId();
        
        String folderName = ParamUtil.getString(portletRequest, "durationType", "Daily");
        LOG.info("folderName : "+folderName);
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		Set<WorkflowTask> workflowTasks = getWorkflowTaskSubmmitedToNPS(companyId, isCompleted, Boolean.FALSE);
		
		Date dueDate = null;
		
		List<Long> workflowInstanceIds = new ArrayList<Long>();
		
		for (WorkflowTask itr : workflowTasks) {
			String url = getWorkflowUrl(portletRequest, themeDisplay);
			Map<String, Serializable> maps = itr.getOptionalAttributes();
			long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
			String entryClassName = String.valueOf(maps.get("entryClassName"));
			ReportMaster reportMaster = null;
			DLFileEntry dlFileEntry = null;
			workflowInstanceIds.add(applicationId);
			
			LOG.info("entryClassName : "+entryClassName+" instance id : "+applicationId);
		
			if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
				ReportUploadLogMaker reportUploadLogMaker  = ReportUploadLogMakerLocalServiceUtil.getReportUploadLogMaker(applicationId);
				if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId() != 0) {
					dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
					reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogMaker.getReportMasterId());
					dueDate = reportUploadLogMaker.getReportDate();
				}
			} 
			if(!isMakerOrChecker) {
				if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
					ReportUploadLogSupervisor reportUploadLogSupervisor  = ReportUploadLogSupervisorLocalServiceUtil.getReportUploadLogSupervisor(applicationId);
					if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId() != 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
						dueDate = reportUploadLogSupervisor.getReportDate();
					}
				}
			}
			
			OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
			List<Integer> logTypesOne = new ArrayList<>();
			logTypesOne.add(KaleoLogUtil.convert("TASK_COMPLETION"));
			List<KaleoLog> kaleoLogsOne = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesOne, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
			
			List<Integer> logTypesTwo = new ArrayList<>();
			logTypesTwo.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
			List<KaleoLog> kaleoLogsTwo = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesTwo, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
			
			String comment = StringPool.BLANK;
			if(kaleoLogsOne.size() > 0) {
				comment = kaleoLogsOne.get(0).getComment();
			}
			
			String userName = "NA";
			if(kaleoLogsTwo.size() >= 2) {
				if(kaleoLogsTwo.get(1).getCurrentAssigneeClassName().equals(User.class.getName())) {
					userName = kaleoLogsTwo.get(1).getUserName();
				} else if(kaleoLogsTwo.size() >= 3 && kaleoLogsTwo.get(2).getCurrentAssigneeClassName().equals(User.class.getName())) {
					userName = kaleoLogsTwo.get(2).getUserName();
				}
			}
			
			if(userName.equals("NA") && !kaleoLogsTwo.isEmpty()) {
				userName = kaleoLogsTwo.get(0).getUserName();
			}
			
			ReportUploadLog reportUploadLog = null;
			String statusKey = null;
			try {
				reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
				statusKey = reportUploadLog.getStatus_();
			} catch (Exception e) {
				LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
			}
			
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
				jsonObject.put("fileName", dlFileEntry.getFileName());
				jsonObject.put("userName", userName);
				jsonObject.put("submittedBy", userName);
				jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
				jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
				jsonObject.put("dueDate", Validator.isNotNull(dueDate)? DATE_FORMAT.format(dueDate):StringPool.BLANK);
				jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
				jsonObject.put("moduleName", reportMaster.getReportName());
				jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
				jsonObject.put("remarks", comment);
				jsonArray.put(jsonObject);
			}
		}
		
		LOG.info("workflowInstanceIds :::::::::::::::::::::::::::"+workflowInstanceIds);
		
		return jsonArray;
	}
	
	public JSONArray getSubmittedToPFMUser(PortletRequest portletRequest, boolean isMakerOrChecker, boolean isNonNpsUser,String checkIntermediaryName) throws Exception{

		LOG.info("getNPSTUsersTasks calling  ");
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isCompleted = false;
        long companyId = themeDisplay.getCompanyId();
        
        String folderName = ParamUtil.getString(portletRequest, "durationType", "Daily");
        LOG.info("folderName : "+folderName);
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		Set<WorkflowTask> workflowTasks = getWorkflowTaskSubmmitedToNPS(companyId, isCompleted, NPSCompany.PFM, isMakerOrChecker);
		
		LOG.info("workflowTasks : "+workflowTasks);
		Date dueDate = null;
		for (WorkflowTask itr : workflowTasks) {
			String url = getWorkflowUrl(portletRequest, themeDisplay);
			Map<String, Serializable> maps = itr.getOptionalAttributes();
			long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
			String entryClassName = String.valueOf(maps.get("entryClassName"));
			ReportMaster reportMaster = null;
			DLFileEntry dlFileEntry = null;
			
			String intermediaryName = StringPool.BLANK;
			
			if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
				ReportUploadLogPFM reportUploadLogPfm  = reportUploadLogPFMLocalService.getReportUploadLogPFM(applicationId);
				if(reportUploadLogPfm != null && reportUploadLogPfm.getFileEntryId() != 0) {
					dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogPfm.getFileEntryId());
					reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPfm.getReportMasterId());
					intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPfm.getReportUploadLogId());
					dueDate = reportUploadLogPfm.getReportDate();
				}
			}
			
			/* For Input Quartely Interval */
			try {
				if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
					//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
					InputQuarterlyInterval inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
					if(inputQuarterlyInterval != null) {
						dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(inputQuarterlyInterval.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(inputQuarterlyInterval.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(inputQuarterlyInterval.getReportUploadLogId());
						dueDate = inputQuarterlyInterval.getReportDate();
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting InputQuarterlyInterval"+ e.getMessage());
			}
			
			/* For MnCompCertificate */
			try {
				if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
					//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
					MnCompCertificate mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
					if(mnCompCertificate != null) {
					//	dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(mnCompCertificate.getAnnexure_h());
						dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(mnCompCertificate.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(mnCompCertificate.getReportUploadLogId());
						dueDate = mnCompCertificate.getReportDate();
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting MnCompCertificate"+ e.getMessage());
			}
			
			/* For AnnualCompCertificate */
			try {
				if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
					//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
					AnnualCompCertificate annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
					if(annualCompCertificate!= null) {
						dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(annualCompCertificate.getAnnexurev());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(annualCompCertificate.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(annualCompCertificate.getReportUploadLogId());
						dueDate = annualCompCertificate.getReportDate();
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting AnnualCompCertificate"+ e.getMessage());
			}
			
			
			/* For QtrStewardshipReport */
			try {
				if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
					LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
					QtrStewardshipReport qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(applicationId);
					if(qtrStewardshipReport!= null) {
						//dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(qtrStewardshipReport.getAnnexure_c());
						dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(qtrStewardshipReport.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(qtrStewardshipReport.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(qtrStewardshipReport.getReportUploadLogId());
						dueDate = qtrStewardshipReport.getReportDate();
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting QtrStewardshipReport"+ e.getMessage());
			}
			
			
			/* For NPA Development */
			try {
				if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
					LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
					MnNpaDevelopment mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
					if(mnNpaDevelopment!= null) {
						dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(mnNpaDevelopment.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(mnNpaDevelopment.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(mnNpaDevelopment.getReportUploadLogId());
						dueDate = mnNpaDevelopment.getReportDate();
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting MnNpaDevelopment"+ e.getMessage());
			}
			
			
			/* Report Upload By PFM-AM-PFRDA */
			try {
				if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
					ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
					if(reportUploadLogPFMAMPFRDA != null ) {
						dlFileEntry =  null;
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
						//comment = reportUploadLogPFMAMPFRDA.getRemarks();
						dueDate = reportUploadLogPFMAMPFRDA.getReportDate();
						//createDate = reportUploadLogPFMAMPFRDA.getStatusDate();
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMAMPFRDA.getReportUploadLogId());
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting MnNpaDevelopment"+ e.getMessage());
			}
			
			/* Detail audit report */
			try {
				if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
					DAR dar = null;
					try {
						dar =darLocalService.getDAR(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(dar != null ) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(dar.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(dar.getReportMasterId());
						dueDate = dar.getReportDate();
						intermediaryName = npsDashboardUtil.getIntermediaryName(dar.getReportUploadLogId());
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting MnNpaDevelopment"+ e.getMessage());
			}
			
			/* PFM Quarterly Internal Audit Report */
			try {
				if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
					Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = null;
					try {
						pfm_Qr_Internal_Audit_Report =pfm_Qr_Internal_Audit_ReportLocalService.getPfm_Qr_Internal_Audit_Report(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(pfm_Qr_Internal_Audit_Report != null ) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(pfm_Qr_Internal_Audit_Report.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(pfm_Qr_Internal_Audit_Report.getReportMasterId());
						dueDate = pfm_Qr_Internal_Audit_Report.getReportDate();
						intermediaryName = npsDashboardUtil.getIntermediaryName(pfm_Qr_Internal_Audit_Report.getReportUploadLogId());
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting MnNpaDevelopment"+ e.getMessage());
			}
			
			/* PFM Quarterly Internal Audit Report */
			try {
				if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
					PFM_hy_comp_cert pfm_hy_comp_cert = null;
					try {
						pfm_hy_comp_cert =pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(pfm_hy_comp_cert != null ) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(pfm_hy_comp_cert.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(pfm_hy_comp_cert.getReportMasterId());
						dueDate = pfm_hy_comp_cert.getReportDate();
						intermediaryName = npsDashboardUtil.getIntermediaryName(pfm_hy_comp_cert.getReportUploadLogId());
					}
				}
			} catch (Exception e) {
				LOG.error("Exception in getting MnNpaDevelopment"+ e.getMessage());
			}
			
			
			/*
			 * Not insuded AM reports as this is method used only for non npst users like (maker, checker, and supervisor) which does not
			 * have permission to see what report submitted by AM user to next level.
			 */
			LOG.info("isMakerOrChecker::" + isMakerOrChecker);
			if(!isMakerOrChecker) {	
				if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
					ReportUploadLogPFMCRA reportUploadLogPfmCra  = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(applicationId);
					if(reportUploadLogPfmCra != null && reportUploadLogPfmCra.getFileEntryId() != 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogPfmCra.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPfmCra.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPfmCra.getReportUploadLogId());
						dueDate = reportUploadLogPfmCra.getReportDate();
					}
				}
			}
			
			OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
			List<Integer> logTypesOne = new ArrayList<>();
			logTypesOne.add(KaleoLogUtil.convert("TASK_COMPLETION"));
			List<KaleoLog> kaleoLogsOne = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesOne, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
			
			List<Integer> logTypesTwo = new ArrayList<>();
			logTypesTwo.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
			List<KaleoLog> kaleoLogsTwo = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesTwo, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
			
			String comment = StringPool.BLANK;
			if(kaleoLogsOne.size() > 0) {
				comment = kaleoLogsOne.get(0).getComment();
			}
			
			String userName = "NA";
			if(kaleoLogsTwo.size() >= 2) {
				if(kaleoLogsTwo.get(1).getCurrentAssigneeClassName().equals(User.class.getName())) {
					userName = kaleoLogsTwo.get(1).getUserName();
				} else if(kaleoLogsTwo.size() >= 3 && kaleoLogsTwo.get(2).getCurrentAssigneeClassName().equals(User.class.getName())) {
					userName = kaleoLogsTwo.get(2).getUserName();
				}
			}
			
			if(userName.equals("NA") && !kaleoLogsTwo.isEmpty()) {
				userName = kaleoLogsTwo.get(0).getUserName();
			}
			
			ReportUploadLog reportUploadLog = null;
			String statusKey = null;
			try {
				reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
				statusKey = reportUploadLog.getStatus_();
			} catch (Exception e) {
				LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
			}
			
			if(isNonNpsUser && intermediaryName.equalsIgnoreCase(checkIntermediaryName) && Validator.isNotNull(checkIntermediaryName)) 
			{
						JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
						if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
							if(dlFileEntry!=null) {
								jsonObject.put("fileName", dlFileEntry.getFileName());
								jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
								jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
							}else {
								jsonObject.put("fileName", "");
								jsonObject.put("url", "");
								jsonObject.put("status", "");
							}
							jsonObject.put("userName", userName);
							jsonObject.put("submittedBy", userName);
							jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
							jsonObject.put("dueDate", Validator.isNotNull(dueDate) ? DATE_FORMAT.format(dueDate):StringPool.BLANK);
							jsonObject.put("moduleName", reportMaster.getReportName());
							jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
							jsonObject.put("remarks", comment);
							jsonObject.put("intermediaryName", intermediaryName);
							jsonArray.put(jsonObject);
						}
			}else if(isNonNpsUser && Validator.isNull(checkIntermediaryName)) {
						JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
						if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
							if(dlFileEntry!=null) {
								jsonObject.put("fileName", dlFileEntry.getFileName());
								jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
								jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
							}else {
								jsonObject.put("fileName", "");
								jsonObject.put("url", "");
								jsonObject.put("status", "");
							}
							jsonObject.put("userName", userName);
							jsonObject.put("submittedBy", userName);
							jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
							jsonObject.put("dueDate", Validator.isNotNull(dueDate) ? DATE_FORMAT.format(dueDate):StringPool.BLANK);
							jsonObject.put("moduleName", reportMaster.getReportName());
							jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
							jsonObject.put("remarks", comment);
							jsonObject.put("intermediaryName", intermediaryName);
							jsonArray.put(jsonObject);
						}
			}else if(!isNonNpsUser && Validator.isNull(checkIntermediaryName)){
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
							if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
								if(dlFileEntry!=null) {
									jsonObject.put("fileName", dlFileEntry.getFileName());
									jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
									jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
								}else {
									jsonObject.put("fileName", "");
									jsonObject.put("url", "");
									jsonObject.put("status", "");
								}
								jsonObject.put("userName", userName);
								jsonObject.put("submittedBy", userName);
								jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
								jsonObject.put("dueDate", Validator.isNotNull(dueDate) ? DATE_FORMAT.format(dueDate):StringPool.BLANK);
								jsonObject.put("moduleName", reportMaster.getReportName());
								jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
								jsonObject.put("remarks", comment);
								jsonObject.put("intermediaryName", intermediaryName);
								jsonArray.put(jsonObject);
							}
			}
			
		}
		
		return jsonArray;
	
	}
	
	@SuppressWarnings("deprecation")
	public static String getWorkflowUrl(PortletRequest portletRequest, ThemeDisplay themeDisplay) throws Exception {
        PortletURL renderURL = PortletURLFactoryUtil.create(portletRequest, PortletKeys.MY_WORKFLOW_TASK, PortletRequest.RENDER_PHASE);
        renderURL.setWindowState(WindowState.NORMAL);
        renderURL.setPortletMode(PortletMode.VIEW);
        renderURL.setParameter("mvcPath", "/edit_workflow_task.jsp");
        renderURL.setParameter("backURL", themeDisplay.getURLCurrent());
        renderURL.setParameter("workflowTaskId", "");

        String workflowUrl = renderURL.toString();
        String[] url = workflowUrl.split("\\?");
        String panelUrl = "/web" + themeDisplay.getScopeGroup().getFriendlyURL() + "/manage";
        String queryParam = url[1];

        workflowUrl = panelUrl + "?" + queryParam;
        return workflowUrl;
    }
	
	public JSONArray getSubmittedToCRAUser(PortletRequest portletRequest, boolean isMakerOrChecker, String companyName, boolean isNonNpsUser,String checkIntermediaryName) throws Exception{

		LOG.info("getNPSTUsersTasks calling  ");
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isCompleted = false;
        long companyId = themeDisplay.getCompanyId();
        
        String folderName = ParamUtil.getString(portletRequest, "durationType", "Daily");
        LOG.info("folderName : "+folderName);
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		Set<WorkflowTask> workflowTasks = getWorkflowTaskSubmmitedToNPS(companyId, isCompleted, NPSCompany.CRA, Boolean.FALSE);
		workflowTasks.addAll(getWorkflowTaskSubmmitedToNPS(companyId, isCompleted, NPSCompany.GRIEVANCES, Boolean.FALSE));
		
		LOG.info("workflowTasks : "+workflowTasks);
		Date dueDate = null;
		for (WorkflowTask itr : workflowTasks) {
			String url = getWorkflowUrl(portletRequest, themeDisplay);
			Map<String, Serializable> maps = itr.getOptionalAttributes();
			long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
			String entryClassName = String.valueOf(maps.get("entryClassName"));
			OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
			String comment = getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
			String userName = getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
			
			ReportMaster reportMaster = null;
			DLFileEntry dlFileEntry = null;
			
			String intermediaryName = StringPool.BLANK;
			if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
					ReportUploadLogCRA reportUploadLogCRA  = reportUploadLogCRALocalService.getReportUploadLogCRA(applicationId);
					if(reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId() != 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogCRA.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogCRA.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogCRA.getReportUploadLogId());
						dueDate = reportUploadLogCRA.getReportDate();
					}
				}
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
					ReportUploadLogGrievances reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
					if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId() != 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogGrievances.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogGrievances.getReportUploadLogId());
						dueDate = reportUploadLogGrievances.getReportDate();
					}
				}
				
				/* Report Upload By Griev-AM-PFRDA */
				if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
					ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(applicationId);
					if(reportUploadLogGrievAMPFRDA != null ) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogGrievAMPFRDA.getReportMasterId());
						//comment = reportUploadLogGrievAMPFRDA.getRemarks();
						dueDate = reportUploadLogGrievAMPFRDA.getReportDate();
						//createDate = reportUploadLogGrievAMPFRDA.getStatusDate();
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogGrievAMPFRDA.getReportUploadLogId());
					}
				}
			}
			
			ReportUploadLog reportUploadLog = null;
			String statusKey = null;
			try {
				reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
				statusKey = reportUploadLog.getStatus_();
			} catch (Exception e) {
				LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
			}
			
			if(isNonNpsUser && intermediaryName.equalsIgnoreCase(checkIntermediaryName)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
					jsonObject.put("fileName", dlFileEntry.getFileName());
					jsonObject.put("userName", userName);
					jsonObject.put("submittedBy", userName);
					jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
					jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
					jsonObject.put("dueDate", Validator.isNotNull(dueDate)? DATE_FORMAT.format(dueDate):StringPool.BLANK);
					jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
					jsonObject.put("moduleName", reportMaster.getReportName());
					jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
					jsonObject.put("remarks", comment);
					jsonObject.put("intermediaryName", intermediaryName);
					jsonArray.put(jsonObject);
				}
			}else if(isNonNpsUser && Validator.isNull(checkIntermediaryName)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
					jsonObject.put("fileName", dlFileEntry.getFileName());
					jsonObject.put("userName", userName);
					jsonObject.put("submittedBy", userName);
					jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
					jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
					jsonObject.put("dueDate", Validator.isNotNull(dueDate)? DATE_FORMAT.format(dueDate):StringPool.BLANK);
					jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
					jsonObject.put("moduleName", reportMaster.getReportName());
					jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
					jsonObject.put("remarks", comment);
					jsonObject.put("intermediaryName", intermediaryName);
					jsonArray.put(jsonObject);
				}
			}else if(!isNonNpsUser && Validator.isNull(checkIntermediaryName)){
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
					jsonObject.put("fileName", dlFileEntry.getFileName());
					jsonObject.put("userName", userName);
					jsonObject.put("submittedBy", userName);
					jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
					jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
					jsonObject.put("dueDate", Validator.isNotNull(dueDate)? DATE_FORMAT.format(dueDate):StringPool.BLANK);
					jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
					jsonObject.put("moduleName", reportMaster.getReportName());
					jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
					jsonObject.put("remarks", comment);
					jsonObject.put("intermediaryName", intermediaryName);
					jsonArray.put(jsonObject);
				}
				
			}
			
			
		}
		
		return jsonArray;
	
	}
	
	public JSONArray getSubmittedToCustodianUser(PortletRequest portletRequest, boolean isMakerOrChecker, boolean isNonNpsUser, String checkIntermediaryName) throws Exception{

		LOG.info("getNPSTUsersTasks calling  ");
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isCompleted = false;
        long companyId = themeDisplay.getCompanyId();
        
        String folderName = ParamUtil.getString(portletRequest, "durationType", "Daily");
        LOG.info("folderName : "+folderName);
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		Set<WorkflowTask> workflowTasks = getWorkflowTaskSubmmitedToNPS(companyId, isCompleted, NPSCompany.CUSTODIAN, isMakerOrChecker);
		
		LOG.info("workflowTasks : "+workflowTasks);
		Date dueDate = null;
		for (WorkflowTask itr : workflowTasks) {
			String url = getWorkflowUrl(portletRequest, themeDisplay);
			Map<String, Serializable> maps = itr.getOptionalAttributes();
			long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
			String entryClassName = String.valueOf(maps.get("entryClassName"));
			
			OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
			String comment = getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
			String userName = getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
			
			ReportMaster reportMaster = null;
			DLFileEntry dlFileEntry = null;
			String intermediaryName = StringPool.BLANK;
			/**
			 * Here isMakerOrChecker = isPFMNonNpsUser (Pfm Maker, checker, supervisor)
			 */
			LOG.info("isMakerOrChecker::::" + isMakerOrChecker);
			if(isMakerOrChecker) {
				if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
					ReportUploadLogPFMCustodian reportUploadLogPFMCustodian = null;
					try {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId() != 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMCustodian.getReportUploadLogId());
						dueDate = reportUploadLogPFMCustodian.getReportDate();
					}
				}
			}else {
				if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
					ReportUploadLogCustodian reportUploadLogCustodian  = null;
					try {
						reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId() != 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogCustodian.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogCustodian.getReportUploadLogId());
						dueDate = reportUploadLogCustodian.getReportDate();
					}
				}
				
				/* Submitted to NPST data for custodian starts*/
				if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
					ReportUploadLogCustodian reportUploadLogCustodian =null; 
					try {
						reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogCustodian.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogCustodian.getReportUploadLogId());
						dueDate = reportUploadLogCustodian.getReportDate();
					}
				}
				if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
					CustodianCompForm custodianCompForm =null; 
					try {
						custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(custodianCompForm != null && custodianCompForm.getQcfile_id()!= 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(custodianCompForm.getQcfile_id());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(custodianCompForm.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(custodianCompForm.getReportUploadLogId());
						dueDate = custodianCompForm.getReportDate();
					}
				}
				if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
					CustAnnualAuditReport custAnnualAuditReport =null; 
					try {
						custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(custAnnualAuditReport != null && custAnnualAuditReport.getAudit_pdf_fileentry_id()!= 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(custAnnualAuditReport.getAudit_pdf_fileentry_id());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(custAnnualAuditReport.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(custAnnualAuditReport.getReportUploadLogId());
						dueDate = custAnnualAuditReport.getReportDate();
					}
				}
				if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
					CustIAR custIAR =null; 
					try {
						custIAR = custIARLocalService.getCustIAR(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(custIAR != null && custIAR.getIar_file_id() != 0) {
						dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(custIAR.getIar_file_id());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(custIAR.getReportMasterId());
						intermediaryName = npsDashboardUtil.getIntermediaryName(custIAR.getReportUploadLogId());
						dueDate = custIAR.getReportDate();
					}
				}
				
				/* Report Upload By Cust-AM-PFRDA */
				if(entryClassName.equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName())) {
					ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = null;
					try {
						reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(applicationId);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
					if(reportUploadLogCustAMPFRDA != null && reportUploadLogCustAMPFRDA.getFileEntryId()!= 0) {
						dlFileEntry =  DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLogCustAMPFRDA.getFileEntryId());
						reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogCustAMPFRDA.getReportMasterId());
						//comment = reportUploadLogCustAMPFRDA.getRemarks();
						dueDate =reportUploadLogCustAMPFRDA.getReportDate();
						//createDate = reportUploadLogCustAMPFRDA.getStatusDate();
						intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogCustAMPFRDA.getReportUploadLogId());
					}
				}
				/* Submitted to NPST data for custodian ends*/
			}
			ReportUploadLog reportUploadLog = null;
			String statusKey = null;
			try {
				reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
				statusKey = reportUploadLog.getStatus_();
			} catch (Exception e) {
				LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
			}
			if(isNonNpsUser && intermediaryName.equalsIgnoreCase(checkIntermediaryName)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				//if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
				if(reportMaster != null ) {
					jsonObject.put("fileName", dlFileEntry.getFileName());
					jsonObject.put("userName", userName);
					jsonObject.put("submittedBy", userName);
					jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
					jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
					jsonObject.put("dueDate", Validator.isNotNull(dueDate)? DATE_FORMAT.format(dueDate):StringPool.BLANK);
					jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
					jsonObject.put("moduleName", reportMaster.getReportName());
					jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
					jsonObject.put("remarks", comment);
					jsonObject.put("intermediaryName", intermediaryName);
					jsonArray.put(jsonObject);
					}
				}else if(isNonNpsUser && Validator.isNull(checkIntermediaryName)) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
						jsonObject.put("fileName", dlFileEntry.getFileName());
						jsonObject.put("userName", userName);
						jsonObject.put("submittedBy", userName);
						jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
						jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
						jsonObject.put("dueDate", Validator.isNotNull(dueDate)? DATE_FORMAT.format(dueDate):StringPool.BLANK);
						jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
						jsonObject.put("moduleName", reportMaster.getReportName());
						jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
						jsonObject.put("remarks", comment);
						jsonObject.put("intermediaryName", intermediaryName);
						jsonArray.put(jsonObject);
						}
				}else if(!isNonNpsUser && Validator.isNull(checkIntermediaryName)){
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					if(reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
						jsonObject.put("fileName", dlFileEntry.getFileName());
						jsonObject.put("userName", userName);
						jsonObject.put("submittedBy", userName);
						jsonObject.put("createDate", DATE_FORMAT.format(itr.getCreateDate()));
						jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
						jsonObject.put("dueDate", Validator.isNotNull(dueDate)? DATE_FORMAT.format(dueDate):StringPool.BLANK);
						jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
						jsonObject.put("moduleName", reportMaster.getReportName());
						jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
						jsonObject.put("remarks", comment);
						jsonObject.put("intermediaryName", intermediaryName);
						jsonArray.put(jsonObject);
						}
				}
			
			
		}
		
		return jsonArray;
	
	}
	
	public String getKaleoComment(long companyId, long workflowInstanceId, OrderByComparator<KaleoLog> comparator) {
		List<Integer> logTypes = new ArrayList<>();
		String comment = StringPool.BLANK;
		logTypes.add(KaleoLogUtil.convert("TASK_COMPLETION"));
		List<KaleoLog> kaleoLogs = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, workflowInstanceId, logTypes, QueryUtil.ALL_POS, KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogsCount(companyId, workflowInstanceId, logTypes)-1, comparator);
		kaleoLogs=kaleoLogs.stream().sorted(Comparator.comparing(KaleoLog::getCreateDate).reversed()).collect(Collectors.toList());
		if(kaleoLogs!=null && !kaleoLogs.isEmpty()) {
			KaleoLog kaleoLog = kaleoLogs.get(0);
			comment = kaleoLog.getComment();
		}
		return comment;
	}
	

	public String getCurrentAssigneeUserName(long companyId, long workflowInstantId, OrderByComparator<KaleoLog> comparator) {
		String assignedTo = StringPool.BLANK;
		List<Integer> logTypesAssignment = new ArrayList<>();
		logTypesAssignment.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
		
		List<KaleoLog> kaleoLogsAssignment = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, 
				workflowInstantId, logTypesAssignment, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
		kaleoLogsAssignment=kaleoLogsAssignment.stream().sorted(Comparator.comparing(KaleoLog::getCreateDate).reversed()).collect(Collectors.toList());
		if(kaleoLogsAssignment != null && !kaleoLogsAssignment.isEmpty()) {
			assignedTo = npsDashboardUtil.getUserName(kaleoLogsAssignment.get(0).getCurrentAssigneeClassPK(),kaleoLogsAssignment.get(0).getCurrentAssigneeClassName());
			//LOG.info("User Name : "+kaleoLogsAssignment.get(0).getUserName());
			//LOG.info("UserId: "+kaleoLogsAssignment.get(0).getUserId());
		}
		//LOG.info("assignedTo : "+assignedTo);
		return assignedTo;
	}
	
	
	
	public int getForwardedToNPST(long companyId, long workflowInstantId, OrderByComparator<KaleoLog> comparator) {
		List<Integer> logTypesAssignment = new ArrayList<>();
		logTypesAssignment.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
		
		List<KaleoLog> kaleoLogsAssignment = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, 
				workflowInstantId, logTypesAssignment, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
		kaleoLogsAssignment=kaleoLogsAssignment.stream().sorted(Comparator.comparing(KaleoLog::getCreateDate).reversed()).collect(Collectors.toList());
		if(kaleoLogsAssignment != null && !kaleoLogsAssignment.isEmpty()) {
			String className=kaleoLogsAssignment.get(0).getCurrentAssigneeClassName();
			long classPk=kaleoLogsAssignment.get(0).getCurrentAssigneeClassPK();
			
			long userId = 0;
			
	
			if(kaleoLogsAssignment.get(0).getCurrentAssigneeClassName().equalsIgnoreCase(Role.class.getName())) {
				Role role = RoleLocalServiceUtil.fetchRole(classPk);
				if(Validator.isNotNull(role)) {
					 if(role.getName().equalsIgnoreCase(NPSTRoleConstants.NPST_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.NPST_DGM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.NPST_GM) ||
							 role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_DGM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_GM) ||
							 role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_MGR) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CRA_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CRA_DGM) ||
							 role.getName().equalsIgnoreCase(NPSTRoleConstants.CRA_GM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_DGM) || 
					 role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_GM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_MGR) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_AM) ||
					 role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_DGM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_GM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_GM)) {
						 return 1;
					 }
				}

			}else if(className.equalsIgnoreCase(User.class.getName())) {
				userId = classPk;
			}
			//LOG.info("UserId : "+userId);
			if(userId > 0) {
				boolean isNonNPsUser=CommonRoleService.isNonNpsUser(userId);
				if(isNonNPsUser) {
					return 0;
				}else {
					return 1;
				}
			}			//LOG.info("User Name : "+kaleoLogsAssignment.get(0).getUserName());
			//LOG.info("UserId: "+kaleoLogsAssignment.get(0).getUserId());
		}
		//LOG.info("assignedTo : "+assignedTo);
		return 0;
	}
	
	public long getCurrentAssigneeUserId(long companyId, long workflowInstantId, OrderByComparator<KaleoLog> comparator) {
		long assignedToId = 0l;
		List<Integer> logTypesAssignment = new ArrayList<>();
		logTypesAssignment.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
		List<KaleoLog> kaleoLogsAssignment = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, 
				workflowInstantId, logTypesAssignment, QueryUtil.ALL_POS, KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogsCount(companyId, workflowInstantId, logTypesAssignment)-1, comparator);
		kaleoLogsAssignment=kaleoLogsAssignment.stream().sorted(Comparator.comparing(KaleoLog::getCreateDate).reversed()).collect(Collectors.toList());
		if(kaleoLogsAssignment != null && !kaleoLogsAssignment.isEmpty()) {
			assignedToId = npsDashboardUtil.getUserId(kaleoLogsAssignment.get(0).getCurrentAssigneeClassPK(),kaleoLogsAssignment.get(0).getCurrentAssigneeClassName());
			//LOG.info("User Name : "+kaleoLogsAssignment.get(0).getUserName());
			//LOG.info("UserId: "+kaleoLogsAssignment.get(0).getUserId());
		}
		//LOG.info("assignedTo : "+assignedTo);
		return assignedToId;
	}

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
	
	@Reference
	private ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService; 
	
	@Reference
	private CustodianCompFormLocalService custodianCompFormLocalService;
	
	@Reference
	private CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;
	
	@Reference
	private CustIARLocalService custIARLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	@Reference
	private InputQuarterlyIntervalLocalService  inputQuarterlyIntervalLocalService;
	
	@Reference
	private MnCompCertificateLocalService mnCompCertificateLocalService;
	
	@Reference
	private AnnualCompCertificateLocalService annualCompCertificateLocalService;
	
	@Reference
	private QtrStewardshipReportLocalService qtrStewardshipReportLocalService;
	
	@Reference
	private MnNpaDevelopmentLocalService mnNpaDevelopmentLocalService;
	
	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private ReportUploadLogPFMAMPFRDALocalService reportUploadLogPFMAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;
	
	@Reference
	private DARLocalService darLocalService;
	
	@Reference
	private Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;
	
	@Reference
	private PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
}
