<%@page import="nps.common.service.util.CommonRoleService"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.nps.dashboard.web.util.NPSDashboardUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="nps.common.service.constants.NPSCompany"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>

<!-- RENDER COMMAND URLs -->
<liferay-portlet:renderURL var="uploadReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/upload_file.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="allReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/all_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="approvedReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/approved_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="pendingReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pending_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="allReportsNPSTScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/all_reportsnpst.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="makersPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/makers_screens/makers_pending.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="makersReuploadScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/makers_screens/makers_reupload.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="makersReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/makers_screens/makers_report_status.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="makersSubmittedToNPSTScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/makers_screens/makers_submitted_to_npst.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="reportSummaryMVCRenderCommandURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcRenderCommandName" value="<%=NPSDashboardWebPortletKeys.REPORT_SUMMARY_MVC_RENDER_COMMAND%>"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="npstPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/npst_screens/npst_pending.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="npstamPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/npst_screens/npst_am_pending.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="npstReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/npst_screens/npst_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="npstReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/npst_screens/npst_report_status.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="npstApprovedReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/npst_screens/npst_approved_report.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="pfrdaApprovedReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfrda_screens/npst_approved_report.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="npstPendingReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/npst_screens/npst_pending_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="npstSubPendingForReviewURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/npst_screens/npst_sub_pending_review_reports.jsp"/>
	<liferay-portlet:param name="department" value="<%= NPSCompany.NPST %>"/>
</liferay-portlet:renderURL>
<!-- RENDER COMMAND URLs -->

<!-- RESOURCE COMMAND URLs -->
<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.UPLOAD_REPORTS_RESOURCE_COMMAND%>" var="uploadReportsResourceCommandURL"/>

<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FETCH_REPORTS_RESOURCE_COMMAND%>" var="fetchReportsResourceCommandURL"/>

<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.ADD_REMARKS_RESOURCE_COMMAND%>" var="addRemarksResourceCommandURL"/>

<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FETCH_PENDING_REVIEW_REPORTS_RESOURCE_COMMAND%>" var="fetchPendingReviewReportsResourceCommandURL"/>

<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FETCH_CACHE_RESOURCE_COMMAND%>" var="fetchCacheResourceCommandURL" />
<!-- RESOURCE COMMAND URLs -->

<!-- PFM User URLs -->
<liferay-portlet:renderURL var="companyMakersPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/makers_pending.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="companyReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/report_status.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="pfmMakerSubmittedToNpstURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/makers_submitted_to_npst.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="pfmPendingForReviewURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/pfm_pending_review_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="pfmamPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/am_pending.jsp"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="pfmApprovedReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/pfm_approved_report.jsp"/>
<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="pfmApprovedFormsReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/approved_forms.jsp"/>
<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="pfmSubPendingForReviewURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/pfm_sub_pending_review_reports.jsp"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="pfmPendingReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/pfm_screens/pfm_pending_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>


<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FETCH_COMPANY_REPORTS_RESOURCE_COMMAND%>" var="fetchCompanyReportsResourceCommandURL"/>
<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FETCH_PFM_APPROVED_FORMS_RESOURCE_COMMAND%>" var="fetchPFMApprovedFormsResourceCommandURL"/>

<!--  PFM User URLs End -->

<!-- CRA User URLs -->
<liferay-portlet:renderURL var="craMakersPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/cra_screens/makers_pending.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="craMakerSubmittedToNpstURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/cra_screens/makers_submitted_to_npst.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="craPendingForReviewURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/cra_screens/cra_pending_review_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="craamPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/cra_screens/am_pending.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="craPendingReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/cra_screens/cra_pending_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="craApprovedReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/cra_screens/cra_approved_report.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="craSubPendingForReviewURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/cra_screens/cra_sub_pending_review_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>

<!-- CRA User URLs END -->

<!-- CUSTODIAN User URLs -->
<liferay-portlet:renderURL var="custodianMakersPendingScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/custodian_screens/makers_pending.jsp"/>
	<liferay-portlet:param name="department" value="<%= NPSCompany.CUSTODIAN %>"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="custodianMakerSubmittedToNpstURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/custodian_screens/makers_submitted_to_npst.jsp"/>
	<liferay-portlet:param name="department" value="<%= NPSCompany.CUSTODIAN %>"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="custodianPendingForReviewURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/custodian_screens/pending_review_reports.jsp"/>
	<liferay-portlet:param name="department" value="<%= NPSCompany.CUSTODIAN %>"/>
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="custodianPendingReportsScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/custodian_screens/custodian_pending_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="custodianApprovedReportStatusScreenURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/custodian_screens/custodian_approved_report.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL var="custodianSubPendingForReviewURL" windowState="<%=LiferayWindowState.MAXIMIZED.toString()%>">
	<liferay-portlet:param name="mvcPath" value="/html/custodian_screens/custodian_sub_pending_review_reports.jsp"/>
	<liferay-portlet:param name="department" value="${department }"/>
