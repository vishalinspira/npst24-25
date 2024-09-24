<%@page import="nps.common.service.util.CommonRoleService"%>
<%@page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil"%>
<%@page import="AnnualComplaincereport_Form.constants.AnnualComplaincereport_FormPortletKeys"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<portlet:resourceURL id="<%=AnnualComplaincereport_FormPortletKeys.annualcomplaincereport%>" var="annualComplainstResourceURL" />


<%

String companies = CommonRoleService.getCompanyName(user);
/* String companies = "";
	if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "SBI", false)){
		companies = "SBI Pension Funds Private Limited";
	}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "UTI", false)){
		companies = "UTI Retirement Solutions Limited";
	}
	else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "LIC", false)){
		companies = "LIC Pension Fund Limited";
	}
	else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "Kotak", false)){
		companies = "Kotak Mahindra Pension Fund Limited";
	}
	else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "ABS", false)){
		companies = "Aditya Birla Sun Life Pension Management Limited";
	}
	else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "HDFC", false)){
		companies = "HDFC Pension Management Company Limited";
	}
	else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "ICICI", false)){
		companies = "ICICI Prudential Pension Funds Management Company Limited";
	}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "TATA", false)){
		companies = "Tata Pension Management Ltd";
	}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "MLP", false)){
		companies = "Max Life Pension Fund Management Ltd";
	}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "APF", false)){
		companies = "Axis Pension Fund Management Ltd";
	}else if(RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "DSP", false)){
	companies = "DSP Pension Fund Management Ltd";
} */
%>