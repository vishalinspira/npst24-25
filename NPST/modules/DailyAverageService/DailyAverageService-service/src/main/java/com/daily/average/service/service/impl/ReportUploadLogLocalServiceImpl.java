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

import com.daily.average.service.exception.NoSuchReportUploadLogException;
import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.model.CustIAR;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.DAR;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportStatusLog;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogCustAMPFRDA;
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.ReportUploadLogGrievAMPFRDA;
import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.ReportUploadLogNPST;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.model.ReportUploadLogPFMCustodian;
import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.model.impl.ReportUploadLogImpl;
import com.daily.average.service.service.AnnualCompCertificateLocalService;
import com.daily.average.service.service.CustAnnualAuditReportLocalService;
import com.daily.average.service.service.CustIARLocalService;
import com.daily.average.service.service.CustodianCompFormLocalService;
import com.daily.average.service.service.DARLocalService;
import com.daily.average.service.service.InputQuarterlyIntervalLocalService;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalService;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportStatusLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogMakerLocalService;
import com.daily.average.service.service.ReportUploadLogNPSTLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.daily.average.service.service.base.ReportUploadLogLocalServiceBaseImpl;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Reference;

import nps.email.api.api.NpsEmailApi;
import nps.email.api.api.NpsSmsApi;
import nps.email.service.impl.NpsEmailServiceImpl;
import nps.email.service.impl.NpsSmsServiceImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ReportUploadLogLocalServiceImpl
	extends ReportUploadLogLocalServiceBaseImpl {
		
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @param createDate
	 * @param createdBy
	 * @param fileEntryId
	 * @param uploaded
	 * @return
	 */
	public ReportUploadLog addReportUploadLog(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded) {
		ReportUploadLog reportUploadLog = reportUploadLogPersistence.create(counterLocalService.increment(ReportUploadLog.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		//reportUploadLog.setUploaded(uploaded);
		if(uploaded) {
			reportUploadLog.setUploaded_i(1);
		}else{
			reportUploadLog.setUploaded_i(0);
		}
		
		return reportUploadLogPersistence.update(reportUploadLog);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @param createDate
	 * @param createdBy
	 * @param fileEntryId
	 * @param uploaded
	 * @param intermediaryname
	 * @param intermediaryid
	 * @return
	 */
	public ReportUploadLog addReportUploadLogWithIntermediary(long reportMasterId, Date reportDate, Date createDate, long createdBy, long fileEntryId, boolean uploaded, String intermediaryname, long intermediaryid) {
		ReportUploadLog reportUploadLog = reportUploadLogPersistence.create(counterLocalService.increment(ReportUploadLog.class.getName()));
		reportUploadLog.setReportMasterId(reportMasterId);
		reportUploadLog.setReportDate(reportDate);
		reportUploadLog.setCreateDate(createDate);
		reportUploadLog.setCreatedBy(createdBy);
		reportUploadLog.setFileEntryId(fileEntryId);
		reportUploadLog.setIntermediaryname(intermediaryname);
		reportUploadLog.setIntermediaryid(intermediaryid);
		//reportUploadLog.setUploaded(uploaded);
		if(uploaded) {
			reportUploadLog.setUploaded_i(1);
		}else{
			reportUploadLog.setUploaded_i(0);
		}
		
		return reportUploadLogPersistence.update(reportUploadLog);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @return
	 */
	public ReportUploadLog fetchByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportUploadLogPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param reportDate
	 * @param intermediaryid
	 * @return
	 */
	public ReportUploadLog fetchByReportMasterIdAndReportDateAndIntermediaryid(long reportMasterId, Date reportDate, long intermediaryid) {
		return reportUploadLogPersistence.fetchByReportMasterIdAndReportDateAndIntermediaryid(reportMasterId, reportDate, intermediaryid);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded_i
	 * @param intermediaryid
	 * @return
	 */
	public List<ReportUploadLog> fetchByReportMasterIdAndUploadedAndIntermediaryid(long reportMasterId, Integer uploaded, long intermediaryid) {
		return reportUploadLogPersistence.findByReportMasterIdAndUploadedAndIntermediaryid(reportMasterId, uploaded, intermediaryid);
	}
	
	/**
	 * 
	 * @param reportMasterId
	 * @param uploaded
	 * @return
	 */
	public List<ReportUploadLog> findByReportMasterIdAndUploaded(long reportMasterId, boolean uploaded) {
		int uploaded_i = 0;
		if(uploaded) {
			uploaded_i= 1;
		}else{
			uploaded_i = 0;
		}
		return reportUploadLogPersistence.findByReportMasterIdAndUploaded_i(reportMasterId, uploaded_i);
	}
	
	public ReportUploadLog findByFileEntryId(long fileEntryId) throws NoSuchReportUploadLogException {
		return reportUploadLogPersistence.findByFileEntryId(fileEntryId);
	}
	
	public ReportUploadLog updateReportUploadLog(int uploaded_i,Long reportUploadLogId) {
		ReportUploadLog  reportUploadLog = reportUploadLogPersistence.fetchByPrimaryKey(reportUploadLogId);
		reportUploadLog.setUploaded_i(uploaded_i);
		return reportUploadLogPersistence.update(reportUploadLog);
	}
	/**
	 * update ReportUploadLog Status
	 * @param status_
	 * @param reportUploadLogId
	 * @return
	 */
	public ReportUploadLog updateReportUploadLogStatus(String status_,Long reportUploadLogId) {
		ReportUploadLog  reportUploadLog = reportUploadLogPersistence.fetchByPrimaryKey(reportUploadLogId);
		reportUploadLog.setStatus_(status_);
		if(status_.equalsIgnoreCase("")) {
			reportUploadLog.setSubmitedToNPST(1);
		}
		_log.info("before #############################  ##########  in updateReportUploadLogStatus");
		ReportUploadLog uploadLog=reportUploadLogPersistence.update(reportUploadLog);
		_log.info(" after  #####  in updateReportUploadLogStatus");
		return uploadLog;
	}
	
	/**
	 * Update submitedToNPST
	 * @param submitedToNPST
	 * @param reportUploadLogId
	 * @return
	 */
	public ReportUploadLog updateReportUploadLogSubmitedToNPST(Integer submitedToNPST, Long reportUploadLogId) {
		ReportUploadLog  reportUploadLog = reportUploadLogPersistence.fetchByPrimaryKey(reportUploadLogId);
		reportUploadLog.setSubmitedToNPST(submitedToNPST);
		return reportUploadLog;
	}
	
	/**
	 * Find ReportUploadLog by submitedToNPST
	 * @param submitedToNPST
	 * @param reportUploadLogId
	 * @return
	 */
	public List<ReportUploadLog> findReportUploadLogAndSubmitedToNPST(Integer submitedToNPST, Long reportMasterId) {
		return reportUploadLogPersistence.findByReportMasterIdAndSubmitedToNPST(reportMasterId, submitedToNPST);
	}
	
	public void sendMailToUser(String status_, long reportUploadLogId, String role_name, long company_id) {
		_log.info("#############################  ##########  in sendMailToUser");
		ReportUploadLog  reportUploadLog = reportUploadLogPersistence.fetchByPrimaryKey(reportUploadLogId);
		ReportMaster reportMaster = reportMasterPersistence.fetchByPrimaryKey(reportUploadLog.getReportMasterId());
		if(Validator.isNotNull(reportMaster)) {
			_log.info("#############################  ########## "+reportMaster.toString());
			if(status_.trim().equals("") || status_ == null) {
				_log.info("############################# status is empty --- no mail sent");
			}else {
				NpsEmailApi mail_api = new NpsEmailServiceImpl();
				mail_api.sendRejectEmail(company_id, role_name, status_, reportMaster.getReportName(),reportUploadLogId);
				NpsSmsApi  npsSmsApi = new NpsSmsServiceImpl();
				npsSmsApi.sendRejectAndReviewSMS(company_id, role_name,status_, reportMaster.getReportName(),reportUploadLogId);
			}
		}else {
			_log.info("############################# report master is null ##########");
		}
	}
	
	
	
/**
 * 
 * @param classPK
 * @param entryClassName
 * @param assignedTo
 * @param isCompleted
 * @param forwaredToNpst
 * @param companyId
 * @param groupId
 */
	public void updateReportStatus(long classPK,String entryClassName,String assignedTo,boolean isCompleted,int forwaredToNpst,long companyId,long groupId){

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		try {
			WorkflowInstanceLink workflowInstanceLink=  WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(companyId, groupId, entryClassName, classPK);

			try {
					ReportUploadLogPFM reportUploadLogPfm = null;
					ReportUploadLogPFMAM reportUploadLogPfmAm = null;
					ReportUploadLogPFMCRA reportUploadLogPfmCra = null;
					InputQuarterlyInterval inputQuarterlyInterval = null;
					MnCompCertificate mnCompCertificate  = null;
					AnnualCompCertificate annualCompCertificate  = null;
					QtrStewardshipReport qtrStewardshipReport  =null;
					MnNpaDevelopment mnNpaDevelopment  = null;
					ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
					DAR dar = null;
					Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report =null;
					PFM_hy_comp_cert pfm_hy_comp_cert =null;
	
					ReportUploadLogCustodian reportUploadLogCustodian = null;
					ReportUploadLogPFMCustodian reportUploadLogPFMCustodian = null;
					CustodianCompForm custodianCompForm = null;
					CustAnnualAuditReport custAnnualAuditReport = null;
					ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = null;
					CustIAR custIAR = null;

					ReportUploadLogCRA reportUploadLogCra = null;
					ReportUploadLogGrievances reportUploadLogGrievances = null;
					ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;
					
					ReportUploadLogMaker reportUploadLogMaker = null;
					ReportUploadLogSupervisor reportUploadLogSupervisor = null;
					ReportUploadLogNPST reportUploadLogNpst = null;
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					Date reportUploadDate=null;
					int status=-1;
					String clazzName="";
		
					ReportUploadLog reportUploadLog = null;
					try {
						reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(classPK);
						reportMaster=reportMasterLocalService.getReportMaster(reportUploadLog.getReportMasterId());
					} catch (Exception e) {
						_log.info("Exception in getting reportUploadLog::: "+ classPK+"   --   " + e.getMessage());
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
						reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(classPK);
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
							reportUploadDate=reportUploadLogMaker.getCreateDate();
							status=reportUploadLogMaker.getStatus();
							clazzName=ReportUploadLogMaker.class.getName();
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
						reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(classPK);
						if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
							reportUploadDate=reportUploadLogSupervisor.getCreateDate();
							status=reportUploadLogSupervisor.getStatus();
							clazzName=ReportUploadLogSupervisor.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
						reportUploadLogNpst  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(classPK);
						if(reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNpst.getFileEntryId());
							reportUploadDate=reportUploadLogNpst.getCreateDate();
							status=reportUploadLogNpst.getStatus();
							clazzName=ReportUploadLogNPST.class.getName();
						}
					}
					
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							reportUploadLogCra  = reportUploadLogCraLocalService.getReportUploadLogCRA(classPK);
							if(reportUploadLogCra != null && reportUploadLogCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCra.getFileEntryId());
								reportUploadDate=reportUploadLogCra.getCreateDate();
								status=reportUploadLogCra.getStatus();
								clazzName=ReportUploadLogCRA.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
							reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(classPK);
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
								reportUploadDate=reportUploadLogGrievances.getCreateDate();
								status=reportUploadLogGrievances.getStatus();
								clazzName=ReportUploadLogGrievances.class.getName();
							}
						}
					/* Report Upload By Griev-AM-PFRDA */
					if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
							_log.info("entryClassName::::" + entryClassName);
							reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(classPK);
							if(reportUploadLogGrievAMPFRDA != null && reportUploadLogGrievAMPFRDA.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogGrievAMPFRDA.getCreateDate();
								status=reportUploadLogGrievAMPFRDA.getStatus();
								clazzName=ReportUploadLogGrievAMPFRDA.class.getName();
							}
						}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(classPK);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
						}
					}
				
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
						reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(classPK);
						if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogCustodian.getCreateDate();
							status=reportUploadLogCustodian.getStatus();
							clazzName=ReportUploadLogCustodian.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
						custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(classPK);
						if(custodianCompForm != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getQcfile_id());
							reportUploadDate=dlFileEntry.getCreateDate();
							status=custodianCompForm.getStatus();
							clazzName=CustodianCompForm.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
						custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(classPK);
						if(custAnnualAuditReport != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custAnnualAuditReport.getAudit_pdf_fileentry_id());
							reportUploadDate=dlFileEntry.getCreateDate();
							status=custAnnualAuditReport.getStatus();
							clazzName=CustAnnualAuditReport.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
						custIAR = custIARLocalService.getCustIAR(classPK);
						if(custIAR != null) {
							dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custIAR.getIar_file_id());
							reportUploadDate=dlFileEntry.getCreateDate();
							status=custIAR.getStatus();
							clazzName=CustIAR.class.getName();
						}
					}
					
					/* Report Upload By Cust-AM-PFRDA */
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName())) {
						reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(classPK);
						if(reportUploadLogCustAMPFRDA != null ) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustAMPFRDA.getFileEntryId());
							reportUploadDate=reportUploadLogCustAMPFRDA.getCreateDate();
							status=reportUploadLogCustAMPFRDA.getStatus();
							clazzName=ReportUploadLogCustAMPFRDA.class.getName();
						}
					}
					
					if( entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(classPK);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
						}
					}
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							reportUploadLogPfm  = reportUploadLogPfmLocalService.getReportUploadLogPFM(classPK);
							if(reportUploadLogPfm != null && reportUploadLogPfm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfm.getFileEntryId());
								reportUploadDate=reportUploadLogPfm.getCreateDate();
								status=reportUploadLogPfm.getStatus();
								clazzName=ReportUploadLogPFM.class.getName();
							}
						}
						//LOG.info("After PFM check ");
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							reportUploadLogPfmAm  = reportUploadLogPfmAmLocalService.getReportUploadLogPFMAM(classPK);
							if(reportUploadLogPfmAm != null && reportUploadLogPfmAm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmAm.getFileEntryId());
								reportUploadDate=reportUploadLogPfmAm.getCreateDate();
								status=reportUploadLogPfmAm.getStatus();
								clazzName=ReportUploadLogPFMAM.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							reportUploadLogPfmCra  = reportUploadLogPfmCraLocalService.getReportUploadLogPFMCRA(classPK);
							if(reportUploadLogPfmCra != null && reportUploadLogPfmCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmCra.getFileEntryId());
								reportUploadDate=reportUploadLogPfmCra.getCreateDate();
								status=reportUploadLogPfmCra.getStatus();
								clazzName=ReportUploadLogPFMCRA.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(classPK);
							if(inputQuarterlyInterval != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getFileEntryId());
								reportUploadDate=inputQuarterlyInterval.getCreatedate();
								status=inputQuarterlyInterval.getStatus();
								clazzName=InputQuarterlyInterval.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(classPK);
							if(mnCompCertificate != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
								reportUploadDate=mnCompCertificate.getCreatedon();
								status=mnCompCertificate.getStatus();
								clazzName=MnCompCertificate.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(classPK);
							if(annualCompCertificate!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(annualCompCertificate.getFileEntryId());
								reportUploadDate=annualCompCertificate.getCreatedate();
								status=annualCompCertificate.getStatus();
								clazzName=AnnualCompCertificate.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(classPK);
							if(qtrStewardshipReport!= null) {
								//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getAnnexure_c());
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getFileEntryId());
								reportUploadDate=qtrStewardshipReport.getCreatedon();
								status=qtrStewardshipReport.getStatus();
								clazzName=QtrStewardshipReport.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(classPK);
							if(mnNpaDevelopment!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnNpaDevelopment.getFileEntryId());
								reportUploadDate=mnNpaDevelopment.getCreatedon();
								status=mnNpaDevelopment.getStatus();
								clazzName=MnNpaDevelopment.class.getName();
							}
						}
						
						/* Report Upload By PFM-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
							_log.info("entryClassName :: "+entryClassName+" Application Id : "+classPK);
							reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(classPK);
							if(reportUploadLogPFMAMPFRDA != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogPFMAMPFRDA.getCreateDate();
								status=reportUploadLogPFMAMPFRDA.getStatus();
								clazzName=ReportUploadLogPFMAMPFRDA.class.getName();
							}
						}
						
						/* Detail audit report */
						if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
						    dar = darLocalService.fetchDAR(classPK);
							if(Validator.isNotNull(dar)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(dar.getFileEntryId());
								reportUploadDate=dar.getCreatedon();
								status=dar.getStatus();
								clazzName=DAR.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportLocalService.fetchPfm_Qr_Internal_Audit_Report(classPK);
							if(Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_Qr_Internal_Audit_Report.getFileEntryId());
								reportUploadDate=pfm_Qr_Internal_Audit_Report.getCreatedon();
								status=pfm_Qr_Internal_Audit_Report.getStatus();
								clazzName=Pfm_Qr_Internal_Audit_Report.class.getName();
							}
						}
						
						/* PFM Quaterly Internal audit report */
						if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							pfm_hy_comp_cert = pfm_hy_comp_certLocalService.fetchPFM_hy_comp_cert(classPK);
							if(Validator.isNotNull(pfm_hy_comp_cert)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_hy_comp_cert.getFileEntryId());
								reportUploadDate=pfm_hy_comp_cert.getCreatedon();
								status=pfm_hy_comp_cert.getStatus();
								clazzName=PFM_hy_comp_cert.class.getName();
							}
						}
					
						if(Validator.isNotNull(reportMaster)) {
							 getReportSummeryObject( reportUploadLog,reportMaster, dlFileEntry, dateFormat.format(reportUploadDate), status,"comment", 
									 workflowInstanceLink.getWorkflowInstanceId(), assignedTo,  reportUploadLog.getStatus_(),0,isCompleted,forwaredToNpst,clazzName);
						}
			}catch (Exception e) {
				_log.error("Exception on fetching log reports : "+e.getMessage());
			}
		} catch (Exception e) {
			_log.error("workflow exception : "+e.getMessage());
		}
		}
	
	
	
	
	/**
	 * 
	 * @param classPK
	 * @param entryClassName
	 * @param assignedTo
	 * @param isCompleted
	 * @param forwaredToNpst
	 */
	public void updateReportStatus(long classPK,String entryClassName,String assignedTo,boolean isCompleted,int forwaredToNpst){

		ServiceContext serviceContext=  ServiceContextThreadLocal.getServiceContext();
		long companyId=serviceContext.getCompanyId();
		long groupId=serviceContext.getScopeGroupId();
		//long userId=serviceContext.getUserId();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		try {
			WorkflowInstanceLink workflowInstanceLink=  WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(companyId, groupId, entryClassName, classPK);

			try {
					ReportUploadLogPFM reportUploadLogPfm = null;
					ReportUploadLogPFMAM reportUploadLogPfmAm = null;
					ReportUploadLogPFMCRA reportUploadLogPfmCra = null;
					InputQuarterlyInterval inputQuarterlyInterval = null;
					MnCompCertificate mnCompCertificate  = null;
					AnnualCompCertificate annualCompCertificate  = null;
					QtrStewardshipReport qtrStewardshipReport  =null;
					MnNpaDevelopment mnNpaDevelopment  = null;
					ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
					DAR dar = null;
					Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report =null;
					PFM_hy_comp_cert pfm_hy_comp_cert =null;
	
					ReportUploadLogCustodian reportUploadLogCustodian = null;
					ReportUploadLogPFMCustodian reportUploadLogPFMCustodian = null;
					CustodianCompForm custodianCompForm = null;
					CustAnnualAuditReport custAnnualAuditReport = null;
					ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = null;
					CustIAR custIAR = null;

					ReportUploadLogCRA reportUploadLogCra = null;
					ReportUploadLogGrievances reportUploadLogGrievances = null;
					ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;
					
					ReportUploadLogMaker reportUploadLogMaker = null;
					ReportUploadLogSupervisor reportUploadLogSupervisor = null;
					ReportUploadLogNPST reportUploadLogNpst = null;
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					Date reportUploadDate=null;
					int status=-1;
					String clazzName="";
		
					ReportUploadLog reportUploadLog = null;
					try {
						reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(classPK);
						reportMaster=reportMasterLocalService.getReportMaster(reportUploadLog.getReportMasterId());
					} catch (Exception e) {
						_log.info("Exception in getting reportUploadLog::: "+ classPK+"   --   " + e.getMessage());
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
						reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(classPK);
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
							reportUploadDate=reportUploadLogMaker.getCreateDate();
							status=reportUploadLogMaker.getStatus();
							clazzName=ReportUploadLogMaker.class.getName();
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
						reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(classPK);
						if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
							reportUploadDate=reportUploadLogSupervisor.getCreateDate();
							status=reportUploadLogSupervisor.getStatus();
							clazzName=ReportUploadLogSupervisor.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
						reportUploadLogNpst  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(classPK);
						if(reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNpst.getFileEntryId());
							reportUploadDate=reportUploadLogNpst.getCreateDate();
							status=reportUploadLogNpst.getStatus();
							clazzName=ReportUploadLogNPST.class.getName();
						}
					}
					
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							reportUploadLogCra  = reportUploadLogCraLocalService.getReportUploadLogCRA(classPK);
							if(reportUploadLogCra != null && reportUploadLogCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCra.getFileEntryId());
								reportUploadDate=reportUploadLogCra.getCreateDate();
								status=reportUploadLogCra.getStatus();
								clazzName=ReportUploadLogCRA.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
							reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(classPK);
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
								reportUploadDate=reportUploadLogGrievances.getCreateDate();
								status=reportUploadLogGrievances.getStatus();
								clazzName=ReportUploadLogGrievances.class.getName();
							}
						}
					/* Report Upload By Griev-AM-PFRDA */
					if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
							_log.info("entryClassName::::" + entryClassName);
							reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(classPK);
							if(reportUploadLogGrievAMPFRDA != null && reportUploadLogGrievAMPFRDA.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogGrievAMPFRDA.getCreateDate();
								status=reportUploadLogGrievAMPFRDA.getStatus();
								clazzName=ReportUploadLogGrievAMPFRDA.class.getName();
							}
						}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(classPK);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
						}
					}
				
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
						reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(classPK);
						if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogCustodian.getCreateDate();
							status=reportUploadLogCustodian.getStatus();
							clazzName=ReportUploadLogCustodian.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
						custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(classPK);
						if(custodianCompForm != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getQcfile_id());
							reportUploadDate=dlFileEntry.getCreateDate();
							status=custodianCompForm.getStatus();
							clazzName=CustodianCompForm.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
						custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(classPK);
						if(custAnnualAuditReport != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custAnnualAuditReport.getAudit_pdf_fileentry_id());
							reportUploadDate=dlFileEntry.getCreateDate();
							status=custAnnualAuditReport.getStatus();
							clazzName=CustAnnualAuditReport.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
						custIAR = custIARLocalService.getCustIAR(classPK);
						if(custIAR != null) {
							dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custIAR.getIar_file_id());
							reportUploadDate=dlFileEntry.getCreateDate();
							status=custIAR.getStatus();
							clazzName=CustIAR.class.getName();
						}
					}
					
					/* Report Upload By Cust-AM-PFRDA */
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName())) {
						reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(classPK);
						if(reportUploadLogCustAMPFRDA != null ) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustAMPFRDA.getFileEntryId());
							reportUploadDate=reportUploadLogCustAMPFRDA.getCreateDate();
							status=reportUploadLogCustAMPFRDA.getStatus();
							clazzName=ReportUploadLogCustAMPFRDA.class.getName();
						}
					}
					
					if( entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(classPK);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
						}
					}
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							reportUploadLogPfm  = reportUploadLogPfmLocalService.getReportUploadLogPFM(classPK);
							if(reportUploadLogPfm != null && reportUploadLogPfm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfm.getFileEntryId());
								reportUploadDate=reportUploadLogPfm.getCreateDate();
								status=reportUploadLogPfm.getStatus();
								clazzName=ReportUploadLogPFM.class.getName();
							}
						}
						//LOG.info("After PFM check ");
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							reportUploadLogPfmAm  = reportUploadLogPfmAmLocalService.getReportUploadLogPFMAM(classPK);
							if(reportUploadLogPfmAm != null && reportUploadLogPfmAm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmAm.getFileEntryId());
								reportUploadDate=reportUploadLogPfmAm.getCreateDate();
								status=reportUploadLogPfmAm.getStatus();
								clazzName=ReportUploadLogPFMAM.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							reportUploadLogPfmCra  = reportUploadLogPfmCraLocalService.getReportUploadLogPFMCRA(classPK);
							if(reportUploadLogPfmCra != null && reportUploadLogPfmCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmCra.getFileEntryId());
								reportUploadDate=reportUploadLogPfmCra.getCreateDate();
								status=reportUploadLogPfmCra.getStatus();
								clazzName=ReportUploadLogPFMCRA.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(classPK);
							if(inputQuarterlyInterval != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getFileEntryId());
								reportUploadDate=inputQuarterlyInterval.getCreatedate();
								status=inputQuarterlyInterval.getStatus();
								clazzName=InputQuarterlyInterval.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(classPK);
							if(mnCompCertificate != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
								reportUploadDate=mnCompCertificate.getCreatedon();
								status=mnCompCertificate.getStatus();
								clazzName=MnCompCertificate.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(classPK);
							if(annualCompCertificate!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(annualCompCertificate.getFileEntryId());
								reportUploadDate=annualCompCertificate.getCreatedate();
								status=annualCompCertificate.getStatus();
								clazzName=AnnualCompCertificate.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(classPK);
							if(qtrStewardshipReport!= null) {
								//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getAnnexure_c());
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getFileEntryId());
								reportUploadDate=qtrStewardshipReport.getCreatedon();
								status=qtrStewardshipReport.getStatus();
								clazzName=QtrStewardshipReport.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(classPK);
							if(mnNpaDevelopment!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnNpaDevelopment.getFileEntryId());
								reportUploadDate=mnNpaDevelopment.getCreatedon();
								status=mnNpaDevelopment.getStatus();
								clazzName=MnNpaDevelopment.class.getName();
							}
						}
						
						/* Report Upload By PFM-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
							_log.info("entryClassName :: "+entryClassName+" Application Id : "+classPK);
							reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(classPK);
							if(reportUploadLogPFMAMPFRDA != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogPFMAMPFRDA.getCreateDate();
								status=reportUploadLogPFMAMPFRDA.getStatus();
								clazzName=ReportUploadLogPFMAMPFRDA.class.getName();
							}
						}
						
						/* Detail audit report */
						if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
						    dar = darLocalService.fetchDAR(classPK);
							if(Validator.isNotNull(dar)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(dar.getFileEntryId());
								reportUploadDate=dar.getCreatedon();
								status=dar.getStatus();
								clazzName=DAR.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportLocalService.fetchPfm_Qr_Internal_Audit_Report(classPK);
							if(Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_Qr_Internal_Audit_Report.getFileEntryId());
								reportUploadDate=pfm_Qr_Internal_Audit_Report.getCreatedon();
								status=pfm_Qr_Internal_Audit_Report.getStatus();
								clazzName=Pfm_Qr_Internal_Audit_Report.class.getName();
							}
						}
						
						/* PFM Quaterly Internal audit report */
						if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							pfm_hy_comp_cert = pfm_hy_comp_certLocalService.fetchPFM_hy_comp_cert(classPK);
							if(Validator.isNotNull(pfm_hy_comp_cert)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_hy_comp_cert.getFileEntryId());
								reportUploadDate=pfm_hy_comp_cert.getCreatedon();
								status=pfm_hy_comp_cert.getStatus();
								clazzName=PFM_hy_comp_cert.class.getName();
							}
						}
					
						if(Validator.isNotNull(reportMaster)) {
							 getReportSummeryObject( reportUploadLog,reportMaster, dlFileEntry, dateFormat.format(reportUploadDate), status,"comment", 
									 workflowInstanceLink.getWorkflowInstanceId(), assignedTo,  reportUploadLog.getStatus_(),0,isCompleted,forwaredToNpst,clazzName);
						}
			}catch (Exception e) {
				_log.error("Exception on fetching log reports : "+e.getMessage());
			}
		} catch (Exception e) {
			_log.error("workflow exception : "+e.getMessage());
		}
		}
	
	/**
	 * 
	 * @param reportUploadLog
	 * @param reportMaster
	 * @param dlFileEntry
	 * @param reportUploadDate
	 * @param reportStatus
	 * @param comment
	 * @param workflowInstanceId
	 * @param assignedTo
	 * @param statusKey
	 * @param assignedToId
	 * @param workflowTaskId
	 * @param isApproved
	 * @param forwaredToNpst
	 * @param clazzName
	 */
