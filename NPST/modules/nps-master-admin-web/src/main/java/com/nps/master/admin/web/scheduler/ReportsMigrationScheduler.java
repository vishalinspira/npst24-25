package com.nps.master.admin.web.scheduler;

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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalServiceUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.constants.NameMappingConstants;
import nps.common.service.util.CommonRoleService;

@Component(immediate = true, service = BaseMessageListener.class)
public class ReportsMigrationScheduler extends BaseMessageListener {
	private static final Log LOG = LogFactoryUtil.getLog(ReportsMigrationScheduler.class);

	//private static final String DEFAULT_CRON_EXPRESSION = "0 */10 * * * ?";
	private static final String DEFAULT_CRON_EXPRESSION = "0 */2 * * * ?";

	private TriggerFactory _triggerFactory;

	private volatile boolean _initialized;

	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
		_triggerFactory = triggerFactory;
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(SchedulerEngineHelper schedulerEngineHelper) {
		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		try {
			String listenerClass = getClass().getName();
			Trigger jobTrigger = _triggerFactory.createTrigger(listenerClass, listenerClass, new Date(), null,
					DEFAULT_CRON_EXPRESSION);
			SchedulerEntryImpl schedulerEntryImpl = new SchedulerEntryImpl(listenerClass, jobTrigger);

			if (_initialized) {
				deactive();
			}

			_schedulerEngineHelper.register(this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);

			LOG.debug("Registered Scheduler with Cron Expression: " + DEFAULT_CRON_EXPRESSION);

			_initialized = true;
		} catch (Exception e) {
			LOG.error("Registered Scheduler with Cron Expression. Error: " + e.getMessage());
		}

		// createNewReportLogs();
	}

