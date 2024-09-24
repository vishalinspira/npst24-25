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
import com.daily.average.service.model.ReportStatusLog;
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
import com.daily.average.service.service.InputQuarterlyIntervalLocalService;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalService;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportStatusLogLocalServiceUtil;
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
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;
import com.nps.dashboard.web.util.NPSTUserUtil;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.portlet.PortletRequest;
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
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_COMPANY_REPORTS_RESOURCE_COMMAND+"old"
		},
		service = MVCResourceCommand.class
)
public class FetchCompanyReportsMVCResourceCommand1 extends BaseMVCResourceCommand {

	private static final Log LOG = LogFactoryUtil.getLog(FetchCompanyReportsMVCResourceCommand1.class);
	private static boolean isNonNpsUser = Boolean.FALSE;
	private String companyName = StringPool.BLANK;
	private static boolean isPfrda = Boolean.FALSE;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		LOG.info("Calling FetchCompanyReportsMVCResourceCommand1..");
		String reportsType = ParamUtil.getString(resourceRequest, "reportsType");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		isNonNpsUser=npsDashboardUtil.isNonNpsUser(themeDisplay.getUserId());
		String department = ParamUtil.getString(resourceRequest, "department");
		String intermediaryName = npstUserUtil.getIntermediaryName(themeDisplay);
        long companyId = themeDisplay.getCompanyId();
        if(department.isEmpty()) {
        	companyName = npsDashboardUtil.getCompanyName(companyId, themeDisplay.getUserId());
        }else {
        	companyName = department;
        }

