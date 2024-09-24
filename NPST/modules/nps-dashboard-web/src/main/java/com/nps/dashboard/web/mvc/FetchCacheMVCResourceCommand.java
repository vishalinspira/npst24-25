package com.nps.dashboard.web.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import nps.cache.schema.model.ReportStatus;
import nps.cache.schema.service.ReportStatusLocalServiceUtil;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_CACHE_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class FetchCacheMVCResourceCommand  extends BaseMVCResourceCommand {

	Log _log = LogFactoryUtil.getLog(getClass());
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("in FetchCacheMVCResourceCommand");
		String skey= ParamUtil.getString(resourceRequest, "skey");
		_log.info(skey);
		//_log.info(ReportStatusLocalServiceUtil.getReportStatusesCount());
		ReportStatus reportStatus = ReportStatusLocalServiceUtil.fetchReportStatus(skey);
		String data = (Validator.isNotNull(reportStatus)) ? reportStatus.getData_() : "";
		_log.info(data);
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(data);
		writer.flush();
		return;
	}

	/*@Reference
	ReportStatusLocalService _ReportStatusLocalService;
	*/
}
