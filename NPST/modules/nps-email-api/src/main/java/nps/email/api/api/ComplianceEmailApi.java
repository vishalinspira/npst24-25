package nps.email.api.api;

public interface ComplianceEmailApi {
	
	public void sendEmail(String from, String to, String subject, String body);

    public String sendOTP(String emailAddress);

}
