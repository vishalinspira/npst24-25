package com.nps.pfm.hy.comp.cert.asset;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny;
import com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.nps.pfm.hy.comp.cert.util.PfmHyccNonNps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.WorkFlowTaskUtil;

public class PFM_hy_comp_certAssetRender extends BaseJSPAssetRenderer<PFM_hy_comp_cert> {
	
	private PFM_hy_comp_cert pfm_hy_comp_cert;
	
	public PFM_hy_comp_certAssetRender(PFM_hy_comp_cert pfm_hy_comp_cert) {
		this.pfm_hy_comp_cert = pfm_hy_comp_cert;
	}

	@Override
	public PFM_hy_comp_cert getAssetObject() {

		return pfm_hy_comp_cert;
	}

	@Override
	public long getGroupId() {
		
		return userLocalService.fetchUser(pfm_hy_comp_cert.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		
		return pfm_hy_comp_cert.getCreatedby();
	}

	@Override
	public String getUserName() {
		
		return userLocalService.fetchUser(pfm_hy_comp_cert.getCreatedby()).getFullName();
	}

	@Override
	public String getUuid() {
		
		return pfm_hy_comp_cert.getUuid();
	}

	@Override
	public String getClassName() {
		
		return PFM_hy_comp_cert.class.getName();
	}

	@Override
	public long getClassPK() {
		
		return pfm_hy_comp_cert.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(pfm_hy_comp_cert.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+pfm_hy_comp_cert.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(pfm_hy_comp_cert.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		
		
		boolean isNonNPSUser = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("pfm_hy_comp_cert---"+pfm_hy_comp_cert);
			if(pfm_hy_comp_cert!=null) {
				Long reportUploadLogId = pfm_hy_comp_cert.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
						/*
						 * annex_comp_certificate =
						 * PMFIARUtil.getDocumentURL(pfm_hy_comp_cert.getAnnex_comp_certificate());
						 * extracts_board_pdf =
						 * PMFIARUtil.getDocumentURL(pfm_hy_comp_cert.getExtracts_board_pdf());
						 * annex_comp_certificate_i =
						 * PMFIARUtil.getDocumentURL(pfm_hy_comp_cert.getAnnex_comp_certificate_i());
						 * annex_comp_certificate_ii =
						 * PMFIARUtil.getDocumentURL(pfm_hy_comp_cert.getAnnex_comp_certificate_ii());
						 * annex_comp_certificate_iii =
						 * PMFIARUtil.getDocumentURL(pfm_hy_comp_cert.getAnnex_comp_certificate_iii());
						 */
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(pfm_hy_comp_cert.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(pfm_hy_comp_cert.getCreatedon());
				try {
					List<PFM_hy_comp_cert_Scrutiny>	comp_cert_Scrutinies=PFM_hy_comp_cert_ScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					PFM_hy_comp_cert_Scrutiny pfm_hy_comp_cert_Scrutiny=comp_cert_Scrutinies.get(comp_cert_Scrutinies.size()-1);
					httpServletRequest.setAttribute("pfm_hy_comp_cert_Scrutiny", pfm_hy_comp_cert_Scrutiny);
				}catch (Exception e) {
					_log.error(e);
				}
			}
			
			PfmHyccNonNps pfmIarNonNps = new PfmHyccNonNps();
			isNonNPSUser = pfmIarNonNps.isNonNpsUser(themeDisplay.getUserId());
			WorkflowTask workflowTask= WorkFlowTaskUtil.getWorkFlowtask(PFM_hy_comp_cert.class.getName(), pfm_hy_comp_cert.getReportUploadLogId(), themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), themeDisplay.getUserId());	
			 boolean isAssignable= WorkFlowTaskUtil.isAssignable(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowTaskId());
				boolean isSelfAsignee=WorkFlowTaskUtil.isSelfAsignee(themeDisplay.getCompanyId(), themeDisplay.getUserId(), workflowTask.getWorkflowInstanceId());
				httpServletRequest.setAttribute("isSelfAsignee", isSelfAsignee);
				httpServletRequest.setAttribute("isAssignable", isAssignable);
				httpServletRequest.setAttribute("annexUrl",getDocumentURL(pfm_hy_comp_cert.getHyccfile()) );
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("pfm_hy_comp_cert", pfm_hy_comp_cert);
		/*
		 * httpServletRequest.setAttribute("annex_comp_certificate",
		 * annex_comp_certificate);
		 * httpServletRequest.setAttribute("extracts_board_pdf", extracts_board_pdf);
		 * httpServletRequest.setAttribute("annex_comp_certificate_i",
		 * annex_comp_certificate_i);
		 * httpServletRequest.setAttribute("annex_comp_certificate_ii",
		 * annex_comp_certificate_ii);
		 * httpServletRequest.setAttribute("annex_comp_certificate_iii",
		 * annex_comp_certificate_iii);
		 */
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/scrutiny.jsp";
	}
	
	public static String getDocumentURL(long documentId) {
		 String documentURL = StringPool.BLANK;
		    if(Validator.isNotNull(documentId)) {
		    	long fileEntryId = documentId;
		    	DLFileEntry dlFileEntry = null;
		    	try {
		    		dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId);
				} catch (Exception e) {
					_log.error("Exception in getting dlFileEntry::" + e.getMessage());
				}
		    	if(Validator.isNotNull(dlFileEntry)) {
		    		documentURL = "/documents/"+dlFileEntry.getRepositoryId()+"/"+dlFileEntry.getFolderId()+"/"+dlFileEntry.getTitle();
		    	}
		    }
		    return documentURL;
		}
	
	
	@Reference
	UserLocalService userLocalService;
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	
	
	private static Log _log = LogFactoryUtil.getLog(PFM_hy_comp_certAssetRender.class);

}
