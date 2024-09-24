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
import com.daily.average.service.model.ReportUploadLogCRAAM;
import com.daily.average.service.model.impl.ReportUploadLogCRAAMImpl;
import com.daily.average.service.service.base.ReportUploadLogCRAAMLocalServiceBaseImpl;
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
public class ReportUploadLogCRAAMLocalServiceImpl
	extends ReportUploadLogCRAAMLocalServiceBaseImpl {
	public ReportUploadLogCRAAM addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogCRAAM reportUploadLog = reportUploadLogCRAAMPersistence.create(counterLocalService.increment(ReportUploadLogCRAAM.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogCRAAMPersistence.update(reportUploadLog);
	}
	
	public ReportUploadLogCRAAM fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogCRAAMPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	public List<ReportUploadLogCRAAM> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogCRAAMPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public ReportUploadLogCRAAM updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		ReportUploadLogCRAAM reportUploadLogCRAAM = null;
		try {
			reportUploadLogCRAAM = reportUploadLogCRAAMLocalService.getReportUploadLogCRAAM(reportUploadLogId);
			_log.info(reportUploadLogCRAAM);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogCRAAM)) {
			reportUploadLogCRAAM = reportUploadLogCRAAMLocalService.createReportUploadLogCRAAM(reportUploadLogId);
			reportUploadLogCRAAM.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogCRAAM.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogCRAAM.setCreateDate(createDate);
			reportUploadLogCRAAM.setCreatedBy(createdBy);
			reportUploadLogCRAAM.setFileEntryId(fileEntryId);
			reportUploadLogCRAAM.setUploaded(uploaded);
			
			reportUploadLogCRAAM.setStatus(status);
			reportUploadLogCRAAM.setStatusByUserId(statusByUserId);
			reportUploadLogCRAAM.setStatusByUserName(statusByUserName);
			reportUploadLogCRAAM.setStatusDate(statusDate);
			reportUploadLogCRAAM.setRemarks(remarks);
			
			reportUploadLogCRAAM = reportUploadLogCRAAMPersistence.update(reportUploadLogCRAAM);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogCRAAM(reportUploadLogCRAAM, serviceContext, createdBy);
		}else {
			reportUploadLogCRAAM.setCreateDate(createDate);
			reportUploadLogCRAAM.setCreatedBy(createdBy);
			reportUploadLogCRAAM.setFileEntryId(fileEntryId);
			reportUploadLogCRAAM.setUploaded(uploaded);
			
			reportUploadLogCRAAM.setStatus(status);
			reportUploadLogCRAAM.setStatusByUserId(statusByUserId);
			reportUploadLogCRAAM.setStatusByUserName(statusByUserName);
			reportUploadLogCRAAM.setStatusDate(statusDate);
			reportUploadLogCRAAM.setRemarks(remarks);
			
			reportUploadLogCRAAM = reportUploadLogCRAAMPersistence.update(reportUploadLogCRAAM);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLogCRAAM.getWorkflowContext();
			try {
				workflowAssignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogCRAAM;
	}
	
	public void createAssetForReportUploadLogCRAAM(ReportUploadLogCRAAM reportUploadLogCRAAM, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(reportUploadLogCRAAM)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    ReportUploadLogCRAAM.class.getName(),
	                    reportUploadLogCRAAM.getReportUploadLogId(),
	                    reportUploadLogCRAAM.getUuid(),
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
	                    "Report Upload Log with Id: " +  reportUploadLogCRAAM.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<ReportUploadLogCRAAM> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogCRAAM.class);
				indexer.reindex(reportUploadLogCRAAM);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						ReportUploadLogCRAAM.class.getName(), reportUploadLogCRAAM.getReportUploadLogId(), reportUploadLogCRAAM, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public ReportUploadLogCRAAM updateReportUploadLogCRAAMStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogCRAAM reportUploadLogCRAAM = null;
		try {
			reportUploadLogCRAAM = reportUploadLogCRAAMPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogCRAAM)) {
			reportUploadLogCRAAM.setStatus(status);
			reportUploadLogCRAAM.setStatusByUserId(userId);
			reportUploadLogCRAAM.setStatusDate(new Date());
			reportUploadLogCRAAM.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogCRAAM.setStatusByUserName(user.getFullName());
				reportUploadLogCRAAM.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogCRAAM = reportUploadLogCRAAMPersistence.update(reportUploadLogCRAAM);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogCRAAM.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogCRAAM.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogCRAAM;
	}
	
	public void workflowAssignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogCRAAM.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogCRAAMByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCRAAMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogCRAAMLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCRAAMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogCRAAMLocalService.dynamicQueryCount(query);
	}
	
	public List< ReportUploadLogCRAAM> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogCRAAMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogCRAAMLocalService.dynamicQuery(query);
	}
	
	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogCRAAMLocalServiceImpl.class);
}