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
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.impl.ReportUploadLogImpl;
import com.daily.average.service.model.impl.ReportUploadLogMakerImpl;
import com.daily.average.service.service.base.ReportUploadLogMakerLocalServiceBaseImpl;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
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
public class ReportUploadLogMakerLocalServiceImpl extends ReportUploadLogMakerLocalServiceBaseImpl {
	
	public ReportUploadLogMaker addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogMaker reportUploadLog = reportUploadLogMakerPersistence.create(counterLocalService.increment(ReportUploadLogMaker.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogMakerPersistence.update(reportUploadLog);
	}
	
	public ReportUploadLogMaker fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogMakerPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	public List<ReportUploadLogMaker> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogMakerPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	/**
	 * 
	 * @param createDate
	 * @param createdBy
	 * @param fileEntryId
	 * @param uploaded
	 * @param reportUploadLogId
	 * @return
	 */
	/*public ReportUploadLogMaker updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId) {
		ReportUploadLogMaker reportUploadLog = reportUploadLogMakerPersistence.fetchByPrimaryKey(reportUploadLogId);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogMakerPersistence.update(reportUploadLog);
	}*/
	
	public ReportUploadLogMaker updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		ReportUploadLogMaker reportUploadLogMaker = null;
		try {
			reportUploadLogMaker = reportUploadLogMakerLocalService.getReportUploadLogMaker(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogMaker)) {
			reportUploadLogMaker = reportUploadLogMakerLocalService.createReportUploadLogMaker(reportUploadLogId);			
			reportUploadLogMaker.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogMaker.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogMaker.setCreateDate(createDate);
			reportUploadLogMaker.setCreatedBy(createdBy);
			reportUploadLogMaker.setFileEntryId(fileEntryId);
			reportUploadLogMaker.setUploaded(uploaded);
			reportUploadLogMaker.setStatus(status);
			reportUploadLogMaker.setStatusByUserId(statusByUserId);
			reportUploadLogMaker.setStatusByUserName(statusByUserName);
			reportUploadLogMaker.setStatusDate(statusDate);
			reportUploadLogMaker.setRemarks(remarks);
			
			reportUploadLogMaker = reportUploadLogMakerPersistence.update(reportUploadLogMaker);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogMaker(reportUploadLogMaker, serviceContext, createdBy);
		}else {
			reportUploadLogMaker.setCreateDate(createDate);
			reportUploadLogMaker.setCreatedBy(createdBy);
			reportUploadLogMaker.setFileEntryId(fileEntryId);
			reportUploadLogMaker.setUploaded(uploaded);
			
			reportUploadLogMaker.setStatus(status);
			reportUploadLogMaker.setStatusByUserId(statusByUserId);
			reportUploadLogMaker.setStatusByUserName(statusByUserName);
			reportUploadLogMaker.setStatusDate(statusDate);
			reportUploadLogMaker.setRemarks(remarks);
			
			reportUploadLogMaker = reportUploadLogMakerPersistence.update(reportUploadLogMaker);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLogMaker.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogMaker;
	}
	
	public void createAssetForReportUploadLogMaker(ReportUploadLogMaker reportUploadLogMaker, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(reportUploadLogMaker)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    ReportUploadLogMaker.class.getName(),
	                    reportUploadLogMaker.getReportUploadLogId(),
	                    reportUploadLogMaker.getUuid(),
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
	                    "Report Upload Log with Id: " +  reportUploadLogMaker.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<ReportUploadLogMaker> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogMaker.class);
				indexer.reindex(reportUploadLogMaker);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						ReportUploadLogMaker.class.getName(), reportUploadLogMaker.getReportUploadLogId(), reportUploadLogMaker, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public ReportUploadLogMaker updateReportUploadLogMakerStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogMaker reportUploadLogMaker = null;
		try {
			reportUploadLogMaker = reportUploadLogMakerPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogMaker)) {
			reportUploadLogMaker.setStatus(status);
			reportUploadLogMaker.setStatusByUserId(userId);
			reportUploadLogMaker.setStatusDate(new Date());
			reportUploadLogMaker.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogMaker.setStatusByUserName(user.getFullName());
				reportUploadLogMaker.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogMaker = reportUploadLogMakerPersistence.update(reportUploadLogMaker);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogMaker.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogMaker.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogMaker;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogMaker.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	Log _log = LogFactoryUtil.getLog(ReportUploadLogMakerLocalServiceImpl.class);
	
	public long countReportUploadLogMakerByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogMakerImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogMakerLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogMakerImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogMakerLocalService.dynamicQueryCount(query);
	}
	
	public List<ReportUploadLogMaker> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogMakerImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogMakerLocalService.dynamicQuery(query);
	}
	
}