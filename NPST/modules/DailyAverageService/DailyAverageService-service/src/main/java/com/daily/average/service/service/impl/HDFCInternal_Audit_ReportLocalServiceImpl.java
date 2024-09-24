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

import com.daily.average.service.model.HDFCInternal_Audit_Report;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.base.HDFCInternal_Audit_ReportLocalServiceBaseImpl;
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
public class HDFCInternal_Audit_ReportLocalServiceImpl
	extends HDFCInternal_Audit_ReportLocalServiceBaseImpl {
	
	public HDFCInternal_Audit_Report addHDFCInternal_Audit_Report(long reportUploadLogId, Date date, String period_from, String period_to, long certificate_pdf_file_id,
			long auditor_pdf_file_id,long intrnlAdtRprt_pdf_file_id, long extrctBrdMinAprvng_pdf_file_id, long iarcircular_excel_file_id, String hdfc_audit_report_contex, String companies) {
		
		HDFCInternal_Audit_Report object = hdfcInternal_Audit_ReportPersistence.fetchByPrimaryKey(reportUploadLogId);
		if(object ==null) {
			object = hdfcInternal_Audit_ReportPersistence.create(reportUploadLogId);
		}					
		object.setDate_(date);
		object.setPeriod_from(period_from);
		object.setPeriod_to(period_to);
		//object.setForm_date(form_date);
		object.setCompanies(companies);
		object.setCertificate_pdf_file_id(certificate_pdf_file_id);
		object.setAuditor_pdf_file_id(auditor_pdf_file_id);
		object.setIntrnlAdtRprt_pdf_file_id(intrnlAdtRprt_pdf_file_id);
		object.setExtrctBrdMinAprvng_pdf_file_id(extrctBrdMinAprvng_pdf_file_id);
		object.setIarcircular_excel_file_id(iarcircular_excel_file_id);
		object.setHdfc_audit_report_contex(hdfc_audit_report_contex);
		
		return hdfcInternal_Audit_ReportPersistence.update(object);
		
	}
	
	public HDFCInternal_Audit_Report updateReportUploadLog(Date createDate, long createdBy, long fileEntryId,
			boolean uploaded, long reportUploadLogId, int status, long statusByUserId, String statusByUserName,
			Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		
		HDFCInternal_Audit_Report hdfcInternal_Audit_Report = null;
		try {
			hdfcInternal_Audit_Report = hdfcInternal_Audit_ReportLocalService.getHDFCInternal_Audit_Report(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {
//			hdfcInternal_Audit_Report = hdfcInternal_Audit_ReportLocalService.createhdfcInternal_Audit_Report(reportUploadLogId);	
		
			hdfcInternal_Audit_Report.setReportMasterId(reportUploadLog.getReportMasterId());
			hdfcInternal_Audit_Report.setReportDate(reportUploadLog.getReportDate());
			hdfcInternal_Audit_Report.setCreatedon(createDate);
			hdfcInternal_Audit_Report.setCreatedby(createdBy);
			hdfcInternal_Audit_Report.setStatus(status);
			hdfcInternal_Audit_Report.setStatusByUserId(statusByUserId);
			hdfcInternal_Audit_Report.setStatusByUserName(statusByUserName);
			hdfcInternal_Audit_Report.setStatusDate(statusDate);
			hdfcInternal_Audit_Report.setRemarks(remarks);
			hdfcInternal_Audit_Report.setFileEntryId(fileEntryId);
			
			hdfcInternal_Audit_Report = hdfcInternal_Audit_ReportPersistence.update(hdfcInternal_Audit_Report);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForhdfcInternal_Audit_Report(hdfcInternal_Audit_Report, serviceContext, createdBy);
		}else {
			hdfcInternal_Audit_Report.setCreatedon(createDate);
			hdfcInternal_Audit_Report.setCreatedby(createdBy);
			
			hdfcInternal_Audit_Report.setStatus(status);
			hdfcInternal_Audit_Report.setStatusByUserId(statusByUserId);
			hdfcInternal_Audit_Report.setStatusByUserName(statusByUserName);
			hdfcInternal_Audit_Report.setStatusDate(statusDate);
			hdfcInternal_Audit_Report.setRemarks(remarks);
			hdfcInternal_Audit_Report.setFileEntryId(fileEntryId);
			
			//hdfcInternal_Audit_Report = qtrStewardshipReportPersistence.update(stewardshipReport);
			hdfcInternal_Audit_Report=hdfcInternal_Audit_ReportPersistence.updateImpl(hdfcInternal_Audit_Report);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = hdfcInternal_Audit_Report.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		return hdfcInternal_Audit_Report;
		
	}
	
	public void createAssetForhdfcInternal_Audit_Report(HDFCInternal_Audit_Report hdfcInternal_Audit_Report, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(hdfcInternal_Audit_Report)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    HDFCInternal_Audit_Report.class.getName(),
	                    hdfcInternal_Audit_Report.getReportUploadLogId(),
	                    hdfcInternal_Audit_Report.getUuid(),
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
	                    "Report Upload Log with Id: " +  hdfcInternal_Audit_Report.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<HDFCInternal_Audit_Report> indexer = IndexerRegistryUtil.nullSafeGetIndexer(HDFCInternal_Audit_Report.class);
				indexer.reindex(hdfcInternal_Audit_Report);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						HDFCInternal_Audit_Report.class.getName(), hdfcInternal_Audit_Report.getReportUploadLogId(), hdfcInternal_Audit_Report, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public HDFCInternal_Audit_Report updateHDFCInternal_Audit_ReportStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		HDFCInternal_Audit_Report hdfcInternal_Audit_Report = null;
		try {
			hdfcInternal_Audit_Report = hdfcInternal_Audit_ReportPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(hdfcInternal_Audit_Report)) {
			hdfcInternal_Audit_Report.setStatus(status);
			hdfcInternal_Audit_Report.setStatusByUserId(userId);
			hdfcInternal_Audit_Report.setStatusDate(new Date());
			hdfcInternal_Audit_Report.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				hdfcInternal_Audit_Report.setStatusByUserName(user.getFullName());
				hdfcInternal_Audit_Report.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			hdfcInternal_Audit_Report = hdfcInternal_Audit_ReportPersistence.update(hdfcInternal_Audit_Report);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(HDFCInternal_Audit_Report.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(HDFCInternal_Audit_Report.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return hdfcInternal_Audit_Report;
	}
	
	
public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, HDFCInternal_Audit_Report.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countCustIARByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(HDFCInternal_Audit_Report.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return hdfcInternal_Audit_ReportLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(HDFCInternal_Audit_Report.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return hdfcInternal_Audit_ReportLocalService.dynamicQueryCount(query);
	}
	
	public List<HDFCInternal_Audit_Report> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(HDFCInternal_Audit_Report.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return hdfcInternal_Audit_ReportLocalService.dynamicQuery(query);
	}
	
	
	
	Log _log = LogFactoryUtil.getLog(HDFCInternal_Audit_ReportLocalServiceImpl.class);

}