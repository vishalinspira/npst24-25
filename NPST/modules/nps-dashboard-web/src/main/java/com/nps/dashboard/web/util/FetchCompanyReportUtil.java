package com.nps.dashboard.web.util;



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
import com.daily.average.service.model.ReportUploadLogCRAAM;
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
import com.daily.average.service.service.ReportStatusLogLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.constants.NameMappingConstants;
@Component(immediate = true, service = FetchCompanyReportUtil.class)
	public class FetchCompanyReportUtil {

		private static final Log LOG = LogFactoryUtil.getLog(FetchCompanyReportUtil.class);
		private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		

		
		
		/**
		 * 
		 * @param department
		 * @param reportsType
		 * @return
		 */
		public List<ReportStatusLog>   getReports(String department,String reportsType,ThemeDisplay themeDisplay){

		//LOG.info("after context user id: "+themeDisplay.getUserId());
		NPSDashboardUtil npsDashboardUtil=new NPSDashboardUtil();
		NPSTUserUtil npstUserUtil=new NPSTUserUtil();
		
		boolean isNonNpsUser=npsDashboardUtil.isNonNpsUser(themeDisplay.getUserId());
		
		String companyName = StringPool.BLANK;
	//	LOG.info("isNon NPS user : "+isNonNpsUser);
		
		String intermediaryName = npstUserUtil.getIntermediaryName(themeDisplay);
		 long companyId = themeDisplay.getCompanyId();
	     if(department.isEmpty()) {
	     	companyName = npsDashboardUtil.getCompanyName(companyId, themeDisplay.getUserId());
	     }else {
	     	companyName = department;
	     }
	     boolean isPfrda=isPfrdaUser(themeDisplay, companyName);
	     LOG.info("isNonnOs user : "+ isNonNpsUser +"   department: "+department +"  intermediary name : "+intermediaryName);
		if(reportsType.equals("MAKERS_REPORT_SUMMARY")) {
			LOG.info("MAKERS_REPORT_SUMMARY");
			try {
	
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					return (getAllDocsForMaker( dateFormat, companyId,isNonNpsUser,intermediaryName,isPfrda));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					return (getCRAGrievReportsForMaker( dateFormat, companyId,isNonNpsUser,intermediaryName,companyName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					return (getCustodianReportsForMaker( dateFormat, companyId,isNonNpsUser,intermediaryName,companyName));
				}else if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
					return (getTrusteeBankReportsForMaker( dateFormat, companyId,isNonNpsUser,intermediaryName,companyName));
				}
	
			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
			
		}
		
//		if(reportsType.equals("ALL")) {
//			LOG.info("REPORT_SUMMARY ALL ");
//			if(isNonNpsUser) {
//				LOG.info("Maker or checker report for all types");
//				//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();;
//				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
//					//jsonArray = npstUserUtil.getSubmittedToPFMUser(resourceRequest, isMakerOrChecker,isNonNpsUser,intermediaryName);
//					return getSubmittedNPSTPFMUser(intermediaryName,isNonNpsUser);
//				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
//					//jsonArray = npstUserUtil.getSubmittedToCRAUser(resourceRequest, isMakerOrChecker, companyName,isNonNpsUser,intermediaryName);
//					return getSubmittedNPSTCRA_GrievUser( intermediaryName,companyName,isNonNpsUser);
//				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
//					//jsonArray = npstUserUtil.getSubmittedToCustodianUser(resourceRequest, isPFMNonNpsUser,isNonNpsUser,intermediaryName);
//					return getSubmittedNPST_CustodianUser( intermediaryName,isNonNpsUser);
//				}else if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
//					return getSubmittedNPST_TrusteeBankUser( intermediaryName,isNonNpsUser);
//				}
//			} 
//		}
		
		if(reportsType.equals("APPROVED_REPORT_SUMMERY")) {
			//skey = department+themeDisplay.getUserId()+"approved";
			try {

				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					return (getPFMApprovedReportSummary( dateFormat, companyId,intermediaryName,isNonNpsUser,isPfrda));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					return (getCRA_GrievApprovedReportSummary( dateFormat, companyId,intermediaryName,companyName,isNonNpsUser));
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					return (getCustodianApprovedReportSummary( dateFormat, companyId,intermediaryName,isNonNpsUser));
				}else if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
					return (getTrusteeBankApprovedReportSummary( dateFormat, companyId,intermediaryName,isNonNpsUser));
				}

			} catch (Exception e) {
				LOG.error("Error Sending Feedbacks" + e.getMessage());
			}
		}
		return null;
		
 }

		/**
		 * 
		 * @param department
		 * @param reportsType
		 * @param durationType
		 * @param isNonNpsUser
		 * @param intermediaryName
		 * @param companyName
		 * @param themeDisplay
		 * @return
		 */
		public List<ReportStatusLog>   getSubmitToNPSTReports(String department,String reportsType,String durationType,boolean isNonNpsUser,String intermediaryName,String companyName,ThemeDisplay themeDisplay){
		boolean isPfrda=isPfrdaUser(themeDisplay, companyName);
		if(reportsType.equals("ALL")) {
			LOG.info("REPORT_SUMMARY ALL, isNON_NPS user : "+isNonNpsUser);
			//if(isNonNpsUser) {
			LOG.info("isNonnOs user : "+ isNonNpsUser +"   department: "+department +"  intermediary name : "+intermediaryName);
				//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();;
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					//jsonArray = npstUserUtil.getSubmittedToPFMUser(resourceRequest, isMakerOrChecker,isNonNpsUser,intermediaryName);
					return getSubmittedNPSTPFMUser(intermediaryName,isNonNpsUser,durationType,isPfrda);
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA) || companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					//jsonArray = npstUserUtil.getSubmittedToCRAUser(resourceRequest, isMakerOrChecker, companyName,isNonNpsUser,intermediaryName);
					return getSubmittedNPSTCRA_GrievUser( intermediaryName,companyName,isNonNpsUser,durationType);
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					//jsonArray = npstUserUtil.getSubmittedToCustodianUser(resourceRequest, isPFMNonNpsUser,isNonNpsUser,intermediaryName);
					return getSubmittedNPST_CustodianUser( intermediaryName,isNonNpsUser,durationType);
				}else if(companyName.equalsIgnoreCase(NPSCompany.NPST)) {
					return getSubmittedNPST_TrusteeBankUser( intermediaryName,isNonNpsUser,durationType);
				}
			//} 
		}
		return null;
		
 }
		

		/**
		 * 
		 * @param themeDisplay
		 */