	@Deactivate
	protected void deactive() {
		if (_initialized) {
			try {
				_schedulerEngineHelper.unregister(this);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}

		_initialized = false;
	}

	@Override
	protected void doReceive(Message message) {
		LOG.debug("scheduler called.");
		//Commented for UAT testing
		reportMigrate();
	}
	
	
	
	public void reportMigrate(){
		long companyId=20097;
		LOG.debug("in reportMigrate method");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		try {
			List<WorkflowTask> allList = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.FALSE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
			List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
		if(Validator.isNotNull(completed)) {
			allList.addAll(completed);
		}
		allList=allList.stream().sorted(Comparator.comparing(WorkflowTask::getWorkflowTaskId).reversed()).collect(Collectors.toList());
		LOG.debug("workflow tasklist size  : "+allList.size());
		Set<Long> addedWorkflowTasks = new HashSet<>();
		for (WorkflowTask itr : allList) {
			try {
				
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				
				if(!addedWorkflowTasks.contains(applicationId)) {
					LOG.debug("workflow itr : "+itr.getWorkflowInstanceId()+"  application id: "+applicationId);
					String entryClassName = String.valueOf(maps.get("entryClassName"));
					//LOG.debug("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
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
						reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(applicationId);
						reportMaster=reportMasterLocalService.getReportMaster(reportUploadLog.getReportMasterId());
					} catch (Exception e) {
						LOG.debug("Exception in getting reportUploadLog::: "+ applicationId+"   --   " + e.getMessage());
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
						reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
							reportUploadDate=reportUploadLogMaker.getCreateDate();
							status=reportUploadLogMaker.getStatus();
							clazzName=ReportUploadLogMaker.class.getName();
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
						reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
						if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
							reportUploadDate=reportUploadLogSupervisor.getCreateDate();
							status=reportUploadLogSupervisor.getStatus();
							clazzName=ReportUploadLogSupervisor.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
						reportUploadLogNpst  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(applicationId);
						if(reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNpst.getFileEntryId());
							reportUploadDate=reportUploadLogNpst.getCreateDate();
							status=reportUploadLogNpst.getStatus();
							clazzName=ReportUploadLogNPST.class.getName();
						}
					}
					
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							reportUploadLogCra  = reportUploadLogCraLocalService.getReportUploadLogCRA(applicationId);
							if(reportUploadLogCra != null && reportUploadLogCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCra.getFileEntryId());
								reportUploadDate=reportUploadLogCra.getCreateDate();
								status=reportUploadLogCra.getStatus();
								clazzName=ReportUploadLogCRA.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
							reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
								reportUploadDate=reportUploadLogGrievances.getCreateDate();
								status=reportUploadLogGrievances.getStatus();
								clazzName=ReportUploadLogGrievances.class.getName();
							}
						}
					/* Report Upload By Griev-AM-PFRDA */
					if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
							LOG.debug("entryClassName::::" + entryClassName);
							reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(applicationId);
							if(reportUploadLogGrievAMPFRDA != null && reportUploadLogGrievAMPFRDA.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogGrievAMPFRDA.getCreateDate();
								status=reportUploadLogGrievAMPFRDA.getStatus();
								clazzName=ReportUploadLogGrievAMPFRDA.class.getName();
							}
						}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
						}
					}
				
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
						reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
						if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogCustodian.getCreateDate();
							status=reportUploadLogCustodian.getStatus();
							clazzName=ReportUploadLogCustodian.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
						custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(applicationId);
						if(custodianCompForm != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getQcfile_id());
							reportUploadDate=itr.getCreateDate();
							status=custodianCompForm.getStatus();
							clazzName=CustodianCompForm.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
						custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
						if(custAnnualAuditReport != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custAnnualAuditReport.getAudit_pdf_fileentry_id());
							reportUploadDate=itr.getCreateDate();
							status=custAnnualAuditReport.getStatus();
							clazzName=CustAnnualAuditReport.class.getName();
						}
					}
					if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
						custIAR = custIARLocalService.getCustIAR(applicationId);
						if(custIAR != null) {
							dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custIAR.getIar_file_id());
							reportUploadDate=itr.getCreateDate();
							status=custIAR.getStatus();
							clazzName=CustIAR.class.getName();
						}
					}
					
					/* Report Upload By Cust-AM-PFRDA */
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName())) {
						reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(applicationId);
						if(reportUploadLogCustAMPFRDA != null ) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustAMPFRDA.getFileEntryId());
							reportUploadDate=reportUploadLogCustAMPFRDA.getCreateDate();
							status=reportUploadLogCustAMPFRDA.getStatus();
							clazzName=ReportUploadLogCustAMPFRDA.class.getName();
						}
					}
					
					if( entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
						}
					}
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							reportUploadLogPfm  = reportUploadLogPfmLocalService.getReportUploadLogPFM(applicationId);
							if(reportUploadLogPfm != null && reportUploadLogPfm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfm.getFileEntryId());
								reportUploadDate=reportUploadLogPfm.getCreateDate();
								status=reportUploadLogPfm.getStatus();
								clazzName=ReportUploadLogPFM.class.getName();
							}
						}
						//LOG.debug("After PFM check ");
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							reportUploadLogPfmAm  = reportUploadLogPfmAmLocalService.getReportUploadLogPFMAM(applicationId);
							if(reportUploadLogPfmAm != null && reportUploadLogPfmAm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmAm.getFileEntryId());
								reportUploadDate=reportUploadLogPfmAm.getCreateDate();
								status=reportUploadLogPfmAm.getStatus();
								clazzName=ReportUploadLogPFMAM.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							reportUploadLogPfmCra  = reportUploadLogPfmCraLocalService.getReportUploadLogPFMCRA(applicationId);
							if(reportUploadLogPfmCra != null && reportUploadLogPfmCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmCra.getFileEntryId());
								reportUploadDate=reportUploadLogPfmCra.getCreateDate();
								status=reportUploadLogPfmCra.getStatus();
								clazzName=ReportUploadLogPFMCRA.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
							if(inputQuarterlyInterval != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getFileEntryId());
								reportUploadDate=inputQuarterlyInterval.getCreatedate();
								status=inputQuarterlyInterval.getStatus();
								clazzName=InputQuarterlyInterval.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
							if(mnCompCertificate != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
								reportUploadDate=mnCompCertificate.getCreatedon();
								status=mnCompCertificate.getStatus();
								clazzName=MnCompCertificate.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
							if(annualCompCertificate!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(annualCompCertificate.getFileEntryId());
								reportUploadDate=annualCompCertificate.getCreatedate();
								status=annualCompCertificate.getStatus();
								clazzName=AnnualCompCertificate.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(applicationId);
							if(qtrStewardshipReport!= null) {
								//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getAnnexure_c());
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getFileEntryId());
								reportUploadDate=qtrStewardshipReport.getCreatedon();
								status=qtrStewardshipReport.getStatus();
								clazzName=QtrStewardshipReport.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
							if(mnNpaDevelopment!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnNpaDevelopment.getFileEntryId());
								reportUploadDate=mnNpaDevelopment.getCreatedon();
								status=mnNpaDevelopment.getStatus();
								clazzName=MnNpaDevelopment.class.getName();
							}
						}
						
						/* Report Upload By PFM-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
							LOG.debug("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
							reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
							if(reportUploadLogPFMAMPFRDA != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogPFMAMPFRDA.getCreateDate();
								status=reportUploadLogPFMAMPFRDA.getStatus();
								clazzName=ReportUploadLogPFMAMPFRDA.class.getName();
							}
						}
						
						/* Detail audit report */
						if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
						    dar = darLocalService.fetchDAR(applicationId);
							if(Validator.isNotNull(dar)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(dar.getFileEntryId());
								reportUploadDate=dar.getCreatedon();
								status=dar.getStatus();
								clazzName=DAR.class.getName();
							}
						}

						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportLocalService.fetchPfm_Qr_Internal_Audit_Report(applicationId);
							if(Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_Qr_Internal_Audit_Report.getFileEntryId());
								reportUploadDate=pfm_Qr_Internal_Audit_Report.getCreatedon();
								status=pfm_Qr_Internal_Audit_Report.getStatus();
								clazzName=Pfm_Qr_Internal_Audit_Report.class.getName();
							}
						}
						
						/* PFM Quaterly Internal audit report */
						if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							pfm_hy_comp_cert = pfm_hy_comp_certLocalService.fetchPFM_hy_comp_cert(applicationId);
							if(Validator.isNotNull(pfm_hy_comp_cert)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_hy_comp_cert.getFileEntryId());
								reportUploadDate=pfm_hy_comp_cert.getCreatedon();
								status=pfm_hy_comp_cert.getStatus();
								clazzName=PFM_hy_comp_cert.class.getName();
							}
						}
					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
					String comment = getKaleoComment(companyId, itr.getWorkflowInstanceId(), comparator);
					String assignedTo = getCurrentAssigneeUserName(companyId, itr.getWorkflowInstanceId(), comparator);
					int forwaredToNpst=getForwardedToNPST(companyId, itr.getWorkflowInstanceId(), comparator);
						if(Validator.isNotNull(reportMaster)) {
							 getReportSummeryObject( reportUploadLog,reportMaster, dlFileEntry, dateFormat.format(reportUploadDate), status,comment, 
									itr.getWorkflowInstanceId(), assignedTo,  reportUploadLog.getStatus_(),0,itr.getWorkflowTaskId(),itr.isCompleted(),forwaredToNpst,clazzName);
						}
				}
				addedWorkflowTasks.add(applicationId);
			}catch (Exception e) {
				LOG.error("Exception on fetching log reports : "+e.getMessage());
			}
		}
		LOG.debug("add applicationId size : - "+addedWorkflowTasks.size());
		} catch (WorkflowException e) {
			LOG.error("workflow exception : "+e.getMessage());
		}
		}
