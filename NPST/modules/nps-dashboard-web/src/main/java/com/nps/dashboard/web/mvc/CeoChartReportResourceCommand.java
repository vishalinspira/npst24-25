package com.nps.dashboard.web.mvc;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSGraphReportService;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.CEO_CHART_REPORTS_COMMAND
		},
		service = MVCResourceCommand.class
)
public class CeoChartReportResourceCommand extends BaseMVCResourceCommand{
	private static final Log _log = LogFactoryUtil.getLog(CeoChartReportResourceCommand.class);
	private static final String REPORT_TYPE_DEPARTMENT_LEVEL="Department level";
	private static final String REPORT_TYPE_PFRDA_LEVEL="PFRDA level";
	private static final String CHART_TYPE_PIE="Pie";
	private static final String CHART_TYPE_BAR="Bar";
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("Calling CeoChartReportResourceCommand.......");
		String chartType = ParamUtil.getString(resourceRequest, "chartType");
		String reportType = ParamUtil.getString(resourceRequest, "reportType");
		Boolean isDrillDownData = ParamUtil.getBoolean(resourceRequest, "dd", false);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		_log.info("Chart type :: "+chartType+" Report Type :: "+reportType);
		
		JSONObject chartObject = JSONFactoryUtil.createJSONObject();
		
		if(reportType.equalsIgnoreCase(REPORT_TYPE_DEPARTMENT_LEVEL)) {
			if(chartType.equalsIgnoreCase(CHART_TYPE_PIE)){
				chartObject = npsGraphReportService.getDepartmentReport(themeDisplay);
			}else if(chartType.equalsIgnoreCase(CHART_TYPE_BAR)){
				String department = ParamUtil.getString(resourceRequest, "department", NPSCompany.NPST);
				_log.info("Department : "+department);
				if(isDrillDownData) {
					chartObject = npsGraphReportService.getDepartmentChartDrilldownArray(department, themeDisplay);
				}else {
					chartObject = npsGraphReportService.getDepartmentChartData(department,themeDisplay);
				}
				
			}
		}else if(reportType.equalsIgnoreCase(REPORT_TYPE_PFRDA_LEVEL)){
			if(chartType.equalsIgnoreCase(CHART_TYPE_PIE)) {
				chartObject = npsGraphReportService.getPFRDADepartmentReport(themeDisplay);
			}
		}
		
		resourceResponse.getWriter().print(chartObject);
	}
	
	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference
	private NPSGraphReportService npsGraphReportService;

}