</liferay-portlet:renderURL>
<!-- CUSTODIAN User URLs END -->

<!--  common url  -->
<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FETCH_SUB_PENDING_REVIEW_REPORTS_RESOURCE_COMMAND%>" var="fetchSubPendingForReviewResourceCommandURL"/>
<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.ASSIGN_REPORTS_RESOURCE_COMMAND%>" var="assignTaskToUserResourceCommandURL">
	<liferay-portlet:param name="jspPage" value="blank" />
	<liferay-portlet:param name="mvcPath" value="blank" />
</portlet:resourceURL>


<!-- Common url end -->
<%
boolean isChecker  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CHECKER, false);
boolean isSupervisor  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.SUPERVISOR, false);
boolean isMaker  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.MAKER, false);

boolean isNPSTUser  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.NPST_USER, false);
boolean isNPSTAM  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.NPST_AM, false);
boolean isNPSTDGM  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.NPST_DGM, false);
boolean isNPSTGM  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.NPST_GM, false);
boolean isNPSTCEO  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.NPST_CEO, false);
boolean isPFRDA_TB  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFRDA_TB, false);


boolean isPFRDA_GRIEVANCE  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.GRIEVANCES_PFRDA, false);
boolean isPFRDA_CRA  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CRA_PFRDA, false);
boolean isPFRDA_PFM  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_PFRDA, false);
boolean isPFRDA_CUSTODIAN  = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_PFRDA, false);


boolean isPfmUser = RoleServiceUtil.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.PFM, Boolean.FALSE);
boolean isCustodianUser = RoleServiceUtil.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CUSTODIAN, Boolean.FALSE);
boolean isCraUser = RoleServiceUtil.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.CRA, Boolean.FALSE);
boolean isGreUser = RoleServiceUtil.hasUserRole(themeDisplay.getUserId(), themeDisplay.getCompanyId(), NPSTRoleConstants.GRIEVANCES, Boolean.FALSE);

boolean isCraSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CRA_SUPERVISOR, false);

boolean isPfmMaker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_MAKER, false);
boolean isPfmChecker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_CHECKER, false);
boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
boolean isPFM_AM=RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_AM, false);

boolean isCustodianMaker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_MAKER, false);
boolean isCustodianChecker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_CHECKER, false);
boolean isCustodianSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_SUPERVISOR, false);

boolean isCustodianAM = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_AM, false);
boolean isCustodianMGR = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_MGR, false);
boolean isCustodianDGM = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_DGM, false);
boolean isCustodianGM = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CUSTODIAN_GM, false);

boolean isCraMaker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CRA_MAKER, false);
boolean isCraChecker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.CRA_CHECKER, false);


boolean isGreMaker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.GRIEVANCES_MAKER, false);
boolean isGreChecker = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.GRIEVANCES_CHECKER, false);
boolean isGreSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.GRIEVANCES_SUPERVISOR, false);

boolean isGreAM = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.GRIEVANCES_AM, false);
boolean isGreMGR = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.GRIEVANCES_MGR, false);

boolean isCompanyMaker = (isPfmMaker ||  isCraMaker || isGreMaker);
boolean isCompanyChecker = (isPfmChecker || isCraChecker || isGreChecker);
boolean isCompanySupervisor = (isPfmSupervisor || isGreSupervisor);

String userDepartment = Validator.isNotNull(request.getAttribute("department")) ? (String) renderRequest.getAttribute("department") : "";

System.out.println("User dashboard ::::::::::::::::::::::::::::::::::::::::::::::::::::; "+userDepartment);

String uploadPageURL = "/web/guest/upload-pfm-maker";
if(isCustodianMaker && NPSCompany.CUSTODIAN.equalsIgnoreCase(userDepartment)) {
	uploadPageURL = "/web/guest/upload-custodian-maker";
}else if(isCraMaker && NPSCompany.CRA.equalsIgnoreCase(userDepartment)) {
	uploadPageURL = "/web/guest/upload-cra-maker";
}else if(isCraMaker && NPSCompany.GRIEVANCES.equalsIgnoreCase(userDepartment)){  // Checking cre maker only becoz only cra maker upload grievances reports
	uploadPageURL = "/web/guest/upload-grievances-maker";
}else if(isCraMaker &&  NPSCompany.PFM.equalsIgnoreCase(userDepartment)){
	uploadPageURL = "/web/guest/upload-cra-supervisor";
}

boolean isWeeklyVisible = true;
if(isPfmMaker || isPfmChecker || isPfmSupervisor){
	isWeeklyVisible = false;
}

boolean analyticReport = Boolean.TRUE;
if(isPfmMaker || isPfmChecker || isPfmSupervisor || isCraMaker || isCraChecker || isCraSupervisor || isGreMaker || isGreChecker || isGreSupervisor 
		|| isCustodianMaker || isCustodianChecker || isCustodianSupervisor){
	analyticReport = Boolean.FALSE;
}
String dashboardBaseURL="/web/guest/dashboard";
//String companies = CommonRoleService.getCompanyName(user);
%>