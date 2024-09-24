<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.util.Map"%>
<%@ include file="/init.jsp" %>

<%
String randomId = workflowTaskDisplayContext.getWorkflowTaskRandomId();

String closeRedirect = ParamUtil.getString(request, "closeRedirect");
System.out.println(" closeRedirect  ::: "+closeRedirect);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

PortletURL redirectURL = renderResponse.createRenderURL();

redirectURL.setParameter("mvcPath", "/view.jsp");

/* Creant Modification*/

Map<String, Serializable> maps = workflowTask.getOptionalAttributes();
String entryClassName = String.valueOf(maps.get("entryClassName"));
System.out.println("entryClassName : "+entryClassName);

boolean isChecker  = com.liferay.portal.kernel.service.RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "Checker", false);
boolean isSupervisor  = com.liferay.portal.kernel.service.RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "Supervisor", false);
List<String> pfmClassNames = Arrays.asList("com.daily.average.service.model.ReportUploadLogPFM", "com.daily.average.service.model.ReportUploadLogPFMAM", "com.daily.average.service.model.ReportUploadLogPFMCRA");
List<String> craClassNames = Arrays.asList("com.daily.average.service.model.ReportUploadLogCRA");

System.out.println(" >>>>>>>>>>>>>>>>>>>>>> "+pfmClassNames.contains(entryClassName));

String jspPathURL = "/web/guest/dashboard?p_p_id=com_nps_dashboard_web_NPSDashboardWebPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath=/html/pending_reports.jsp";
if(pfmClassNames.contains(entryClassName)){
	jspPathURL = "/web/guest/dashboard?p_p_id=com_nps_dashboard_web_NPSDashboardWebPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath=/html/pfm_screens/pfm_pending_review_reports.jsp";
}else if(craClassNames.contains(entryClassName)){
	jspPathURL = "/web/guest/dashboard?p_p_id=com_nps_dashboard_web_NPSDashboardWebPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath=/html/pending_reports.jsp";
}

System.out.println("jspPathURL in ::: "+jspPathURL);

/* Creant Modification*/
%>

<liferay-ui:icon-menu
	cssClass="lfr-asset-actions"
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showExpanded="<%= row == null %>"
>
	<c:if test="<%= !workflowTask.isCompleted() %>">
		<c:choose>
			<c:when test="<%= workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">
				
				<%
				List<String> transitionNames = workflowTaskDisplayContext.getTransitionNames(workflowTask);

				for (String transitionName : transitionNames) {
					String message = workflowTaskDisplayContext.getTransitionMessage(transitionName);
				%>
				<%--==START== Creant modification--%>
                <%
	                    String transitionRedirectURL = jspPathURL + "&_com_nps_dashboard_web_NPSDashboardWebPortlet_transitionName="+transitionName;
                %>
                <%System.out.println("transitionRedirectURL:"+transitionRedirectURL ); %>
				<%--==START== Creant modification--%>
					<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/portal_workflow_task/complete_task" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="editURL">
						<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
						<%-- <portlet:param name="redirect" value="<%= transitionRedirectURL %>" /> --%>
						<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
						<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
						<portlet:param name="assigneeUserId" value="<%= String.valueOf(workflowTask.getAssigneeUserId()) %>" />

						<c:if test="<%= transitionName != null %>">
							<portlet:param name="transitionName" value="<%= transitionName %>" />
						</c:if>
					</liferay-portlet:actionURL>

				<% System.out.println("editURL : "+editURL); %>
					<liferay-ui:icon
						cssClass='<%= "workflow-task-" + randomId + " task-change-status-link" %>'
						data="<%= workflowTaskDisplayContext.getWorkflowTaskActionLinkData() %>"
						id='<%= randomId + HtmlUtil.escapeAttribute(transitionName) + "taskChangeStatusLink" %>'
						message="<%= HtmlUtil.escape(message) %>"
						method="get"
						url="<%= editURL %>"
					/>

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="assignToMeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/workflow_task_assign.jsp" />
					<portlet:param name="redirect" value='<%= Validator.isNull(request.getParameter("workflowTaskId")) ? redirectURL.toString() : currentURL %>' />
					<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
					<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					message="assign-to-me"
					onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "taskAssignToMe('" + assignToMeURL + "');" %>'
					url="javascript:;"
				/>
			</c:otherwise>
		</c:choose>

		<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="assignURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/workflow_task_assign.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</liferay-portlet:renderURL>
		<liferay-ui:icon
			message=""
			onClick=''
			url="javascript:;"
		/>
		<%-- <liferay-ui:icon
			message="assign-to-..."
			onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "taskAssign('" + assignURL + "');" %>'
			url="javascript:;"
		/> --%>

		<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="updateDueDateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/workflow_task_due_date.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</liferay-portlet:renderURL>

		<%-- <liferay-ui:icon
			message="update-due-date"
			onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "taskUpdate('" + updateDueDateURL + "');" %>'
			url="javascript:;"
		/> --%>
	</c:if>
</liferay-ui:icon-menu>

<aui:form name='<%= randomId + "form" %>' id="fm">
	<div class="hide" id="<%= randomId %>updateComments">
		<aui:input cols="57" cssClass="task-content-comment" name="comment" placeholder="comment" id="taskCommentId" rows="1" type="textarea">
			<aui:validator name="required" errorMessage="Please enter a message." />
		</aui:input>
		<div class="portlet-msg-error" style="display:none;" id="errorMsg">Please enter remarks.</div>
	</div>
</aui:form>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind(
		'onTaskClick',
		Liferay.WorkflowTasks,
		'<%= randomId %>'
	);
console.log("<%=workflowTask.getWorkflowDefinitionName() %>");
	<c:if test="<%= !workflowTask.isCompleted() && workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">

		<%
		List<String> transitionNames = workflowTaskDisplayContext.getTransitionNames(workflowTask);

		for (String transitionName : transitionNames) {
		%>

			Liferay.delegateClick(
				'<portlet:namespace /><%= randomId + HtmlUtil.escapeJS(transitionName) %>taskChangeStatusLink',
				onTaskClickFn
			);

		<%
		}
		%>

	</c:if>
</aui:script>

<aui:script>
	function <portlet:namespace />taskAssign(uri) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 430,
				resizable: false,
				width: 896,
			},
			dialogIframe: {
				bodyCssClass: 'task-dialog',
			},
			id: '<portlet:namespace />assignToDialog',
			title: '<liferay-ui:message key="assign-to-..." />',
			uri: uri,
		});
	}

	function <portlet:namespace />taskAssignToMe(uri) {
		console.log('<%= workflowTask.getWorkflowDefinitionName()%>');
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 340,
				resizable: false,
				width: 896,
			},
			dialogIframe: {
				bodyCssClass: 'task-dialog',
			},
			id: '<portlet:namespace />assignToDialog',
			title: '<liferay-ui:message key="assign-to-me" />',
			uri: uri,
		});
	}

	function <portlet:namespace />taskUpdate(uri) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 430,
				resizable: false,
				width: 896,
			},
			dialogIframe: {
				bodyCssClass: 'task-dialog',
			},
			id: '<portlet:namespace />updateDialog',
			title: '<liferay-ui:message key="update-due-date" />',
			uri: uri,
		});
	}

	function <portlet:namespace />refreshPortlet(uri) {
		location.href = uri;
	}
</aui:script>