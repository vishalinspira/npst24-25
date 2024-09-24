package testpasswordpolicy.portlet;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
@Component(
		immediate = true,
		property = {
			"key=login.events.post"
		},
		service = LifecycleAction.class
	)
public class Postlogin implements LifecycleAction {

	
	
	
	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {
		 _log.info("login command called");
		 HttpServletRequest httpReq = lifecycleEvent.getRequest();
			HttpServletResponse httpResp = lifecycleEvent.getResponse();
			StringBuffer url = httpReq.getRequestURL();
			_log.info("http request URL: "+url);

        // Fetch user
        User user = (User) httpReq.getSession().getAttribute(WebKeys.USER);
        String urls="";
        if (user != null && isPasswordChangeRequired(user)) {
            // Redirect to change password page
            try {
            	 urls="/web/guest/manage?p_p_id=com_liferay_my_account_web_portlet_MyAccountPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_my_account_web_portlet_MyAccountPortlet_p_u_i_d=195604&_com_liferay_my_account_web_portlet_MyAccountPortlet_mvcRenderCommandName=%2Fusers_admin%2Fedit_user&_com_liferay_my_account_web_portlet_MyAccountPortlet_screenNavigationCategoryKey=general&_com_liferay_my_account_web_portlet_MyAccountPortlet_screenNavigationEntryKey=password&p_p_auth=tTXrcL7K";
				_log.info("urls:: "+urls);
            	httpResp.sendRedirect(urls);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				_log.error(e);
			}
        } else {
            // Proceed with the normal behavior
        	try {
        		urls=httpReq.getRequestURL()+"";
        		_log.info("urls:: "+urls);
				httpResp.sendRedirect(urls);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				_log.error(e);
			}
        }
    }
	
	
	
	private boolean isPasswordChangeRequired(User user) {
		try {
       
        PasswordPolicy passwordPolicy=user.getPasswordPolicy();
        long expirationTime = passwordPolicy.getMaxAge();
        Date lastPasswordResetDate = user.getPasswordModifiedDate();
        Date currentDate = new Date();
        if (Validator.isNotNull(lastPasswordResetDate)) {
            long diffInMillis = currentDate.getTime() - lastPasswordResetDate.getTime();
            long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
           long expirationDays=expirationTime / (1000 * 60 * 60 * 24);
            if (diffInDays > expirationDays) {
                _log.info("Password expired for user ID: "+user.getUserId()+"diffinDyas:: "+diffInDays  +" expirationDays:::: "+expirationDays);
                return true;
            } else {
            	_log.info("Password is still valid for user ID: "+user.getUserId()+"diffinDyas:: "+diffInDays  +" expirationDays:::: "+expirationDays);
            	return true;
            }
        } else {
        	_log.info("No password reset date found for user ID: "+user.getUserId());
        }
    
		}catch (Exception e) {
			_log.error(e);
		}
        return false;
		
    }
	
	
	
	
	
	private static Log _log=LogFactoryUtil.getLog(Postlogin.class.getName()); 
	
}

