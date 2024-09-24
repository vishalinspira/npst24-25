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

import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.QtrStewardshipReportImpl;
import com.daily.average.service.service.base.QtrStewardshipReportLocalServiceBaseImpl;
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
public class QtrStewardshipReportLocalServiceImpl
	extends QtrStewardshipReportLocalServiceBaseImpl {
	
	public QtrStewardshipReport addQtrStewardshipReport(long reportUploadLogId, String date_1, 
			String conflict_of_interest, String monitoring_situation, String resolutions_voted,String conflict_rem1,String monitoring_rem1,String resolutions_rem1,
			String resolutionsVoted1, String adversealert, String insInvestorSituation,String resolutionsVoted1_rem1,String adversealert_rem1,String insInvestorSituation_rem1,
			String date_2, String employee_name, String place, String roles, long annexure_a, 
			long annexure_b_i, long annexure_b_ii, long annexure_c,String annexureA_rem,String annexureB_I_rem,String annexureB_II_rem,String annexureC_rem,
	Date createDate, 
			long createdBy) { 
		
		QtrStewardshipReport stewardshipReport = qtrStewardshipReportLocalService.fetchQtrStewardshipReport(reportUploadLogId);
		
		if(stewardshipReport == null) {
			stewardshipReport = qtrStewardshipReportPersistence.create(reportUploadLogId);
		}
		
		stewardshipReport.setDate_1(date_1);
		stewardshipReport.setConflict_of_interest(conflict_of_interest);
		stewardshipReport.setMonitoring_situation(monitoring_situation);
		stewardshipReport.setResolutions_voted(resolutions_voted);
		stewardshipReport.setConflict_rem1(conflict_rem1);
		stewardshipReport.setMonitoring_rem1(monitoring_rem1);
		stewardshipReport.setResolutions_rem1(resolutions_rem1);
		stewardshipReport.setResolutionsVoted1(resolutionsVoted1);
		stewardshipReport.setAdversealert(adversealert);
		stewardshipReport.setInsInvestorSituation(insInvestorSituation);
		stewardshipReport.setResolutionsVoted1_rem1(resolutionsVoted1_rem1);
		stewardshipReport.setAdversealert_rem1(adversealert_rem1);
		stewardshipReport.setInsInvestorSituation_rem1(insInvestorSituation_rem1);
		stewardshipReport.setDate_2(date_2);
		stewardshipReport.setEmployee_name(employee_name);
		stewardshipReport.setPlace(place);
		stewardshipReport.setRoles(roles);
		stewardshipReport.setAnnexure_a(annexure_a);
		stewardshipReport.setAnnexure_b_i(annexure_b_i);
		stewardshipReport.setAnnexure_b_ii(annexure_b_ii);
		stewardshipReport.setAnnexure_c(annexure_c);
		stewardshipReport.setAnnexureA_rem(annexureA_rem);
		stewardshipReport.setAnnexureB_I_rem(annexureB_I_rem);
		stewardshipReport.setAnnexureB_II_rem(annexureB_II_rem);
		stewardshipReport.setAnnexureC_rem(annexureC_rem);
		stewardshipReport.setCreatedon(createDate);
		stewardshipReport.setCreatedby(createdBy);
		
	
		
		
		return qtrStewardshipReportPersistence.update(stewardshipReport);
		
	}
	
	public QtrStewardshipReport updateReportUploadLog(Date createDate, long createdBy, long fileEntryId,
			boolean uploaded, long reportUploadLogId, int status, long statusByUserId, String statusByUserName,
			Date statusDate, ServiceContext serviceContext, String remarks,boolean reupload) {
		QtrStewardshipReport stewardshipReport = null;
		try {
			stewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		_log.info("Reupload ====== "+reupload);
		if(reupload) {
			//stewardshipReport = qtrStewardshipReportLocalService.createQtrStewardshipReport(reportUploadLogId);			
			stewardshipReport.setReportMasterId(reportUploadLog.getReportMasterId());
			stewardshipReport.setReportDate(reportUploadLog.getReportDate());
			stewardshipReport.setCreatedon(createDate);
			stewardshipReport.setCreatedby(createdBy);
			stewardshipReport.setStatus(status);
			stewardshipReport.setStatusByUserId(statusByUserId);
			stewardshipReport.setStatusByUserName(statusByUserName);
			stewardshipReport.setStatusDate(statusDate);
			stewardshipReport.setRemarks(remarks);
			stewardshipReport.setFileEntryId(fileEntryId);
			
			stewardshipReport = qtrStewardshipReportPersistence.update(stewardshipReport);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForQtrStewardshipReport(stewardshipReport, serviceContext, createdBy);
			_log.info("workflow is done ");
		}else {
			stewardshipReport.setCreatedon(createDate);
			stewardshipReport.setCreatedby(createdBy);
			
			stewardshipReport.setStatus(status);
			stewardshipReport.setStatusByUserId(statusByUserId);
			stewardshipReport.setStatusByUserName(statusByUserName);
			stewardshipReport.setStatusDate(statusDate);
			stewardshipReport.setRemarks(remarks);
			stewardshipReport.setFileEntryId(fileEntryId);
			_log.info("stewardshipReport :  "+stewardshipReport);
			stewardshipReport = qtrStewardshipReportPersistence.update(stewardshipReport);
			//reportUploadLog.setUploaded(true);
			//reportUploadLog.setUploaded_i(1);
			
			//_log.info(reportUploadLogPersistence.update(reportUploadLog));
			
			try {
				reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId);
				reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			} catch (Exception e) {
				_log.error("Exception in updating reportUploadLog::" + e.getMessage());
			}
			
			String workflowContext = stewardshipReport.getWorkflowContext();
			
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		return stewardshipReport;
	}
	
	public void createAssetForQtrStewardshipReport(QtrStewardshipReport stewardshipReport, ServiceContext serviceContext, long CreatedBy) {
		
		_log.info("inside assest creation :::::::::::");
		_log.info("steward ship report :::::: "+Validator.isNotNull(stewardshipReport));
		_log.info("stewardshipReport ::::::::::::::: "+stewardshipReport.toString());
		if(Validator.isNotNull(stewardshipReport)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    QtrStewardshipReport.class.getName(),
	                    stewardshipReport.getReportUploadLogId(),
	                    stewardshipReport.getUuid(),
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
	                    "Report Upload Log with Id: " +  stewardshipReport.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<QtrStewardshipReport> indexer = IndexerRegistryUtil.nullSafeGetIndexer(QtrStewardshipReport.class);
				indexer.reindex(stewardshipReport);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						QtrStewardshipReport.class.getName(), stewardshipReport.getReportUploadLogId(), stewardshipReport, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public QtrStewardshipReport updateQtrStewardshipReportStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		QtrStewardshipReport stewardshipReport = null;
		try {
			stewardshipReport = qtrStewardshipReportPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(stewardshipReport)) {
			stewardshipReport.setStatus(status);
			stewardshipReport.setStatusByUserId(userId);
			stewardshipReport.setStatusDate(new Date());
			stewardshipReport.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				stewardshipReport.setStatusByUserName(user.getFullName());
				stewardshipReport.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			stewardshipReport = qtrStewardshipReportPersistence.update(stewardshipReport);
		}

		try {
			_log.info("workflow status ::::::::: "+status);
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(QtrStewardshipReport.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(QtrStewardshipReport.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return stewardshipReport;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, QtrStewardshipReport.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countQtrStewardshipReportByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(QtrStewardshipReportImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return qtrStewardshipReportLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(QtrStewardshipReportImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return qtrStewardshipReportLocalService.dynamicQueryCount(query);
	}
	
	public List<QtrStewardshipReport> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(QtrStewardshipReportImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return qtrStewardshipReportLocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(QtrStewardshipReportLocalServiceImpl.class);
	
	
}