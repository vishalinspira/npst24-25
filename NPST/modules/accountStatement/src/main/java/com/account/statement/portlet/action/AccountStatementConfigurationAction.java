package com.account.statement.portlet.action;

import com.account.statement.constants.accountStatementPortletKeys;
import com.account.statement.portlet.configuration.AccountStatementConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

@Component(
	    configurationPid = "com.account.statement.portlet.configuration.AccountStatementConfiguration",
	    configurationPolicy = ConfigurationPolicy.OPTIONAL,
	    immediate = true,
	    property = {
	        "javax.portlet.name=" + accountStatementPortletKeys.ACCOUNTSTATEMENT
	    },
	    service = ConfigurationAction.class
	)
public class AccountStatementConfigurationAction extends DefaultConfigurationAction {
	
	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		String email=ParamUtil.getString(actionRequest, "email");
		String sms = ParamUtil.getString(actionRequest, "sms");
		setPreference(actionRequest, "email", email);
		setPreference(actionRequest, "sms", sms);
		
		super.processAction(portletConfig, actionRequest, actionResponse);
	}
	
	@Override
	public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		
		httpServletRequest.setAttribute(AccountStatementConfiguration.class.getName(),
				accountStatementConfiguration);
		
		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		
            accountStatementConfiguration = ConfigurableUtil.createConfigurable(
                    AccountStatementConfiguration.class, properties);
	}

	private volatile AccountStatementConfiguration accountStatementConfiguration;
}
