package com.nps.report.status.portlet;

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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.nps.report.status.constants.ReportStatusPortletKeys;
import com.nps.report.status.util.NPSDashboardUtil;
import com.nps.report.status.util.NPSTUserUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NameMappingConstants;

/**
 * @author VishalKumar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ReportStatus",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ReportStatusPortletKeys.REPORTSTATUS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ReportStatusPortlet extends MVCPortlet {
	private static final Log _log = LogFactoryUtil.getLog(ReportStatusPortlet.class);
	@Override
		public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
				throws IOException, PortletException {

		_log.info("in do view method");
		PrintWriter writer=resourceResponse.getWriter();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		try {
			List<WorkflowTask> allList = WorkflowTaskManagerUtil.getWorkflowTasks(serviceContext.getCompanyId(), Boolean.FALSE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(true));
			List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(serviceContext.getCompanyId(), Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(true));
		if(Validator.isNotNull(completed)) {
			allList.addAll(completed);
		}
		allList=allList.stream().sorted(Comparator.comparing(WorkflowTask::getCreateDate).reversed()).collect(Collectors.toList());
		_log.info("workflow tasklist size  : "+allList.size());
		Set<Long> addedWorkflowTasks = new HashSet<>();
		for (WorkflowTask itr : allList) {
			try {
				
				Map<String, Serializable> maps = itr.getOptionalAttributes();
				long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
				
				if(!addedWorkflowTasks.contains(applicationId)) {
					_log.debug("workflow itr : "+itr.getWorkflowInstanceId()+"  application id: "+applicationId);
					String entryClassName = String.valueOf(maps.get("entryClassName"));
					//LOG.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
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
						_log.info("Exception in getting reportUploadLog::: "+ applicationId+"   --   " + e.getMessage());
					}
					
				if(true) {

					if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
						reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
						if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
							reportUploadDate=reportUploadLogMaker.getCreateDate();
							status=reportUploadLogMaker.getStatus();
							clazzName=ReportUploadLogMaker.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogMaker.getReportMasterId());
						}
					}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
						reportUploadLogSupervisor  = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(applicationId);
						if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
							reportUploadDate=reportUploadLogSupervisor.getCreateDate();
							status=reportUploadLogSupervisor.getStatus();
							clazzName=ReportUploadLogSupervisor.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogSupervisor.getReportMasterId());
						}
					}
					if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
						reportUploadLogNpst  = reportUploadLogNPSTLocalService.getReportUploadLogNPST(applicationId);
						if(reportUploadLogNpst != null && reportUploadLogNpst.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNpst.getFileEntryId());
							reportUploadDate=reportUploadLogNpst.getCreateDate();
							status=reportUploadLogNpst.getStatus();
							clazzName=ReportUploadLogNPST.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogNpst.getReportMasterId());
						}
					}
					
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							reportUploadLogCra  = reportUploadLogCraLocalService.getReportUploadLogCRA(applicationId);
							if(reportUploadLogCra != null && reportUploadLogCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCra.getFileEntryId());
								reportUploadDate=reportUploadLogCra.getCreateDate();
								status=reportUploadLogCra.getStatus();
								clazzName=ReportUploadLogCRA.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCra.getReportMasterId());
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
							reportUploadLogGrievances  = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
								reportUploadDate=reportUploadLogGrievances.getCreateDate();
								status=reportUploadLogGrievances.getStatus();
								clazzName=ReportUploadLogGrievances.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievances.getReportMasterId());
							}
						}
					/* Report Upload By Griev-AM-PFRDA */
					if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
							_log.info("entryClassName::::" + entryClassName);
							reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(applicationId);
							if(reportUploadLogGrievAMPFRDA != null && reportUploadLogGrievAMPFRDA.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogGrievAMPFRDA.getCreateDate();
								status=reportUploadLogGrievAMPFRDA.getStatus();
								clazzName=ReportUploadLogGrievAMPFRDA.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogGrievAMPFRDA.getReportMasterId());
							}
						}
					
					if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
						}
					}
				
					if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
						reportUploadLogCustodian  = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
						if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogCustodian.getCreateDate();
							status=reportUploadLogCustodian.getStatus();
							clazzName=ReportUploadLogCustodian.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustodian.getReportMasterId());
						}
					}
					if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
						custodianCompForm  = custodianCompFormLocalService.getCustodianCompForm(applicationId);
						if(custodianCompForm != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getQcfile_id());
							reportUploadDate=itr.getCreateDate();
							status=custodianCompForm.getStatus();
							clazzName=CustodianCompForm.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(custodianCompForm.getReportMasterId());
						}
					}
					if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
						custAnnualAuditReport  = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
						if(custAnnualAuditReport != null) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(custAnnualAuditReport.getAudit_pdf_fileentry_id());
							reportUploadDate=itr.getCreateDate();
							status=custAnnualAuditReport.getStatus();
							clazzName=CustAnnualAuditReport.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(custAnnualAuditReport.getReportMasterId());
						}
					}
					if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
						custIAR = custIARLocalService.getCustIAR(applicationId);
						if(custIAR != null) {
							dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custIAR.getIar_file_id());
							reportUploadDate=itr.getCreateDate();
							status=custIAR.getStatus();
							clazzName=CustIAR.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(custIAR.getReportMasterId());
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
							//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogCustAMPFRDA.getReportMasterId());
						}
					}
					
					if( entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
						reportUploadLogPFMCustodian  = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
						if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
							dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
							reportUploadDate=reportUploadLogPFMCustodian.getCreateDate();
							status=reportUploadLogPFMCustodian.getStatus();
							clazzName=ReportUploadLogPFMCustodian.class.getName();
							//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMCustodian.getReportMasterId());
						}
					}
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							reportUploadLogPfm  = reportUploadLogPfmLocalService.getReportUploadLogPFM(applicationId);
							if(reportUploadLogPfm != null && reportUploadLogPfm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfm.getFileEntryId());
								reportUploadDate=reportUploadLogPfm.getCreateDate();
								status=reportUploadLogPfm.getStatus();
								clazzName=ReportUploadLogPFM.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfm.getReportMasterId());
							}
						}
						//LOG.info("After PFM check ");
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							reportUploadLogPfmAm  = reportUploadLogPfmAmLocalService.getReportUploadLogPFMAM(applicationId);
							if(reportUploadLogPfmAm != null && reportUploadLogPfmAm.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmAm.getFileEntryId());
								reportUploadDate=reportUploadLogPfmAm.getCreateDate();
								status=reportUploadLogPfmAm.getStatus();
								clazzName=ReportUploadLogPFMAM.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfmAm.getReportMasterId());
							}
						}

						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							reportUploadLogPfmCra  = reportUploadLogPfmCraLocalService.getReportUploadLogPFMCRA(applicationId);
							if(reportUploadLogPfmCra != null && reportUploadLogPfmCra.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPfmCra.getFileEntryId());
								reportUploadDate=reportUploadLogPfmCra.getCreateDate();
								status=reportUploadLogPfmCra.getStatus();
								clazzName=ReportUploadLogPFMCRA.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPfmCra.getReportMasterId());
							}
						}

						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
							if(inputQuarterlyInterval != null) {
								//dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getAnnex_iv());
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getFileEntryId());
								reportUploadDate=inputQuarterlyInterval.getCreatedate();
								status=inputQuarterlyInterval.getStatus();
								clazzName=InputQuarterlyInterval.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(inputQuarterlyInterval.getReportMasterId());
							}
						}

						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
							if(mnCompCertificate != null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
								reportUploadDate=mnCompCertificate.getCreatedon();
								status=mnCompCertificate.getStatus();
								clazzName=MnCompCertificate.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(mnCompCertificate.getReportMasterId());
							}
						}

						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
							if(annualCompCertificate!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(annualCompCertificate.getFileEntryId());
								reportUploadDate=annualCompCertificate.getCreatedate();
								status=annualCompCertificate.getStatus();
								clazzName=AnnualCompCertificate.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(annualCompCertificate.getReportMasterId());
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
								//reportMaster = reportMasterLocalService.getReportMaster(qtrStewardshipReport.getReportMasterId());
							}
						}

						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							mnNpaDevelopment  = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
							if(mnNpaDevelopment!= null) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(mnNpaDevelopment.getFileEntryId());
								reportUploadDate=mnNpaDevelopment.getCreatedon();
								status=mnNpaDevelopment.getStatus();
								clazzName=MnNpaDevelopment.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(mnNpaDevelopment.getReportMasterId());
							}
						}
						
						/* Report Upload By PFM-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
							_log.info("entryClassName :: "+entryClassName+" Application Id : "+applicationId);
							reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
							if(reportUploadLogPFMAMPFRDA != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
								reportUploadDate=reportUploadLogPFMAMPFRDA.getCreateDate();
								status=reportUploadLogPFMAMPFRDA.getStatus();
								clazzName=ReportUploadLogPFMAMPFRDA.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(reportUploadLogPFMAMPFRDA.getReportMasterId());
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
								//reportMaster = reportMasterLocalService.getReportMaster(dar.getReportMasterId());
							}
						}

						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportLocalService.fetchPfm_Qr_Internal_Audit_Report(applicationId);
							if(Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_Qr_Internal_Audit_Report.getFileEntryId());
								reportUploadDate=pfm_Qr_Internal_Audit_Report.getCreatedon();
								status=pfm_Qr_Internal_Audit_Report.getStatus();
								clazzName=Pfm_Qr_Internal_Audit_Report.class.getName();
								//reportMaster = reportMasterLocalService.getReportMaster(pfm_Qr_Internal_Audit_Report.getReportMasterId());
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
								//reportMaster = reportMasterLocalService.getReportMaster(pfm_hy_comp_cert.getReportMasterId());
							}
						}
					}

					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
					
					String comment = npstUserUtil.getKaleoComment(serviceContext.getCompanyId(), itr.getWorkflowInstanceId(), comparator);
					String assignedTo = npstUserUtil.getCurrentAssigneeUserName(serviceContext.getCompanyId(), itr.getWorkflowInstanceId(), comparator);
					long assignedToId = npstUserUtil.getCurrentAssigneeUserId(serviceContext.getCompanyId(), itr.getWorkflowInstanceId(), comparator);
					int forwaredToNpst=npstUserUtil.getForwardedToNPST(serviceContext.getCompanyId(), itr.getWorkflowInstanceId(), comparator);
	
						if(Validator.isNotNull(reportMaster)) {
							 getReportSummeryObject( reportUploadLog,reportMaster, dlFileEntry, dateFormat.format(reportUploadDate), status,comment, 
									itr.getWorkflowInstanceId(), assignedTo,  reportUploadLog.getStatus_(),assignedToId,itr.getWorkflowTaskId(),itr.isCompleted(),forwaredToNpst,clazzName);
						}
				}
				addedWorkflowTasks.add(applicationId);
			}catch (Exception e) {
				_log.error("Exception on fetching log reports : "+e.getMessage());
			}
		}
		_log.info("add applicationId size : - "+addedWorkflowTasks.size());
		} catch (WorkflowException e) {
			// TODO Auto-generated catch block
			_log.error("workflow exception : "+e.getMessage());
		}
		writer.write("Data has been migrated successfully");
		writer.close();
		}
	
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
		_log.debug("report status saved for : "+reportUploadLog.getReportUploadLogId());
	}
	
	
	@Reference
	private ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private NPSTUserUtil npstUserUtil;
	
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
	private NPSDashboardUtil npsDashboardUtil;
	
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