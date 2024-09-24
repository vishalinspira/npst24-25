<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@page import="com.custodian.iar.constants.CustodianInternalAuditReportPortletKeys;"%>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<portlet:resourceURL id="<%=CustodianInternalAuditReportPortletKeys.custodianIAR%>" var="custodianIARResourceURL" />

<portlet:resourceURL id="<%=CustodianInternalAuditReportPortletKeys.custodianIARScrutiny%>" var="custodianIARScrutinyResourceURL" />