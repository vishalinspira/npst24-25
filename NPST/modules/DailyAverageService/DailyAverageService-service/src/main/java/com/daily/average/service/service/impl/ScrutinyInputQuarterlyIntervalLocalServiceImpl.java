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

import com.daily.average.service.model.ScrutinyInputQuarterlyInterval;
import com.daily.average.service.service.base.ScrutinyInputQuarterlyIntervalLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ScrutinyInputQuarterlyIntervalLocalServiceImpl
	extends ScrutinyInputQuarterlyIntervalLocalServiceBaseImpl {
	
	public ScrutinyInputQuarterlyInterval addScrutinyInputQuarterlyInterval(String oneia_rem, String oneib_rem, String oneiia_rem, String oneiib_rem, 
			String oneiic_rem, String oneiid_rem, String oneiiia_rem, String oneiiib_rem, String oneiva_rem, String onev_rem, String twoia_rem, String
			twoib_rem, String twoic_rem, String twoiione_rem, String twoiitwo_rem, String twoiithree_rem, String twoiifour_rem, String twoiifive_rem,
			String twoiisix_rem, String twoiiseven_rem, String threea_rem, String threeb_rem, String threec_rem, String threeci_rem, String threecii_rem,
			String threeciii_rem, String threeciv_rem, String foura_rem, String fourb_rem, String fourc_rem, String fivei_rem, String fiveii_rem, String 
			fiveiii_rem, String fiveiv_rem, String fiveva_rem, String fivevb_rem, String fivevc_rem, String fivevia_rem, String fivevib_rem,
			String sixi_rem, String sixiia_rem, String sixiib_rem, String sixiic_rem, String sevenia_rem, String sevenib_rem, String sevenic_rem,
			String sevenid_rem, String sevenie_rem, String eightia_rem, String eightib_rem, String eightii_rem, String eightiii_rem, String eightiv_rem,
			String eightv_rem, String eightvia_rem, String eightvib_rem, String eightviia_rem, String eightviib_rem, String eightviii_rem,String eightix_rem,String eightx_rem, String ninea_rem,
			String nineb_rem, String ten_rem, String elevena_rem, String elevenb_rem, String twelve_rem, String board_a_rem, String board_b_rem, String investment_a_rem,
			String investment_b_rem, String risk_a_rem, String risk_b_rem, String annex_ii_rem, String annex_iii_rem, String annex_iv_rem, String annex_comp_certificate_rem, Date createdate, long createdby,
			String username,double mnversion,long userid, long reportUploadLogId, String annex_i_rem) {
		
		ScrutinyInputQuarterlyInterval scrutinyInputQuarterlyInterval= scrutinyInputQuarterlyIntervalPersistence.create(CounterLocalServiceUtil.increment(ScrutinyInputQuarterlyInterval.class.getName()));
		
		scrutinyInputQuarterlyInterval.setUsername(username);
		scrutinyInputQuarterlyInterval.setVersion(mnversion);
		scrutinyInputQuarterlyInterval.setUserid(userid);
		scrutinyInputQuarterlyInterval.setReportUploadLogId(reportUploadLogId);
		
		scrutinyInputQuarterlyInterval.setOneia_rem(oneia_rem);
		scrutinyInputQuarterlyInterval.setOneib_rem(oneib_rem);
		scrutinyInputQuarterlyInterval.setOneiia_rem(oneiia_rem);
		scrutinyInputQuarterlyInterval.setOneiib_rem(oneiib_rem);
		scrutinyInputQuarterlyInterval.setOneiic_rem(oneiic_rem);
		scrutinyInputQuarterlyInterval.setOneiid_rem(oneiid_rem);
		scrutinyInputQuarterlyInterval.setOneiiia_rem(oneiiia_rem);
		scrutinyInputQuarterlyInterval.setOneiiib_rem(oneiiib_rem);
		scrutinyInputQuarterlyInterval.setOneiva_rem(oneiva_rem);
		scrutinyInputQuarterlyInterval.setOnev_rem(onev_rem);
		scrutinyInputQuarterlyInterval.setTwoia_rem(twoia_rem);
		scrutinyInputQuarterlyInterval.setTwoib_rem(twoib_rem);
		scrutinyInputQuarterlyInterval.setTwoic_rem(twoic_rem);
		scrutinyInputQuarterlyInterval.setTwoiione_rem(twoiione_rem);
		scrutinyInputQuarterlyInterval.setTwoiitwo_rem(twoiitwo_rem);
		scrutinyInputQuarterlyInterval.setTwoiithree_rem(twoiithree_rem);
		scrutinyInputQuarterlyInterval.setTwoiifour_rem(twoiifour_rem);
		scrutinyInputQuarterlyInterval.setTwoiifive_rem(twoiifive_rem);
		scrutinyInputQuarterlyInterval.setTwoiisix_rem(twoiisix_rem);
		scrutinyInputQuarterlyInterval.setTwoiiseven_rem(twoiiseven_rem);
		scrutinyInputQuarterlyInterval.setThreea_rem(threea_rem);
		scrutinyInputQuarterlyInterval.setThreeb_rem(threeb_rem);
		scrutinyInputQuarterlyInterval.setThreec_rem(threec_rem);
		scrutinyInputQuarterlyInterval.setThreeci_rem(threeci_rem);
		scrutinyInputQuarterlyInterval.setThreecii_rem(threecii_rem);
		scrutinyInputQuarterlyInterval.setThreeciii_rem(threeciii_rem);
		scrutinyInputQuarterlyInterval.setThreeciv_rem(threeciv_rem);
		scrutinyInputQuarterlyInterval.setFoura_rem(foura_rem);
		scrutinyInputQuarterlyInterval.setFourb_rem(fourb_rem);
		scrutinyInputQuarterlyInterval.setFourc_rem(fourc_rem);
		scrutinyInputQuarterlyInterval.setFivei_rem(fivei_rem);
		scrutinyInputQuarterlyInterval.setFiveii_rem(fiveii_rem);
		scrutinyInputQuarterlyInterval.setFiveiii_rem(fiveiii_rem);
		scrutinyInputQuarterlyInterval.setFiveiv_rem(fiveiv_rem);
		scrutinyInputQuarterlyInterval.setFiveva_rem(fiveva_rem);
		scrutinyInputQuarterlyInterval.setFivevb_rem(fivevb_rem);
		scrutinyInputQuarterlyInterval.setFivevc_rem(fivevc_rem);
		scrutinyInputQuarterlyInterval.setFivevia_rem(fivevia_rem);
		scrutinyInputQuarterlyInterval.setFivevib_rem(fivevib_rem);
		scrutinyInputQuarterlyInterval.setSixi_rem(sixi_rem);
		scrutinyInputQuarterlyInterval.setSixiia_rem(sixiia_rem);
		scrutinyInputQuarterlyInterval.setSixiib_rem(sixiib_rem);
		scrutinyInputQuarterlyInterval.setSixiic_rem(sixiic_rem);
		scrutinyInputQuarterlyInterval.setSevenia_rem(sevenia_rem);
		scrutinyInputQuarterlyInterval.setSevenib_rem(sevenib_rem);
		scrutinyInputQuarterlyInterval.setSevenic_rem(sevenic_rem);
		scrutinyInputQuarterlyInterval.setSevenid_rem(sevenid_rem);
		scrutinyInputQuarterlyInterval.setSevenie_rem(sevenie_rem);
		scrutinyInputQuarterlyInterval.setEightia_rem(eightia_rem);
		scrutinyInputQuarterlyInterval.setEightib_rem(eightib_rem);
		scrutinyInputQuarterlyInterval.setEightii_rem(eightii_rem);
		scrutinyInputQuarterlyInterval.setEightiii_rem(eightiii_rem);
		scrutinyInputQuarterlyInterval.setEightiv_rem(eightiv_rem);
		scrutinyInputQuarterlyInterval.setEightv_rem(eightv_rem);
		scrutinyInputQuarterlyInterval.setEightvia_rem(eightvia_rem);
		scrutinyInputQuarterlyInterval.setEightvib_rem(eightvib_rem);
		scrutinyInputQuarterlyInterval.setEightviia_rem(eightviia_rem);
		scrutinyInputQuarterlyInterval.setEightviib_rem(eightviib_rem);
		scrutinyInputQuarterlyInterval.setEightviii_rem(eightviii_rem);
		scrutinyInputQuarterlyInterval.setEightix_rem(eightix_rem);
		scrutinyInputQuarterlyInterval.setEightx_rem(eightx_rem);
		scrutinyInputQuarterlyInterval.setNinea_rem(ninea_rem);
		scrutinyInputQuarterlyInterval.setNineb_rem(nineb_rem);
		scrutinyInputQuarterlyInterval.setTen_rem(ten_rem);
		scrutinyInputQuarterlyInterval.setElevena_rem(elevena_rem);
		scrutinyInputQuarterlyInterval.setElevenb_rem(elevenb_rem);
		scrutinyInputQuarterlyInterval.setTwelve_rem(twelve_rem);
		
		scrutinyInputQuarterlyInterval.setAnnex_i_rem(annex_i_rem);
		scrutinyInputQuarterlyInterval.setBoard_a_rem(board_a_rem);
		scrutinyInputQuarterlyInterval.setBoard_b_rem(board_b_rem);
		scrutinyInputQuarterlyInterval.setInvestment_a_rem(investment_a_rem);
		scrutinyInputQuarterlyInterval.setInvestment_b_rem(investment_b_rem);
		scrutinyInputQuarterlyInterval.setRisk_a_rem(risk_a_rem);
		scrutinyInputQuarterlyInterval.setRisk_b_rem(risk_b_rem);
		scrutinyInputQuarterlyInterval.setAnnex_ii_rem(annex_ii_rem);
		scrutinyInputQuarterlyInterval.setAnnex_iii_rem(annex_iii_rem);
		scrutinyInputQuarterlyInterval.setAnnex_iv_rem(annex_iv_rem);
		scrutinyInputQuarterlyInterval.setAnnex_comp_certificate_rem(annex_comp_certificate_rem);

		scrutinyInputQuarterlyInterval.setCreatedate(createdate);
		scrutinyInputQuarterlyInterval.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+scrutinyInputQuarterlyInterval.toString());
		
		return scrutinyInputQuarterlyIntervalPersistence.update(scrutinyInputQuarterlyInterval);
	}
	
	public List<ScrutinyInputQuarterlyInterval> findByReportUploadLogId(long ReportUploadLogId) {
		return scrutinyInputQuarterlyIntervalPersistence.findByReportUploadLogId(ReportUploadLogId);
	}
	Log _log = LogFactoryUtil.getLog(getClass());
}
