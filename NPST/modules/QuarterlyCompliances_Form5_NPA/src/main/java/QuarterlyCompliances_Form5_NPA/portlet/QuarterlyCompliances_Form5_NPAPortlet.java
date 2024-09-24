package QuarterlyCompliances_Form5_NPA.portlet;

import QuarterlyCompliances_Form5_NPA.constants.QuarterlyCompliances_Form5_NPAPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author peerv
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=QuarterlyCompliances_Form5_NPA",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + QuarterlyCompliances_Form5_NPAPortletKeys.QUARTERLYCOMPLIANCES_FORM5_NPA,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class QuarterlyCompliances_Form5_NPAPortlet extends MVCPortlet {
}