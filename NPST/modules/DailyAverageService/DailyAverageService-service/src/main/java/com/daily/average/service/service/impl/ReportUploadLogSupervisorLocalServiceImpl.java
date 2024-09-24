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
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.model.impl.ReportUploadLogMakerImpl;
import com.daily.average.service.model.impl.ReportUploadLogSupervisorImpl;
import com.daily.average.service.service.base.ReportUploadLogSupervisorLocalServiceBaseImpl;
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
public class ReportUploadLogSupervisorLocalServiceImpl
	extends ReportUploadLogSupervisorLocalServiceBaseImpl {
	public ReportUploadLogSupervisor addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogSupervisor reportUploadLog = reportUploadLogSupervisorPersistence.create(counterLocalService.increment(ReportUploadLogSupervisor.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogSupervisorPersistence.update(reportUploadLog);
	}
	
	public ReportUploadLogSupervisor fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogSupervisorPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	public List<ReportUploadLogSupervisor> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogSupervisorPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	

	
	public ReportUploadLogSupervisor updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		ReportUploadLogSupervisor reportUploadLog = null;
		
		try {
			reportUploadLog = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog2 = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLog)) {
			reportUploadLog = reportUploadLogSupervisorLocalService.createReportUploadLogSupervisor(reportUploadLogId);			
			reportUploadLog.setReportMasterId(reportUploadLog2.getReportMasterId());
			reportUploadLog.setReportDate(reportUploadLog2.getReportDate());
			reportUploadLog.setCreateDate(createDate);
			reportUploadLog.setCreatedBy(createdBy);
			reportUploadLog.setFileEntryId(fileEntryId);
			reportUploadLog.setUploaded(uploaded);
			
			reportUploadLog.setStatus(status);
			reportUploadLog.setStatusByUserId(statusByUserId);
			reportUploadLog.setStatusByUserName(statusByUserName);
			reportUploadLog.setStatusDate(statusDate);
			reportUploadLog.setRemarks(remarks);
			
			reportUploadLog = reportUploadLogSupervisorPersistence.update(reportUploadLog);
			reportUploadLog2.setUploaded_i(1);
			
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog2);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogSupervisor(reportUploadLog, serviceContext, createdBy);
		}else {
			reportUploadLog.setCreateDate(createDate);
			reportUploadLog.setCreatedBy(createdBy);
			reportUploadLog.setFileEntryId(fileEntryId);
			reportUploadLog.setUploaded(uploaded);
			
			reportUploadLog.setStatus(status);
			reportUploadLog.setStatusByUserId(statusByUserId);
			reportUploadLog.setStatusByUserName(statusByUserName);
			reportUploadLog.setStatusDate(statusDate);
			reportUploadLog.setRemarks(remarks);
			
			reportUploadLog = reportUploadLogSupervisorPersistence.update(reportUploadLog);
			reportUploadLog2.setUploaded_i(1);
			
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog2);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLog.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLog;
	}
	
	public void createAssetForReportUploadLogSupervisor(ReportUploadLogSupervisor ReportUploadLogSupervisor, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(ReportUploadLogSupervisor)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    ReportUploadLogSupervisor.class.getName(),
	                    ReportUploadLogSupervisor.getReportUploadLogId(),
	                    ReportUploadLogSupervisor.getUuid(),
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
	                    "Report Upload Log with Id: " +  ReportUploadLogSupervisor.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<ReportUploadLogSupervisor> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogSupervisor.class);
				indexer.reindex(ReportUploadLogSupervisor);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						ReportUploadLogSupervisor.class.getName(), ReportUploadLogSupervisor.getReportUploadLogId(), ReportUploadLogSupervisor, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public ReportUploadLogSupervisor updateReportUploadLogSupervisorStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogSupervisor ReportUploadLogSupervisor = null;
		try {
			ReportUploadLogSupervisor = reportUploadLogSupervisorPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(ReportUploadLogSupervisor)) {
			ReportUploadLogSupervisor.setStatus(status);
			ReportUploadLogSupervisor.setStatusByUserId(userId);
			ReportUploadLogSupervisor.setStatusDate(new Date());
			ReportUploadLogSupervisor.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				ReportUploadLogSupervisor.setStatusByUserName(user.getFullName());
				ReportUploadLogSupervisor.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			ReportUploadLogSupervisor = reportUploadLogSupervisorPersistence.update(ReportUploadLogSupervisor);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogSupervisor.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogSupervisor.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return ReportUploadLogSupervisor;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogSupervisor.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = 0;
		if(workflowTasks != null && !workflowTasks.isEmpty())
			workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}

	
	public long countReportUploadLogSupervisorByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogSupervisorImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogSupervisorLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogSupervisorImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogSupervisorLocalService.dynamicQueryCount(query);
	}
	
	public List<ReportUploadLogSupervisor> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogSupervisorImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogSupervisorLocalService.dynamicQuery(query);
	}

	Log _log = LogFactoryUtil.getLog(getClass());
}