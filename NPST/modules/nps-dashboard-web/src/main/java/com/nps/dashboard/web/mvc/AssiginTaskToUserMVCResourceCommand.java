package com.nps.dashboard.web.mvc;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys;

import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + NPSDashboardWebPortletKeys.NPSDASHBOARDWEB,
				"mvc.command.name=" + NPSDashboardWebPortletKeys.ASSIGN_REPORTS_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)

public class AssiginTaskToUserMVCResourceCommand implements MVCResourceCommand{
	Log LOG = LogFactoryUtil.getLog(AssiginTaskToUserMVCResourceCommand.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		LOG.info("Calling AssiginTaskToUserMVCResourceCommand");
		//ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		User user = null;
		try {
			user = PortalUtil.getUser(resourceRequest);
		} catch (PortalException e1) {
			LOG.error(e1.getMessage());
		}
		Company company = null;
		try {
			company = PortalUtil.getCompany(resourceRequest);
		} catch (PortalException e1) {
			LOG.error(e1.getMessage());
		}
        long companyId = company.getCompanyId();
        
		JSONObject jsonPendingDocs = JSONFactoryUtil.createJSONObject();
		jsonPendingDocs = assignTaskToUser(companyId, user.getUserId(),resourceRequest);
		LOG.info("returning json object::" + jsonPendingDocs);
		try {
			PrintWriter out = resourceResponse.getWriter();
			out.print(jsonPendingDocs.toString());
			out.flush();
		} catch (Exception e) {
			LOG.error("Error Sending Feedbacks" + e.getMessage());
		}
		
		return true;
	}
	
	public JSONObject assignTaskToUser(long companyId, long assigneeUserId,ResourceRequest resourceRequest) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		long workflowTaskId=ParamUtil.getLong(resourceRequest, "workflowTaskId");
		long assignedToUserId = ParamUtil.getLong(resourceRequest, "assignedUser");
		String comment = ParamUtil.getString(resourceRequest, "comment");
		String workflowContext = ParamUtil.getString(resourceRequest, "workflowContext");
		LOG.info("workflowTaskId : "+workflowTaskId);
		LOG.info("assignedToUserId : "+assignedToUserId);
		LOG.info("comment : "+comment);
		
		/* Give permission to assignedToUserId start */
		User user = null;
		User user1 = null;
		try {
			user = UserLocalServiceUtil.getUserById(assigneeUserId);
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(user);
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		} catch (Exception e) {
			LOG.error("Exception in getting user::" + e.getMessage());
		}
		/* Give permission to assignedToUserId end */
		
		try {
			WorkflowTaskManagerUtil.assignWorkflowTaskToUser(companyId, assigneeUserId, workflowTaskId,
					assignedToUserId, comment, null, WorkflowContextUtil.convert(workflowContext));
			jsonObject.put("status", true);
		} catch (Exception e) {
			LOG.error(e);
			jsonObject.put("status", false);
		}
		LOG.info("jsonObject:::::::::::::::::::::::::::::::::::::::::" + jsonObject);
		return jsonObject;
	}

}
