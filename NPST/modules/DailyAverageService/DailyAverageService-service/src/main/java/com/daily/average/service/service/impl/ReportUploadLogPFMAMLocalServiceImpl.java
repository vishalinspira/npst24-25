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
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.impl.ReportUploadLogPFMAMImpl;
import com.daily.average.service.service.base.ReportUploadLogPFMAMLocalServiceBaseImpl;
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
public class ReportUploadLogPFMAMLocalServiceImpl
	extends ReportUploadLogPFMAMLocalServiceBaseImpl {
	public  ReportUploadLogPFMAM addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		 ReportUploadLogPFMAM reportUploadLog = reportUploadLogPFMAMPersistence.create(counterLocalService.increment( ReportUploadLogPFMAM.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogPFMAMPersistence.update(reportUploadLog);
	}
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @return
	 */
	public  ReportUploadLogPFMAM fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogPFMAMPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	
	public List< ReportUploadLogPFMAM> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogPFMAMPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public  ReportUploadLogPFMAM updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		 ReportUploadLogPFMAM reportUploadLogPFMAM = null;
		try {
			reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(reportUploadLogId);
			_log.info(reportUploadLogPFMAM);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogPFMAM)) {
			reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.createReportUploadLogPFMAM(reportUploadLogId);			
			reportUploadLogPFMAM.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogPFMAM.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogPFMAM.setCreateDate(createDate);
			reportUploadLogPFMAM.setCreatedBy(createdBy);
			reportUploadLogPFMAM.setFileEntryId(fileEntryId);
			reportUploadLogPFMAM.setUploaded(uploaded);
			
			reportUploadLogPFMAM.setStatus(status);
			reportUploadLogPFMAM.setStatusByUserId(statusByUserId);
			reportUploadLogPFMAM.setStatusByUserName(statusByUserName);
			reportUploadLogPFMAM.setStatusDate(statusDate);
			reportUploadLogPFMAM.setRemarks(remarks);
			
			reportUploadLogPFMAM = reportUploadLogPFMAMPersistence.update(reportUploadLogPFMAM);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogPFMAM(reportUploadLogPFMAM, serviceContext, createdBy);
		}else {
			reportUploadLogPFMAM.setCreateDate(createDate);
			reportUploadLogPFMAM.setCreatedBy(createdBy);
			reportUploadLogPFMAM.setFileEntryId(fileEntryId);
			reportUploadLogPFMAM.setUploaded(uploaded);
			
			reportUploadLogPFMAM.setStatus(status);
			reportUploadLogPFMAM.setStatusByUserId(statusByUserId);
			reportUploadLogPFMAM.setStatusByUserName(statusByUserName);
			reportUploadLogPFMAM.setStatusDate(statusDate);
			reportUploadLogPFMAM.setRemarks(remarks);
			
			reportUploadLogPFMAM = reportUploadLogPFMAMPersistence.update(reportUploadLogPFMAM);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLogPFMAM.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogPFMAM;
	}
	
	public  ReportUploadLogPFMAM updateReportUploadLog2(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, String fileList) {
		 ReportUploadLogPFMAM reportUploadLogPFMAM = null;
		try {
			reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(reportUploadLogId);
			_log.info(reportUploadLogPFMAM);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogPFMAM)) {
			reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.createReportUploadLogPFMAM(reportUploadLogId);			
			reportUploadLogPFMAM.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogPFMAM.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogPFMAM.setCreateDate(createDate);
			reportUploadLogPFMAM.setCreatedBy(createdBy);
			reportUploadLogPFMAM.setFileEntryId(fileEntryId);
			reportUploadLogPFMAM.setUploaded(uploaded);
			
			reportUploadLogPFMAM.setStatus(status);
			reportUploadLogPFMAM.setStatusByUserId(statusByUserId);
			reportUploadLogPFMAM.setStatusByUserName(statusByUserName);
			reportUploadLogPFMAM.setStatusDate(statusDate);
			reportUploadLogPFMAM.setRemarks(remarks);
			
			reportUploadLogPFMAM = reportUploadLogPFMAMPersistence.update(reportUploadLogPFMAM);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			createAssetForReportUploadLogPFMAM(reportUploadLogPFMAM, serviceContext, createdBy);
		}else {
			reportUploadLogPFMAM.setCreateDate(createDate);
			reportUploadLogPFMAM.setCreatedBy(createdBy);
			reportUploadLogPFMAM.setFileEntryId(fileEntryId);
			reportUploadLogPFMAM.setUploaded(uploaded);
			
			reportUploadLogPFMAM.setStatus(status);
			reportUploadLogPFMAM.setStatusByUserId(statusByUserId);
			reportUploadLogPFMAM.setStatusByUserName(statusByUserName);
			reportUploadLogPFMAM.setStatusDate(statusDate);
			reportUploadLogPFMAM.setRemarks(remarks);
			
			reportUploadLogPFMAM = reportUploadLogPFMAMPersistence.update(reportUploadLogPFMAM);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			String workflowContext = reportUploadLogPFMAM.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogPFMAM;
	}
	
	public void createAssetForReportUploadLogPFMAM( ReportUploadLogPFMAM reportUploadLogPFMAM, ServiceContext serviceContext, long CreatedBy) {
			
			
			if(Validator.isNotNull(reportUploadLogPFMAM)) {
				AssetEntry assetEntry = null;
				try {
					assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
		                    serviceContext.getScopeGroupId(),
		                    new Date(),
		                    new Date(),
		                     ReportUploadLogPFMAM.class.getName(),
		                    reportUploadLogPFMAM.getReportUploadLogId(),
		                    reportUploadLogPFMAM.getUuid(),
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
		                    "Report Upload Log with Id: " +  reportUploadLogPFMAM.getReportUploadLogId(),
		                    null,
		                    null,
		                    null,
		                    0,
		                    0,
		                    null);
	
					Indexer< ReportUploadLogPFMAM> indexer = IndexerRegistryUtil.nullSafeGetIndexer( ReportUploadLogPFMAM.class);
					indexer.reindex(reportUploadLogPFMAM);
	
					WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
							 ReportUploadLogPFMAM.class.getName(), reportUploadLogPFMAM.getReportUploadLogId(), reportUploadLogPFMAM, serviceContext);
					
					
	
				} catch (Exception e) {
					 _log.error(e);
				}
			}	
			
		}
	public  ReportUploadLogPFMAM updateReportUploadLogPFMAMStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		 ReportUploadLogPFMAM reportUploadLogPFMAM = null;
		try {
			reportUploadLogPFMAM = reportUploadLogPFMAMPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogPFMAM)) {
			reportUploadLogPFMAM.setStatus(status);
			reportUploadLogPFMAM.setStatusByUserId(userId);
			reportUploadLogPFMAM.setStatusDate(new Date());
			reportUploadLogPFMAM.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogPFMAM.setStatusByUserName(user.getFullName());
				reportUploadLogPFMAM.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogPFMAM = reportUploadLogPFMAMPersistence.update(reportUploadLogPFMAM);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry( ReportUploadLogPFMAM.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible( ReportUploadLogPFMAM.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogPFMAM;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId,  ReportUploadLogPFMAM.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogPFMAMByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogPFMAMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogPFMAMLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogPFMAMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMAMLocalService.dynamicQueryCount(query);
	}
	
	public List< ReportUploadLogPFMAM> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass( ReportUploadLogPFMAMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMAMLocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog( ReportUploadLogPFMAMLocalServiceImpl.class);
}