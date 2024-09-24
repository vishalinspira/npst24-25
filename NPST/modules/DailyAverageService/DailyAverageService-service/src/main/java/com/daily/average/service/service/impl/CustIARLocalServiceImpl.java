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

import com.daily.average.service.model.CustIAR;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.CustIARImpl;
import com.daily.average.service.service.base.CustIARLocalServiceBaseImpl;
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
public class CustIARLocalServiceImpl extends CustIARLocalServiceBaseImpl {
	
	public CustIAR addIAR(long iar_file_id, Date date_1, Date date_2, 
			String iar_details, String name, String designation, String date_3, 
			Date createdon, long createdby, long reportUploadLogId) {
		CustIAR custIAR = custIARLocalService.fetchCustIAR(reportUploadLogId);
		if(custIAR == null) {
			custIAR = custIARPersistence.create(reportUploadLogId);
		}
		custIAR.setIar_file_id(iar_file_id);
		custIAR.setDate_1(date_1);
		custIAR.setDate_2(date_2);
		custIAR.setIar_details(iar_details);
		custIAR.setName_(name);
		custIAR.setDesignation(designation);
		custIAR.setDate_3(date_3);
		
		custIAR = custIARPersistence.update(custIAR);
				
		return custIAR;
		
	}
	
	public CustIAR updateReportUploadLog(Date createDate, long createdBy, long fileEntryId,
			boolean uploaded, long reportUploadLogId, int status, long statusByUserId, String statusByUserName,
			Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		CustIAR custIAR = null;
		try {
			custIAR = custIARLocalService.getCustIAR(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {
			//custIAR = custIARLocalService.createCustIAR(reportUploadLogId);			
			custIAR.setReportMasterId(reportUploadLog.getReportMasterId());
			custIAR.setReportDate(reportUploadLog.getReportDate());
			custIAR.setCreatedon(createDate);
			custIAR.setCreatedby(createdBy);
			custIAR.setStatus(status);
			custIAR.setStatusByUserId(statusByUserId);
			custIAR.setStatusByUserName(statusByUserName);
			custIAR.setStatusDate(statusDate);
			custIAR.setRemarks(remarks);
			custIAR.setFileEntryId(fileEntryId);
			
			custIAR = custIARPersistence.update(custIAR);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForCustIAR(custIAR, serviceContext, createdBy);
		}else {
			custIAR.setCreatedon(createDate);
			custIAR.setCreatedby(createdBy);
			
			custIAR.setStatus(status);
			custIAR.setStatusByUserId(statusByUserId);
			custIAR.setStatusByUserName(statusByUserName);
			custIAR.setStatusDate(statusDate);
			custIAR.setRemarks(remarks);
			custIAR.setFileEntryId(fileEntryId);
			
			custIAR = custIARPersistence.update(custIAR);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = custIAR.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		return custIAR;
	}
	
	public void createAssetForCustIAR(CustIAR custIAR, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(custIAR)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    CustIAR.class.getName(),
	                    custIAR.getReportUploadLogId(),
	                    custIAR.getUuid(),
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
	                    "Report Upload Log with Id: " +  custIAR.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<CustIAR> indexer = IndexerRegistryUtil.nullSafeGetIndexer(CustIAR.class);
				indexer.reindex(custIAR);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						CustIAR.class.getName(), custIAR.getReportUploadLogId(), custIAR, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public CustIAR updateCustIARStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		CustIAR custIAR = null;
		try {
			custIAR = custIARPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(custIAR)) {
			custIAR.setStatus(status);
			custIAR.setStatusByUserId(userId);
			custIAR.setStatusDate(new Date());
			custIAR.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				custIAR.setStatusByUserName(user.getFullName());
				custIAR.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			custIAR = custIARPersistence.update(custIAR);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(CustIAR.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(CustIAR.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return custIAR;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, CustIAR.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countCustIARByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustIARImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return custIARLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustIARImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return custIARLocalService.dynamicQueryCount(query);
	}
	
	public List<CustIAR> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustIARImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return custIARLocalService.dynamicQuery(query);
	}
	
	Log _log = LogFactoryUtil.getLog(CustIARLocalServiceImpl.class);
	
	
	
	
}