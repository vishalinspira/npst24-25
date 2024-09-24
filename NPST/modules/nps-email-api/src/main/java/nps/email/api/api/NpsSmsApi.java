package nps.email.api.api;

public interface NpsSmsApi {
	
	    public void sendFormSubmitSuccessfulSMS(String mobileNo, String name, String message, String contentId);
	    public void sendSMS1(String mobileNo, String name,String role_name,long reportUploadlogId);
	    public void sendSMS2(String mobileNo, String name,String role_name,long reportUploadlogId);
	    public void sendRejectAndReviewSMS(long companyId, String role_name,String status, String name,long reportUploadlogId);
}
