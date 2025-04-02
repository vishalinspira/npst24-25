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

import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.CustodianCompFormImpl;
import com.daily.average.service.service.CustodianCompFormLocalServiceUtil;
import com.daily.average.service.service.base.CustodianCompFormLocalServiceBaseImpl;
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
public class CustodianCompFormLocalServiceImpl
	extends CustodianCompFormLocalServiceBaseImpl {
	
	

	public CustodianCompForm addCustodianCompForm(Date formdate,String remarks_i_i, String remarks_ii,
			String remarks_iii, String remarks_iv,  String remarks_v, String remarks_vi,
			String remarks_vii, String remarks_viii, String remarks_ix, String remarks_x,
			String remarks_xi, String remarks_xii, String remarks_xiii, String remarks_xiv, String remarks_xv,String remarks_xvi,String remarks_xvii,
			String remarks_xviii, String month, String signature,
			String employeeName, String designation, String date_3, String place, long reportUploadLogId, long qcfile_id) {
		CustodianCompForm custodianCompForm = CustodianCompFormLocalServiceUtil.fetchCustodianCompForm(reportUploadLogId);
		_log.info("addCustodianCompForm::" + custodianCompForm);
		if(custodianCompForm == null) {
			custodianCompForm =  custodianCompFormPersistence.create(reportUploadLogId);
		}
		try {
			custodianCompForm.setFormdate(formdate);
			custodianCompForm.setRemarks_i_i(remarks_i_i);
			//custodianCompForm.setRemarks_i_ii(remarks_i_ii);
			custodianCompForm.setRemarks_ii(remarks_ii);
			custodianCompForm.setRemarks_iii(remarks_iii);
			custodianCompForm.setRemarks_iv(remarks_iv);
			custodianCompForm.setRemarks_v(remarks_v);
			custodianCompForm.setRemarks_vi(remarks_vi);
			custodianCompForm.setRemarks_vii(remarks_vii);
			custodianCompForm.setRemarks_viii(remarks_viii);
			custodianCompForm.setRemarks_ix(remarks_ix);
			custodianCompForm.setRemarks_x(remarks_x);
			custodianCompForm.setRemarks_xi(remarks_xi);
			custodianCompForm.setRemarks_xii(remarks_xii);
			custodianCompForm.setRemarks_xiii(remarks_xiii);
			custodianCompForm.setRemarks_xiv(remarks_xiv);
			custodianCompForm.setRemarks_xv(remarks_xv);
			custodianCompForm.setRemarks_xvi(remarks_xvi);
			custodianCompForm.setRemarks_xvii(remarks_xvii);
			custodianCompForm.setRemarks_xviii(remarks_xviii);
			custodianCompForm.setMonth(month);
			custodianCompForm.setSignature(signature);
			custodianCompForm.setEmployeeName(employeeName);
			custodianCompForm.setDesignation(designation);
			custodianCompForm.setDate_3(date_3);
			custodianCompForm.setPlace(place);
			custodianCompForm.setQcfile_id(qcfile_id);
			custodianCompForm = custodianCompFormPersistence.update(custodianCompForm);
		} catch (Exception e) {
			_log.error("Exception in adding custodian comp form::" + e.getMessage());
		}
		
		return custodianCompForm;
	}
	
	public CustodianCompForm updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		CustodianCompForm custodianCompForm = null;
		try {
			custodianCompForm = custodianCompFormLocalService.getCustodianCompForm(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {		
			custodianCompForm.setReportMasterId(reportUploadLog.getReportMasterId());
			custodianCompForm.setReportDate(reportUploadLog.getReportDate());
			custodianCompForm.setCreatedate(createDate);
			custodianCompForm.setCreatedby(createdBy);
			custodianCompForm.setStatus(status);
			custodianCompForm.setStatusByUserId(statusByUserId);
			custodianCompForm.setStatusByUserName(statusByUserName);
			custodianCompForm.setStatusDate(statusDate);
			custodianCompForm.setRemarks(remarks);
			custodianCompForm.setFileEntryId(fileEntryId);
			
			custodianCompForm = custodianCompFormPersistence.update(custodianCompForm);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForCustodianCompForm(custodianCompForm, serviceContext, createdBy);
		}else {
			custodianCompForm.setCreatedate(createDate);
			custodianCompForm.setCreatedby(createdBy);
			
			custodianCompForm.setStatus(status);
			custodianCompForm.setStatusByUserId(statusByUserId);
			custodianCompForm.setStatusByUserName(statusByUserName);
			custodianCompForm.setStatusDate(statusDate);
			custodianCompForm.setRemarks(remarks);
			custodianCompForm.setFileEntryId(fileEntryId);
			
			custodianCompForm = custodianCompFormPersistence.update(custodianCompForm);
			//reportUploadLog.setUploaded(true);
			//reportUploadLog.setUploaded_i(1);
			//_log.info(reportUploadLogPersistence.update(reportUploadLog));
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = custodianCompForm.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return custodianCompForm;
	}
	
	public void createAssetForCustodianCompForm(CustodianCompForm custodianCompForm, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(custodianCompForm)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    CustodianCompForm.class.getName(),
	                    custodianCompForm.getReportUploadLogId(),
	                    custodianCompForm.getUuid(),
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
	                    "Report Upload Log with Id: " +  custodianCompForm.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<CustodianCompForm> indexer = IndexerRegistryUtil.nullSafeGetIndexer(CustodianCompForm.class);
				indexer.reindex(custodianCompForm);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						CustodianCompForm.class.getName(), custodianCompForm.getReportUploadLogId(), custodianCompForm, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public CustodianCompForm updateCustodianCompFormStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		CustodianCompForm custodianCompForm = null;
		try {
			custodianCompForm = custodianCompFormPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(custodianCompForm)) {
			custodianCompForm.setStatus(status);
			custodianCompForm.setStatusByUserId(userId);
			custodianCompForm.setStatusDate(new Date());
			custodianCompForm.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				custodianCompForm.setStatusByUserName(user.getFullName());
				custodianCompForm.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			custodianCompForm = custodianCompFormPersistence.update(custodianCompForm);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(CustodianCompForm.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(CustodianCompForm.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return custodianCompForm;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, CustodianCompForm.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	Log _log = LogFactoryUtil.getLog(CustodianCompFormLocalServiceImpl.class);
	
	public long countCustodianCompFormByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustodianCompFormImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return custodianCompFormLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustodianCompFormImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return custodianCompFormLocalService.dynamicQueryCount(query);
	}
	
	public List<CustodianCompForm> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustodianCompFormImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return custodianCompFormLocalService.dynamicQuery(query);
	}

	
}