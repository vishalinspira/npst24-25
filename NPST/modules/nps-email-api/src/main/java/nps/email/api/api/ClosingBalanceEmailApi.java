package nps.email.api.api;

public interface ClosingBalanceEmailApi {
	
	public void sendEmail(String from, String to, String subject, String body);

    public String sendOTP(String emailAddress);
	
}