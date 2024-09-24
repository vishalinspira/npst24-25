package com.quarter.ended.portlet;

import com.quarter.ended.constants.quarterEndedPortletKeys;
import com.quarter.ended.portlet.configuration.QuaterEndedConfiguration;

import java.io.IOException;
import java.util.Map;


import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;



/**
 * @author abhis
 */
@Component(
		configurationPid =
	    "com.quarter.ended.portlet.configuration.QuaterEndedConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=quarterEnded",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + quarterEndedPortletKeys.QUARTERENDED,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class quarterEndedPortlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		renderRequest.setAttribute(QuaterEndedConfiguration.class.getName(),
				quaterEndedConfiguration);
		
		super.doView(renderRequest, renderResponse);
	}
	public String getEmail(Map labels) {
		return (String) labels.get(quaterEndedConfiguration.email());
	}
	public String getSms(Map labels) {
		return (String) labels.get(quaterEndedConfiguration.sms());
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		quaterEndedConfiguration = ConfigurableUtil.createConfigurable(
            		QuaterEndedConfiguration.class, properties);
    }
	
	private volatile QuaterEndedConfiguration quaterEndedConfiguration;
}