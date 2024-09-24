package nps.email.service.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.ws.rs.core.UriBuilder;

import org.osgi.service.component.annotations.Component;


import nps.email.api.api.QuaterEndedSmsApi;
import nps.email.api.props.PropValues;
import nps.email.service.client.RestClient;

@Component(immediate = true, property = {

}, service = QuaterEndedSmsApi.class)

public class QuaterEndedSmsServiceImpl extends RestClient implements QuaterEndedSmsApi{

	@Override
	public void sendFormSubmitSuccessfulSMS(String mobileNo, String name) {
		
		 String message = PropValues.BODY_SMS1;
		 message = message.replace("{{NAME}}", name);
	     String contentId = PropValues.BODY_SMS1_CONTENTID;

	        String url = UriBuilder.fromPath(PropValues.ENDPOINT_SMS_GATEWAY)
	        		 .queryParam("username", PropValues.SMS_GATEWAY_USERNAME)
	        		 .queryParam("password", PropValues.SMS_GATEWAY_PASSWORD)
	                 .queryParam("mobile", mobileNo)
	                 .queryParam("message", message)
	        		 .queryParam("senderid", PropValues.SMS_GATEWAY_SENDERID)
	                 .queryParam("peid", PropValues.SMS_GATEWAY_PEID)
	                 .queryParam("contentid", contentId).build().toString();
	         _log.debug("sendFormSubmitSuccessfulSMS url =>" + url);
	         sendSms(url);
		
	}

	 private static final Log _log = LogFactoryUtil.getLog(NpsSmsServiceImpl.class);

	}


