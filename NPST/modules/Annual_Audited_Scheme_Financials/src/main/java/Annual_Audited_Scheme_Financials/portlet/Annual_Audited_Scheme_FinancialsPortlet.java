package Annual_Audited_Scheme_Financials.portlet;

import Annual_Audited_Scheme_Financials.constants.Annual_Audited_Scheme_FinancialsPortletKeys;

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
		"javax.portlet.display-name=Annual_Audited_Scheme_Financials",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Annual_Audited_Scheme_FinancialsPortletKeys.ANNUAL_AUDITED_SCHEME_FINANCIALS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Annual_Audited_Scheme_FinancialsPortlet extends MVCPortlet {
}