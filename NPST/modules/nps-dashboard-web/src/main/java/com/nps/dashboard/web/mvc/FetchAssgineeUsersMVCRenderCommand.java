package com.nps.dashboard.web.mvc;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.constants.NPSCompany;
import nps.common.service.constants.NPSTRoleConstants;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.FETCH_ASSINGEE_RENDER_COMMAND
		},
		service = MVCRenderCommand.class
)
public class FetchAssgineeUsersMVCRenderCommand implements MVCRenderCommand {

	private static final Log log = LogFactoryUtil.getLog(FetchAssgineeUsersMVCRenderCommand.class);
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		log.info("Calling FetchAssgineeUsersMVCRenderCommand..");
 		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String department = ParamUtil.getString(renderRequest, "department", NPSCompany.NPST);
		log.info("Department : "+department);
		Set<User> assginees = new HashSet<>();
		String amRoleName = StringPool.BLANK;
		String mgrRoleName = StringPool.BLANK;
		String faRoleName = StringPool.BLANK;
		List<User> onlyNPSTAMUser = new ArrayList<>();
		
		if(department.equalsIgnoreCase(NPSCompany.PFM)) {
			amRoleName = NPSTRoleConstants.PFM_AM;
			mgrRoleName = NPSTRoleConstants.PFM_MGR;
		}else if(department.equalsIgnoreCase(NPSCompany.CRA)) {
			amRoleName = NPSTRoleConstants.CRA_AM;
		}else if(department.equalsIgnoreCase(NPSCompany.GRIEVANCES)) {
			amRoleName = NPSTRoleConstants.GRIEVANCES_AM;
			mgrRoleName = NPSTRoleConstants.GRIEVANCES_MGR;
		}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)) {
			amRoleName = NPSTRoleConstants.CUSTODIAN_AM;
			mgrRoleName = NPSTRoleConstants.CUSTODIAN_MGR;
			faRoleName = NPSTRoleConstants.CUSTODIAN_FA;
		}else {
			amRoleName = NPSTRoleConstants.NPST_AM;
		}
		log.info("Am Role: "+amRoleName+" MGR Role : "+mgrRoleName);
		if(!amRoleName.isEmpty()) {
			try {
				Role amRole = roleLocalService.getRole(themeDisplay.getCompanyId(), amRoleName);
				List<User> amUsers = userLocalService.getRoleUsers(amRole.getRoleId());
				if(amRoleName.equalsIgnoreCase(NPSTRoleConstants.NPST_AM)) {
					for(User amUser : amUsers) {
						boolean pfmAM = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.PFM_AM, amUser.getUserId(), false);
						boolean craAM = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.CRA_AM, amUser.getUserId(), false);
						boolean grievanceAM = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.GRIEVANCES_AM, amUser.getUserId(), false);
						boolean custodianAM = userLocalService.hasRoleUser(themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN_AM, amUser.getUserId(), false);
						if(pfmAM || craAM || grievanceAM || custodianAM) {
							// Do nothing
						}else {
							onlyNPSTAMUser.add(amUser);
						}
					}
					assginees.addAll(onlyNPSTAMUser);
				}else {
					assginees.addAll(amUsers);
				}
			} catch (PortalException e) {
				log.error("Exception on fetching role by name : "+e.getMessage(),e);
			}
		}
		
		if(!mgrRoleName.isEmpty()) {
			try {
				Role amgrRole = roleLocalService.getRole(themeDisplay.getCompanyId(), mgrRoleName);
				List<User> mgrUsers = userLocalService.getRoleUsers(amgrRole.getRoleId());
				assginees.addAll(mgrUsers);
			} catch (PortalException e) {
				log.error("Exception on fetching role by name : "+e.getMessage(),e);
			}
		}
		
		if(!faRoleName.isEmpty()) {
			try {
				Role faRole = roleLocalService.getRole(themeDisplay.getCompanyId(), faRoleName);
				List<User> faUsers = userLocalService.getRoleUsers(faRole.getRoleId());
				assginees.addAll(faUsers);
			} catch (PortalException e) {
				log.error("Exception on fetching role by name : "+e.getMessage(),e);
			}
		}
		renderRequest.setAttribute("workflowTaskId", ParamUtil.getLong(renderRequest, "workflowTaskId"));
		renderRequest.setAttribute("workflowContext", ParamUtil.getString(renderRequest, "workflowContext"));
		renderRequest.setAttribute("assignees", assginees);
		return "/assign-task-modal.jsp";
	}
	
	@Reference
	private RoleLocalService roleLocalService;
	
	@Reference
	private UserLocalService userLocalService;

}
