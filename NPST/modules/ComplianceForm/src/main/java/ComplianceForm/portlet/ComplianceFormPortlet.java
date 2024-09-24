package ComplianceForm.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.trusteebank.annex13.configuration.IComplianceCertificate;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import ComplianceForm.constants.ComplianceFormPortletKeys;

/**
 * @author deepak
 */
@Component(
	configurationPid = "com.trusteebank.annex13.configuration.IComplianceCertificate",	
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ComplianceForm",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ComplianceFormPortletKeys.COMPLIANCEFORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ComplianceFormPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		_log.info("Inside view");
		
		renderRequest.setAttribute(
				IComplianceCertificate.class.getName(),
				complianceCertificate);
		
		super.doView(renderRequest, renderResponse);
	}
	
	public String getEmail(Map labels) {
		return (String) labels.get(complianceCertificate.email());
	}
	
	public String getSms(Map labels) {
		return (String) labels.get(complianceCertificate.sms());
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		complianceCertificate = ConfigurableUtil.createConfigurable(
				IComplianceCertificate.class, properties);
    }
	
	private volatile IComplianceCertificate complianceCertificate;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}