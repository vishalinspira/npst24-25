package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.model.CustIAR;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.DAR;
import com.daily.average.service.model.HDFCInternal_Audit_Report;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.Manpowerform_i;
import com.daily.average.service.model.Manpowerform_ii;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogCustAMPFRDA;
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.ReportUploadLogGrievAMPFRDA;
import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.ReportUploadLogNPST;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.model.ReportUploadLogPFMCustodian;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.AnnualCompCertificateLocalService;
import com.daily.average.service.service.CustAnnualAuditReportLocalService;
import com.daily.average.service.service.CustIARLocalService;
import com.daily.average.service.service.CustodianCompFormLocalService;
import com.daily.average.service.service.DARLocalService;
import com.daily.average.service.service.HDFCInternal_Audit_ReportLocalService;
import com.daily.average.service.service.InputQuarterlyIntervalLocalService;
import com.daily.average.service.service.Manpowerform_iLocalService;
import com.daily.average.service.service.Manpowerform_iiLocalService;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalService;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogMakerLocalService;
import com.daily.average.service.service.ReportUploadLogNPSTLocalService;
import com.daily.average.service.service.ReportUploadLogNPSTLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.TRUSTEE_BANK_REPORT_BY_REPORT_TYPE
		},
		service = MVCResourceCommand.class
)
public class GetTrusteeBankReportMVCResource extends BaseMVCResourceCommand{
	private static final Log log = LogFactoryUtil.getLog(GetTrusteeBankReportMVCResource.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		String reportType = ParamUtil.getString(resourceRequest, "reportType");
		String reportStatus = ParamUtil.getString(resourceRequest, "reportStatus");
		String department = ParamUtil.getString(resourceRequest, "department", NPSCompany.NPST);
		log.info("Report Type:: "+reportType+" reportStatus ::: "+reportStatus+" Department : "+department);
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		int uploaded_i = reportStatus.equalsIgnoreCase("Pending")?0:1;
				
//		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		
		List<ReportMaster> reportMasters = reportMasterLocalService.getByReportTypeAndDepartment(reportType, department);
		List<Long> reportMasterIds = reportMasters.stream().map(ReportMaster::getReportMasterId).collect(Collectors.toList());
		List<ReportUploadLog> reportUploadLogs = new ArrayList<>();
		
		if(uploaded_i == 0) {
			reportUploadLogs = reportUploadLogLocalService.getByReportMasterIdsAndUploaded_i(reportMasterIds, uploaded_i);
		}else {
			int workflowStatus = WorkflowConstants.STATUS_PENDING;
			if(reportStatus.equalsIgnoreCase("approved")) {
				workflowStatus = WorkflowConstants.STATUS_APPROVED;
			}
			boolean status = true;
			if(workflowStatus == WorkflowConstants.STATUS_PENDING) {
				status = false;
			}else if(workflowStatus == WorkflowConstants.STATUS_APPROVED) {
				status = true;
			}
			log.info("Workflow status : "+workflowStatus);
			Set<Long> reportuploadLogIds = new HashSet<>();
			if(NPSCompany.NPST.equalsIgnoreCase(department)) {
				//List<ReportUploadLogMaker> reportUploadLogMakers = reportUploadLogMakerLocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				//List<ReportUploadLogSupervisor> reportUploadLogSupervisors = reportUploadLogSupervisorLocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				//List<ReportUploadLogNPST> reportUploadLogNPST = reportUploadLogNPSTLocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				
				//reportuploadLogIds.addAll(reportUploadLogMakers.stream().map(ReportUploadLogMaker::getReportUploadLogId).collect(Collectors.toSet()));
				//reportuploadLogIds.addAll(reportUploadLogSupervisors.stream().map(ReportUploadLogSupervisor::getReportUploadLogId).collect(Collectors.toSet()));
				//reportuploadLogIds.addAll(reportUploadLogNPST.stream().map(ReportUploadLogNPST::getReportUploadLogId).collect(Collectors.toSet()));
				reportuploadLogIds = getDepartmentReportUploadId(reportMasterIds, status, department, themeDisplay);
				
			}else if(NPSCompany.PFM.equalsIgnoreCase(department)) {
				//List<ReportUploadLogPFM> reportUploadLogPFM = reportUploadLogPFMLocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				//List<ReportUploadLogPFMAM> reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				//List<ReportUploadLogPFMCRA> reportUploadLogPFMCRA = reportUploadLogPFMCRALocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				
				
				//reportuploadLogIds.addAll(reportUploadLogPFM.stream().map(ReportUploadLogPFM::getReportUploadLogId).collect(Collectors.toSet()));
				//reportuploadLogIds.addAll(reportUploadLogPFMAM.stream().map(ReportUploadLogPFMAM::getReportUploadLogId).collect(Collectors.toSet()));
				//reportuploadLogIds.addAll(reportUploadLogPFMCRA.stream().map(ReportUploadLogPFMCRA::getReportUploadLogId).collect(Collectors.toSet()));
				reportuploadLogIds = getDepartmentReportUploadId(reportMasterIds, status, department, themeDisplay);
			}else if(NPSCompany.CRA.equalsIgnoreCase(department)) {
				//List<ReportUploadLogCRA> reportUploadLogCRA  = reportUploadLogCRALocalService.getByReportMasterIdsAndStatus(reportMasterIds,workflowStatus);
				//reportuploadLogIds.addAll(reportUploadLogCRA.stream().map(ReportUploadLogCRA::getReportUploadLogId).collect(Collectors.toSet()));
				reportuploadLogIds = getDepartmentReportUploadId(reportMasterIds, status, department, themeDisplay);
			}else if(NPSCompany.GRIEVANCES.equalsIgnoreCase(department)) {
				//List<ReportUploadLogGrievances> reportUploadLogGrievances = reportUploadLogGrievancesLocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				//reportuploadLogIds.addAll(reportUploadLogGrievances.stream().map(ReportUploadLogGrievances::getReportUploadLogId).collect(Collectors.toSet()));
				reportuploadLogIds = getDepartmentReportUploadId(reportMasterIds, status, department, themeDisplay);
			}else if(NPSCompany.CUSTODIAN.equalsIgnoreCase(department)) {
				//List<ReportUploadLogCustodian> reportUploadLogCustodian = reportUploadLogCustodianLocalService.getByReportMasterIdsAndStatus(reportMasterIds, workflowStatus);
				//reportuploadLogIds.addAll(reportUploadLogCustodian.stream().map(ReportUploadLogCustodian::getReportUploadLogId).collect(Collectors.toSet()));
				reportuploadLogIds = getDepartmentReportUploadId(reportMasterIds, status, department, themeDisplay);
			}
			
			log.info("reportuploadLogIds : "+reportuploadLogIds);
			reportUploadLogs = reportUploadLogLocalService.getByReportUploadLogIds(new ArrayList<Long>(reportuploadLogIds));
		}
		
		
		for(ReportUploadLog reportUploadLog: reportUploadLogs) {
			JSONObject object = JSONFactoryUtil.createJSONObject();
			ReportMaster reportMaster = reportMasterLocalService.getReportMaster(reportUploadLog.getReportMasterId());
			String fileUrl = "Pending".equalsIgnoreCase(reportStatus) ? StringPool.BLANK : getFileURL(reportUploadLog.getReportUploadLogId(), themeDisplay);
			object.put("reportName", reportMaster.getReportName() + " " + reportUploadLog.getIntermediaryname());
			object.put("dueDate", dateFormat.format(reportUploadLog.getReportDate()));
			object.put("url", fileUrl);
			
			jsonArray.put(object);
		}
		resourceResponse.getWriter().print(jsonArray);
	}
	
