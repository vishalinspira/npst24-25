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

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.MnCompCertificateImpl;
import com.daily.average.service.service.base.MnCompCertificateLocalServiceBaseImpl;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalServiceUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class MnCompCertificateLocalServiceImpl
	extends MnCompCertificateLocalServiceBaseImpl {
	
	
	public MnCompCertificate addMnCompCertificate(Date date_1, String purchase_of_securities, 
			String detailed_investment, String investments_approved, String decision_of_investment, String investments_non_dematerialized, 
			String all_investments_from_funds, String delivery_of_securities_purch, 
			String investment_done_in_ipo, long reportUploadLogId,String Purchase_of_sec_rem_1_1_2,String Purchase_of_sec_rem_1_2a_2,String Purchase_of_sec_rem_1_2b_2,String Purchase_of_sec_rem_1_2c_2,
			String Purchase_of_sec_rem_1_2d_2,String Purchase_of_sec_rem_1_2e_2,String Purchase_of_sec_rem_1_3_2,String Purchase_of_secu_rem_1_4_2,String Securities_held_2_1_2,
			String Securities_held_2_2a_2,String Securities_held_2_2b_2,String Securities_held_2_2c_2,String Securities_held_2_3_2,String Securities_held_2_4_2,String Securities_held_2_5_2,String Securities_held_2_6_2,
			String Sale_of_securities_3_1a_2,String Sale_of_securities_3_1b_2,String Sale_of_securities_3_2_2,String Reports_and_Disclosure_4_1a_2,String Reports_and_Disclosure_4_1b_2,
			String Reports_and_Disclosure_4_1c_2,String Reports_and_Disclosure_4_1d_2,String Reports_and_Disclosure_4_1e_2,String Reports_and_Disclosure_4_2a_2,
			String Reports_and_Disclosure_4_2b_2,String Disclosure_requirements_5_1a_2,String Disclosure_requirements_5_1b_2,String Disclosure_requirements_5_1c_2,String Disclosure_requirements_5_1d_2,
			String Disclosure_requirements_5_1e_2,String Disclosure_requirements_5_1f_2, String scheme_investments, 
			String stop_loss_trigger, String decision_approved_by_committee, String decision_properly_documented, String inter_scheme_transfer, 
			String investment_held_in_equity, String investment_in_equity_shares, String active_passive_breaches, String disinvestments_approved, 
			String decision_of_disinvestment, String delivery_of_securities_sale,String all_investment_charges, 
			String pfm_adhered, String records_of_the_audit_of_nav, String scheme_wise_nav_uploaded, String scheme_wise_nav_submitted, 
			String monthly_reports_submitted, String scrip_wise_details, String scheme_wise, String scheme_wise_daily, String periodic_reports_monthly, String scrip_wise_details_portfolio,
			String pension_fund_published, String pension_fund_disclosed, String date_2, String company_name, String emplolyee_name, 
			String roles, Date date_3, String place,long annexure_a_i, long annexure_a_ii, long annexure_b, 
			long annexure_c, long annexure_d, long annexure_e, long annexure_f,long annexure_g, long annexure_h, 
			Date createDate, long createdBy,long fileEntryId,int isAmRejected) {
		
MnCompCertificate mnCompCertificate = mnCompCertificateLocalService.fetchMnCompCertificate(reportUploadLogId);
		
		if(mnCompCertificate == null) {
			mnCompCertificate = mnCompCertificatePersistence.create(reportUploadLogId);
		}
		
		mnCompCertificate.setFileEntryId(fileEntryId);
		mnCompCertificate.setDate_1(date_1);
		mnCompCertificate.setPurchase_of_securities(purchase_of_securities);
		mnCompCertificate.setDetailed_investment(detailed_investment);
		mnCompCertificate.setInvestments_approved(investments_approved);
		mnCompCertificate.setDecision_of_investment(decision_of_investment);
		mnCompCertificate.setInvestments_non_dematerialized(investments_non_dematerialized);
		mnCompCertificate.setAll_investments_from_funds(all_investments_from_funds);
		mnCompCertificate.setDelivery_of_securities_purch(delivery_of_securities_purch);
		mnCompCertificate.setInvestment_done_in_ipo(investment_done_in_ipo);
		
		mnCompCertificate.setPurchase_of_sec_rem_1_1_2(Purchase_of_sec_rem_1_1_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2a_2(Purchase_of_sec_rem_1_2a_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2b_2(Purchase_of_sec_rem_1_2b_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2c_2(Purchase_of_sec_rem_1_2c_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2d_2(Purchase_of_sec_rem_1_2d_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2e_2(Purchase_of_sec_rem_1_2e_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_3_2(Purchase_of_sec_rem_1_3_2);
		mnCompCertificate.setPurchase_of_secu_rem_1_4_2(Purchase_of_secu_rem_1_4_2);
		mnCompCertificate.setSecurities_held_2_1_2(Securities_held_2_1_2);
		mnCompCertificate.setSecurities_held_2_2a_2(Securities_held_2_2a_2);
		mnCompCertificate.setSecurities_held_2_2b_2(Securities_held_2_2b_2);
		mnCompCertificate.setSecurities_held_2_2c_2(Securities_held_2_2c_2);
		mnCompCertificate.setSecurities_held_2_3_2(Securities_held_2_3_2);
		mnCompCertificate.setSecurities_held_2_4_2(Securities_held_2_4_2);
		mnCompCertificate.setSecurities_held_2_5_2(Securities_held_2_5_2);
		mnCompCertificate.setSecurities_held_2_6_2(Securities_held_2_6_2);
		mnCompCertificate.setSale_of_securities_3_1a_2(Sale_of_securities_3_1a_2);
		mnCompCertificate.setSale_of_securities_3_1b_2(Sale_of_securities_3_1b_2);
		mnCompCertificate.setSale_of_securities_3_2_2(Sale_of_securities_3_2_2);
		mnCompCertificate.setReports_and_Disclosure_4_1a_2(Reports_and_Disclosure_4_1a_2);
		mnCompCertificate.setReports_and_Disclosure_4_1b_2(Reports_and_Disclosure_4_1b_2);
		mnCompCertificate.setReports_and_Disclosure_4_1c_2(Reports_and_Disclosure_4_1c_2);
		mnCompCertificate.setReports_and_Disclosure_4_1d_2(Reports_and_Disclosure_4_1d_2);
		mnCompCertificate.setReports_and_Disclosure_4_1e_2(Reports_and_Disclosure_4_1e_2);
		mnCompCertificate.setReports_and_Disclosure_4_2a_2(Reports_and_Disclosure_4_2a_2);
		mnCompCertificate.setReports_and_Disclosure_4_2b_2(Reports_and_Disclosure_4_2b_2);
		mnCompCertificate.setDisclosure_requirements_5_1a_2(Disclosure_requirements_5_1a_2);
		mnCompCertificate.setDisclosure_requirements_5_1b_2(Disclosure_requirements_5_1b_2);
		mnCompCertificate.setDisclosure_requirements_5_1c_2(Disclosure_requirements_5_1c_2);
		mnCompCertificate.setDisclosure_requirements_5_1d_2(Disclosure_requirements_5_1d_2);
		mnCompCertificate.setDisclosure_requirements_5_1e_2(Disclosure_requirements_5_1e_2);
		mnCompCertificate.setDisclosure_requirements_5_1f_2(Disclosure_requirements_5_1f_2);
    	mnCompCertificate.setScheme_investments(scheme_investments);
		mnCompCertificate.setStop_loss_trigger(stop_loss_trigger);
		mnCompCertificate.setDecision_approved_by_committee(decision_approved_by_committee);
		mnCompCertificate.setDecision_properly_documented(decision_properly_documented);
		mnCompCertificate.setInter_scheme_transfer(inter_scheme_transfer);
		mnCompCertificate.setInvestment_held_in_equity(investment_held_in_equity);
		mnCompCertificate.setInvestment_in_equity_shares(investment_in_equity_shares);
		
		mnCompCertificate.setDisinvestments_approved(disinvestments_approved);
		mnCompCertificate.setDecision_of_disinvestment(decision_of_disinvestment);
		mnCompCertificate.setDelivery_of_securities_sale(delivery_of_securities_sale);
		
		mnCompCertificate.setAll_investment_charges(all_investment_charges);
		mnCompCertificate.setPfm_adhered(pfm_adhered);
		mnCompCertificate.setRecords_of_the_audit_of_nav(records_of_the_audit_of_nav);
		mnCompCertificate.setScheme_wise_nav_uploaded(scheme_wise_nav_uploaded);
		mnCompCertificate.setScheme_wise_nav_submitted(scheme_wise_nav_submitted);
		mnCompCertificate.setMonthly_reports_submitted(monthly_reports_submitted);
		mnCompCertificate.setScrip_wise_details(scrip_wise_details);
		
		mnCompCertificate.setDate_2(date_2);
		mnCompCertificate.setCompany_name(company_name);
		mnCompCertificate.setEmplolyee_name(emplolyee_name);
		mnCompCertificate.setRoles(roles);
		mnCompCertificate.setDate_3(date_3);
		mnCompCertificate.setPlace(place);
		
		mnCompCertificate.setAnnexure_a_i(annexure_a_i);
		mnCompCertificate.setAnnexure_a_ii(annexure_a_ii);
		mnCompCertificate.setAnnexure_b(annexure_b);
		mnCompCertificate.setAnnexure_c(annexure_c);
		mnCompCertificate.setAnnexure_d(annexure_d);
		mnCompCertificate.setAnnexure_e(annexure_e);
		mnCompCertificate.setAnnexure_f(annexure_f);
		mnCompCertificate.setAnnexure_g(annexure_g);
		mnCompCertificate.setAnnexure_h(annexure_h);
		mnCompCertificate.setCreatedon(createDate);
		mnCompCertificate.setCreatedby(createdBy);
		
		return mnCompCertificatePersistence.update(mnCompCertificate);

	}
	
	public MnCompCertificate addSection1(Date date_1, String purchase_of_securities, 
			String detailed_investment, String investments_approved, 
			String decision_of_investment, String investments_non_dematerialized, 
			String all_investments_from_funds, String delivery_of_securities_purch, 
			String investment_done_in_ipo, long reportUploadLogId,String Purchase_of_sec_rem_1_1_2,String Purchase_of_sec_rem_1_2a_2,String Purchase_of_sec_rem_1_2b_2,String Purchase_of_sec_rem_1_2c_2,
			String Purchase_of_sec_rem_1_2d_2,String Purchase_of_sec_rem_1_2e_2,String Purchase_of_sec_rem_1_3_2,String Purchase_of_secu_rem_1_4_2,String Securities_held_2_1_2,
			String Securities_held_2_2a_2,String Securities_held_2_2b_2,String Securities_held_2_2c_2,String Securities_held_2_3_2,String Securities_held_2_4_2,String Securities_held_2_5_2, String Securities_held_2_6_2,
			String Sale_of_securities_3_1a_2,String Sale_of_securities_3_1b_2,String Sale_of_securities_3_2_2,String Reports_and_Disclosure_4_1a_2,String Reports_and_Disclosure_4_1b_2,
			String Reports_and_Disclosure_4_1c_2,String Reports_and_Disclosure_4_1d_2,String Reports_and_Disclosure_4_1e_2,String Reports_and_Disclosure_4_2a_2,
			String Reports_and_Disclosure_4_2b_2, String Disclosure_requirements_5_1a_2, String Disclosure_requirements_5_1b_2, String Disclosure_requirements_5_1c_2,
			String Disclosure_requirements_5_1d_2, String Disclosure_requirements_5_1e_2, String Disclosure_requirements_5_1f_2) {
		
		MnCompCertificate mnCompCertificate = mnCompCertificateLocalService.fetchMnCompCertificate(reportUploadLogId);
		
		if(mnCompCertificate == null) {
			mnCompCertificate = mnCompCertificatePersistence.create(reportUploadLogId);
		}
		
		mnCompCertificate.setDate_1(date_1);
		mnCompCertificate.setPurchase_of_securities(purchase_of_securities);
		mnCompCertificate.setDetailed_investment(detailed_investment);
		mnCompCertificate.setInvestments_approved(investments_approved);
		mnCompCertificate.setDecision_of_investment(decision_of_investment);
		mnCompCertificate.setInvestments_non_dematerialized(investments_non_dematerialized);
		mnCompCertificate.setAll_investments_from_funds(all_investments_from_funds);
		mnCompCertificate.setDelivery_of_securities_purch(delivery_of_securities_purch);
		mnCompCertificate.setInvestment_done_in_ipo(investment_done_in_ipo);
		
		mnCompCertificate.setPurchase_of_sec_rem_1_1_2(Purchase_of_sec_rem_1_1_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2a_2(Purchase_of_sec_rem_1_2a_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2b_2(Purchase_of_sec_rem_1_2b_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2c_2(Purchase_of_sec_rem_1_2c_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2d_2(Purchase_of_sec_rem_1_2d_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_2e_2(Purchase_of_sec_rem_1_2e_2);
		mnCompCertificate.setPurchase_of_sec_rem_1_3_2(Purchase_of_sec_rem_1_3_2);
		mnCompCertificate.setPurchase_of_secu_rem_1_4_2(Purchase_of_secu_rem_1_4_2);
		mnCompCertificate.setSecurities_held_2_1_2(Securities_held_2_1_2);
		mnCompCertificate.setSecurities_held_2_2a_2(Securities_held_2_2a_2);
		mnCompCertificate.setSecurities_held_2_2b_2(Securities_held_2_2b_2);
		mnCompCertificate.setSecurities_held_2_2c_2(Securities_held_2_2c_2);
		mnCompCertificate.setSecurities_held_2_3_2(Securities_held_2_3_2);
		mnCompCertificate.setSecurities_held_2_4_2(Securities_held_2_4_2);
		mnCompCertificate.setSecurities_held_2_5_2(Securities_held_2_5_2);
		mnCompCertificate.setSecurities_held_2_6_2(Securities_held_2_6_2);
		mnCompCertificate.setSale_of_securities_3_1a_2(Sale_of_securities_3_1a_2);
		mnCompCertificate.setSale_of_securities_3_1b_2(Sale_of_securities_3_1b_2);
		mnCompCertificate.setSale_of_securities_3_2_2(Sale_of_securities_3_2_2);
		mnCompCertificate.setReports_and_Disclosure_4_1a_2(Reports_and_Disclosure_4_1a_2);
		mnCompCertificate.setReports_and_Disclosure_4_1b_2(Reports_and_Disclosure_4_1b_2);
		mnCompCertificate.setReports_and_Disclosure_4_1c_2(Reports_and_Disclosure_4_1c_2);
		mnCompCertificate.setReports_and_Disclosure_4_1d_2(Reports_and_Disclosure_4_1d_2);
		mnCompCertificate.setReports_and_Disclosure_4_1e_2(Reports_and_Disclosure_4_1e_2);
		mnCompCertificate.setReports_and_Disclosure_4_2a_2(Reports_and_Disclosure_4_2a_2);
		mnCompCertificate.setReports_and_Disclosure_4_2b_2(Reports_and_Disclosure_4_2b_2);
		mnCompCertificate.setDisclosure_requirements_5_1a_2(Disclosure_requirements_5_1a_2);
		mnCompCertificate.setDisclosure_requirements_5_1b_2(Disclosure_requirements_5_1b_2);
		mnCompCertificate.setDisclosure_requirements_5_1c_2(Disclosure_requirements_5_1c_2);
		mnCompCertificate.setDisclosure_requirements_5_1d_2(Disclosure_requirements_5_1d_2);
		mnCompCertificate.setDisclosure_requirements_5_1e_2(Disclosure_requirements_5_1e_2);
		mnCompCertificate.setDisclosure_requirements_5_1f_2(Disclosure_requirements_5_1f_2);
		
		
		
		
		return mnCompCertificatePersistence.update(mnCompCertificate);
		
	}
	
	public MnCompCertificate addSection2(MnCompCertificate sectionTwoInfo, String scheme_investments, 
			String stop_loss_trigger, String decision_approved_by_committee, 
			String decision_properly_documented, String inter_scheme_transfer, 
			String investment_held_in_equity, String investment_in_equity_shares, String active_passive_breaches) {
		
		sectionTwoInfo.setScheme_investments(scheme_investments);
		sectionTwoInfo.setStop_loss_trigger(stop_loss_trigger);
		sectionTwoInfo.setDecision_approved_by_committee(decision_approved_by_committee);
		sectionTwoInfo.setDecision_properly_documented(decision_properly_documented);
		sectionTwoInfo.setInter_scheme_transfer(inter_scheme_transfer);
		sectionTwoInfo.setInvestment_held_in_equity(investment_held_in_equity);
		sectionTwoInfo.setInvestment_in_equity_shares(investment_in_equity_shares);
		sectionTwoInfo.setActive_passive_breaches(active_passive_breaches);
				
		return mnCompCertificatePersistence.update(sectionTwoInfo);
		
	}
	
	public MnCompCertificate addSection3(MnCompCertificate sectionThreeInfo, String disinvestments_approved, 
			String decision_of_disinvestment, String delivery_of_securities_sale) {
		
		sectionThreeInfo.setDisinvestments_approved(disinvestments_approved);
		sectionThreeInfo.setDecision_of_disinvestment(decision_of_disinvestment);
		sectionThreeInfo.setDelivery_of_securities_sale(delivery_of_securities_sale);
				
		return mnCompCertificatePersistence.update(sectionThreeInfo);
		
	}
	
	public MnCompCertificate addSection4(MnCompCertificate sectionFourInfo, String all_investment_charges, 
			String pfm_adhered, String records_of_the_audit_of_nav, 
			String scheme_wise_nav_uploaded, String scheme_wise_nav_submitted, 
			String monthly_reports_submitted, String scrip_wise_details,
			String scheme_wise,String scheme_wise_daily,String periodic_reports_monthly,
			String scrip_wise_details_portfolio, String pension_fund_published, String pension_fund_disclosed) {
		
		sectionFourInfo.setAll_investment_charges(all_investment_charges);
		sectionFourInfo.setPfm_adhered(pfm_adhered);
		sectionFourInfo.setRecords_of_the_audit_of_nav(records_of_the_audit_of_nav);
		sectionFourInfo.setScheme_wise_nav_uploaded(scheme_wise_nav_uploaded);
		sectionFourInfo.setScheme_wise_nav_submitted(scheme_wise_nav_submitted);
		sectionFourInfo.setMonthly_reports_submitted(monthly_reports_submitted);
		sectionFourInfo.setScrip_wise_details(scrip_wise_details);
		sectionFourInfo.setScheme_wise(scheme_wise);
		sectionFourInfo.setScheme_wise_daily(scheme_wise_daily);
		sectionFourInfo.setPeriodic_reports_monthly(periodic_reports_monthly);
		sectionFourInfo.setScrip_wise_details_portfolio(scrip_wise_details_portfolio);
		sectionFourInfo.setPension_fund_published(pension_fund_published);
		sectionFourInfo.setPension_fund_disclosed(pension_fund_disclosed);
				
		return mnCompCertificatePersistence.update(sectionFourInfo);
		
	}
	
	public MnCompCertificate addSection5(MnCompCertificate sectionFiveInfo, String date_2, 
			String company_name, String emplolyee_name, 
			String roles, Date date_3, 
			String place) {
		
		sectionFiveInfo.setDate_2(date_2);
		sectionFiveInfo.setCompany_name(company_name);
		sectionFiveInfo.setEmplolyee_name(emplolyee_name);
		sectionFiveInfo.setRoles(roles);
		sectionFiveInfo.setDate_3(date_3);
		sectionFiveInfo.setPlace(place);
				
		return mnCompCertificatePersistence.update(sectionFiveInfo);
		
	}
	
	public MnCompCertificate addSection6(MnCompCertificate sectionSixInfo, long annexure_a_i, 
			long annexure_a_ii, long annexure_b, 
			long annexure_c, long annexure_d, 
			long annexure_e, long annexure_f,
			long annexure_g, long annexure_h, 
			Date createDate, long createdBy) {
		
		sectionSixInfo.setAnnexure_a_i(annexure_a_i);
		sectionSixInfo.setAnnexure_a_ii(annexure_a_ii);
		sectionSixInfo.setAnnexure_b(annexure_b);
		sectionSixInfo.setAnnexure_c(annexure_c);
		sectionSixInfo.setAnnexure_d(annexure_d);
		sectionSixInfo.setAnnexure_e(annexure_e);
		sectionSixInfo.setAnnexure_f(annexure_f);
		sectionSixInfo.setAnnexure_g(annexure_g);
		sectionSixInfo.setAnnexure_h(annexure_h);
		sectionSixInfo.setCreatedon(createDate);
		sectionSixInfo.setCreatedby(createdBy);
				
		return mnCompCertificatePersistence.update(sectionSixInfo);
		
	}
	
	
	public MnCompCertificate updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks,boolean reupload) {
		MnCompCertificate mnCompCertificate = null;
		try {
			mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {
			//mnCompCertificate = mnCompCertificateLocalService.createMnCompCertificate(reportUploadLogId);			
			mnCompCertificate.setReportMasterId(reportUploadLog.getReportMasterId());
			mnCompCertificate.setReportDate(reportUploadLog.getReportDate());
			mnCompCertificate.setCreatedon(createDate);
			mnCompCertificate.setCreatedby(createdBy);
			mnCompCertificate.setStatus(status);
			mnCompCertificate.setStatusByUserId(statusByUserId);
			mnCompCertificate.setStatusByUserName(statusByUserName);
			mnCompCertificate.setStatusDate(statusDate);
			mnCompCertificate.setRemarks(remarks);
			mnCompCertificate.setFileEntryId(fileEntryId);
			
			mnCompCertificate = mnCompCertificatePersistence.update(mnCompCertificate);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForMnCompCertificate(mnCompCertificate, serviceContext, createdBy);
		}else {
			mnCompCertificate.setCreatedon(createDate);
			mnCompCertificate.setCreatedby(createdBy);
			
			mnCompCertificate.setStatus(status);
			mnCompCertificate.setStatusByUserId(statusByUserId);
			mnCompCertificate.setStatusByUserName(statusByUserName);
			mnCompCertificate.setStatusDate(statusDate);
			mnCompCertificate.setRemarks(remarks);
			mnCompCertificate.setFileEntryId(fileEntryId);
			
			mnCompCertificate = mnCompCertificatePersistence.update(mnCompCertificate);
			//reportUploadLog.setUploaded(true);
			//reportUploadLog.setUploaded_i(1);
			
			try {
				reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId);
				reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			} catch (Exception e) {
				_log.error("Exception in updating reportUploadLog::" + e.getMessage());
			}
			
			String workflowContext = mnCompCertificate.getWorkflowContext();
			
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return mnCompCertificate;
	}
	
	public void createAssetForMnCompCertificate(MnCompCertificate mnCompCertificate, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(mnCompCertificate)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    MnCompCertificate.class.getName(),
	                    mnCompCertificate.getReportUploadLogId(),
	                    mnCompCertificate.getUuid(),
	                    0,
	                    null,
	                    null,
	                    true,
	                    false,
	                    new Date(),
	                    null,
	                    new Date(),
	                    null,
	                    ContentTypes.TEXT_HTML,
	                    "Report Upload Log Asset",
	                    "Report Upload Log with Id: " +  mnCompCertificate.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<MnCompCertificate> indexer = IndexerRegistryUtil.nullSafeGetIndexer(MnCompCertificate.class);
				indexer.reindex(mnCompCertificate);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						MnCompCertificate.class.getName(), mnCompCertificate.getReportUploadLogId(), mnCompCertificate, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public MnCompCertificate updateMnCompCertificateStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		MnCompCertificate mnCompCertificate = null;
		try {
			mnCompCertificate = mnCompCertificatePersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(mnCompCertificate)) {
			mnCompCertificate.setStatus(status);
			mnCompCertificate.setStatusByUserId(userId);
			mnCompCertificate.setStatusDate(new Date());
			mnCompCertificate.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				mnCompCertificate.setStatusByUserName(user.getFullName());
				mnCompCertificate.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			mnCompCertificate = mnCompCertificatePersistence.update(mnCompCertificate);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(MnCompCertificate.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(MnCompCertificate.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return mnCompCertificate;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, MnCompCertificate.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	Log _log = LogFactoryUtil.getLog(MnCompCertificateLocalServiceImpl.class);
	
	public long countMnCompCertificateByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MnCompCertificateImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return mnCompCertificateLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MnCompCertificateImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return mnCompCertificateLocalService.dynamicQueryCount(query);
	}
	
	public List<MnCompCertificate> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MnCompCertificateImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return mnCompCertificateLocalService.dynamicQuery(query);
	}
	
	public List<MnCompCertificate> getByReportUploadlogIdAndIssubmitted(long reportUploadLogId,boolean isSubmitted ){
		return mnCompCertificatePersistence.findByreportUploadLogIdAndIssubmitted(reportUploadLogId, isSubmitted);
		
	}
}