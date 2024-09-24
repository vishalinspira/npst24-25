package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportMasterModel;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
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
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;
import com.nps.dashboard.web.util.NPSTUserUtil;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.constants.NameMappingConstants;
import nps.common.service.util.CommonRoleService;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class CheckReportsMVCResourceCommand extends BaseMVCResourceCommand {

	private static final Log LOG = LogFactoryUtil.getLog(CheckReportsMVCResourceCommand.class);
	
	//private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
	private static boolean isNonNpsUser = Boolean.FALSE;
	private String companyName = StringPool.BLANK;
	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		LOG.info("Calling doServeResource of CheckReportsMVCResourceCommand...");
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String action = ParamUtil.getString(resourceRequest, "action");
		String department = ParamUtil.getString(resourceRequest, "department", NPSCompany.NPST);
		
		LOG.info("Action :: "+action);
		LOG.info("Department Name :: "+department);
		long userId = themeDisplay.getUserId();
		long companyId = themeDisplay.getCompanyId();
		
		boolean isPfrdaUser = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFRDA_TB, Boolean.FALSE);
		
		boolean isPfrdaUserGrievance = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_PFRDA, Boolean.FALSE);
		boolean isPfrdaUserCRA = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_PFRDA, Boolean.FALSE);
		boolean isPfrdaUserCustodian = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_PFRDA, Boolean.FALSE);
		boolean isPfrdaUserPFM =  roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_PFRDA, Boolean.FALSE);
		
		boolean isMakerOrChecker = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.MAKER, Boolean.FALSE)
				|| roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CHECKER, Boolean.FALSE);
		
		boolean isSupervisor = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.SUPERVISOR, Boolean.FALSE);
		
		boolean isCraMaker = roleLocalService.hasUserRole(userId,companyId,NPSTRoleConstants.CRA_MAKER,Boolean.FALSE);
		
		boolean isGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.NPST_GM, Boolean.FALSE);
		boolean isDGM  = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.NPST_DGM, false);
		boolean isPfmSupervisor = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_SUPERVISOR, Boolean.FALSE);
		
		boolean isAmUser = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.NPST_AM, Boolean.FALSE);
		
		boolean isMrg = Boolean.FALSE;
		
		if(department.isEmpty()) {
        	companyName = npsDashboardUtil.getCompanyName(companyId, themeDisplay.getUserId());
        }else {
        	companyName = department;
        }
		
		setDefaultValues(themeDisplay);
		String intermediaryName = npstUserUtil.getIntermediaryName(themeDisplay);
		
		String roleName = NPSTRoleConstants.NPST_AM;
		Map<String, String> roles = new HashMap<String, String>();
		//String companyName = npsDashboardUtil.getCompanyName(companyId, userId);
		
		if(department.equalsIgnoreCase(NPSCompany.NPST)) {
			roles.put("maker", NPSTRoleConstants.MAKER);
			roles.put("supervisor", NPSTRoleConstants.SUPERVISOR);
		}else if(department.equalsIgnoreCase(NPSCompany.PFM)) {
			isAmUser = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_AM, Boolean.FALSE);
			isGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_GM, Boolean.FALSE);
			isDGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_DGM, Boolean.FALSE);
			isMakerOrChecker = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_MAKER, Boolean.FALSE)
					|| roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_CHECKER, Boolean.FALSE);
			
			isMrg = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_MGR, Boolean.FALSE);
			isSupervisor = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_SUPERVISOR, Boolean.FALSE);

			
			roleName = NPSTRoleConstants.PFM_AM;
			roles.put("maker", NPSTRoleConstants.PFM_MAKER);
			roles.put("supervisor", NPSTRoleConstants.CRA_SUPERVISOR);
			
		}else if(department.equalsIgnoreCase(NPSCompany.CRA)) {
			isAmUser = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_AM, Boolean.FALSE);
			isGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_GM, Boolean.FALSE);
			isDGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_DGM, Boolean.FALSE);
			isMakerOrChecker = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_MAKER, Boolean.FALSE)
					|| roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_CHECKER, Boolean.FALSE);
			
			roleName = NPSTRoleConstants.CRA_AM;
			roles.put("maker", NPSTRoleConstants.CRA_MAKER);
		}else if(department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
			isAmUser = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_AM, Boolean.FALSE);
			//isAmUser = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_AM_MGR, Boolean.FALSE);
			isGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_GM, Boolean.FALSE);
			isDGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_DGM, Boolean.FALSE);
			isMakerOrChecker = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_MAKER, Boolean.FALSE)
					|| roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_CHECKER, Boolean.FALSE);
			
			isMrg = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_MGR, Boolean.FALSE);
			//isMrg = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_AM_MGR, Boolean.FALSE);
			
			
			roleName = NPSTRoleConstants.GRIEVANCES_AM_MGR;
			roles.put("maker", NPSTRoleConstants.CRA_MAKER);
		}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
			isAmUser = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_AM, Boolean.FALSE);
			isGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_GM, Boolean.FALSE);
			isDGM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_DGM, Boolean.FALSE);
			boolean isPfmMakerOrChecker = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_MAKER, Boolean.FALSE)
					|| roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_CHECKER, Boolean.FALSE)
					|| roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_SUPERVISOR, Boolean.FALSE);
			
			boolean isCustodianMakerOrChecker = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_MAKER, Boolean.FALSE)
					|| roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_CHECKER, Boolean.FALSE);
			
			isMrg = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_MGR, Boolean.FALSE);
			
			isMakerOrChecker = isPfmMakerOrChecker || isCustodianMakerOrChecker;
			
			isSupervisor = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_SUPERVISOR, Boolean.FALSE);
			
			roleName = NPSTRoleConstants.CUSTODIAN_AM;
			roles.put("maker", (isCustodianMakerOrChecker || isSupervisor ) ? NPSTRoleConstants.CUSTODIAN_MAKER: NPSTRoleConstants.PFM_MAKER);
		}
		LOG.info("isMakerOrChecker : "+isMakerOrChecker);
		LOG.info("isSupervisor : "+isSupervisor);
		LOG.info("isAmUser : "+isAmUser);
		LOG.info("isPfrdaUser : "+isPfrdaUser);
		LOG.info("isGM : "+isGM);
		LOG.info("isDGM : "+isDGM);
		LOG.info("isMrg : "+isMrg);
		LOG.info("Department wise roles : "+roles);
		
		JSONObject mainJSONObject = JSONFactoryUtil.createJSONObject();
		
		List<ReportMaster> reportMasterList = new ArrayList<>();
		if((isAmUser || isPfrdaUser || isGM || isDGM || isMrg)&& NPSDashboardWebPortletKeys.PENDING_FOR_UPLOAD_BY_AM.equalsIgnoreCase(action)) {
			LOG.info("1");
			reportMasterList = reportMasterLocalService.getByUploaderRoleAndDepartment(roleName, department);
		}else if(isMakerOrChecker) {
			LOG.info("1");
			reportMasterList = reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get("maker"), department);
			LOG.info("roles.get('maker'), department"+roles.get("maker")+"   "+ department+" reportMasterList size "+reportMasterList.size());
		}else if(isSupervisor) {
			LOG.info("1");
			reportMasterList = new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get("maker"), department));
			reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get("supervisor"), department)));
			if(isCraMaker) {
			reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get(NPSTRoleConstants.CRA_MAKER), department)));
			}
		}else if(NPSDashboardWebPortletKeys.PENDING_FOR_UPLOAD_BY_NON_NPST_USER.equalsIgnoreCase(action)) {
			LOG.info("1");
			reportMasterList = new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get("maker"), department));
			if((isMrg || isAmUser || isGM || isDGM) && department.equalsIgnoreCase("Trustee Bank")) {
				LOG.info("1");
				reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(NPSTRoleConstants.SUPERVISOR, department)));
				reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(NPSTRoleConstants.CRA_SUPERVISOR, department)));
			 }else if((isMrg || isAmUser || isGM || isDGM) && department.equalsIgnoreCase("PFM")) {
				 LOG.info("1");
				 reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(NPSTRoleConstants.CRA_MAKER, department)));
			 }else if((isMrg || isAmUser || isGM || isDGM) && department.equalsIgnoreCase("Custodian")) {
				 LOG.info("1");
				 reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(NPSTRoleConstants.CUSTODIAN_MAKER, department)));
				 reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(NPSTRoleConstants.CUSTODIAN_AM, department)));
			 }else if(isPfrdaUserGrievance && department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				 LOG.info("1");
				 reportMasterList = new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(NPSTRoleConstants.GRIEVANCES_AM_MGR, department));
			 }else if(isPfrdaUserCRA && department.equalsIgnoreCase(NPSCompany.CRA)) {
				 LOG.info("1");
				 reportMasterList = new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get("NPSTAM-CRA"), department));
			 }else if(isPfrdaUserCustodian && department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				 LOG.info("1");
				 reportMasterList = new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get("NPSTAM-Custodian"), department));
			 }else if(isPfrdaUserPFM && department.equalsIgnoreCase(NPSCompany.PFM)) {
					LOG.info("isPfrdaUserPFM && department.equalsIgnoreCase(NPSCompany.PFM)");
					reportMasterList = reportMasterLocalService.getByUploaderRoleAndDepartment("NPSTAM-PFM", department);
			 }
			
			//reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRoleAndDepartment(roles.get("maker"), department)));
		}else if(isPfmSupervisor){
			LOG.info("1");
			reportMasterList = new ArrayList<>(reportMasterLocalService.getByUploaderRole(NPSTRoleConstants.PFM_MAKER));
			reportMasterList.addAll(new ArrayList<>(reportMasterLocalService.getByUploaderRole(NPSTRoleConstants.PFM_SUPERVISOR)));
		}else {
			LOG.info("1");
			reportMasterList = reportMasterLocalService.getReportMasters(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		
		for(ReportMaster rm : reportMasterList) {
			LOG.info(rm);
			getReportLogJSONObject(rm, mainJSONObject, userId, companyId, isNonNpsUser,intermediaryName);
		}
		
		//LOG.debug("Main Object :::::::::::; "+mainJSONObject.toString());
		
		mainJSONObject.put("Department", department);
		
		PrintWriter out = null;
		try {
			out = resourceResponse.getWriter();
			out.println(mainJSONObject.toString());
			out.flush();
		} catch(Exception e) {
			LOG.error(e.getMessage());
		} finally {
			out.close();
		}
	}
	
	private JSONObject getReportLogJSONObject(ReportMasterModel rm, JSONObject mainJsonObject, long userId, long companyId,
			boolean isNonNpsUser,String checkIntermediaryName) {
		long intermediaryId = 0;
		try {
			intermediaryId = commonRoleService.getUserIntermediaryId(rm.getIntermediarytype(), userId, companyId);
		} catch (PortalException e) {
			LOG.error("Exception on fetching intermediary details : "+e.getMessage(),e);
		}
		List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
		if(isNonNpsUser) {
			reportUploadLogs =ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(rm.getReportMasterId(), false);
		}else {
			reportUploadLogs =ReportUploadLogLocalServiceUtil.findByReportMasterIdAndUploaded(rm.getReportMasterId(), false);
			//reportUploadLogs = ReportUploadLogLocalServiceUtil.findReportUploadLogAndSubmitedToNPST(0,rm.getReportMasterId());
		}
		
		if(intermediaryId > 0) {
			reportUploadLogs = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndUploadedAndIntermediaryid(rm.getReportMasterId(), 0, intermediaryId);
		}
		
		JSONArray jsonArray = mainJsonObject.getJSONArray(rm.getReportType());
		if(Validator.isNull(jsonArray)) {
			jsonArray = JSONFactoryUtil.createJSONArray();
		}
		if(Validator.isNotNull(reportUploadLogs)) {
			for (ReportUploadLog reportUploadLog : reportUploadLogs) {
				if(isNonNpsUser && reportUploadLog.getIntermediaryname().equalsIgnoreCase(checkIntermediaryName) && Validator.isNotNull(checkIntermediaryName)) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					jsonObject.put("reportMasterId", rm.getReportMasterId());
					String reportName=rm.getReportName() + (Validator.isNotNull(rm.getCra()) ? " - " + rm.getCra() : "");
					 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
					jsonObject.put("reportName", reportName);
					jsonObject.put("isUploaded", "Pending");
					jsonObject.put("toBeUploadedBy", rm.getUploaderRole().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
					jsonObject.put("dueDate", DATE_FORMAT.format(reportUploadLog.getReportDate()));
					String mediaryName=reportUploadLog.getIntermediaryname();
					String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(mediaryName);
					if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
						mediaryName=intrmedName;
					}

					jsonObject.put("intermediaryName", mediaryName);
					jsonArray.put(jsonObject);
				}else if(isNonNpsUser && Validator.isNull(checkIntermediaryName)){
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					jsonObject.put("reportMasterId", rm.getReportMasterId());
					String reportName=rm.getReportName() + (Validator.isNotNull(rm.getCra()) ? " - " + rm.getCra() : "");
					 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
					jsonObject.put("reportName", reportName);
					jsonObject.put("isUploaded", "Pending");
					jsonObject.put("toBeUploadedBy", rm.getUploaderRole().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
					jsonObject.put("dueDate", DATE_FORMAT.format(reportUploadLog.getReportDate()));
					String mediaryName=reportUploadLog.getIntermediaryname();
					String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(mediaryName);
					if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
						mediaryName=intrmedName;
					}

					jsonObject.put("intermediaryName", mediaryName);
					//jsonObject.put("intermediaryName", reportUploadLog.getIntermediaryname());
					jsonArray.put(jsonObject);
				}else if(!isNonNpsUser && Validator.isNull(checkIntermediaryName)){
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					jsonObject.put("reportMasterId", rm.getReportMasterId());
					String reportName=rm.getReportName() + (Validator.isNotNull(rm.getCra()) ? " - " + rm.getCra() : "");
					 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
					jsonObject.put("reportName", reportName);
					jsonObject.put("isUploaded", "Pending");
					jsonObject.put("toBeUploadedBy", rm.getUploaderRole().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
					jsonObject.put("dueDate", DATE_FORMAT.format(reportUploadLog.getReportDate()));
					String mediaryName=reportUploadLog.getIntermediaryname();
					String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(mediaryName);
					if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
						mediaryName=intrmedName;
					}

					jsonObject.put("intermediaryName", mediaryName);
				//	jsonObject.put("intermediaryName", reportUploadLog.getIntermediaryname());
					jsonArray.put(jsonObject);
				}
				
			}
			mainJsonObject.put(rm.getReportType(), jsonArray);
		}
		
		return mainJsonObject;
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
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				boolean craSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_SUPERVISOR, userId, inherited);
				isNonNpsUser = (craMaker || craChecker || craSupervisor);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				boolean custodianMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_MAKER, userId, inherited);
				boolean custodianChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_CHECKER, userId, inherited);
				boolean custodianSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_SUPERVISOR, userId, inherited);
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				isNonNpsUser = (custodianMaker || custodianChecker || custodianSupervisor || pfmMaker || pfmChecker || pfmSupervisor);
				
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				boolean greMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_MAKER, userId, inherited);
				boolean greChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_CHECKER, userId, inherited);
				boolean greSupervisor = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_SUPERVISOR, userId, inherited);
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				isNonNpsUser = (greMaker || greChecker || greSupervisor || craMaker || craChecker);
			}
			
			
		} catch (Exception e) {
			LOG.error("Exception on setting default values : "+e.getMessage(),e);
		}
	}
	
	

	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	@Reference
	private CommonRoleService commonRoleService;
	
	@Reference
	private UserLocalService userLocalService;
	
	@Reference
	private NPSTUserUtil npstUserUtil;
}
