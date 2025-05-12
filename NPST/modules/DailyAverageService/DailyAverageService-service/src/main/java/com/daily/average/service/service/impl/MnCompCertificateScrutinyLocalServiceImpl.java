/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.daily.average.service.service.impl;

import com.daily.average.service.model.MnCompCertificateScrutiny;
import com.daily.average.service.service.base.MnCompCertificateScrutinyLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MnCompCertificateScrutinyLocalServiceImpl
	extends MnCompCertificateScrutinyLocalServiceBaseImpl {
	
	public MnCompCertificateScrutiny saveMnCompCertificateScrutiny(String username,double mnversion,long userid,String purchase_of_securities_rem,
			String detailed_investment_rem,String investments_approved_rem,String decision_of_investment_rem,String invest_non_dematerialized_rem,
			String all_investments_from_funds_rem,String delivery_of_security_purch_rem,String investment_done_in_ipo_rem,String scheme_investments_rem,
			String stop_loss_trigger_rem,String decision_appr_by_committee_rem,String decision_prop_documented_rem,String inter_scheme_transfer_rem,
			String investment_held_in_equity_rem,String invest_in_equity_shares_rem, String active_passive_breaches_rem, String disinvestments_approved_rem,String decision_of_disinvestment_rem,
			String delivery_of_security_sale_rem,String all_investment_charges_rem,String pfm_adhered_rem,String records_of_audit_of_nav_rem,
			String scheme_wise_nav_uploaded_rem,String scheme_wise_nav_submitted_rem,String monthly_reports_submitted_rem,String scrip_wise_details_rem,
			String scheme_wise_rem,String scheme_wise_daily_rem,String periodic_reports_monthly_rem,String scrip_wise_details_pf_rem,String pension_fund_published_rem,String pension_fund_disclosed_rem,
			String annexure_a_i_rem,String annexure_a_ii_rem,String annexure_b_rem,String annexure_c_rem,String annexure_d_rem,String annexure_e_rem,String annexure_f_rem,
			String annexure_g_rem,String annexure_h_rem,Date createdon,long createdby, long reportUploadLogId,String npsRemark) {
		 
		MnCompCertificateScrutiny mnCompCertificateScrutiny = mnCompCertificateScrutinyPersistence.create(counterLocalService.increment());
		long scrutinyid = counterLocalService.increment(MnCompCertificateScrutiny.class.getName());
		
		mnCompCertificateScrutiny.setReportUploadLogId(reportUploadLogId);
		mnCompCertificateScrutiny.setScrutinyid(scrutinyid);
		mnCompCertificateScrutiny.setUsername(username);
		mnCompCertificateScrutiny.setVersion(mnversion);
		mnCompCertificateScrutiny.setUserid(userid);
		
		//purches ramrks::::::::::::::::::
		mnCompCertificateScrutiny.setPurchase_of_securities_rem(purchase_of_securities_rem);
		mnCompCertificateScrutiny.setDetailed_investment_rem(detailed_investment_rem);
		mnCompCertificateScrutiny.setInvestments_approved_rem(investments_approved_rem);
		mnCompCertificateScrutiny.setDecision_of_investment_rem(decision_of_investment_rem);
		mnCompCertificateScrutiny.setInvest_non_dematerialized_rem(invest_non_dematerialized_rem);
		mnCompCertificateScrutiny.setAll_investments_from_funds_rem(all_investments_from_funds_rem);
		mnCompCertificateScrutiny.setDelivery_of_security_purch_rem(delivery_of_security_purch_rem);
		mnCompCertificateScrutiny.setInvestment_done_in_ipo_rem(investment_done_in_ipo_rem);
		mnCompCertificateScrutiny.setScheme_investments_rem(scheme_investments_rem);
		mnCompCertificateScrutiny.setStop_loss_trigger_rem(stop_loss_trigger_rem);
		mnCompCertificateScrutiny.setDecision_appr_by_committee_rem(decision_appr_by_committee_rem);
		mnCompCertificateScrutiny.setDecision_prop_documented_rem(decision_prop_documented_rem);
		mnCompCertificateScrutiny.setInter_scheme_transfer_rem(inter_scheme_transfer_rem);
		mnCompCertificateScrutiny.setInvestment_held_in_equity_rem(investment_held_in_equity_rem);
		mnCompCertificateScrutiny.setInvest_in_equity_shares_rem(invest_in_equity_shares_rem);
		mnCompCertificateScrutiny.setActive_passive_breaches_rem(active_passive_breaches_rem);
		mnCompCertificateScrutiny.setDisinvestments_approved_rem(disinvestments_approved_rem);
		mnCompCertificateScrutiny.setDecision_of_disinvestment_rem(decision_of_disinvestment_rem);
		mnCompCertificateScrutiny.setDelivery_of_security_sale_rem(delivery_of_security_sale_rem);
		mnCompCertificateScrutiny.setAll_investment_charges_rem(all_investment_charges_rem);
		mnCompCertificateScrutiny.setPfm_adhered_rem(pfm_adhered_rem);
		mnCompCertificateScrutiny.setRecords_of_audit_of_nav_rem(records_of_audit_of_nav_rem);
		mnCompCertificateScrutiny.setScheme_wise_nav_submitted_rem(scheme_wise_nav_submitted_rem);
		mnCompCertificateScrutiny.setScheme_wise_nav_uploaded_rem(scheme_wise_nav_uploaded_rem);
		mnCompCertificateScrutiny.setMonthly_reports_submitted_rem(monthly_reports_submitted_rem);
		mnCompCertificateScrutiny.setScrip_wise_details_rem(scrip_wise_details_rem);
		
		mnCompCertificateScrutiny.setScheme_wise_rem(scheme_wise_rem);
		mnCompCertificateScrutiny.setScheme_wise_daily_rem(scheme_wise_daily_rem);
		mnCompCertificateScrutiny.setPeriodic_reports_monthly_rem(periodic_reports_monthly_rem);
		mnCompCertificateScrutiny.setScrip_wise_details_pf_rem(scrip_wise_details_pf_rem);
		mnCompCertificateScrutiny.setPension_fund_published_rem(pension_fund_published_rem);
		mnCompCertificateScrutiny.setPension_fund_disclosed_rem(pension_fund_disclosed_rem);
		
		//document remarks::::::::::::::::: 
		mnCompCertificateScrutiny.setAnnexure_a_i_rem(annexure_a_i_rem);
		mnCompCertificateScrutiny.setAnnexure_a_ii_rem(annexure_a_ii_rem);
		mnCompCertificateScrutiny.setAnnexure_b_rem(annexure_b_rem);
		mnCompCertificateScrutiny.setAnnexure_c_rem(annexure_c_rem);
		mnCompCertificateScrutiny.setAnnexure_d_rem(annexure_d_rem);
		mnCompCertificateScrutiny.setAnnexure_e_rem(annexure_e_rem);
		mnCompCertificateScrutiny.setAnnexure_f_rem(annexure_f_rem);
		mnCompCertificateScrutiny.setAnnexure_g_rem(annexure_g_rem);
		mnCompCertificateScrutiny.setAnnexure_h_rem(annexure_h_rem);
		mnCompCertificateScrutiny.setCreatedon(createdon);
		mnCompCertificateScrutiny.setCreatedby(createdby);
		mnCompCertificateScrutiny.setNpsRemark(npsRemark);
		_log.info("mnCompCertificateScrutiny:::::::::::::::::::::::::::::"+mnCompCertificateScrutiny.toString());
		
		return mnCompCertificateScrutinyPersistence.update(mnCompCertificateScrutiny);
	}
	
	/**
	 * 
	 * @param reportUploadLogId
	 * @return
	 */
	public List<MnCompCertificateScrutiny> findByReportUploadLogId(long reportUploadLogId) {
		return mnCompCertificateScrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	}
	Log _log = LogFactoryUtil.getLog(getClass());
}