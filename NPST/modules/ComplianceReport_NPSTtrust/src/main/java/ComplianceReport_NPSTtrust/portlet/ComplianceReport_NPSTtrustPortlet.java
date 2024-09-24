package ComplianceReport_NPSTtrust.portlet;

import ComplianceReport_NPSTtrust.constants.ComplianceReport_NPSTtrustPortletKeys;

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
		"javax.portlet.display-name=ComplianceReport_NPSTtrust",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ComplianceReport_NPSTtrustPortletKeys.COMPLIANCEREPORT_NPSTTRUST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ComplianceReport_NPSTtrustPortlet extends MVCPortlet {
}