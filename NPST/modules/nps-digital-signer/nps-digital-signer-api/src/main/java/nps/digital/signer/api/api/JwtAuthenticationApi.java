package nps.digital.signer.api.api;

import nps.digital.signer.api.model.JwtModel;

public interface JwtAuthenticationApi {
	public boolean verifyToken(String token);
	public JwtModel getAuthToken();
}
