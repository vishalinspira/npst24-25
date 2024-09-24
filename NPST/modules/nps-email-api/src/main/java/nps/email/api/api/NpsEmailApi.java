package nps.email.api.api;

public interface NpsEmailApi {
	public void sendEmail(String from, String to, String subject, String body);

    public String sendOTP(String emailAddress);
    
    //public void sendRejectEmail(long companyId, String role_name, String status, String reportName);
    public void sendAlertEmail(long companyId, String role_name, String status, String reportName);

	void sendManpowerEmail(long companyId, String roleName, String pfmName, String userName, String entityName,String action);

	void sendRejectEmail(long companyId, String role_name, String status, String reportName, long reportUploadlogId);
}