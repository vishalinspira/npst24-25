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
import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
import com.daily.average.service.model.impl.ReportUploadLogPFMAMPFRDAImpl;
import com.daily.average.service.service.base.ReportUploadLogPFMAMPFRDALocalServiceBaseImpl;
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
public class ReportUploadLogPFMAMPFRDALocalServiceImpl
	extends ReportUploadLogPFMAMPFRDALocalServiceBaseImpl {
	public ReportUploadLogPFMAMPFRDA addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogPFMAMPFRDA reportUploadLog = reportUploadLogPFMAMPFRDAPersistence.create(counterLocalService.increment(ReportUploadLogPFMAMPFRDA.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogPFMAMPFRDAPersistence.update(reportUploadLog);
	}
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @return
	 */
	public ReportUploadLogPFMAMPFRDA fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogPFMAMPFRDAPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	
	public List<ReportUploadLogPFMAMPFRDA> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogPFMAMPFRDAPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public ReportUploadLogPFMAMPFRDA updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, String fileList) {
		ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
		try {
			reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(reportUploadLogId);
			_log.info(reportUploadLogPFMAMPFRDA);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogPFMAMPFRDA)) {
			reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.createReportUploadLogPFMAMPFRDA(reportUploadLogId);			
			reportUploadLogPFMAMPFRDA.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogPFMAMPFRDA.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogPFMAMPFRDA.setCreateDate(createDate);
			reportUploadLogPFMAMPFRDA.setCreatedBy(createdBy);
			reportUploadLogPFMAMPFRDA.setFileEntryId(fileEntryId);
			reportUploadLogPFMAMPFRDA.setUploaded(uploaded);
			
			reportUploadLogPFMAMPFRDA.setStatus(status);
			reportUploadLogPFMAMPFRDA.setStatusByUserId(statusByUserId);
			reportUploadLogPFMAMPFRDA.setStatusByUserName(statusByUserName);
			reportUploadLogPFMAMPFRDA.setStatusDate(statusDate);
			reportUploadLogPFMAMPFRDA.setRemarks(remarks);
			
			reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDAPersistence.update(reportUploadLogPFMAMPFRDA);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			createAssetForReportUploadLogPFMAMPFRDA(reportUploadLogPFMAMPFRDA, serviceContext, createdBy);
		}else {
			reportUploadLogPFMAMPFRDA.setCreateDate(createDate);
			reportUploadLogPFMAMPFRDA.setCreatedBy(createdBy);
			reportUploadLogPFMAMPFRDA.setFileEntryId(fileEntryId);
			reportUploadLogPFMAMPFRDA.setUploaded(uploaded);
			
			reportUploadLogPFMAMPFRDA.setStatus(status);
			reportUploadLogPFMAMPFRDA.setStatusByUserId(statusByUserId);
			reportUploadLogPFMAMPFRDA.setStatusByUserName(statusByUserName);
			reportUploadLogPFMAMPFRDA.setStatusDate(statusDate);
			reportUploadLogPFMAMPFRDA.setRemarks(remarks);
			
			reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDAPersistence.update(reportUploadLogPFMAMPFRDA);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			String workflowContext = reportUploadLogPFMAMPFRDA.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogPFMAMPFRDA;
	}
	
	public void createAssetForReportUploadLogPFMAMPFRDA(ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA, ServiceContext serviceContext, long CreatedBy) {
			
			
			if(Validator.isNotNull(reportUploadLogPFMAMPFRDA)) {
				AssetEntry assetEntry = null;
				try {
					assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
		                    serviceContext.getScopeGroupId(),
		                    new Date(),
		                    new Date(),
		                    ReportUploadLogPFMAMPFRDA.class.getName(),
		                    reportUploadLogPFMAMPFRDA.getReportUploadLogId(),
		                    reportUploadLogPFMAMPFRDA.getUuid(),
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
		                    "Report Upload Log with Id: " +  reportUploadLogPFMAMPFRDA.getReportUploadLogId(),
		                    null,
		                    null,
		                    null,
		                    0,
		                    0,
		                    null);
	
					Indexer<ReportUploadLogPFMAMPFRDA> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogPFMAMPFRDA.class);
					indexer.reindex(reportUploadLogPFMAMPFRDA);
	
					WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
							ReportUploadLogPFMAMPFRDA.class.getName(), reportUploadLogPFMAMPFRDA.getReportUploadLogId(), reportUploadLogPFMAMPFRDA, serviceContext);
					
					
	
				} catch (Exception e) {
					 _log.error(e);
				}
			}	
			
		}
	public ReportUploadLogPFMAMPFRDA updateReportUploadLogPFMAMPFRDAStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
		try {
			reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDAPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogPFMAMPFRDA)) {
			reportUploadLogPFMAMPFRDA.setStatus(status);
			reportUploadLogPFMAMPFRDA.setStatusByUserId(userId);
			reportUploadLogPFMAMPFRDA.setStatusDate(new Date());
			reportUploadLogPFMAMPFRDA.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogPFMAMPFRDA.setStatusByUserName(user.getFullName());
				reportUploadLogPFMAMPFRDA.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDAPersistence.update(reportUploadLogPFMAMPFRDA);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogPFMAMPFRDA.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogPFMAMPFRDA.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogPFMAMPFRDA;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogPFMAMPFRDA.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogPFMAMPFRDAByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMAMPFRDAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogPFMAMPFRDALocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMAMPFRDAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMAMPFRDALocalService.dynamicQueryCount(query);
	}
	
	public List<ReportUploadLogPFMAMPFRDA> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMAMPFRDAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMAMPFRDALocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogPFMAMPFRDALocalServiceImpl.class);
}