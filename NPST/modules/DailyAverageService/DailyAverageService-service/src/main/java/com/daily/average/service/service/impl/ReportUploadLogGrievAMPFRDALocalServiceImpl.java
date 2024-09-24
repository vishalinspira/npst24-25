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
import com.daily.average.service.model.ReportUploadLogGrievAMPFRDA;
import com.daily.average.service.model.impl.ReportUploadLogGrievAMPFRDAImpl;
import com.daily.average.service.service.base.ReportUploadLogGrievAMPFRDALocalServiceBaseImpl;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Brian Wing Shun Chan
 */
public class ReportUploadLogGrievAMPFRDALocalServiceImpl
	extends ReportUploadLogGrievAMPFRDALocalServiceBaseImpl {
	
	public ReportUploadLogGrievAMPFRDA addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogGrievAMPFRDA reportUploadLog = reportUploadLogGrievAMPFRDAPersistence.create(counterLocalService.increment(ReportUploadLogGrievAMPFRDA.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogGrievAMPFRDAPersistence.update(reportUploadLog);
	}
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @return
	 */
	public ReportUploadLogGrievAMPFRDA fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogGrievAMPFRDAPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	
	public List<ReportUploadLogGrievAMPFRDA> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogGrievAMPFRDAPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
	}
	
	public ReportUploadLogGrievAMPFRDA updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, String fileList) {
		ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;
		try {
			reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(reportUploadLogId);
			_log.info(reportUploadLogGrievAMPFRDA);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogGrievAMPFRDA)) {
			_log.info("in if condition");
			reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDALocalService.createReportUploadLogGrievAMPFRDA(reportUploadLogId);			
			reportUploadLogGrievAMPFRDA.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogGrievAMPFRDA.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogGrievAMPFRDA.setCreateDate(createDate);
			reportUploadLogGrievAMPFRDA.setCreatedBy(createdBy);
			reportUploadLogGrievAMPFRDA.setFileEntryId(fileEntryId);
			reportUploadLogGrievAMPFRDA.setUploaded(uploaded);
			
			reportUploadLogGrievAMPFRDA.setStatus(status);
			reportUploadLogGrievAMPFRDA.setStatusByUserId(statusByUserId);
			reportUploadLogGrievAMPFRDA.setStatusByUserName(statusByUserName);
			reportUploadLogGrievAMPFRDA.setStatusDate(statusDate);
			reportUploadLogGrievAMPFRDA.setRemarks(remarks);
			
			reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDAPersistence.update(reportUploadLogGrievAMPFRDA);
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			createAssetForReportUploadLogGrievAMPFRDA(reportUploadLogGrievAMPFRDA, serviceContext, createdBy);
		}else {
			_log.info("in if condition");
			reportUploadLogGrievAMPFRDA.setCreateDate(createDate);
			reportUploadLogGrievAMPFRDA.setCreatedBy(createdBy);
			reportUploadLogGrievAMPFRDA.setFileEntryId(fileEntryId);
			reportUploadLogGrievAMPFRDA.setUploaded(uploaded);
			
			reportUploadLogGrievAMPFRDA.setStatus(status);
			reportUploadLogGrievAMPFRDA.setStatusByUserId(statusByUserId);
			reportUploadLogGrievAMPFRDA.setStatusByUserName(statusByUserName);
			reportUploadLogGrievAMPFRDA.setStatusDate(statusDate);
			reportUploadLogGrievAMPFRDA.setRemarks(remarks);
			_log.info("before reportUploadLogGrievAMPFRDAPersistence update");
			reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDAPersistence.update(reportUploadLogGrievAMPFRDA);
			_log.info("after reportUploadLogGrievAMPFRDAPersistence update");
			reportUploadLog.setUploaded_i(1);
			reportUploadLogLocalService.updateReportUploadLog(reportUploadLog);
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			String workflowContext = reportUploadLogGrievAMPFRDA.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogGrievAMPFRDA;
	}
	
	public void createAssetForReportUploadLogGrievAMPFRDA(ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA, ServiceContext serviceContext, long CreatedBy) {
			
			
			if(Validator.isNotNull(reportUploadLogGrievAMPFRDA)) {
				AssetEntry assetEntry = null;
				try {
					assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
		                    serviceContext.getScopeGroupId(),
		                    new Date(),
		                    new Date(),
		                    ReportUploadLogGrievAMPFRDA.class.getName(),
		                    reportUploadLogGrievAMPFRDA.getReportUploadLogId(),
		                    reportUploadLogGrievAMPFRDA.getUuid(),
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
		                    "Report Upload Log with Id: " +  reportUploadLogGrievAMPFRDA.getReportUploadLogId(),
		                    null,
		                    null,
		                    null,
		                    0,
		                    0,
		                    null);
	
					Indexer<ReportUploadLogGrievAMPFRDA> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogGrievAMPFRDA.class);
					indexer.reindex(reportUploadLogGrievAMPFRDA);
	
					WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
							ReportUploadLogGrievAMPFRDA.class.getName(), reportUploadLogGrievAMPFRDA.getReportUploadLogId(), reportUploadLogGrievAMPFRDA, serviceContext);
					
					
	
				} catch (Exception e) {
					 _log.error(e);
				}
			}	
			
		}
	public ReportUploadLogGrievAMPFRDA updateReportUploadLogGrievAMPFRDAStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;
		try {
			reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDAPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogGrievAMPFRDA)) {
			reportUploadLogGrievAMPFRDA.setStatus(status);
			reportUploadLogGrievAMPFRDA.setStatusByUserId(userId);
			reportUploadLogGrievAMPFRDA.setStatusDate(new Date());
			reportUploadLogGrievAMPFRDA.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogGrievAMPFRDA.setStatusByUserName(user.getFullName());
				reportUploadLogGrievAMPFRDA.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogGrievAMPFRDA = reportUploadLogGrievAMPFRDAPersistence.update(reportUploadLogGrievAMPFRDA);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogGrievAMPFRDA.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogGrievAMPFRDA.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogGrievAMPFRDA;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogGrievAMPFRDA.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		workflowTasks=workflowTasks.stream().sorted(Comparator.comparing(WorkflowTask::getWorkflowTaskId).reversed()).collect(Collectors.toList());
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		_log.info("workflow taskid:  "+workflowTaskId);
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countReportUploadLogGrievAMPFRDAByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogGrievAMPFRDAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogGrievAMPFRDALocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogGrievAMPFRDAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogGrievAMPFRDALocalService.dynamicQueryCount(query);
	}
	
	public List<ReportUploadLogGrievAMPFRDA> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogGrievAMPFRDAImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogGrievAMPFRDALocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(ReportUploadLogGrievAMPFRDALocalServiceImpl.class);
}