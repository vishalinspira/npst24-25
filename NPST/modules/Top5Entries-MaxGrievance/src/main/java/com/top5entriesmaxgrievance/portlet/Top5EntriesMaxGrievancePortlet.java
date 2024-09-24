package com.top5entriesmaxgrievance.portlet;

import com.top5entriesmaxgrievance.constants.Top5EntriesMaxGrievancePortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Epiphany
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Top5EntriesMaxGrievance",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Top5EntriesMaxGrievancePortletKeys.TOP5ENTRIESMAXGRIEVANCE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Top5EntriesMaxGrievancePortlet extends MVCPortlet {
}