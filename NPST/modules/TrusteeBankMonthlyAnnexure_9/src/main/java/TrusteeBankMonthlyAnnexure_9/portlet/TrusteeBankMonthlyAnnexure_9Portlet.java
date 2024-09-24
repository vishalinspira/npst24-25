package TrusteeBankMonthlyAnnexure_9.portlet;

import TrusteeBankMonthlyAnnexure_9.constants.TrusteeBankMonthlyAnnexure_9PortletKeys;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.trusteebank.annex9.configuration.IPerformanceRepConfiguration;
import com.trusteebank.annex9.resource.PerformanceRepResource;

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
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.display-name=TrusteeBankMonthlyAnnexure_9",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TrusteeBankMonthlyAnnexure_9PortletKeys.TRUSTEEBANKMONTHLYANNEXURE_9,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TrusteeBankMonthlyAnnexure_9Portlet extends MVCPortlet {
	
	private static Log _log = LogFactoryUtil.getLog(TrusteeBankMonthlyAnnexure_9Portlet.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		_log.info("Inside doview");
		
		renderRequest.setAttribute(
				IPerformanceRepConfiguration.class.getName(),
				performanceRepConfiguration);
		
		super.doView(renderRequest, renderResponse);
		
	}
	
	public String getEmail(Map labels) {
		return (String) labels.get(performanceRepConfiguration.email());
	}
	
	public String getSms(Map labels) {
		return (String) labels.get(performanceRepConfiguration.sms());
	}
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		performanceRepConfiguration = ConfigurableUtil.createConfigurable(
				IPerformanceRepConfiguration.class, properties);
    }
	
	private volatile IPerformanceRepConfiguration performanceRepConfiguration;
	
}