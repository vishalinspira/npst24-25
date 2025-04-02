<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="npst.common.util.NpstCommonUtil"%>
<%@page import="npst.common.constant.NpstRoleConstant"%>
<%@page import="com.nps.manpower.util.ManpowerUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<liferay-theme:defineObjects />

<portlet:defineObjects />
<!-- 
comment for version upgarde
 <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css" />
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js" ></script>
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script> -->


<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js" ></script>
 <script type="text/javascript" src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
 
 
<%--  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.css" rel="stylesheet"> --%>
 
<%
boolean hasPermission=NpstCommonUtil.hasPermission(NpstRoleConstant.MANPOWER_CREATION_ROLES, user.getUserId(), user.getCompanyId());
boolean isNpstUser=NpstCommonUtil.isNpstUser( user.getUserId(), user.getCompanyId());
%>
	<%-- <portlet:param name="<%=ManpowerEmployeeFieldName.BACK_URL %>" value="<%=themeDisplay.getURLCurrent() %>" /> --%>

<portlet:renderURL var="viewManpowerEmployeeURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	<portlet:param name="mvcPath" value="/view-manpower-employee.jsp" />
</portlet:renderURL>


<portlet:renderURL var="viewManpowerDirectorURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	<portlet:param name="mvcPath" value="/view-manpower-director.jsp" />
</portlet:renderURL>
<portlet:renderURL var="viewInvestmentCommitteeURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	<portlet:param name="mvcPath" value="/view-investment-committee.jsp" />
</portlet:renderURL>
<portlet:renderURL var="viewRiskManagementCommitteeURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	<portlet:param name="mvcPath" value="/view-risk-committee.jsp" />
</portlet:renderURL>

<portlet:renderURL var="viewDesignationHistoryURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	<portlet:param name="mvcPath" value="/designation-history.jsp" />
</portlet:renderURL>

<portlet:renderURL var="backURL" windowState="<%=LiferayWindowState.NORMAL.toString()%>">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

