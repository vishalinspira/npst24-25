package com.detailedauditreportform.asset;

import com.daily.average.service.model.DAR;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.detailedauditreportform.util.DARNonNPSUserUtil;
import com.detailedauditreportform.util.DARUtil;
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


public class DARAssetRender extends BaseJSPAssetRenderer<DAR> {
	
	private final DAR dar;
	
	@Reference
	UserLocalService userLocalService; 
	
	public DARAssetRender(DAR dar) {
		this.dar = dar;
	}

	@Override
	public DAR getAssetObject() {
		return dar;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(dar.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		return dar.getCreatedby();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(dar.getCreatedby()).getFullName();		
	}

	@Override
	public String getUuid() {
		return dar.getUuid();
	}

	@Override
	public String getClassName() {
		return DAR.class.getName();
	}

	@Override
	public long getClassPK() {
		return dar.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(dar.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+dar.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(dar.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String DARFile_1_URL="";
		String DARFile_2_URL="";
		String DARFile_3_URL="";
		String DARFile_4_URL="";
		String DARFile_5_URL="";
		
		boolean isNonNPSUser = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("dar---"+dar);
			if(dar!=null) {
				Long reportUploadLogId = dar.getPrimaryKey();
				_log.info("reportUploadLogId---"+reportUploadLogId);
			    httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null)
					try {
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						DARFile_1_URL=DARUtil.getDocumentURL(dar.getDar_file_1_id());
						DARFile_2_URL=DARUtil.getDocumentURL(dar.getDar_file_2_id());
						DARFile_3_URL=DARUtil.getDocumentURL(dar.getDar_file_3_id());
						DARFile_4_URL=DARUtil.getDocumentURL(dar.getDar_file_4_id());
						DARFile_5_URL=DARUtil.getDocumentURL(dar.getDar_file_5_id());
					} catch (Exception e) {
						_log.error(e);
					}
					
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(dar.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(dar.getCreatedon());
			}
			DARNonNPSUserUtil darNonNPSUserUtil= new DARNonNPSUserUtil();
			isNonNPSUser = darNonNPSUserUtil.isNonNpsUser(themeDisplay.getUserId());
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("dar", dar);
		httpServletRequest.setAttribute("DARFile_1_URL", DARFile_1_URL);
		httpServletRequest.setAttribute("DARFile_2_URL", DARFile_2_URL);
		httpServletRequest.setAttribute("DARFile_3_URL", DARFile_3_URL);
		httpServletRequest.setAttribute("DARFile_4_URL", DARFile_4_URL);
	    httpServletRequest.setAttribute("DARFile_5_URL", DARFile_5_URL);
	    httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/detailed_audit_workflow_abstract_view.jsp";
	}
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(DARAssetRender.class);

}
