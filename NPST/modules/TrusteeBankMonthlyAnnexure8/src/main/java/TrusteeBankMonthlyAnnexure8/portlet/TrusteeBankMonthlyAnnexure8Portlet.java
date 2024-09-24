package TrusteeBankMonthlyAnnexure8.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.trusteebank.annex8.configuration.IClosingBalConfiguration;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import TrusteeBankMonthlyAnnexure8.constants.TrusteeBankMonthlyAnnexure8PortletKeys;

/**
 * @author deepak
 */
@Component(
	configurationPid = "com.trusteebank.annex8.configuration.IClosingBalConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=TrusteeBankMonthlyAnnexure8",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TrusteeBankMonthlyAnnexure8PortletKeys.TRUSTEEBANKMONTHLYANNEXURE8,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TrusteeBankMonthlyAnnexure8Portlet extends MVCPortlet {
	
	private static Log _log = LogFactoryUtil.getLog(TrusteeBankMonthlyAnnexure8Portlet.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		_log.info("Inside doview");
		
		renderRequest.setAttribute(
				IClosingBalConfiguration.class.getName(),
				closingBalConfiguration);
		
		super.doView(renderRequest, renderResponse);
	}
	
	public String getEmail(Map labels) {
		return (String) labels.get(closingBalConfiguration.email());
	}
	
	public String getSms(Map labels) {
		return (String) labels.get(closingBalConfiguration.sms());
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		closingBalConfiguration = ConfigurableUtil.createConfigurable(
				IClosingBalConfiguration.class, properties);
    }
	
	private volatile IClosingBalConfiguration closingBalConfiguration;

}