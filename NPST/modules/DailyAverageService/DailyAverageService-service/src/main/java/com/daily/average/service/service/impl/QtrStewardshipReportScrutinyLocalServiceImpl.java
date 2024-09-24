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

import com.daily.average.service.model.QtrStewardshipReportScrutiny;
import com.daily.average.service.service.base.QtrStewardshipReportScrutinyLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QtrStewardshipReportScrutinyLocalServiceImpl extends QtrStewardshipReportScrutinyLocalServiceBaseImpl {
	
	public QtrStewardshipReportScrutiny saveQtrStewardshipReportScrutiny(String username, double mnversion,
			long userid, String conflict_rem, String monitoring_rem, String resolutions_rem,String resolutionsVoted1_rem, String adversealert_rem, String insInvestorSituation_rem, 
			String annexureA_rem, String annexureB_I_rem, String annexureB_II_rem, 
			String annexureC_rem, Date createdon, long createdby, long reportUploadLogId) {
		
		QtrStewardshipReportScrutiny qtrStewardshipReportScrutiny= qtrStewardshipReportScrutinyPersistence.create(CounterLocalServiceUtil.increment(QtrStewardshipReportScrutiny.class.getName()));
		
		qtrStewardshipReportScrutiny.setReportUploadLogId(reportUploadLogId);
		qtrStewardshipReportScrutiny.setUsername(username);
		qtrStewardshipReportScrutiny.setVersion_(mnversion);
		qtrStewardshipReportScrutiny.setUserid(userid);
		
		qtrStewardshipReportScrutiny.setConflict_of_interest_rem(conflict_rem);
		qtrStewardshipReportScrutiny.setMonitoring_situation_rem(monitoring_rem);
		qtrStewardshipReportScrutiny.setResolutions_voted_rem(resolutions_rem);
		
		qtrStewardshipReportScrutiny.setResolutionsVoted1_rem(resolutionsVoted1_rem);
		qtrStewardshipReportScrutiny.setAdversealert_rem(adversealert_rem);
		qtrStewardshipReportScrutiny.setInsInvestorSituation_rem(insInvestorSituation_rem);
		
		qtrStewardshipReportScrutiny.setAnnexure_a(annexureA_rem);
		qtrStewardshipReportScrutiny.setAnnexure_b_i(annexureB_I_rem);
		qtrStewardshipReportScrutiny.setAnnexure_b_ii(annexureB_II_rem);
		qtrStewardshipReportScrutiny.setAnnexure_c(annexureC_rem);
		qtrStewardshipReportScrutiny.setCreatedon(createdon);
		qtrStewardshipReportScrutiny.setCreatedby(createdby);
		
		return qtrStewardshipReportScrutinyPersistence.update(qtrStewardshipReportScrutiny);
		
	}
	
	public List<QtrStewardshipReportScrutiny> findQtrStewardshipReportScrutinyByReportUploadLogId(long reportUploadLogId) {
		return qtrStewardshipReportScrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	}
	
}