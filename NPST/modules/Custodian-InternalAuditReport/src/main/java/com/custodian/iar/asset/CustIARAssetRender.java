package com.custodian.iar.asset;

import com.custodian.iar.util.CustodianIARUtil;
import com.custodian.iar.util.CustodianInternalAuditReportUtil;
import com.daily.average.service.model.CustIAR;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

public class CustIARAssetRender extends BaseJSPAssetRenderer<CustIAR> {
	
	private CustIAR custIAR;
	
	public CustIARAssetRender(CustIAR custIAR) {
		this.custIAR = custIAR;
	}

	@Override
	public CustIAR getAssetObject() {
		
		return custIAR;
	}

	@Override
	public long getGroupId() {
		
		return userLocalService.fetchUser(custIAR.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		
		return custIAR.getCreatedby();
	}

	@Override
	public String getUserName() {

		return userLocalService.fetchUser(custIAR.getCreatedby()).getFullName();
	}

	@Override
	public String getUuid() {
		
		return custIAR.getUuid();
	}

	@Override
	public String getClassName() {
		
		return CustIAR.class.getName();
	}

	@Override
	public long getClassPK() {
		
		return custIAR.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(custIAR.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+custIAR.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(custIAR.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String Internal_audit_reportURL="";
		
		boolean isNonNPSUser = false;
		
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("qtrStewardshipReport---"+custIAR);
			if(custIAR!=null) {
				Long reportUploadLogId = custIAR.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						Internal_audit_reportURL=CustodianIARUtil.getDocumentURL(custIAR.getIar_file_id());
						httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
						httpServletRequest.setAttribute("reportMasterId", custIAR.getReportMasterId());
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(custIAR.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(custIAR.getCreatedon());
			}
			CustodianInternalAuditReportUtil  custodianInternalAuditReportUtil = new CustodianInternalAuditReportUtil();
			isNonNPSUser = custodianInternalAuditReportUtil.isNonNpsUser(themeDisplay.getUserId());
			
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("custIAR", custIAR);
		httpServletRequest.setAttribute("Internal_audit_reportURL", Internal_audit_reportURL);
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/internal_audit_workflow_abstract_view.jsp";
	}
	
	@Reference
	UserLocalService userLocalService;
	
	Log _log = LogFactoryUtil.getLog(CustIARAssetRender.class);

}
