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

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.base.CustAnnualAuditReportLocalServiceBaseImpl;
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
public class CustAnnualAuditReportLocalServiceImpl
	extends CustAnnualAuditReportLocalServiceBaseImpl {
	
	public CustAnnualAuditReport saveCustAnnualAuditReport(long reportUploadLogId,long audit_pdf_fileentry_id,String cust_report_details,String name,String designation,String date_,String place,
			Date createdon,long createdby, Date reportDate) {
		 
		CustAnnualAuditReport custAnnual = custAnnualAuditReportLocalService.fetchCustAnnualAuditReport(reportUploadLogId);
		if(custAnnual == null) {
			custAnnual = custAnnualAuditReportPersistence.create(reportUploadLogId);
		//long reportUploadLogId = counterLocalService.increment(AnnualCompCertificate.class.getName());
		}
		
		
		//eligibility::::::::::::::::::
		custAnnual.setAudit_pdf_fileentry_id(audit_pdf_fileentry_id);
		custAnnual.setCust_report_details(cust_report_details);
		custAnnual.setName(name);
		custAnnual.setPlace(place);
		custAnnual.setDate_(date_);
		custAnnual.setDesignation(designation);
	
		custAnnual.setReportDate(reportDate);
		
		_log.info("annualComp:::::::::::::::::::::::::::::"+custAnnual.toString());
			
		return custAnnualAuditReportPersistence.update(custAnnual);
	}

	public CustAnnualAuditReport updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		_log.info("CustAnnualAuditReport NPS::::::");
		CustAnnualAuditReport custAnnual = null;
		try {
			custAnnual = custAnnualAuditReportLocalService.getCustAnnualAuditReport(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {
			_log.info("inside if");
		    custAnnual.setReportMasterId(reportUploadLog.getReportMasterId());
		    custAnnual.setReportDate(reportUploadLog.getReportDate());
		    custAnnual.setCreatedon(createDate);
		    custAnnual.setCreatedby(createdBy);
		    custAnnual.setStatus(status);
		    custAnnual.setStatusByUserId(statusByUserId);
		    custAnnual.setStatusByUserName(statusByUserName);
		    custAnnual.setStatusDate(statusDate);
		    custAnnual.setRemarks(remarks);
		    custAnnual.setFileEntryId(fileEntryId);
			
		    custAnnual = custAnnualAuditReportPersistence.update(custAnnual);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForCustAnnualAuditReport(custAnnual, serviceContext, createdBy);
		}else {
			_log.info("inside else");
			custAnnual.setCreatedon(createDate);
			custAnnual.setCreatedby(createdBy);
			
			custAnnual.setStatus(status);
			custAnnual.setStatusByUserId(statusByUserId);
			custAnnual.setStatusByUserName(statusByUserName);
			custAnnual.setStatusDate(statusDate);
			custAnnual.setRemarks(remarks);
			custAnnual.setFileEntryId(fileEntryId);
			
			custAnnual = custAnnualAuditReportPersistence.update(custAnnual);
			//reportUploadLog.setUploaded(true);
			//reportUploadLog.setUploaded_i(1);
			try {
				reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId);
				//_log.info(reportUploadLogPersistence.updateImpl(reportUploadLog));
				reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			} catch (Exception e) {
				_log.error("Exception in updating reportUploadLog::" + e.getMessage());
			}
		
			String workflowContext = custAnnual.getWorkflowContext();
		try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
		
			
		}
		
		
		return custAnnual;
	}
	
	public void createAssetForCustAnnualAuditReport(CustAnnualAuditReport custAnnual, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(custAnnual)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    CustAnnualAuditReport.class.getName(),
	                    custAnnual.getReportUploadLogId(),
	                    custAnnual.getUuid(),
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
	                    "Report Upload Log with Id: " +  custAnnual.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<CustAnnualAuditReport> indexer = IndexerRegistryUtil.nullSafeGetIndexer(CustAnnualAuditReport.class);
				indexer.reindex(custAnnual);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						CustAnnualAuditReport.class.getName(), custAnnual.getReportUploadLogId(), custAnnual, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public CustAnnualAuditReport updateCustAnnualAuditReportStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		CustAnnualAuditReport custAnnual = null;
		try {
			custAnnual = custAnnualAuditReportPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(custAnnual)) {
			custAnnual.setStatus(status);
			custAnnual.setStatusByUserId(userId);
			custAnnual.setStatusDate(new Date());
			custAnnual.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				custAnnual.setStatusByUserName(user.getFullName());
				custAnnual.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			custAnnual = custAnnualAuditReportPersistence.update(custAnnual);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(CustAnnualAuditReport.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(CustAnnualAuditReport.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return custAnnual;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, CustAnnualAuditReport.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	Log _log = LogFactoryUtil.getLog(AnnualCompCertificateLocalServiceImpl.class);
	
	public long countMnCompCertificateByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustAnnualAuditReport.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return custAnnualAuditReportLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustAnnualAuditReport.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return custAnnualAuditReportLocalService.dynamicQueryCount(query);
	}
	
	public List<CustAnnualAuditReport> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(CustAnnualAuditReport.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return custAnnualAuditReportLocalService.dynamicQuery(query);
		
	}
}