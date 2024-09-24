<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.petra.string.StringPool"%>
<%@page import="com.trusteebank.annex11.configuration.IAuditorCertificate"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />


<% IAuditorCertificate auditorCetificateConfiguration=
		(IAuditorCertificate)
			renderRequest.getAttribute(IAuditorCertificate.class.getName());

		String email = StringPool.BLANK;
		String sms = StringPool.BLANK;
		String emailSelected = "";
		String smsSelected = "";
		if(Validator.isNotNull(auditorCetificateConfiguration)){
			email = portletPreferences.getValue("email",auditorCetificateConfiguration.email());
			emailSelected = (Validator.isNotNull(email) && email.equalsIgnoreCase("Mail")?"checked='checked'":"");
		}
		if(Validator.isNotNull(auditorCetificateConfiguration)){
			sms = portletPreferences.getValue("sms", auditorCetificateConfiguration.sms());
			smsSelected = (Validator.isNotNull(email) && sms.equalsIgnoreCase("SMS")?"checked='checked'":"");
		}
		
%>
