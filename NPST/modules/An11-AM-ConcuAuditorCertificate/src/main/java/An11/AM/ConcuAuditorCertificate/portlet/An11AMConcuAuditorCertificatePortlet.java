package An11.AM.ConcuAuditorCertificate.portlet;

import An11.AM.ConcuAuditorCertificate.constants.An11AMConcuAuditorCertificatePortletKeys;

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
		"javax.portlet.display-name=An11AMConcuAuditorCertificate",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + An11AMConcuAuditorCertificatePortletKeys.AN11AMCONCUAUDITORCERTIFICATE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class An11AMConcuAuditorCertificatePortlet extends MVCPortlet {
}