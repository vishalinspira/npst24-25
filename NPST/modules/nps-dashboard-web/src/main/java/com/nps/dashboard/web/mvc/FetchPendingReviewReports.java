package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.model.CustIAR;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.DAR;
import com.daily.average.service.model.HDFCInternal_Audit_Report;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.Manpowerform_i;
import com.daily.average.service.model.Manpowerform_ii;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
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
import com.daily.average.service.service.HDFCInternal_Audit_ReportLocalService;
import com.daily.average.service.service.InputQuarterlyIntervalLocalService;
import com.daily.average.service.service.Manpowerform_iLocalService;
import com.daily.average.service.service.Manpowerform_iiLocalService;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalService;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogNPSTLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexResponse.DetailsAnalyzer;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalServiceUtil;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NameMappingConstants;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_PENDING_REVIEW_REPORTS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class FetchPendingReviewReports  extends BaseMVCResourceCommand {
	private static final Log LOG = LogFactoryUtil.getLog(FetchPendingReviewReports.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		LOG.info("Calling FetchPendingReviewReports");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		User user = PortalUtil.getUser(resourceRequest);
		Company company = PortalUtil.getCompany(resourceRequest);
        long companyId = company.getCompanyId();
        
		JSONObject jsonPendingDocs = getPendingDocs(resourceRequest, themeDisplay, dateFormat, user, companyId);
		try {
			resourceResponse.getWriter().println(jsonPendingDocs);
			resourceResponse.getWriter().flush();
		} catch (Exception e) {
			LOG.error("Error Sending Feedbacks" + e.getMessage());
		}
		
	}

	public Map<Long, ReportMaster> getReportMasterMap(){
		Map<Long, ReportMaster> reportMastersMap=new HashMap<Long, ReportMaster>();
		List<ReportMaster> reportMasters=ReportMasterLocalServiceUtil.getReportMasters(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		for(ReportMaster master: reportMasters) {
			reportMastersMap.put(master.getReportMasterId(), master);
		}
		return reportMastersMap;
		
	}
	public JSONObject getPendingDocs(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, DateFormat dateFormat, User user, long companyId) throws PortalException {
		JSONObject pendingReports = JSONFactoryUtil.createJSONObject();
		
		Map<Long, ReportMaster> reportMastersMap =getReportMasterMap();
		
		long assigneeUserId = user.getUserId();
        Boolean isCompleted = false;
        String department = ParamUtil.getString(resourceRequest, "department", NPSCompany.NPST);
        LOG.info("Department :: "+department);
			try {
				List<WorkflowTask> assigneToMe = WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId, assigneeUserId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				List<WorkflowTask> assigneToMe2 = WorkflowTaskManagerUtil.getWorkflowTasksByUser(companyId, assigneeUserId, isCompleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				
				assigneToMe.addAll(assigneToMe2);
				LOG.info("assigneToMe : "+assigneToMe);
				for (WorkflowTask itr : assigneToMe) {
					try {
					String url = getWorkflowUrl(resourceRequest, themeDisplay);
					Map<String, Serializable> maps = itr.getOptionalAttributes();
					long applicationId = Long.parseLong(String.valueOf(maps.get("entryClassPK")));
					String entryClassName = String.valueOf(maps.get("entryClassName"));
					
					LOG.debug("Task ID : "+itr.getWorkflowTaskId());
					LOG.debug("Class Name : "+entryClassName);
					LOG.debug("Class PK : "+applicationId);
					
					String comment = StringPool.BLANK;
					String dueDate = StringPool.BLANK;
					String intermediaryName = StringPool.BLANK;
					Date createDate = new Date();
					
					ReportMaster reportMaster = null;
					DLFileEntry dlFileEntry = null;
					if(department.equalsIgnoreCase(NPSCompany.NPST)) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
							ReportUploadLogMaker reportUploadLogMaker = null;
							try {
								reportUploadLogMaker = ReportUploadLogMakerLocalServiceUtil.getReportUploadLogMaker(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogMaker != null && reportUploadLogMaker.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogMaker.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogMaker.getReportMasterId());
								comment = reportUploadLogMaker.getRemarks();
								dueDate = dateFormat.format(reportUploadLogMaker.getReportDate());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogSupervisor.class.getName())) {
							ReportUploadLogSupervisor reportUploadLogSupervisor = null;
							try {
								reportUploadLogSupervisor = ReportUploadLogSupervisorLocalServiceUtil.getReportUploadLogSupervisor(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogSupervisor != null && reportUploadLogSupervisor.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogSupervisor.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogSupervisor.getReportMasterId());
								comment = reportUploadLogSupervisor.getRemarks();
								dueDate = dateFormat.format(reportUploadLogSupervisor.getReportDate());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogNPST.class.getName())) {
							ReportUploadLogNPST reportUploadLogNPST = null;
							try {
								reportUploadLogNPST = ReportUploadLogNPSTLocalServiceUtil.getReportUploadLogNPST(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogNPST != null && reportUploadLogNPST.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogNPST.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogNPST.getReportMasterId());
								comment = reportUploadLogNPST.getRemarks();
								dueDate = dateFormat.format(reportUploadLogNPST.getReportDate());
							}
						}
					}else if(department.equalsIgnoreCase(NPSCompany.PFM)) {
					
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							ReportUploadLogPFM reportUploadLogPFM = null;
							try {
								reportUploadLogPFM = reportUploadLogPFMLocalService.getReportUploadLogPFM(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogPFM != null && reportUploadLogPFM.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFM.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogPFM.getReportMasterId());
								LOG.info("is reportUploadLogPFM"+reportMaster.getReportMasterId()+" applicationId:"+applicationId);
								comment = reportUploadLogPFM.getRemarks();
								dueDate = dateFormat.format(reportUploadLogPFM.getReportDate());
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFM.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							ReportUploadLogPFMAM reportUploadLogPFMAM = null;
							try {
								reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogPFMAM != null && reportUploadLogPFMAM.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAM.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogPFMAM.getReportMasterId());
								comment = reportUploadLogPFMAM.getRemarks();
								dueDate = dateFormat.format(reportUploadLogPFMAM.getReportDate());
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMAM.getReportUploadLogId());
							}
						}
						
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							ReportUploadLogPFMCRA reportUploadLogPFMCRA = null;
							try {
								reportUploadLogPFMCRA = reportUploadLogPFMCRALocalService.getReportUploadLogPFMCRA(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogPFMCRA != null && reportUploadLogPFMCRA.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCRA.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogPFMCRA.getReportMasterId());
								comment = reportUploadLogPFMCRA.getRemarks();
								dueDate = dateFormat.format(reportUploadLogPFMCRA.getReportDate());
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMCRA.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							InputQuarterlyInterval inputQuarterlyInterval  = null;
							try {
								inputQuarterlyInterval = inputQuarterlyIntervalLocalService.getInputQuarterlyInterval(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(inputQuarterlyInterval != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(inputQuarterlyInterval.getFileEntryId());
								reportMaster = reportMastersMap.get(inputQuarterlyInterval.getReportMasterId());
								comment = inputQuarterlyInterval.getRemarks();
								dueDate = dateFormat.format(inputQuarterlyInterval.getReportDate());
								createDate = inputQuarterlyInterval.getCreatedate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(inputQuarterlyInterval.getReportUploadLogId());
							}
						}
						if(entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							MnCompCertificate mnCompCertificate = null;
							List<ReportUploadFileLog> fileLogs = null;
							ReportUploadFileLog reportUploadFileLog = null;
							try {
								mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(applicationId);
								
								fileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(applicationId);
								if(Validator.isNotNull(fileLogs) && fileLogs.size() > 1)
									reportUploadFileLog = fileLogs.get(fileLogs.size() -1);
								
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(mnCompCertificate != null ) {
								if(Validator.isNotNull(reportUploadFileLog)) {
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadFileLog.getFileEntryId());
								}else{
									dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(mnCompCertificate.getFileEntryId());
								}
								
								reportMaster = reportMastersMap.get(mnCompCertificate.getReportMasterId());
								comment = mnCompCertificate.getRemarks();
								dueDate = dateFormat.format(mnCompCertificate.getReportDate());
								createDate = mnCompCertificate.getCreatedon();
								intermediaryName = npsDashboardUtil.getIntermediaryName(mnCompCertificate.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							AnnualCompCertificate annualCompCertificate = null;
							try {
								annualCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(annualCompCertificate != null ) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(annualCompCertificate.getFileEntryId()); 
								reportMaster = reportMastersMap.get(annualCompCertificate.getReportMasterId());
								comment = annualCompCertificate.getRemarks();
								dueDate = dateFormat.format(annualCompCertificate.getReportDate());
								createDate = annualCompCertificate.getCreatedate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(annualCompCertificate.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							QtrStewardshipReport qtrStewardshipReport = null;
							try {
								qtrStewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(qtrStewardshipReport != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(qtrStewardshipReport.getFileEntryId());
								reportMaster = reportMastersMap.get(qtrStewardshipReport.getReportMasterId());
								comment = qtrStewardshipReport.getRemarks();
								dueDate = dateFormat.format(qtrStewardshipReport.getReportDate());
								createDate = qtrStewardshipReport.getCreatedon();
								intermediaryName = npsDashboardUtil.getIntermediaryName(qtrStewardshipReport.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(MnNpaDevelopment.class.getName())) {
							MnNpaDevelopment mnNpaDevelopment = null;
							try {
								mnNpaDevelopment = mnNpaDevelopmentLocalService.getMnNpaDevelopment(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(mnNpaDevelopment != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(mnNpaDevelopment.getFileEntryId());
								reportMaster = reportMastersMap.get(mnNpaDevelopment.getReportMasterId());
								comment = mnNpaDevelopment.getRemarks();
								dueDate = dateFormat.format(mnNpaDevelopment.getReportDate());
								createDate = mnNpaDevelopment.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(mnNpaDevelopment.getReportUploadLogId());
							}
						}
						if(entryClassName.equalsIgnoreCase(HDFCInternal_Audit_Report.class.getName())) {
							HDFCInternal_Audit_Report hDFCInternal_Audit_Report = null;
							try {
								hDFCInternal_Audit_Report = hDFCInternal_Audit_ReportLocalService.getHDFCInternal_Audit_Report(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(hDFCInternal_Audit_Report != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(hDFCInternal_Audit_Report.getFileEntryId());
								reportMaster = reportMastersMap.get(hDFCInternal_Audit_Report.getReportMasterId());
								comment = hDFCInternal_Audit_Report.getRemarks();
								dueDate = dateFormat.format(hDFCInternal_Audit_Report.getReportDate());
								createDate = hDFCInternal_Audit_Report.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(hDFCInternal_Audit_Report.getReportUploadLogId());
							}
						}
						/* MAN f 1*/
						if(entryClassName.equalsIgnoreCase(Manpowerform_i.class.getName())) {
							Manpowerform_i manpowerform_i = null;
							try {
								manpowerform_i = manpowerform_iLocalService.getManpowerform_i(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(manpowerform_i != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(manpowerform_i.getFileEntryId());
								reportMaster = reportMastersMap.get(manpowerform_i.getReportMasterId());
								comment = manpowerform_i.getRemarks();
								dueDate = "";//dateFormat.format(manpowerform_i.getReportDate());
								createDate = manpowerform_i.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(manpowerform_i.getReportUploadLogId());
							}
						}
						/* MAN f 2*/
						if(entryClassName.equalsIgnoreCase(Manpowerform_ii.class.getName())) {
							Manpowerform_ii manpowerform_ii = null;
							try {
								manpowerform_ii = manpowerform_iiLocalService.getManpowerform_ii(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(manpowerform_ii != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(manpowerform_ii.getFileEntryId());
								reportMaster = reportMastersMap.get(manpowerform_ii.getReportMasterId());
								comment = manpowerform_ii.getRemarks();
								dueDate = "";//dateFormat.format(manpowerform_ii.getReportDate());
								createDate = manpowerform_ii.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(manpowerform_ii.getReportUploadLogId());
							}
						}
						
						/* Report Upload By PFM-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
							ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
							try {
								reportUploadLogPFMAMPFRDA =reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogPFMAMPFRDA != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMAMPFRDA.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogPFMAMPFRDA.getReportMasterId());
								comment = reportUploadLogPFMAMPFRDA.getRemarks();
								dueDate = "";//dateFormat.format(manpowerform_ii.getReportDate());
								createDate = reportUploadLogPFMAMPFRDA.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMAMPFRDA.getReportUploadLogId());
							}
						}
						
						/* Detail audit report */
						if(entryClassName.equalsIgnoreCase(DAR.class.getName())) {
							DAR dar = null;
							try {
								dar =darLocalService.getDAR(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(dar != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(dar.getFileEntryId());
								reportMaster = reportMastersMap.get(dar.getReportMasterId());
								comment = dar.getRemarks();
								dueDate = dateFormat.format(dar.getReportDate());
								createDate = dar.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(dar.getReportUploadLogId());
							}
						}
						
						/*Pfm_Qr_Internal_Audit_Report*/
						if(entryClassName.equalsIgnoreCase(Pfm_Qr_Internal_Audit_Report.class.getName())) {
							Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = null;
							try {
								pfm_Qr_Internal_Audit_Report =pfm_Qr_Internal_Audit_ReportLocalService.getPfm_Qr_Internal_Audit_Report(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(pfm_Qr_Internal_Audit_Report != null ) {
								
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_Qr_Internal_Audit_Report.getFileEntryId());
								reportMaster = reportMasterLocalService.fetchReportMaster(ReportUploadLogLocalServiceUtil.getReportUploadLog(applicationId).getReportMasterId());
								comment = pfm_Qr_Internal_Audit_Report.getRemarks();
								dueDate = dateFormat.format(pfm_Qr_Internal_Audit_Report.getReportDate());
								createDate = pfm_Qr_Internal_Audit_Report.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(pfm_Qr_Internal_Audit_Report.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							PFM_hy_comp_cert pfm_hy_comp_cert = null;
							try {
								pfm_hy_comp_cert =pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(pfm_hy_comp_cert != null ) {
								
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(pfm_hy_comp_cert.getFileEntryId());
								reportMaster = reportMasterLocalService.fetchReportMaster(ReportUploadLogLocalServiceUtil.getReportUploadLog(applicationId).getReportMasterId());
								comment = pfm_hy_comp_cert.getRemarks();
								dueDate = dateFormat.format(pfm_hy_comp_cert.getReportDate());
								createDate = pfm_hy_comp_cert.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(pfm_hy_comp_cert.getReportUploadLogId());
							}
						}
						
					}else if(department.equalsIgnoreCase(NPSCompany.CRA)) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							ReportUploadLogCRA reportUploadLogCRA = null;
							try {
								reportUploadLogCRA = reportUploadLogCRALocalService.getReportUploadLogCRA(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogCRA != null && reportUploadLogCRA.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCRA.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogCRA.getReportMasterId());
								comment = reportUploadLogCRA.getRemarks();
								dueDate = dateFormat.format(reportUploadLogCRA.getReportDate());
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogCRA.getReportUploadLogId());
							}
						}
					}else if(department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievances.class.getName())) {
							ReportUploadLogGrievances reportUploadLogGrievances = null;
							try {
								reportUploadLogGrievances = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogGrievances != null && reportUploadLogGrievances.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievances.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogGrievances.getReportMasterId());
								comment = reportUploadLogGrievances.getRemarks();
								dueDate = dateFormat.format(reportUploadLogGrievances.getReportDate());
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogGrievances.getReportUploadLogId());
							}
						}
						/* Report Upload By Griev-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogGrievAMPFRDA.class.getName())) {
							ReportUploadLogGrievAMPFRDA reportUploadLogGrievAMPFRDA = null;
							ReportUploadLog reportUploadLog = null;
							try {
								reportUploadLog = reportUploadLogLocalService.getReportUploadLog(applicationId);
								reportUploadLogGrievAMPFRDA =reportUploadLogGrievAMPFRDALocalService.getReportUploadLogGrievAMPFRDA(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogGrievAMPFRDA != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogGrievAMPFRDA.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogGrievAMPFRDA.getReportMasterId());
								comment = reportUploadLogGrievAMPFRDA.getRemarks();
								dueDate = dateFormat.format(reportUploadLog.getReportDate());
								createDate = reportUploadLogGrievAMPFRDA.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogGrievAMPFRDA.getReportUploadLogId());
							}
						}
					}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
							ReportUploadLogCustodian reportUploadLogCustodian = null;
							try {
								reportUploadLogCustodian = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogCustodian != null && reportUploadLogCustodian.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustodian.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogCustodian.getReportMasterId());
								comment = reportUploadLogCustodian.getRemarks();
								dueDate = dateFormat.format(reportUploadLogCustodian.getReportDate());
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogCustodian.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCustodian.class.getName())) {
							ReportUploadLogPFMCustodian reportUploadLogPFMCustodian = null;
							try {
								reportUploadLogPFMCustodian = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogPFMCustodian != null && reportUploadLogPFMCustodian.getFileEntryId()!=0) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogPFMCustodian.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogPFMCustodian.getReportMasterId());
								comment = reportUploadLogPFMCustodian.getRemarks();
								dueDate = dateFormat.format(reportUploadLogPFMCustodian.getReportDate());
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogPFMCustodian.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(CustodianCompForm.class.getName())) {
							CustodianCompForm custodianCompForm = null;
							try {
								custodianCompForm = custodianCompFormLocalService.getCustodianCompForm(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(custodianCompForm != null ) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custodianCompForm.getFileEntryId());
								reportMaster = reportMastersMap.get(custodianCompForm.getReportMasterId());
								comment = custodianCompForm.getRemarks();
								dueDate = dateFormat.format(custodianCompForm.getReportDate());
								createDate = custodianCompForm.getCreatedate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(custodianCompForm.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(CustIAR.class.getName())) {
							CustIAR custIAR = null;
							try {
								custIAR = custIARLocalService.getCustIAR(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(custIAR != null ) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custIAR.getFileEntryId());
								reportMaster = reportMastersMap.get(custIAR.getReportMasterId());
								comment = custIAR.getRemarks();
								dueDate = dateFormat.format(custIAR.getReportDate());
								createDate = custIAR.getCreatedon();
								intermediaryName = npsDashboardUtil.getIntermediaryName(custIAR.getReportUploadLogId());
							}
						}
						
						if(entryClassName.equalsIgnoreCase(CustAnnualAuditReport.class.getName())) {
							CustAnnualAuditReport custAnnualAuditReport = null;
							try {
								custAnnualAuditReport = custAnnualAuditReportLocalService.getCustAnnualAuditReport(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(custAnnualAuditReport != null ) {
								dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(custAnnualAuditReport.getFileEntryId());
								reportMaster = reportMastersMap.get(custAnnualAuditReport.getReportMasterId());
								comment = custAnnualAuditReport.getRemarks();
								dueDate = dateFormat.format(custAnnualAuditReport.getReportDate());
								createDate = custAnnualAuditReport.getCreatedon();
								intermediaryName = npsDashboardUtil.getIntermediaryName(custAnnualAuditReport.getReportUploadLogId());
							}
						}
						
						/* Report Upload By Cust-AM-PFRDA */
						if(entryClassName.equalsIgnoreCase(ReportUploadLogCustAMPFRDA.class.getName())) {
							ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = null;
							try {
								reportUploadLogCustAMPFRDA =reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(applicationId);
							} catch (Exception e) {
								LOG.error(e.getMessage());
							}
							if(reportUploadLogCustAMPFRDA != null ) {
								dlFileEntry =  dlFileEntryLocalService.fetchDLFileEntry(reportUploadLogCustAMPFRDA.getFileEntryId());
								reportMaster = reportMastersMap.get(reportUploadLogCustAMPFRDA.getReportMasterId());
								comment = reportUploadLogCustAMPFRDA.getRemarks();
								dueDate = "";//dateFormat.format(manpowerform_ii.getReportDate());
								createDate = reportUploadLogCustAMPFRDA.getStatusDate();
								intermediaryName = npsDashboardUtil.getIntermediaryName(reportUploadLogCustAMPFRDA.getReportUploadLogId());
							}
						}
						
						
					}
					
					OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId", false);
					List<Integer> logTypesOne = new ArrayList<>();
					logTypesOne.add(KaleoLogUtil.convert("TASK_COMPLETION"));
					List<KaleoLog> kaleoLogsOne = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesOne, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
					
					List<Integer> logTypesTwo = new ArrayList<>();
					logTypesTwo.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
					List<KaleoLog> kaleoLogsTwo = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId, itr.getWorkflowInstanceId(), logTypesTwo, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
					//LOG.info("kaleoLogsTwo : "+kaleoLogsTwo);
					
					
					if(!kaleoLogsOne.isEmpty() && kaleoLogsOne.get(0).getComment() != null && !kaleoLogsOne.get(0).getComment().isEmpty()) {
						comment = kaleoLogsOne.get(0).getComment();
					}
					
					
					String userName = "NA";
					if(kaleoLogsTwo.size() >= 2) {
						if(kaleoLogsTwo.get(1).getCurrentAssigneeClassName().equals(User.class.getName())) {
							userName = kaleoLogsTwo.get(1).getUserName();
						} else if(kaleoLogsTwo.size() >= 3 && kaleoLogsTwo.get(2).getCurrentAssigneeClassName().equals(User.class.getName())) {
							userName = kaleoLogsTwo.get(2).getUserName();
						}
					}
					
					if(userName.equals("NA") && !kaleoLogsTwo.isEmpty()) {
						userName = kaleoLogsTwo.get(0).getUserName();
					}
					//LOG.info("userName : "+userName);

					String currentlyAssignedUserName = StringPool.BLANK;
					if(!kaleoLogsTwo.isEmpty()) {
						currentlyAssignedUserName = npsDashboardUtil.getUserName(kaleoLogsTwo.get(0).getCurrentAssigneeClassPK(), kaleoLogsTwo.get(0).getCurrentAssigneeClassName());
					}
					LOG.debug("userName : "+userName +"    currentlyAssignedUserName : "+currentlyAssignedUserName);
					
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					LOG.info("userName : "+userName +"    currentlyAssignedUserName : "+currentlyAssignedUserName+"    ###################Class Name : "+entryClassName+"   classpk   : -"+applicationId);
//					LOG.info("Class PK : "+applicationId);
					ReportUploadLog reportUploadLog=ReportUploadLogLocalServiceUtil.getReportUploadLog(applicationId);
					if(reportMaster != null && reportUploadLog.getUploaded_i()>0) {
						LOG.info("reportMaster.getReportType() ::: "+reportMaster.getReportType());
						JSONArray jsonArray = pendingReports.getJSONArray(reportMaster.getReportType());
						if(Validator.isNull(jsonArray)) {
							jsonArray = JSONFactoryUtil.createJSONArray();
						}
						
						if(dlFileEntry!=null) {
							jsonObject.put("fileName", dlFileEntry.getFileName());
							jsonObject.put("createDate", dateFormat.format(dlFileEntry.getCreateDate()));
							jsonObject.put("url", "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle());
							jsonObject.put("status", WorkflowConstants.getStatusLabel(dlFileEntry.getStatus()).toUpperCase());
						}else {
							jsonObject.put("fileName", "");
							jsonObject.put("createDate", dateFormat.format(createDate));
							jsonObject.put("url", "#");
							jsonObject.put("status", "");
						}
						jsonObject.put("userName", reportMaster.getUploaderRole());
						String intrmedName=NameMappingConstants.CRA_NAME_MAP.get(intermediaryName);
						if(Validator.isNotNull(intrmedName) && intrmedName!="" ) {
							intermediaryName=intrmedName;
						}
						
						
						String craName=NameMappingConstants.CRA_NAME_MAP.get(reportMaster.getCra());
						if(Validator.isNull(craName) || craName=="" ) {
							craName=reportMaster.getCra();
						}
						
						String reportName=reportMaster.getReportName();
						long masterid=reportMaster.getReportMasterId();
						if(masterid==11 || masterid==23 || masterid==128 || masterid==3 || masterid==17 || masterid==18 ||masterid==4 || masterid==19 || masterid==124 ||masterid==7 || masterid==20 || masterid==125 ||masterid==8 || masterid==21 || masterid==126 ||masterid==10 || masterid==22 || masterid==127 ) {
							reportName=reportName+" "+craName;
						}
						
						jsonObject.put("moduleName", reportName);
						jsonObject.put("actionURL", (url + itr.getWorkflowTaskId()));
						jsonObject.put("remarks", comment);
						
						jsonObject.put("intermediaryName", intermediaryName);
						jsonObject.put("assignedTo", currentlyAssignedUserName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW));
						jsonObject.put("dueDate", dueDate);
						
						LOG.info("application id: "+applicationId+"json object: :::  "+jsonObject);
						jsonArray.put(jsonObject);
						
						pendingReports.put(reportMaster.getReportType(), jsonArray);
						
					}
				}catch(Exception e) {
					LOG.error(e);
				}
				}
			} catch (Exception e1) {
				LOG.error(e1);
			}
			//LOG.info("pendingReports : "+pendingReports);
			LOG.info("JSON Array length: "+pendingReports.length());
		return pendingReports;
	}
	
	@SuppressWarnings("deprecation")
	public static String getWorkflowUrl(PortletRequest portletRequest, ThemeDisplay themeDisplay) throws Exception {
        PortletURL renderURL = PortletURLFactoryUtil.create(portletRequest, PortletKeys.MY_WORKFLOW_TASK, PortletRequest.RENDER_PHASE);
        renderURL.setWindowState(WindowState.NORMAL);
        renderURL.setPortletMode(PortletMode.VIEW);
        renderURL.setParameter("mvcPath", "/edit_workflow_task.jsp");
        renderURL.setParameter("backURL", themeDisplay.getURLCurrent());
        renderURL.setParameter("workflowTaskId", "");

        String workflowUrl = renderURL.toString();
        String[] url = workflowUrl.split("\\?");
        String panelUrl = "/web" + themeDisplay.getScopeGroup().getFriendlyURL() + "/manage";
        String queryParam = url[1];

        workflowUrl = panelUrl + "?" + queryParam;
        return workflowUrl;
    }
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference
	private DLFileEntryLocalService dlFileEntryLocalService;
	
	@Reference
	private ReportUploadLogPFMLocalService reportUploadLogPFMLocalService;
	
	@Reference
	private ReportUploadLogPFMAMLocalService reportUploadLogPFMAMLocalService;
	
	@Reference
	private ReportUploadLogPFMCRALocalService reportUploadLogPFMCRALocalService;
	
	@Reference
	private ReportUploadLogCRALocalService reportUploadLogCRALocalService;
	
	@Reference
	private ReportUploadLogCustodianLocalService reportUploadLogCustodianLocalService;
	
	@Reference
	private ReportUploadLogGrievancesLocalService reportUploadLogGrievancesLocalService;
	
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
	private HDFCInternal_Audit_ReportLocalService hDFCInternal_Audit_ReportLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	@Reference
	private CustodianCompFormLocalService custodianCompFormLocalService;
	
	@Reference
	private CustIARLocalService custIARLocalService; 
	
	@Reference
	private CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;
	
	@Reference
	private ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService;
	
	@Reference
	private Manpowerform_iLocalService manpowerform_iLocalService;
	
	@Reference
	private Manpowerform_iiLocalService manpowerform_iiLocalService;
	
	@Reference
	private ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogPFMAMPFRDALocalService reportUploadLogPFMAMPFRDALocalService;
	
	@Reference
	private ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDALocalService;
	
	@Reference
	private DARLocalService darLocalService;
	
	@Reference
	private Pfm_Qr_Internal_Audit_ReportLocalService pfm_Qr_Internal_Audit_ReportLocalService;
	
	@Reference
	private PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;
}
