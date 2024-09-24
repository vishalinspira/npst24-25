package com.nps.cra.am.reportuploadlog.portlet;

import com.nps.cra.am.reportuploadlog.constants.NpsCraAmReportuploadlogPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author VishalKumar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=NpsCraAmReportuploadlog",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + NpsCraAmReportuploadlogPortletKeys.NPSCRAAMREPORTUPLOADLOG,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NpsCraAmReportuploadlogPortlet extends MVCPortlet {
}