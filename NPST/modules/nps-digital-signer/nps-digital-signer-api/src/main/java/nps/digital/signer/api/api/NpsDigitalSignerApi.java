package nps.digital.signer.api.api;

/**
 * @author ASUS
 */
public interface NpsDigitalSignerApi {
	public void SignPdfWithSettingName(String pdfData, String sDHubConnectionIdFromBrowser,
			String certificateFromBrowser,String certificateJSON,Long fileEntryId);
	
	public void SignDLFileWithSettingName(Long fileEntryId, String sDHubConnectionIdFromBrowser, 
			String certificateFromBrowser,String certificateJSON);
}