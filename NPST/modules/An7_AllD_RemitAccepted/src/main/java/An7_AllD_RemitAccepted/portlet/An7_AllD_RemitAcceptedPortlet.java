package An7_AllD_RemitAccepted.portlet;

import An7_AllD_RemitAccepted.constants.An7_AllD_RemitAcceptedPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author deepak
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=An7_AllD_RemitAccepted",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + An7_AllD_RemitAcceptedPortletKeys.AN7_ALLD_REMITACCEPTED,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class An7_AllD_RemitAcceptedPortlet extends MVCPortlet {
}