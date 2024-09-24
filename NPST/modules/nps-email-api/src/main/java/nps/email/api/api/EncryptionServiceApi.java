package nps.email.api.api;

import com.liferay.portal.kernel.json.JSONObject;

public interface EncryptionServiceApi {
	public String encrypt(String secretMessage);
	public String decrypt(String encodedMessage);
	public String getPK();
}
