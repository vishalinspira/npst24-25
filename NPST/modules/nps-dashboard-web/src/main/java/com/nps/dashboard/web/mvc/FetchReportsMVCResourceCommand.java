package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportStatusLog;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.ReportUploadLogNPST;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogMakerLocalService;
import com.daily.average.service.service.ReportUploadLogNPSTLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
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
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.FetchCompanyReportUtil;
import com.nps.dashboard.web.util.NPSDashboardUtil;
import com.nps.dashboard.web.util.NPSTUserUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;
//import Annexure_IV_PFM.utils.ValidateSheetName;
import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.constants.NameMappingConstants;
import nps.common.service.util.CommonRoleService;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_REPORTS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class FetchReportsMVCResourceCommand extends BaseMVCResourceCommand {
	
	static Log _log = LogFactoryUtil.getLog(FetchReportsMVCResourceCommand.class);

	private static final Log LOG = LogFactoryUtil.getLog(FetchReportsMVCResourceCommand.class);
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		LOG.info("Calling fetch report ..");
		String reportsType = ParamUtil.getString(resourceRequest, "reportsType");
		String folderName = ParamUtil.getString(resourceRequest, "durationType", "Daily");
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		//long repositoryId = themeDisplay.getScopeGroupId();
		User user = portal.getUser(resourceRequest);
		LOG.info("Report type : "+reportsType+" Duration type : "+folderName);
		boolean isNonNpsUser = CommonRoleService.isNonNpsUser(themeDisplay.getUserId());
		Company company = portal.getCompany(resourceRequest);
        long companyId = company.getCompanyId();
		
		boolean isChecker  = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.CHECKER, false);
		boolean isSupervisor  = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.SUPERVISOR, false);
		boolean isNPSTUser = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.NPST_USER, false);
		
	//	boolean isNPSTAM = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.NPST_AM, false);
		//boolean isNPSTCEO = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.NPST_CEO, false);
		//boolean isNPSTDGM = roleLocalService.hasUserRole(user.getUserId(), companyId,NPSTRoleConstants.NPST_DGM, false);
		//boolean isNPSTGM = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.NPST_GM, false);
		boolean isMaker = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.MAKER, false);
		boolean isPfrda = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.PFRDA_TB, false);
		String intermediaryName = npstUserUtil.getIntermediaryName(themeDisplay);
		
		if(reportsType.equals("PENDING") && isNPSTUser) {
			try {
				resourceResponse.getWriter().println(npstUserUtil.getPendingForReviewList(resourceRequest).toString());
				resourceResponse.getWriter().flush();
				
				return;
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
		}
		
		if(reportsType.equals("MAKERS_REPORT_SUMMARY")) {
			try {
				resourceResponse.getWriter().println(getAllDocsForMaker(resourceRequest, themeDisplay, dateFormat, companyId));
				resourceResponse.getWriter().flush();
				
				return;
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
		}
		
		if(reportsType.equals("APPROVED_REPORT_SUMMERY")) {
			try {
				resourceResponse.getWriter().println(getApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda));
				resourceResponse.getWriter().flush();
				return;
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
		}
		
		if(reportsType.equals("ALL")) {
			//if(isMaker || isChecker || isSupervisor) {
				LOG.info("Maker or checker report for all types");
				FetchCompanyReportUtil fcr=new FetchCompanyReportUtil();
				List<ReportStatusLog> reportStatusLogs=fcr.getSubmitToNPSTReports(NPSCompany.NPST, reportsType, folderName, isNonNpsUser, intermediaryName, NPSCompany.NPST, themeDisplay);
				LOG.info("after fetching logs /....");
				if(Validator.isNotNull(reportStatusLogs)) {
				LOG.info("reportStatusLogs size : "+ reportStatusLogs.size()+"  report Type: "+folderName);
				}
				JSONArray jsonArray=getJsonArray(reportStatusLogs);
				resourceResponse.getWriter().println(jsonArray);
			//} 
		}
		
     //   JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
        
        /*if(Validator.isNotNull(folder)) {		
			long folderId = folder.getFolderId();
			List<DLFileEntry> dlFileEntries = dlFileEntryLocalService.getFileEntries(themeDisplay.getScopeGroupId(), folderId);
			LOG.info("Folder name : "+folder.getName()+"folderId : "+folderId);
			
				for (Iterator<DLFileEntry> iterator = dlFileEntries.iterator(); iterator.hasNext();) {
					DLFileEntry n = (DLFileEntry) iterator.next();
					
					if(reportsType.equals("APPROVED")) {
						
						if(isNPSTUser) {
							jsonArray = getApprovedDocs(resourceRequest, themeDisplay, dateFormat, folderId ,user, companyId);
						} else {
							if(n.getStatus() == WorkflowConstants.STATUS_APPROVED) {
								JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
								jsonObject.put("fileName", n.getFileName());
								jsonObject.put("userName", n.getUserName());
								jsonObject.put("createDate", dateFormat.format(n.getCreateDate()));
								jsonObject.put("url", "/documents/" + n.getRepositoryId() + "/" + n.getFolderId() + "/" + n.getTitle());
								jsonObject.put("status", WorkflowConstants.getStatusLabel(n.getStatus()).toUpperCase());
								jsonObject.put("fileEntryId", n.getFileEntryId());
								jsonObject.put("moduleName", n.getDescription());
								jsonObject.put("remarks", "");
								jsonArray.put(jsonObject);
							}
						}
						
					} else if(reportsType.equals("PENDING")) {
						if(isChecker || isSupervisor || isNPSTUser) {
							try {
								jsonArray = getPendingDocs(resourceRequest, themeDisplay, dateFormat, folderName ,user, companyId);
							} catch (PortalException e) {
								 _log.error(e);
							}
						} else if(isMaker) {
							try {
//								jsonArray = getNPSTAMDocs(resourceRequest, themeDisplay, dateFormat, folderId, user, companyId);
								jsonArray = npstUserUtil.getNPSTUsersTasks(resourceRequest, Boolean.FALSE);
							} catch (PortalException e) {
								 _log.error(e);
							}
						} else {
							if(n.getStatus() == WorkflowConstants.STATUS_PENDING) {						
									JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									jsonObject.put("fileName", n.getFileName());
									jsonObject.put("userName", n.getUserName());
									jsonObject.put("createDate", dateFormat.format(n.getCreateDate()));
									jsonObject.put("url", "/documents/" + n.getRepositoryId() + "/" + n.getFolderId() + "/" + n.getTitle());
									jsonObject.put("dueDate", dateFormat.format(n.getCreateDate()));
									jsonObject.put("status", WorkflowConstants.getStatusLabel(n.getStatus()).toUpperCase());
									jsonObject.put("moduleName", n.getDescription());
									jsonObject.put("remarks", "");
									jsonArray.put(jsonObject);
							}
						}
					} else {
						if(isNPSTAM || isNPSTCEO || isNPSTDGM || isNPSTGM) {
							try {
								jsonArray = getPendingDocs(resourceRequest, themeDisplay, dateFormat, folderName ,user, companyId);
							} catch (PortalException e) {
								 _log.error(e);
							}
						} else if(isMaker || isChecker) {
							try {
//								jsonArray = getNPSTAMDocs(resourceRequest, themeDisplay, dateFormat, folderId, user, companyId);
								jsonArray = npstUserUtil.getNPSTUsersTasks(resourceRequest, Boolean.FALSE);
							} catch (PortalException e) {
								 _log.error(e);
							}
						} else {
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
							jsonObject.put("fileName", n.getFileName());
							jsonObject.put("userName", n.getUserName());
							jsonObject.put("createDate", dateFormat.format(n.getCreateDate()));
							jsonObject.put("submittedBy", n.getUserName());
							jsonObject.put("url", "/documents/" + n.getRepositoryId() + "/" + n.getFolderId() + "/" + n.getTitle());
							jsonObject.put("dueDate", dateFormat.format(n.getCreateDate()));
							jsonObject.put("status", WorkflowConstants.getStatusLabel(n.getStatus()).toUpperCase());
							jsonObject.put("moduleName", n.getDescription());
							jsonObject.put("remarks", "");
							jsonArray.put(jsonObject);
						}
					}
				}
			
			try {
				resourceResponse.getWriter().println(jsonArray);
				resourceResponse.getWriter().flush();
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
		}*/
	}

	public JSONArray getPendingDocs(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat,String  folderName, User user, long companyId) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
        long assigneeUserId = user.getUserId();
        Boolean isCompleted = false;
			try {
				List<WorkflowTask> assigneToRole = WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId, assigneeUserId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, assigneeUserId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				
				assigneToMe.addAll(assigneToRole);
				
				for (WorkflowTask itr : assigneToMe) {
					String url = getWorkflowUrl(resourceRequest, themeDisplay);
					Map<String, Serializable> maps = itr.getOptionalAttributes();
					long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
					String entryClassName = String.valueOf(maps.get("entryClassName"));
					ReportUploadLogMaker reportUploadLogMaker = null;
					ReportUploadLogSupervisor reportUploadLogSupervisor = null;
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
						reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogMaker.getReportMasterId());
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
						reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
						if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
						}
					}
					
					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", true);
					//System.err.println("itr.getWorkflowInstanceId() ::::: " + itr.getWorkflowInstanceId());
					List<Integer> logTypesOne = new ArrayList<>();
					logTypesOne.add(KaleoLogUtil.convert("TASK_COMPLETION"));
					List<KaleoLog> kaleoLogsOne = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesOne, QueryUtil.ALL_POS, KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogsCount(companyId, itr.getWorkflowInstanceId(), logTypesOne)-1, comparator);
					
					List<Integer> logTypesTwo = new ArrayList<>();
					logTypesTwo.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
					List<KaleoLog> kaleoLogsTwo = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesTwo, QueryUtil.ALL_POS,KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogsCount(companyId, itr.getWorkflowInstanceId(), logTypesOne)-1, comparator);
					
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
						jsonObject.put("fileName", HtmlUtil.escape(dlFileEntry.getFileName()));
						jsonObject.put("userName", reportMaster.getUploaderRole().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
						jsonObject.put("createDate", dateFormat.format(dlFileEntry.getCreateDate()));
						jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
						jsonObject.put("dueDate", dateFormat.format(reportUploadLogMaker.getReportDate()));
						jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
						jsonObject.put("moduleName", reportMaster.getReportName());
						jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
						jsonObject.put("remarks", HtmlUtil.escape(comment.isEmpty() ? reportUploadLogMaker.getRemarks() : comment));
						jsonArray.put(jsonObject);
					} else if(reportUploadLogSupervisor != null && reportMaster != null && reportMaster.getReportType().equalsIgnoreCase(folderName)) {
						jsonObject.put("fileName", HtmlUtil.escape(dlFileEntry.getFileName()));
						jsonObject.put("userName", reportMaster.getUploaderRole().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
						jsonObject.put("createDate", dateFormat.format(dlFileEntry.getCreateDate()));
						jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
						jsonObject.put("dueDate", dateFormat.format(reportUploadLogSupervisor.getReportDate()));
						jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase()));
						jsonObject.put("moduleName", reportMaster.getReportName());
						jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
						jsonObject.put("remarks", HtmlUtil.escape(comment.isEmpty() ? reportUploadLogSupervisor.getRemarks() : comment));
						jsonArray.put(jsonObject);
					}
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		return jsonArray;
	}
	
	public JSONArray getApprovedDocs(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat,long  folderid, User user, long companyId) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		long assigneeUserId = user.getUserId();
		try {
			List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId, assigneeUserId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> assigneToMe2 = WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId, assigneeUserId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> assigneToMe3 = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, assigneeUserId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> assigneToMe4 = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, assigneeUserId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			assigneToMe.addAll(assigneToMe2);
			assigneToMe.addAll(assigneToMe3);
			assigneToMe.addAll(assigneToMe4);
			
			for (WorkflowTask itr : assigneToMe) {
				String url = getWorkflowUrl(resourceRequest, themeDisplay);
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
//				String entryType = String.valueOf(maps.get("entryType"));
				String entryClassName = String.valueOf(maps.get("entryClassName"));

				DLFileEntry dlFileEntry = null;
				if(entryClassName.equalsIgnoreCase("com.liferay.document.library.kernel.model.DLFileEntry")) {
					List<DLFileEntryMetadata> dLFileEntryMetadatas = dlFileEntryMetadataLocalService.getFileVersionFileEntryMetadatas(applicationId);
					
					if(dLFileEntryMetadatas != null && dLFileEntryMetadatas.size() > 0) {
						dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(dLFileEntryMetadatas.get(0).getFileEntryId());
					}
				}
				List<MBMessage> mbMessages = mbMessageLocalService.getMessages("com.liferay.document.library.kernel.model.DLFileEntry", applicationId, 0);
				StringBuffer messageBuffer = new StringBuffer();
				for(MBMessage mbMessage : mbMessages) {
					LOG.info(mbMessage.getBody());
					messageBuffer.append(mbMessage.getBody());
				}
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				if(dlFileEntry != null && dlFileEntry.getFolderId() == folderid) {
					jsonObject.put("fileName", HtmlUtil.escape(dlFileEntry.getFileName()));
					jsonObject.put("userName", HtmlUtil.escape(dlFileEntry.getUserName()));
					jsonObject.put("createDate", dateFormat.format(dlFileEntry.getCreateDate()));
					jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
					jsonObject.put("status", WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase());
					jsonObject.put("moduleName", HtmlUtil.escape(dlFileEntry.getDescription()));
					jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
					jsonObject.put("remarks", HtmlUtil.escape(messageBuffer.toString()));
					jsonArray.put(jsonObject);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		return jsonArray;
	}
	
	/**
	 * All reports those are assgined to any user.
	 * 
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @return
	 * @throws PortalException
	 */
	public JSONArray getAllDocsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId){
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			Role NPSTAM = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_AM);
			Role NPSTDGM = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_DGM);
			Role NPSTGM = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_GM);
			Role NPSTCEO = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_CEO);
			//User user = themeDisplay.getUser();
		//	boolean isChecker  = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.CHECKER, false);
			//boolean isMaker = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.MAKER, false);
			List<WorkflowTask> allList = new ArrayList<>();
			boolean isNonNpsUser =  npsDashboardUtil.isNonNpsUser(themeDisplay.getUserId());
			
			//List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> pending = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.FALSE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			//allList.addAll(completed);
			allList.addAll(pending);
			
			Set<Long> addedWorkflowTasks = new HashSet<>();
			
			for (WorkflowTask itr : allList) {
				try {
					Map<String, Serializable> maps = itr.getOptionalAttributes();
					long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
					if(!addedWorkflowTasks.contains(applicationId)) {
						String entryClassName = String.valueOf(maps.get("entryClassName"));
						ReportUploadLogMaker reportUploadLogMaker = null;
						ReportUploadLogSupervisor reportUploadLogSupervisor = null;
						ReportUploadLogNPST reportUploadLogNpst = null;
						ReportMaster reportMaster = null;
						DLFileEntry dlFileEntry = null;
			
						if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
							reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
							if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogMaker.getReportMasterId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
							reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
							if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
							}
						}
						if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
							reportUploadLogNpst  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(applicationId);
							if(reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNpst.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogNpst.getReportMasterId());
							}
						}
						OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
						
						List<Integer> logTypes = new ArrayList<>();
						logTypes.add(KaleoLogUtil.convert("TASK_COMPLETION"));
						//logTypes.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
						
						List<KaleoLog> kaleoLogs = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypes, QueryUtil.ALL_POS, KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogsCount(companyId, itr.getWorkflowInstanceId(), logTypes)-1, comparator);
						
						String comment = StringPool.BLANK;
						if(!kaleoLogs.isEmpty()) {
							KaleoLog kaleoLog = kaleoLogs.get(0);
							comment = kaleoLog.getComment();
							long cpk = kaleoLog.getCurrentAssigneeClassPK();
							//_log.info("kaleoLog"+kaleoLog);
							if(kaleoLog.getCurrentAssigneeClassName().equalsIgnoreCase(Role.class.getName())
									&& (cpk == NPSTAM.getRoleId() || cpk == NPSTDGM.getRoleId() ||cpk == NPSTGM.getRoleId() ||cpk == NPSTCEO.getRoleId())) {
								continue;
							}
//							else if(kaleoLog.getCurrentAssigneeClassName().equalsIgnoreCase(User.class.getName()) 
//									&& ((roleLocalService.hasUserRole(cpk, companyId, NPSTRoleConstants.NPST_AM, false))
//									|| (roleLocalService.hasUserRole(cpk, companyId, NPSTRoleConstants.NPST_DGM, false))
//									|| (roleLocalService.hasUserRole(cpk, companyId, NPSTRoleConstants.NPST_GM, false))
//									|| (roleLocalService.hasUserRole(cpk, companyId, NPSTRoleConstants.NPST_CEO, false)))) {
//								continue;
//							}
						}
						
						String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
						long assignedToId = npstUserUtil.getCurrentAssigneeUserId(companyId, itr.getWorkflowInstanceId(), comparator);
						
						JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
						if(Validator.isNotNull(dlFileEntry) && reportMaster != null ) {
							
							long reportMasterId = reportMaster.getReportMasterId();
							String reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
							 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
							ReportUploadLog reportUploadLog = null;
							try {
								reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
							} catch (Exception e) {
								LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
							}
							
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogMaker)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogMaker.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogMaker.getCreateDate()), reportUploadLogMaker.getStatus(), comment, 
										itr.getWorkflowInstanceId(), dateFormat.format(reportUploadLogMaker.getReportDate()), reportUploadLog.getStatus_(),
										assignedTo,assignedToId,isNonNpsUser);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogSupervisor)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogSupervisor.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogSupervisor.getCreateDate()), reportUploadLogSupervisor.getStatus(), comment, 
										itr.getWorkflowInstanceId(), dateFormat.format(reportUploadLogSupervisor.getReportDate()), reportUploadLog.getStatus_(),
										assignedTo,assignedToId,isNonNpsUser);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogNpst)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogNpst.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogNpst.getCreateDate()), reportUploadLogNpst.getStatus(), comment, 
										itr.getWorkflowInstanceId(), dateFormat.format(reportUploadLogNpst.getReportDate()), reportUploadLog.getStatus_(),
										assignedTo,assignedToId,isNonNpsUser);
							}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								jsonArray.put(jsonObject);
							}
						}
					}
					addedWorkflowTasks.add(applicationId);
				}catch (Exception e) {
					LOG.error("Exception on fetching log reports : "+e.getMessage());
				}
			}
		
		}catch (Exception e) {
			LOG.error("Exception : "+e.getMessage(), e);
		}
		return jsonArray;
	}
	
	private JSONObject getReportSummeryObject(String reportName, long reportUploadLogId, long reportMasterId, DLFileEntry dlFileEntry, 
			String reportUploadDate, int reportStatus, String comment, long workflowInstanceId, String dueDate, String statusKey,String assignedTo,
			long assignedToId, boolean isNonNpsUser) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		if(isNonNpsUser && npsDashboardUtil.isNonNpsUser(assignedToId)) {
			jsonObject.put("fileName", HtmlUtil.escape(reportName));
			jsonObject.put("reportUploadLogId",reportUploadLogId);
			jsonObject.put("reportMasterId", reportMasterId);
			jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
			jsonObject.put("userName", HtmlUtil.escape(dlFileEntry.getUserName()));
			jsonObject.put("dueDate", dueDate);
			jsonObject.put("createDate", reportUploadDate);
			jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
			jsonObject.put("remarks", HtmlUtil.escape(comment));
			jsonObject.put("workflowInstanceId", workflowInstanceId);
			jsonObject.put("assignedTo", assignedTo.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		}else if(isNonNpsUser && npsDashboardUtil.isNonNpsUser(assignedToId)){
			jsonObject.put("fileName", HtmlUtil.escape(reportName));
			jsonObject.put("reportUploadLogId",reportUploadLogId);
			jsonObject.put("reportMasterId", reportMasterId);
			jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
			jsonObject.put("userName", HtmlUtil.escape(dlFileEntry.getUserName()));
			jsonObject.put("dueDate", dueDate);
			jsonObject.put("createDate", reportUploadDate);
			jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
			jsonObject.put("remarks", HtmlUtil.escape(comment));
			jsonObject.put("workflowInstanceId", workflowInstanceId);
			jsonObject.put("assignedTo", assignedTo.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		}else if(!isNonNpsUser) {
			jsonObject.put("fileName", HtmlUtil.escape(reportName));
			jsonObject.put("reportUploadLogId",reportUploadLogId);
			jsonObject.put("reportMasterId", reportMasterId);
			jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
			jsonObject.put("userName", HtmlUtil.escape(dlFileEntry.getUserName()));
			jsonObject.put("dueDate", dueDate);
			jsonObject.put("createDate", reportUploadDate);
			jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
			jsonObject.put("remarks", HtmlUtil.escape(comment));
			jsonObject.put("workflowInstanceId", workflowInstanceId);
			jsonObject.put("assignedTo", assignedTo.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		}
		return jsonObject;
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
	
	/**
	 * This method writtern to display only approved reports. 
	 * @author Jyostna.Pradhan
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @return
	 * @throws PortalException
	 */
	public JSONArray getApprovedReportSummary(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId, boolean isPfrda) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("Get approved report summary for user.");
		
		Set<Long> addedWorkflowTasks = new HashSet<>();
		 
		try {
			
			List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			for (WorkflowTask itr : completed) {
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				if(!addedWorkflowTasks.contains(applicationId)) {
					String entryClassName = String.valueOf(maps.get("entryClassName"));
					//LOG.info("Class Name : "+entryClassName+" Class PK : "+applicationId);
					ReportUploadLogNPST reportUploadLogNpst = null;
					ReportUploadLogMaker reportUploadLogMaker = null;
					ReportUploadLogSupervisor reportUploadLogSupervisor = null;
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					boolean isNonNpsUser =  npsDashboardUtil.isNonNpsUser(themeDisplay.getUserId());

					if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
						reportUploadLogNpst  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(applicationId);
						if(reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNpst.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogNpst.getReportMasterId());
						}
					}
					
					if(!isPfrda) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
							reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
							if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogMaker.getReportMasterId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
							reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
							if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
							}
						}
					}
					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
					
					List<Integer> logTypes = new ArrayList<>();
					logTypes.add(KaleoLogUtil.convert("TASK_COMPLETION"));
					
					List<KaleoLog> kaleoLogs = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypes, QueryUtil.ALL_POS, KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogsCount(companyId, itr.getWorkflowInstanceId(), logTypes)-1, comparator);
					String comment = StringPool.BLANK;
					if(!kaleoLogs.isEmpty()) {
						KaleoLog kaleoLog = kaleoLogs.get(0);
						comment = kaleoLog.getComment();
					}
					
					String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
					long assignedToId = npstUserUtil.getCurrentAssigneeUserId(companyId, itr.getWorkflowInstanceId(), comparator);
					
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					if(Validator.isNotNull(dlFileEntry) && reportMaster != null ) {
						int status = -1;
						long reportMasterId = reportMaster.getReportMasterId();
						String reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
						 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
						ReportUploadLog reportUploadLog = null;
						try {
							reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
						} catch (Exception e) {
							LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
						}
						
						if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogNpst)) {
							jsonObject = getReportSummeryObject(reportName, reportUploadLogNpst.getReportUploadLogId(), reportMasterId, dlFileEntry, 
									dateFormat.format(reportUploadLogNpst.getCreateDate()), reportUploadLogNpst.getStatus(), comment, itr.getWorkflowInstanceId(), 
									dateFormat.format(reportUploadLogNpst.getReportDate()), reportUploadLog.getStatus_(),assignedTo,
									assignedToId,isNonNpsUser);
							status = reportUploadLogNpst.getStatus();
						}
						
						if(!isPfrda) {
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogMaker)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogMaker.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogMaker.getCreateDate()), reportUploadLogMaker.getStatus(), 
										comment, itr.getWorkflowInstanceId(), dateFormat.format(reportUploadLogMaker.getReportDate()), 
										reportUploadLog.getStatus_(),assignedTo,assignedToId,isNonNpsUser);
								status = reportUploadLogMaker.getStatus();
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogSupervisor)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogSupervisor.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogSupervisor.getCreateDate()),
										reportUploadLogSupervisor.getStatus(), comment, itr.getWorkflowInstanceId(), 
										dateFormat.format(reportUploadLogSupervisor.getReportDate()), reportUploadLog.getStatus_(),assignedTo,
										assignedToId,isNonNpsUser);
								status = reportUploadLogSupervisor.getStatus();
							}
						}
						if(status == 0) {
							jsonArray.put(jsonObject);
						}
					}
				}
				
				addedWorkflowTasks.add(applicationId);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		return jsonArray;
	}
	
	
	private JSONArray getJsonArray(List<ReportStatusLog> reportStatusLogs) {
		JSONArray array= JSONFactoryUtil.createJSONArray();
		if(Validator.isNotNull(reportStatusLogs)) {
			for(ReportStatusLog reportStatusLog:reportStatusLogs) {
				try {
				String intermediaryName = reportStatusLog.getIntermediaryname();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				jsonObject.put("fileName", HtmlUtil.escape(reportStatusLog.getReportName()));
				jsonObject.put("reportUploadLogId",reportStatusLog.getReportUploadLogId()+"");
				jsonObject.put("reportMasterId", reportStatusLog.getReportMasterId()+"");
				jsonObject.put("dueDate", dateFormat.format(reportStatusLog.getReportDate()));
				jsonObject.put("createDate", dateFormat.format(reportStatusLog.getCreateDate()));
				jsonObject.put("status", (reportStatusLog.getStatus_() != null && !reportStatusLog.getStatus_().isEmpty() ? reportStatusLog.getStatus_().toUpperCase() : WorkflowConstants.getStatusLabel(reportStatusLog.getStatus()).toUpperCase()));
				jsonObject.put("remarks", HtmlUtil.escape(reportStatusLog.getRemarks()));
				jsonObject.put("workflowInstanceId", reportStatusLog.getWorkflowInstanceId()+"");
				jsonObject.put("url", reportStatusLog.getFileUrl());
				jsonObject.put("userName", HtmlUtil.escape(reportStatusLog.getUserName()));
				jsonObject.put("submittedBy", HtmlUtil.escape(reportStatusLog.getUserName()));
				String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
				if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
					intermediaryName=intrmedName;
				}
				jsonObject.put("intermediaryName",HtmlUtil.escape(intermediaryName ));
				jsonObject.put("assignedTo", reportStatusLog.getAssignedTo().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
				jsonObject.put("moduleName", HtmlUtil.escape(reportStatusLog.getReportName()));
				jsonObject.put("intermediaryName", HtmlUtil.escape(intermediaryName));
				
				array.put(jsonObject);
				}catch (Exception e) {
				LOG.error("Error while get JSON Array: " + e);
			}
		}
		}
		return array;
		
	}
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private NPSTUserUtil npstUserUtil;

	@Reference
	private ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;
	
	@Reference
	private DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService;
	
	@Reference
	private MBMessageLocalService mbMessageLocalService;
	
	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference
	private Portal portal;
	
	@Reference
	private DLAppService dlAppService;
	
	@Reference
	private DLFileEntryLocalService dlFileEntryLocalService;
	
	@Reference
	private ReportUploadLogMakerLocalService reportUploadLogMakerLocalService;
	
	@Reference
	private ReportUploadLogNPSTLocalService reportUploadLogNPSTLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	
}
