package Cra_Compliance_Report_NPST_Trust.portlet;

import Cra_Compliance_Report_NPST_Trust.constants.Cra_Compliance_Report_NPST_TrustPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author LENOVO
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Cra_Compliance_Report_NPST_Trust",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Cra_Compliance_Report_NPST_TrustPortletKeys.CRA_COMPLIANCE_REPORT_NPST_TRUST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Cra_Compliance_Report_NPST_TrustPortlet extends MVCPortlet {
}