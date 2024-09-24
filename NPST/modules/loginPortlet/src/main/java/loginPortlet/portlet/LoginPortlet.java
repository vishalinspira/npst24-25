package loginPortlet.portlet;

import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.email.api.api.EncryptionServiceApi;

/**
 * @author crerant
 */
@Component(
		immediate = true,
		property = { 
            "javax.portlet.name="+LoginPortletKeys.LOGIN,
    		"mvc.command.name=/"
    		}, 
    service = MVCRenderCommand.class)
public class LoginPortlet implements MVCRenderCommand{


	Log _log=LogFactoryUtil.getLog(getClass());
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		String publicKey = encryptionServiceApi.getPK();
		renderRequest.setAttribute("publicKey", publicKey);
		
		return  mvcRenderCommand.render(renderRequest, renderResponse);
	}
	
	@Reference(target = "(component.name=com.liferay.login.web.internal.portlet.LoginPortlet)")
		  protected MVCRenderCommand mvcRenderCommand;
	
	@Reference
	EncryptionServiceApi encryptionServiceApi;
	
	//@Reference(target = "(&amp;(mvc.command.name=/login/login)(javax.portlet.name=com_liferay_login_web_portlet_FastLoginPortlet) "
	//		+ "(javax.portlet.name=com_liferay_login_web_portlet_LoginPortlet)(component.name=login.action.module.actioncommand.CustomLogInActionCommand))")
	
	
}
    