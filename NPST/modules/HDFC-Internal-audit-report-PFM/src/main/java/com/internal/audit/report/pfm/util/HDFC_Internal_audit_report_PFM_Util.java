package com.internal.audit.report.pfm.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import nps.common.service.constants.NPSTRoleConstants;

public class HDFC_Internal_audit_report_PFM_Util {
	private static Log LOG = LogFactoryUtil.getLog(HDFC_Internal_audit_report_PFM_Util.class);
	public static JSONObject getloopingContexOfInternalAuditReport() {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("Board Meeting", 5);
		jsonObject.put("Investment Operational Manual/Procedure", 8);
		jsonObject.put("Investment Committee", 6);
		jsonObject.put("Investment Policy", 9);
		jsonObject.put("Risk Management Committee", 5);
		jsonObject.put("Risk Management Policy", 14);
		jsonObject.put("Pattern of Investment", 5);
		jsonObject.put("Front office dealing procedure", 6);
		jsonObject.put("Back Office Procedure", 4);
		jsonObject.put("Exposure & Prudential Norms", 6);
		jsonObject.put("Custodian Controls", 2);
		jsonObject.put("Verification of Other investments", 2);
		jsonObject.put("Units Accounting", 3);
		jsonObject.put("Investment Bank accounts", 3);
		jsonObject.put("Appointment of Brokers", 3);
		jsonObject.put("Non-Performing Investments", 3);
		jsonObject.put("Inter Scheme Transfer", 2);
		jsonObject.put("Investment Deals verification", 11);
		jsonObject.put("Accounting", 6);
		jsonObject.put("NAV", 9);
		jsonObject.put("Disclosure", 5);
		jsonObject.put("Periodical returns to Authority/ Trust", 4);
		jsonObject.put("Compliances", 4);
		jsonObject.put("Internal Audit/ PFM Audit/ Scheme Audit", 1);
		jsonObject.put("Marketing & distribution", 1);
		jsonObject.put("Other regulatory compliances", 7);
		
		return jsonObject;
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
