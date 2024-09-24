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
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.base.AnnualCompCertificateLocalServiceBaseImpl;
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
public class AnnualCompCertificateLocalServiceImpl
	extends AnnualCompCertificateLocalServiceBaseImpl {
	public AnnualCompCertificate saveAnnualCompCertificate(Date date_1,String address,String eligibilityia,String eligibilityib,String eligibilityic,String eligibilityid,String eligibilityie,String eligibilityif,
			String eligibilityig,String eligibilityih,String eligibilityii,String eligibilityij,String eligibilityik,String eligibilityil,String eligibilityim,String eligibilityin,String eligibilityio,String eligibilityip,String eligibilityiq,String eligibilityir,String eligibilityis,String booka,String bookb,String bookc,String audita,String auditb,String auditc,String stewardshipa,String stewardshipb,String stewardshipc,String othersa,String othersb,String othersc,String othersd,String otherse,String othersf,String meetingdate,String eligibilityIa_rem,String eligibilityIb_rem,String eligibilityIc_rem,String eligibilityId_rem,String eligibilityIe_rem,
			String eligibilityIf_rem,String eligibilityIg_rem,String eligibilityIh_rem,String eligibilityIi_rem,String eligibilityIj_rem,String eligibilityIk_rem,String eligibilityIl_rem,String eligibilityIm_rem,String eligibilityIn_rem,String eligibilityIo_rem,String eligibilityIp_rem,String eligibilityIq_rem,String eligibilityIr_rem,String eligibilityIs_rem,String bookIIa_rem,String bookIIb_rem,String bookIIc_rem,String audita_rem,String auditb_rem,String auditc_rem,String stewardshipa_rem,String stewardshipb_rem,String stewardshipc_rem,String othersa_rem,String othersb_rem,String othersc_rem,String othersd_rem,String otherse_rem,String othersf_rem,String emplolyee_name,String roles,Date date_2,String place,long annexurei,long annexureii,long annexureiii,long annexureiv,long annexurev,Date createdate,long createdby,long reportUploadLogId, long reportMasterId, Date reportDate) {

		AnnualCompCertificate annualComp = annualCompCertificateLocalService.fetchAnnualCompCertificate(reportUploadLogId);
		if(annualComp == null) {
			annualComp = annualCompCertificatePersistence.create(reportUploadLogId);
		}
		
		
		//eligibility::::::::::::::::::
		annualComp.setDate_1(date_1);
		annualComp.setAddress(address);
		annualComp.setEligibilityia(eligibilityia);
		annualComp.setEligibilityib(eligibilityib);
		annualComp.setEligibilityic(eligibilityic);
		annualComp.setEligibilityid(eligibilityid);
		annualComp.setEligibilityie(eligibilityie);
		annualComp.setEligibilityif(eligibilityif);
		annualComp.setEligibilityig(eligibilityig);
		annualComp.setEligibilityih(eligibilityih);
		annualComp.setEligibilityii(eligibilityii);
		annualComp.setEligibilityij(eligibilityij);
		annualComp.setEligibilityik(eligibilityik);
		annualComp.setEligibilityil(eligibilityil);
		annualComp.setEligibilityim(eligibilityim);
		annualComp.setEligibilityin(eligibilityin);
		annualComp.setEligibilityio(eligibilityio);
		annualComp.setEligibilityip(eligibilityip);
		annualComp.setEligibilityiq(eligibilityiq);
		annualComp.setEligibilityir(eligibilityir);
		annualComp.setEligibilityis(eligibilityis);
		
		annualComp.setEligibilityIa_rem(eligibilityIa_rem);
		annualComp.setEligibilityIb_rem(eligibilityIb_rem);
		annualComp.setEligibilityIc_rem(eligibilityIc_rem);
		annualComp.setEligibilityId_rem(eligibilityId_rem);
		annualComp.setEligibilityIe_rem(eligibilityIe_rem);
		annualComp.setEligibilityIf_rem(eligibilityIf_rem);
		annualComp.setEligibilityIg_rem(eligibilityIg_rem);
		annualComp.setEligibilityIh_rem(eligibilityIh_rem);
		annualComp.setEligibilityIi_rem(eligibilityIi_rem);
		annualComp.setEligibilityIj_rem(eligibilityIj_rem);
		annualComp.setEligibilityIk_rem(eligibilityIk_rem);
		annualComp.setEligibilityIl_rem(eligibilityIl_rem);
		annualComp.setEligibilityIm_rem(eligibilityIm_rem);
		annualComp.setEligibilityIn_rem(eligibilityIn_rem);
		annualComp.setEligibilityIo_rem(eligibilityIo_rem);
		annualComp.setEligibilityIp_rem(eligibilityIp_rem);
		annualComp.setEligibilityIq_rem(eligibilityIq_rem);
		annualComp.setEligibilityIr_rem(eligibilityIr_rem);
		annualComp.setEligibilityIs_rem(eligibilityIs_rem);
		
		//book
		annualComp.setBooka(booka);
		annualComp.setBookb(bookb);
		annualComp.setBookc(bookc);
		
		annualComp.setBookIIa_rem(bookIIa_rem);
		annualComp.setBookIIb_rem(bookIIb_rem);
		annualComp.setBookIIc_rem(bookIIc_rem);
		
		//audit
		annualComp.setAudita(audita);
		annualComp.setAuditb(auditb);
		annualComp.setAuditc(auditc);
		
		annualComp.setAudita_rem(audita_rem);
		annualComp.setAuditb_rem(auditb_rem);
		annualComp.setAuditc_rem(auditc_rem);
		
		//stewardshipa
		annualComp.setStewardshipa(stewardshipa);
		annualComp.setStewardshipb(stewardshipb);
		annualComp.setStewardshipc(stewardshipc);
		
		annualComp.setStewardshipa_rem(stewardshipa_rem);
		annualComp.setStewardshipb_rem(stewardshipb_rem);
		annualComp.setStewardshipc_rem(stewardshipc_rem);
		
		
		//othres 
		annualComp.setOthersa(othersa);
		annualComp.setOthersb(othersb);
		annualComp.setOthersc(othersc);
		annualComp.setOthersd(othersd);
		annualComp.setOtherse(otherse);
		annualComp.setOthersf(othersf);
		
		annualComp.setOthersa_rem(othersa_rem);
		annualComp.setOthersb_rem(othersb_rem);
		annualComp.setOthersc_rem(othersc_rem);
		annualComp.setOthersd_rem(othersd_rem);
		annualComp.setOtherse_rem(otherse_rem);
		annualComp.setOthersf_rem(othersf_rem);
		
		
		
		
		annualComp.setMeetingdate(meetingdate);
		annualComp.setEmplolyee_name(emplolyee_name);
		annualComp.setPlace(place);
		annualComp.setRoles(roles);
		annualComp.setDate_2(date_2);
		
		//documents
		annualComp.setAnnexurei(annexurei);
		annualComp.setAnnexureii(annexureii);
		annualComp.setAnnexureiii(annexureiii);
		annualComp.setAnnexureiv(annexureiv);
		annualComp.setAnnexurev(annexurev);
		
		annualComp.setReportMasterId(reportMasterId);
		annualComp.setReportDate(reportDate);
		
		_log.info("annualComp:::::::::::::::::::::::::::::"+annualComp.toString());
		
		return annualCompCertificatePersistence.update(annualComp);
	}

	public AnnualCompCertificate updateReportUploadLog(Date createDate, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
			String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks,boolean reupload) {
		AnnualCompCertificate annualComp = null;
		try {
			annualComp = annualCompCertificateLocalService.getAnnualCompCertificate(reportUploadLogId);
		} catch (PortalException e) {
			_log.error(e);
		}
		
		ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
		if(reupload) {
			//annualComp = annualCompCertificateLocalService.createAnnualCompCertificate(reportUploadLogId);			
			annualComp.setReportMasterId(reportUploadLog.getReportMasterId());
			annualComp.setReportDate(reportUploadLog.getReportDate());
			annualComp.setCreatedate(createDate);
			annualComp.setCreatedby(createdBy);
			annualComp.setStatus(status);
			annualComp.setStatusByUserId(statusByUserId);
			annualComp.setStatusByUserName(statusByUserName);
			annualComp.setStatusDate(statusDate);
			annualComp.setRemarks(remarks);
			annualComp.setFileEntryId(fileEntryId);
			
			annualComp = annualCompCertificatePersistence.update(annualComp);
			//reportUploadLog.setUploaded(true);
			//reportUploadLog.setUploaded_i(1);
			
			_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
			//reportUploadLogPersistence.flush();
			reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			createAssetForAnnualCompCertificate(annualComp, serviceContext, createdBy);
		}else {
			annualComp.setCreatedate(createDate);
			annualComp.setCreatedby(createdBy);
			
			annualComp.setStatus(status);
			annualComp.setStatusByUserId(statusByUserId);
			annualComp.setStatusByUserName(statusByUserName);
			annualComp.setStatusDate(statusDate);
			annualComp.setRemarks(remarks);
			annualComp.setFileEntryId(fileEntryId);
			
			annualComp = annualCompCertificatePersistence.update(annualComp);
			//reportUploadLog.setUploaded(true);
			//reportUploadLog.setUploaded_i(1);
			
			//_log.info(reportUploadLogPersistence.updateImpl(reportUploadLog));
			
			try {
				reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId);
				reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
			} catch (Exception e) {
				_log.error("Exception in updating reportUploadLog::" + e.getMessage());
			}
			
			
			String workflowContext = annualComp.getWorkflowContext();
			try {
				workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
			} catch (PortalException e) {
				 _log.error(e);
			}
			
			
		}
		
		
		return annualComp;
	}
	
	public void createAssetForAnnualCompCertificate(AnnualCompCertificate annualComp, ServiceContext serviceContext, long CreatedBy) {
		
		
		if(Validator.isNotNull(annualComp)) {
			AssetEntry assetEntry = null;
			try {
				assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
	                    serviceContext.getScopeGroupId(),
	                    new Date(),
	                    new Date(),
	                    AnnualCompCertificate.class.getName(),
	                    annualComp.getReportUploadLogId(),
	                    annualComp.getUuid(),
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
	                    "Report Upload Log with Id: " +  annualComp.getReportUploadLogId(),
	                    null,
	                    null,
	                    null,
	                    0,
	                    0,
	                    null);

				Indexer<AnnualCompCertificate> indexer = IndexerRegistryUtil.nullSafeGetIndexer(AnnualCompCertificate.class);
				indexer.reindex(annualComp);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
						AnnualCompCertificate.class.getName(), annualComp.getReportUploadLogId(), annualComp, serviceContext);
				
				

			} catch (Exception e) {
				 _log.error(e);
			}
		}	
		
	}
	
	public AnnualCompCertificate updateAnnualCompCertificateStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
		AnnualCompCertificate annualComp = null;
		try {
			annualComp = annualCompCertificatePersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(annualComp)) {
			annualComp.setStatus(status);
			annualComp.setStatusByUserId(userId);
			annualComp.setStatusDate(new Date());
			annualComp.setWorkflowContext(workflowContext);

			User user;

			try {
				user = userLocalService.getUser(userId);
				annualComp.setStatusByUserName(user.getFullName());
				annualComp.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			annualComp = annualCompCertificatePersistence.update(annualComp);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(AnnualCompCertificate.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(AnnualCompCertificate.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return annualComp;
	}
	
	public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
		
		String transitionName = "Reupload";
		Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
		
		OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
		List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, AnnualCompCertificate.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
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
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(AnnualCompCertificate.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		query.add(RestrictionsFactoryUtil.eq("status", status));
		return annualCompCertificateLocalService.dynamicQueryCount(query);
	}
	
	public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(AnnualCompCertificate.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return annualCompCertificateLocalService.dynamicQueryCount(query);
	}
	
	public List<AnnualCompCertificate> getByReportUploadlogIdAndIssubmitted(long reportUploadLogId,boolean isSubmitted ){
		return annualCompCertificatePersistence.findByreportUploadLogIdAndIssubmitted(reportUploadLogId, isSubmitted);
		
	}
	
	public List<AnnualCompCertificate> getByReportMasterIdsAndStatus(List<Long> ids,int status){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(AnnualCompCertificate.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("status", status));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return annualCompCertificateLocalService.dynamicQuery(query);
		
	}
}