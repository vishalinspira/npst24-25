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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(renderRequest, "redirect");

long assigneeUserId = ParamUtil.getLong(renderRequest, "assigneeUserId");

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

boolean hasAssignableUsers = workflowTaskDisplayContext.hasAssignableUsers(workflowTask);
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/portal_workflow_task/assign_task" var="assignURL" />

<div class="task-action">
	<aui:form action="<%= assignURL %>" method="post" name="assignFm">
		<div class="modal-body task-action-content">
			<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />

			<c:choose>
				<c:when test="<%= assigneeUserId > 0 %>">
					<aui:input name="assigneeUserId" type="hidden" value="<%= String.valueOf(assigneeUserId) %>" />
				</c:when>
				<c:otherwise>
					<aui:select disabled="<%= !hasAssignableUsers %>" label="assign-to" name="assigneeUserId">

						<%
						for (User assignableUser : workflowTaskDisplayContext.getAssignableUsers(workflowTask)) {
						%>

							<aui:option label="<%= HtmlUtil.escape(assignableUser.getScreenName()) + StringPool.SPACE + StringPool.OPEN_PARENTHESIS + HtmlUtil.escape(assignableUser.getFullName()) + StringPool.CLOSE_PARENTHESIS %>" selected="<%= workflowTask.getAssigneeUserId() == assignableUser.getUserId() %>" value="<%= String.valueOf(assignableUser.getUserId()) %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>

			<aui:input cols="55" cssClass="task-action-comment" disabled="<%= !hasAssignableUsers && (assigneeUserId <= 0) %>" name="comment" placeholder="comment" rows="1" type="textarea" />
		</div>

		<div class="modal-footer">
			<div class="modal-item-last">
				<div class="btn-group">
					<div class="btn-group-item">
						<aui:button name="close" type="cancel" />
					</div>

					<div class="btn-group-item">
						<aui:button disabled="<%= !hasAssignableUsers && (assigneeUserId <= 0) %>" name="done" primary="<%= true %>" value="done" />
					</div>
				</div>
			</div>
		</div>
	</aui:form>
</div>
<!-- Modal -->
<%-- 
<div id="success_tic" class="modal fade success_modal" role="dialog">
	<div class="modal-dialog">
	    <!-- Modal content-->
	    <div class="modal-content">
      		<a class="close" href="#" data-dismiss="modal">&times;</a>
     		<div class="page-body">
     			<h1 style="text-align:center;">
					<div class="checkmark-circle">
  						<div class="background"></div>
  						<div class="checkmark draw"></div>
					</div>
				</h1>
   				<div class="head">
			      <h6>DATA SUBMITED SUCCESSFULY.</h6>
			    </div>
  			</div>
		</div>
	</div>
</div>
 --%>
<aui:script use="aui-base">
	var done = A.one('#<portlet:namespace />done');

	if (done) {
		done.on('click', function (event) {
			var data = new FormData(
				document.querySelector('#<portlet:namespace />assignFm')
			);

			Liferay.Util.fetch('<%= assignURL.toString() %>', {
				body: data,
				method: 'POST',
			}).then(function () {
				//$('#success_tic').modal('show');
				//alert('<%= redirect.toString() %>');
				//alert('<%= PortalUtil.escapeRedirect(redirect.toString()) %>');
				Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
					'<%= redirect.toString() %>'
				);
				
				Liferay.Util.getWindow(
					'<portlet:namespace />assignToDialog'
				).destroy();
			});
		});
	}
</aui:script>