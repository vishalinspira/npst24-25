package TrusteeBankQuarterlyAnnexure_11.portlet;

import TrusteeBankQuarterlyAnnexure_11.constants.TrusteeBankQuarterlyAnnexure_11PortletKeys;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.trusteebank.annex11.configuration.IAuditorCertificate;
import com.trusteebank.annex11.resource.QuarterlyAuditorResource;

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
 * @author deepak
 */
@Component(
	configurationPid = "com.trusteebank.annex11.configuration.IAuditorCertificate",
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=TrusteeBankQuarterlyAnnexure_11",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TrusteeBankQuarterlyAnnexure_11PortletKeys.TRUSTEEBANKQUARTERLYANNEXURE_11,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TrusteeBankQuarterlyAnnexure_11Portlet extends MVCPortlet {
	
	private static Log _log = LogFactoryUtil.getLog(TrusteeBankQuarterlyAnnexure_11Portlet.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		_log.info("Inside view");
		
		renderRequest.setAttribute(
				IAuditorCertificate.class.getName(),
				auditorCertificate);
		
		super.doView(renderRequest, renderResponse);
	}
	
	public String getEmail(Map labels) {
		return (String) labels.get(auditorCertificate.email());
	}
	
	public String getSms(Map labels) {
		return (String) labels.get(auditorCertificate.sms());
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		auditorCertificate = ConfigurableUtil.createConfigurable(
				IAuditorCertificate.class, properties);
    }
	
	private volatile IAuditorCertificate auditorCertificate;
	
}