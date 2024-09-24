package reportUploadLog.mvc;

import com.daily.average.service.exception.NoSuchReportMasterException;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.util.CommonRoleService;
import reportUploadLog.constants.ReportUploadLogPortletKeys;

@Component(property = { 
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXURE16INTERNALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.DAILYAVERAGE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AN15ANNUALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AN18BIODATA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AN4WEEKLYAVGBALREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXTUREXA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXURE11CONCURRENTAUDITORSCERTIFICATE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXURE12STATUTORYAUDITORCERTIFICATE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXURE13COMPLIANCECERTIFICATE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXURE17CHANGEININTERESTOFDIRECTOR,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXUREFOURA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXUREVIIA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXUREXIV,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.EXCELACCOUNTSTATEMENT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AM_ANNEXURE_12,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AM_ANNEXURE_9,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AN11AMCONCUAUDITORCERTIFICATE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AN15AMANNUALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.AN16AMINTERNALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.WEEKLYREPONONEQ,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MONTHLYPFMFORM1TO14,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.SESVOTEMATRIX,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.W05CRANSDLDATA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.WEEKLY_CRA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.VALUATION_REP_CG,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.VALUATION_REP_SG,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.QUARTERLYCOMPLIANCESFORMS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_SBIREQUESTFORDATAREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.IIASVOTING_REPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.BENCHMARK,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.REPORTDEBIT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.REPORTEQUITY,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.BENCHMARKMONTHLY,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FINALIAREPORTSBIPF_INTERNALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.NPSTRUSTFEEPFM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MONTHLYNONUNANIMOUSVOTING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.INVESTMENTMANAGEMENTFEE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.GROWTHDATA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FORM1REPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.EVOTINGSUMMARYPFM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MAJORITYVOTESPFM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.UNAUDITEDFINANCIAL,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNUALPROXYPFM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.NPADEVELOPMENT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FORM3REPORTBYDIRECTORANDKP,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNUALEVOTING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.COMPLIANCECERTIFICATEATHALFYEARLYINTERVAL,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.TDSREPORTFROMPFM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.HALFYEARLYIAR,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.QR_REPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.VOTE_MATRIX,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.VOTE_REPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.QUARTERLYIAREVOTINGCERTIFICATE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.GRIEVANCETOPFIVEDATA_MONTHLY,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FINALINTIMATION_NPS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.QUARTERLYSTEWARDSHIPREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.COMPLIANCE_CERTIFICATE_AT_QUARTERLY_INTERVAL,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MONTHLYCOMPCERTIFICATE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.EVOTINGCOUNT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CUT_ANNUAL_AUDIT_REPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNUALCOMPLAINCEREPORT_FORM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FINALINTIMATIONAPY,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.LETTERSOFAPY,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.LETTERS_NPS_GRIEVANCES,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MONTHLYMISREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.STATUSDAYS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.STATUSOFGRIEVANCES,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.GRIEVPENDING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CATEGARYWISEAGAING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXUREI_AUC_DETAILS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXUREFORCUSTODIAN,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.COMPLIANCE_REPORT_CUSTODIAN,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.COMPLIANCECERTIFICATEATHALFYEARLYINTERVAL,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.HDFCINTERNALAUDITREPORTPFM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXURE_IV_PFM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNEXURE_ONE_PFM_AUC_DETAILS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MONTHLY_CUSTODIAN_TDS_REPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.WEBSITEJUNE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FINAL_COUNT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFVOTINGRECOMMENDATION,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CRA_COMPLIANCE_REPORT_NPST_TRUST,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CUSTODIANINTERNALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CRA_QUATERLY_DATA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CRA_NPS_TRUST,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FORMSIX,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.QUARTERLYNONUNANIMOUS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FORM3REPORT_BY_DIRECTOR_KP,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.SLAFORMTRUSTEEBANK,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ASSETNOTUNDERCUSTODYREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.QUARTERLYCOMPLIANCECERTIFICATE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CUSTODIANPDFINTERNALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNUALAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.DETAILEDAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PROXYVOTINGREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.STEWARDSHIPREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFMINTERNALAUDITREPORTS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.EXCEPTIONREPORTING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.YEARLYEXCEPTIONREPORTING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNUALPROXYVOTINGREPORTS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.FINALINTIMATION,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.TOP5ENTRIESMAXGRIEVANCE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNUALDETAILEDAUDITREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_MONTHLYECEPTIONREPORTING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_MONTHLYREPORTTOAUTHORITY,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_QUATERLYEXCEPTIONREPORTING,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_QUATERLYREPORT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_QUATERLYSUBMISSION,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.GRIEV_HANDLEDNPSTRUST,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.GRIEVANCE_TRANSFERRINGPFRDA,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.VALUATIONREPORTS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.DETAILEDAUDITREPORTFORM,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.IMF_NPS_TRUST_FEE,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ASP_FILE_UPLOAD,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.QUARTERLYSUBMISSIONOFFORMSASPERPFRDAREGUALTIONS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.CONSOLIDATED_FINANCIAL_AND_KEY_STATS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MONTHLYPFMFORM1TO8,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_INTERNAL_AUDIT_REPORT_QUARTERLY,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.ANNUAL_AUDITED_SCHEME_FINANCIALS,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.PFM_HY_COMP_CERT,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.VALUATIONREPCB,
		"javax.portlet.name=" + ReportUploadLogPortletKeys.MONTHLY_AUM_RETURN_DATA,
		"mvc.command.name=/"
		}, 
service = MVCRenderCommand.class)
public class ReportUploadLogMVCRenderCommand implements MVCRenderCommand{

