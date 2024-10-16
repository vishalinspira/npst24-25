package com.nps.dashboard.web.mvc;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.DAR;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil;
import com.daily.average.service.service.DARLocalServiceUtil;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.daily.average.service.service.MnCompCertificateLocalServiceUtil;
import com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil;
import com.daily.average.service.service.QtrStewardshipReportLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.daily.average.service.service.ReportUploadLogCustodianLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.average.service.service.ReportUploadLogMakerLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalServiceUtil;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSTRoleConstants;
import nps.common.service.constants.NameMappingConstants;
import nps.common.service.util.PreviewFileURLUtil;

@Component(immediate = true, property = { "javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
		"mvc.command.name="
				+ NPSDashboardWebPortletKeys.REPORT_SUMMARY_MVC_RENDER_COMMAND, }, service = MVCRenderCommand.class)
public class ReportSummaryMVCRenderCommand implements MVCRenderCommand {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

	private static final Log LOG = LogFactoryUtil.getLog(ReportSummaryMVCRenderCommand.class);

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		long workflowInstanceId = ParamUtil.getLong(renderRequest, "workflowInstanceId", 0L);
		long reportUploadLogId = ParamUtil.getLong(renderRequest, "reportUploadLogId", 0L);
		String department = ParamUtil.getString(renderRequest, "department");
		Company company = null;
		try {
			company = portal.getCompany(renderRequest);
		} catch (PortalException e1) {
			e1.printStackTrace();
		}
		long companyId = 0l;
		if (company != null)
			companyId = company.getCompanyId();
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();
		boolean isChecker = false;
		boolean isMaker = false;
		try {
			isChecker = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.CHECKER, false);
			isMaker = roleLocalService.hasUserRole(user.getUserId(), companyId, NPSTRoleConstants.MAKER, false);
		} catch (PortalException e1) {
			e1.printStackTrace();
		}

		DLFileEntry dlFileEntry = null;
		/*
		 * try { dlFileEntry =
		 * DLFileEntryLocalServiceUtil.fetchDLFileEntry(fileEntryId); } catch(Exception
		 * e) { LOG.error(e.getMessage()); }
		 */

		ReportUploadLog reportUploadLog = null;
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		JSONArray urlArray = JSONFactoryUtil.createJSONArray();
		try {
			reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
			LOG.info("reportUploadLog" + reportUploadLog.getReportUploadLogId());
			reportUploadFileLogs = reportUploadFileLogLocalService.findByReportUploadLogId(reportUploadLogId);
			LOG.info("reportUploadFileLogs" + reportUploadFileLogs);
			LOG.info("department" + department);
			//if(department.equalsIgnoreCase("PFM")){
				
			/*}else{
			}*/
				 urlArray = PreviewFileURLUtil.getPreviewMultiFileURL(themeDisplay, reportUploadFileLogs);
			
			//reportUploadFileLogs=reportUploadFileLogs.stream().sorted(Comparator.comparing(ReportUploadFileLog::getFileEntryId).reversed()).collect(Collectors.toList());
			reportUploadFileLogs=reportUploadFileLogs.stream().sorted(Comparator.comparing(ReportUploadFileLog::getFileEntryId)).collect(Collectors.toList());
			
			dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(reportUploadLog.getFileEntryId());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		ReportMaster reportMaster = null;
		try {
			reportMaster = reportMasterLocalService.fetchReportMaster(reportUploadLog.getReportMasterId());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		/*
		 * Company company = null; try { company = PortalUtil.getCompany(renderRequest);
		 * } catch (PortalException e) {  _log.error(e); }
		 */

		if (Validator.isNotNull(company) && workflowInstanceId > 0) {
			// long companyId = company.getCompanyId();
			OrderByComparator<KaleoLog> comparator = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId",false);
			List<Integer> logTypesTwo = new ArrayList<>();
			logTypesTwo.add(KaleoLogUtil.convert("TASK_COMPLETION"));
			logTypesTwo.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
			logTypesTwo.add(0);
			
			LOG.info("logTypesTwo : "+logTypesTwo);
			
			List<KaleoLog> kaleoLogsTwo = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId,
					workflowInstanceId, logTypesTwo, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparator);
			LOG.info("================================================");
			LOG.info("kaleoLogsTwo ::::::::::::: "+kaleoLogsTwo);
			
			boolean isNonNpsUser = npsDashboardUtil.isNonNpsUser(themeDisplay.getUserId());
			
			JSONObject jsonObjectHist = JSONFactoryUtil.createJSONObject();
			jsonObjectHist.put("userName", "");
			/* Custom code to get Maker comment start */
			
			String makerComment = StringPool.BLANK;
			
			OrderByComparator<KaleoLog> comparatorOne = OrderByComparatorFactoryUtil.create("KALEOLOG", "kaleoLogId",false);
			List<Integer> logTypesOne = new ArrayList<>();
			logTypesOne.add(KaleoLogUtil.convert("TASK_ASSIGNMENT"));
			logTypesOne.add(0);
			
			List<KaleoLog> kaleoLogsOne = null;
			List<WorkflowTask> allList =null;
			KaleoInstance kaleoInstance= null;
			try {
				 kaleoLogsOne = KaleoLogLocalServiceUtil.getKaleoInstanceKaleoLogs(companyId,
						workflowInstanceId, logTypesOne, QueryUtil.ALL_POS, QueryUtil.ALL_POS, comparatorOne);
				 if(Validator.isNotNull(kaleoLogsOne) && kaleoLogsOne.size() >0) {
					 LOG.info("kaleoLogsOne::::" + kaleoLogsOne);
					 long kaleoInstanceId = 0l;
					 kaleoInstanceId = kaleoLogsOne.get(0).getKaleoInstanceId();
					 kaleoInstance = KaleoInstanceLocalServiceUtil.fetchKaleoInstance(kaleoInstanceId );
				 }
			} catch (Exception e) {
				LOG.error("Exception in getting kaleoLogsOne:::" + e.getMessage());
			}
			/*
			try {
				allList = new ArrayList<>();
				List<WorkflowTask> completed = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.TRUE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				List<WorkflowTask> pending = WorkflowTaskManagerUtil.getWorkflowTasks(companyId, Boolean.FALSE, QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getTaskCreateDateComparator(false));
				allList.addAll(completed);
				allList.addAll(pending);
			} catch (Exception e) {
				LOG.error("Exception in getting allList:::" + e.getMessage());
			}
			*/
			if(Validator.isNotNull(kaleoInstance)) {
				//for (WorkflowTask itr : allList) {
					//Map<String, Serializable> maps = itr.getOptionalAttributes();
					long makerUserID = Long.parseLong(String.valueOf(kaleoInstance.getUserId()));
					//if(isMaker(makerUserID)) {
						LOG.info("workflowInstance matched");
						long applicationId = Long.parseLong(String.valueOf(kaleoInstance.getClassPK()));
						String entryClassName = String.valueOf(kaleoInstance.getClassName());
						LOG.info("entryClassName for comment::" + entryClassName);
						ReportUploadLogPFM reportUploadLogPfm = null;
						ReportUploadLogPFMAM reportUploadLogPfmAm = null;
						ReportUploadLogPFMCRA reportUploadLogPfmCra = null;
						ReportUploadLogMaker reportUploadLogMaker = null;
						ReportUploadLogCRA reportUploadLogCRA = null;
						InputQuarterlyInterval inputQuarterlyInterval = null;
						PFM_hy_comp_cert pfm_hy_comp_cert=null;
						if(entryClassName.equalsIgnoreCase(ReportUploadLogPFM.class.getName())) {
							urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
							try {
								reportUploadLogPfm  = reportUploadLogPfmLocalService.getReportUploadLogPFM(applicationId);
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(reportUploadLogPfm)) {
								makerComment = reportUploadLogPfm.getRemarks();
							}
						}
						
						else if(entryClassName.equalsIgnoreCase(ReportUploadLogCustodian.class.getName())) {
							 urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
							 ReportUploadLogCustodian reportUploadLogCustodian = null;
							try {
								reportUploadLogCustodian  = ReportUploadLogCustodianLocalServiceUtil.getReportUploadLogCustodian(applicationId);
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(reportUploadLogPfmAm)) {
								makerComment = reportUploadLogCustodian.getRemarks();
							}
						}
						else if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAM.class.getName())) {
							urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
							try {
								reportUploadLogPfmAm  = reportUploadLogPfmAmLocalService.getReportUploadLogPFMAM(applicationId);
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(reportUploadLogPfmAm)) {
								makerComment = reportUploadLogPfmAm.getRemarks();
							}
						}
						else if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMAMPFRDA.class.getName())) {
							ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = null;
							//urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
							try {
								reportUploadLogPFMAMPFRDA  = ReportUploadLogPFMAMPFRDALocalServiceUtil.getReportUploadLogPFMAMPFRDA(applicationId);
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(reportUploadLogPFMAMPFRDA)) {
								makerComment = reportUploadLogPFMAMPFRDA.getRemarks();
							}
						}
						
						else if(entryClassName.equalsIgnoreCase(ReportUploadLogPFMCRA.class.getName())) {
							 urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
							try {
								reportUploadLogPfmCra  = reportUploadLogPfmCraLocalService.getReportUploadLogPFMCRA(applicationId);
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(reportUploadLogPfmAm)) {
								makerComment = reportUploadLogPfmAm.getRemarks();
							}
						}
						
						else if(entryClassName.equalsIgnoreCase(ReportUploadLogCRA.class.getName())) {
							try {
								reportUploadLogCRA = reportUploadLogCRALocalService.getReportUploadLogCRA(applicationId);
							} catch (Exception e) {
								LOG.error("Exception in getting ReportUploadLogCRA:::" + e.getMessage());
							}
							if(Validator.isNotNull(reportUploadLogCRA)) {
								makerComment = reportUploadLogCRA.getRemarks();
							}
						}
						
						else if(entryClassName.equalsIgnoreCase(ReportUploadLogMaker.class.getName())) {
							urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
							LOG.info("app id::" +applicationId);
							try {
								reportUploadLogMaker  = reportUploadLogMakerLocalService.getReportUploadLogMaker(applicationId);
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(reportUploadLogMaker)) {
								makerComment = reportUploadLogMaker.getRemarks();
							}
						}
						else if (entryClassName.equalsIgnoreCase(InputQuarterlyInterval.class.getName())) {
							try {
								JSONArray fileList = JSONFactoryUtil.createJSONArray();
								inputQuarterlyInterval  = InputQuarterlyIntervalLocalServiceUtil.getInputQuarterlyInterval(applicationId);
								JSONObject annex_i = JSONFactoryUtil.createJSONObject();
								annex_i.put("fileEntryId", inputQuarterlyInterval.getAnnex_i());
								annex_i.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getAnnex_i(),renderRequest));
								fileList.put(annex_i);
								JSONObject board_a = JSONFactoryUtil.createJSONObject();
								board_a.put("fileEntryId", inputQuarterlyInterval.getBoard_a());
								board_a.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getBoard_a(),renderRequest));
								fileList.put(board_a);
								JSONObject board_b = JSONFactoryUtil.createJSONObject();
								board_b.put("fileEntryId", inputQuarterlyInterval.getBoard_b());
								board_b.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getBoard_b(),renderRequest));
								fileList.put(board_b);
								JSONObject investment_a = JSONFactoryUtil.createJSONObject();
								investment_a.put("fileEntryId", inputQuarterlyInterval.getInvestment_a());
								investment_a.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getInvestment_a(),renderRequest));
								fileList.put(investment_a);
								JSONObject investment_b = JSONFactoryUtil.createJSONObject();
								investment_b.put("fileEntryId", inputQuarterlyInterval.getInvestment_b());
								investment_b.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getInvestment_b(),renderRequest));
								fileList.put(investment_b);
								JSONObject risk_a = JSONFactoryUtil.createJSONObject();
								risk_a.put("fileEntryId", inputQuarterlyInterval.getRisk_a());
								risk_a.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getRisk_a(),renderRequest));
								fileList.put(risk_a);
								JSONObject risk_b = JSONFactoryUtil.createJSONObject();
								risk_b.put("fileEntryId", inputQuarterlyInterval.getRisk_b());
								risk_b.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getRisk_b(),renderRequest));
								fileList.put(risk_b);
								JSONObject annex_ii = JSONFactoryUtil.createJSONObject();
								annex_ii.put("fileEntryId", inputQuarterlyInterval.getAnnex_ii());
								annex_ii.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getAnnex_ii(),renderRequest));
								fileList.put(annex_ii);
								JSONObject annex_iii = JSONFactoryUtil.createJSONObject();
								annex_iii.put("fileEntryId", inputQuarterlyInterval.getAnnex_iii());
								annex_iii.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getAnnex_iii(),renderRequest));
								fileList.put(annex_iii);
								JSONObject annex_iv = JSONFactoryUtil.createJSONObject();
								annex_iv.put("fileEntryId", inputQuarterlyInterval.getAnnex_iv());
								annex_iv.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getAnnex_iv(),renderRequest));
								fileList.put(annex_iv);
								
								
								JSONObject pdfFile = JSONFactoryUtil.createJSONObject();
								pdfFile.put("fileEntryId", inputQuarterlyInterval.getFileEntryId());
								pdfFile.put("downloadURL", getDocumentURL(inputQuarterlyInterval.getFileEntryId(),renderRequest));
								fileList.put(pdfFile);
								urlArray = JSONFactoryUtil.createJSONArray(); 
								urlArray.put(JSONFactoryUtil.createJSONObject().put("fileList", fileList).put("version", 1));
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(inputQuarterlyInterval)) {
								makerComment = inputQuarterlyInterval.getRemarks();
							}
						}
						else if (entryClassName.equalsIgnoreCase(MnCompCertificate.class.getName())) {
							urlArray = PreviewFileURLUtil.getPreviewMultiFileURL2(themeDisplay, reportUploadFileLogs);
							MnCompCertificate mnCompCertificate = null;
							try {
								JSONArray fileList = JSONFactoryUtil.createJSONArray();
								mnCompCertificate  = MnCompCertificateLocalServiceUtil.getMnCompCertificate(applicationId);
								JSONObject file = JSONFactoryUtil.createJSONObject();
								file.put("fileEntryId", mnCompCertificate.getAnnexure_a_i());
								file.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_a_i(),renderRequest));
								fileList.put(file);
								JSONObject file_aii = JSONFactoryUtil.createJSONObject();
								file_aii.put("fileEntryId", mnCompCertificate.getAnnexure_a_ii());
								file_aii.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_a_ii(),renderRequest));
								fileList.put(file_aii);
								JSONObject file_anxb = JSONFactoryUtil.createJSONObject();
								file_anxb.put("fileEntryId", mnCompCertificate.getAnnexure_b());
								file_anxb.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_b(),renderRequest));
								fileList.put(file_anxb);
								JSONObject file_anxc = JSONFactoryUtil.createJSONObject();
								file_anxc.put("fileEntryId", mnCompCertificate.getAnnexure_c());
								file_anxc.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_c(),renderRequest));
								fileList.put(file_anxc);
								JSONObject file_anxd = JSONFactoryUtil.createJSONObject();
								file_anxd.put("fileEntryId", mnCompCertificate.getAnnexure_d());
								file_anxd.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_d(),renderRequest));
								fileList.put(file_anxd);
								JSONObject file_anxe = JSONFactoryUtil.createJSONObject();
								file_anxe.put("fileEntryId", mnCompCertificate.getAnnexure_e());
								file_anxe.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_e(),renderRequest));
								fileList.put(file_anxe);
								JSONObject file_anxf = JSONFactoryUtil.createJSONObject();
								file_anxf.put("fileEntryId", mnCompCertificate.getAnnexure_f());
								file_anxf.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_f(),renderRequest));
								fileList.put(file_anxf);
								JSONObject file_anxg = JSONFactoryUtil.createJSONObject();
								file_anxg.put("fileEntryId", mnCompCertificate.getAnnexure_g());
								file_anxg.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_g(),renderRequest));
								fileList.put(file_anxg);
								JSONObject file_anxh = JSONFactoryUtil.createJSONObject();
								file_anxh.put("fileEntryId", mnCompCertificate.getAnnexure_h());
								file_anxh.put("downloadURL", getDocumentURL(mnCompCertificate.getAnnexure_h(),renderRequest));
								fileList.put(file_anxh);
								
								JSONObject pdfFile = JSONFactoryUtil.createJSONObject();
								pdfFile.put("fileEntryId", mnCompCertificate.getFileEntryId());
								pdfFile.put("downloadURL", getDocumentURL(mnCompCertificate.getFileEntryId(),renderRequest));
								fileList.put(pdfFile);
								urlArray = JSONFactoryUtil.createJSONArray(); 
								urlArray.put(JSONFactoryUtil.createJSONObject().put("fileList", fileList).put("version", 1));
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(mnCompCertificate)) {
								makerComment = mnCompCertificate.getRemarks();
							}
						}
						else if (entryClassName.equalsIgnoreCase(AnnualCompCertificate.class.getName())) {
							AnnualCompCertificate  annualCompCertificate = null;
							try {
								JSONArray fileList = JSONFactoryUtil.createJSONArray();
								annualCompCertificate  = AnnualCompCertificateLocalServiceUtil.getAnnualCompCertificate(applicationId);
								JSONObject Annexurei = JSONFactoryUtil.createJSONObject();
								Annexurei.put("fileEntryId", annualCompCertificate.getAnnexurei());
								Annexurei.put("downloadURL", getDocumentURL(annualCompCertificate.getAnnexurei(),renderRequest));
								fileList.put(Annexurei);
								JSONObject Annexureii = JSONFactoryUtil.createJSONObject();
								Annexureii.put("fileEntryId", annualCompCertificate.getAnnexureii());
								Annexureii.put("downloadURL", getDocumentURL(annualCompCertificate.getAnnexureii(),renderRequest));
								fileList.put(Annexureii);
								JSONObject Annexureiii = JSONFactoryUtil.createJSONObject();
								Annexureiii.put("fileEntryId", annualCompCertificate.getAnnexureiii());
								Annexureiii.put("downloadURL", getDocumentURL(annualCompCertificate.getAnnexureiii(),renderRequest));
								fileList.put(Annexureiii);
								JSONObject Annexureiv = JSONFactoryUtil.createJSONObject();
								Annexureiv.put("fileEntryId", annualCompCertificate.getAnnexureiv());
								Annexureiv.put("downloadURL", getDocumentURL(annualCompCertificate.getAnnexureiv(),renderRequest));
								fileList.put(Annexureiv);
								JSONObject Annexurev = JSONFactoryUtil.createJSONObject();
								Annexurev.put("fileEntryId", annualCompCertificate.getAnnexurev());
								Annexurev.put("downloadURL", getDocumentURL(annualCompCertificate.getAnnexurev(),renderRequest));
								fileList.put(Annexurev);
								
								
								JSONObject pdfFile = JSONFactoryUtil.createJSONObject();
								pdfFile.put("fileEntryId", annualCompCertificate.getFileEntryId());
								pdfFile.put("downloadURL", getDocumentURL(annualCompCertificate.getFileEntryId(),renderRequest));
								fileList.put(pdfFile);
								
								urlArray = JSONFactoryUtil.createJSONArray(); 
								urlArray.put(JSONFactoryUtil.createJSONObject().put("fileList", fileList).put("version", 1));
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(annualCompCertificate)) {
								makerComment = annualCompCertificate.getRemarks();
							}
						}
						else if (entryClassName.equalsIgnoreCase(DAR.class.getName())) {
							DAR dar = null;
							try {
								JSONArray fileList = JSONFactoryUtil.createJSONArray();
								dar  = DARLocalServiceUtil.getDAR(applicationId);
								JSONObject file = JSONFactoryUtil.createJSONObject();
								file.put("fileEntryId", dar.getDar_file_1_id());
								file.put("downloadURL", getDocumentURL(dar.getDar_file_1_id(),renderRequest));
								fileList.put(file);
								JSONObject file2 = JSONFactoryUtil.createJSONObject();
								file2.put("fileEntryId", dar.getDar_file_2_id());
								file2.put("downloadURL", getDocumentURL(dar.getDar_file_2_id(),renderRequest));
								fileList.put(file2);
								JSONObject file3 = JSONFactoryUtil.createJSONObject();
								file3.put("fileEntryId", dar.getDar_file_3_id());
								file3.put("downloadURL", getDocumentURL(dar.getDar_file_3_id(),renderRequest));
								fileList.put(file3);
								JSONObject file4 = JSONFactoryUtil.createJSONObject();
								file4.put("fileEntryId", dar.getDar_file_4_id());
								file4.put("downloadURL", getDocumentURL(dar.getDar_file_4_id(),renderRequest));
								fileList.put(file4);
								JSONObject file5 = JSONFactoryUtil.createJSONObject();
								file5.put("fileEntryId", dar.getDar_file_5_id());
								file5.put("downloadURL", getDocumentURL(dar.getDar_file_5_id(),renderRequest));
								fileList.put(file5);
								JSONObject pdfFile = JSONFactoryUtil.createJSONObject();
								pdfFile.put("fileEntryId", dar.getFileEntryId());
								pdfFile.put("downloadURL", getDocumentURL(dar.getFileEntryId(),renderRequest));
								fileList.put(pdfFile);
								
								urlArray = JSONFactoryUtil.createJSONArray(); 
								urlArray.put(JSONFactoryUtil.createJSONObject().put("fileList", fileList).put("version", 1));
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(dar)) {
								makerComment = dar.getRemarks();
							}
						}
						else if(entryClassName.equalsIgnoreCase(PFM_hy_comp_cert.class.getName())) {
							try {
								JSONArray fileList = JSONFactoryUtil.createJSONArray();
								pfm_hy_comp_cert  = PFM_hy_comp_certLocalServiceUtil.getPFM_hy_comp_cert(applicationId);
								JSONObject annex = JSONFactoryUtil.createJSONObject();
								annex.put("fileEntryId", pfm_hy_comp_cert.getHyccfile());
								annex.put("downloadURL", getDocumentURL(pfm_hy_comp_cert.getHyccfile(),renderRequest));
								fileList.put(annex);
								
								
								
								JSONObject pdfFile = JSONFactoryUtil.createJSONObject();
								pdfFile.put("fileEntryId", pfm_hy_comp_cert.getFileEntryId());
								pdfFile.put("downloadURL", getDocumentURL(pfm_hy_comp_cert.getFileEntryId(),renderRequest));
								fileList.put(pdfFile);
								urlArray = JSONFactoryUtil.createJSONArray(); 
								urlArray.put(JSONFactoryUtil.createJSONObject().put("fileList", fileList).put("version", 1));
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(pfm_hy_comp_cert)) {
								makerComment = pfm_hy_comp_cert.getRemarks();
							}
						}
						else if (entryClassName.equalsIgnoreCase(QtrStewardshipReport.class.getName())) {
							QtrStewardshipReport qtrStewardshipReport = null;
							try {
								JSONArray fileList = JSONFactoryUtil.createJSONArray();
								qtrStewardshipReport  = QtrStewardshipReportLocalServiceUtil.getQtrStewardshipReport(applicationId);
								JSONObject file = JSONFactoryUtil.createJSONObject();
								file.put("fileEntryId", qtrStewardshipReport.getAnnexure_a());
								file.put("downloadURL", getDocumentURL(qtrStewardshipReport.getAnnexure_a(),renderRequest));
								fileList.put(file);
								JSONObject anx_bi = JSONFactoryUtil.createJSONObject();
								anx_bi.put("fileEntryId", qtrStewardshipReport.getAnnexure_b_i());
								anx_bi.put("downloadURL", getDocumentURL(qtrStewardshipReport.getAnnexure_b_i(),renderRequest));
								fileList.put(anx_bi);
								JSONObject anx_bii = JSONFactoryUtil.createJSONObject();
								anx_bii.put("fileEntryId", qtrStewardshipReport.getAnnexure_b_ii());
								anx_bii.put("downloadURL", getDocumentURL(qtrStewardshipReport.getAnnexure_b_ii(),renderRequest));
								fileList.put(anx_bii);
								JSONObject anx_c = JSONFactoryUtil.createJSONObject();
								anx_c.put("fileEntryId", qtrStewardshipReport.getAnnexure_c());
								anx_c.put("downloadURL", getDocumentURL(qtrStewardshipReport.getAnnexure_c(),renderRequest));
								fileList.put(anx_c);
								JSONObject pdfFile = JSONFactoryUtil.createJSONObject();
								pdfFile.put("fileEntryId", qtrStewardshipReport.getFileEntryId());
								pdfFile.put("downloadURL", getDocumentURL(qtrStewardshipReport.getFileEntryId(),renderRequest));
								fileList.put(pdfFile);
								urlArray = JSONFactoryUtil.createJSONArray(); 
								urlArray.put(JSONFactoryUtil.createJSONObject().put("fileList", fileList).put("version", 1));
							} catch (PortalException e) {
								LOG.error("Exception in getting reportUploadLogPfm::" + e.getMessage());
							}
							if(Validator.isNotNull(qtrStewardshipReport)) {
								makerComment = qtrStewardshipReport.getRemarks();
							}
						}
					//}
				//}
				LOG.info("maker userID::" + kaleoLogsOne.get(0).getUserId());
				if(isMaker(kaleoLogsOne.get(kaleoLogsOne.size()-1).getUserId())){
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					jsonObject.put("id", kaleoLogsOne.get(kaleoLogsOne.size()-1).getKaleoLogId());
					jsonObject.put("userName", kaleoLogsOne.get(kaleoLogsOne.size()-1).getUserName());
					jsonObject.put("createDate", DATE_FORMAT.format(kaleoLogsOne.get(kaleoLogsOne.size()-1).getCreateDate()));
					jsonObject.put("remarks", makerComment);
					jsonArray.put(jsonObject);
				}
			}
			
			
			/* Custom code to get Maker comment end */
			
			if(isNonNpsUser) {
				LOG.info("inside isNONNpsUser::" + isNonNpsUser);
				for (KaleoLog KL : kaleoLogsTwo) {
					if(npsDashboardUtil.isNonNpsUser(KL.getUserId())){
					LOG.info("userName::" + KL.getUserName());
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					jsonObject.put("id", KL.getKaleoLogId());
					jsonObject.put("userName", KL.getUserName());
					jsonObject.put("createDate", DATE_FORMAT.format(KL.getCreateDate()));
					if (KL.getType().equalsIgnoreCase("TASK_COMPLETION")) {
						jsonObject.put("remarks", KL.getComment());
					}else {
						jsonObject.put("remarks","");
					}
					if (!jsonObject.getString("userName").equalsIgnoreCase(jsonObjectHist.getString("userName"))) {
						if (isChecker || isMaker) {
							if (jsonObject.getString("userName").equalsIgnoreCase("AM")
									|| jsonObject.getString("userName").equalsIgnoreCase("DGM")
									|| jsonObject.getString("userName").equalsIgnoreCase("GM")) {

							}else {
								jsonArray.put(jsonObject);
							}
						} else {
							jsonArray.put(jsonObject);
						}

					}
					jsonObjectHist = jsonObject;
				  }
				}
			}else {
				LOG.info("inside else");
				for (KaleoLog KL : kaleoLogsTwo) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					jsonObject.put("id", KL.getKaleoLogId());
					jsonObject.put("userName", KL.getUserName());
					jsonObject.put("createDate", DATE_FORMAT.format(KL.getCreateDate()));
					if (KL.getType().equalsIgnoreCase("TASK_COMPLETION")) {
						jsonObject.put("remarks", KL.getComment());
					}else {
						jsonObject.put("remarks", "");
					}
					if (!jsonObject.getString("userName").equalsIgnoreCase(jsonObjectHist.getString("userName"))) {
						if (isChecker || isMaker) {
							if (jsonObject.getString("userName").equalsIgnoreCase("AM")
									|| jsonObject.getString("userName").equalsIgnoreCase("DGM")
									|| jsonObject.getString("userName").equalsIgnoreCase("GM")) {

							}else {
								jsonArray.put(jsonObject);
							}
						} else {
							jsonArray.put(jsonObject);
						}

					}
					jsonObjectHist = jsonObject;
				}
			}
		}
		LOG.info("jsonArray::" + jsonArray);
		renderRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		renderRequest.setAttribute("jsonArray", jsonArray);
		LOG.info(urlArray);
		renderRequest.setAttribute("urlArray", urlArray);
		renderRequest.setAttribute("dept", department);
		String reportName= Validator.isNotNull(reportMaster) ? reportMaster.getReportName()+ (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : ""): "NA";
		 reportName=reportName.replace(NameMappingConstants.NCRA_OLD, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.NCRA_OLD1, NameMappingConstants.NCRA_NEW).replace(NameMappingConstants.CAMS_OLD, NameMappingConstants.CAMS_NEW).replace(NameMappingConstants.KCRA_OLD, NameMappingConstants.KCRA_NEW);
		renderRequest.setAttribute("reportName",reportName);
		renderRequest.setAttribute("fileDownloadLink",
				Validator.isNotNull(dlFileEntry)
						? "/documents/" + dlFileEntry.getRepositoryId() + "/" + dlFileEntry.getFolderId() + "/"
								+ dlFileEntry.getTitle()
						: "javascript:void(0);");
		renderRequest.setAttribute("dueDate",
				Validator.isNotNull(reportUploadLog) ? DATE_FORMAT.format(reportUploadLog.getReportDate()) : "NA");

		return "/html/makers_screens/report_summary.jsp";
	}	
	
	
	public boolean isMaker(long userId) {
		boolean isMaker = false;
		User user = null;
		boolean inherited = Boolean.FALSE;
		long companyId = 0l;
		try {
			user = UserLocalServiceUtil.getUser(userId);
		} catch (Exception e) {
			LOG.error("Exception in getting user::" + e.getMessage());
		}
		if(Validator.isNotNull(user)) {
			companyId = user.getCompanyId();
		}
		try {
			if(Validator.isNotNull(companyId)) {
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				isMaker = pfmMaker;
			}
			if(Validator.isNotNull(companyId) && !isMaker) {
				boolean craMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CRA_MAKER, userId, inherited);
				isMaker = craMaker;
			}
			if(Validator.isNotNull(companyId) && !isMaker) {
				boolean custodianMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.CUSTODIAN_MAKER, userId, inherited);
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.PFM_MAKER, userId, inherited);
				isMaker = (custodianMaker || pfmMaker);
			}
			if(Validator.isNotNull(companyId) && !isMaker) {
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.MAKER, userId, inherited);
				isMaker = pfmMaker;
			}
			if(Validator.isNotNull(companyId) && !isMaker) {
				boolean pfmMaker = userLocalService.hasRoleUser(companyId, NPSTRoleConstants.GRIEVANCES_MAKER, userId, inherited);
				isMaker = pfmMaker;
			}
		} catch (Exception e) {
			LOG.error("Exception in getting role::" + e.getMessage());
		}
		return isMaker;
	}
	
	public static String getDocumentURL(long documentId,RenderRequest renderRequest) {
		
		 String documentURL = StringPool.BLANK;
		 try {
		    if(Validator.isNotNull(documentId)) {
		    	long fileEntryId = documentId;
		    	//DLFileEntry dlFileEntry = null;
		    	FileEntry fileEntry=null;
		    	try {
		    		//dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId);
		    		fileEntry=DLAppLocalServiceUtil.getFileEntry(fileEntryId);
				} catch (Exception e) {
					 LOG.error(e);
				}
		    	if(Validator.isNotNull(fileEntry)) {
		    		 ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		    		documentURL=	DLURLHelperUtil.getDownloadURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK);
		    		//documentURL = "/documents/"+dlFileEntry.getRepositoryId()+"/"+dlFileEntry.getFolderId()+"/"+dlFileEntry.getTitle();
		    	}
		    }
		}catch (Exception e) {
			LOG.error(e);
		}
		    return documentURL;
	}

	@Reference
	ReportMasterLocalService reportMasterLocalService;

	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;

	@Reference
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;

	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference
	private UserLocalService userLocalService;
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;
	
	@Reference
	private ReportUploadLogPFMLocalService reportUploadLogPfmLocalService;
	
	@Reference
	private ReportUploadLogPFMAMLocalService reportUploadLogPfmAmLocalService;
	
	@Reference
	private ReportUploadLogPFMCRALocalService reportUploadLogPfmCraLocalService;
	
	@Reference
	private ReportUploadLogMakerLocalService reportUploadLogMakerLocalService;
	
	@Reference
	private ReportUploadLogCRALocalService reportUploadLogCRALocalService;

	@Reference
	private Portal portal;
}
