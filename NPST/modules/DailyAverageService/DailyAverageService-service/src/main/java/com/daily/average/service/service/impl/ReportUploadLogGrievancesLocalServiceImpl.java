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
import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.model.impl.ReportUploadLogGrievancesImpl;
import com.daily.average.service.service.base.ReportUploadLogGrievancesLocalServiceBaseImpl;
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
public class ReportUploadLogGrievancesLocalServiceImpl
	extends ReportUploadLogGrievancesLocalServiceBaseImpl {
	
	public  ReportUploadLogGrievances addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, 
			long fileEntryId, boolean uploaded) {
		ReportUploadLogGrievances reportUploadLog = reportUploadLogGrievancesPersistence.create(counterLocalService.increment(ReportUploadLogGrievances.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogGrievancesPersistence.update(reportUploadLog);
	}
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @return
	 */
	public  ReportUploadLogGrievances fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogGrievancesPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	
	public List< ReportUploadLogGrievances> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogGrievancesPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public  ReportUploadLogGrievances updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		 ReportUploadLogGrievances reportUploadLogGrievances = null;
		try {
			reportUploadLogGrievances = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(reportUploadLogId);
			_log.info(reportUploadLogGrievances);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogGrievances)) {
			reportUploadLogGrievances = reportUploadLogGrievancesLocalService.createReportUploadLogGrievances(reportUploadLogId);			
			reportUploadLogGrievances.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogGrievances.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogGrievances.setCreateDate(createDate);
			reportUploadLogGrievances.setCreatedBy(createdBy);
			reportUploadLogGrievances.setFileEntryId(fileEntryId);
			reportUploadLogGrievances.setUploaded(uploaded);
			
			reportUploadLogGrievances.setStatus(status);
			reportUploadLogGrievances.setStatusByUserId(statusByUserId);
			reportUploadLogGrievances.setStatusByUserName(statusByUserName);
			reportUploadLogGrievances.setStatusDate(statusDate);
			reportUploadLogGrievances.setRemarks(remarks);
			
			reportUploadLogGrievances = reportUploadLogGrievancesPersistence.update(reportUploadLogGrievances);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogGrievances(reportUploadLogGrievances, serviceContext, createdBy);
		}else {
			reportUploadLogGrievances.setCreateDate(createDate);
			reportUploadLogGrievances.setCreatedBy(createdBy);
			reportUploadLogGrievances.setFileEntryId(fileEntryId);
			reportUploadLogGrievances.setUploaded(uploaded);
			
			reportUploadLogGrievances.setStatus(status);
			reportUploadLogGrievances.setStatusByUserId(statusByUserId);
			reportUploadLogGrievances.setStatusByUserName(statusByUserName);
			reportUploadLogGrievances.setStatusDate(statusDate);
			reportUploadLogGrievances.setRemarks(remarks);
			
			reportUploadLogGrievances = reportUploadLogGrievancesPersistence.update(reportUploadLogGrievances);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLogGrievances.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogGrievances;
	}
	
	public void createAssetForReportUploadLogGrievances(ReportUploadLogGrievances reportUploadLogGrievances, ServiceContext serviceContext, long CreatedBy) {
			
			
			if(Validator.isNotNull(reportUploadLogGrievances)) {
				AssetEntry assetEntry = null;
				try {
					assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
		                    serviceContext.getScopeGroupId(),
		                    new Date(),
		                    new Date(),
		                     ReportUploadLogGrievances.class.getName(),
		                    reportUploadLogGrievances.getReportUploadLogId(),
		                    reportUploadLogGrievances.getUuid(),
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
		                    "Report Upload Log with Id: " +  reportUploadLogGrievances.getReportUploadLogId(),
		                    null,
		                    null,
		                    null,
		                    0,
		                    0,
		                    null);
	
					Indexer< ReportUploadLogGrievances> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogGrievances.class);
					indexer.reindex(reportUploadLogGrievances);
	
					WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
							 ReportUploadLogGrievances.class.getName(), reportUploadLogGrievances.getReportUploadLogId(), reportUploadLogGrievances, serviceContext);
					
					
	
				} catch (Exception e) {
					 _log.error(e);
				}
			}	
			
		}
	public  ReportUploadLogGrievances updateReportUploadLogGrievancesStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		 ReportUploadLogGrievances reportUploadLogGrievances = null;
		try {
			reportUploadLogGrievances = reportUploadLogGrievancesPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogGrievances)) {
			reportUploadLogGrievances.setStatus(status);
			reportUploadLogGrievances.setStatusByUserId(userId);
			reportUploadLogGrievances.setStatusDate(new Date());
			reportUploadLogGrievances.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogGrievances.setStatusByUserName(user.getFullName());
				reportUploadLogGrievances.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogGrievances = reportUploadLogGrievancesPersistence.update(reportUploadLogGrievances);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry( ReportUploadLogGrievances.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible( ReportUploadLogGrievances.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogGrievances;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId,  ReportUploadLogGrievances.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogGrievancesByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogGrievancesImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogGrievancesLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogGrievancesImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogGrievancesLocalService.dynamicQueryCount(query);
	}
	
	public List< ReportUploadLogGrievances> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogGrievancesImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogGrievancesLocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogGrievancesLocalServiceImpl.class);
}