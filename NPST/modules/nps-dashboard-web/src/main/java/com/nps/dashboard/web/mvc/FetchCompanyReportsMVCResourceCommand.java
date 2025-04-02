package com.nps.dashboard.web.mvc;

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
import com.daily.average.service.model.ReportStatusLog;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogCustAMPFRDA;
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.ReportUploadLogGrievAMPFRDA;
import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.model.ReportUploadLogPFMCustodian;
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
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
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
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.FetchCompanyReportUtil;
import com.nps.dashboard.web.util.NPSDashboardUtil;
import com.nps.dashboard.web.util.NPSTUserUtil;
import com.nps.dashboard.web.util.SaveCacheUtil;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_COMPANY_REPORTS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class FetchCompanyReportsMVCResourceCommand extends BaseMVCResourceCommand {

	private static final Log LOG = LogFactoryUtil.getLog(FetchCompanyReportsMVCResourceCommand.class);
	private static boolean isNonNpsUser = Boolean.FALSE;
	private String companyName = StringPool.BLANK;
	private static boolean isPfrda = Boolean.FALSE;
	private static boolean isMakerOrChecker = Boolean.FALSE;
	private static boolean isPFMNonNpsUser = Boolean.FALSE;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		LOG.info("Calling FetchCompanyReportsMVCResourceCommand..");
		String reportsType = ParamUtil.getString(resourceRequest, "reportsType");
		String folderName = ParamUtil.getString(resourceRequest, "durationType", "Daily");
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		LOG.debug("Report type : "+reportsType+" Duration type : "+folderName);
		
		String department = ParamUtil.getString(resourceRequest, "department");
		String intermediaryName = npstUserUtil.getIntermediaryName(themeDisplay);
		LOG.debug("intermediaryName::" + intermediaryName);
		LOG.debug("reportsType::" + reportsType);
		Company company = portal.getCompany(resourceRequest);
        long companyId = company.getCompanyId();
        String skey = department+themeDisplay.getUserId()+"status";
        if(department.isEmpty()) {
        	companyName = npsDashboardUtil.getCompanyName(companyId, themeDisplay.getUserId());
        }else {
        	companyName = department;
        }
        boolean isPfrdaUserPFM =  roleLocalService.hasUserRole(themeDisplay.getUserId(), companyId, NPSTRoleConstants.PFM_PFRDA, Boolean.FALSE);
		
		LOG.debug("Logged in user department name : "+companyName);
		
		setDefaultValues(themeDisplay);
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		if(reportsType.equals("MAKERS_REPORT_SUMMARY")) {
			
			
			try {
				PrintWriter writer = resourceResponse.getWriter();
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					jsonArray =(getAllDocsForMaker(resourceRequest, themeDisplay, dateFormat, companyId,isNonNpsUser,intermediaryName, isPfrdaUserPFM));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					jsonArray = (getCRAReportsForMaker(resourceRequest, themeDisplay, dateFormat, companyId,isNonNpsUser,intermediaryName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					jsonArray = (getCustodianReportsForMaker(resourceRequest, themeDisplay, dateFormat, companyId,isNonNpsUser,intermediaryName));
				}
				try {
				SaveCacheUtil.saveCache(skey, jsonArray.toString());
				}catch (Exception e) {
					LOG.error(e);
				}
				writer.println(jsonArray);
				writer.flush();
				return;
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
			
		}
		
		if(reportsType.equals("ALL")) {
			//if(isNonNpsUser) {
				LOG.info("Maker or checker report for all types");
				PrintWriter writer = resourceResponse.getWriter();
				//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();;
//					if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
//						jsonArray = npstUserUtil.getSubmittedToPFMUser(resourceRequest, isMakerOrChecker,isNonNpsUser,intermediaryName);
//						FetchCompanyReportUtil fcr=new FetchCompanyReportUtil();
//						List<ReportStatusLog> reportStatusLogs=fcr.getReports(department,reportsType,themeDisplay);
//						jsonArray=getJsonArray(reportStatusLogs);
//					}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
//						jsonArray = npstUserUtil.getSubmittedToCRAUser(resourceRequest, isMakerOrChecker, companyName,isNonNpsUser,intermediaryName);
//					}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
//						jsonArray = npstUserUtil.getSubmittedToCustodianUser(resourceRequest, isPFMNonNpsUser,isNonNpsUser,intermediaryName);
//					}
				FetchCompanyReportUtil fcr=new FetchCompanyReportUtil();
				List<ReportStatusLog> reportStatusLogs=fcr.getSubmitToNPSTReports(department, reportsType, folderName, isNonNpsUser, intermediaryName, companyName, themeDisplay);
				LOG.info("after fetching logs /....");
				if(Validator.isNotNull(reportStatusLogs)) {
				LOG.info("reportStatusLogs size : "+ reportStatusLogs.size()+"  report Type: "+folderName);
				}
				jsonArray=getJsonArray(reportStatusLogs);
				writer.println(jsonArray);
				writer.flush();
				return;
		}
		
		if(reportsType.equals("APPROVED_REPORT_SUMMERY")) {
			skey = department+themeDisplay.getUserId()+"approved";
			try {
				PrintWriter writer = resourceResponse.getWriter();
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					jsonArray =(getApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					jsonArray =(getCRAApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					jsonArray =(getCustodianApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda));
				}
				writer.println(jsonArray);
				writer.flush();
				return;
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
		}
		
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

	private void setDefaultValues(ThemeDisplay themeDisplay) {
		long userId = themeDisplay.getUserId();
		long companyId = themeDisplay.getCompanyId();
		boolean inherited = Boolean.FALSE;
		try {
			if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_PFRDA, userId, inherited);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
				isMakerOrChecker = pfmChecker || pfmMaker;
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				boolean craSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_SUPERVISOR, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_PFRDA, userId, inherited);
				isNonNpsUser = (craMaker || craChecker || craSupervisor);
				isMakerOrChecker = craChecker || craMaker;
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				boolean custodianMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_MAKER, userId, inherited);
				boolean custodianChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_CHECKER, userId, inherited);
				boolean custodianSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_SUPERVISOR, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_PFRDA, userId, inherited);
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				isNonNpsUser = (custodianMaker || custodianChecker || custodianSupervisor || pfmMaker || pfmChecker || pfmSupervisor);
				isMakerOrChecker = custodianChecker || custodianMaker;
				
				isPFMNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
				
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				boolean greMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_MAKER, userId, inherited);
				boolean greChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_CHECKER, userId, inherited);
				boolean greSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_SUPERVISOR, userId, inherited);
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_PFRDA, userId, inherited);
				isNonNpsUser = (greMaker || greChecker || greSupervisor || craMaker || craChecker);
				isMakerOrChecker = greChecker || greMaker || craMaker || craChecker;
			}
			
			
		} catch (Exception e) {
			LOG.error("Exception on setting default values : "+e.getMessage(),e);
		}
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
	public JSONArray getAllDocsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat,
			long companyId, boolean isNonNpsUser, String intermediaryName, boolean isPfrdaUserPFM){
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			List<WorkflowTask> allList = new ArrayList<>();
			
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
						//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
						ReportUploadLogPFM reportUploadLogPfm = null;
						ReportUploadLogPFMAM reportUploadLogPfmAm = null;
						ReportUploadLogPFMCRA reportUploadLogPfmCra = null;
						
						InputQuarterlyInterval inputQuarterlyInterval = null;
						MnCompCertificate mnCompCertificate  = null;
						AnnualCompCertificate annualCompCertificate  = null;
						QtrStewardshipReport qtrStewardshipReport  =null;
						MnNpaDevelopment mnNpaDevelopment  = null;
						ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
						DAR dar = null;
						Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report =null;
						PFM_hy_comp_cert pfm_hy_comp_cert =null;
						
						ReportMaster reportMaster = null;
						DLFileEntry dlFileEntry = null;
						
						if(isPfrdaUserPFM) {
							/* Report Upload By PFM-AM-PFRDA */
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
								LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
								reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
								if(reportUploadLogPFMAMPFRDA != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
								}
							}
						}else {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
								reportUploadLogPfm  = reportUploadLogPfmLocalService.getReportUploadLogPFM(applicationId);
								if(reportUploadLogPfm != null && reportUploadLogPfm.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfm.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfm.getReportMasterId());
								}
							}
							//LOG.info("After PFM check ");
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
								reportUploadLogPfmAm  = reportUploadLogPfmAmLocalService.getReportUploadLogPFMAM(applicationId);
								if(reportUploadLogPfmAm != null && reportUploadLogPfmAm.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmAm.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfmAm.getReportMasterId());
								}
							}
							//LOG.info("After PFM AM check ");
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
								reportUploadLogPfmCra  = reportUploadLogPfmCraLocalService.getReportUploadLogPFMCRA(applicationId);
								if(reportUploadLogPfmCra != null && reportUploadLogPfmCra.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmCra.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfmCra.getReportMasterId());
								}
							}
							/* For Input Quartely Interval */
							if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
								//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
								inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
								if(inputQuarterlyInterval != null) {
									//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getAnnex_iv());
									dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(inputQuarterlyInterval.getReportMasterId());
								}
							}
							/* For MnCompCertificate */
							if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
								//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
								mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
								if(mnCompCertificate != null) {
									dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(mnCompCertificate.getReportMasterId());
								}
							}
							/* For AnnualCompCertificate */
							if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
								//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
								annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
								if(annualCompCertificate!= null) {
									dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(annualCompCertificate.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(annualCompCertificate.getReportMasterId());
								}
							}
							
						
							
							/* For QtrStewardshipReport */
							if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
								//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
								qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(applicationId);
								if(qtrStewardshipReport!= null) {
									//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getAnnexure_c());
									dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(qtrStewardshipReport.getReportMasterId());
								}
							}
							
							/* For NPA Development */
							if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
								//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
								mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
								if(mnNpaDevelopment!= null) {
									dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnNpaDevelopment.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(mnNpaDevelopment.getReportMasterId());
								}
							}
							
							/* Report Upload By PFM-AM-PFRDA */
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
								LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
								reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
								if(reportUploadLogPFMAMPFRDA != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
								}
							}
							
							/* Detail audit report */
							if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
							    dar = null;
								try {
									dar =darLocalService.getDAR(applicationId);
								} catch (Exception e) {
									LOG.error(e.getMessage());
								}
								if(dar != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(dar.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(dar.getReportMasterId());
								}
							}
							
							/* PFM Quaterly Internal audit report */
							if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
								//LOG.info("in =============="+Pfm_Qr_Internal_Audit_Report.class.getName());
								pfm_Qr_Internal_Audit_Report = null;
								try {
									pfm_Qr_Internal_Audit_Report =pfm_Qr_Internal_Audit_ReportLocalService.getPfm_Qr_Internal_Audit_Report(applicationId);
									//LOG.info("in ================="+Pfm_Qr_Internal_Audit_Report.class.getName()+pfm_Qr_Internal_Audit_Report);
								} catch (Exception e) {
									LOG.error(e.getMessage());
								}
								if(pfm_Qr_Internal_Audit_Report != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_Qr_Internal_Audit_Report.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(pfm_Qr_Internal_Audit_Report.getReportMasterId());
								}
							}
							
							/* PFM Quaterly Internal audit report */
							if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
								//LOG.info("in =============="+PFM_hy_comp_cert.class.getName());
								pfm_hy_comp_cert = null;
								try {
									pfm_hy_comp_cert = pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(applicationId);
									//LOG.info("in ================="+PFM_hy_comp_cert.class.getName()+pfm_hy_comp_cert);
								} catch (Exception e) {
									LOG.error(e.getMessage());
								}
								if(pfm_hy_comp_cert != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_hy_comp_cert.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(pfm_hy_comp_cert.getReportMasterId());
								}
							}
						}
			
						
						
						//LOG.info("After PFM CRA check ");
						OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
						
						String comment = npstUserUtil.getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
						/**
						 * Get task assigned to current user.
						 */
						String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
						long assignedToId = npstUserUtil.getCurrentAssigneeUserId(companyId, itr.getWorkflowInstanceId(), comparator);
						
						JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
						//if(Validator.isNotNull(dlFileEntry) && reportMaster != null ) {
							if(reportMaster != null ) {
							
							long reportMasterId = reportMaster.getReportMasterId();
							String reportName=reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
							 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
							ReportUploadLog reportUploadLog = null;
							try {
								reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
							} catch (Exception e) {
								LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
							}
							
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogPfm)) {
								
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogPfm.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogPfm.getCreateDate()), reportUploadLogPfm.getStatus(),
										!comment.isEmpty()?comment:reportUploadLogPfm.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogPfmAm)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogPfmAm.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogPfmAm.getCreateDate()), reportUploadLogPfmAm.getStatus(), 
										!comment.isEmpty()?comment:reportUploadLogPfmAm.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogPfmCra)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogPfmCra.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogPfmCra.getCreateDate()), reportUploadLogPfmCra.getStatus(), 
										!comment.isEmpty()?comment:reportUploadLogPfmCra.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}
							
							else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(inputQuarterlyInterval)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, inputQuarterlyInterval.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(inputQuarterlyInterval.getCreatedate()), inputQuarterlyInterval.getStatus(), 
										!comment.isEmpty()?comment:inputQuarterlyInterval.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(mnCompCertificate)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, mnCompCertificate.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(mnCompCertificate.getCreatedon()), mnCompCertificate.getStatus(), 
										!comment.isEmpty()?comment:mnCompCertificate.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(annualCompCertificate)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, annualCompCertificate.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(annualCompCertificate.getCreatedate()), annualCompCertificate.getStatus(), 
										!comment.isEmpty()?comment:annualCompCertificate.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(qtrStewardshipReport)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, qtrStewardshipReport.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(qtrStewardshipReport.getCreatedon()), qtrStewardshipReport.getStatus(), 
										!comment.isEmpty()?comment:qtrStewardshipReport.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(mnNpaDevelopment)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, mnNpaDevelopment.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(mnNpaDevelopment.getCreatedon()), mnNpaDevelopment.getStatus(), 
										!comment.isEmpty()?comment:mnNpaDevelopment.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser,intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogPFMAMPFRDA)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogPFMAMPFRDA.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogPFMAMPFRDA.getCreateDate()), reportUploadLogPFMAMPFRDA.getStatus(), 
										!comment.isEmpty()?comment:reportUploadLogPFMAMPFRDA.getRemarks(), 
												itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
												isNonNpsUser,intermediaryName,assignedToId);
							}
							else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(dar)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, dar.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(dar.getCreatedon()), dar.getStatus(), 
										!comment.isEmpty()?comment:dar.getRemarks(), 
												itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
												isNonNpsUser,intermediaryName,assignedToId);
							}
							else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, pfm_Qr_Internal_Audit_Report.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(pfm_Qr_Internal_Audit_Report.getCreatedon()), pfm_Qr_Internal_Audit_Report.getStatus(), 
										!comment.isEmpty()?comment:pfm_Qr_Internal_Audit_Report.getRemarks(), 
												itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
												isNonNpsUser,intermediaryName,assignedToId);
							}
							else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(pfm_hy_comp_cert)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, pfm_hy_comp_cert.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(pfm_hy_comp_cert.getCreatedon()), pfm_hy_comp_cert.getStatus(), 
										!comment.isEmpty()?comment:pfm_hy_comp_cert.getRemarks(), 
												itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
												isNonNpsUser,intermediaryName,assignedToId);
							}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								//LOG.info("jsonObject::" + jsonObject);
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
					LOG.info("Class Name : "+entryClassName+" Class PK : "+applicationId);
					ReportUploadLogPFM reportUploadLogPfm = null;
					ReportUploadLogPFMCRA reportUploadLogPfmCra = null;
					ReportUploadLogPFMAM reportUploadLogPfmAm = null;
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					
					InputQuarterlyInterval inputQuarterlyInterval = null;
					MnCompCertificate mnCompCertificate  = null;
					AnnualCompCertificate annualCompCertificate  = null;
					QtrStewardshipReport qtrStewardshipReport  =null;
					MnNpaDevelopment mnNpaDevelopment  = null;
					ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
		
					// Needs to include the entity data fro AM upload
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
						reportUploadLogPfmAm  = reportUploadLogPfmAmLocalService.getReportUploadLogPFMAM(applicationId);
						if(reportUploadLogPfmAm != null && reportUploadLogPfmAm.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmAm.getFileEntryId());
							reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfmAm.getReportMasterId());
						}
					}
					LOG.info("isPfrda::" + isPfrda);
					if(!isPfrda) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							reportUploadLogPfm  = reportUploadLogPfmLocalService.getReportUploadLogPFM(applicationId);
							if(reportUploadLogPfm != null && reportUploadLogPfm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfm.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfm.getReportMasterId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							reportUploadLogPfmCra  = reportUploadLogPfmCraLocalService.getReportUploadLogPFMCRA(applicationId);
							if(reportUploadLogPfmCra != null && reportUploadLogPfmCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmCra.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfmCra.getReportMasterId());
							}
						}
						
						/* For Input Quartely Interval */
						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
							inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
							if(inputQuarterlyInterval != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getAnnex_iv());
								reportMaster = reportMasterLocalService.getReportMaster(inputQuarterlyInterval.getReportMasterId());
							}
						}
						/* For MnCompCertificate */
						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
							mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
							if(mnCompCertificate != null) {
								//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getAnnexure_h());
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(mnCompCertificate.getReportMasterId());
							}
						}
						/* For AnnualCompCertificate */
						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
							annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
							if(annualCompCertificate!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(annualCompCertificate.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(annualCompCertificate.getReportMasterId());
							}
						}
						
						/* For QtrStewardshipReport */
						if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
							qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(applicationId);
							if(qtrStewardshipReport!= null) {
								//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getAnnexure_c());
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(qtrStewardshipReport.getReportMasterId());
							}
						}
						
						/* For NPA Development */
						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
							mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
							if(mnNpaDevelopment!= null) {
								//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry();
								reportMaster = reportMasterLocalService.getReportMaster(mnNpaDevelopment.getReportMasterId());
							}
						}
						
						/* Report Upload By PFM-AM-PFRDA */
						
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
								reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
								if(reportUploadLogPFMAMPFRDA != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
									reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
									//comment = reportUploadLogPFMAMPFRDA.getRemarks();
								//	dueDate = reportUploadLogPFMAMPFRDA.getReportDate();
									//createDate = reportUploadLogPFMAMPFRDA.getStatusDate();
								//	intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMAMPFRDA.getReportUploadLogId());
								}
							}
							
							/* Detail audit report */
							if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
								DAR dar = null;
								try {
									dar =darLocalService.getDAR(applicationId);
								} catch (Exception e) {
									LOG.error(e.getMessage());
								}
								if(dar != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(dar.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(dar.getReportMasterId());
								}
							}
						
					}
					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
					
					String comment = npstUserUtil.getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
					String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
					
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
						
						if(!isPfrda) {
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogPfm)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogPfm.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogPfm.getCreateDate()), reportUploadLogPfm.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = reportUploadLogPfm.getStatus();
							}
							
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(inputQuarterlyInterval)) {
								jsonObject = getReportSummeryObject(reportName, inputQuarterlyInterval.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(inputQuarterlyInterval.getCreatedate()), inputQuarterlyInterval.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = inputQuarterlyInterval.getStatus();
							}
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(mnCompCertificate)) {
								jsonObject = getReportSummeryObject(reportName, mnCompCertificate.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(mnCompCertificate.getCreatedon()), mnCompCertificate.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = mnCompCertificate.getStatus();
							}
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(annualCompCertificate)) {
								jsonObject = getReportSummeryObject(reportName, annualCompCertificate.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(annualCompCertificate.getCreatedate()), annualCompCertificate.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = annualCompCertificate.getStatus();
							}
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(qtrStewardshipReport)) {
								jsonObject = getReportSummeryObject(reportName, qtrStewardshipReport.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(qtrStewardshipReport.getCreatedon()), qtrStewardshipReport.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = qtrStewardshipReport.getStatus();
							}
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(mnNpaDevelopment)) {
								jsonObject = getReportSummeryObject(reportName, mnNpaDevelopment.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(mnNpaDevelopment.getCreatedon()), mnNpaDevelopment.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = mnNpaDevelopment.getStatus();
							}
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogPFMAMPFRDA)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogPFMAMPFRDA.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogPFMAMPFRDA.getCreateDate()), reportUploadLogPFMAMPFRDA.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = reportUploadLogPFMAMPFRDA.getStatus();
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
	
	@SuppressWarnings("unused")
	private boolean matchRoleId(long companyId, long classPK) {
		Role am = null, dgm = null, gm = null, ceo = null;
		try {
			if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
				am = roleLocalService.getRole(companyId, NPSTRoleConstants.PFM_AM);
				dgm = roleLocalService.getRole(companyId, NPSTRoleConstants.PFM_DGM);
				gm = roleLocalService.getRole(companyId, NPSTRoleConstants.PFM_GM);
				ceo = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_CEO);
				return (classPK == (am!=null ? am.getRoleId() : 0l)
						|| classPK == (gm!=null ? gm.getRoleId() : 0l)
						||classPK == (dgm!=null ? dgm.getRoleId() : 0l)
						||classPK == (ceo!=null ? ceo.getRoleId() : 0l));
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				am = roleLocalService.getRole(companyId, NPSTRoleConstants.CRA_AM);
				dgm = roleLocalService.getRole(companyId, NPSTRoleConstants.CRA_DGM);
				gm = roleLocalService.getRole(companyId, NPSTRoleConstants.CRA_GM);
				ceo = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_CEO);
				return (classPK == (am!=null ? am.getRoleId() : 0l)
						|| classPK == (gm!=null ? gm.getRoleId() : 0l)
						||classPK == (dgm!=null ? dgm.getRoleId() : 0l)
						||classPK == (ceo!=null ? ceo.getRoleId() : 0l));
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				am = roleLocalService.getRole(companyId, NPSTRoleConstants.CUSTODIAN_AM);
				dgm = roleLocalService.getRole(companyId, NPSTRoleConstants.CUSTODIAN_DGM);
				gm = roleLocalService.getRole(companyId, NPSTRoleConstants.CUSTODIAN_GM);
				ceo = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_CEO);
				return (classPK == (am!=null ? am.getRoleId() : 0l)
						|| classPK == (gm!=null ? gm.getRoleId() : 0l)
						||classPK == (dgm!=null ? dgm.getRoleId() : 0l)
						||classPK == (ceo!=null ? ceo.getRoleId() : 0l));
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				am = roleLocalService.getRole(companyId, NPSTRoleConstants.GRIEVANCES_AM);
				dgm = roleLocalService.getRole(companyId, NPSTRoleConstants.GRIEVANCES_DGM);
				gm = roleLocalService.getRole(companyId, NPSTRoleConstants.GRIEVANCES_GM);
				ceo = roleLocalService.getRole(companyId, NPSTRoleConstants.NPST_CEO);
				return (classPK == (am!=null ? am.getRoleId() : 0l)
						|| classPK == (gm!=null ? gm.getRoleId() : 0l)
						||classPK == (dgm!=null ? dgm.getRoleId() : 0l)
						||classPK == (ceo!=null ? ceo.getRoleId() : 0l));
			}
		} catch (Exception e) {
			LOG.error("Exception : "+e.getMessage());
		}
		
		return Boolean.FALSE;
	}
	
	@SuppressWarnings("unused")
	private boolean matchUserId(long classPK, long companyId) {
		try {
			if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
				return (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.PFM_AM, false))
				|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.PFM_DGM, false))
				|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.PFM_GM, false))
				|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.NPST_CEO, false));
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				return (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.CRA_AM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.CRA_DGM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.CRA_GM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.NPST_CEO, false));
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				return (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.CUSTODIAN_AM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.CUSTODIAN_DGM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.CUSTODIAN_GM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.NPST_CEO, false));
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				return (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.GRIEVANCES_AM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.GRIEVANCES_DGM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.GRIEVANCES_GM, false))
						|| (roleLocalService.hasUserRole(classPK, companyId, NPSTRoleConstants.NPST_CEO, false));
			}
		}catch (Exception e) {
			LOG.error("Exception : "+e.getMessage());
		}
		
		return Boolean.FALSE;
	}
	
	private JSONObject getReportSummeryObject(String reportName, long reportUploadLogId, long reportMasterId, DLFileEntry dlFileEntry, 
			String reportUploadDate, int reportStatus, String comment, long workflowInstanceId, String assignedTo, String dueDate, String statusKey) {
		String intermediaryName = StringPool.BLANK;
		try {
			ReportUploadLog reportUploadLog = reportUploadLogLocalService.getReportUploadLog(reportUploadLogId);
			intermediaryName = reportUploadLog.getIntermediaryname();
		} catch (PortalException e) {
			LOG.error("Exception on fetching report upload log : "+e.getMessage(),e);
		}
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("fileName", HtmlUtil.escape(reportName));
		jsonObject.put("reportUploadLogId",reportUploadLogId);
		jsonObject.put("reportMasterId", reportMasterId);
		if(dlFileEntry!=null) {
			jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
			jsonObject.put("userName", dlFileEntry.getUserName());
		}else {
			jsonObject.put("url", "");
			jsonObject.put("userName", "");
		}
		jsonObject.put("dueDate", dueDate);
		jsonObject.put("createDate", reportUploadDate);
		jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
		jsonObject.put("remarks", HtmlUtil.escape(comment));
		jsonObject.put("workflowInstanceId", workflowInstanceId);
		String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
		if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
			intermediaryName=intrmedName;
		}
		jsonObject.put("intermediaryName", HtmlUtil.escape(intermediaryName));
		jsonObject.put("assignedTo", assignedTo.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		
		return jsonObject;
	}
	
	private JSONObject getReportSummeryObjectNonNPS(String reportName, long reportUploadLogId, long reportMasterId, DLFileEntry dlFileEntry, 
			String reportUploadDate, int reportStatus, String comment, long workflowInstanceId, String assignedTo, String dueDate, String statusKey,
			boolean isNonNpsUser, String checkIntermediaryName,long assignedToId) {
		String intermediaryName = StringPool.BLANK;
		try {
			ReportUploadLog reportUploadLog = reportUploadLogLocalService.getReportUploadLog(reportUploadLogId);
			intermediaryName = reportUploadLog.getIntermediaryname();
		} catch (PortalException e) {
			LOG.error("Exception on fetching report upload log : "+e.getMessage(),e);
		}
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		LOG.debug("isNON::" + isNonNpsUser + "::::intermediaryName:::" + intermediaryName + "::checkIntermediaryName::" + checkIntermediaryName);
		//LOG.info("assignedTo::" + assignedTo + "npsDashboardUtil.isNonNpsAssignedTo::" + npsDashboardUtil.isNonNpsUser(assignedToId));
		if(isNonNpsUser && intermediaryName.equalsIgnoreCase(checkIntermediaryName) && Validator.isNotNull(checkIntermediaryName) 
				&& npsDashboardUtil.isNonNpsUser(assignedToId)) {
			jsonObject.put("fileName", HtmlUtil.escape(reportName));
			jsonObject.put("reportUploadLogId",reportUploadLogId);
			jsonObject.put("reportMasterId", reportMasterId);
			if(dlFileEntry!=null) {
				jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
				jsonObject.put("userName", dlFileEntry.getUserName());
			} else {
				jsonObject.put("url", "");
				jsonObject.put("userName", "");
			}
			jsonObject.put("dueDate", dueDate);
			jsonObject.put("createDate", reportUploadDate);
			jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
			jsonObject.put("remarks", HtmlUtil.escape(comment));
			jsonObject.put("workflowInstanceId", workflowInstanceId);
			String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
			if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
				intermediaryName=intrmedName;
			}
			jsonObject.put("intermediaryName", HtmlUtil.escape(intermediaryName));
			jsonObject.put("assignedTo", assignedTo.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		}else if(isNonNpsUser && Validator.isNull(checkIntermediaryName) && npsDashboardUtil.isNonNpsUser(assignedToId)){
			jsonObject.put("fileName", HtmlUtil.escape(reportName));
			jsonObject.put("reportUploadLogId",reportUploadLogId);
			jsonObject.put("reportMasterId", reportMasterId);
			if(dlFileEntry!=null) {
				jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
				jsonObject.put("userName", dlFileEntry.getUserName());
			} else {
				jsonObject.put("url", "");
				jsonObject.put("userName", "");
			}
			jsonObject.put("dueDate", dueDate);
			jsonObject.put("createDate", reportUploadDate);
			jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
			jsonObject.put("remarks", HtmlUtil.escape(comment));
			jsonObject.put("workflowInstanceId", workflowInstanceId);
			String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
			if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
				intermediaryName=intrmedName;
			}
			jsonObject.put("intermediaryName", HtmlUtil.escape(intermediaryName));
			jsonObject.put("assignedTo", assignedTo.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		}else if(!isNonNpsUser && Validator.isNull(checkIntermediaryName)){
			jsonObject.put("fileName", HtmlUtil.escape(reportName));
			jsonObject.put("reportUploadLogId",reportUploadLogId);
			jsonObject.put("reportMasterId", reportMasterId);
			if(dlFileEntry!=null) {
				jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
				jsonObject.put("userName", dlFileEntry.getUserName());
			} else {
				jsonObject.put("url", "");
				jsonObject.put("userName", "");
			}
			jsonObject.put("dueDate", dueDate);
			jsonObject.put("createDate", reportUploadDate);
			jsonObject.put("status", (statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
			jsonObject.put("remarks", HtmlUtil.escape(comment));
			jsonObject.put("workflowInstanceId", workflowInstanceId);
			String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
			if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
				intermediaryName=intrmedName;
			}
			jsonObject.put("intermediaryName", HtmlUtil.escape(intermediaryName));
			jsonObject.put("assignedTo", assignedTo.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		}
		return jsonObject;
	}
	
	private JSONArray getCRAReportsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay,
			DateFormat dateFormat, long companyId,boolean isNonNpsUser,String intermediaryName) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			List<WorkflowTask> allList = new ArrayList<>();
			
			///List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 
					//WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> pending = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.FALSE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 
					WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			//allList.addAll(completed);
			allList.addAll(pending);
			Set<Long> addedWorkflowTasks = new HashSet<>();
			
			for (WorkflowTask itr : allList) {
				try {
					Map<String, Serializable> maps = itr.getOptionalAttributes();
					long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
					if(!addedWorkflowTasks.contains(applicationId)) {
						String entryClassName = String.valueOf(maps.get("entryClassName"));
						//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
						OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
						
						String comment = npstUserUtil.getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
						String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
						long assignedToId = npstUserUtil.getCurrentAssigneeUserId(companyId, itr.getWorkflowInstanceId(), comparator);

						ReportMaster reportMaster = null;
						DLFileEntry dlFileEntry = null;
			
						ReportUploadLogCRA reportUploadLogCra = null;
						ReportUploadLogGrievances reportUploadLogGrievances = null;
						ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;
						if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
								reportUploadLogCra  = reportUploadLogCraLocalService.getReportUploadLogCRA(applicationId);
								if(reportUploadLogCra != null && reportUploadLogCra.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCra.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCra.getReportMasterId());
								}
							}
						}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
								reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
								if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievances.getReportMasterId());
								}
							}
							
							/* Report Upload By Griev-AM-PFRDA */
							if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
								reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(applicationId);
								if(reportUploadLogGrievAMPFRDA != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievAMPFRDA.getReportMasterId());
								}
							}
							
						}
						
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
							
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogCra)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogCra.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogCra.getCreateDate()), reportUploadLogCra.getStatus(),
										!comment.isEmpty()?comment:reportUploadLogCra.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser, intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogGrievances)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogGrievances.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogGrievances.getCreateDate()), reportUploadLogGrievances.getStatus(),
										!comment.isEmpty()?comment:reportUploadLogGrievances.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser, intermediaryName,assignedToId);
							}
						else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogGrievAMPFRDA)) {
							jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogGrievAMPFRDA.getReportUploadLogId(), reportMasterId, dlFileEntry, 
									dateFormat.format(reportUploadLogGrievAMPFRDA.getCreateDate()), reportUploadLogGrievAMPFRDA.getStatus(),
									!comment.isEmpty()?comment:reportUploadLogGrievAMPFRDA.getRemarks(), 
											itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
											isNonNpsUser, intermediaryName,assignedToId);
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
	
	private JSONArray getCustodianReportsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay,
			DateFormat dateFormat, long companyId, boolean isNonNpsUser,String intermediaryName) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			List<WorkflowTask> allList = new ArrayList<>();
			
			//List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 
					//WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> pending = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.FALSE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 
					WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			//allList.addAll(completed);
			allList.addAll(pending);
			Set<Long> addedWorkflowTasks = new HashSet<>();
			
			for (WorkflowTask itr : allList) {
				try {
					Map<String, Serializable> maps = itr.getOptionalAttributes();
					long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
					if(!addedWorkflowTasks.contains(applicationId)) {
						String entryClassName = String.valueOf(maps.get("entryClassName"));
						//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
						OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
						
						String comment = npstUserUtil.getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
						String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
						long assignedToId = npstUserUtil.getCurrentAssigneeUserId(companyId, itr.getWorkflowInstanceId(), comparator);
						
						ReportMaster reportMaster = null;
						DLFileEntry dlFileEntry = null;
			
						ReportUploadLogCustodian reportUploadLogCustodian = null;
						ReportUploadLogPFMCustodian reportUploadLogPFMCustodian = null;
						CustodianCompForm custodianCompForm = null;
						CustAnnualAuditReport custAnnualAuditReport = null;
						ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = null;
						CustIAR custIAR = null;
						if(isPFMNonNpsUser) {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
								reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
								if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
								}
							}
						}else {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
								reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
								if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustodian.getReportMasterId());
								}
							}
							if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
								custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(applicationId);
								if(custodianCompForm != null) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getQcfile_id());
									reportMaster = reportMasterLocalService.getReportMaster(custodianCompForm.getReportMasterId());
								}
							}
							if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
								custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
								if(custAnnualAuditReport != null) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custAnnualAuditReport.getAudit_pdf_fileentry_id());
									reportMaster = reportMasterLocalService.getReportMaster(custAnnualAuditReport.getReportMasterId());
								}
							}
							if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
								custIAR = custIARLocalService.getCustIAR(applicationId);
								if(custIAR != null) {
									dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custIAR.getIar_file_id());
									reportMaster = reportMasterLocalService.getReportMaster(custIAR.getReportMasterId());
								}
							}
							
							/* Report Upload By Cust-AM-PFRDA */
							if(entryClassName.equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName())) {
								reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(applicationId);
								if(reportUploadLogCustAMPFRDA != null ) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustAMPFRDA.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustAMPFRDA.getReportMasterId());
								}
							}
							
							if(!isNonNpsUser && entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
								reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
								if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
								}
							}
						}
						
						JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
						if(reportMaster != null ) {
							long reportMasterId = reportMaster.getReportMasterId();
							String reportName =  reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
							 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
							ReportUploadLog reportUploadLog = null;
							try {
								reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
							} catch (Exception e) {
								LOG.info("Exception in getting reportUploadLog:::" + e.getMessage());
							}
							
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogCustodian)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogCustodian.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(itr.getCreateDate()), reportUploadLogCustodian.getStatus(),
										!comment.isEmpty()?comment:reportUploadLogCustodian.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser, intermediaryName,assignedToId);
							} else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogPFMCustodian)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogPFMCustodian.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(itr.getCreateDate()), reportUploadLogPFMCustodian.getStatus(),
										!comment.isEmpty()?comment:reportUploadLogPFMCustodian.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser, intermediaryName,assignedToId);
							} else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(custodianCompForm)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, custodianCompForm.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(itr.getCreateDate()), custodianCompForm.getStatus(),
										!comment.isEmpty()?comment:custodianCompForm.getRemarks(), 
										itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
										isNonNpsUser, intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(custAnnualAuditReport)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, custAnnualAuditReport.getReportUploadLogId(), reportMasterId, dlFileEntry, 
									dateFormat.format(itr.getCreateDate()), custAnnualAuditReport.getStatus(),
									!comment.isEmpty()?comment:custAnnualAuditReport.getRemarks(), 
											itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
											isNonNpsUser, intermediaryName,assignedToId);
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(custIAR)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, custIAR.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(itr.getCreateDate()), custIAR.getStatus(),
										!comment.isEmpty()?comment:custIAR.getRemarks(), 
												itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
												isNonNpsUser, intermediaryName,assignedToId);
							}
							else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogCustAMPFRDA)) {
								jsonObject = getReportSummeryObjectNonNPS(reportName, reportUploadLogCustAMPFRDA.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(itr.getCreateDate()), reportUploadLogCustAMPFRDA.getStatus(),
										!comment.isEmpty()?comment:reportUploadLogCustAMPFRDA.getRemarks(), 
												itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_(),
												isNonNpsUser, intermediaryName,assignedToId);
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
	
	public JSONArray getCRAApprovedReportSummary(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId, boolean isPfrda) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("Get approved report summary for user.");
		
		Set<Long> addedWorkflowTasks = new HashSet<>();
		 
		try {
			
			List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 
					WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			for (WorkflowTask itr : completed) {
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				if(!addedWorkflowTasks.contains(applicationId)) {
					String entryClassName = String.valueOf(maps.get("entryClassName"));
					//LOG.info("Class Name : "+entryClassName+" Class PK : "+applicationId);
					
					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
					
					String comment = npstUserUtil.getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
					String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
					
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					
					
					ReportUploadLogCRA reportUploadLogCra = null;
					ReportUploadLogGrievances reportUploadLogGrievances = null;
					ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;

					
					if(!isPfrda) {
						if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
								reportUploadLogCra  = reportUploadLogCraLocalService.getReportUploadLogCRA(applicationId);
								if(reportUploadLogCra != null && reportUploadLogCra.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCra.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCra.getReportMasterId());
								}
							}
						}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
								reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
								if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievances.getReportMasterId());
								}
							}
						}
						/* Report Upload By Griev-AM-PFRDA */
						else if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
								LOG.info("entryClassName::::" + entryClassName);
								reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(applicationId);
								if(reportUploadLogGrievAMPFRDA != null && reportUploadLogGrievAMPFRDA.getFileEntryId()!=0) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievAMPFRDA.getReportMasterId());
								}
							}
					}
					
					// End CRA
					
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
						
						if(!isPfrda) {
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogCra)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogCra.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogCra.getCreateDate()), reportUploadLogCra.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()),reportUploadLog.getStatus_() );
								status = reportUploadLogCra.getStatus();
							}else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogGrievances)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogGrievances.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogGrievances.getCreateDate()), reportUploadLogGrievances.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = reportUploadLogGrievances.getStatus();
							}
						else if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogGrievAMPFRDA)) {
							jsonObject = getReportSummeryObject(reportName, reportUploadLogGrievAMPFRDA.getReportUploadLogId(), reportMasterId, dlFileEntry, 
									dateFormat.format(reportUploadLogGrievAMPFRDA.getCreateDate()), reportUploadLogGrievAMPFRDA.getStatus(), 
									comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
							status = reportUploadLogGrievAMPFRDA.getStatus();
						}
							
							// include entity details for Grivences
							
							/* Report Upload By PFM-AM-PFRDA */
							
							/*if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
								ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
								if(reportUploadLogPFMAMPFRDA != null ) {
									dlFileEntry =  null;
									reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
									//comment = reportUploadLogPFMAMPFRDA.getRemarks();
								//	dueDate = reportUploadLogPFMAMPFRDA.getReportDate();
									//createDate = reportUploadLogPFMAMPFRDA.getStatusDate();
								//	intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMAMPFRDA.getReportUploadLogId());
								}
							}*/
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
	
	public JSONArray getCustodianApprovedReportSummary(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId, boolean isPfrda) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("Get approved report summary for user.");
		
		Set<Long> addedWorkflowTasks = new HashSet<>();
		 
		try {
			
			List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 
					WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			for (WorkflowTask itr : completed) {
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				if(!addedWorkflowTasks.contains(applicationId)) {
					String entryClassName = String.valueOf(maps.get("entryClassName"));
					LOG.info("Class Name : "+entryClassName+" Class PK : "+applicationId);
					
					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
					
					String comment = npstUserUtil.getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
					String assignedTo = npstUserUtil.getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
					
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					
					
					ReportUploadLogCustodian reportUploadLogCustodian = null;
					
					if(!isPfrda) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
							reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
							if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustodian.getReportMasterId());
							}
						}
					}
					
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
						
						if(!isPfrda) {
							if(Validator.isNotNull(reportMaster) && Validator.isNotNull(reportUploadLogCustodian)) {
								jsonObject = getReportSummeryObject(reportName, reportUploadLogCustodian.getReportUploadLogId(), reportMasterId, dlFileEntry, 
										dateFormat.format(reportUploadLogCustodian.getCreateDate()), reportUploadLogCustodian.getStatus(), 
										comment, itr.getWorkflowInstanceId(), assignedTo, dateFormat.format(reportUploadLog.getReportDate()), reportUploadLog.getStatus_());
								status = reportUploadLogCustodian.getStatus();
							}
							
							/* Report Upload By PFM-AM-PFRDA */
							
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
								ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
								if(reportUploadLogPFMAMPFRDA != null ) {
									dlFileEntry =  null;
									reportMaster = ReportMasterLocalServiceUtil.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
									//comment = reportUploadLogPFMAMPFRDA.getRemarks();
								//	dueDate = reportUploadLogPFMAMPFRDA.getReportDate();
									//createDate = reportUploadLogPFMAMPFRDA.getStatusDate();
								//	intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMAMPFRDA.getReportUploadLogId());
								}
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
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private NPSTUserUtil npstUserUtil;
	
	@Reference
	private ReportUploadLogPFMLocalService reportUploadLogPfmLocalService;

	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference
	private UserLocalService userLocalService;
	
	@Reference
	private Portal portal;
	
	@Reference
	private DLFileEntryLocalService dlFileEntryLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	@Reference
	private ReportUploadLogPFMAMLocalService reportUploadLogPfmAmLocalService;

	@Reference
	private ReportUploadLogPFMCRALocalService reportUploadLogPfmCraLocalService;
	
	@Reference
	private ReportUploadLogCRALocalService reportUploadLogCraLocalService;
	
	@Reference
	private ReportUploadLogGrievancesLocalService reportUploadLogGrievancesLocalService;
	
	@Reference
	private ReportUploadLogCustodianLocalService reportUploadLogCustodianLocalService;
	
	@Reference
	private CustodianCompFormLocalService custodianCompFormLocalService;
	
	@Reference
	private CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;
	
	@Reference
	private CustIARLocalService custIARLocalService;
	
	@Reference
	private ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService;
	
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
	private ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogPFMAMPFRDALocalService reportUploadLogPFMAMPFRDALocalService;
	
	@Reference
	private DARLocalService darLocalService;
	
	@Reference
	private Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;
	
	@Reference
	private PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
}
