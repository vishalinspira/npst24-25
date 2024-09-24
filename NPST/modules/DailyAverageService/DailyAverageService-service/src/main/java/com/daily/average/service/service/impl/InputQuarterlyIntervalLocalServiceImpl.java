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
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.InputQuarterlyIntervalImpl;
import com.daily.average.service.service.base.InputQuarterlyIntervalLocalServiceBaseImpl;
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
public class InputQuarterlyIntervalLocalServiceImpl
	extends InputQuarterlyIntervalLocalServiceBaseImpl {
	
	public InputQuarterlyInterval addInputQuarterlyInterval(long reportUploadLogId,Date formDate, String oneia, String oneib, String oneiia, String oneiib,
			String oneiic, String oneiid, String oneiiia, String oneiiib, String oneiva, String onev, String twoia, String twoib, 
			String twoic, String twoiione, String twoiitwo, String twoiithree, String twoiifour, String twoiifive, String twoiisix,
			String twoiiseven, String threea, String threeb, String threec, String threeci, String threecii, String threeciii,
			String threeciv, String foura, String fourb, String fourc, String fivei, String fiveii, String fiveiii, String fiveiv,
			String fiveva, String fivevb, String fivevc, String fivevia, String fivevib, String sixi, String sixiia, String sixiib,
			String sixiic, String sevenia, String sevenib, String sevenic, String sevenid, String sevenie, String eightia, String eightib,
			String eightii, String eightiii, String eightiv, String eightv, String eightvia, String eightvib, String eightviia,
			String eightviib, String eightviii,String eightix,String eightx, String ninea, String nineb,String ten, String elevena, String elevenb, String twelve, 
			
			String oneia_remark, String oneib_remark, String oneiia_remark, String oneiib_remark,
			String oneiic_remark, String oneiid_remark, String oneiiia_remark, String oneiiib_remark, String oneiva_remark, String onev_remark, String twoia_remark, String twoib_remark, 
			String twoic_remark, String twoiione_remark, String twoiitwo_remark, String twoiithree_remark, String twoiifour_remark, String twoiifive_remark, String twoiisix_remark,
			String twoiiseven_remark, String threea_remark, String threeb_remark, String threec_remark, String threeci_remark, String threecii_remark, String threeciii_remark,
			String threeciv_remark, String foura_remark, String fourb_remark, String fourc_remark, String fivei_remark, String fiveii_remark, String fiveiii_remark, String fiveiv_remark,
			String fiveva_remark, String fivevb_remark, String fivevc_remark, String fivevia_remark, String fivevib_remark, String sixi_remark, String sixiia_remark, String sixiib_remark,
			String sixiic_remark, String sevenia_remark, String sevenib_remark, String sevenic_remark, String sevenid_remark, String sevenie_remark, String eightia_remark, String eightib_remark,
			String eightii_remark, String eightiii_remark, String eightiv_remark, String eightv_remark, String eightvia_remark, String eightvib_remark, String eightviia_remark,
			String eightviib_remark, String eightviii_remark,String eightix_remark,String eightx_remark, String ninea_remark, String nineb_remark,String ten_remark, String elevena_remark, String elevenb_remark, String twelve_remark,
			String board_a_rem, String board_b_rem, String investment_a_rem, String investment_b_rem, String risk_a_rem, String risk_b_rem, String annex_i_rem,String annex_ii_rem, 
			String annex_iii_rem,String annex_iv_rem, String annex_comp_certificate_rem,
			String notedate, String companies, String date_3, String place, String ceoname, String ceonameii, long board_a, long board_b,
			long investment_a, long investment_b, long risk_a, long risk_b, long annex_ii, long annex_iii, long annex_iv, long annex_comp_certificate, Date createdate, long createdby,long annex_i ,
			long fileEntryId,int isAmRejected) {
		
		InputQuarterlyInterval inputQuarterlyInterval= inputQuarterlyIntervalPersistence.fetchByPrimaryKey(reportUploadLogId);
		if(inputQuarterlyInterval == null) {
			inputQuarterlyInterval= inputQuarterlyIntervalPersistence.create(reportUploadLogId);
		}
		inputQuarterlyInterval.setFormDate(formDate);
		inputQuarterlyInterval.setAnnex_comp_certificate(annex_comp_certificate);
		
		inputQuarterlyInterval.setOneia(oneia);
		inputQuarterlyInterval.setOneib(oneib);
		inputQuarterlyInterval.setOneiia(oneiia);
		inputQuarterlyInterval.setOneiib(oneiib);
		inputQuarterlyInterval.setOneiic(oneiic);
		inputQuarterlyInterval.setOneiid(oneiid);
		inputQuarterlyInterval.setOneiiia(oneiiia);
		inputQuarterlyInterval.setOneiiib(oneiiib);
		inputQuarterlyInterval.setOneiva(oneiva);
		inputQuarterlyInterval.setOnev(onev);
		inputQuarterlyInterval.setTwoia(twoia);
		inputQuarterlyInterval.setTwoib(twoib);
		inputQuarterlyInterval.setTwoic(twoic);
		inputQuarterlyInterval.setTwoiione(twoiione);
		inputQuarterlyInterval.setTwoiitwo(twoiitwo);
		inputQuarterlyInterval.setTwoiithree(twoiithree);
		inputQuarterlyInterval.setTwoiifour(twoiifour);
		inputQuarterlyInterval.setTwoiifive(twoiifive);
		inputQuarterlyInterval.setTwoiisix(twoiisix);
		inputQuarterlyInterval.setTwoiiseven(twoiiseven);
		inputQuarterlyInterval.setThreea(threea);
		inputQuarterlyInterval.setThreeb(threeb);
		inputQuarterlyInterval.setThreec(threec);
		inputQuarterlyInterval.setThreeci(threeci);
		inputQuarterlyInterval.setThreecii(threecii);
		inputQuarterlyInterval.setThreeciii(threeciii);
		inputQuarterlyInterval.setThreeciv(threeciv);
		inputQuarterlyInterval.setFoura(foura);
		inputQuarterlyInterval.setFourb(fourb);
		inputQuarterlyInterval.setFourc(fourc);
		inputQuarterlyInterval.setFivei(fivei);
		inputQuarterlyInterval.setFiveii(fiveii);
		inputQuarterlyInterval.setFiveiii(fiveiii);
		inputQuarterlyInterval.setFiveiv(fiveiv);
		inputQuarterlyInterval.setFiveva(fiveva);
		inputQuarterlyInterval.setFivevb(fivevb);
		inputQuarterlyInterval.setFivevc(fivevc);
		inputQuarterlyInterval.setFivevia(fivevia);
		inputQuarterlyInterval.setFivevib(fivevib);
		inputQuarterlyInterval.setSixi(sixi);
		inputQuarterlyInterval.setSixiia(sixiia);
		inputQuarterlyInterval.setSixiib(sixiib);
		inputQuarterlyInterval.setSixiic(sixiic);
		inputQuarterlyInterval.setSevenia(sevenia);
		inputQuarterlyInterval.setSevenib(sevenib);
		inputQuarterlyInterval.setSevenic(sevenic);
		inputQuarterlyInterval.setSevenid(sevenid);
		inputQuarterlyInterval.setSevenie(sevenie);
		inputQuarterlyInterval.setEightia(eightia);
		inputQuarterlyInterval.setEightib(eightib);
		inputQuarterlyInterval.setEightii(eightii);
		inputQuarterlyInterval.setEightiii(eightiii);
		inputQuarterlyInterval.setEightiv(eightiv);
		inputQuarterlyInterval.setEightv(eightv);
		inputQuarterlyInterval.setEightvia(eightvia);
		inputQuarterlyInterval.setEightvib(eightvib);
		inputQuarterlyInterval.setEightviia(eightviia);
		inputQuarterlyInterval.setEightviib(eightviib);
		inputQuarterlyInterval.setEightviii(eightviii);
		inputQuarterlyInterval.setEightix(eightix);
		inputQuarterlyInterval.setEightx(eightx);
		inputQuarterlyInterval.setNinea(ninea);
		inputQuarterlyInterval.setNineb(nineb);
		inputQuarterlyInterval.setTen(ten);
		inputQuarterlyInterval.setElevena(elevena);
		inputQuarterlyInterval.setElevenb(elevenb);
		inputQuarterlyInterval.setTwelve(twelve);
		
		
		inputQuarterlyInterval.setOneia_remark(oneia_remark);
		inputQuarterlyInterval.setOneib_remark(oneib_remark);
		inputQuarterlyInterval.setOneiia_remark(oneiia_remark);
		inputQuarterlyInterval.setOneiib_remark(oneiib_remark);
		inputQuarterlyInterval.setOneiic_remark(oneiic_remark);
		inputQuarterlyInterval.setOneiid_remark(oneiid_remark);
		inputQuarterlyInterval.setOneiiia_remark(oneiiia_remark);
		inputQuarterlyInterval.setOneiiib_remark(oneiiib_remark);
		inputQuarterlyInterval.setOneiva_remark(oneiva_remark);
		inputQuarterlyInterval.setOnev_remark(onev_remark);
		inputQuarterlyInterval.setTwoia_remark(twoia_remark);
		inputQuarterlyInterval.setTwoib_remark(twoib_remark);
		inputQuarterlyInterval.setTwoic_remark(twoic_remark);
		inputQuarterlyInterval.setTwoiione_remark(twoiione_remark);
		inputQuarterlyInterval.setTwoiitwo_remark(twoiitwo_remark);
		inputQuarterlyInterval.setTwoiithree_remark(twoiithree_remark);
		inputQuarterlyInterval.setTwoiifour_remark(twoiifour_remark);
		inputQuarterlyInterval.setTwoiifive_remark(twoiifive_remark);
		inputQuarterlyInterval.setTwoiisix_remark(twoiisix_remark);
		inputQuarterlyInterval.setTwoiiseven_remark(twoiiseven_remark);
		inputQuarterlyInterval.setThreea_remark(threea_remark);
		inputQuarterlyInterval.setThreeb_remark(threeb_remark);
		inputQuarterlyInterval.setThreec_remark(threec_remark);
		inputQuarterlyInterval.setThreeci_remark(threeci_remark);
		inputQuarterlyInterval.setThreecii_remark(threecii_remark);
		inputQuarterlyInterval.setThreeciii_remark(threeciii_remark);
		inputQuarterlyInterval.setThreeciv_remark(threeciv_remark);
		inputQuarterlyInterval.setFoura_remark(foura_remark);
		inputQuarterlyInterval.setFourb_remark(fourb_remark);
		inputQuarterlyInterval.setFourc_remark(fourc_remark);
		inputQuarterlyInterval.setFivei_remark(fivei_remark);
		inputQuarterlyInterval.setFiveii_remark(fiveii_remark);
		inputQuarterlyInterval.setFiveiii_remark(fiveiii_remark);
		inputQuarterlyInterval.setFiveiv_remark(fiveiv_remark);
		inputQuarterlyInterval.setFiveva_remark(fiveva_remark);
		inputQuarterlyInterval.setFivevb_remark(fivevb_remark);
		inputQuarterlyInterval.setFivevc_remark(fivevc_remark);
		inputQuarterlyInterval.setFivevia_remark(fivevia_remark);
		inputQuarterlyInterval.setFivevib_remark(fivevib_remark);
		inputQuarterlyInterval.setSixi_remark(sixi_remark);
		inputQuarterlyInterval.setSixiia_remark(sixiia_remark);
		inputQuarterlyInterval.setSixiib_remark(sixiib_remark);
		inputQuarterlyInterval.setSixiic_remark(sixiic_remark);
		inputQuarterlyInterval.setSevenia_remark(sevenia_remark);
		inputQuarterlyInterval.setSevenib_remark(sevenib_remark);
		inputQuarterlyInterval.setSevenic_remark(sevenic_remark);
		inputQuarterlyInterval.setSevenid_remark(sevenid_remark);
		inputQuarterlyInterval.setSevenie_remark(sevenie_remark);
		inputQuarterlyInterval.setEightia_remark(eightia_remark);
		inputQuarterlyInterval.setEightib_remark(eightib_remark);
		inputQuarterlyInterval.setEightii_remark(eightii_remark);
		inputQuarterlyInterval.setEightiii_remark(eightiii_remark);
		inputQuarterlyInterval.setEightiv_remark(eightiv_remark);
		inputQuarterlyInterval.setEightv_remark(eightv_remark);
		inputQuarterlyInterval.setEightvia_remark(eightvia_remark);
		inputQuarterlyInterval.setEightvib_remark(eightvib_remark);
		inputQuarterlyInterval.setEightviia_remark(eightviia_remark);
		inputQuarterlyInterval.setEightviib_remark(eightviib_remark);
		inputQuarterlyInterval.setEightviii_remark(eightviii_remark);
		inputQuarterlyInterval.setEightix_remark(eightix_remark);;
		inputQuarterlyInterval.setEightx_remark(eightx_remark);
		inputQuarterlyInterval.setNinea_remark(ninea_remark);
		inputQuarterlyInterval.setNineb_remark(nineb_remark);
		inputQuarterlyInterval.setTen_remark(ten_remark);
		inputQuarterlyInterval.setElevena_remark(elevena_remark);
		inputQuarterlyInterval.setElevenb_remark(elevenb_remark);
		inputQuarterlyInterval.setTwelve_remark(twelve_remark);
		inputQuarterlyInterval.setAnnex_i_rem(annex_i_rem);
		inputQuarterlyInterval.setBoard_a_rem(board_a_rem);
		inputQuarterlyInterval.setBoard_b_rem(board_b_rem);
		inputQuarterlyInterval.setInvestment_a_rem(investment_a_rem);
		inputQuarterlyInterval.setInvestment_b_rem(investment_b_rem);
		inputQuarterlyInterval.setRisk_a_rem(risk_a_rem);
		inputQuarterlyInterval.setRisk_b_rem(risk_b_rem);
		inputQuarterlyInterval.setAnnex_ii_rem(annex_ii_rem);
		inputQuarterlyInterval.setAnnex_iii_rem(annex_iii_rem);
		inputQuarterlyInterval.setAnnex_iv_rem(annex_iv_rem);
		inputQuarterlyInterval.setAnnex_comp_certificate_rem(annex_comp_certificate_rem);
		
		
		
		inputQuarterlyInterval.setNotedate(notedate);
		inputQuarterlyInterval.setCompanies(companies);
		inputQuarterlyInterval.setDate_3(date_3);
		inputQuarterlyInterval.setPlace(place);
		inputQuarterlyInterval.setCeoname(ceoname);
		inputQuarterlyInterval.setCeonameii(ceonameii);
		inputQuarterlyInterval.setAnnex_i(annex_i);
		inputQuarterlyInterval.setBoard_a(board_a);
		inputQuarterlyInterval.setBoard_b(board_b);
		inputQuarterlyInterval.setInvestment_a(investment_a);
		inputQuarterlyInterval.setInvestment_b(investment_b);
		inputQuarterlyInterval.setRisk_a(risk_a);
		inputQuarterlyInterval.setRisk_b(risk_b);
		inputQuarterlyInterval.setAnnex_ii(annex_ii);
		inputQuarterlyInterval.setAnnex_iii(annex_iii);
		inputQuarterlyInterval.setAnnex_iv(annex_iv);
		inputQuarterlyInterval.setAnnex_iv(annex_iv);
		inputQuarterlyInterval.setAnnex_comp_certificate(annex_comp_certificate);
		inputQuarterlyInterval.setCreatedate(createdate);
		inputQuarterlyInterval.setCreatedby(createdby);
		inputQuarterlyInterval.setFileEntryId(fileEntryId);
		inputQuarterlyInterval.setIsAmRejected(isAmRejected);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+inputQuarterlyInterval.toString());
		
		return inputQuarterlyIntervalPersistence.update(inputQuarterlyInterval);
	}
	
	
	public InputQuarterlyInterval updateIntermediaryRem(long reportUploadLogId,String oneia_remark, String oneib_remark, String oneiia_remark, String oneiib_remark,
			String oneiic_remark, String oneiid_remark, String oneiiia_remark, String oneiiib_remark, String oneiva_remark, String onev_remark, String twoia_remark, String twoib_remark, 
			String twoic_remark, String twoiione_remark, String twoiitwo_remark, String twoiithree_remark, String twoiifour_remark, String twoiifive_remark, String twoiisix_remark,
			String twoiiseven_remark, String threea_remark, String threeb_remark, String threec_remark, String threeci_remark, String threecii_remark, String threeciii_remark,
			String threeciv_remark, String foura_remark, String fourb_remark, String fourc_remark, String fivei_remark, String fiveii_remark, String fiveiii_remark, String fiveiv_remark,
			String fiveva_remark, String fivevb_remark, String fivevc_remark, String fivevia_remark, String fivevib_remark, String sixi_remark, String sixiia_remark, String sixiib_remark,
			String sixiic_remark, String sevenia_remark, String sevenib_remark, String sevenic_remark, String sevenid_remark, String sevenie_remark, String eightia_remark, String eightib_remark,
			String eightii_remark, String eightiii_remark, String eightiv_remark, String eightv_remark, String eightvia_remark, String eightvib_remark, String eightviia_remark,
			String eightviib_remark, String eightviii_remark, String ninea_remark, String nineb_remark,String ten_remark, String elevena_remark, String elevenb_remark, String twelve_remark){
		
		InputQuarterlyInterval inputQuarterlyInterval= inputQuarterlyIntervalPersistence.fetchByPrimaryKey(reportUploadLogId);
		
		inputQuarterlyInterval.setOneia_remark(oneia_remark);
		inputQuarterlyInterval.setOneib_remark(oneib_remark);
		inputQuarterlyInterval.setOneiia_remark(oneiia_remark);
		inputQuarterlyInterval.setOneiib_remark(oneiib_remark);
		inputQuarterlyInterval.setOneiic_remark(oneiic_remark);
		inputQuarterlyInterval.setOneiid_remark(oneiid_remark);
		inputQuarterlyInterval.setOneiiia_remark(oneiiia_remark);
		inputQuarterlyInterval.setOneiiib_remark(oneiiib_remark);
		inputQuarterlyInterval.setOneiva_remark(oneiva_remark);
		inputQuarterlyInterval.setOnev_remark(onev_remark);
		inputQuarterlyInterval.setTwoia_remark(twoia_remark);
		inputQuarterlyInterval.setTwoib_remark(twoib_remark);
		inputQuarterlyInterval.setTwoic_remark(twoic_remark);
		inputQuarterlyInterval.setTwoiione_remark(twoiione_remark);
		inputQuarterlyInterval.setTwoiitwo_remark(twoiitwo_remark);
		inputQuarterlyInterval.setTwoiithree_remark(twoiithree_remark);
		inputQuarterlyInterval.setTwoiifour_remark(twoiifour_remark);
		inputQuarterlyInterval.setTwoiifive_remark(twoiifive_remark);
		inputQuarterlyInterval.setTwoiisix_remark(twoiisix_remark);
		inputQuarterlyInterval.setTwoiiseven_remark(twoiiseven_remark);
		inputQuarterlyInterval.setThreea_remark(threea_remark);
		inputQuarterlyInterval.setThreeb_remark(threeb_remark);
		inputQuarterlyInterval.setThreec_remark(threec_remark);
		inputQuarterlyInterval.setThreeci_remark(threeci_remark);
		inputQuarterlyInterval.setThreecii_remark(threecii_remark);
		inputQuarterlyInterval.setThreeciii_remark(threeciii_remark);
		inputQuarterlyInterval.setThreeciv_remark(threeciv_remark);
		inputQuarterlyInterval.setFoura_remark(foura_remark);
		inputQuarterlyInterval.setFourb_remark(fourb_remark);
		inputQuarterlyInterval.setFourc_remark(fourc_remark);
		inputQuarterlyInterval.setFivei_remark(fivei_remark);
		inputQuarterlyInterval.setFiveii_remark(fiveii_remark);
		inputQuarterlyInterval.setFiveiii_remark(fiveiii_remark);
		inputQuarterlyInterval.setFiveiv_remark(fiveiv_remark);
		inputQuarterlyInterval.setFiveva_remark(fiveva_remark);
		inputQuarterlyInterval.setFivevb_remark(fivevb_remark);
		inputQuarterlyInterval.setFivevc_remark(fivevc_remark);
		inputQuarterlyInterval.setFivevia_remark(fivevia_remark);
		inputQuarterlyInterval.setFivevib_remark(fivevib_remark);
		inputQuarterlyInterval.setSixi_remark(sixi_remark);
		inputQuarterlyInterval.setSixiia_remark(sixiia_remark);
		inputQuarterlyInterval.setSixiib_remark(sixiib_remark);
		inputQuarterlyInterval.setSixiic_remark(sixiic_remark);
		inputQuarterlyInterval.setSevenia_remark(sevenia_remark);
		inputQuarterlyInterval.setSevenib_remark(sevenib_remark);
		inputQuarterlyInterval.setSevenic_remark(sevenic_remark);
		inputQuarterlyInterval.setSevenid_remark(sevenid_remark);
		inputQuarterlyInterval.setSevenie_remark(sevenie_remark);
		inputQuarterlyInterval.setEightia_remark(eightia_remark);
		inputQuarterlyInterval.setEightib_remark(eightib_remark);
		inputQuarterlyInterval.setEightii_remark(eightii_remark);
		inputQuarterlyInterval.setEightiii_remark(eightiii_remark);
		inputQuarterlyInterval.setEightiv_remark(eightiv_remark);
		inputQuarterlyInterval.setEightv_remark(eightv_remark);
		inputQuarterlyInterval.setEightvia_remark(eightvia_remark);
		inputQuarterlyInterval.setEightvib_remark(eightvib_remark);
		inputQuarterlyInterval.setEightviia_remark(eightviia_remark);
		inputQuarterlyInterval.setEightviib_remark(eightviib_remark);
		inputQuarterlyInterval.setEightviii_remark(eightviii_remark);
		inputQuarterlyInterval.setNinea_remark(ninea_remark);
		inputQuarterlyInterval.setNineb_remark(nineb_remark);
		inputQuarterlyInterval.setTen_remark(ten_remark);
		inputQuarterlyInterval.setElevena_remark(elevena_remark);
		inputQuarterlyInterval.setElevenb_remark(elevenb_remark);
		inputQuarterlyInterval.setTwelve_remark(twelve_remark);
		return inputQuarterlyIntervalPersistence.update(inputQuarterlyInterval);

		
	
	}
	public InputQuarterlyInterval updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		InputQuarterlyInterval inputQuarterlyInterval = null;
		try {
			inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {		
			inputQuarterlyInterval.setReportMasterId(reportUploadLog.getReportMasterId());
			inputQuarterlyInterval.setReportDate(reportUploadLog.getReportDate());
			inputQuarterlyInterval.setCreatedate(createDate);
			inputQuarterlyInterval.setCreatedby(createdBy);
			inputQuarterlyInterval.setStatus(status);
			inputQuarterlyInterval.setStatusByUserId(statusByUserId);
			inputQuarterlyInterval.setStatusByUserName(statusByUserName);
			inputQuarterlyInterval.setStatusDate(statusDate);
			inputQuarterlyInterval.setRemarks(remarks);
			inputQuarterlyInterval.setFileEntryId(fileEntryId);
			
			inputQuarterlyInterval = inputQuarterlyIntervalPersistence.update(inputQuarterlyInterval);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForInputQuarterlyInterval(inputQuarterlyInterval, serviceContext, createdBy);
		}else {
			inputQuarterlyInterval.setCreatedate(createDate);
			inputQuarterlyInterval.setCreatedby(createdBy);
			
			inputQuarterlyInterval.setStatus(status);
			inputQuarterlyInterval.setStatusByUserId(statusByUserId);
			inputQuarterlyInterval.setStatusByUserName(statusByUserName);
			inputQuarterlyInterval.setStatusDate(statusDate);
			inputQuarterlyInterval.setRemarks(remarks);
			inputQuarterlyInterval.setFileEntryId(fileEntryId);
			
			inputQuarterlyInterval = inputQuarterlyIntervalPersistence.update(inputQuarterlyInterval);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = inputQuarterlyInterval.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return inputQuarterlyInterval;
	}
	
	public void createAssetForInputQuarterlyInterval(InputQuarterlyInterval inputQuarterlyInterval, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(inputQuarterlyInterval)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    InputQuarterlyInterval.class.getName(),
	                    inputQuarterlyInterval.getReportUploadLogId(),
	                    inputQuarterlyInterval.getUuid(),
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
	                    "Report Upload Log with Id: " +  inputQuarterlyInterval.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<InputQuarterlyInterval> indexer = IndexerRegistryUtil.nullSafeGetIndexer(InputQuarterlyInterval.class);
				indexer.reindex(inputQuarterlyInterval);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						InputQuarterlyInterval.class.getName(), inputQuarterlyInterval.getReportUploadLogId(), inputQuarterlyInterval, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public InputQuarterlyInterval updateInputQuarterlyIntervalStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		InputQuarterlyInterval inputQuarterlyInterval = null;
		try {
			inputQuarterlyInterval = inputQuarterlyIntervalPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(inputQuarterlyInterval)) {
			inputQuarterlyInterval.setStatus(status);
			inputQuarterlyInterval.setStatusByUserId(userId);
			inputQuarterlyInterval.setStatusDate(new Date());
			inputQuarterlyInterval.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				inputQuarterlyInterval.setStatusByUserName(user.getFullName());
				inputQuarterlyInterval.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			inputQuarterlyInterval = inputQuarterlyIntervalPersistence.update(inputQuarterlyInterval);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(InputQuarterlyInterval.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(InputQuarterlyInterval.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return inputQuarterlyInterval;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, InputQuarterlyInterval.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	Log _log = LogFactoryUtil.getLog(InputQuarterlyIntervalLocalServiceImpl.class);
	
	public long countInputQuarterlyIntervalByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(InputQuarterlyIntervalImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return inputQuarterlyIntervalLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(InputQuarterlyIntervalImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return inputQuarterlyIntervalLocalService.dynamicQueryCount(query);
	}
	
	public List<InputQuarterlyInterval> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(InputQuarterlyIntervalImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return inputQuarterlyIntervalLocalService.dynamicQuery(query);
	}
	
	public List<InputQuarterlyInterval> getByReportUploadlogIdAndIssubmitted(long reportUploadLogId,boolean isSubmitted ){
		return inputQuarterlyIntervalPersistence.findByreportUploadLogIdAndIssubmitted(reportUploadLogId, isSubmitted);
		
	}
}