package com.compliance.report.custodian.portlet;

import com.compliance.report.custodian.constants.compliance_report_custodianPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=false",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.display-name=compliance_report_custodian_SCR",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/scrutnyview.jsp",
			"javax.portlet.name=" + compliance_report_custodianPortletKeys.COMPLIANCE_REPORT_CUSTODIAN_SCRUTINY,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ComplianceScrutinyPortlet extends MVCPortlet{

}
