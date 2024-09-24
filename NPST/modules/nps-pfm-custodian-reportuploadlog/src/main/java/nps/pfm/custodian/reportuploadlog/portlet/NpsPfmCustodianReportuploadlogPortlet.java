package nps.pfm.custodian.reportuploadlog.portlet;

import nps.pfm.custodian.reportuploadlog.constants.NpsPfmCustodianReportuploadlogPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Comp
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=NpsPfmCustodianReportuploadlog",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + NpsPfmCustodianReportuploadlogPortletKeys.NPSPFMCUSTODIANREPORTUPLOADLOG,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NpsPfmCustodianReportuploadlogPortlet extends MVCPortlet {
}