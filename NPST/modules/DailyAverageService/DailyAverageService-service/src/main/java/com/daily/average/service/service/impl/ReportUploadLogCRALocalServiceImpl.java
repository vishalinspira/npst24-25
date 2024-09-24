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
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.impl.ReportUploadLogCRAImpl;
import com.daily.average.service.model.impl.ReportUploadLogPFMAMImpl;
import com.daily.average.service.service.base.ReportUploadLogCRALocalServiceBaseImpl;
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
public class ReportUploadLogCRALocalServiceImpl extends ReportUploadLogCRALocalServiceBaseImpl {
	
	public ReportUploadLogCRA addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogCRA reportUploadLog = reportUploadLogCRAPersistence.create(counterLocalService.increment(ReportUploadLogCRA.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogCRAPersistence.update(reportUploadLog);
	}
	
	public ReportUploadLogCRA fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogCRAPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	public List<ReportUploadLogCRA> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogCRAPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public ReportUploadLogCRA updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		ReportUploadLogCRA reportUploadLogCRA = null;
		try {
			reportUploadLogCRA = reportUploadLogCRALocalService.getReportUploadLogCRA(reportUploadLogId);
			_log.info(reportUploadLogCRA);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogCRA)) {
			reportUploadLogCRA = reportUploadLogCRALocalService.createReportUploadLogCRA(reportUploadLogId);
			reportUploadLogCRA.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogCRA.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogCRA.setCreateDate(createDate);
			reportUploadLogCRA.setCreatedBy(createdBy);
			reportUploadLogCRA.setFileEntryId(fileEntryId);
			reportUploadLogCRA.setUploaded(uploaded);
			
			reportUploadLogCRA.setStatus(status);
			reportUploadLogCRA.setStatusByUserId(statusByUserId);
			reportUploadLogCRA.setStatusByUserName(statusByUserName);
			reportUploadLogCRA.setStatusDate(statusDate);
			reportUploadLogCRA.setRemarks(remarks);
			
			reportUploadLogCRA = reportUploadLogCRAPersistence.update(reportUploadLogCRA);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogCRA(reportUploadLogCRA, serviceContext, createdBy);
		}else {
			reportUploadLogCRA.setCreateDate(createDate);
			reportUploadLogCRA.setCreatedBy(createdBy);
			reportUploadLogCRA.setFileEntryId(fileEntryId);
			reportUploadLogCRA.setUploaded(uploaded);
			
			reportUploadLogCRA.setStatus(status);
			reportUploadLogCRA.setStatusByUserId(statusByUserId);
			reportUploadLogCRA.setStatusByUserName(statusByUserName);
			reportUploadLogCRA.setStatusDate(statusDate);
			reportUploadLogCRA.setRemarks(remarks);
			
			reportUploadLogCRA = reportUploadLogCRAPersistence.update(reportUploadLogCRA);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLogCRA.getWorkflowContext();
			try {
				workflowAssignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogCRA;
	}
	
	public void createAssetForReportUploadLogCRA(ReportUploadLogCRA reportUploadLogCRA, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(reportUploadLogCRA)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    ReportUploadLogCRA.class.getName(),
	                    reportUploadLogCRA.getReportUploadLogId(),
	                    reportUploadLogCRA.getUuid(),
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
	                    "Report Upload Log with Id: " +  reportUploadLogCRA.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<ReportUploadLogCRA> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogCRA.class);
				indexer.reindex(reportUploadLogCRA);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						ReportUploadLogCRA.class.getName(), reportUploadLogCRA.getReportUploadLogId(), reportUploadLogCRA, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public ReportUploadLogCRA updateReportUploadLogCRAStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogCRA reportUploadLogCRA = null;
		try {
			reportUploadLogCRA = reportUploadLogCRAPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogCRA)) {
			reportUploadLogCRA.setStatus(status);
			reportUploadLogCRA.setStatusByUserId(userId);
			reportUploadLogCRA.setStatusDate(new Date());
			reportUploadLogCRA.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogCRA.setStatusByUserName(user.getFullName());
				reportUploadLogCRA.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogCRA = reportUploadLogCRAPersistence.update(reportUploadLogCRA);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogCRA.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogCRA.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogCRA;
	}
	
	public void workflowAssignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogCRA.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogCRAByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCRAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogCRALocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCRAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogCRALocalService.dynamicQueryCount(query);
	}
	
	public List< ReportUploadLogCRA> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCRAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogCRALocalService.dynamicQuery(query);
	}
	
	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogCRALocalServiceImpl.class);
	
	
}