private void getReportSummeryObject( ReportUploadLog reportUploadLog,ReportMaster reportMaster, DLFileEntry dlFileEntry, 
		String reportUploadDate, int reportStatus, String comment, long workflowInstanceId, String assignedTo, String statusKey,
		long assignedToId,boolean isApproved,int forwaredToNpst,String clazzName) {
	ReportStatusLog reportStatusLog=ReportStatusLogLocalServiceUtil.fetchReportStatusLog(reportUploadLog.getReportUploadLogId());
	if(Validator.isNull(reportStatusLog)) {
		reportStatusLog=ReportStatusLogLocalServiceUtil.createReportStatusLog(reportUploadLog.getReportUploadLogId());
	}
	reportStatusLog.setReportUploadLogId(reportUploadLog.getReportUploadLogId());
	reportStatusLog.setReportMasterId(reportUploadLog.getReportMasterId());
	reportStatusLog.setCreatedBy(dlFileEntry.getUserId());
	reportStatusLog.setCreateDate(dlFileEntry.getCreateDate());
	reportStatusLog.setReportDate(reportUploadLog.getReportDate());
	reportStatusLog.setFileEntryId(dlFileEntry.getFileEntryId());
	reportStatusLog.setStatus(reportStatus);
	reportStatusLog.setStatus_((statusKey != null && !statusKey.isEmpty() ? statusKey.toUpperCase() : WorkflowConstants.getStatusLabel(reportStatus).toUpperCase()));
	reportStatusLog.setIntermediaryname(reportUploadLog.getIntermediaryname());
	reportStatusLog.setIntermediaryid(reportUploadLog.getIntermediaryid());
	reportStatusLog.setIntermediarytype(reportMaster.getIntermediarytype()+"");	
	reportStatusLog.setDepartment(reportMaster.getDepartment());
	reportStatusLog.setPfrdaReport(reportMaster.getPfrdaReport());
	reportStatusLog.setReportName(reportMaster.getReportName());
	reportStatusLog.setReportType(reportMaster.getReportType());
	reportStatusLog.setUploaderRole(reportMaster.getUploaderRole());
	reportStatusLog.setCra(reportMaster.getCra());
	reportStatusLog.setWorkflowInstanceId(workflowInstanceId);
	reportStatusLog.setAssignedTo(assignedTo);
	reportStatusLog.setSubmitedToNPST(reportUploadLog.getSubmitedToNPST());
	reportStatusLog.setRemarks(comment);
	reportStatusLog.setUserName(dlFileEntry.getUserName());
	reportStatusLog.setFileUrl("/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
	reportStatusLog.setIsApproved(isApproved==true?1:0);
	reportStatusLog.setSubmitedToNPST(forwaredToNpst);
	reportStatusLog.setClazzName(clazzName);
	ReportStatusLogLocalServiceUtil.updateReportStatusLog(reportStatusLog);
	_log.info("report status saved for : "+reportUploadLog.getReportUploadLogId());
}
		
	public List<ReportUploadLog> getByUploaded_i(int uploaded_i){
		return reportUploadLogPersistence.findByUploaded_i(uploaded_i);
	}
	
	public int countByUploaded_i(int uploaded_i){
		return reportUploadLogPersistence.countByUploaded_i(uploaded_i);
	}
	
	public List<Long> getReportUploadLogIdsByUploaded_i(int uploaded_i) {
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("uploaded_i", uploaded_i));
		query.setProjection(ProjectionFactoryUtil.property("reportUploadLogId"));
		
		return reportUploadLogLocalService.dynamicQuery(query);
	}
	
	public long countByReportMasterIdsAndUploaded_i(List<Long> ids,int uploaded_i){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("uploaded_i", uploaded_i));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogLocalService.dynamicQueryCount(query);
	}
	
	public List<ReportUploadLog> getByReportMasterIdsAndUploaded_i(List<Long> ids,int uploaded_i){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("uploaded_i", uploaded_i));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
		
		return reportUploadLogLocalService.dynamicQuery(query);
	}
	
	public List<ReportUploadLog> getByReportUploadLogIds(List<Long> ids){
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
		
		return reportUploadLogLocalService.dynamicQuery(query);
	}
	
	public List<ReportUploadLog> getMonthlyReportUploadLog(Date fromDate, Date toDate, List<Long> reportMasterIds, int uploaded_i){
		if(reportMasterIds.isEmpty()) {
			return null;
		}
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.between("reportDate", fromDate, toDate));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", reportMasterIds));
		query.add(RestrictionsFactoryUtil.eq("uploaded_i", uploaded_i));
		return reportUploadLogLocalService.dynamicQuery(query);
	}
	
	public double countMonthlyReportUploadLog(Date fromDate, Date toDate, List<Long> reportMasterIds, int uploaded_i){
		if(reportMasterIds.isEmpty()) {
			return 0;
		}
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.between("reportDate", fromDate, toDate));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", reportMasterIds));
		query.add(RestrictionsFactoryUtil.eq("uploaded_i", uploaded_i));
		return reportUploadLogLocalService.dynamicQueryCount(query);
	}
	/**
	 * Fetch the month wise count using intermediary ids and all other parameters
	 * @param fromDate
	 * @param toDate
	 * @param reportMasterIds
	 * @param uploaded_i
	 * @param intermediaryIds
	 * @return
	 */
	public double countMonthlyReportUploadLog(Date fromDate, Date toDate, List<Long> reportMasterIds, int uploaded_i, long intermediaryId){
		if(reportMasterIds.isEmpty()) {
			return 0;
		}
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ReportUploadLogImpl.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.between("reportDate", fromDate, toDate));
		query.add(RestrictionsFactoryUtil.in("reportMasterId", reportMasterIds));
		query.add(RestrictionsFactoryUtil.eq("intermediaryid", intermediaryId));
		query.add(RestrictionsFactoryUtil.eq("uploaded_i", uploaded_i));
		return reportUploadLogLocalService.dynamicQueryCount(query);
	}
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;


	@Reference
	private ReportUploadLogPFMLocalService reportUploadLogPfmLocalService;

	@Reference
	private RoleLocalService roleLocalService;

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private Portal portal;

	@Reference
	private DLFileEntryLocalService dlFileEntryLocalService;

	@Reference
	private ReportUploadLogPFMAMLocalService reportUploadLogPfmAmLocalService;

	@Reference
	private ReportUploadLogPFMCRALocalService reportUploadLogPfmCraLocalService;

	@Reference
	private ReportUploadLogCRALocalService reportUploadLogCraLocalService;

	@Reference
	private ReportUploadLogGrievancesLocalService reportUploadLogGrievancesLocalService;

	@Reference
	private ReportUploadLogCustodianLocalService reportUploadLogCustodianLocalService;

	@Reference
	private CustodianCompFormLocalService custodianCompFormLocalService;

	@Reference
	private CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;

	@Reference
	private CustIARLocalService custIARLocalService;

	@Reference
	private ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService;

	@Reference
	private InputQuarterlyIntervalLocalService  inputQuarterlyIntervalLocalService;

	@Reference
	private MnCompCertificateLocalService mnCompCertificateLocalService;

	@Reference
	private AnnualCompCertificateLocalService annualCompCertificateLocalService;

	@Reference
	private QtrStewardshipReportLocalService qtrStewardshipReportLocalService;

	@Reference
	private MnNpaDevelopmentLocalService mnNpaDevelopmentLocalService;

	@Reference
	private ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;

	@Reference
	private ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDALocalService;

	@Reference
	private ReportUploadLogPFMAMPFRDALocalService reportUploadLogPFMAMPFRDALocalService;

	@Reference
	private DARLocalService darLocalService;

	@Reference
	private ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;

	@Reference
	private Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;

	@Reference
	private PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
	@Reference
	private ReportUploadLogMakerLocalService reportUploadLogMakerLocalService;

	@Reference
	private ReportUploadLogNPSTLocalService reportUploadLogNPSTLocalService;
	

	
	private static final Log _log = LogFactoryUtil.getLog(ReportUploadLogLocalServiceImpl.class);
	
	
}