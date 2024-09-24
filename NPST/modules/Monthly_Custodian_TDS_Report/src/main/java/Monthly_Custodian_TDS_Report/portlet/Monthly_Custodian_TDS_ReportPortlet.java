package Monthly_Custodian_TDS_Report.portlet;

import Monthly_Custodian_TDS_Report.constants.Monthly_Custodian_TDS_ReportPortletKeys;

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
		"javax.portlet.display-name=Monthly_Custodian_TDS_Report",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Monthly_Custodian_TDS_ReportPortletKeys.MONTHLY_CUSTODIAN_TDS_REPORT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Monthly_Custodian_TDS_ReportPortlet extends MVCPortlet {
}