		LOG.debug("Logged in user department name : "+companyName);
		setDefaultValues(themeDisplay);
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		if(reportsType.equals("MAKERS_REPORT_SUMMARY")) {
			LOG.info("MAKERS_REPORT_SUMMARY");
			try {
				PrintWriter writer = resourceResponse.getWriter();
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					jsonArray =(getAllDocsForMaker(resourceRequest, themeDisplay, dateFormat, companyId,isNonNpsUser,intermediaryName, isPfrda));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					jsonArray = (getCRAGrievReportsForMaker(resourceRequest, themeDisplay, dateFormat, companyId,isNonNpsUser,intermediaryName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					jsonArray = (getCustodianReportsForMaker(resourceRequest, themeDisplay, dateFormat, companyId,isNonNpsUser,intermediaryName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
					jsonArray = (getTrusteeBankReportsForMaker(resourceRequest, themeDisplay, dateFormat, companyId,isNonNpsUser,intermediaryName));
				}
				try {
				//SaveCacheUtil.saveCache(skey, jsonArray.toString());
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
			LOG.info("REPORT_SUMMARY ALL ");
			if(isNonNpsUser) {
				LOG.info("Maker or checker report for all types");
				try {
					PrintWriter writer = resourceResponse.getWriter();
					//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();;
					if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
						//jsonArray = npstUserUtil.getSubmittedToPFMUser(resourceRequest, isMakerOrChecker,isNonNpsUser,intermediaryName);
						jsonArray = getSubmittedNPSTPFMUser(resourceRequest, intermediaryName);
					}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
						//jsonArray = npstUserUtil.getSubmittedToCRAUser(resourceRequest, isMakerOrChecker, companyName,isNonNpsUser,intermediaryName);
						jsonArray = getSubmittedNPSTCRA_GrievUser(resourceRequest, intermediaryName);
					}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
						//jsonArray = npstUserUtil.getSubmittedToCustodianUser(resourceRequest, isPFMNonNpsUser,isNonNpsUser,intermediaryName);
						jsonArray = getSubmittedNPST_CustodianUser(resourceRequest, intermediaryName);
					}else if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
						jsonArray = getSubmittedNPST_TrusteeBankUser(resourceRequest, intermediaryName);
					}
					writer.println(jsonArray);
					writer.flush();
					return;
				} catch (PortalException e) {
					LOG.error("Exception on getting submitted to NPST user reports : "+e.getMessage(),e);
				}
			} 
		}
		
		if(reportsType.equals("APPROVED_REPORT_SUMMERY")) {
			//skey = department+themeDisplay.getUserId()+"approved";
			try {
				PrintWriter writer = resourceResponse.getWriter();
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					jsonArray =(getPFMApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda,intermediaryName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					jsonArray =(getCRA_GrievApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda,intermediaryName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					jsonArray =(getCustodianApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda,intermediaryName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
					jsonArray =(getTrusteeBankApprovedReportSummary(resourceRequest, themeDisplay, dateFormat, companyId, isPfrda,intermediaryName));
				}
				writer.println(jsonArray);
				writer.flush();
				return;
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
		}
		
	}

	/**
	 * 
	 * @param themeDisplay
	 */
	private void setDefaultValues(ThemeDisplay themeDisplay) {
		long userId = themeDisplay.getUserId();
		long companyId = themeDisplay.getCompanyId();
		boolean inherited = Boolean.FALSE;
		try {
			if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_PFRDA, userId, inherited);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_PFRDA, userId, inherited);
			}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_PFRDA, userId, inherited);
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				isPfrda = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_PFRDA, userId, inherited);
			}
		} catch (Exception e) {
			LOG.error("Exception on setting default values : "+e.getMessage(),e);
		}
	}

	/**
	 * 
	 * @param portletRequest
	 * @param isMakerOrChecker
	 * @param isNonNpsUser
	 * @param intermediaryName
	 * @return
	 * @throws Exception
	 */
	public JSONArray getSubmittedNPSTPFMUser(PortletRequest portletRequest,String intermediaryName) throws Exception{
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
						LOG.info("call getSubmittedNPSTPFMUser method ");

						JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
						List<ReportStatusLog> reportStatusLogs= null;
						if(isNonNpsUser) {
							reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndSubmittedToNPST(NPSCompany.PFM, intermediaryName,1);
						}else {
							reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndSubmittedToNPST(NPSCompany.PFM,1);
						}
						for(ReportStatusLog reportStatusLog:reportStatusLogs) {
							try {
						if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFM.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAM.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(InputQuarterlyInterval.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(MnCompCertificate.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(AnnualCompCertificate.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(QtrStewardshipReport.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(MnNpaDevelopment.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(DAR.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(PFM_hy_comp_cert.class.getName())){
							jsonObject = getReportSummeryObject(reportStatusLog);
						}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								jsonArray.put(jsonObject);
							}
						}catch (Exception e) {
							LOG.error("Exception on fetching log reports : "+e.getMessage());
						}
						}
				}catch (Exception e) {
					LOG.error("Exception on fetching log reports : "+e.getMessage());
				}
		return jsonArray;

	
	}
	
	/**
	 * 
	 * @param portletRequest
	 * @param isMakerOrChecker
	 * @param isNonNpsUser
	 * @param intermediaryName
	 * @return
	 * @throws Exception
	 */
	public JSONArray getSubmittedNPSTCRA_GrievUser(PortletRequest portletRequest,String intermediaryName) throws Exception{
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			LOG.info("call getSubmittedNPSTCRA_GrievUser method ");
			List<ReportStatusLog> reportStatusLogs= null;
			if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				if(isNonNpsUser) {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndSubmittedToNPST(NPSCompany.CRA, intermediaryName,1);
				}else {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndSubmittedToNPST(NPSCompany.CRA,1);
				}
								for(ReportStatusLog reportStatusLog:reportStatusLogs) {
									try {
										JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRA.class.getName()) ){
										jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
									}
									if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
										jsonArray.put(jsonObject);
									}	
									}catch (Exception e) {
										LOG.error(e);
									}
								}
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				
				if(isNonNpsUser) {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndSubmittedToNPST(NPSCompany.GRIEVANCES, intermediaryName,1);
				}else {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndSubmittedToNPST(NPSCompany.GRIEVANCES,1);
				}
								for(ReportStatusLog reportStatusLog:reportStatusLogs) {
									try {
										JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ||
											reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName()) ){
										jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
									}
									if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
										jsonArray.put(jsonObject);
									}	
									}catch (Exception e) {
										LOG.error(e);
									}
								}
			}
		}catch (Exception e) {
			LOG.error("Exception : "+e.getMessage(), e);
		}
		return jsonArray;
	}
	
	/**
	 * 
	 * @param portletRequest
	 * @param isMakerOrChecker
	 * @param isNonNpsUser
	 * @param intermediaryName
	 * @return
	 * @throws Exception
	 */
	public JSONArray getSubmittedNPST_CustodianUser(PortletRequest portletRequest,String intermediaryName) throws Exception{
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("call getSubmittedNPST_CustodianUser method ");
		List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndSubmittedToNPST(NPSCompany.CUSTODIAN,1);

						for(ReportStatusLog reportStatusLog:reportStatusLogs) {
							try {
								JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
							if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
								jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);
							}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								jsonArray.put(jsonObject);
							}	
							}catch (Exception e) {
								LOG.error(e);
							}
						}
		return jsonArray;

	}
	
	/**
	 * 
	 * @param portletRequest
	 * @param isMakerOrChecker
	 * @param isNonNpsUser
	 * @param intermediaryName
	 * @return
	 * @throws Exception
	 */
	public JSONArray getSubmittedNPST_TrusteeBankUser(PortletRequest portletRequest,String intermediaryName) throws Exception{
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("call getSubmittedNPST_TrusteeBankUser method ");
		List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndSubmittedToNPST(NPSCompany.NPST,1);		

						for(ReportStatusLog reportStatusLog:reportStatusLogs) {
							try {
								JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
							if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogNPST.class.getName()) ){
								jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);
							}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								jsonArray.put(jsonObject);
							}	
							}catch (Exception e) {
								LOG.error(e);
							}
						}
		return jsonArray;

	}
	
	/**
	 * Get All PFM Reports
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @param isNonNpsUser
	 * @param intermediaryName
	 * @param isPfrdaUserPFM
	 * @return
	 */
	public JSONArray getAllDocsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat,
			long companyId, boolean isNonNpsUser, String intermediaryName, boolean isPfrdaUserPFM){
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
						LOG.info("call getAllDocsForMaker method ");

						JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
						List<ReportStatusLog> reportStatusLogs= null;
						if(isNonNpsUser) {
							reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryName(NPSCompany.PFM, intermediaryName);
						}else {
							reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.PFM);
						}
						for(ReportStatusLog reportStatusLog:reportStatusLogs) {
							try {
//							if(isPfrdaUserPFM) {
//								/* Report Upload By PFM-AM-PFRDA */
//								if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
//									jsonObject = getReportSummeryObjectNonNPS(reportStatusLog);
//								}
//							}else {
							
						if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFM.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAM.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(InputQuarterlyInterval.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(MnCompCertificate.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(AnnualCompCertificate.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(QtrStewardshipReport.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(MnNpaDevelopment.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(DAR.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(PFM_hy_comp_cert.class.getName())
																){
							jsonObject = getReportSummeryObjectNonNPS(reportStatusLog);
						}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								jsonArray.put(jsonObject);
							}	
						}catch (Exception e) {
							LOG.error("Exception on fetching log reports : "+e.getMessage());
						}
						}
				}catch (Exception e) {
					LOG.error("Exception on fetching log reports : "+e.getMessage());
				}

		return jsonArray;
	}

	/**
	 * Get CRA And Grievances Report
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @param isNonNpsUser
	 * @param intermediaryName
	 * @return
	 */
	private JSONArray getCRAGrievReportsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay,
			DateFormat dateFormat, long companyId,boolean isNonNpsUser,String intermediaryName) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			LOG.info("call getCRAGrievReportsForMaker method ");
			List<ReportStatusLog> reportStatusLogs= null;
			if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				if(isNonNpsUser) {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryName(NPSCompany.CRA, intermediaryName);
				}else {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.CRA);
				}
								for(ReportStatusLog reportStatusLog:reportStatusLogs) {
									try {
										JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRA.class.getName()) ){
										jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
									}
									if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
										jsonArray.put(jsonObject);
									}	
									}catch (Exception e) {
										LOG.error(e);
									}
								}
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				if(isNonNpsUser) {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryName(NPSCompany.GRIEVANCES, intermediaryName);
				}else {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.GRIEVANCES);
				}
								for(ReportStatusLog reportStatusLog:reportStatusLogs) {
									try {
										JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ||
											reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName()) ){
										jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
									}
									if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
										jsonArray.put(jsonObject);
									}	
									}catch (Exception e) {
										LOG.error(e);
									}
								}
			}
		
		}catch (Exception e) {
			LOG.error("Exception : "+e.getMessage(), e);
		}
		return jsonArray;
	
	}
	
	
	/*
	 * get Reports for Custodian
	 */
	private JSONArray getCustodianReportsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay,
			DateFormat dateFormat, long companyId, boolean isNonNpsUser,String intermediaryName) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("call getCustodianReportsForMaker method ");
		List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.CUSTODIAN);	

						for(ReportStatusLog reportStatusLog:reportStatusLogs) {
							try {
								JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
							if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
								jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
							}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								jsonArray.put(jsonObject);
							}	
							}catch (Exception e) {
								LOG.error(e);
							}
						}
		return jsonArray;
	
	}
	
	/**
	 * 
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @param isNonNpsUser
	 * @param intermediaryName
	 * @return
	 */
	private JSONArray getTrusteeBankReportsForMaker(ResourceRequest resourceRequest, ThemeDisplay themeDisplay,
			DateFormat dateFormat, long companyId, boolean isNonNpsUser,String intermediaryName) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("call getTrusteeBankReportsForMaker method ");
		List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.NPST);
						for(ReportStatusLog reportStatusLog:reportStatusLogs) {
							try {
								JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
								if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ||
										reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogNPST.class.getName()) ){
								jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
							}
							if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
								jsonArray.put(jsonObject);
							}	
							}catch (Exception e) {
								LOG.error(e);
							}
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
	public JSONArray getPFMApprovedReportSummary(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId, boolean isPfrda,String intermediaryName) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("Get pfm approved report summary for user.");

		List<ReportStatusLog> reportStatusLogs= null;		
		if(isNonNpsUser) {
			reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndIsApproved(NPSCompany.PFM, intermediaryName,1);
		}else {
			reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.PFM,1);
		}
					for(ReportStatusLog reportStatusLog:reportStatusLogs) {
						int status = -1;
						try {
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					if(!isPfrda) {
						if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFM.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAM.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(InputQuarterlyInterval.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(MnCompCertificate.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(AnnualCompCertificate.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(QtrStewardshipReport.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(MnNpaDevelopment.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(DAR.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(PFM_hy_comp_cert.class.getName())){
							jsonObject =getReportSummeryObject(reportStatusLog);
							status=reportStatusLog.getStatus();	
						}	
					}
					if(status == 0) {
						jsonArray.put(jsonObject);
					}
						}catch (Exception e) {
							LOG.error(e);
						}
					}
		return jsonArray;
	}

	
	/**
	 * 
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @param isPfrda
	 * @return
	 * @throws PortalException
	 */
	public JSONArray getCRA_GrievApprovedReportSummary(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId, boolean isPfrda,String intermediaryName) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			LOG.info("call getCRA_GrievApprovedReportSummary method ");
			List<ReportStatusLog> reportStatusLogs= null;
			if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
				if(isNonNpsUser) {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndIsApproved(NPSCompany.CRA, intermediaryName,1);
				}else {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.CRA,1);
				}
								for(ReportStatusLog reportStatusLog:reportStatusLogs) {
									try {
										JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRA.class.getName()) ){
										jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
									}
									if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
										jsonArray.put(jsonObject);
									}	
									}catch (Exception e) {
										LOG.error(e);
									}
								}
			}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
				if(isNonNpsUser) {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndIsApproved(NPSCompany.GRIEVANCES, intermediaryName,1);
				}else {
					reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.GRIEVANCES,1);
				}
								for(ReportStatusLog reportStatusLog:reportStatusLogs) {
									try {
										JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ||
											reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName()) ){
										jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
									}
									if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
										jsonArray.put(jsonObject);
									}	
									}catch (Exception e) {
										LOG.error(e);
									}
								}
			}
		
		}catch (Exception e) {
			LOG.error("Exception : "+e.getMessage(), e);
		}
		return jsonArray;
	
	}
	
	/**
	 * 
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @param isPfrda
	 * @return
	 * @throws PortalException
	 */
	public JSONArray getCustodianApprovedReportSummary(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId, boolean isPfrda,String intermediaryName) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("call getCustodianApprovedReportSummary method..");
		List<ReportStatusLog> reportStatusLogs= ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.CUSTODIAN,1);

		for(ReportStatusLog reportStatusLog:reportStatusLogs) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
					reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
					reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
				jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
			}
			if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
				jsonArray.put(jsonObject);
			}	
			}catch (Exception e) {
				LOG.error(e);
			}
		}
		return jsonArray;
	}

	/**
	 * 
	 * @param resourceRequest
	 * @param themeDisplay
	 * @param dateFormat
	 * @param companyId
	 * @param isPfrda
	 * @return
	 * @throws PortalException
	 */
	public JSONArray getTrusteeBankApprovedReportSummary(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, long companyId, boolean isPfrda,String intermediaryName) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		LOG.info("call getTrusteeBankApprovedReportSummary method..");
		List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.NPST,1);

		for(ReportStatusLog reportStatusLog:reportStatusLogs) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ||
						reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogNPST.class.getName()) ){
				jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
			}
			if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
				jsonArray.put(jsonObject);
			}	
			}catch (Exception e) {
				LOG.error(e);
			}
		}
		return jsonArray;
	}
	
	private JSONObject getReportSummeryObject(ReportStatusLog reportStatusLog) {
//		String intermediaryName = StringPool.BLANK;
//
//		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
//		jsonObject.put("fileName", reportStatusLog.getReportName());
//		jsonObject.put("reportUploadLogId",reportStatusLog.getReportUploadLogId());
//		jsonObject.put("reportMasterId", reportStatusLog.getReportMasterId());
//			jsonObject.put("url", reportStatusLog.getFileUrl());
//			jsonObject.put("userName", reportStatusLog.getUserName());
//		
//		jsonObject.put("dueDate",dateFormat.format(reportStatusLog.getReportDate()));
//		jsonObject.put("createDate", dateFormat.format(reportStatusLog.getCreateDate()));
//		jsonObject.put("status", (reportStatusLog.getStatus_() != null && !reportStatusLog.getStatus_().isEmpty() ? reportStatusLog.getStatus_().toUpperCase() : WorkflowConstants.getStatusLabel(reportStatusLog.getStatus()).toUpperCase()));
//		jsonObject.put("remarks", reportStatusLog.getRemarks());
//		jsonObject.put("workflowInstanceId", reportStatusLog.getWorkflowInstanceId());
//		String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(reportStatusLog.getIntermediaryname());
//		if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
//			intermediaryName=intrmedName;
//		}
//		jsonObject.put("intermediaryName", intermediaryName);
//		jsonObject.put("assignedTo", reportStatusLog.getAssignedTo().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
//		
		return getReportSummeryObjectNonNPS(reportStatusLog);
	}
	
	private JSONObject getReportSummeryObjectNonNPS(ReportStatusLog reportStatusLog) {
		String intermediaryName = StringPool.BLANK;

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("fileName", reportStatusLog.getReportName());
		jsonObject.put("reportUploadLogId",reportStatusLog.getReportUploadLogId());
		jsonObject.put("reportMasterId", reportStatusLog.getReportMasterId());
		jsonObject.put("dueDate", dateFormat.format(reportStatusLog.getReportDate()));
		jsonObject.put("createDate", dateFormat.format(reportStatusLog.getCreateDate()));
		jsonObject.put("status", (reportStatusLog.getStatus_() != null && !reportStatusLog.getStatus_().isEmpty() ? reportStatusLog.getStatus_().toUpperCase() : WorkflowConstants.getStatusLabel(reportStatusLog.getStatus()).toUpperCase()));
		jsonObject.put("remarks", reportStatusLog.getRemarks());
		jsonObject.put("workflowInstanceId", reportStatusLog.getWorkflowInstanceId());
		jsonObject.put("url", reportStatusLog.getFileUrl());
		jsonObject.put("userName", reportStatusLog.getUserName());
		jsonObject.put("intermediaryName", reportStatusLog.getIntermediaryname());
		jsonObject.put("assignedTo", reportStatusLog.getAssignedTo().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
		String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
		if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
			intermediaryName=intrmedName;
		}
		
//		if(isNonNpsUser && intermediaryName.equalsIgnoreCase(checkIntermediaryName) && Validator.isNotNull(checkIntermediaryName) 
//				&& npsDashboardUtil.isNonNpsUser(assignedToId)) {
//		}else if(isNonNpsUser && Validator.isNull(checkIntermediaryName) && npsDashboardUtil.isNonNpsUser(assignedToId)){
//		}else if(!isNonNpsUser && Validator.isNull(checkIntermediaryName)){
//		}
		return jsonObject;
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
