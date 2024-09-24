package com.monthly.compcertificate.util;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import nps.common.service.constants.NPSTRoleConstants;

public class PFMMccNonNps {
	
	private static Log LOG = LogFactoryUtil.getLog(PFMMccNonNps.class); 
	
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
			LOG.info("user companyID::" + user.getCompanyId());
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

}