/**
 * 
 * @param companyId
 * @param workflowInstanceId
 * @param comparator
 * @return
 */
	private String getKaleoComment(long companyId, long workflowInstanceId, OrderByComparator<KaleoLog> comparator) {
		List<Integer> logTypes = new ArrayList<>();
		String comment = StringPool.BLANK;
		logTypes.add(KaleoLogUtil.convert("TASK_COMPLETION"));
		List<KaleoLog> kaleoLogs = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, workflowInstanceId, logTypes, QueryUtil.ALL_POS, KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogsCount(companyId, workflowInstanceId, logTypes)-1, comparator);
		kaleoLogs=kaleoLogs.stream().sorted(Comparator.comparing(KaleoLog::getCreateDate).reversed()).collect(Collectors.toList());
		if(kaleoLogs!=null && !kaleoLogs.isEmpty()) {
			KaleoLog kaleoLog = kaleoLogs.get(0);
			comment = kaleoLog.getComment();
		}
		return comment;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param workflowInstantId
	 * @param comparator
	 * @return
	 */
	private String getCurrentAssigneeUserName(long companyId, long workflowInstantId, OrderByComparator<KaleoLog> comparator) {
		String assignedTo = StringPool.BLANK;
		List<Integer> logTypesAssignment = new ArrayList<>();
		logTypesAssignment.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
		
		List<KaleoLog> kaleoLogsAssignment = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, 
				workflowInstantId, logTypesAssignment, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
		kaleoLogsAssignment=kaleoLogsAssignment.stream().sorted(Comparator.comparing(KaleoLog::getCreateDate).reversed()).collect(Collectors.toList());
		if(kaleoLogsAssignment != null && !kaleoLogsAssignment.isEmpty()) {
			assignedTo = getUserName(kaleoLogsAssignment.get(0).getCurrentAssigneeClassPK(),kaleoLogsAssignment.get(0).getCurrentAssigneeClassName());

		}
		return assignedTo;
	}
	
	/**
	 * 
	 * @param classPK
	 * @param className
	 * @return
	 */
	private String getUserName(long classPK, String className) {
		long userId = 0;
		if(className.equalsIgnoreCase(Role.class.getName())) {
			Role role = RoleLocalServiceUtil.fetchRole(classPK);
			if(Validator.isNotNull(role)) {
				return role.getName();
			}
		}else if(className.equalsIgnoreCase(User.class.getName())) {
			userId = classPK;
		}
		if(userId > 0) {
			try {
				return userLocalService.getUser(userId).getFullName();
			} catch (PortalException e) {
				LOG.debug("Exception on getting user name : "+e.getMessage());
			}
		}
		return StringPool.BLANK;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param workflowInstantId
	 * @param comparator
	 * @return
	 */
	private int getForwardedToNPST(long companyId, long workflowInstantId, OrderByComparator<KaleoLog> comparator) {
		List<Integer> logTypesAssignment = new ArrayList<>();
		logTypesAssignment.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
		
		List<KaleoLog> kaleoLogsAssignment = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, workflowInstantId, logTypesAssignment, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
		kaleoLogsAssignment=kaleoLogsAssignment.stream().sorted(Comparator.comparing(KaleoLog::getCreateDate).reversed()).collect(Collectors.toList());
		if(kaleoLogsAssignment != null && !kaleoLogsAssignment.isEmpty()) {
			String className=kaleoLogsAssignment.get(0).getCurrentAssigneeClassName();
			long classPk=kaleoLogsAssignment.get(0).getCurrentAssigneeClassPK();
			
			long userId = 0;
			if(kaleoLogsAssignment.get(0).getCurrentAssigneeClassName().equalsIgnoreCase(Role.class.getName())) {
				Role role = RoleLocalServiceUtil.fetchRole(classPk);
				if(Validator.isNotNull(role)) {
					 if(role.getName().equalsIgnoreCase(NPSTRoleConstants.NPST_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.NPST_DGM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.NPST_GM) ||
							 role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_DGM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_GM) ||
							 role.getName().equalsIgnoreCase(NPSTRoleConstants.PFM_MGR) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CRA_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CRA_DGM) ||
							 role.getName().equalsIgnoreCase(NPSTRoleConstants.CRA_GM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_AM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_DGM) || 
					 role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_GM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.GRIEVANCES_MGR) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_AM) ||
					 role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_DGM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_GM) || role.getName().equalsIgnoreCase(NPSTRoleConstants.CUSTODIAN_GM)) {
						 return 1;
					 }
				}

			}else if(className.equalsIgnoreCase(User.class.getName())) {
				userId = classPk;
			}
			if(userId > 0) {
				boolean isNonNPsUser=CommonRoleService.isNonNpsUser(userId);
				if(isNonNPsUser) {
					return 0;
				}else {
					return 1;
				}
			}
		}
		return 0;
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
			long assignedToId,long workflowTaskId,boolean isApproved,int forwaredToNpst,String clazzName) {
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
		
		reportStatusLog.setReportType(reportMaster.getReportType());
		reportStatusLog.setUploaderRole(reportMaster.getUploaderRole());
		String craName=NameMappingConstants.CRA_NAME_MAP.get(reportMaster.getCra());
		if(Validator.isNull(craName) || craName=="" ) {
			craName=reportMaster.getCra();
		}
		String reportName=reportMaster.getReportName();
		long masterid=reportMaster.getReportMasterId();
		if(masterid==11 || masterid==23 || masterid==128 || masterid==3 || masterid==17 || masterid==18 ||masterid==4 || masterid==19 || masterid==124 ||masterid==7 || masterid==20 || masterid==125 ||masterid==8 || masterid==21 || masterid==126 ||masterid==10 || masterid==22 || masterid==127 ) {
			reportName=reportName+" "+craName;
		}
		reportStatusLog.setCra(craName);
		reportStatusLog.setReportName(reportName);
		reportStatusLog.setWorkflowInstanceId(workflowInstanceId);
		reportStatusLog.setAssignedTo(assignedTo);
		reportStatusLog.setSubmitedToNPST(reportUploadLog.getSubmitedToNPST());
		reportStatusLog.setRemarks(comment);
		reportStatusLog.setUserName(dlFileEntry.getUserName());
		reportStatusLog.setTaskId(workflowTaskId+"");
		reportStatusLog.setFileUrl("/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
		reportStatusLog.setIsApproved(isApproved==true?1:0);
		reportStatusLog.setSubmitedToNPST(forwaredToNpst);
		reportStatusLog.setClazzName(clazzName);
		ReportStatusLogLocalServiceUtil.updateReportStatusLog(reportStatusLog);
		LOG.debug("report status saved for : "+reportUploadLog.getReportUploadLogId());
	}

	@Reference
	private ReportMasterLocalService reportMasterLocalService;

	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;

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
	
	}