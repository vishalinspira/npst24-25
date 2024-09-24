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
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.PFM_hy_comp_certImpl;
import com.daily.average.service.service.base.PFM_hy_comp_certLocalServiceBaseImpl;
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
public class PFM_hy_comp_certLocalServiceImpl
	extends PFM_hy_comp_certLocalServiceBaseImpl {
	
	/**
	 * Add Update Hy_comp_cert
	 * @param reportUploadLogId
	 * @param year
	 * @param hycc_details
	 * @param date1
	 * @param date2
	 * @param date3
	 * @param hycc_place
	 * @param hyccfile
	 * @return
	 */
	public PFM_hy_comp_cert addHy_comp_cert(long reportUploadLogId, String year, String hycc_details, 
			Date date1, Date date2, Date date3, String hycc_place, long hyccfile) {
	
	
		PFM_hy_comp_cert  pfm_hy_comp_cert = pfm_hy_comp_certPersistence.fetchByPrimaryKey(reportUploadLogId);
		
		if (pfm_hy_comp_cert == null) {
			pfm_hy_comp_cert = pfm_hy_comp_certPersistence.create(reportUploadLogId);
		}
		pfm_hy_comp_cert.setYear(year);
		pfm_hy_comp_cert.setHycc_details(hycc_details);
		pfm_hy_comp_cert.setDate1(date1);
		pfm_hy_comp_cert.setDate2(date2);
		pfm_hy_comp_cert.setDate3(date3);
		pfm_hy_comp_cert.setHycc_place(hycc_place);
		pfm_hy_comp_cert.setHyccfile(hyccfile);
		
		pfm_hy_comp_cert = pfm_hy_comp_certPersistence.updateImpl(pfm_hy_comp_cert);
		
		return pfm_hy_comp_cert;

	}
	
	public PFM_hy_comp_cert updateReportUploadLog(Date createDate, long createdBy, long fileEntryId,
			boolean uploaded, long reportUploadLogId, int status, long statusByUserId, String statusByUserName,
			Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload) {
		PFM_hy_comp_cert pfm_hy_comp_cert = null;
		try {
			pfm_hy_comp_cert = pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(reportUploadLogId);
		} catch (PortalException e) {
			// _log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {
			//pfm_hy_comp_cert = pfm_hy_comp_certLocalService.createpfm_hy_comp_cert(reportUploadLogId);			
			pfm_hy_comp_cert.setReportMasterId(reportUploadLog.getReportMasterId());
			pfm_hy_comp_cert.setReportDate(reportUploadLog.getReportDate());
			pfm_hy_comp_cert.setCreatedon(createDate);
			pfm_hy_comp_cert.setCreatedby(createdBy);
			pfm_hy_comp_cert.setStatus(status);
			pfm_hy_comp_cert.setStatusByUserId(statusByUserId);
			pfm_hy_comp_cert.setStatusByUserName(statusByUserName);
			pfm_hy_comp_cert.setStatusDate(statusDate);
			pfm_hy_comp_cert.setRemarks(remarks);
			pfm_hy_comp_cert.setFileEntryId(fileEntryId);
			
			
			pfm_hy_comp_cert = pfm_hy_comp_certPersistence.update(pfm_hy_comp_cert);
			//reportUploadLog.setUploaded(true);
			/*reportUploadLog.setUploaded_i(1);*/
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForPFM_hy_comp_cert(pfm_hy_comp_cert, serviceContext, createdBy);
		}else {
			pfm_hy_comp_cert.setCreatedon(createDate);
			pfm_hy_comp_cert.setCreatedby(createdBy);
			
			pfm_hy_comp_cert.setStatus(status);
			pfm_hy_comp_cert.setStatusByUserId(statusByUserId);
			pfm_hy_comp_cert.setStatusByUserName(statusByUserName);
			pfm_hy_comp_cert.setStatusDate(statusDate);
			pfm_hy_comp_cert.setRemarks(remarks);
			pfm_hy_comp_cert.setFileEntryId(fileEntryId);
			
			pfm_hy_comp_cert = pfm_hy_comp_certPersistence.update(pfm_hy_comp_cert);
			//reportUploadLog.setUploaded(true);
			reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogPersistence.update(reportUploadLog));
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			String workflowContext = pfm_hy_comp_cert.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		return pfm_hy_comp_cert;
	}
	
	public void createAssetForPFM_hy_comp_cert(PFM_hy_comp_cert pfm_hy_comp_cert, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(pfm_hy_comp_cert)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    PFM_hy_comp_cert.class.getName(),
	                    pfm_hy_comp_cert.getReportUploadLogId(),
	                    pfm_hy_comp_cert.getUuid(),
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
	                    "Report Upload Log with Id: " +  pfm_hy_comp_cert.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<PFM_hy_comp_cert> indexer = IndexerRegistryUtil.nullSafeGetIndexer(PFM_hy_comp_cert.class);
				indexer.reindex(pfm_hy_comp_cert);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						PFM_hy_comp_cert.class.getName(), pfm_hy_comp_cert.getReportUploadLogId(), pfm_hy_comp_cert, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public PFM_hy_comp_cert updatePFM_hy_comp_certStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		PFM_hy_comp_cert pfm_hy_comp_cert = null;
		try {
			pfm_hy_comp_cert = pfm_hy_comp_certPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(pfm_hy_comp_cert)) {
			pfm_hy_comp_cert.setStatus(status);
			pfm_hy_comp_cert.setStatusByUserId(userId);
			pfm_hy_comp_cert.setStatusDate(new Date());
			pfm_hy_comp_cert.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				pfm_hy_comp_cert.setStatusByUserName(user.getFullName());
				pfm_hy_comp_cert.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			pfm_hy_comp_cert = pfm_hy_comp_certPersistence.update(pfm_hy_comp_cert);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(PFM_hy_comp_cert.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(PFM_hy_comp_cert.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return pfm_hy_comp_cert;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, PFM_hy_comp_cert.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
		//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
		
		//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
		long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
		OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
		List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
		long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
		WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
		
	}
	
	public long countCustIARByIdsAndStatus(List<Long> ids, int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(PFM_hy_comp_certImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return pfm_hy_comp_certLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(PFM_hy_comp_certImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return pfm_hy_comp_certLocalService.dynamicQueryCount(query);
	}
	
	public List<PFM_hy_comp_cert> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(PFM_hy_comp_certImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return pfm_hy_comp_certLocalService.dynamicQuery(query);
	}
	
	public List<PFM_hy_comp_cert> getByReportUploadlogIdAndIssubmitted(long reportUploadLogId,boolean isSubmitted ){
		return pfm_hy_comp_certPersistence.findByreportUploadLogIdAndIssubmitted(reportUploadLogId, isSubmitted);
		
	}
	
	Log _log = LogFactoryUtil.getLog(PFM_hy_comp_certLocalServiceImpl.class);
	
	


}