	private String getFileURL(long reportUploadLogId, ThemeDisplay themeDisplay) {
		// Get
		long fileEntryId = 0;
		try {
			ReportUploadLogMaker maker = reportUploadLogMakerLocalService.getReportUploadLogMaker(reportUploadLogId);
			fileEntryId = maker.getFileEntryId();
		}catch (Exception e) {
			log.error("There is not maker with Pk id : "+reportUploadLogId + " Exception::" + e.getMessage());
		}
		
		if(fileEntryId == 0) {
			try {
				ReportUploadLogSupervisor supervisor = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(reportUploadLogId);
				fileEntryId = supervisor.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not Supervisor with Pk id : "+reportUploadLogId + " Exception::" + e.getMessage());
			}
		}
		if(fileEntryId == 0) {
			try {
				ReportUploadLogNPST npstAM = reportUploadLogNPSTLocalService.getReportUploadLogNPST(reportUploadLogId);
				fileEntryId = npstAM.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not npst AM with Pk id : "+reportUploadLogId + " Exception::" + e.getMessage());
			}
		}
		
		//ReportUploadLogPFM
		if(fileEntryId == 0) {
			try {
				ReportUploadLogPFM reportUploadLogPfm  = reportUploadLogPFMLocalService.getReportUploadLogPFM(reportUploadLogId);
				fileEntryId = reportUploadLogPfm.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not ReportUploadLogPFM with Pk id : "+reportUploadLogId + "Exception : "+e.getMessage());
			}
		}
		
		//MnCompCertificate
		if(fileEntryId == 0) {
			try {
				MnCompCertificate mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(reportUploadLogId);
				fileEntryId = mnCompCertificate.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not MnCompCertificate with Pk id : "+reportUploadLogId + " Exception :"+e.getMessage());
			}
		}
		
		//AnnualCompCertificate
		if(fileEntryId == 0) {
			try {
				AnnualCompCertificate annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(reportUploadLogId);
				fileEntryId = annualCompCertificate.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not AnnualCompCertificate with Pk id : "+reportUploadLogId + " Exception : "+e.getMessage());
			}
		}
		
		//QtrStewardshipReport
		if(fileEntryId == 0) {
			try {
				QtrStewardshipReport qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(reportUploadLogId);
				fileEntryId = qtrStewardshipReport.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not QtrStewardshipReport with Pk id : "+reportUploadLogId + " Exception : "+e.getMessage());
			}
		}
		
		//ReportUploadLogPFMAMPFRDA
		if(fileEntryId == 0) {
			try {
				ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(reportUploadLogId);
				fileEntryId = reportUploadLogPFMAMPFRDA.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not ReportUploadLogPFMAMPFRDA with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//ReportUploadLogPFMCRA
		if(fileEntryId == 0) {
			try {
				ReportUploadLogPFMCRA reportUploadLogPfmCra  = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(reportUploadLogId);
				fileEntryId = reportUploadLogPfmCra.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not reportUploadLogPfmCra with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//ReportUploadLogGrievances
		if(fileEntryId == 0) {
			try {
				ReportUploadLogGrievances reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(reportUploadLogId);
				fileEntryId = reportUploadLogGrievances.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not reportUploadLogGrievances with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//ReportUploadLogGrievAMPFRDA
		if(fileEntryId == 0) {
			try {
				ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(reportUploadLogId);
				fileEntryId = reportUploadLogGrievAMPFRDA.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not reportUploadLogGrievAMPFRDA with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//ReportUploadLogPFMCustodian
		if(fileEntryId == 0) {
			try {
				ReportUploadLogPFMCustodian reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(reportUploadLogId);
				fileEntryId = reportUploadLogPFMCustodian.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not reportUploadLogPFMCustodian with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//ReportUploadLogCustodian
		if(fileEntryId == 0) {
			try {
				ReportUploadLogCustodian reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(reportUploadLogId);
				fileEntryId = reportUploadLogCustodian.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not reportUploadLogCustodian with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//CustodianCompForm
		if(fileEntryId == 0) {
			try {
				CustodianCompForm custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(reportUploadLogId);
				fileEntryId = custodianCompForm.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not custodianCompForm with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//CustAnnualAuditReport
		if(fileEntryId == 0) {
			try {
				CustAnnualAuditReport custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(reportUploadLogId);
				fileEntryId = custAnnualAuditReport.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not custAnnualAuditReport with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//CustIAR
		if(fileEntryId == 0) {
			try {
				CustIAR custIAR = custIARLocalService.getCustIAR(reportUploadLogId);
				fileEntryId = custIAR.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not custIAR with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//ReportUploadLogCustAMPFRDA
		if(fileEntryId == 0) {
			try {
				ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(reportUploadLogId);
				fileEntryId = reportUploadLogCustAMPFRDA.getFileEntryId();
			}catch (Exception e) {
				log.error("There is not reportUploadLogCustAMPFRDA with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		//ReportUploadFileLog
		if(fileEntryId == 0) {
			try {
				ReportUploadFileLog reportUploadFileLog = reportUploadFileLogLocalService.getReportUploadFileLog(reportUploadLogId);
				fileEntryId = reportUploadFileLog.getFileEntryId();
			} catch (Exception e) {
				log.error("There is not ReportUploadFileLog with Pk id : "+reportUploadLogId + " Exception :" + e.getMessage());
			}
		}
		
		String fileURL = StringPool.BLANK;
		if(fileEntryId > 0) {
			try {
				FileEntry fileEntry = dlAppService.getFileEntry(fileEntryId);
				fileURL = dlURLHelper.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK);
			}catch (PortalException e) {
				log.error("Exception on fetching file from DL : "+e.getMessage(), e);
			}
		}
		
		return fileURL;
	}
	
	/* CEO Dashboard New Changes Starts */
	
	public Set<Long> getDepartmentReportUploadId(List<Long> reportMasterIds, boolean statusPending, String department, ThemeDisplay themeDisplay) {
		String companyName = NPSCompany.NPST;
		if(department.equalsIgnoreCase(NPSCompany.NPST)) {
			companyName = NPSCompany.NPST;
		}else if(department.equalsIgnoreCase(NPSCompany.PFM)) {
			companyName = NPSCompany.PFM;
		}else if(department.equalsIgnoreCase(NPSCompany.CRA)) {
			companyName = NPSCompany.CRA;
		}else if(department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
			companyName = NPSCompany.GRIEVANCES;
		}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
			companyName = NPSCompany.CUSTODIAN;
		}
		
		Set<WorkflowTask> workflowTasks = null;
		try {
			workflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), statusPending, companyName);
			//log.info("workflowTasks workflowTasks workflowTasks:::" + workflowTasks.size() + " :: statusPending" +  statusPending);
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		Set<Long> reportUploadId = new HashSet();
			for(long reportMasterId : reportMasterIds) {
				reportUploadId = getApprovedOrSubmittedCountWithReportMasterId(workflowTasks, department,reportMasterId,reportUploadId,statusPending);
			}
			//log.info("reportUploadId::::::::::" + reportUploadId.size());
		return reportUploadId;
	}
	
	public Set<WorkflowTask> getWorkflowTaskSubmmitedToNPS(long companyId, boolean isCompleted, String companyName){
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
			}else if(companyName.equalsIgnoreCase(NPSCompany.PFM) || (companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN))) {
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
			} if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				amRole = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.CUSTODIAN_AM);
				dgmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_DGM);
				gmRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_GM);
				mgrRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_MGR);
				//faRole = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.CUSTODIAN_FA);
			}
			
			if(Validator.isNotNull(amRole)) {
				List<WorkflowTask> assigneToMe1 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, amRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe1);
				List<User> users = UserLocalServiceUtil.getRoleUsers(amRole.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(dgmRole)) {
				List<WorkflowTask> assigneToMe2 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, dgmRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe2);
				List<User> users = UserLocalServiceUtil.getRoleUsers(dgmRole.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(gmRole)) {
				List<WorkflowTask> assigneToMe3 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, gmRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe3);
				List<User> users = UserLocalServiceUtil.getRoleUsers(gmRole.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(mgrRole)) {
				List<WorkflowTask> assigneToMe3 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, mgrRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe3);
				List<User> users = UserLocalServiceUtil.getRoleUsers(mgrRole.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(faRole)) {
				List<WorkflowTask> assigneToMe4 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, faRole.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe4);
				List<User> users = UserLocalServiceUtil.getRoleUsers(faRole.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
		}catch (Exception e) {
			log.error("Exception : "+e.getMessage(),e);
		}
		
		return workflowTasks;
	}
	
	public Set<Long> getApprovedOrSubmittedCountWithReportMasterId(Set<WorkflowTask> workflowTasks, String department, long reportMasterId, Set<Long> reportUploadLogId,boolean status) {
		//List<Long> reportUploadLogId = new ArrayList<Long>();
		Set<Long> addedWorkflowTasks = new HashSet<>();
		 for (WorkflowTask itr : workflowTasks) {
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				if(!addedWorkflowTasks.contains(applicationId)) {
					//log.info("applicationId:::" + applicationId);
				String entryClassName = String.valueOf(maps.get("entryClassName"));
		    	if(department.equalsIgnoreCase(NPSCompany.NPST)) {
					if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
						ReportUploadLogMaker reportUploadLogMaker = null;
						try {
							reportUploadLogMaker = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
						} catch (PortalException e) {
							log.error(e.getMessage());
						}
						//log.info("reportMasterId:: " + reportMasterId + " :: reportUploadLogMaker.getReportMasterId()" + reportUploadLogMaker.getReportMasterId());
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId() != 0 && reportMasterId == reportUploadLogMaker.getReportMasterId()) {
							if(status) {
								if(reportUploadLogMaker.getStatus() == 0)
								reportUploadLogId.add(reportUploadLogMaker.getReportUploadLogId());
							}else {
								reportUploadLogId.add(reportUploadLogMaker.getReportUploadLogId());
							}
							
						}
					} 
					if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
							ReportUploadLogSupervisor reportUploadLogSupervisor = null;
							try {
								reportUploadLogSupervisor = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
							} catch (PortalException e) {
								log.error(e.getMessage());
							}
							//log.info("reportMasterId:: " + reportMasterId + " :: reportUploadLogSupervisor.getReportMasterId()" + reportUploadLogSupervisor.getReportMasterId());
							if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId() != 0 && reportMasterId == reportUploadLogSupervisor.getReportMasterId()) {
								if(status) {
									if(reportUploadLogSupervisor.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogSupervisor.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogSupervisor.getReportUploadLogId());
								}
							}
					}
					if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
						ReportUploadLogNPST reportUploadLogNPST = null;
						try {
							reportUploadLogNPST = ReportUploadLogNPSTLocalServiceUtil.getReportUploadLogNPST(applicationId);
						} catch (Exception e) {
							log.error(e.getMessage());
						}
						//log.info("reportMasterId:: " + reportMasterId + " :: reportUploadLogSupervisor.getReportMasterId()" + reportUploadLogNPST.getReportMasterId());
						if(reportUploadLogNPST != null && reportUploadLogNPST.getFileEntryId()!=0 && reportMasterId == reportUploadLogNPST.getReportMasterId()) {
							if(status) {
								if(reportUploadLogNPST.getStatus() == 0)
								reportUploadLogId.add(reportUploadLogNPST.getReportUploadLogId());
							}else {
								reportUploadLogId.add(reportUploadLogNPST.getReportUploadLogId());
							}
						}
					}
		    	 }else if(department.equalsIgnoreCase(NPSCompany.PFM)) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							ReportUploadLogPFM reportUploadLogPFM = null;
							try {
								reportUploadLogPFM = reportUploadLogPFMLocalService.getReportUploadLogPFM(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogPFM != null && reportUploadLogPFM.getFileEntryId()!=0 && reportMasterId == reportUploadLogPFM.getReportMasterId()) {
								if(status) {
									if(reportUploadLogPFM.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogPFM.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogPFM.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							ReportUploadLogPFMAM reportUploadLogPFMAM = null;
							try {
								reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogPFMAM != null && reportUploadLogPFMAM.getFileEntryId()!=0 && reportMasterId == reportUploadLogPFMAM.getReportMasterId()) {
								if(status) {
									if(reportUploadLogPFMAM.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogPFMAM.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogPFMAM.getReportUploadLogId());
								}
							}
						}
						
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							ReportUploadLogPFMCRA reportUploadLogPFMCRA = null;
							try {
								reportUploadLogPFMCRA = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogPFMCRA != null && reportUploadLogPFMCRA.getFileEntryId()!=0 && reportMasterId == reportUploadLogPFMCRA.getReportMasterId()) {
								if(status) {
									if(reportUploadLogPFMCRA.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogPFMCRA.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogPFMCRA.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							InputQuarterlyInterval inputQuarterlyInterval  = null;
							try {
								inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(inputQuarterlyInterval != null && reportMasterId == inputQuarterlyInterval.getReportMasterId()) {
								if(status) {
									if(inputQuarterlyInterval.getStatus() == 0)
									reportUploadLogId.add(inputQuarterlyInterval.getReportUploadLogId());
								}else {
									reportUploadLogId.add(inputQuarterlyInterval.getReportUploadLogId());
								}
							}
						}
						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							MnCompCertificate mnCompCertificate = null;
							try {
								mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(mnCompCertificate != null && reportMasterId == mnCompCertificate.getReportMasterId()) {
								if(status) {
									if(mnCompCertificate.getStatus() == 0)
									reportUploadLogId.add(mnCompCertificate.getReportUploadLogId());
								}else {
									reportUploadLogId.add(mnCompCertificate.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							AnnualCompCertificate annualCompCertificate = null;
							try {
								annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(annualCompCertificate != null && reportMasterId == annualCompCertificate.getReportMasterId()) {
								if(status) {
									if(annualCompCertificate.getStatus() == 0)
									reportUploadLogId.add(annualCompCertificate.getReportUploadLogId());
								}else {
									reportUploadLogId.add(annualCompCertificate.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							QtrStewardshipReport qtrStewardshipReport = null;
							try {
								qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(qtrStewardshipReport != null && reportMasterId == qtrStewardshipReport.getReportMasterId()) {
								if(status) {
									if(qtrStewardshipReport.getStatus() == 0)
									reportUploadLogId.add(qtrStewardshipReport.getReportUploadLogId());
								}else {
									reportUploadLogId.add(qtrStewardshipReport.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							MnNpaDevelopment mnNpaDevelopment = null;
							try {
								mnNpaDevelopment = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(mnNpaDevelopment != null && reportMasterId == mnNpaDevelopment.getReportMasterId()) {
								if(status) {
									if(mnNpaDevelopment.getStatus() == 0)
									reportUploadLogId.add(mnNpaDevelopment.getReportUploadLogId());
								}else {
									reportUploadLogId.add(mnNpaDevelopment.getReportUploadLogId());
								}
							}
						}
						if(entryClassName.equalsIgnoreCase(HDFCInternal_Audit_Report.class.getName())) {
							HDFCInternal_Audit_Report hDFCInternal_Audit_Report = null;
							try {
								hDFCInternal_Audit_Report = hDFCInternal_Audit_ReportLocalService.getHDFCInternal_Audit_Report(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(hDFCInternal_Audit_Report != null && reportMasterId == hDFCInternal_Audit_Report.getReportMasterId()) {
								if(status) {
									if(hDFCInternal_Audit_Report.getStatus() == 0)
									reportUploadLogId.add(hDFCInternal_Audit_Report.getReportUploadLogId());
								}else {
									reportUploadLogId.add(hDFCInternal_Audit_Report.getReportUploadLogId());
								}
							}
						}
						/* MAN f 1*/
						if(entryClassName.equalsIgnoreCase(Manpowerform_i.class.getName())) {
							Manpowerform_i manpowerform_i = null;
							try {
								manpowerform_i = manpowerform_iLocalService.getManpowerform_i(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(manpowerform_i != null && reportMasterId == manpowerform_i.getReportMasterId()) {
								if(status) {
									if(manpowerform_i.getStatus() == 0)
									reportUploadLogId.add(manpowerform_i.getReportUploadLogId());
								}else {
									reportUploadLogId.add(manpowerform_i.getReportUploadLogId());
								}
							}
						}
						/* MAN f 2*/
						if(entryClassName.equalsIgnoreCase(Manpowerform_ii.class.getName())) {
							Manpowerform_ii manpowerform_ii = null;
							try {
								manpowerform_ii = manpowerform_iiLocalService.getManpowerform_ii(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(manpowerform_ii != null && reportMasterId == manpowerform_ii.getReportMasterId()) {
								if(status) {
									if(manpowerform_ii.getStatus() == 0)
									reportUploadLogId.add(manpowerform_ii.getReportUploadLogId());
								}else {
									reportUploadLogId.add(manpowerform_ii.getReportUploadLogId());
								}
							}
						}
						
						/* Report Upload By PFM-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
							ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
							try {
								reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogPFMAMPFRDA != null && reportMasterId == reportUploadLogPFMAMPFRDA.getReportMasterId()) {
								if(status) {
									if(reportUploadLogPFMAMPFRDA.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogPFMAMPFRDA.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogPFMAMPFRDA.getReportUploadLogId());
								}
							}
						}
						
						/* Detail audit report */
						if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
							DAR dar = null;
							try {
								dar =darLocalService.getDAR(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(dar != null && reportMasterId == dar.getReportMasterId()) {
								if(status) {
									if(dar.getStatus() == 0)
									reportUploadLogId.add(dar.getReportUploadLogId());
								}else {
									reportUploadLogId.add(dar.getReportUploadLogId());
								}
							}
						}
						
						/* PFM Quaterly Internal audit report */
						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = null;
							try {
								pfm_Qr_Internal_Audit_Report =pfm_Qr_Internal_Audit_ReportLocalService.getPfm_Qr_Internal_Audit_Report(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(pfm_Qr_Internal_Audit_Report != null && reportMasterId == pfm_Qr_Internal_Audit_Report.getReportMasterId()) {
								if(status) {
									if(pfm_Qr_Internal_Audit_Report.getStatus() == 0)
									reportUploadLogId.add(pfm_Qr_Internal_Audit_Report.getReportUploadLogId());
								}else {
									reportUploadLogId.add(pfm_Qr_Internal_Audit_Report.getReportUploadLogId());
								}
							}
						}
						
						/* PFM Quaterly Internal audit report */
						if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							PFM_hy_comp_cert pfm_hy_comp_cert = null;
							try {
								pfm_hy_comp_cert =pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(pfm_hy_comp_cert != null && reportMasterId == pfm_hy_comp_cert.getReportMasterId()) {
								if(status) {
									if(pfm_hy_comp_cert.getStatus() == 0)
									reportUploadLogId.add(pfm_hy_comp_cert.getReportUploadLogId());
								}else {
									reportUploadLogId.add(pfm_hy_comp_cert.getReportUploadLogId());
								}
							}
						}
		    	   }else if(department.equalsIgnoreCase(NPSCompany.CRA)) {
		    		   if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							ReportUploadLogCRA reportUploadLogCRA = null;
							try {
								reportUploadLogCRA = reportUploadLogCRALocalService.getReportUploadLogCRA(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId()!=0 && reportMasterId == reportUploadLogCRA.getReportMasterId()) {
								if(status) {
									if(reportUploadLogCRA.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogCRA.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogCRA.getReportUploadLogId());
								}
							}
						}
		    	   }else if(department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {

						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
							ReportUploadLogGrievances reportUploadLogGrievances = null;
							try {
								reportUploadLogGrievances = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0 && reportMasterId == reportUploadLogGrievances.getReportMasterId()) {
								if(status) {
									if(reportUploadLogGrievances.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogGrievances.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogGrievances.getReportUploadLogId());
								}
							}
						}
						/* Report Upload By Griev-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
							ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;
							try {
								reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogGrievAMPFRDA != null && reportMasterId == reportUploadLogGrievAMPFRDA.getReportMasterId()) {
								if(status) {
									if(reportUploadLogGrievAMPFRDA.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogGrievAMPFRDA.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogGrievAMPFRDA.getReportUploadLogId());
								}
							}
						}
		    	   }else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
		    		   

						if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
							ReportUploadLogCustodian reportUploadLogCustodian = null;
							try {
								reportUploadLogCustodian = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0 && reportMasterId == reportUploadLogCustodian.getReportMasterId()) {
								if(status) {
									if(reportUploadLogCustodian.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogCustodian.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogCustodian.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
							ReportUploadLogPFMCustodian reportUploadLogPFMCustodian = null;
							try {
								reportUploadLogPFMCustodian = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0 && reportMasterId == reportUploadLogPFMCustodian.getReportMasterId()) {
								if(status) {
									if(reportUploadLogPFMCustodian.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogPFMCustodian.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogPFMCustodian.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
							CustodianCompForm custodianCompForm = null;
							try {
								custodianCompForm = custodianCompFormLocalService.getCustodianCompForm(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(custodianCompForm != null && reportMasterId == custodianCompForm.getReportMasterId()) {
								if(status) {
									if(custodianCompForm.getStatus() == 0)
									reportUploadLogId.add(custodianCompForm.getReportUploadLogId());
								}else {
									reportUploadLogId.add(custodianCompForm.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
							CustIAR custIAR = null;
							try {
								custIAR = custIARLocalService.getCustIAR(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(custIAR != null && reportMasterId == custIAR.getReportMasterId()) {
								if(status) {
									if(custIAR.getStatus() == 0)
									reportUploadLogId.add(custIAR.getReportUploadLogId());
								}else {
									reportUploadLogId.add(custIAR.getReportUploadLogId());
								}
							}
						}
						
						if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
							CustAnnualAuditReport custAnnualAuditReport = null;
							try {
								custAnnualAuditReport = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(custAnnualAuditReport != null && reportMasterId == custAnnualAuditReport.getReportMasterId()) {
								if(status) {
									if(custAnnualAuditReport.getStatus() == 0)
									reportUploadLogId.add(custAnnualAuditReport.getReportUploadLogId());
								}else {
									reportUploadLogId.add(custAnnualAuditReport.getReportUploadLogId());
								}
							}
						}
						
						/* Report Upload By Cust-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName())) {
							ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = null;
							try {
								reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(reportUploadLogCustAMPFRDA != null && reportMasterId == reportUploadLogCustAMPFRDA.getReportMasterId()) {
								if(status) {
									if(reportUploadLogCustAMPFRDA.getStatus() == 0)
									reportUploadLogId.add(reportUploadLogCustAMPFRDA.getReportUploadLogId());
								}else {
									reportUploadLogId.add(reportUploadLogCustAMPFRDA.getReportUploadLogId());
								}
							}
						}
		    		   
		    	   }
				//count++ ;
				}
				addedWorkflowTasks.add(applicationId);
		    }
		 return reportUploadLogId;
	}
	
	
	/* CEO Dashboard New Changes Ends*/
	

	@Reference
	private ReportMasterLocalService reportMasterLocalService;

	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private ReportUploadLogMakerLocalService reportUploadLogMakerLocalService;
	
	@Reference
	private ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;
	
	@Reference
	private DLURLHelper dlURLHelper;
	
	@Reference
	private DLAppService dlAppService;
	
	@Reference
	private ReportUploadLogNPSTLocalService reportUploadLogNPSTLocalService;
	
	@Reference
	private ReportUploadLogPFMLocalService reportUploadLogPFMLocalService;
	
	@Reference
	private ReportUploadLogPFMAMLocalService reportUploadLogPFMAMLocalService;
	
	@Reference
	private ReportUploadLogPFMCRALocalService reportUploadLogPFMCRALocalService;
	
	
	// Newly Added
	
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
	private ReportUploadLogPFMAMPFRDALocalService reportUploadLogPFMAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;


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
	private ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
/* import for graph starts */
	
	@Reference
	private DLFileEntryLocalService dlFileEntryLocalService;
	
	@Reference
	private HDFCInternal_Audit_ReportLocalService hDFCInternal_Audit_ReportLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	@Reference
	private Manpowerform_iLocalService manpowerform_iLocalService;
	
	@Reference
	private Manpowerform_iiLocalService manpowerform_iiLocalService;
	
	@Reference
	private DARLocalService darLocalService;
	
	@Reference
	private Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;
	
	@Reference
	private PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
	
	/* import for graph starts */
	
}
