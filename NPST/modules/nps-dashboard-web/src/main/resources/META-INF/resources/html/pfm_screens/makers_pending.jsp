<%@page import="nps.common.service.constants.NPSCompany"%>
<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>
<%@ include file="../../init.jsp"%>

<%
String department = ParamUtil.getString(renderRequest, "department");
JSONArray intermediaryArray = NPSDashboardUtil.getIntermediaryList(user, department);

%>
	<nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	    <li class="breadcrumb-item active" aria-current="page">Pending for Upload</li>
	  </ol>
	</nav>
<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND%>" var="checkReportsMVCResourceCommandURL">
	<portlet:param name="department" value="<%=department%>"/>
</portlet:resourceURL>

<%@include file="/html/pfm_screens/pending_for_uplod.jspf" %>