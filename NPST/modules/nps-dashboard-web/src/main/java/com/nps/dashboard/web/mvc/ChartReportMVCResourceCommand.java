package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.DAR;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.ReportUploadLogNPST;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.model.ReportUploadLogPFMCustodian;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.AnnualCompCertificateLocalService;
import com.daily.average.service.service.CustAnnualAuditReportLocalService;
import com.daily.average.service.service.CustodianCompFormLocalService;
import com.daily.average.service.service.DARLocalService;
import com.daily.average.service.service.InputQuarterlyIntervalLocalService;
import com.daily.average.service.service.IntermediaryListLocalService;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalService;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogMakerLocalService;
import com.daily.average.service.service.ReportUploadLogNPSTLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;
import com.nps.dashboard.web.util.NPSTUserUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCommonConstants;
import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.util.CommonRoleService;

@Component(immediate = true, property = { "javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
		"mvc.command.name=chartData" }, service = MVCResourceCommand.class)
public class ChartReportMVCResourceCommand extends BaseMVCResourceCommand {

	private static final Log log = LogFactoryUtil.getLog(ChartReportMVCResourceCommand.class);
	private static boolean isMaker = Boolean.FALSE;
	private static String roleName = StringPool.BLANK;
	private static String companyName = StringPool.BLANK;
	private static List<String> makersReportTypes = null;
	private static boolean isNonNpsUser = Boolean.FALSE;
	private static boolean isPFMNonNpsUser = Boolean.FALSE;
	private static boolean isPfrda = Boolean.FALSE;
	private static boolean isChecker = Boolean.FALSE;
	private static List<String> reportTypes = null;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		log.debug("Calling serve resource of Chart report command ");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		companyName = ParamUtil.getString(resourceRequest, "department", NPSCompany.NPST);
		log.debug("companyName : "+companyName);
		if(Validator.isNull(companyName)) {
			companyName = NPSCompany.NPST;
		}
		setDefaultValues(themeDisplay);
		
		int selectedYearChart = ParamUtil.getInteger(resourceRequest, "selectedYearChart");
		String reportType = ParamUtil.getString(resourceRequest, "reportType");

		log.debug("selectedYearChart1 : " + selectedYearChart + " reportType :: " + reportType);
		
		List<String> plotColors = Arrays.asList("#3485D8","#F59225","#F37699", "#06C9E9","#9206e9","#50a510","#a5a010");
		
		JSONObject chartObject = JSONFactoryUtil.createJSONObject();
		JSONArray chartArray = JSONFactoryUtil.createJSONArray();
		log.debug("main report type::" + reportType);
		if(reportType.equalsIgnoreCase("chart1")) {
			// Pending for upload for maker and pending for review for all other user.
			log.debug("isMaker : "+isMaker+" Role Name : "+roleName+" Company Name : "+companyName+" isNonNpsUser : "+isNonNpsUser);
			log.debug("Maker report types : "+makersReportTypes);
			
			if (isMaker) {
				if(makersReportTypes != null) {
					long intermediaryId = commonRoleService.getUserIntermediaryId(roleName, themeDisplay.getUserId(), themeDisplay.getCompanyId());
					log.debug("intermediaryId :: "+intermediaryId);
					for(int i=0; i<makersReportTypes.size();i++) {
						String folderName = makersReportTypes.get(i);
						
						List<ReportMaster> reportMasters = reportMasterLocalService.getByReportTypeAndUploaderRole(folderName, roleName);
						List<Long> reportMasterIds = reportMasters.stream().map(ReportMaster::getReportMasterId).collect(Collectors.toList());
						chartArray.put(getMonthlyPendingReportCount(reportMasterIds, folderName, selectedYearChart, plotColors.get(i), intermediaryId));
					}
				}
				chartObject.put("chartData", chartArray);
				chartObject.put("label", "Pending for upload");
			}else {
				// Get pending for review 
				chartArray = getPendingForReviewCount(themeDisplay, plotColors, selectedYearChart);
				chartObject.put("chartData", chartArray);
				chartObject.put("label", "Pending for review");
			}
		} else if(reportType.equalsIgnoreCase("chart2")) {
			
			if(isNonNpsUser) {
				// Display the Report with Submmited to NPS user.
				chartArray = getSubmittedToNPSUserCount(themeDisplay, selectedYearChart, companyName);
				chartObject.put("chartData", chartArray);
				chartObject.put("label", "Submitted to NPS Trust");
			}else {
				// Fetch Approved reports for AM, DGM, GM and PFRDA
				chartArray = getApprovedReportCount(themeDisplay, selectedYearChart);
				chartObject.put("chartData", chartArray);
				chartObject.put("label", "Approved Reports");
			}
		}
		
