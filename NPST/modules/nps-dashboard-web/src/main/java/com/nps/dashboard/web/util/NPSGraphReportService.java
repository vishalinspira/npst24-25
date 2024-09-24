package com.nps.dashboard.web.util;

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
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogMakerLocalService;
import com.daily.average.service.service.ReportUploadLogNPSTLocalService;
import com.daily.average.service.service.ReportUploadLogNPSTLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.nps.dashboard.web.model.DepartmentData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCommonConstants;
import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;

@Component(immediate = true, service = NPSGraphReportService.class)
public class NPSGraphReportService {
	private static final Log log = LogFactoryUtil.getLog(NPSGraphReportService.class);
	
	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private ReportUploadLogMakerLocalService reportUploadLogMakerLocalService;
	
	@Reference
	private ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;
	
	@Reference
	private ReportUploadLogNPSTLocalService reportUploadLogNPSTLocalService;
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
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
	
	
	/* import for graph starts */
	
	@Reference
	private DLFileEntryLocalService dlFileEntryLocalService;
	
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
	private HDFCInternal_Audit_ReportLocalService hDFCInternal_Audit_ReportLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	@Reference
	private CustodianCompFormLocalService custodianCompFormLocalService;
	
	@Reference
	private CustIARLocalService custIARLocalService; 
	
	@Reference
	private CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;
	
	@Reference
	private ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService;
	
	@Reference
	private Manpowerform_iLocalService manpowerform_iLocalService;
	
	@Reference
	private Manpowerform_iiLocalService manpowerform_iiLocalService;
	
	@Reference
	private ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogPFMAMPFRDALocalService reportUploadLogPFMAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDALocalService;
	
	@Reference
	private DARLocalService darLocalService;
	
	@Reference
	Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;
	
	@Reference
	PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
	
	/* import for graph starts */
	
	
	
	
	public JSONObject getDepartmentReport(ThemeDisplay themeDisplay) {
		JSONArray departmentReportArray = JSONFactoryUtil.createJSONArray();
		
		DepartmentData trusteeBank = getDepartmentData(NPSCompany.NPST,themeDisplay);
		DepartmentData pfm = getDepartmentData(NPSCompany.PFM,themeDisplay);
		DepartmentData cra = getDepartmentData(NPSCompany.CRA,themeDisplay);
		DepartmentData grievances = getDepartmentData(NPSCompany.GRIEVANCES,themeDisplay);
		DepartmentData custodian = getDepartmentData(NPSCompany.CUSTODIAN,themeDisplay);
		
		departmentReportArray.put(getDepartmentObject(NPSCompany.NPST, trusteeBank.getTotalCount(), NPSCompany.NPST));
		departmentReportArray.put(getDepartmentObject(NPSCompany.PFM, pfm.getTotalCount(), NPSCompany.PFM));
		departmentReportArray.put(getDepartmentObject(NPSCompany.GRIEVANCES, grievances.getTotalCount(), NPSCompany.GRIEVANCES));
		departmentReportArray.put(getDepartmentObject(NPSCompany.CRA, cra.getTotalCount(), NPSCompany.CRA));
		departmentReportArray.put(getDepartmentObject(NPSCompany.CUSTODIAN, custodian.getTotalCount(), NPSCompany.CUSTODIAN));
		
		/**
		 * Trustee Bank series drilldown
		 */
		JSONArray drilldownSeries = JSONFactoryUtil.createJSONArray();
		drilldownSeries.put(getDepartmentWiseData(trusteeBank, NPSCompany.NPST));
		drilldownSeries.put(getDepartmentWiseData(pfm, NPSCompany.PFM));
		drilldownSeries.put(getDepartmentWiseData(cra, NPSCompany.CRA));
		drilldownSeries.put(getDepartmentWiseData(grievances, NPSCompany.GRIEVANCES));
		drilldownSeries.put(getDepartmentWiseData(custodian, NPSCompany.CUSTODIAN));
		
		/*
		 * String grivenceData =
		 * "{name:\"Grievances\",id:\"Grievances\",data:[[\"Pending\",53.02],[\"Submitted\",53.02],[\"Approved\",53.02]]}";
		 * String craData =
		 * "{name:\"CRA\",id:\"CRA\",data:[[\"Pending\",53.02],[\"Submitted\",53.02],[\"Approved\",53.02]]}";
		 * String Custodian =
		 * "{name:\"Custodian\",id:\"Custodian\",data:[[\"Pending\",53.02],[\"Submitted\",53.02],[\"Approved\",53.02]]}";
		 * 
		 * drilldownSeries.put(getOtherDepartmentDetails(grivenceData));
		 * drilldownSeries.put(getOtherDepartmentDetails(craData));
		 * drilldownSeries.put(getOtherDepartmentDetails(Custodian));
		 */
		
		JSONObject object = JSONFactoryUtil.createJSONObject();
		object.put("data", departmentReportArray);
		object.put("drilldown", drilldownSeries);
		
		log.info("Pia chart object ::: "+object);
		return object;
	}

	private JSONObject getDepartmentWiseData(DepartmentData trusteeBank, String name) {
		JSONArray dataArray = JSONFactoryUtil.createJSONArray();
		dataArray.put(createSeriesDataArray("Pending", trusteeBank.getPendingCount()));
		dataArray.put(createSeriesDataArray("Submitted", trusteeBank.getSubmittedCount()));
		dataArray.put(createSeriesDataArray("Approved", trusteeBank.getApprovedCount()));
		
		JSONObject object = JSONFactoryUtil.createJSONObject();
		object.put("name", name);
		object.put("id", name);
		object.put("data", dataArray);
		
		return object;
	}
	
