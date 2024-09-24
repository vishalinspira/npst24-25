package com.cra.enps.mvc;

import com.cra.enps.constants.CRAEnpsPortletKeys;
import com.daily.average.service.model.CraEnps;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.CraEnpsLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { "javax.portlet.name=" + CRAEnpsPortletKeys.CRAENPS,
		"mvc.command.name=/craamenps/save" }, service = MVCResourceCommand.class)
public class SaveCRAAMEnpsResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = saveCRAAMEnps(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}

	private JSONObject saveCRAAMEnps(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		int [] srno = ParamUtil.getIntegerValues(resourceRequest, "srno[]");
		long [] pran = ParamUtil.getLongValues(resourceRequest, "pran[]");
		String [] subName = ParamUtil.getStringValues(resourceRequest, "subName[]");
		String [] claimentName = ParamUtil.getStringValues(resourceRequest, "claimentName[]");
		Date [] authClaimDate = ParamUtil.getDateValues(resourceRequest, "authClaimDate[]", dateFormat);
		Date [] claimDispatchDate = ParamUtil.getDateValues(resourceRequest, "claimDispatchDate[]", dateFormat);
		String [] trackId = ParamUtil.getStringValues(resourceRequest, "trackId[]");
		List<CraEnps> craEnps = new ArrayList<CraEnps>();
		for (int i = 0; i < trackId.length; i++) {
			CraEnps craEnps2 = CraEnpsLocalServiceUtil.createCraEnps(CounterLocalServiceUtil.increment(CraEnps.class.getName()));
			craEnps2.setSrno(i+1);
			craEnps2.setPran(pran[i]);
			craEnps2.setSubName(subName[i]);
			craEnps2.setClaimentName(claimentName[i]);
			craEnps2.setAuthClaimDate(authClaimDate[i]);
			craEnps2.setClaimDispatchDate(claimDispatchDate[i]);
			craEnps2.setTrackId(trackId[i]);
			craEnps2.setReportUploadLogId(reportUploadLogId);
			_log.info("craEnps2"+craEnps2);
			craEnps.add(craEnps2);
		}
		CraEnpsLocalServiceUtil.addCraEnpsList(craEnps);
		ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil.fetchReportUploadLog(reportUploadLogId);
		reportUploadLog.setUploaded_i(2);
		ReportUploadLogLocalServiceUtil.updateReportUploadLog(reportUploadLog);
		return resultJsonObject;
	}
	Log _log = LogFactoryUtil.getLog(SaveCRAAMEnpsResourceCommand.class);
}
