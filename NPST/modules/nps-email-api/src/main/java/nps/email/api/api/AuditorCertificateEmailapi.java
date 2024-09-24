package nps.email.api.api;

public interface AuditorCertificateEmailapi {
	public void sendEmail(String from, String to, String subject, String body);

    public String sendOTP(String emailAddress);
}
