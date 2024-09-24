package com.trusteebank.annex9.resource;

import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.portal.kernel.json.JSONException;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import TrusteeBankMonthlyAnnexure_9.constants.TrusteeBankMonthlyAnnexure_9PortletKeys;



@Component(property = { 
		"javax.portlet.name=" + TrusteeBankMonthlyAnnexure_9PortletKeys.TRUSTEEBANKMONTHLYANNEXURE_9,
		"mvc.command.name=/rejectionreturn/getreportlogdetails" 
		}, 
service = MVCResourceCommand.class)
public class GetReportUploadLogDetails implements MVCResourceCommand{
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = getReportUploadLogDetails(resourceRequest, resourceResponse);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}
	
	public JSONObject getReportUploadLogDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse)  {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		_log.info("reportMasterId.........."+reportMasterId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<ReportUploadLog> reportUploadLogs = reportUploadLogLocalService.findByReportMasterIdAndUploaded(reportMasterId, false);
		if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size() >0) {
			try {
				
				jsonObject = JSONFactoryUtil.createJSONObject(reportUploadLogs.get(0).toString());
				_log.info(jsonObject);
				jsonObject.put("ReportDate", dateFormat.format(reportUploadLogs.get(0).getReportDate()));
				jsonObject.put("status", true);
			} catch (JSONException e) {
				 _log.error(e);
			}
			
		}else {
			jsonObject.put("status", false);
		}
		
		return jsonObject;
	}
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}



