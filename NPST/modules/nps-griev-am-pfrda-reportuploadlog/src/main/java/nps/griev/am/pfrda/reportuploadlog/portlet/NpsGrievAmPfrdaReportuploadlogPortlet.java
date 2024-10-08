package nps.griev.am.pfrda.reportuploadlog.portlet;

import nps.griev.am.pfrda.reportuploadlog.constants.NpsGrievAmPfrdaReportuploadlogPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author ASUS
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=NpsGrievAmPfrdaReportuploadlog",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + NpsGrievAmPfrdaReportuploadlogPortletKeys.NPSGRIEVAMPFRDAREPORTUPLOADLOG,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NpsGrievAmPfrdaReportuploadlogPortlet extends MVCPortlet {
}