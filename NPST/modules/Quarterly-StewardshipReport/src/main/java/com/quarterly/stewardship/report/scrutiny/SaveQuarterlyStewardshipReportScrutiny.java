package com.quarterly.stewardship.report.scrutiny;

import com.daily.average.service.service.QtrStewardshipReportScrutinyLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.quarterly.stewardship.report.constants.QuarterlyStewardshipReportPortletKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { 
		"javax.portlet.name=" + QuarterlyStewardshipReportPortletKeys.QUARTERLYSTEWARDSHIPREPORT,
		"mvc.command.name=" + QuarterlyStewardshipReportPortletKeys.saveQrStewardshipScrutiny, 
		}, 
service = MVCResourceCommand.class)

public class SaveQuarterlyStewardshipReportScrutiny implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(SaveQuarterlyStewardshipReportScrutiny.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method 2");
		
		String conflict_rem = ParamUtil.getString(resourceRequest, "conflict_rem");
		String monitoring_rem = ParamUtil.getString(resourceRequest, "monitoring_rem");
		String resolutions_rem = ParamUtil.getString(resourceRequest, "resolutions_rem");
		String resolutionsVoted1_rem = ParamUtil.getString(resourceRequest, "resolutionsVoted1_rem");
		String adversealert_rem = ParamUtil.getString(resourceRequest, "adversealert_rem");
		String insInvestorSituation_rem = ParamUtil.getString(resourceRequest, "insInvestorSituation_rem");
		String annexureA_rem = ParamUtil.getString(resourceRequest, "annexureA_rem");
		String annexureB_I_rem = ParamUtil.getString(resourceRequest, "annexureB_I_rem");
		String annexureB_II_rem = ParamUtil.getString(resourceRequest, "annexureB_II_rem");
		String annexureC_rem = ParamUtil.getString(resourceRequest, "annexureC_rem");
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		//double version = ParamUtil.getDouble(resourceRequest, "version");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean isError = false;
		
		try {
			qtrStewardshipReportScrutinyLocalService.saveQtrStewardshipReportScrutiny(themeDisplay.getUser().getFullName(), 0, 
					themeDisplay.getUserId(), conflict_rem, monitoring_rem, 
					resolutions_rem, annexureA_rem, annexureB_I_rem, resolutionsVoted1_rem, adversealert_rem, insInvestorSituation_rem,
					annexureB_II_rem, annexureC_rem, new Date(), themeDisplay.getUserId(), reportUploadLogId);
		} catch (Exception e) {
			isError = true;
			_log.error(e.getMessage(), e);
		}
		
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				writer.print("Success");
			}
			
		} catch (IOException e) {
			_log.error(e);
		}
		
		return false;
	}
	
	@Reference
	QtrStewardshipReportScrutinyLocalService qtrStewardshipReportScrutinyLocalService;
	
	@Reference(unbind = "-")
	public void setQtrStewardshipReportScrutinyLocalService(
			QtrStewardshipReportScrutinyLocalService qtrStewardshipReportScrutinyLocalService) {
		this.qtrStewardshipReportScrutinyLocalService = qtrStewardshipReportScrutinyLocalService;
	}


}