	private JSONArray createSeriesDataArray(String lebel, long count) {
		JSONArray dataArray = JSONFactoryUtil.createJSONArray();
		dataArray.put(lebel);
		dataArray.put((double)count);
		
		return dataArray;
	}

	private JSONObject getDepartmentObject(String departmentName, double count, String drilldownName) {
		JSONObject departmentObject = JSONFactoryUtil.createJSONObject();
		departmentObject.put("name", departmentName);
		departmentObject.put("y", count);
		departmentObject.put("drilldown", drilldownName);
		departmentObject.put("type", "mainSeries");
		
		return departmentObject;
	}
	
	/**
	 * This method should be updated once all department data are ready to make the graph dynamic for all the departments
	 * @return
	 */
	private JSONObject getOtherDepartmentDetails(String data) {
		try {
			return JSONFactoryUtil.createJSONObject(data);
		} catch (JSONException e) {
			log.error("Exception on parsing data : "+e.getMessage());
		}
		
		return JSONFactoryUtil.createJSONObject();
	}
	
	public DepartmentData getDepartmentData(String department, ThemeDisplay themeDisplay) {
		
		List<ReportMaster> reportMasters = reportMasterLocalService.getByDepartment(department);
		List<Long> reportMasterIds = reportMasters.stream().map(ReportMaster::getReportMasterId).collect(Collectors.toList());
		/** 
		 * Get Submitted and approved reports count
		 */
		List<Long> reportUploadLogIds = null;
		if(!reportMasterIds.isEmpty()) {
			List<ReportUploadLog> reportUploadLogs = null;
			try {
				//reportUploadLogs = reportUploadLogLocalService.getByReportMasterIdsAndUploaded_i(reportMasterIds, 1);
				
				reportUploadLogs = ReportUploadLogLocalServiceUtil.getByReportMasterIdsAndUploaded_i(reportMasterIds, 1);
				log.info("reportUpload Logs size::" + reportUploadLogs.size());
				reportUploadLogIds = reportUploadLogs.stream().map(ReportUploadLog::getReportUploadLogId).collect(Collectors.toList());
			} catch (Exception e) {
				log.error("Error in getting reportUploadLogs:::" + e.getMessage());
			}
			
		}
		
		if(reportUploadLogIds != null) {
			if(NPSCompany.NPST.equalsIgnoreCase(department)) {
				//return getTrusteeBankDetails(reportMasterIds, reportUploadLogIds);
				return getCountDetailsTrusteeBank(reportMasterIds, reportUploadLogIds, themeDisplay);
			}else if(NPSCompany.PFM.equalsIgnoreCase(department)) {
				//return getPFMDetails(reportMasterIds, reportUploadLogIds);
				return getCountDetailsPFM(reportMasterIds, reportUploadLogIds, themeDisplay);
			}else if(NPSCompany.CRA.equalsIgnoreCase(department)) {
				//return getCRADetails(reportMasterIds, reportUploadLogIds);
				return getCountDetailsCRA(reportMasterIds, reportUploadLogIds, themeDisplay);
			}else if(NPSCompany.GRIEVANCES.equalsIgnoreCase(department)) {
				//return getGrievancesDetails(reportMasterIds, reportUploadLogIds);
				return getCountDetailsGrievances(reportMasterIds, reportUploadLogIds, themeDisplay);
			}else if(NPSCompany.CUSTODIAN.equalsIgnoreCase(department)) {
				//return getCustodianDetails(reportMasterIds, reportUploadLogIds);
				return getCountDetailsCustodian(reportMasterIds, reportUploadLogIds,themeDisplay);
			}
		}
		
		return new DepartmentData();
	}
	