	private final Log _log = LogFactoryUtil.getLog(ReportUploadLogMVCRenderCommand.class);
	
	@Reference
	private Portal portal;
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private RoleLocalService roleLocalService;
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		_log.info("Calling MVC Render for Annexure 16 report ...");
		HttpServletRequest request = portal.getOriginalServletRequest(portal.getHttpServletRequest(renderRequest));
		String reportName = ParamUtil.getString(request, "report-name");
		_log.info("Report name : "+reportName);
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long reportMasterId = 0;
		long userId =themeDisplay.getUserId();
		long companyId =  themeDisplay.getCompanyId();
		if(isAM(userId, companyId)) {
			List<String> allUserRoles = getAllRoles(userId);
			for (String role : allUserRoles) {
				setReportUploadLogDetails(userId, companyId, renderRequest, reportMasterId, reportName, role);
			}
		}else {
			String uploaderRole = getUserRoleName(userId,companyId);
			setReportUploadLogDetails(userId, companyId, renderRequest, reportMasterId, reportName, uploaderRole);
		}
		
		return "/view.jsp";
	}

	private String getUserRoleName(long userId, long companyId) {
		try {
			if(roleLocalService.hasUserRole(userId, companyId, nps.common.service.constants.NPSTRoleConstants.NPST_AM, Boolean.FALSE)) {
				return NPSTRoleConstants.NPST_AM;
			}else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.MAKER, Boolean.FALSE)) {
				return NPSTRoleConstants.MAKER;
			}else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.SUPERVISOR, Boolean.FALSE)) {
				return NPSTRoleConstants.SUPERVISOR;
			}else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_MAKER, Boolean.FALSE)) {
				return NPSTRoleConstants.PFM_MAKER;
			}else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_AM, Boolean.FALSE)) {
				return NPSTRoleConstants.PFM_AM;
			}
			else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_SUPERVISOR, Boolean.FALSE)) {
				return NPSTRoleConstants.CRA_SUPERVISOR;
			}
			else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_MAKER, Boolean.FALSE)) {
				return NPSTRoleConstants.CRA_MAKER;
			}
			else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_MAKER, Boolean.FALSE)) {
				return NPSTRoleConstants.CUSTODIAN_MAKER;
			}
			else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_AM, Boolean.FALSE)) {
				return NPSTRoleConstants.CUSTODIAN_AM;
			}
			/*
			 * else if(roleLocalService.hasUserRole(userId, companyId,
			 * NPSTRoleConstants.GRIEVANCES_MGR, Boolean.FALSE)) { return
			 * NPSTRoleConstants.GRIEVANCES_MGR; }
			 */
			else if(roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_AM_MGR, Boolean.FALSE)) {
				return NPSTRoleConstants.GRIEVANCES_AM_MGR;
			}
		} catch (PortalException e) {
			_log.error(e.getMessage());
		}
		
		return StringPool.BLANK;
	}
	
	public List<String> getAllRoles(long userId) {
		List<String> roleString = new ArrayList<String>();
		List<Role> roles = roleLocalService.getUserRoles(userId);
		for (Role role : roles) {
			roleString.add(role.getName());
		}
		return roleString;
	}
	
	public void setReportUploadLogDetails(long userId, long companyId, PortletRequest renderRequest, long reportMasterId, String reportName, String uploaderRole) {
		try {
			
			ReportMaster reportMaster = reportMasterLocalService.getReportMasterByReportName(reportName, uploaderRole);
			reportMasterId = reportMaster.getReportMasterId();
			_log.info("uploaderRole"+uploaderRole);
			_log.info("Report Master Id : "+reportMasterId);
			long intermediarytype = reportMaster.getIntermediarytype();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<ReportUploadLog> reportUploadLogs = new ArrayList<ReportUploadLog>();
			if(intermediarytype > 0) {
				reportUploadLogs = reportUploadLogLocalService.fetchByReportMasterIdAndUploadedAndIntermediaryid(reportMasterId,0, commonRoleService.getUserIntermediaryId(intermediarytype, userId, companyId));
			}else {
				reportUploadLogs = reportUploadLogLocalService.findByReportMasterIdAndUploaded(reportMasterId, Boolean.FALSE);
			}
			if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
				ReportUploadLog reportUploadLog = reportUploadLogs.get(0);
				_log.info("reportUploadLog"+reportUploadLog);
				renderRequest.setAttribute("reportUploadLogId", reportUploadLog.getReportUploadLogId());
				renderRequest.setAttribute("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
				renderRequest.setAttribute("reportUploadLogExist", Boolean.TRUE);
				renderRequest.setAttribute("reportMasterId", reportMasterId);
			}
		} catch (NoSuchReportMasterException e) {
			_log.error("Exception on fectching report master : "+e.getMessage(), e);
		} catch (PortalException e) {
			_log.error("Error fetching intermediarytype"+e.getMessage(),e);
		}
	}
	
	public Boolean isAM(long userId, long companyId) {
		Boolean isAM = false;
		try {
			isAM = roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.NPST_AM, Boolean.FALSE)  || 
					roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CUSTODIAN_AM, Boolean.FALSE) ||
					roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.PFM_AM, Boolean.FALSE) ||
					roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.GRIEVANCES_AM, Boolean.FALSE)||
					roleLocalService.hasUserRole(userId, companyId, NPSTRoleConstants.CRA_AM, Boolean.FALSE);
		} catch (PortalException e) {
			_log.error("Error fetching intermediarytype"+e.getMessage(),e);
		}
		return isAM;
	}

	@Reference
	private CommonRoleService commonRoleService;
}
