package loginPortlet.portlet;
import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.DynamicActionRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
           "javax.portlet.name=" + LoginPortletKeys.LOGIN,
           "javax.portlet.name=" + LoginPortletKeys.FAST_LOGIN,
           "mvc.command.name=/login/login",
           "service.ranking:Integer=100"
        },
        service = MVCActionCommand.class)
public class LoginPortletStruts extends BaseMVCActionCommand {
	private static final Log _log=LogFactoryUtil.getLog(LoginPortletStruts.class);

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		DynamicActionRequest dynamicActionRequest = new DynamicActionRequest(actionRequest);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long companyId = themeDisplay.getCompanyId();
		String emailAddress = ParamUtil.getString(actionRequest, "login");
		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));
		String auth=AuthTokenUtil.getToken(httpReq);
		_log.info(auth);
		String uuid = "aW1d!";
		try {
			User user = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, emailAddress);
			uuid =  user.getUserUuid();
		} catch (Exception e) {
			 _log.error(e);
		}
		 
			try {
				CaptchaUtil.check(actionRequest);
			} catch (CaptchaException e) {
				
				SessionErrors.add(actionRequest, e.getClass(), e);
                _log.error("CAPTCHA verification failed.");
                throw new CaptchaException("CAPTCHA verification failed.");
			}
	       dynamicActionRequest.setParameter("password", getEncryptedPassword(actionRequest, ParamUtil.getString(actionRequest, "password")).replace(uuid, "").replace(auth, ""));
	        mvcActionCommand.processAction(dynamicActionRequest, actionResponse);
			//mvcActionCommand.processAction(actionRequest, actionResponse);
		
	}

	private String getEncryptedPassword(ActionRequest actionRequest, String data) {
		String password = null; 
		_log.info("data:::::" + data);
		String pk="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALgQqopE+aJ9tm7ELfrnmUdC+kGNX17/MnBMsID8mF+wvGvG/Vsa0/VcHpiKqwYue6Z0Cq1DHYnJn9Bgs5vYpbh58tj6W5aTqw+Fw0/Oe+vin4NbE1O78VYHGMvexxb4bS5VOmsF8jkK9LuOlHxLZwVXEddKDT0f+atjzHdzIYqlAgMBAAECgYEAr1VQ3n1tvlc53LGLiyHqZHNsp9zfKEK37TlgOL5k92IHWjMnTA12MpcZnf0ZhL3qk1iegwUXshUZ4wj6jk4FLOLQWf4jeM7omLdPPy1ejv3l/tYn6kDab8HanbbjfgtWd5bzbpispLJtEoZ5vieaveRcvIR0RbaBVbewXbtk3lECQQDmfhdoUVwPA505/y+gYTEyfWu2Q+oec1qIqFv2QaDAdmmVuLiBa4FXJVtrl9Y8vZdmaurBwEEikUEAnzAFLWDvAkEAzG9GNeHqIly3ZtjyeqZMzZrpv3Qg9rAwbMN4akvNQdYdYi2cGDAvyrOE/u6nhyuUjl8bxJP//0geC0xKg6TlqwJBAJ8zJFPHLkCojGZMnca2mtZ2znjbu89AeZ2+O5aBidilcRQavEWXzYQ1p0lPXIgjo3j4DCG6+sI3daBKU06gIz0CQQCAqjv3J6NzUwU+3qXl2YqXELuy+oTQnd0sZu3/gIvDtt2xb+FBO/qoScoXpN6dbSlHXcadN0S0R5BZLf/r0LmXAkBiXBv5jiW68GcHWu/vjN4TZ4XZyJO0wUlXIdYzwgiYRO3PwqihJqIW08xHrWIicHbT5AZa/y+PsZTiRdZydjUt";
		try {
			password = decrypt(data,pk);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		_log.info("password::::::::::::::::::" + password);
		return password;
	}
	
	
	 public static PrivateKey getPrivateKey(String base64PrivateKey){
	        PrivateKey privateKey = null;
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
	        KeyFactory keyFactory = null;
	        try {
	            keyFactory = KeyFactory.getInstance("RSA");
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        try {
	            privateKey = keyFactory.generatePrivate(keySpec);
	        } catch (InvalidKeySpecException e) {
	            e.printStackTrace();
	        }
	        _log.info("privateKey:::" + privateKey);
	        return privateKey;
	    }
	 
	 
	 public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
	        //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        //Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        return new String(cipher.doFinal(data));
	    }
	 
	 
	 public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
	        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
	    }
	 
	 @Reference(
		        target = "(component.name=com.liferay.login.web.internal.portlet.action.LoginMVCActionCommand)")
		    protected MVCActionCommand mvcActionCommand;
}
