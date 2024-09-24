package com.cra.enps.mvc;

import com.cra.enps.constants.CRAEnpsPortletKeys;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { "javax.portlet.name=" + CRAEnpsPortletKeys.CRAForm,
		"mvc.command.name=/cranpstrust/save" }, service = MVCResourceCommand.class)
public class GetCraEnpsReportUploadDueDate implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = getCRAEnpsReportUploadDueDate(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}

	private JSONObject getCRAEnpsReportUploadDueDate(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		long companyId = themeDisplay.getCompanyId();
		long reportMasterId = 123;
		
		long intermediaryid = getIntermediaryid(userId, companyId);
		List<ReportUploadLog> reportUploadLogs = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndUploadedAndIntermediaryid(reportMasterId, 0, intermediaryid);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size()>0) {
			ReportUploadLog reportUploadLog =  reportUploadLogs.get(0);
			resultJsonObject.put("reportUploadLogId", reportUploadLog.getReportUploadLogId());
			resultJsonObject.put("reportDate", reportUploadLog.getReportDate());
		}
		return null;
	}

	private long getIntermediaryid(long userId, long companyId) {
		try {
			if(RoleLocalServiceUtil.hasUserRole(userId, companyId, "KCRA-Maker", false)) {
				return 1l;
			}else if (RoleLocalServiceUtil.hasUserRole(userId, companyId, "NCRA-Maker", false)) {
				return 2l;
			}else if (RoleLocalServiceUtil.hasUserRole(userId, companyId, "CAMS-Maker", false)) {
				return 3l;
			}
		} catch (PortalException e) {
			 _log.error(e);
		}
		return 0;
	}
	Log _log = LogFactoryUtil.getLog(getClass());
}