//		private boolean  setDefaultValues(String companyName,long userId,long companyId) {
//			boolean isPfrda=false;
//			boolean inherited = Boolean.FALSE;
//			try {
//				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
//					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_PFRDA, userId, inherited);
//				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
//					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_PFRDA, userId, inherited);
//				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
//					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_PFRDA, userId, inherited);
//				}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
//					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_PFRDA, userId, inherited);
//				}
//			} catch (Exception e) {
//				LOG.error("Exception on setting default values : "+e.getMessage(),e);
//			}
//			return isPfrda;
//		}

		/**
		 * 
		 * @param portletRequest
		 * @param isMakerOrChecker
		 * @param isNonNpsUser
		 * @param intermediaryName
		 * @return
		 * @throws Exception
		 */
		public List<ReportStatusLog> getSubmittedNPSTPFMUser(String intermediaryName,boolean isNonNpsUser,String reportType,boolean isPfrda){
			//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			try {
							LOG.info("call getSubmittedNPSTPFMUser method ");

							//JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
							List<ReportStatusLog> reportStatusLogs= null;
							if(isNonNpsUser) {
								reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptAndIntermediaryAndSubmittedToNPSTAndReportType(NPSCompany.PFM, intermediaryName,1,reportType);
							}else {
								reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptmentAndSubmittedToNPSTAndReportType(NPSCompany.PFM,1,reportType);
							}
							for(ReportStatusLog reportStatusLog:reportStatusLogs) {
								try {
									if(isPfrda) {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
											list.add(reportStatusLog);
										}
									}else {
							if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFM.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAM.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(InputQuarterlyInterval.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(MnCompCertificate.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(AnnualCompCertificate.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(QtrStewardshipReport.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(MnNpaDevelopment.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(DAR.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(PFM_hy_comp_cert.class.getName())){
								//jsonObject = getReportSummeryObject(reportStatusLog);
								list.add(reportStatusLog);
							}
								}
								//if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
									//jsonArray.put(jsonObject);
								//}
							}catch (Exception e) {
								LOG.error("Exception on fetching log reports : "+e.getMessage());
							}
							}
					}catch (Exception e) {
						LOG.error("Exception on fetching log reports : "+e.getMessage());
					}
			LOG.info("data fetched successfully....");
			return list;

		
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
		public List<ReportStatusLog> getSubmittedNPSTCRA_GrievUser(String intermediaryName,String companyName ,boolean isNonNpsUser,String reportType){
			//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			try {
				LOG.info("call getSubmittedNPSTCRA_GrievUser method ");
				List<ReportStatusLog> reportStatusLogs= null;
				if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
					if(isNonNpsUser) {
						reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptAndIntermediaryAndSubmittedToNPSTAndReportType(NPSCompany.CRA, intermediaryName,1,reportType);
					}else {
						reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptmentAndSubmittedToNPSTAndReportType(NPSCompany.CRA,1,reportType);
					}
									for(ReportStatusLog reportStatusLog:reportStatusLogs) {
										try {
											//JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRA.class.getName()) ||reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRAAM.class.getName()) ){
											//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
											list.add(reportStatusLog);
										}
//										if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
//											jsonArray.put(jsonObject);
//										}	
										}catch (Exception e) {
											LOG.error(e);
										}
									}
				}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					
					if(isNonNpsUser) {
						reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptAndIntermediaryAndSubmittedToNPSTAndReportType(NPSCompany.GRIEVANCES, intermediaryName,1,reportType);
					}else {
						reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptmentAndSubmittedToNPSTAndReportType(NPSCompany.GRIEVANCES,1,reportType);
					}
									for(ReportStatusLog reportStatusLog:reportStatusLogs) {
										try {
											if(isNonNpsUser) {
												if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ){
													list.add(reportStatusLog);
												}
											}else {
												if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ||
														reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName()) ){
													list.add(reportStatusLog);
												}
											}
										
//										if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
//											jsonArray.put(jsonObject);
//										}	
										}catch (Exception e) {
											LOG.error(e);
										}
									}
				}
			}catch (Exception e) {
				LOG.error("Exception : "+e.getMessage(), e);
			}
			LOG.info("data fetched successfully....");
			return list;
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
		public List<ReportStatusLog> getSubmittedNPST_CustodianUser(String intermediaryName,boolean isNonNpsUser,String reportType){
			//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			LOG.info("call getSubmittedNPST_CustodianUser method ");
			List<ReportStatusLog> reportStatusLogs=null;
			if(isNonNpsUser) {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptAndIntermediaryAndSubmittedToNPSTAndReportType(NPSCompany.CUSTODIAN, intermediaryName, 1,reportType);
			}else {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptmentAndSubmittedToNPSTAndReportType(NPSCompany.CUSTODIAN,1,reportType);
			}
			
			//List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndSubmittedToNPST(NPSCompany.CUSTODIAN,1);

							for(ReportStatusLog reportStatusLog:reportStatusLogs) {
								try {
									if(isNonNpsUser) {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
												 reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
											//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
											list.add(reportStatusLog);
										}
									}else {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
											//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
											list.add(reportStatusLog);
										}
									}	
								}catch (Exception e) {
									LOG.error(e);
								}
							}
							LOG.info("data fetched successfully....");
			return list;

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
		public List<ReportStatusLog> getSubmittedNPST_TrusteeBankUser(String intermediaryName,boolean isNonNpsUser,String reportType){
			//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			LOG.info("call getSubmittedNPST_TrusteeBankUser method ");
			List<ReportStatusLog> reportStatusLogs=null;
//			if(isNonNpsUser) {
//				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptAndIntermediaryAndSubmittedToNPSTAndReportType(NPSCompany.NPST, intermediaryName, 1,reportType);
//			}else {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDeptmentAndSubmittedToNPSTAndReportType(NPSCompany.NPST,1,reportType);
			//}
			
			//List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndSubmittedToNPST(NPSCompany.NPST,1);		

							for(ReportStatusLog reportStatusLog:reportStatusLogs) {
								try {
									//JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(isNonNpsUser) {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ){
											list.add(reportStatusLog);
										}
									}else {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogNPST.class.getName()) ){
											list.add(reportStatusLog);
										}
									}
									
