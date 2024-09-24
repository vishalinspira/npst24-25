package nps.email.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;

import nps.email.api.api.ClosingBalanceEmailApi;
import nps.email.service.client.RestClient;

@Component(immediate = true, property = {

}, service = ClosingBalanceEmailApi.class)

public class ClosingBalanceEmailServiceImpl extends RestClient implements ClosingBalanceEmailApi {

	@Override
	public void sendEmail(String from, String to, String subject, String body) {
		try {

			MailMessage mailMessage = new MailMessage();

			try {

				mailMessage.setFrom(new InternetAddress(from));
				mailMessage.setTo(new InternetAddress(to));

				mailMessage.setSubject(subject);
				mailMessage.setBody(body);
				mailMessage.setHTMLFormat(true);
				MailServiceUtil.sendEmail(mailMessage);
				_log.debug("Mail sent to user at ::=> " + to);

			} catch (AddressException e1) {
				_log.error("Mail sent to 'add' 'lp' User failed ::", e1);
			}
		} catch (Exception e) {
			_log.error("Email notification could not be sent", e);
		}
		
	}

	@Override
	public String sendOTP(String emailAddress) {
		String body = "<p>Hi,</p>" 
				+ "<p>Form submited successfully</p>" 
				+ "<p>Regards,<br/>Deepak</p>";
		String subject = "Closing balance confirmation form submitted";
		sendEmail("deepak@creanttechnologies.com", emailAddress, subject, body);

		return body;
	}
	
	private static final Log _log = LogFactoryUtil.getLog(ClosingBalanceEmailServiceImpl.class);

}
