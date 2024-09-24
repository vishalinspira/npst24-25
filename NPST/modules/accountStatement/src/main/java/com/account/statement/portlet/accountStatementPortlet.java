package com.account.statement.portlet;

import com.account.statement.constants.accountStatementPortletKeys;
import com.account.statement.portlet.configuration.AccountStatementConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;



@Component(
		configurationPid =
	    "com.account.statement.portlet.configuration.AccountStatementConfiguration",
		immediate = true, 
		property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=accountStatement",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + accountStatementPortletKeys.ACCOUNTSTATEMENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, 
		service = Portlet.class)
public class accountStatementPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		renderRequest.setAttribute(AccountStatementConfiguration.class.getName(),
					accountStatementConfiguration);
		
		super.doView(renderRequest, renderResponse);
	}
	public String getEmail(Map labels) {
		return (String) labels.get(accountStatementConfiguration.email());
	}
	public String getSms(Map labels) {
		return (String) labels.get(accountStatementConfiguration.sms());
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
            accountStatementConfiguration = ConfigurableUtil.createConfigurable(
                    AccountStatementConfiguration.class, properties);
    }
	
	private volatile AccountStatementConfiguration accountStatementConfiguration;
}