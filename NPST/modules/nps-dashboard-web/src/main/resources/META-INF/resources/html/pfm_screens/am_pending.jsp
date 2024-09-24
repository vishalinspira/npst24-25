<%@ include file="../../init.jsp"%>
<%
JSONArray intermediaryArray = NPSDashboardUtil.getIntermediaryList(user, NPSCompany.PFM);
String department = NPSCompany.PFM;

%>
	<nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	    <li class="breadcrumb-item active" aria-current="page">Pending Reports from AM</li>
	  </ol>
	</nav>
<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND%>" var="checkReportsMVCResourceCommandURL">
	<portlet:param name="action" value="<%=NPSDashboardWebPortletKeys.PENDING_FOR_UPLOAD_BY_AM%>"/>
	<portlet:param name="department" value="<%=NPSCompany.PFM%>"/>
</portlet:resourceURL>

<%@include file="/html/pfm_screens/pending_for_uplod.jspf" %>