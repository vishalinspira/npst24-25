package com.DailyAverage.portlet;

import com.DailyAverage.constants.DailyAveragePortletKeys;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=DailyAverage", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DailyAveragePortletKeys.DAILYAVERAGE, "javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class DailyAveragePortlet extends MVCPortlet {

	@Reference
	private ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		boolean uploaded = false;
		long reportMasterId = 1;
		List<ReportUploadLog> reportUploadLogs = reportUploadLogLocalService.findByReportMasterIdAndUploaded(reportMasterId, uploaded);
		renderRequest.setAttribute("reportUploadLogs", reportUploadLogs);
		super.doView(renderRequest, renderResponse);
	}
}
