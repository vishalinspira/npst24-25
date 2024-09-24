package com.nps.dashboard.web.mvc;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;
import com.nps.dashboard.web.util.NPSDashboardUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=/department/pages"
		},
		service = MVCRenderCommand.class
)
public class GetDepartmentPagesRenderCommand implements MVCRenderCommand{
	private static final Log _log = LogFactoryUtil.getLog(GetDepartmentPagesRenderCommand.class);
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		_log.info("Calling GetDepartmentPagesRenderCommand................");
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String departmentName = ParamUtil.getString(renderRequest, "department", NPSCompany.NPST);
		_log.info("department name::" + departmentName);
		JSONObject assignedRole = npsDashboardUtil.getDepartmentAssignedRole(themeDisplay, departmentName);
		_log.info("Assigned roles :: "+assignedRole+" For the comapny : "+departmentName);
		renderRequest.setAttribute("assignedRole", assignedRole);
		renderRequest.setAttribute("department", departmentName);
		
		return "/department_pages.jsp";
	}
	
	@Reference
	private NPSDashboardUtil npsDashboardUtil;

}
