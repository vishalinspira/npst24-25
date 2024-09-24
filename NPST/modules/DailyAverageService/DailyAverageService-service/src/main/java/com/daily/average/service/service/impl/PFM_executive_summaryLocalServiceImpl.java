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

import com.daily.average.service.model.PFM_executive_summary;
import com.daily.average.service.service.PFM_executive_summaryLocalServiceUtil;
import com.daily.average.service.service.base.PFM_executive_summaryLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PFM_executive_summaryLocalServiceImpl
	extends PFM_executive_summaryLocalServiceBaseImpl {
	
	public PFM_executive_summary addExecutiveSummary(long reportUploadLogId,String boardDiscription,String auditorObservation,String managementResponse,String riskrating,String npstComment){
		
		PFM_executive_summary executive_summary=PFM_executive_summaryLocalServiceUtil.createPFM_executive_summary(CounterLocalServiceUtil.increment());
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		Date date=new Date();
		executive_summary.setReportUploadLogId(reportUploadLogId);
		executive_summary.setBoardDescription(boardDiscription);
		executive_summary.setAuditorobservation(auditorObservation);
		executive_summary.setManagementResponse(managementResponse);
		executive_summary.setRiskrating(riskrating);
		executive_summary.setNpstComment(npstComment);
		executive_summary.setCreateDate(date);
		executive_summary.setModifyDate(date);
		executive_summary.setCreatedby(serviceContext.getUserId());
		executive_summary.setModifyBy(serviceContext.getUserId());
		return pfm_executive_summaryLocalService.addPFM_executive_summary(executive_summary);
	}
	public List<PFM_executive_summary> getExecutiveSummaryByReportUploadLogId(long reportUploadLogId){
		return pfm_executive_summaryPersistence.findByReportUploadLogId(reportUploadLogId);
	}
	
	public void deleteExecutiveSummaryByReportUplodLogId(long reportUploadLogId){
		try {
		List<PFM_executive_summary> executive_summaries=getExecutiveSummaryByReportUploadLogId(reportUploadLogId);
		for(PFM_executive_summary executive_summary:executive_summaries) {
			try {
				pfm_executive_summaryPersistence.remove(executive_summary.getExecutiveSummaryId());
			} catch (Exception e) {
				_log.error(e.getMessage());
			}
		}
		}catch (Exception e) {
			_log.error(e.getMessage());
		}
	}
	
	private Log _log=LogFactoryUtil.getLog(PFM_executive_summaryLocalServiceImpl.class.getName());
}