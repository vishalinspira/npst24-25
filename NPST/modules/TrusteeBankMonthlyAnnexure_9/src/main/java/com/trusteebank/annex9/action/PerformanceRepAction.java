package com.trusteebank.annex9.action;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.trusteebank.annex9.configuration.IPerformanceRepConfiguration;

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

import TrusteeBankMonthlyAnnexure_9.constants.TrusteeBankMonthlyAnnexure_9PortletKeys;

@Component(
	    configurationPid = "com.trusteebank.annex9.configuration.IPerformanceRepConfiguration",
	    configurationPolicy = ConfigurationPolicy.OPTIONAL,
	    immediate = true,
	    property = {
	    		"javax.portlet.name=" + TrusteeBankMonthlyAnnexure_9PortletKeys.TRUSTEEBANKMONTHLYANNEXURE_9,
	    },
	    service = ConfigurationAction.class
	)


public class PerformanceRepAction extends DefaultConfigurationAction{
	
	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		String email = ParamUtil.getString(actionRequest, "email");
		String sms = ParamUtil.getString(actionRequest, "sms");
		setPreference(actionRequest, "email", email);
		setPreference(actionRequest, "sms", sms);
		
		super.processAction(portletConfig, actionRequest, actionResponse);
	}
	
	@Override
	public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		
		httpServletRequest.setAttribute(
				IPerformanceRepConfiguration.class.getName(),
				performanceRepConfiguration);
		
		
		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		performanceRepConfiguration = ConfigurableUtil.createConfigurable(
				IPerformanceRepConfiguration.class, properties);
    }
	
	private volatile IPerformanceRepConfiguration performanceRepConfiguration;

}
