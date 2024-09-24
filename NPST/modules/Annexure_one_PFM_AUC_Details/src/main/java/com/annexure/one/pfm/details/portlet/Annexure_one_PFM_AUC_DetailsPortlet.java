package com.annexure.one.pfm.details.portlet;

import com.annexure.one.pfm.details.constants.Annexure_one_PFM_AUC_DetailsPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Admin
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Annexure_one_PFM_AUC_Details",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Annexure_one_PFM_AUC_DetailsPortletKeys.ANNEXURE_ONE_PFM_AUC_DETAILS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Annexure_one_PFM_AUC_DetailsPortlet extends MVCPortlet {
}