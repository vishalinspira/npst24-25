<%@page import="nps.common.service.constants.NPSCompany"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@ include file="init.jsp"%>
<%
JSONObject assignedRoleObject = (JSONObject) renderRequest.getAttribute("assignedRole");
String department = (String) renderRequest.getAttribute("department");

%>
<%
	if(department.equalsIgnoreCase(NPSCompany.PFM)){
%>
<div class="dashboard_block mx-4  department1">
	<div class="row">
		<% 
		if(assignedRoleObject != null){
			if(assignedRoleObject.getBoolean("isMaker")) { 
		%>
			<%@ include file="/html/dashboard_screens/pfm/pfm_makers.jspf" %>
		<% } else if(assignedRoleObject.getBoolean("isChecker")) { %>
			<%@ include file="/html/dashboard_screens/pfm/pfm_checker.jspf" %>
		<% 
			} else if(assignedRoleObject.getBoolean("isAm")) { 
		%>
			<%@ include file="/html/dashboard_screens/pfm/am.jspf" %>
			<%@ include file="/html/dashboard_screens/pfm/pfm_user_dashboard.jspf" %>
		<!-- Changes for CRA Supervisor -->	
		<%}else if(assignedRoleObject.getBoolean("isCraMaker")) { %>
			<%@ include file="/html/dashboard_screens/cra/cra_makers.jspf" %>
		<%}else {%>
		<%@ include file="/html/dashboard_screens/pfm/pfm_user_dashboard.jspf" %>
		<%}}else {%>
		<%@ include file="/html/dashboard_screens/pfm/pfm_user_dashboard.jspf" %>
		<%} %>
	</div>
</div>
<%
	}else if(department.equalsIgnoreCase(NPSCompany.CRA) || department.equalsIgnoreCase(NPSCompany.GRIEVANCES)){
%>	
<div class="dashboard_block mx-4  department1">
	<div class="row">
		<% 
		if(assignedRoleObject != null){
			if(assignedRoleObject.getBoolean("isMaker")) { %>
				<%@ include file="/html/dashboard_screens/cra/cra_makers.jspf" %>
			<%} else if(assignedRoleObject.getBoolean("isChecker")) { %>
				<%@ include file="/html/dashboard_screens/cra/cra_checker.jspf" %>
			<%}else if(assignedRoleObject.getBoolean("isAm") && department.equalsIgnoreCase(NPSCompany.CRA)){%>
			<%@ include file="/html/dashboard_screens/cra/am.jspf" %>
			<%@ include file="/html/dashboard_screens/cra/user_dashboard.jspf" %>
			<%}else{%>
			<%@ include file="/html/dashboard_screens/cra/user_dashboard.jspf" %>
			<% }
		}else{ %>
		<%@ include file="/html/dashboard_screens/cra/user_dashboard.jspf" %>
		<%} %>
	</div>
</div>
<%	
	}else if(department.equalsIgnoreCase(NPSCompany.CUSTODIAN)){
%>
<div class="dashboard_block mx-4  department1">
	<div class="row">
		<% 
		if(assignedRoleObject != null){
			if(assignedRoleObject.getBoolean("isMaker")) { %>
				<%@ include file="/html/dashboard_screens/custodian/custodian_makers.jspf" %>
			<%} else if(assignedRoleObject.getBoolean("isChecker") || isPfmChecker) { %>
				<%@ include file="/html/dashboard_screens/custodian/custodian_checker.jspf" %>
			<%} else if(assignedRoleObject.getBoolean("isSupervisor") || isPfmSupervisor) { %>
				<%@include file="/html/dashboard_screens/custodian/custodian_supervisor.jspf" %>
			<%}else{%>
			<%@ include file="/html/dashboard_screens/custodian/user_dashboard.jspf" %>
			<% }
		}else{ %>
		<%@ include file="/html/dashboard_screens/custodian/user_dashboard.jspf" %>
		<%} %>
		
	</div>
</div>
<%
	}else {
%>
	<div class="dashboard_block mx-4  department0">
			<div class="row">
				<%if(assignedRoleObject != null && assignedRoleObject.getBoolean("isAm")) { %>
					<%@ include file="/html/dashboard_screens/npst_am.jspf" %>
				<%} %>
				<%@ include file="/html/dashboard_screens/npst_user_dashboard.jspf" %>
			</div>
	</div>
<%
	}
%>
