
<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>
<%@page import="nps.common.service.constants.ReportsNameConstants"%>
<%@page import="com.daily.average.service.service.ReportUploadLogLocalServiceUtil"%>
<%@page import="com.daily.average.service.service.ReportMasterLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FORMS_PDF_CREATION_RESOURCE_COMMAND%>" var="formsPDFCreationsResourceURL"></portlet:resourceURL>
<%
long reportLogId=ParamUtil.getLong(request, "reportUploadLogId");
long reportMasterId=ReportUploadLogLocalServiceUtil.getReportUploadLog(reportLogId).getReportMasterId();
String reportName=ReportMasterLocalServiceUtil.getReportMaster(reportMasterId).getReportName();
%>
<%if(reportName.equalsIgnoreCase(ReportsNameConstants.QUARTERLY_COMPLIANCE_CERTIFICATE)){ %>
<%@ include file="/html/pdf_forms/quarterly-compliance-form.jsp" %>
<%}else if(reportName.equalsIgnoreCase(ReportsNameConstants.MONTHLY_COMPLIANCE_CERTIFICATE)){ %>
<%@ include file="/html/pdf_forms/monthly-compliance-form.jsp" %>
<%}else if(reportName.equalsIgnoreCase(ReportsNameConstants.HY_COMPLIANCE_CERTIFICATE)){ %>
<%@ include file="/html/pdf_forms/hy-compliance-form.jsp" %>
<%} else if(reportName.equalsIgnoreCase(ReportsNameConstants.ANNUALLY_COMPLIANCE_CERTIFICATE)){ %>
<%@ include file="/html/pdf_forms/annually-compliance-form.jsp" %>
<%} %>