//								if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
//									jsonArray.put(jsonObject);
//								}	
								}catch (Exception e) {
									LOG.error(e);
								}
							}
							LOG.info("data fetched successfully....");
			return list;

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
		public List<ReportStatusLog> getAllDocsForMaker(DateFormat dateFormat,long companyId, boolean isNonNpsUser, String intermediaryName,boolean isPfrda){
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
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
									if(isPfrda) {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
											list.add(reportStatusLog);
										}
									}else {
							if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFM.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAM.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(InputQuarterlyInterval.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(MnCompCertificate.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(AnnualCompCertificate.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(QtrStewardshipReport.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(MnNpaDevelopment.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(DAR.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(PFM_hy_comp_cert.class.getName())
																	){
								//jsonObject = getReportSummeryObjectNonNPS(reportStatusLog);
								list.add(reportStatusLog);
							}
									}
								if(Validator.isNotNull(jsonObject)) {
									jsonArray.put(jsonObject);
								}	
							}catch (Exception e) {
								LOG.error("Exception on fetching log reports : "+e.getMessage());
							}
							}
					}catch (Exception e) {
						LOG.error("Exception on fetching log reports : "+e.getMessage());
					}
			LOG.info("data fetched successfully....");
			//return jsonArray;
			return list;
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
		private List<ReportStatusLog> getCRAGrievReportsForMaker(
				DateFormat dateFormat, long companyId,boolean isNonNpsUser,String intermediaryName,String companyName) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
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
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRAAM.class.getName()) ){
											//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
											list.add(reportStatusLog);
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
											if(isNonNpsUser) {
												if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ){
													list.add(reportStatusLog);
												}
											}else {
												if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ||
														reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName()) ){
													list.add(reportStatusLog);
												}
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
			LOG.info("data fetched successfully....");
			return list;
		
		}
		
		
		/*
		 * get Reports for Custodian
		 */
		private List<ReportStatusLog> getCustodianReportsForMaker(
				DateFormat dateFormat, long companyId, boolean isNonNpsUser,String intermediaryName,String companyName) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			LOG.info("call getCustodianReportsForMaker method ");
			List<ReportStatusLog> reportStatusLogs=null;
			if(isNonNpsUser) {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryName(NPSCompany.CUSTODIAN, intermediaryName);
			}else {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.CUSTODIAN);
			}

							for(ReportStatusLog reportStatusLog:reportStatusLogs) {
								try {
									JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(isNonNpsUser) {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
												 reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
											//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
											list.add(reportStatusLog);
										}
									}else {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
											//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
											list.add(reportStatusLog);
										}
									}
								
								if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
									jsonArray.put(jsonObject);
								}	
								}catch (Exception e) {
									LOG.error(e);
								}
							}
							LOG.info("data fetched successfully....");
							LOG.info("cust json array size:  "+jsonArray.length());
			return list;
		
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
		private List<ReportStatusLog> getTrusteeBankReportsForMaker(
				DateFormat dateFormat, long companyId, boolean isNonNpsUser,String intermediaryName,String companyName) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			LOG.info("call getTrusteeBankReportsForMaker method. ");
			List<ReportStatusLog> reportStatusLogs=null;
