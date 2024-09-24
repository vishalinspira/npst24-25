package com.nps.manPower.portlet;

import com.nps.manPower.constants.ManPowerForm2PortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lenovo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ManPowerForm2",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ManPowerForm2PortletKeys.MANPOWERFORM2,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.header-portlet-javascript=https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.debug.js",
		"com.liferay.portlet.header-portlet-javascript=/js/html2canvas.min.js"
	},
	service = Portlet.class
)
public class ManPowerForm2Portlet extends MVCPortlet {
}