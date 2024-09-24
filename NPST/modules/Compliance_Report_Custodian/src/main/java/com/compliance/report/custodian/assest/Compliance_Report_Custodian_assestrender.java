package com.compliance.report.custodian.assest;

import com.compliance.report.custodian.util.ComplianceReportCustodianUtil;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
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

public class Compliance_Report_Custodian_assestrender extends BaseJSPAssetRenderer<CustodianCompForm> {
	
	private CustodianCompForm custodianCompForm;
	
	@Reference
	UserLocalService userLocalService; 
	
	
	public Compliance_Report_Custodian_assestrender(CustodianCompForm custodianCompForm) {
		this.custodianCompForm = custodianCompForm;
	}

	@Override
	public CustodianCompForm getAssetObject() {
		return custodianCompForm;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(custodianCompForm.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		return custodianCompForm.getCreatedby();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(custodianCompForm.getCreatedby()).getFullName();		
	}

	@Override
	public String getUuid() {
		return custodianCompForm.getUuid();
	}

	@Override
	public String getClassName() {
		return CustodianCompForm.class.getName();
	}

	@Override
	public long getClassPK() {
		return custodianCompForm.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(custodianCompForm.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+custodianCompForm.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(custodianCompForm.getReportMasterId());
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
			_log.info("custodianCompForm---"+custodianCompForm);
			if(custodianCompForm!=null) {
				Long reportUploadLogId = custodianCompForm.getPrimaryKey();
				httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(custodianCompForm.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(custodianCompForm.getCreatedate());
			}
			ComplianceReportCustodianUtil complianceReportCustodianUtil = new ComplianceReportCustodianUtil();
			isNonNPSUser = complianceReportCustodianUtil.isNonNpsUser(themeDisplay.getUserId());
			
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("custodianCompForm", custodianCompForm);
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);

		
		return "/asset/scrutiny.jsp";
	}
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(Compliance_Report_Custodian_assestrender.class);

}

