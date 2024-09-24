package com.nps.pfm.monthly.ard.portlet;

import com.nps.pfm.monthly.ard.constants.PFMMonthlyAUMAndReturnDataPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author PRIYANKA
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=PFMMonthlyAUMAndReturnData",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PFMMonthlyAUMAndReturnDataPortletKeys.PFMMONTHLYAUMANDRETURNDATA,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class PFMMonthlyAUMAndReturnDataPortlet extends MVCPortlet {
}