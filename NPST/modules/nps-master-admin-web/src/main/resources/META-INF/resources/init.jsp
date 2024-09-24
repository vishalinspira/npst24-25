<%@page import="com.nps.master.admin.web.constants.NpsMasterAdminWebPortletKeys"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<portlet:resourceURL id="<%=NpsMasterAdminWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND%>" var="checkReportsMVCResourceCommandURL"/>
<portlet:resourceURL id="<%=NpsMasterAdminWebPortletKeys.GEN_REPORT_DATE_MVC_RESOURCE_COMMAND %>" var="genReportDateMVCResourceCommandURL"></portlet:resourceURL>