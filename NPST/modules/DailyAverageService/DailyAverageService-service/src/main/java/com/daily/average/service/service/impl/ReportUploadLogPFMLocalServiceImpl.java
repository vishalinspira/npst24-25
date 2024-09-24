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
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.impl.ReportUploadLogPFMImpl;
import com.daily.average.service.service.base.ReportUploadLogPFMLocalServiceBaseImpl;
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
public class ReportUploadLogPFMLocalServiceImpl
	extends ReportUploadLogPFMLocalServiceBaseImpl {
	public ReportUploadLogPFM addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLogPFM reportUploadLog = reportUploadLogPFMPersistence.create(counterLocalService.increment(ReportUploadLogPFM.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogPFMPersistence.update(reportUploadLog);
	}
	
	public ReportUploadLogPFM fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogPFMPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	public List<ReportUploadLogPFM> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		return reportUploadLogPFMPersistence.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
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
	/*public ReportUploadLogPFM updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId) {
		ReportUploadLogPFM reportUploadLog = reportUploadLogPFMPersistence.fetchByPrimaryKey(reportUploadLogId);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setUploaded(uploaded);
		
		return reportUploadLogPFMPersistence.update(reportUploadLog);
	}*/
	
	public ReportUploadLogPFM updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks) {
		ReportUploadLogPFM reportUploadLogPFM = null;
		try {
			reportUploadLogPFM = reportUploadLogPFMLocalService.getReportUploadLogPFM(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogPFM)) {
			reportUploadLogPFM = reportUploadLogPFMLocalService.createReportUploadLogPFM(reportUploadLogId);			
			reportUploadLogPFM.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogPFM.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogPFM.setCreateDate(createDate);
			reportUploadLogPFM.setCreatedBy(createdBy);
			reportUploadLogPFM.setFileEntryId(fileEntryId);
			reportUploadLogPFM.setUploaded(uploaded);
			reportUploadLogPFM.setStatus(status);
			reportUploadLogPFM.setStatusByUserId(statusByUserId);
			reportUploadLogPFM.setStatusByUserName(statusByUserName);
			reportUploadLogPFM.setStatusDate(statusDate);
			reportUploadLogPFM.setRemarks(remarks);
			
			reportUploadLogPFM = reportUploadLogPFMPersistence.update(reportUploadLogPFM);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForReportUploadLogPFM(reportUploadLogPFM, serviceContext, createdBy);
		}else {
			reportUploadLogPFM.setCreateDate(createDate);
			reportUploadLogPFM.setCreatedBy(createdBy);
			reportUploadLogPFM.setFileEntryId(fileEntryId);
			reportUploadLogPFM.setUploaded(uploaded);
			
			reportUploadLogPFM.setStatus(status);
			reportUploadLogPFM.setStatusByUserId(statusByUserId);
			reportUploadLogPFM.setStatusByUserName(statusByUserName);
			reportUploadLogPFM.setStatusDate(statusDate);
			reportUploadLogPFM.setRemarks(remarks);
			
			reportUploadLogPFM = reportUploadLogPFMPersistence.update(reportUploadLogPFM);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = reportUploadLogPFM.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return reportUploadLogPFM;
	}
	
	public ReportUploadLogPFM updateReportUploadLog2(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, String fileList) {
		ReportUploadLogPFM reportUploadLogPFM = null;
		try {
			reportUploadLogPFM = reportUploadLogPFMLocalService.getReportUploadLogPFM(reportUploadLogId);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(Validator.isNull(reportUploadLogPFM)) {
			reportUploadLogPFM = reportUploadLogPFMLocalService.createReportUploadLogPFM(reportUploadLogId);			
			reportUploadLogPFM.setReportMasterId(reportUploadLog.getReportMasterId());
			reportUploadLogPFM.setReportDate(reportUploadLog.getReportDate());
			reportUploadLogPFM.setCreateDate(createDate);
			reportUploadLogPFM.setCreatedBy(createdBy);
			reportUploadLogPFM.setFileEntryId(fileEntryId);
			reportUploadLogPFM.setUploaded(uploaded);
			reportUploadLogPFM.setStatus(status);
			reportUploadLogPFM.setStatusByUserId(statusByUserId);
			reportUploadLogPFM.setStatusByUserName(statusByUserName);
			reportUploadLogPFM.setStatusDate(statusDate);
			reportUploadLogPFM.setRemarks(remarks);
			
			reportUploadLogPFM = reportUploadLogPFMPersistence.update(reportUploadLogPFM);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			createAssetForReportUploadLogPFM(reportUploadLogPFM, serviceContext, createdBy);
		}else {
			reportUploadLogPFM.setCreateDate(createDate);
			reportUploadLogPFM.setCreatedBy(createdBy);
			reportUploadLogPFM.setFileEntryId(fileEntryId);
			reportUploadLogPFM.setUploaded(uploaded);
			
			reportUploadLogPFM.setStatus(status);
			reportUploadLogPFM.setStatusByUserId(statusByUserId);
			reportUploadLogPFM.setStatusByUserName(statusByUserName);
			reportUploadLogPFM.setStatusDate(statusDate);
			reportUploadLogPFM.setRemarks(remarks);
			
			reportUploadLogPFM = reportUploadLogPFMPersistence.update(reportUploadLogPFM);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadLogLocalService.updateReportUploadLogStatus("Pending", reportUploadLogId);
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy, fileList);
			_log.info("report upload file log.");
			String workflowContext = reportUploadLogPFM.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		_log.info("Report upload log PFM :"+reportUploadLogPFM);
		return reportUploadLogPFM;
	}
	
	public void createAssetForReportUploadLogPFM(ReportUploadLogPFM reportUploadLogPFM, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(reportUploadLogPFM)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    ReportUploadLogPFM.class.getName(),
	                    reportUploadLogPFM.getReportUploadLogId(),
	                    reportUploadLogPFM.getUuid(),
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
	                    "Report Upload Log with Id: " +  reportUploadLogPFM.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<ReportUploadLogPFM> indexer = IndexerRegistryUtil.nullSafeGetIndexer(ReportUploadLogPFM.class);
				indexer.reindex(reportUploadLogPFM);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						ReportUploadLogPFM.class.getName(), reportUploadLogPFM.getReportUploadLogId(), reportUploadLogPFM, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public ReportUploadLogPFM updateReportUploadLogPFMStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		ReportUploadLogPFM reportUploadLogPFM = null;
		try {
			reportUploadLogPFM = reportUploadLogPFMPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(reportUploadLogPFM)) {
			reportUploadLogPFM.setStatus(status);
			reportUploadLogPFM.setStatusByUserId(userId);
			reportUploadLogPFM.setStatusDate(new Date());
			reportUploadLogPFM.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				reportUploadLogPFM.setStatusByUserName(user.getFullName());
				reportUploadLogPFM.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			reportUploadLogPFM = reportUploadLogPFMPersistence.update(reportUploadLogPFM);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ReportUploadLogPFM.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ReportUploadLogPFM.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return reportUploadLogPFM;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		try {
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		_log.info("start workflow task assignment1");
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, ReportUploadLogPFM.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		_log.info("start workflow task assignment2");
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		_log.info("start workflow task assignment3 & size :  "+workflowTasks.size());
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		_log.info("start workflow task assignment4");
		}catch (Exception e) {
			_log.error(e.getMessage());
		}
		_log.info("complete workflow task assignment");
	}
	Log _log = LogFactoryUtil.getLog(ReportUploadLogPFMLocalServiceImpl.class);
	
	public long countReportUploadLogPFMByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return reportUploadLogPFMLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMLocalService.dynamicQueryCount(query);
	}
	
	public List<ReportUploadLogPFM> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogPFMImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogPFMCRALocalService.dynamicQuery(query);
	}
}