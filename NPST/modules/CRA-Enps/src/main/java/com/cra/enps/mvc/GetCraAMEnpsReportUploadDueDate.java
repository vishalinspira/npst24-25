package com.cra.enps.mvc;

import com.cra.enps.constants.CRAEnpsPortletKeys;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { "javax.portlet.name=" + CRAEnpsPortletKeys.CRAENPS,
		"mvc.command.name=/craamenps/getreportlogdetails" }, service = MVCResourceCommand.class)
public class GetCraAMEnpsReportUploadDueDate implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = getCRAAMEnpsReportUploadDueDate(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}

	private JSONObject getCRAAMEnpsReportUploadDueDate(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		//Integer uploaded = ParamUtil.getInteger(resourceRequest, "uploaded");
		long intermediaryid = ParamUtil.getLong(resourceRequest, "intermediaryid");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<ReportUploadLog> reportUploadLogs = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndUploadedAndIntermediaryid(reportMasterId, 0, intermediaryid);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size()>0) {
			ReportUploadLog reportUploadLog =  reportUploadLogs.get(0);
			resultJsonObject.put("reportUploadLogId", reportUploadLog.getReportUploadLogId());
			resultJsonObject.put("reportDate", dateFormat.format(reportUploadLog.getReportDate()));
			resultJsonObject.put("status", true);
		}
		return resultJsonObject;
	}
	Log _log = LogFactoryUtil.getLog(getClass());

}
