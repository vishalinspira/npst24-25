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

import com.daily.average.service.model.Manpowerform_ii;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.Manpowerform_iiLocalServiceUtil;
import com.daily.average.service.service.base.Manpowerform_iiLocalServiceBaseImpl;
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
public class Manpowerform_iiLocalServiceImpl
	extends Manpowerform_iiLocalServiceBaseImpl {
	
	public Manpowerform_ii addManpowerform_ii(long reportUploadLogId, 
			 Date createDate, long createdBy) {
		Manpowerform_ii manpowerform_ii = Manpowerform_iiLocalServiceUtil.createManpowerform_ii(reportUploadLogId);
		manpowerform_ii.setCreatedby(createdBy);
		manpowerform_ii.setCreatedon(new Date());
		manpowerform_iiPersistence.update(manpowerform_ii);
		manpowerform_iiPersistence.flush();
		return manpowerform_ii;
	}
	
	public Manpowerform_ii updateReportUploadLog(Date createDate, long createdBy, long fileEntryId,
			boolean uploaded, long reportUploadLogId, int status, long statusByUserId, String statusByUserName,
			Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		Manpowerform_ii manpowerform_ii = null;
		try {
			manpowerform_ii = manpowerform_iiLocalService.getManpowerform_ii(reportUploadLogId);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		//if(Validator.isNull(manpowerform_ii)) {
			//manpowerform_ii = qtrmanpowerform_iiLocalService.createQtrStewardshipReport(reportUploadLogId);			
		_log.info("reportUploadLog"+reportUploadLog+"manpowerform_ii"+manpowerform_ii);
		if(reupload) {
			manpowerform_ii.setReportMasterId(reportUploadLog.getReportMasterId());
			manpowerform_ii.setReportDate(reportUploadLog.getReportDate());
			manpowerform_ii.setCreatedon(createDate);
			manpowerform_ii.setCreatedby(createdBy);
			manpowerform_ii.setStatus(status);
			manpowerform_ii.setStatusByUserId(statusByUserId);
			manpowerform_ii.setStatusByUserName(statusByUserName);
			manpowerform_ii.setStatusDate(statusDate);
			manpowerform_ii.setRemarks(remarks);
			manpowerform_ii.setFileEntryId(fileEntryId);
			
			manpowerform_ii = manpowerform_iiPersistence.update(manpowerform_ii);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForManpowerform_ii(manpowerform_ii, serviceContext, createdBy);
		}else {
			manpowerform_ii.setCreatedon(createDate);
			manpowerform_ii.setCreatedby(createdBy);
			
			manpowerform_ii.setStatus(status);
			manpowerform_ii.setStatusByUserId(statusByUserId);
			manpowerform_ii.setStatusByUserName(statusByUserName);
			manpowerform_ii.setStatusDate(statusDate);
			manpowerform_ii.setRemarks(remarks);
			manpowerform_ii.setFileEntryId(fileEntryId);
			manpowerform_ii = manpowerform_iiPersistence.update(manpowerform_ii);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = manpowerform_ii.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		return manpowerform_ii;
	}
	
	public void createAssetForManpowerform_ii(Manpowerform_ii manpowerform_ii, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(manpowerform_ii)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    Manpowerform_ii.class.getName(),
	                    manpowerform_ii.getReportUploadLogId(),
	                    manpowerform_ii.getUuid(),
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
	                    "Report Upload Log with Id: " +  manpowerform_ii.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<Manpowerform_ii> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Manpowerform_ii.class);
				indexer.reindex(manpowerform_ii);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						Manpowerform_ii.class.getName(), manpowerform_ii.getReportUploadLogId(), manpowerform_ii, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public Manpowerform_ii updateManpowerform_iiStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		Manpowerform_ii manpowerform_ii = null;
		try {
			manpowerform_ii = manpowerform_iiPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(manpowerform_ii)) {
			manpowerform_ii.setStatus(status);
			manpowerform_ii.setStatusByUserId(userId);
			manpowerform_ii.setStatusDate(new Date());
			manpowerform_ii.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				manpowerform_ii.setStatusByUserName(user.getFullName());
				manpowerform_ii.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			manpowerform_ii = manpowerform_iiPersistence.update(manpowerform_ii);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(Manpowerform_ii.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(Manpowerform_ii.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return manpowerform_ii;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, Manpowerform_ii.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countManpowerform_iiReportByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Manpowerform_ii.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return manpowerform_iiLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Manpowerform_ii.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return manpowerform_iiLocalService.dynamicQueryCount(query);
	}
	
	public List<Manpowerform_ii> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Manpowerform_ii.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return manpowerform_iiLocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(Manpowerform_iiLocalServiceImpl.class);
	
	
}