	public DepartmentData getTrusteeBankDetails(List<Long> reportMasterIds, List<Long> reportUploadLogIds ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		long submittedCount = reportUploadLogSupervisorLocalService.countReportUploadLogSupervisorByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING)
		 		+ reportUploadLogMakerLocalService.countReportUploadLogMakerByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING)
				+ reportUploadLogNPSTLocalService.countReportUploadLogNPSTByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING);;
		
		log.info("Submitted reprorts : "+submittedCount);
		
		long approvedCount = reportUploadLogMakerLocalService.countReportUploadLogMakerByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED)
				+ reportUploadLogSupervisorLocalService.countReportUploadLogSupervisorByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED)
				+ reportUploadLogNPSTLocalService.countReportUploadLogNPSTByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED);
		log.info("Approved report count :: "+approvedCount);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	private DepartmentData getPFMDetails(List<Long> reportMasterIds, List<Long> reportUploadLogIds ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		long submittedCount = reportUploadLogPFMLocalService.countReportUploadLogPFMByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING)
		 	+ reportUploadLogPFMAMLocalService.countReportUploadLogPFMAMByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING)
				+ reportUploadLogPFMCRALocalService.countReportUploadLogPFMCRAByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING);;
		
		 submittedCount = 0l;
		
		log.info("Submitted reprorts : "+submittedCount);
		
		long approvedCount = reportUploadLogPFMLocalService.countReportUploadLogPFMByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED)
				+ reportUploadLogPFMAMLocalService.countReportUploadLogPFMAMByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED)
				+ reportUploadLogPFMCRALocalService.countReportUploadLogPFMCRAByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED);
		 approvedCount = 0l;
		log.info("Approved report count :: "+approvedCount);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	private DepartmentData getCRADetails(List<Long> reportMasterIds, List<Long> reportUploadLogIds ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		long submittedCount = reportUploadLogCRALocalService.countReportUploadLogCRAByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING);
		submittedCount = 0l;
		
		
		log.info("Submitted reprorts : "+submittedCount);
		
		long approvedCount = reportUploadLogCRALocalService.countReportUploadLogCRAByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED);
		approvedCount = 0l;
		
		log.info("Approved report count :: "+approvedCount);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	private DepartmentData getGrievancesDetails(List<Long> reportMasterIds, List<Long> reportUploadLogIds ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		long submittedCount = reportUploadLogGrievancesLocalService.countReportUploadLogGrievancesByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING);
		submittedCount = 0l;
		
		log.info("Submitted reprorts : "+submittedCount);
		
		long approvedCount = reportUploadLogGrievancesLocalService.countReportUploadLogGrievancesByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED);
		approvedCount = 0l;
		log.info("Approved report count :: "+approvedCount);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	private DepartmentData getCustodianDetails(List<Long> reportMasterIds, List<Long> reportUploadLogIds ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		long submittedCount = reportUploadLogCustodianLocalService.countReportUploadLogCustodianByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING);
		
		submittedCount = 0l;
		log.info("Submitted reprorts : "+submittedCount);
		
		long approvedCount = reportUploadLogCustodianLocalService.countReportUploadLogCustodianByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED);
		approvedCount = 0l;
		log.info("Approved report count :: "+approvedCount);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	
	/**
	 * This is for bar chart of trustee bank
	 * @param themeDisplay 
	 * @param trusteeBank
	 * @return
	 */
	public JSONObject getDepartmentChartData(String department, ThemeDisplay themeDisplay) {
		JSONObject chartObject = JSONFactoryUtil.createJSONObject();
		DepartmentData departmentData = getDepartmentData(department,themeDisplay);
		
		JSONArray chartSeriesArray = JSONFactoryUtil.createJSONArray();
		chartSeriesArray.put(createTrustessBankSeriesDataObject(departmentData.getPendingCount(), "Pending", "Pending", "#d9d92c"));
		chartSeriesArray.put(createTrustessBankSeriesDataObject(departmentData.getSubmittedCount(), "Submitted", "Submitted", "#7cb5ec"));
		chartSeriesArray.put(createTrustessBankSeriesDataObject(departmentData.getApprovedCount(), "Approved", "Approved", "#5b954f"));
		
		JSONArray drilldownArray = JSONFactoryUtil.createJSONArray();//getTrusteeBankDrillDownArray(department,themeDisplay);
		
		chartObject.put("seriesData", chartSeriesArray);
		chartObject.put("drilldownData", drilldownArray);
		log.info("chartObject : "+chartObject);
		return chartObject;
	}
	
	/**
	 * This is for drill down data
	 * @param department
	 * @param themeDisplay
	 * @return
	 */
	public JSONObject getDepartmentChartDrilldownArray(String department, ThemeDisplay themeDisplay) {
		JSONObject chartObject = JSONFactoryUtil.createJSONObject();
		DepartmentData departmentData = getDepartmentData(department,themeDisplay);
		
		JSONArray chartSeriesArray = JSONFactoryUtil.createJSONArray();
		chartSeriesArray.put(createTrustessBankSeriesDataObject(departmentData.getPendingCount(), "Pending", "Pending", "#d9d92c"));
		chartSeriesArray.put(createTrustessBankSeriesDataObject(departmentData.getSubmittedCount(), "Submitted", "Submitted", "#7cb5ec"));
		chartSeriesArray.put(createTrustessBankSeriesDataObject(departmentData.getApprovedCount(), "Approved", "Approved", "#5b954f"));
		
		JSONArray drilldownArray = getTrusteeBankDrillDownArray(department,themeDisplay);
		
		chartObject.put("seriesData", chartSeriesArray);
		chartObject.put("drilldownData", drilldownArray);
		log.info("chartObject : "+chartObject);
		return chartObject;
	}

	private JSONArray getTrusteeBankDrillDownArray(String department, ThemeDisplay themeDisplay) {
		JSONArray drilldownArray = JSONFactoryUtil.createJSONArray();
		List<String> reportTypes = new ArrayList<>();
		if(NPSCompany.NPST.equalsIgnoreCase(department)) {
			reportTypes = NPSCommonConstants.allReportTypes;
		}else if(NPSCompany.PFM.equalsIgnoreCase(department)) {
			reportTypes = NPSCommonConstants.pfmMakerReportTypes;
		}else if(NPSCompany.CRA.equalsIgnoreCase(department) || NPSCompany.GRIEVANCES.equalsIgnoreCase(department)) {
			reportTypes = NPSCommonConstants.grievanseMakerReportTypes;
		}else if(NPSCompany.CUSTODIAN.equalsIgnoreCase(department)) {
			//reportTypes = NPSCommonConstants.custodianMakerReportTypes;
			reportTypes = Arrays.asList("Monthly", "Quarterly", "Annually");
		}
		log.info("Report types  are : "+reportTypes);
		Map<String, List<Long>> reportMasterIdMap = new LinkedHashMap<>();
		for(String reportType : reportTypes) {
			List<ReportMaster> reportMasters = reportMasterLocalService.getByReportTypeAndDepartment(reportType, department);
			List<Long> reportMasterIds = reportMasters.stream().map(ReportMaster::getReportMasterId).collect(Collectors.toList());
			log.info("Key : "+reportType+" ::: Value : "+reportMasterIds);
			reportMasterIdMap.put(reportType, reportMasterIds);
		}
		log.info("reportMasterIdMap : "+reportMasterIdMap);
		/**
		 * Get pending drilldown objects
		 */
		JSONObject pendingObject = JSONFactoryUtil.createJSONObject();
		pendingObject.put("name", "Pending");
		pendingObject.put("id", "Pending");
		JSONArray dataArray = JSONFactoryUtil.createJSONArray();
		for (Map.Entry<String, List<Long>> entry : reportMasterIdMap.entrySet()) {
			double count = entry.getValue().isEmpty() ? 0.0 : reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(entry.getValue(), 0);
			JSONArray array = JSONFactoryUtil.createJSONArray();
			array.put(entry.getKey());
			array.put(count);
			
			dataArray.put(array);
	    }
		pendingObject.put("data", dataArray);
		drilldownArray.put(pendingObject);
		
		/**
		 * Get Submitted drilldown object
		 */
		JSONObject submittedObject = JSONFactoryUtil.createJSONObject();
		submittedObject.put("name", "Submitted");
		submittedObject.put("id", "Submitted");
		submittedObject.put("data", getUploadedReportCount(reportMasterIdMap, WorkflowConstants.STATUS_PENDING, department,themeDisplay));
		drilldownArray.put(submittedObject);
		
		/**
		 * Get Approved drilldown object
		 */
		JSONObject approvedObject = JSONFactoryUtil.createJSONObject();
		approvedObject.put("name", "Approved");
		approvedObject.put("id", "Approved");
		approvedObject.put("data", getUploadedReportCount(reportMasterIdMap, WorkflowConstants.STATUS_APPROVED, department,themeDisplay));
		drilldownArray.put(approvedObject);
		
		return drilldownArray;
	}

	private JSONArray getUploadedReportCount(Map<String, List<Long>> reportMasterIdMap, int statusPending, String department, ThemeDisplay themeDisplay) {
		JSONArray uploadedReportArray = JSONFactoryUtil.createJSONArray();
		for (Map.Entry<String, List<Long>> entry : reportMasterIdMap.entrySet()) {
			double count = 0;
			if(!entry.getValue().isEmpty()) {
				log.info("Entry Value:::::::::::::::::::::" + entry.getValue());
				boolean status = true;
				if(statusPending == WorkflowConstants.STATUS_PENDING) {
					status = false;
				}else if(statusPending == WorkflowConstants.STATUS_APPROVED) {
					status = true;
				}
				if(NPSCompany.NPST.equalsIgnoreCase(department)) {
					/*count = reportUploadLogMakerLocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending)
							+ reportUploadLogSupervisorLocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending)
							+ reportUploadLogNPSTLocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending);*/
					
					count = getDepartmentTotalCount(entry.getValue(), status, department,themeDisplay);
					//count = 0;
				}else if(NPSCompany.PFM.equalsIgnoreCase(department)) {
					/*count = reportUploadLogPFMLocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending)
							+ reportUploadLogPFMAMLocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending)
							+ reportUploadLogPFMCRALocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending);*/
					//count = 0;
					count = getDepartmentTotalCount(entry.getValue(), status, department,themeDisplay);
				}else if(NPSCompany.CRA.equalsIgnoreCase(department)) {
					//count = reportUploadLogCRALocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending);
					count = getDepartmentTotalCount(entry.getValue(), status, department,themeDisplay);
					//count = 0;
				}else if(NPSCompany.GRIEVANCES.equalsIgnoreCase(department)) {
					//count = reportUploadLogGrievancesLocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending);
					count = getDepartmentTotalCount(entry.getValue(), status, department,themeDisplay);
					//count = 0;
				}else if(NPSCompany.CUSTODIAN.equalsIgnoreCase(department)) {
					//count = reportUploadLogCustodianLocalService.countByReportMasterIdsAndStatus(entry.getValue(), statusPending);
					count = getDepartmentTotalCount(entry.getValue(), status, department,themeDisplay);
					//count = 0;
				}
			}
			JSONArray array = JSONFactoryUtil.createJSONArray();
			array.put(entry.getKey());
			array.put(count);
			
			uploadedReportArray.put(array);
	    }
		return uploadedReportArray;
	}

	private JSONObject createTrustessBankSeriesDataObject(double y, String name, String drilldown, String color) {
		JSONObject object = JSONFactoryUtil.createJSONObject();
		object.put("name", name);
		object.put("color", color);
		object.put("y", y);
		object.put("drilldown", drilldown);
		
		return object;
	}
	
	/**
	 * PFRDA Report
	 * @param themeDisplay 
	 */
	public JSONObject getPFRDADepartmentReport(ThemeDisplay themeDisplay) {
		JSONArray departmentReportArray = JSONFactoryUtil.createJSONArray();
		
		/*DepartmentData trusteeBank = getPfrdaDepartmentDetails(NPSCompany.NPST);
		DepartmentData pfm = getPfrdaDepartmentDetails(NPSCompany.PFM);
		DepartmentData cra = getDepartmentData(NPSCompany.CRA);
		DepartmentData grievances = getDepartmentData(NPSCompany.GRIEVANCES);
		DepartmentData custodian = getDepartmentData(NPSCompany.CUSTODIAN);*/
		
		DepartmentData trusteeBank = getPfrdaDataDetails(NPSCompany.NPST,themeDisplay);
		DepartmentData pfm = getPfrdaDataDetails(NPSCompany.PFM,themeDisplay);
		DepartmentData cra = getPfrdaDataDetails(NPSCompany.CRA,themeDisplay);
		DepartmentData grievances = getPfrdaDataDetails(NPSCompany.GRIEVANCES,themeDisplay);
		DepartmentData custodian = getPfrdaDataDetails(NPSCompany.CUSTODIAN,themeDisplay);
		
		departmentReportArray.put(getDepartmentObject(NPSCompany.NPST, trusteeBank.getTotalCount(), NPSCompany.NPST));
		departmentReportArray.put(getDepartmentObject(NPSCompany.PFM, pfm.getTotalCount(), NPSCompany.PFM));
		departmentReportArray.put(getDepartmentObject(NPSCompany.GRIEVANCES, grievances.getTotalCount(), NPSCompany.GRIEVANCES));
		departmentReportArray.put(getDepartmentObject(NPSCompany.CRA, cra.getTotalCount(), NPSCompany.CRA));
		departmentReportArray.put(getDepartmentObject(NPSCompany.CUSTODIAN, custodian.getTotalCount(), NPSCompany.CUSTODIAN));
		
		/**
		 * Trustee Bank series drilldown
		 */
		JSONArray drilldownSeries = JSONFactoryUtil.createJSONArray();
		drilldownSeries.put(getDepartmentWiseData(trusteeBank, NPSCompany.NPST));
		drilldownSeries.put(getDepartmentWiseData(pfm, NPSCompany.PFM));
		drilldownSeries.put(getDepartmentWiseData(cra, NPSCompany.CRA));
		drilldownSeries.put(getDepartmentWiseData(grievances, NPSCompany.GRIEVANCES));
		drilldownSeries.put(getDepartmentWiseData(custodian, NPSCompany.CUSTODIAN));
		
		JSONObject object = JSONFactoryUtil.createJSONObject();
		object.put("data", departmentReportArray);
		object.put("drilldown", drilldownSeries);
		
		log.info("Pia chart object ::: "+object);
		return object;
	}
	
	public DepartmentData getPfrdaDepartmentDetails(String department) {
		DepartmentData trusteeBank = new DepartmentData();
		String roleName = StringPool.BLANK;
		if(NPSCompany.NPST.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.NPST_AM;
		}else if(NPSCompany.PFM.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.PFM_AM;
		}else if(NPSCompany.CRA.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.CRA_AM;
		}else if(NPSCompany.GRIEVANCES.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.GRIEVANCES_AM;
		}else if(NPSCompany.CUSTODIAN.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.CUSTODIAN_AM;
		}
		
		List<ReportMaster> reportMasters = reportMasterLocalService.getByUploaderRoleAndDepartment(roleName, department);
		List<Long> reportMasterIds = reportMasters.stream().map(ReportMaster::getReportMasterId).collect(Collectors.toList());
		log.info("Report master Id :: "+reportMasterIds);
		long pendingCount = 0;
		List<Long> reportUploadLogIds = null;
		long uploadedReportSize = 0;
		if(!reportMasterIds.isEmpty()) {
			pendingCount = reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
			List<ReportUploadLog> reportUploadLogs = reportUploadLogLocalService.getByReportMasterIdsAndUploaded_i(reportMasterIds, 1);
			reportUploadLogIds = reportUploadLogs.stream().map(ReportUploadLog::getReportUploadLogId).collect(Collectors.toList());
			uploadedReportSize = reportUploadLogs.size();
		}
		
		log.info("Report upload logs ids those are already submitted ::: "+reportUploadLogIds);
		
		long submittedCount = 0, approvedCount = 0;
		if(reportUploadLogIds != null) {
			if(NPSCompany.NPST.equalsIgnoreCase(department)) {
				submittedCount = reportUploadLogNPSTLocalService.countReportUploadLogNPSTByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING);
				approvedCount = reportUploadLogNPSTLocalService.countReportUploadLogNPSTByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED);
			}else if(NPSCompany.PFM.equalsIgnoreCase(department)) {
				submittedCount = reportUploadLogPFMAMLocalService.countReportUploadLogPFMAMByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_PENDING);
				approvedCount = reportUploadLogPFMAMLocalService.countReportUploadLogPFMAMByIdsAndStatus(reportUploadLogIds, WorkflowConstants.STATUS_APPROVED);
			}
		}
		
		
		trusteeBank.setTotalCount(pendingCount + uploadedReportSize);
		trusteeBank.setPendingCount(pendingCount);
		trusteeBank.setSubmittedCount(submittedCount);
		trusteeBank.setApprovedCount(approvedCount);
		
		return trusteeBank;
	}
	
	public DepartmentData getPfrdaDataDetails(String department, ThemeDisplay themeDisplay) {
		DepartmentData trusteeBank = new DepartmentData();
		long pendingCount = 0;
		long submittedCount = 0, approvedCount = 0;
		String roleName = StringPool.BLANK;
		if(NPSCompany.NPST.equalsIgnoreCase(department)) {
			//pendingCount = 5;
			pendingCount = getPFRDAPendingCount(department);
			roleName = NPSTRoleConstants.NPST_AM;
		}else if(NPSCompany.PFM.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.PFM_AM;
			//pendingCount = 13;
			pendingCount = getPFRDAPendingCount(department);
		}else if(NPSCompany.CRA.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.CRA_AM;
			//pendingCount = 0;
			pendingCount = getPFRDAPendingCount(department);
		}else if(NPSCompany.GRIEVANCES.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.GRIEVANCES_AM;
			//pendingCount = 4;
			pendingCount = getPFRDAPendingCount(department);
		}else if(NPSCompany.CUSTODIAN.equalsIgnoreCase(department)) {
			roleName = NPSTRoleConstants.CUSTODIAN_AM;
			//pendingCount = 3;
			pendingCount = getPFRDAPendingCount(department);
		}
		
		List<ReportMaster> reportMasters = reportMasterLocalService.getByUploaderRoleAndDepartment(roleName, department);
		List<Long> reportMasterIds = reportMasters.stream().map(ReportMaster::getReportMasterId).collect(Collectors.toList());
		log.info("Report master Id :: "+reportMasterIds);
		List<Long> reportUploadLogIds = null;
		long uploadedReportSize = 0;
		if(!reportMasterIds.isEmpty()) {
			List<ReportUploadLog> reportUploadLogs = reportUploadLogLocalService.getByReportMasterIdsAndUploaded_i(reportMasterIds, 1);
			reportUploadLogIds = reportUploadLogs.stream().map(ReportUploadLog::getReportUploadLogId).collect(Collectors.toList());
			uploadedReportSize = reportUploadLogs.size();
		}
		
		log.info("Report upload logs ids those are already submitted ::: "+reportUploadLogIds);
		
		if(reportUploadLogIds != null) {
				boolean isSubmitted = false;
			    boolean isApproved = true;
			    Set<WorkflowTask> submittedWorkflowTasks = null;
			    Set<WorkflowTask> approvedWorkflowTasks = null;
			    try {
			    	submittedWorkflowTasks =  getWorkflowTaskSubmmitedToPFRDA(themeDisplay.getCompanyId(), isSubmitted, department);
			    	log.info("submittedWorkflowTasks size::" + submittedWorkflowTasks.size());
				} catch (Exception e) {
					log.error("Exception in getting workflow task:" + e.getMessage());
				}
			    
			    try {
			    	approvedWorkflowTasks =  getWorkflowTaskSubmmitedToPFRDA(themeDisplay.getCompanyId(), isApproved, department);
			    	log.info("approvedWorkflowTasks size::" + approvedWorkflowTasks.size());
				} catch (Exception e) {
					log.error("Exception in getting workflow task:" + e.getMessage());
				}
				
				submittedCount = getApprovedOrSubmittedCount(submittedWorkflowTasks, department,isSubmitted);
				approvedCount = getApprovedOrSubmittedCount(approvedWorkflowTasks, department,isApproved);
		}
		
		
		
		trusteeBank.setTotalCount(pendingCount + submittedCount + approvedCount);
		trusteeBank.setPendingCount(pendingCount);
		trusteeBank.setSubmittedCount(submittedCount);
		trusteeBank.setApprovedCount(approvedCount);
		return trusteeBank;
		
	}
	
	public int getPFRDAPendingCount(String department) {
		int uploadedReportSize = 0;
		
		List<ReportMaster> reportMasters = null;
		try {
			reportMasters = reportMasterLocalService.getByPfrdaReportAndDepartment(1, department);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(Validator.isNotNull(reportMasters)) {
			List<Long> reportMasterIds = reportMasters.stream().map(ReportMaster::getReportMasterId).collect(Collectors.toList());
			log.info("Report master Id :: "+reportMasterIds);
			List<Long> reportUploadLogIds = null;
			if(!reportMasterIds.isEmpty()) {
				List<ReportUploadLog> reportUploadLogs = null;
				try {
					reportUploadLogs = reportUploadLogLocalService.getByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
					reportUploadLogIds = reportUploadLogs.stream().map(ReportUploadLog::getReportUploadLogId).collect(Collectors.toList());
					uploadedReportSize = reportUploadLogs.size();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}
		
		return uploadedReportSize;
	}
	
	/* Department Wise Chart Data */
	public DepartmentData getCountDetailsTrusteeBank(List<Long> reportMasterIds, List<Long> reportUploadLogIds, ThemeDisplay themeDisplay ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		//log.info("Pending report count :: "+pendingCount);
		
		//log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		
		long submittedCount = 0l;
		long approvedCount = 0l;
	    boolean isSubmitted = false;
	    boolean isApproved = true;
	    Set<WorkflowTask> submittedWorkflowTasks = null;
	    Set<WorkflowTask> approvedWorkflowTasks = null;
	    try {
	    	submittedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isSubmitted, NPSCompany.NPST);
	    	log.info("submittedWorkflowTasks size::" + submittedWorkflowTasks.size());
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
	    
	    try {
	    	approvedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isApproved, NPSCompany.NPST);
	    	log.info("approvedWorkflowTasks size::" + approvedWorkflowTasks.size());
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
	    
	    submittedCount = getApprovedOrSubmittedCount(submittedWorkflowTasks, NPSCompany.NPST,isSubmitted);
	    approvedCount = getApprovedOrSubmittedCount(approvedWorkflowTasks, NPSCompany.NPST,isApproved);
	    log.info("npst submitted count::" + submittedCount);
	    log.info("npst approvedCount count::" + approvedCount);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	public DepartmentData getCountDetailsPFM(List<Long> reportMasterIds, List<Long> reportUploadLogIds, ThemeDisplay themeDisplay ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		
		long submittedCount = 0l;
		long approvedCount = 0l;
		boolean isSubmitted = false;
		boolean isApproved = true;
		Set<WorkflowTask> submittedWorkflowTasks = null;
		Set<WorkflowTask> approvedWorkflowTasks = null;
		try {
			submittedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isSubmitted, NPSCompany.PFM);
			//log.info("submittedWorkflowTasks:::::::: pfm::" + submittedWorkflowTasks.size());
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		try {
			approvedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isApproved, NPSCompany.PFM);
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		submittedCount = getApprovedOrSubmittedCount(submittedWorkflowTasks, NPSCompany.PFM,isSubmitted);
		//log.info("submittedWorkflowTasks submittedCount:::::::: pfm::" + submittedCount);

		approvedCount = getApprovedOrSubmittedCount(approvedWorkflowTasks, NPSCompany.PFM,isApproved);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	public DepartmentData getCountDetailsCRA(List<Long> reportMasterIds, List<Long> reportUploadLogIds, ThemeDisplay themeDisplay ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		
		long submittedCount = 0l;
		long approvedCount = 0l;
		boolean isSubmitted = false;
		boolean isApproved = true;
		Set<WorkflowTask> submittedWorkflowTasks = null;
		Set<WorkflowTask> approvedWorkflowTasks = null;
		try {
			submittedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isSubmitted, NPSCompany.CRA);
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		try {
			approvedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isApproved, NPSCompany.CRA);
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		submittedCount = getApprovedOrSubmittedCount(submittedWorkflowTasks, NPSCompany.CRA,isSubmitted);
		approvedCount = getApprovedOrSubmittedCount(approvedWorkflowTasks, NPSCompany.CRA,isApproved);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	
	public DepartmentData getCountDetailsGrievances(List<Long> reportMasterIds, List<Long> reportUploadLogIds, ThemeDisplay themeDisplay ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		
		long submittedCount = 0l;
		long approvedCount = 0l;
		boolean isSubmitted = false;
		boolean isApproved = true;
		Set<WorkflowTask> submittedWorkflowTasks = null;
		Set<WorkflowTask> approvedWorkflowTasks = null;
		try {
			submittedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isSubmitted, NPSCompany.GRIEVANCES);
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		try {
			approvedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isApproved, NPSCompany.GRIEVANCES);
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		submittedCount = getApprovedOrSubmittedCount(submittedWorkflowTasks, NPSCompany.GRIEVANCES,isSubmitted);
		approvedCount = getApprovedOrSubmittedCount(approvedWorkflowTasks, NPSCompany.GRIEVANCES,isApproved);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	public DepartmentData getCountDetailsCustodian(List<Long> reportMasterIds, List<Long> reportUploadLogIds, ThemeDisplay themeDisplay ) {
		DepartmentData departmentData = new DepartmentData();
		
		int pendingCount = (int)reportUploadLogLocalService.countByReportMasterIdsAndUploaded_i(reportMasterIds, 0);
		log.info("Pending report count :: "+pendingCount);
		
		log.info("report upload log ids those are already submitted : "+reportUploadLogIds);
		// Submitted Reports count (Which is pending as per workflow status)
		
		long submittedCount = 0l;
		long approvedCount = 0l;
		boolean isSubmitted = false;
		boolean isApproved = true;
		Set<WorkflowTask> submittedWorkflowTasks = null;
		Set<WorkflowTask> approvedWorkflowTasks = null;
		try {
			submittedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isSubmitted, NPSCompany.CUSTODIAN);
			log.info("submittedWorkflowTasks:::" + submittedWorkflowTasks.size());
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		try {
			approvedWorkflowTasks =  getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), isApproved, NPSCompany.CUSTODIAN);
			log.info("approvedWorkflowTasks:::" + approvedWorkflowTasks.size());
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
		
		submittedCount = getApprovedOrSubmittedCount(submittedWorkflowTasks, NPSCompany.CUSTODIAN,isSubmitted);
		approvedCount = getApprovedOrSubmittedCount(approvedWorkflowTasks, NPSCompany.CUSTODIAN,isApproved);
		
		departmentData.setPendingCount(pendingCount);
		departmentData.setSubmittedCount(submittedCount);
		departmentData.setApprovedCount(approvedCount);
		departmentData.setTotalCount(pendingCount + submittedCount + approvedCount);
		return departmentData;
	}
	
	
	
	/* Common method to get workflow task */
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
	
	public long getApprovedOrSubmittedCount(Set<WorkflowTask> workflowTasks, String department, boolean status) {
		long count = 0;
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
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId() != 0) {
							if(status) {
								if(reportUploadLogMaker.getStatus() == 0)
									count = count + 1;
							}else {
								count = count + 1;
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
							if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId() != 0) {
								if(status) {
									if(reportUploadLogSupervisor.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
						if(reportUploadLogNPST != null && reportUploadLogNPST.getFileEntryId()!=0) {
							if(status) {
								if(reportUploadLogNPST.getStatus() == 0)
									count = count + 1;
							}else {
								count = count + 1;
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
							if(reportUploadLogPFM != null && reportUploadLogPFM.getFileEntryId()!=0) {
								if(status) {
									if(reportUploadLogPFM.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogPFMAM != null && reportUploadLogPFMAM.getFileEntryId()!=0) {
								if(status) {
									if(reportUploadLogPFMAM.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogPFMCRA != null && reportUploadLogPFMCRA.getFileEntryId()!=0) {
								if(status) {
									if(reportUploadLogPFMCRA.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(inputQuarterlyInterval != null ) {
								if(status) {
									if(inputQuarterlyInterval.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(mnCompCertificate != null ) {
								if(status) {
									if(mnCompCertificate.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(annualCompCertificate != null ) {
								if(status) {
									if(annualCompCertificate.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(qtrStewardshipReport != null ) {
								if(status) {
									if(qtrStewardshipReport.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(mnNpaDevelopment != null ) {
								if(status) {
									if(mnNpaDevelopment.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(hDFCInternal_Audit_Report != null ) {
								if(status) {
									if(hDFCInternal_Audit_Report.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(manpowerform_i != null ) {
								if(status) {
									if(manpowerform_i.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(manpowerform_ii != null ) {
								if(status) {
									if(manpowerform_ii.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogPFMAMPFRDA != null ) {
								if(status) {
									if(reportUploadLogPFMAMPFRDA.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(dar != null ) {
								if(status) {
									if(dar.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
								}
							}
						}
						
						/* PFM quaterly internal audit report */
						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = null;
							try {
								pfm_Qr_Internal_Audit_Report =pfm_Qr_Internal_Audit_ReportLocalService.getPfm_Qr_Internal_Audit_Report(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(pfm_Qr_Internal_Audit_Report != null ) {
								if(status) {
									if(pfm_Qr_Internal_Audit_Report.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
								}
							}
						}
						
						/* PFM quaterly internal audit report */
						if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							PFM_hy_comp_cert pfm_hy_comp_cert = null;
							try {
								pfm_hy_comp_cert =pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(applicationId);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
							if(pfm_hy_comp_cert != null ) {
								if(status) {
									if(pfm_hy_comp_cert.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId()!=0) {
								if(status) {
									if(reportUploadLogCRA.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
								if(status) {
									if(reportUploadLogGrievances.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogGrievAMPFRDA != null ) {
								if(status) {
									if(reportUploadLogGrievAMPFRDA.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
								if(status) {
									if(reportUploadLogCustodian.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
								if(status) {
									if(reportUploadLogPFMCustodian.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(custodianCompForm != null ) {
								if(status) {
									if(custodianCompForm.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(custIAR != null ) {
								if(status) {
									if(custIAR.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(custAnnualAuditReport != null ) {
								if(status) {
									if(custAnnualAuditReport.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
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
							if(reportUploadLogCustAMPFRDA != null ) {
								if(status) {
									if(reportUploadLogCustAMPFRDA.getStatus() == 0)
										count = count + 1;
								}else {
									count = count + 1;
								}
							}
						}
		    		   
		    	   }
				//count++ ;
				}
				addedWorkflowTasks.add(applicationId);
		    }
		 return count;
	}
	
	/* Common method to get workflow task */
	public Set<WorkflowTask> getWorkflowTaskSubmmitedToPFRDA(long companyId, boolean isCompleted, String companyName){
		Set<WorkflowTask> workflowTasks = new HashSet<>();
		Role pfrdaTB = null;
		Role pfrdaPFM = null;
		Role pfrdaCRA = null;
		Role pfrdaGrievance = null;
		Role pfrdaCustodian = null;

		try {
			if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
				pfrdaTB = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.PFRDA_TB);
			}else if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
				pfrdaPFM = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.PFM_PFRDA);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				pfrdaCRA = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.CRA_PFRDA);
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				pfrdaGrievance = RoleLocalServiceUtil.getRole(companyId, NPSTRoleConstants.GRIEVANCES_PFRDA);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				pfrdaCustodian = RoleLocalServiceUtil.getRole(companyId,NPSTRoleConstants.CUSTODIAN_PFRDA);
			}
			
			if(Validator.isNotNull(pfrdaTB)) {
				List<WorkflowTask> assigneToMyRole = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, pfrdaTB.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMyRole);
				List<User> users = UserLocalServiceUtil.getRoleUsers(pfrdaTB.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(pfrdaPFM)) {
				List<WorkflowTask> assigneToMe2 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, pfrdaPFM.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe2);
				List<User> users = UserLocalServiceUtil.getRoleUsers(pfrdaPFM.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(pfrdaCRA)) {
				List<WorkflowTask> assigneToMe3 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, pfrdaCRA.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe3);
				List<User> users = UserLocalServiceUtil.getRoleUsers(pfrdaCRA.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(pfrdaGrievance)) {
				List<WorkflowTask> assigneToMe3 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, pfrdaGrievance.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe3);
				List<User> users = UserLocalServiceUtil.getRoleUsers(pfrdaGrievance.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
			if(Validator.isNotNull(pfrdaCustodian)) {
				List<WorkflowTask> assigneToMe4 = WorkflowTaskManagerUtil.getWorkflowTasksByRole(companyId, pfrdaCustodian.getRoleId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				workflowTasks.addAll(assigneToMe4);
				List<User> users = UserLocalServiceUtil.getRoleUsers(pfrdaCustodian.getRoleId());
				for (User user : users) {
					List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, user.getUserId(), isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
					workflowTasks.addAll(assigneToMe);
				}
			}
			
		}catch (Exception e) {
			log.error("Exception : "+e.getMessage(),e);
		}
		log.info("pfrda workflow size:::::::::::::::::" + workflowTasks.size());
		return workflowTasks;
	}
	
	public long getDepartmentTotalCount(List<Long> reportMasterIds, boolean statusPending, String department, ThemeDisplay themeDisplay) {
		long count  = 0l;
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
			log.info("workflowTasks workflowTasks workflowTasks:::" + workflowTasks.size() + " :: statusPending" +  statusPending);
		} catch (Exception e) {
			log.error("Exception in getting workflow task:" + e.getMessage());
		}
			List<Long> counts = new ArrayList<>();
			for(long reportMasterId : reportMasterIds) {
				long c1 = getApprovedOrSubmittedCountWithReportMasterId(workflowTasks, department,reportMasterId,statusPending);
				counts.add(c1);
			}
			for(long counties : counts) {
				count = count + counties;
			}
		return count;
	}

	
	
	public long getApprovedOrSubmittedCountWithReportMasterId(Set<WorkflowTask> workflowTasks, String department, long reportMasterId,boolean status) {
		long count = 0;
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
								count = count + 1;
							}else {
								count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
								count = count + 1;
							}else {
								count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
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
									count = count + 1;
								}else {
									count = count + 1;
								}
							}
						}
		    		   
		    	   }
				//count++ ;
				}
				addedWorkflowTasks.add(applicationId);
		    }
		 return count;
	}
}
