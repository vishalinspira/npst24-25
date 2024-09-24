package nps.common.service.util;

import com.daily.average.service.model.IntermediaryList;
import com.daily.average.service.service.IntermediaryListLocalService;
import com.daily.average.service.service.ReportMasterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSTRoleConstants;

@Component(
		immediate = true,
		service = CommonRoleService.class
)
public class CommonRoleService {
	private static final Log log = LogFactoryUtil.getLog(CommonRoleService.class);
	
	public long getUserIntermediaryId(long intermediarytype, long userId, long companyId) throws PortalException{
		List<IntermediaryList> intermediaryLists = intermediaryListLocalService.fetchIntermediaryListByTypeId(intermediarytype);
		for(IntermediaryList intermediaryList : intermediaryLists) {
			if(roleLocalService.hasUserRole(userId, companyId,intermediaryList.getIntermediaryname(), Boolean.FALSE)) {
				return intermediaryList.getId();
			}
		}
		return 0;
	}
	
	public static String getCompanyName(long userId) {
		User user=null;
		try {
		user=UserLocalServiceUtil.getUser(userId);
		return getCompanyName(user);
		}catch (Exception e) {
			log.error(e);
		}
		return null ;
	}
	
	public static String getCompanyName(String pfmName) {
		String companyName="";
		if(pfmName.equalsIgnoreCase("SBI")) {
			companyName = "SBI Pension Funds Private Limited";
		}else if(pfmName.equalsIgnoreCase("UTI")) {
			companyName = "UTI Retirement Solutions Limited";
		}else if(pfmName.equalsIgnoreCase("LIC")) {
			companyName = "LIC Pension Fund Limited";
		}else if(pfmName.equalsIgnoreCase("Kotak")) {
			companyName = "Kotak Mahindra Pension Fund Limited";
		}else if(pfmName.equalsIgnoreCase("ABS")) {
			companyName = "Aditya Birla Sun Life Pension Management Limited";
		}else if(pfmName.equalsIgnoreCase("HDFC")) {
			companyName = "HDFC Pension Management Company Limited";
		}else if(pfmName.equalsIgnoreCase("ICICI")) {
			companyName = "ICICI Prudential Pension Funds Management Company Limited";
		}else if(pfmName.equalsIgnoreCase("TATA")) {
			companyName = "Tata Pension Management Ltd";
		}else if(pfmName.equalsIgnoreCase("MLP")) {
			companyName = "Max Life Pension Fund Management Ltd";
		}else if(pfmName.equalsIgnoreCase("APF")) {
			companyName = "Axis Pension Fund Management Ltd";
		}else if(pfmName.equalsIgnoreCase("DSP")) {
			companyName = "DSP Pension Fund Management Ltd";
		}
		return companyName;
	}
	
	public static String getCompanyName(User user) {
		String companies = "";
		try {
		if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "SBI", false)){
			companies = "SBI Pension Funds Private Limited";
		}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "UTI", false)){
			companies = "UTI Retirement Solutions Limited";
		}
		else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "LIC", false)){
			companies = "LIC Pension Fund Limited";
		}
		else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "Kotak", false)){
			companies = "Kotak Mahindra Pension Fund Limited";
		}
		else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "ABS", false)){
			companies = "Aditya Birla Sun Life Pension Management Limited";
		}
		else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "HDFC", false)){
			companies = "HDFC Pension Management Company Limited";
		}
		else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "ICICI", false)){
			companies = "ICICI Prudential Pension Funds Management Company Limited";
		}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "TATA", false)){
			companies = "Tata Pension Management Ltd";
		}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "MLP", false)){
			companies = "Max Life Pension Fund Management Ltd";
		}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "APF", false)){
			companies = "Axis Pension Fund Management Ltd";
		}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "DSP", false)){
			companies = "DSP Pension Fund Management Ltd";
		}
		}catch (Exception e) {
			log.error(e);
		}
		return companies;
	}
	
	public long getUserIntermediaryId(String roleName, long userId, long companyId) throws PortalException{
		List<Long> intermediaryTypes = reportMasterLocalService.getIntermediartTypeByUploaderRole(roleName);
		log.debug("intermediaryTypes : "+intermediaryTypes);
		for(Long type: intermediaryTypes) {
			return getUserIntermediaryId(type, userId, companyId);
		}
		return 0;
	}
	
	public static boolean isNonNpsUser(long userId) {
		boolean isNonNpsUser = false;
		User user = null;
		boolean inherited = Boolean.FALSE;
		long companyId = 0l;
		try {
			user = UserLocalServiceUtil.getUser(userId);
		} catch (Exception e) {
			LOG.error("Exception in getting user::" + e.getMessage());
		}
		if(Validator.isNotNull(user)) {
			companyId = user.getCompanyId();
			LOG.debug("user companyID::" + user.getCompanyId());
		}
		try {
			if(Validator.isNotNull(companyId)) {
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean craMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				boolean craChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_CHECKER, userId, inherited);
				boolean craSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CRA_SUPERVISOR, userId, inherited);
				isNonNpsUser = (craMaker || craChecker || craSupervisor);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean custodianMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_MAKER, userId, inherited);
				boolean custodianChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_CHECKER, userId, inherited);
				boolean custodianSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_SUPERVISOR, userId, inherited);
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.PFM_SUPERVISOR, userId, inherited);
				isNonNpsUser = (custodianMaker || custodianChecker || custodianSupervisor || pfmMaker || pfmChecker || pfmSupervisor);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.SUPERVISOR, userId, inherited);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
			}
			if(Validator.isNotNull(companyId) && !isNonNpsUser) {
				boolean pfmMaker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_MAKER, userId, inherited);
				boolean pfmChecker = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_CHECKER, userId, inherited);
				boolean pfmSupervisor = UserLocalServiceUtil.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_SUPERVISOR, userId, inherited);
				isNonNpsUser = (pfmChecker || pfmSupervisor || pfmMaker);
			}
		} catch (Exception e) {
			LOG.error("Exception in getting role::" + e.getMessage());
		}
		return isNonNpsUser;
	}

	private static Log LOG=LogFactoryUtil.getLog(CommonRoleService.class.getName());
	@Reference
	private IntermediaryListLocalService intermediaryListLocalService;
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	private RoleLocalService roleLocalService;
}
