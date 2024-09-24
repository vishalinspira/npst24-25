package com.cra.enps.portlet;

import com.cra.enps.constants.CRAEnpsPortletKeys;
import com.daily.average.service.model.CraEnps;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.CraEnpsLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=CRAFormPortlet",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/craview.jsp",
			"javax.portlet.name=" + CRAEnpsPortletKeys.CRAForm,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class CraFormPortlet extends MVCPortlet{
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = getCRAEnpsReport(themeDisplay, renderRequest);
		Long reportUploadLogId = resultJsonObject.getLong("reportUploadLogId");
		List<CraEnps> craEnps = new ArrayList<CraEnps>();
		if(Validator.isNotNull(reportUploadLogId) && reportUploadLogId>0) {
			craEnps = CraEnpsLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
		}
		
		renderRequest.setAttribute("resultJsonObject", resultJsonObject);
		_log.info(craEnps);
		renderRequest.setAttribute("craEnps", craEnps);
		super.render(renderRequest, renderResponse);
	}

	private JSONObject getCRAEnpsReport(ThemeDisplay themeDisplay, RenderRequest renderRequest) {
		Long userId = themeDisplay.getUserId();
		long companyId = themeDisplay.getCompanyId();
		long reportMasterId = 123;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long intermediaryid = getIntermediaryid(userId, companyId);
		List<ReportUploadLog> reportUploadLogs = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndUploadedAndIntermediaryid(reportMasterId, 2, intermediaryid);
		_log.info(reportUploadLogs);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(Validator.isNotNull(reportUploadLogs) && reportUploadLogs.size()>0) {
			ReportUploadLog reportUploadLog =  reportUploadLogs.get(0);
			resultJsonObject.put("reportUploadLogId", reportUploadLog.getReportUploadLogId());
			resultJsonObject.put("reportDate", simpleDateFormat.format(reportUploadLog.getReportDate()));
		}
		return resultJsonObject;
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
	Log _log = LogFactoryUtil.getLog(CraFormPortlet.class);
}
