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
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.impl.ReportUploadLogCustodianImpl;
import com.daily.average.service.service.base.ReportUploadLogCustodianLocalServiceBaseImpl;
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
public class ReportUploadLogCustodianLocalServiceImpl
	extends ReportUploadLogCustodianLocalServiceBaseImpl {

	public  ReportUploadLogCustodian addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		 ReportUploadLogCustodian reportUploadLog = reportUploadLogCustodianPersistence.create(counterLocalService.increment( ReportUploadLogCustodian.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogCustodianPersistence.update(reportUploadLog);
	}
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @return
	 */
	public  ReportUploadLogCustodian fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogCustodianPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	
	public List< ReportUploadLogCustodian> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogCustodianPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public  ReportUploadLogCustodian updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, String fileList) {
		 ReportUploadLogCustodian reportUploadLogCustodian = null;
		try {
			reportUploadLogCustodian = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(reportUploadLogId);
			_log.info(reportUploadLogCustodian);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogCustodian)) {
			reportUploadLogCustodian = reportUploadLogCustodianLocalService.createReportUploadLogCustodian(reportUploadLogId);			
			reportUploadLogCustodian.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogCustodian.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogCustodian.setCreateDate(createDate);
			reportUploadLogCustodian.setCreatedBy(createdBy);
			reportUploadLogCustodian.setFileEntryId(fileEntryId);
			reportUploadLogCustodian.setUploaded(uploaded);
			
			reportUploadLogCustodian.setStatus(status);
			reportUploadLogCustodian.setStatusByUserId(statusByUserId);
			reportUploadLogCustodian.setStatusByUserName(statusByUserName);
			reportUploadLogCustodian.setStatusDate(statusDate);
			reportUploadLogCustodian.setRemarks(remarks);
			
			reportUploadLogCustodian = reportUploadLogCustodianPersistence.update(reportUploadLogCustodian);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			createAssetForReportUploadLogCustodian(reportUploadLogCustodian, serviceContext, createdBy);
		}else {
			reportUploadLogCustodian.setCreateDate(createDate);
			reportUploadLogCustodian.setCreatedBy(createdBy);
			reportUploadLogCustodian.setFileEntryId(fileEntryId);
			reportUploadLogCustodian.setUploaded(uploaded);
			
			reportUploadLogCustodian.setStatus(status);
			reportUploadLogCustodian.setStatusByUserId(statusByUserId);
			reportUploadLogCustodian.setStatusByUserName(statusByUserName);
			reportUploadLogCustodian.setStatusDate(statusDate);
			reportUploadLogCustodian.setRemarks(remarks);
			
			reportUploadLogCustodian = reportUploadLogCustodianPersistence.update(reportUploadLogCustodian);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			String workflowContext = reportUploadLogCustodian.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogCustodian;
	}
	
	public void createAssetForReportUploadLogCustodian( ReportUploadLogCustodian reportUploadLogCustodian, ServiceContext serviceContext, long CreatedBy) {
			
			
			if(Validator.isNotNull(reportUploadLogCustodian)) {
				AssetEntry assetEntry = null;
				try {
					assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
		                    serviceContext.getScopeGroupId(),
		                    new Date(),
		                    new Date(),
		                     ReportUploadLogCustodian.class.getName(),
		                    reportUploadLogCustodian.getReportUploadLogId(),
		                    reportUploadLogCustodian.getUuid(),
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
		                    "Report Upload Log with Id: " +  reportUploadLogCustodian.getReportUploadLogId(),
		                    null,
		                    null,
		                    null,
		                    0,
		                    0,
		                    null);
	
					Indexer< ReportUploadLogCustodian> indexer = IndexerRegistryUtil.nullSafeGetIndexer( ReportUploadLogCustodian.class);
					indexer.reindex(reportUploadLogCustodian);
	
					WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
							 ReportUploadLogCustodian.class.getName(), reportUploadLogCustodian.getReportUploadLogId(), reportUploadLogCustodian, serviceContext);
					
					
	
				} catch (Exception e) {
					 _log.error(e);
				}
			}	
			
		}
	public  ReportUploadLogCustodian updateReportUploadLogCustodianStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		 ReportUploadLogCustodian reportUploadLogCustodian = null;
		try {
			reportUploadLogCustodian = reportUploadLogCustodianPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogCustodian)) {
			reportUploadLogCustodian.setStatus(status);
			reportUploadLogCustodian.setStatusByUserId(userId);
			reportUploadLogCustodian.setStatusDate(new Date());
			reportUploadLogCustodian.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogCustodian.setStatusByUserName(user.getFullName());
				reportUploadLogCustodian.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogCustodian = reportUploadLogCustodianPersistence.update(reportUploadLogCustodian);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry( ReportUploadLogCustodian.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible( ReportUploadLogCustodian.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogCustodian;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId,  ReportUploadLogCustodian.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogCustodianByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCustodianImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogCustodianLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCustodianImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogCustodianLocalService.dynamicQueryCount(query);
	}
	
	public List< ReportUploadLogCustodian> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCustodianImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogCustodianLocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogCustodianLocalServiceImpl.class);

}