		resourceResponse.getWriter().print(chartObject);
	}
	
	private void setDefaultValues(ThemeDisplay themeDisplay) {
		long companyId = themeDisplay.getCompanyId();
		long userId = themeDisplay.getUserId();
		boolean inherited = Boolean.FALSE;
		
		try {
			if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
				boolean maker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.MAKER, userId, inherited);
				boolean checker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CHECKER, userId, inherited);
				boolean supervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.SUPERVISOR, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.PFRDA_TB, themeDisplay.getUserId(), Boolean.FALSE);
				roleName = NPSTRoleConstants.MAKER;
				isNonNpsUser = (maker || checker || supervisor);
				isMaker = maker;
				makersReportTypes = NPSCommonConstants.makerReportTypes;
				reportTypes = NPSCommonConstants.allReportTypes;
				isChecker = checker;
			}else if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				boolean craSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_SUPERVISOR, userId, inherited);
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_PFRDA, themeDisplay.getUserId(), Boolean.FALSE);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker || craSupervisor || craMaker || craChecker);
				isMaker = (pfmMaker || craMaker);
				makersReportTypes = NPSCommonConstants.pfmMakerReportTypes;
				roleName = NPSTRoleConstants.PFM_MAKER;
				reportTypes = NPSCommonConstants.pfmMakerReportTypes;
				isChecker = (pfmChecker || craChecker);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				boolean craSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_SUPERVISOR, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_PFRDA, themeDisplay.getUserId(), Boolean.FALSE);
				isNonNpsUser = (craMaker || craChecker || craSupervisor);
				isMaker = craMaker;
				makersReportTypes = NPSCommonConstants.grievanseMakerReportTypes;
				roleName = NPSTRoleConstants.CRA_MAKER;
				isChecker = craChecker;
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				boolean custodianMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_MAKER, userId, inherited);
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean custodianChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_CHECKER, userId, inherited);
				boolean custodianSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_SUPERVISOR, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_PFRDA, themeDisplay.getUserId(), Boolean.FALSE);
				isNonNpsUser = (custodianMaker || custodianChecker || custodianSupervisor || pfmMaker || pfmChecker);
				isMaker = custodianMaker || pfmMaker;
				roleName = NPSTRoleConstants.CUSTODIAN_MAKER;
				reportTypes = NPSCommonConstants.custodianMakerReportTypes;
				isChecker = custodianChecker;
				isPFMNonNpsUser = pfmMaker || pfmChecker;
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				boolean greMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_MAKER, userId, inherited);
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean greChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_CHECKER, userId, inherited);
				boolean greSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_SUPERVISOR, userId, inherited);
				isPfrda = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.GRIEVANCES_PFRDA, themeDisplay.getUserId(), Boolean.FALSE);
				isNonNpsUser = (greMaker || greChecker || greSupervisor || craMaker);
				isMaker = greMaker || craMaker;
				makersReportTypes = NPSCommonConstants.grievanseMakerReportTypes;
				roleName = NPSTRoleConstants.GRIEVANCES_MAKER;
				reportTypes = NPSCommonConstants.grievanseMakerReportTypes;
				isChecker = greChecker;
			}
			
			
		} catch (Exception e) {
			log.error("Exception on setting default values : "+e.getMessage(),e);
		}
		
	}
	
	private static Date getTimeToBeginningOfDay(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	private static Date getTimeToEndofDay(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	private JSONObject getMonthlyPendingReportCount(List<Long> reportMasterIds, String reportType, int year, String plotColor,long intermediaryId) {
		List<Double> data = new ArrayList<Double>();
		/**
		 * Getting count of 12 months 0=JAN, 1=FEB and so on
		 */
		log.info("reportmaster Ids : "+reportMasterIds);
		if(intermediaryId > 0) {
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(0, year), getTimeToEndofDay(0, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(1, year), getTimeToEndofDay(1, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(2, year), getTimeToEndofDay(2, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(3, year), getTimeToEndofDay(3, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(4, year), getTimeToEndofDay(4, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(5, year), getTimeToEndofDay(5, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(6, year), getTimeToEndofDay(6, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(7, year), getTimeToEndofDay(7, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(8, year), getTimeToEndofDay(8, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(9, year), getTimeToEndofDay(9, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(10, year), getTimeToEndofDay(10, year), reportMasterIds, 0, intermediaryId));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(11, year), getTimeToEndofDay(11, year), reportMasterIds, 0, intermediaryId));
		}else {
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(0, year), getTimeToEndofDay(0, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(1, year), getTimeToEndofDay(1, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(2, year), getTimeToEndofDay(2, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(3, year), getTimeToEndofDay(3, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(4, year), getTimeToEndofDay(4, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(5, year), getTimeToEndofDay(5, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(6, year), getTimeToEndofDay(6, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(7, year), getTimeToEndofDay(7, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(8, year), getTimeToEndofDay(8, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(9, year), getTimeToEndofDay(9, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(10, year), getTimeToEndofDay(10, year), reportMasterIds, 0));
			data.add(reportUploadLogLocalService.countMonthlyReportUploadLog(getTimeToBeginningOfDay(11, year), getTimeToEndofDay(11, year), reportMasterIds, 0));
		
		}
		
		JSONObject object = JSONFactoryUtil.createJSONObject();
		object.put("label", HtmlUtil.escape(reportType));
		object.put("data", data);
		object.put("yAxisID", "y");
		object.put("fill", Boolean.FALSE);
		object.put("borderWidth", 1);
		object.put("borderColor", plotColor);
		object.put("backgroundColor", plotColor);
		
		return object;
	}
	
	private JSONArray getPendingForReviewCount(ThemeDisplay themeDisplay, List<String> plotColors, int year) {
		long companyId = themeDisplay.getCompanyId();
		long assigneeUserId = themeDisplay.getUserId();
		boolean isCompleted = Boolean.FALSE;
		
		JSONArray chartArray = JSONFactoryUtil.createJSONArray();
		Map<String, List<Double>> reportTypeCountMap = new HashMap<String, List<Double>>();
		List<WorkflowTask> assigneToMe = null;
		try {
			assigneToMe = workflowTaskManager.getWorkflowTasksByUserRoles(companyId, assigneeUserId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> assigneToMe2 = workflowTaskManager.getWorkflowTasksByUser(companyId, assigneeUserId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			assigneToMe.addAll(assigneToMe2);
			
		}catch (Exception e) {
			log.error("Exception on getting workflow task : "+e.getMessage(),e);
		}
		if(assigneToMe != null && !assigneToMe.isEmpty()) {
			for (WorkflowTask itr : assigneToMe) {
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				String entryClassName = String.valueOf(maps.get("entryClassName"));
				log.debug("applicationId : "+applicationId+" Entry class name : "+entryClassName);
				ReportMaster reportMaster = null;
				Date uploadedDate = null;
				CustodianCompForm custodianCompForm = null;
				CustAnnualAuditReport custAnnualAuditReport = null;
				if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
							ReportUploadLogMaker reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
							if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogMaker.getReportMasterId());
								uploadedDate = reportUploadLogMaker.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
							ReportUploadLogSupervisor reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
							if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
								uploadedDate = reportUploadLogSupervisor.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
							ReportUploadLogNPST reportUploadLogNPST  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(applicationId);
							if(reportUploadLogNPST != null && reportUploadLogNPST.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogNPST.getReportMasterId());
								uploadedDate = reportUploadLogNPST.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
				}
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							ReportUploadLogPFM reportUploadLogPFM  = reportUploadLogPFMLocalService.getReportUploadLogPFM(applicationId);
							if(reportUploadLogPFM != null && reportUploadLogPFM.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFM.getReportMasterId());
								uploadedDate = reportUploadLogPFM.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							ReportUploadLogPFMAM reportUploadLogPFMAM  = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(applicationId);
							if(reportUploadLogPFMAM != null && reportUploadLogPFMAM.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMAM.getReportMasterId());
								uploadedDate = reportUploadLogPFMAM.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
				
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							ReportUploadLogPFMCRA reportUploadLogPFMCRA  = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(applicationId);
							if(reportUploadLogPFMCRA != null && reportUploadLogPFMCRA.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCRA.getReportMasterId());
								uploadedDate = reportUploadLogPFMCRA.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					/* For Input Quartely Interval */
					try {
						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							InputQuarterlyInterval inputQuarterlyInterval  = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
							if(inputQuarterlyInterval != null) {
								reportMaster = reportMasterLocalService.getReportMaster(inputQuarterlyInterval.getReportMasterId());
								uploadedDate = inputQuarterlyInterval.getCreatedate();
							}
						}
					}catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					/* For MnCompCertificate */
					try {
						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							MnCompCertificate mnCompCertificate  = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
							if(mnCompCertificate != null) {
								reportMaster = reportMasterLocalService.getReportMaster(mnCompCertificate.getReportMasterId());
								uploadedDate = mnCompCertificate.getCreatedon();
							}
						}
					} catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					/* For AnnualCompCertificate */
					try {
						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							AnnualCompCertificate annualCompCertificate  = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
							if(annualCompCertificate != null) {
								reportMaster = reportMasterLocalService.getReportMaster(annualCompCertificate.getReportMasterId());
								uploadedDate = annualCompCertificate.getCreatedate();
							}
						}
					} catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					/* For QtrStewardshipReport */
					try {
						if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							QtrStewardshipReport qtrStewardshipReport  = qtrStewardshipReportLocalService.getQtrStewardshipReport(applicationId);
							if(qtrStewardshipReport != null) {
								reportMaster = reportMasterLocalService.getReportMaster(qtrStewardshipReport.getReportMasterId());
								uploadedDate = qtrStewardshipReport.getCreatedon();
							}
						}
					} catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					/* For NPA Development */
					
					try {
						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							MnNpaDevelopment mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
							if(mnNpaDevelopment != null) {
								reportMaster = reportMasterLocalService.getReportMaster(mnNpaDevelopment.getReportMasterId());
								uploadedDate = mnNpaDevelopment.getCreatedon();
							}
						}
					} catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					
					/* For DAR */
					
					try {
						if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
							DAR dar  = darLocalService.getDAR(applicationId);
							if(dar != null) {
								reportMaster = reportMasterLocalService.getReportMaster(dar.getReportMasterId());
								uploadedDate = dar.getCreatedon();
							}
						}
					} catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					/* For Pfm_Qr_Internal_Audit_Report */
					try {
						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report  = pfm_Qr_Internal_Audit_ReportLocalService.getPfm_Qr_Internal_Audit_Report(applicationId);
							if(pfm_Qr_Internal_Audit_Report != null) {
								reportMaster = reportMasterLocalService.getReportMaster(pfm_Qr_Internal_Audit_Report.getReportMasterId());
								uploadedDate = pfm_Qr_Internal_Audit_Report.getCreatedon();
							}
						}
					} catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					/* For Pfm_Qr_Internal_Audit_Report */
					try {
						if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							PFM_hy_comp_cert pfm_hy_comp_cert  = pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(applicationId);
							if(pfm_hy_comp_cert != null) {
								reportMaster = reportMasterLocalService.getReportMaster(pfm_hy_comp_cert.getReportMasterId());
								uploadedDate = pfm_hy_comp_cert.getCreatedon();
							}
						}
					} catch (Exception e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
				}
				
				if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							ReportUploadLogCRA reportUploadLogCRA  = reportUploadLogCRALocalService.getReportUploadLogCRA(applicationId);
							if(reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCRA.getReportMasterId());
								uploadedDate = reportUploadLogCRA.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
				}
				
				if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					try {
						if(isPFMNonNpsUser) {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
								ReportUploadLogPFMCustodian reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
								if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
									uploadedDate = reportUploadLogPFMCustodian.getCreateDate();
								}
							}
						}else {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
								ReportUploadLogCustodian reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
								if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustodian.getReportMasterId());
									uploadedDate = reportUploadLogCustodian.getCreateDate();
								}
							}
							
							if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
								log.debug("reportUploadLogCustodian.getFileEntryId::");
								custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(applicationId);
								if(custodianCompForm != null) {
									//dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(custodianCompForm.getReportMasterId());
									uploadedDate = custodianCompForm.getCreatedate();
								}
							}
							if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
								log.debug("CustAnnualAuditReport::" );
								custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
								log.debug("custAnnualAuditReport name::" + custAnnualAuditReport.getName());
								if(custAnnualAuditReport != null) {
									//dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getFileEntryId());
									reportMaster = reportMasterLocalService.getReportMaster(custAnnualAuditReport.getReportMasterId());
									uploadedDate = custAnnualAuditReport.getCreatedon();
								}
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
				}
				
				if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					try {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
							ReportUploadLogGrievances reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievances.getReportMasterId());
								uploadedDate = reportUploadLogGrievances.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
				}
				
				if(Validator.isNotNull(reportMaster)) {
					int month = getMonth(uploadedDate);
					
					if(year == getYear(uploadedDate)) {
						List<Double> monthLyWiseCount = reportTypeCountMap.get(reportMaster.getReportType());
						if(monthLyWiseCount == null) {
							monthLyWiseCount = new ArrayList<Double>(Arrays.asList(0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d));
						}
						double count = monthLyWiseCount.get(month);
						monthLyWiseCount.set(month, count+1);
						
						reportTypeCountMap.put(reportMaster.getReportType(), monthLyWiseCount);
					}
				}
			}
			
			log.debug("reportTypeCountMap : "+reportTypeCountMap);
			log.debug("reportTypes : "+reportTypes);
			if(!reportTypeCountMap.isEmpty() && reportTypes != null) {
				for(int i=0; i<reportTypes.size();i++) {
					JSONObject object = JSONFactoryUtil.createJSONObject();
					String folderName = reportTypes.get(i);
					object.put("label", HtmlUtil.escape(folderName));
					object.put("data", reportTypeCountMap.get(folderName));
					object.put("yAxisID", "y");
					object.put("fill", Boolean.FALSE);
					object.put("borderWidth", 1);
					object.put("borderColor", plotColors.get(i));
					object.put("backgroundColor", plotColors.get(i));
					
					chartArray.put(object);
				}
			}
		}
		return chartArray;
	}
	
	private int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.MONTH);
	}
	
	private int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.YEAR);
	}
	
	private JSONArray getSubmittedToNPSUserCount(ThemeDisplay themeDisplay, int year, String companyName) {
		try {
			Set<WorkflowTask> workflowTasks = npstUserUtil.getWorkflowTaskSubmmitedToNPS(themeDisplay.getCompanyId(), Boolean.FALSE, companyName, isPFMNonNpsUser);
			return getChart2Data(themeDisplay, new ArrayList<WorkflowTask>(workflowTasks), Boolean.TRUE, year, Boolean.FALSE, Boolean.FALSE);
		}catch (Exception e) {
			log.error("Exception on fetching approved reports : "+e.getMessage(), e);
		}
		return JSONFactoryUtil.createJSONArray();
	}
	
	private JSONArray getApprovedReportCount(ThemeDisplay themeDisplay, int year) {
		try {
			List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(themeDisplay.getCompanyId(), Boolean.TRUE, QueryUtil.ALL_POS, 
					QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			
			return getChart2Data(themeDisplay, completed, !isPfrda, year, Boolean.TRUE, Boolean.TRUE);
		}catch (Exception e) {
			log.error("Exception on fetching approved reports : "+e.getMessage(), e);
		}
		
		return JSONFactoryUtil.createJSONArray();
	}
	
	private JSONArray getChart2Data(ThemeDisplay themeDisplay, List<WorkflowTask> workflowTasks, boolean includeNonNpstReports, int year, 
			boolean includeOnlyApproved, boolean includeNPSTReports) {
		Map<String, Double> reportTypeCountMap = new HashMap<>();
		JSONArray chartArray = JSONFactoryUtil.createJSONArray();
		Set<Long> addedWorkflowTasks = new HashSet<>();
		String checkIntermediaryName = npstUserUtil.getIntermediaryName(themeDisplay);
		 log.debug("includeOnlyApproved : "+includeOnlyApproved);
		for (WorkflowTask itr : workflowTasks) {
			Map<String, Serializable> maps = itr.getOptionalAttributes();
			long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
			if(!addedWorkflowTasks.contains(applicationId)) {
				String entryClassName = String.valueOf(maps.get("entryClassName"));
				log.debug("Entry class name : "+entryClassName+"::Entry class pk :: "+applicationId + ":: companyName::" + companyName);
				ReportMaster reportMaster = null;
				Date uploadedDate = null;
				if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
					try {
						if(includeNPSTReports && entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
							ReportUploadLogNPST reportUploadLogNpst  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(applicationId);
							boolean condition = false;
							if(includeOnlyApproved) {
								condition = (reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0 && reportUploadLogNpst.getStatus() == 0);
							}else {
								condition = (reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0);
							}
							if(condition) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogNpst.getReportMasterId());
								uploadedDate = reportUploadLogNpst.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					if(includeNonNpstReports) {
						try {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
								ReportUploadLogMaker reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
								boolean condition = false;
								if(includeOnlyApproved) {
									condition = (reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0 && reportUploadLogMaker.getStatus() == 0);
								}else {
									condition = (reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0);
								}
								if(condition) {
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogMaker.getReportMasterId());
									uploadedDate = reportUploadLogMaker.getCreateDate();
								}
							}
						}catch (PortalException e) {
							log.error("Exception while fetching data : "+e.getMessage(), e);
						}
						if(!(isMaker || isChecker)) {
							try {
								if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
									ReportUploadLogSupervisor reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
									boolean condition = false;
									if(includeOnlyApproved) {
										condition = (reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0 && reportUploadLogSupervisor.getStatus() == 0);
									}else {
										condition = (reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0);
									}
									if(condition) {
										reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
										uploadedDate = reportUploadLogSupervisor.getCreateDate();
									}
								}
							}catch (PortalException e) {
								log.error("Exception while fetching data : "+e.getMessage(), e);
							}
						}
						
					}
				}else if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					try {
						if(includeNPSTReports && entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							ReportUploadLogPFMAM reportUploadLogPFMAM  = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(applicationId);
							boolean condition = false;
							if(includeOnlyApproved) {
								condition = (reportUploadLogPFMAM != null && reportUploadLogPFMAM.getFileEntryId()!=0 && reportUploadLogPFMAM.getStatus() == 0);
							}else {
								condition = (reportUploadLogPFMAM != null && reportUploadLogPFMAM.getFileEntryId()!=0);
							}
							if(condition) {
								reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMAM.getReportMasterId());
								uploadedDate = reportUploadLogPFMAM.getCreateDate();
							}
						}
					}catch (PortalException e) {
						log.error("Exception while fetching data : "+e.getMessage(), e);
					}
					
					if(includeNonNpstReports) {
						if(!(isMaker || isChecker)) {
							try {
								if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
									ReportUploadLogPFMCRA reportUploadLogPFMCRA  = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(applicationId);
									boolean condition = false;
									if(includeOnlyApproved) {
										condition = (reportUploadLogPFMCRA != null && reportUploadLogPFMCRA.getFileEntryId()!=0 && reportUploadLogPFMCRA.getStatus() == 0);
									}else {
										condition = (reportUploadLogPFMCRA != null && reportUploadLogPFMCRA.getFileEntryId()!=0);
									}
									if(condition) {
										reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCRA.getReportMasterId());
										uploadedDate = reportUploadLogPFMCRA.getCreateDate();
									}
								}
							}catch (PortalException e) {
								log.error("Exception while fetching data : "+e.getMessage(), e);
							}
						}
						
						try {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
								ReportUploadLogPFM reportUploadLogPFM = reportUploadLogPFMLocalService.getReportUploadLogPFM(applicationId);
								String  intermediaryName= npsDashboardUtil.getIntermediaryName(reportUploadLogPFM.getReportUploadLogId());
								boolean condition = false;
								if(includeOnlyApproved) {
									condition = (reportUploadLogPFM != null && reportUploadLogPFM.getFileEntryId()!=0 && reportUploadLogPFM.getStatus() == 0);
								}else {
									condition = (reportUploadLogPFM != null && reportUploadLogPFM.getFileEntryId()!=0);
								}
								if(condition && Validator.isNull(checkIntermediaryName)) {
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFM.getReportMasterId());
									uploadedDate = reportUploadLogPFM.getCreateDate();
								}else if(condition && Validator.isNotNull(checkIntermediaryName) && checkIntermediaryName.equalsIgnoreCase(intermediaryName)) {
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFM.getReportMasterId());
									uploadedDate = reportUploadLogPFM.getCreateDate();
								}
							}
						}catch (PortalException e) {
							log.error("Exception while fetching data : "+e.getMessage(), e);
						}
					}
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
					if(includeNonNpstReports) {
						try {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
								ReportUploadLogCRA reportUploadLogCRA  = reportUploadLogCRALocalService.getReportUploadLogCRA(applicationId);
								boolean condition = false;
								if(includeOnlyApproved) {
									condition = (reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId()!=0 && reportUploadLogCRA.getStatus() == 0);
								}else {
									condition = (reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId()!=0);
								}
								if(condition) {
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCRA.getReportMasterId());
									uploadedDate = reportUploadLogCRA.getCreateDate();
								}
							}
						}catch (PortalException e) {
							log.error("Exception while fetching data : "+e.getMessage(), e);
						}
					}
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					log.debug("company name custodian::" + includeNonNpstReports);
					if(includeNonNpstReports) {
						try {
							if(isPFMNonNpsUser) {
								if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
									ReportUploadLogPFMCustodian reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
									boolean condition = false;
									if(includeOnlyApproved) {
										condition = (reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0 && reportUploadLogPFMCustodian.getStatus() == 0);
									}else {
										condition = (reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0);
									}
									if(condition) {
										reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
										uploadedDate = reportUploadLogPFMCustodian.getCreateDate();
									}
								}
							}else {
								if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
									ReportUploadLogCustodian reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
									boolean condition = false;
									if(includeOnlyApproved) {
										condition = (reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0 && reportUploadLogCustodian.getStatus() == 0);
									}else {
										condition = (reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0);
									}
									if(condition) {
										reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustodian.getReportMasterId());
										uploadedDate = reportUploadLogCustodian.getCreateDate();
									}
								}
								
								if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
									CustodianCompForm custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(applicationId);
									boolean condition = false;
									if(includeOnlyApproved) {
										condition = (custodianCompForm != null && custodianCompForm.getStatus() == 0);
									}else {
										condition = (custodianCompForm != null);
									}
									if(condition) {
										reportMaster = reportMasterLocalService.getReportMaster(custodianCompForm.getReportMasterId());
										uploadedDate = custodianCompForm.getCreatedate();
									}
								}
								if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
									CustAnnualAuditReport custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
									boolean condition = false;
									if(includeOnlyApproved) {
										condition = (custAnnualAuditReport != null  && custAnnualAuditReport.getStatus() == 0);
									}else {
										condition = (custAnnualAuditReport != null);
									}
									if(condition) {
										reportMaster = reportMasterLocalService.getReportMaster(custAnnualAuditReport.getReportMasterId());
										uploadedDate = custAnnualAuditReport.getCreatedon();
									}
								}
							}
						}catch (PortalException e) {
							log.error("Exception while fetching data : "+e.getMessage(), e);
						}
					}
				}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					if(includeNonNpstReports) {
						try {
							if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
								ReportUploadLogGrievances reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
								boolean condition = false;
								if(includeOnlyApproved) {
									condition = (reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0 && reportUploadLogGrievances.getStatus() == 0);
								}else {
									condition = (reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0);
								}
								if(condition) {
									reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievances.getReportMasterId());
									uploadedDate = reportUploadLogGrievances.getCreateDate();
								}
							}
						}catch (PortalException e) {
							log.error("Exception while fetching data : "+e.getMessage(), e);
						}
					}
				}
				
				if(Validator.isNotNull(reportMaster) && uploadedDate != null && (year == getYear(uploadedDate))) {
					reportTypeCountMap.computeIfAbsent(reportMaster.getReportType(), v -> 0d);
					reportTypeCountMap.computeIfPresent(reportMaster.getReportType(), (k, v) -> v+1);
				}
			}
			
			addedWorkflowTasks.add(applicationId);
		}
		
		log.debug("reportTypeCountMap : "+reportTypeCountMap);
		if(!reportTypeCountMap.isEmpty()) {
			for(int i=0; i<NPSDashboardWebPortletKeys.folderNames.length;i++) {
				String folderName = NPSDashboardWebPortletKeys.folderNames[i];
				chartArray.put(reportTypeCountMap.get(folderName));
			}
		}
		
		
		return chartArray;
	}
	
	@Reference
	private CommonRoleService commonRoleService;
	
	@Reference
	private IntermediaryListLocalService intermediaryListLocalService;
	
	@Reference
	private UserLocalService userLocalService;
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private WorkflowTaskManager workflowTaskManager;
	
	@Reference
	private ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;
	
	@Reference
	private ReportUploadLogMakerLocalService reportUploadLogMakerLocalService;
	
	@Reference
	private ReportUploadLogNPSTLocalService reportUploadLogNPSTLocalService;
	
	@Reference
	private ReportUploadLogPFMLocalService reportUploadLogPFMLocalService;
	
	@Reference
	private ReportUploadLogPFMAMLocalService reportUploadLogPFMAMLocalService;
	
	@Reference
	private ReportUploadLogPFMCRALocalService reportUploadLogPFMCRALocalService;
	
	@Reference
	private ReportUploadLogCRALocalService reportUploadLogCRALocalService;
	
	@Reference
	private ReportUploadLogCustodianLocalService reportUploadLogCustodianLocalService;
	
	@Reference
	private ReportUploadLogGrievancesLocalService reportUploadLogGrievancesLocalService;
	
	@Reference
	private ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService;
	
	@Reference
	private CustodianCompFormLocalService custodianCompFormLocalService;
	
	@Reference
	private CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;
	
	@Reference
	private NPSTUserUtil npstUserUtil;
	
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
	private DARLocalService darLocalService;	
	
	@Reference
	private Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;
	
	@Reference
	private PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
}
