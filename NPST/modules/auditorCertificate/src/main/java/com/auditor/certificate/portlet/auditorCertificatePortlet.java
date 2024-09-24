package com.auditor.certificate.portlet;

import com.auditor.certificate.constants.auditorCertificatePortletKeys;
import com.auditor.certificate.portlet.configuration.AuditorCertificateConfiguration;
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

/**
 * @author abhis
 */
@Component(
			configurationPid =
			"com.auditor.certificate.portlet.configuration.AuditorCertificateConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=auditorCertificate",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + auditorCertificatePortletKeys.AUDITORCERTIFICATE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.requires-namespaced-parameters=false"
	},
	service = Portlet.class
)
public class auditorCertificatePortlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		renderRequest.setAttribute(AuditorCertificateConfiguration.class.getName(),
				auditorCertificateConfiguration);
		super.doView(renderRequest, renderResponse);
		
	}
	
	public String getEmail(Map labels) {
		return (String) labels.get(auditorCertificateConfiguration.email());
	}
	public String getSms(Map labels) {
		return (String) labels.get(auditorCertificateConfiguration.sms());
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		auditorCertificateConfiguration = ConfigurableUtil.createConfigurable(
				AuditorCertificateConfiguration.class, properties);
    }
	
	private volatile AuditorCertificateConfiguration auditorCertificateConfiguration;
}