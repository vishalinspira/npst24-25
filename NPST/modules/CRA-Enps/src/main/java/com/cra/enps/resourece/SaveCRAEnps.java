package com.cra.enps.resourece;

import com.cra.enps.constants.CRAEnpsPortletKeys;
import com.daily.average.service.model.CraEnps;
import com.daily.average.service.service.CraEnpsLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { "javax.portlet.name=" + CRAEnpsPortletKeys.CRAForm,
"mvc.command.name=/compformcustodian/save" }, service = MVCResourceCommand.class)

public class SaveCRAEnps implements MVCResourceCommand {
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		resultJsonObject.put("status", true);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		Integer srno = ParamUtil.getInteger(resourceRequest, "srno");
		Long pran = ParamUtil.getLong(resourceRequest, "pran");
		String subName = ParamUtil.getString(resourceRequest, "subName");
		String claimentName = ParamUtil.getString(resourceRequest, "claimentName");
		Date authClaimDate = ParamUtil.getDate(resourceRequest, "formDate", dateFormat);
		Date claimDispatchDate = ParamUtil.getDate(resourceRequest, "formDate", dateFormat);
		Long trackId = ParamUtil.getLong(resourceRequest, "trackId");
		Date receiptAuthFormDate = ParamUtil.getDate(resourceRequest, "formDate", dateFormat);
		Date claimProcessDate = ParamUtil.getDate(resourceRequest, "formDate", dateFormat);
		Integer tat = ParamUtil.getInteger(resourceRequest, "srno");
		
		Long createdBy = themeDisplay.getUserId();
		
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {

		}
		
		CraEnps  craEnps = CraEnpsLocalServiceUtil.fetchCraEnps(reportUploadLogId);
		CraEnpsLocalServiceUtil.addCraEnps(craEnps);
		return false;
		
		
	}	
		
}
