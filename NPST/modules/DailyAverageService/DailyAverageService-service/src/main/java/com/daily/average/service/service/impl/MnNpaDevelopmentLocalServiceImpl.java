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

import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.base.MnNpaDevelopmentLocalServiceBaseImpl;
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
public class MnNpaDevelopmentLocalServiceImpl extends MnNpaDevelopmentLocalServiceBaseImpl {
	
	public MnNpaDevelopment addMnNpaDevelopment(long reportUploadLogId, String companies, 
			String date_1, String tableDetails, Date createDate, long createdBy) {
		
		MnNpaDevelopment npaDevelopment = mnNpaDevelopmentLocalService.fetchMnNpaDevelopment(reportUploadLogId);
		
		if(npaDevelopment == null) {
			npaDevelopment = mnNpaDevelopmentPersistence.create(reportUploadLogId);
		}
		
		npaDevelopment.setCompanies(companies);
		npaDevelopment.setDate_1(date_1);
		npaDevelopment.setTabledetails(tableDetails);
		npaDevelopment.setCreatedon(createDate);
		npaDevelopment.setCreatedby(createdBy);
		
		return mnNpaDevelopmentPersistence.update(npaDevelopment);
		
	}
	
	public MnNpaDevelopment updateReportUploadLog(Date createDate, long createdBy, long fileEntryId,
			boolean uploaded, long reportUploadLogId, int status, long statusByUserId, String statusByUserName,
			Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		MnNpaDevelopment npaDevelopment = null;
		try {
			npaDevelopment = mnNpaDevelopmentLocalService.getMnNpaDevelopment(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		//if(Validator.isNull(npaDevelopment)) {
			//npaDevelopment = qtrnpaDevelopmentLocalService.createQtrStewardshipReport(reportUploadLogId);			
			if(reupload) {
			npaDevelopment.setReportMasterId(reportUploadLog.getReportMasterId());
			npaDevelopment.setReportDate(reportUploadLog.getReportDate());
			npaDevelopment.setCreatedon(createDate);
			npaDevelopment.setCreatedby(createdBy);
			npaDevelopment.setStatus(status);
			npaDevelopment.setStatusByUserId(statusByUserId);
			npaDevelopment.setStatusByUserName(statusByUserName);
			npaDevelopment.setStatusDate(statusDate);
			npaDevelopment.setRemarks(remarks);
			npaDevelopment.setFileEntryId(fileEntryId);
			
			npaDevelopment = mnNpaDevelopmentPersistence.update(npaDevelopment);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForMnNpaDevelopment(npaDevelopment, serviceContext, createdBy);
		}else {
			npaDevelopment.setCreatedon(createDate);
			npaDevelopment.setCreatedby(createdBy);
			
			npaDevelopment.setStatus(status);
			npaDevelopment.setStatusByUserId(statusByUserId);
			npaDevelopment.setStatusByUserName(statusByUserName);
			npaDevelopment.setStatusDate(statusDate);
			npaDevelopment.setRemarks(remarks);
			npaDevelopment.setFileEntryId(fileEntryId);
			npaDevelopment = mnNpaDevelopmentPersistence.update(npaDevelopment);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = npaDevelopment.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		return npaDevelopment;
	}
	
	public void createAssetForMnNpaDevelopment(MnNpaDevelopment npaDevelopment, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(npaDevelopment)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    MnNpaDevelopment.class.getName(),
	                    npaDevelopment.getReportUploadLogId(),
	                    npaDevelopment.getUuid(),
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
	                    "Report Upload Log with Id: " +  npaDevelopment.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<MnNpaDevelopment> indexer = IndexerRegistryUtil.nullSafeGetIndexer(MnNpaDevelopment.class);
				indexer.reindex(npaDevelopment);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						MnNpaDevelopment.class.getName(), npaDevelopment.getReportUploadLogId(), npaDevelopment, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public MnNpaDevelopment updateMnNpaDevelopmentStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		MnNpaDevelopment npaDevelopment = null;
		try {
			npaDevelopment = mnNpaDevelopmentPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(npaDevelopment)) {
			npaDevelopment.setStatus(status);
			npaDevelopment.setStatusByUserId(userId);
			npaDevelopment.setStatusDate(new Date());
			npaDevelopment.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				npaDevelopment.setStatusByUserName(user.getFullName());
				npaDevelopment.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			npaDevelopment = mnNpaDevelopmentPersistence.update(npaDevelopment);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(MnNpaDevelopment.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(MnNpaDevelopment.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return npaDevelopment;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, MnNpaDevelopment.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countMnNpaDevelopmentReportByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MnNpaDevelopment.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return mnNpaDevelopmentLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MnNpaDevelopment.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return mnNpaDevelopmentLocalService.dynamicQueryCount(query);
	}
	
	public List<MnNpaDevelopment> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MnNpaDevelopment.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return mnNpaDevelopmentLocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(MnNpaDevelopmentLocalServiceImpl.class);
	
	
}