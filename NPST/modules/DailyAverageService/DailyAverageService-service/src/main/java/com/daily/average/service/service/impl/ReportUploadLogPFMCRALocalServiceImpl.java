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

import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.model.impl.ReportUploadLogPFMCRAImpl;
import com.daily.average.service.service.base.ReportUploadLogPFMCRALocalServiceBaseImpl;
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
public class ReportUploadLogPFMCRALocalServiceImpl
	extends ReportUploadLogPFMCRALocalServiceBaseImpl {
	public ReportUploadLogPFMCRA addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogPFMCRA reportUploadLog = reportUploadLogPFMCRAPersistence.create(counterLocalService.increment(ReportUploadLogPFMCRA.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogPFMCRAPersistence.update(reportUploadLog);
	}
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @return
	 */
	public ReportUploadLogPFMCRA fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogPFMCRAPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	
	public List<ReportUploadLogPFMCRA> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogPFMCRAPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public ReportUploadLogPFMCRA updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		ReportUploadLogPFMCRA reportUploadLogPFMCRA = null;
		try {
			reportUploadLogPFMCRA = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(reportUploadLogId);
			_log.info(reportUploadLogPFMCRA);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogPFMCRA)) {
			reportUploadLogPFMCRA = reportUploadLogPFMCRALocalService.createReportUploadLogPFMCRA(reportUploadLogId);			
			reportUploadLogPFMCRA.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogPFMCRA.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogPFMCRA.setCreateDate(createDate);
			reportUploadLogPFMCRA.setCreatedBy(createdBy);
			reportUploadLogPFMCRA.setFileEntryId(fileEntryId);
			reportUploadLogPFMCRA.setUploaded(uploaded);
			
			reportUploadLogPFMCRA.setStatus(status);
			reportUploadLogPFMCRA.setStatusByUserId(statusByUserId);
			reportUploadLogPFMCRA.setStatusByUserName(statusByUserName);
			reportUploadLogPFMCRA.setStatusDate(statusDate);
			reportUploadLogPFMCRA.setRemarks(remarks);
			
			reportUploadLogPFMCRA = reportUploadLogPFMCRAPersistence.update(reportUploadLogPFMCRA);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogPFMCRA(reportUploadLogPFMCRA, serviceContext, createdBy);
		}else {
			reportUploadLogPFMCRA.setCreateDate(createDate);
			reportUploadLogPFMCRA.setCreatedBy(createdBy);
			reportUploadLogPFMCRA.setFileEntryId(fileEntryId);
			reportUploadLogPFMCRA.setUploaded(uploaded);
			
			reportUploadLogPFMCRA.setStatus(status);
			reportUploadLogPFMCRA.setStatusByUserId(statusByUserId);
			reportUploadLogPFMCRA.setStatusByUserName(statusByUserName);
			reportUploadLogPFMCRA.setStatusDate(statusDate);
			reportUploadLogPFMCRA.setRemarks(remarks);
			
			reportUploadLogPFMCRA = reportUploadLogPFMCRAPersistence.update(reportUploadLogPFMCRA);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLogPFMCRA.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogPFMCRA;
	}
	
	public void createAssetForReportUploadLogPFMCRA(ReportUploadLogPFMCRA reportUploadLogPFMCRA, ServiceContext serviceContext, long CreatedBy) {
			
			
			if(Validator.isNotNull(reportUploadLogPFMCRA)) {
				AssetEntry assetEntry = null;
				try {
					assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
		                    serviceContext.getScopeGroupId(),
		                    new Date(),
		                    new Date(),
		                    ReportUploadLogPFMCRA.class.getName(),
		                    reportUploadLogPFMCRA.getReportUploadLogId(),
		                    reportUploadLogPFMCRA.getUuid(),
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
		                    "Report Upload Log with Id: " +  reportUploadLogPFMCRA.getReportUploadLogId(),
		                    null,
		                    null,
		                    null,
		                    0,
		                    0,
		                    null);
	
					Indexer<ReportUploadLogPFMCRA> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogPFMCRA.class);
					indexer.reindex(reportUploadLogPFMCRA);
	
					WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
							ReportUploadLogPFMCRA.class.getName(), reportUploadLogPFMCRA.getReportUploadLogId(), reportUploadLogPFMCRA, serviceContext);
					
					
	
				} catch (Exception e) {
					 _log.error(e);
				}
			}	
			
		}
	public ReportUploadLogPFMCRA updateReportUploadLogPFMCRAStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogPFMCRA reportUploadLogPFMCRA = null;
		try {
			reportUploadLogPFMCRA = reportUploadLogPFMCRAPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogPFMCRA)) {
			reportUploadLogPFMCRA.setStatus(status);
			reportUploadLogPFMCRA.setStatusByUserId(userId);
			reportUploadLogPFMCRA.setStatusDate(new Date());
			reportUploadLogPFMCRA.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogPFMCRA.setStatusByUserName(user.getFullName());
				reportUploadLogPFMCRA.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogPFMCRA = reportUploadLogPFMCRAPersistence.update(reportUploadLogPFMCRA);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogPFMCRA.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogPFMCRA.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogPFMCRA;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogPFMCRA.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogPFMCRAByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMCRAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogPFMCRALocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMCRAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMCRALocalService.dynamicQueryCount(query);
	}
	
	public List<ReportUploadLogPFMCRA> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMCRAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMCRALocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogPFMCRALocalServiceImpl.class);
}