//			if(isNonNpsUser) {
//				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryName(NPSCompany.NPST, intermediaryName);
//			}else {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.NPST);
			//}
			//List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartment(NPSCompany.NPST);
							for(ReportStatusLog reportStatusLog:reportStatusLogs) {
								try {
									JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
									if(isNonNpsUser) {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ){
											list.add(reportStatusLog);
										}
									}else {
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ||
												reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogNPST.class.getName()) ){
											list.add(reportStatusLog);
										}
									}
								if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
									jsonArray.put(jsonObject);
								}	
								}catch (Exception e) {
									LOG.error(e);
								}
							}
							LOG.info("data fetched successfully....");
			return list;
		
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
		public List<ReportStatusLog> getPFMApprovedReportSummary( DateFormat dateFormat, long companyId,String intermediaryName,boolean isNonNpsUser,boolean isPfrda) throws PortalException {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
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
					//	if(!isPfrda) {
								if(isPfrda) {
									if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
										list.add(reportStatusLog);
										status=reportStatusLog.getStatus();
									}
								}else {
							if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFM.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAM.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(InputQuarterlyInterval.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(MnCompCertificate.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(AnnualCompCertificate.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(QtrStewardshipReport.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(MnNpaDevelopment.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(DAR.class.getName()) ||
									reportStatusLog.getClazzName().equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(PFM_hy_comp_cert.class.getName())){
								//jsonObject =getReportSummeryObject(reportStatusLog);
								list.add(reportStatusLog);
								status=reportStatusLog.getStatus();	
							}
							}
			//			}
						if(status == 0) {
							jsonArray.put(jsonObject);
						}
							}catch (Exception e) {
								LOG.error(e);
							}
						}
						LOG.info("data fetched successfully....");
			return list;
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
		public List<ReportStatusLog> getCRA_GrievApprovedReportSummary( DateFormat dateFormat, long companyId,String intermediaryName,String companyName,boolean isNonNpsUser) throws PortalException {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
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
										if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCRAAM.class.getName()) ){
											//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
											list.add(reportStatusLog);
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
											if(isNonNpsUser) {
												if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ){
													list.add(reportStatusLog);
												}
											}else {
												if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievances.class.getName()) ||
														reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName()) ){
													list.add(reportStatusLog);
												}
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
			LOG.info(Validator.isNotNull(list)?list.size():"list is empty"+"             data fetched successfully....");
			return list;
		
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
		public List<ReportStatusLog> getCustodianApprovedReportSummary(DateFormat dateFormat, long companyId,String intermediaryName,boolean isNonNpsUser) throws PortalException {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			LOG.info("call getCustodianApprovedReportSummary method..");
			List<ReportStatusLog> reportStatusLogs=null;
			if(isNonNpsUser) {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndIsApproved(NPSCompany.CUSTODIAN, intermediaryName, 1);
			}else {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.CUSTODIAN,1);
			}
			
			//List<ReportStatusLog> reportStatusLogs= ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.CUSTODIAN,1);

			for(ReportStatusLog reportStatusLog:reportStatusLogs) {
				try {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					if(isNonNpsUser) {
						if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
								 reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
							//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
							list.add(reportStatusLog);
						}
					}else {
						if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustodian.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(CustodianCompForm.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustAnnualAuditReport.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(CustIAR.class.getName())){
							//jsonObject =getReportSummeryObjectNonNPS(reportStatusLog);	
							list.add(reportStatusLog);
						}
					}
				if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
					jsonArray.put(jsonObject);
				}	
				}catch (Exception e) {
					LOG.error(e);
				}
			}
			LOG.info("data fetched successfully....");
			return list;
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
		public List<ReportStatusLog> getTrusteeBankApprovedReportSummary( DateFormat dateFormat, long companyId,String intermediaryName,boolean isNonNpsUser) throws PortalException {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
			List<ReportStatusLog> list=new ArrayList<ReportStatusLog>();
			LOG.info("call getTrusteeBankApprovedReportSummary method..");
			List<ReportStatusLog> reportStatusLogs=null;
//			if(isNonNpsUser) {
//				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIntermediaryAndIsApproved(NPSCompany.NPST, intermediaryName, 1);
//			}else {
				reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.NPST,1);
		//	}
			
			//List<ReportStatusLog> reportStatusLogs=ReportStatusLogLocalServiceUtil.findByDepartmentAndIsApproved(NPSCompany.NPST,1);

			for(ReportStatusLog reportStatusLog:reportStatusLogs) {
				try {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					if(isNonNpsUser) {
						if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ){
							list.add(reportStatusLog);
						}
					}else {
						if(reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogMaker.class.getName()) || reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogSupervisor.class.getName()) ||
								reportStatusLog.getClazzName().equalsIgnoreCase(ReportUploadLogNPST.class.getName()) ){
							list.add(reportStatusLog);
						}
					}
				if(Validator.isNotNull(jsonObject) && jsonObject.length() > 2) {
					jsonArray.put(jsonObject);
				}	
				}catch (Exception e) {
					LOG.error(e);
				}
			}
			LOG.info("data fetched successfully....");
			return list;
		}
		
		private JSONObject getReportSummeryObject(ReportStatusLog reportStatusLog) {
//			String intermediaryName = StringPool.BLANK;
	//
//			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
//			jsonObject.put("fileName", reportStatusLog.getReportName());
//			jsonObject.put("reportUploadLogId",reportStatusLog.getReportUploadLogId());
//			jsonObject.put("reportMasterId", reportStatusLog.getReportMasterId());
//				jsonObject.put("url", reportStatusLog.getFileUrl());
//				jsonObject.put("userName", reportStatusLog.getUserName());
//			
//			jsonObject.put("dueDate",dateFormat.format(reportStatusLog.getReportDate()));
//			jsonObject.put("createDate", dateFormat.format(reportStatusLog.getCreateDate()));
//			jsonObject.put("status", (reportStatusLog.getStatus_() != null && !reportStatusLog.getStatus_().isEmpty() ? reportStatusLog.getStatus_().toUpperCase() : WorkflowConstants.getStatusLabel(reportStatusLog.getStatus()).toUpperCase()));
//			jsonObject.put("remarks", reportStatusLog.getRemarks());
//			jsonObject.put("workflowInstanceId", reportStatusLog.getWorkflowInstanceId());
//			String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(reportStatusLog.getIntermediaryname());
//			if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
//				intermediaryName=intrmedName;
//			}
//			jsonObject.put("intermediaryName", intermediaryName);
//			jsonObject.put("assignedTo", reportStatusLog.getAssignedTo().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
//			
			return getReportSummeryObjectNonNPS(reportStatusLog);
		}
		
		private JSONObject getReportSummeryObjectNonNPS(ReportStatusLog reportStatusLog) {
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
			String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
			if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
				intermediaryName=intrmedName;
			}
			jsonObject.put("intermediaryName",intermediaryName );
			jsonObject.put("assignedTo", reportStatusLog.getAssignedTo().replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
			
			
//			if(isNonNpsUser && intermediaryName.equalsIgnoreCase(checkIntermediaryName) && Validator.isNotNull(checkIntermediaryName) 
//					&& npsDashboardUtil.isNonNpsUser(assignedToId)) {
//			}else if(isNonNpsUser && Validator.isNull(checkIntermediaryName) && npsDashboardUtil.isNonNpsUser(assignedToId)){
//			}else if(!isNonNpsUser && Validator.isNull(checkIntermediaryName)){
//			}
			return jsonObject;
		}
		
		private boolean isPfrdaUser(ThemeDisplay themeDisplay,String companyName) {
			boolean isPfrda=Boolean.FALSE;
			long userId = themeDisplay.getUserId();
			long companyId = themeDisplay.getCompanyId();
			boolean inherited = Boolean.FALSE;
			try {
				if(companyName.equalsIgnoreCase(NPSCompany.PFM)) {
					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_PFRDA, userId, inherited);
				}else if(companyName.equalsIgnoreCase(NPSCompany.CRA)) {
					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_PFRDA, userId, inherited);
				}else if(companyName.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_PFRDA, userId, inherited);
				}else if(companyName.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
					isPfrda = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_PFRDA, userId, inherited);
				}
			} catch (Exception e) {
				LOG.error("Exception on setting default values : "+e.getMessage(),e);
			}
			return isPfrda;
		}

	}

