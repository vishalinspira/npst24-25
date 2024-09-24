package com.nps.dashboard.web.mvc;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.UPLOAD_REPORTS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class UploadReportsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		npsDashboardUtil.addReportsToDL(resourceRequest, "npsUpdatedReport", "Daily");
	}
	
	@Reference
	NPSDashboardUtil npsDashboardUtil;
	
}
