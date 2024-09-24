package nps.digital.signer.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import nps.digital.signer.api.api.JwtAuthenticationApi;
import nps.digital.signer.api.model.JwtModel;
import nps.digital.signer.model.impl.JwtModelImpl;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = {}, service = {JwtAuthenticationApi.class})
public class JwtAuthenticationService implements JwtAuthenticationApi {
  public boolean verifyToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256("");
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT jwt = verifier.verify(token);
    return ((new Date()).getTime() > jwt.getExpiresAt().getTime());
  }
  
  Log _log = LogFactoryUtil.getLog(JwtAuthenticationService.class);
  
  private JwtModel jwtModel;
  
  private static final ClientBuilder clientBuilder = ClientBuilder.newBuilder();
  
  static {
    clientBuilder.connectTimeout(600L, TimeUnit.SECONDS);
    clientBuilder.readTimeout(600L, TimeUnit.SECONDS);
    TrustManager[] trustAllCerts = { new X509TrustManager() {
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }
          
          public void checkClientTrusted(X509Certificate[] certs, String authType) {}
          
          public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        } };
    try {
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, trustAllCerts, new SecureRandom());
      clientBuilder.sslContext(sc);
    } catch (Exception exception) {}
  }
  
  public JwtModel getAuthToken() {
    this.jwtModel = (JwtModel)new JwtModelImpl();
    JSONObject userCredential = JSONFactoryUtil.createJSONObject();
    userCredential.put("UserName", "admin");
    userCredential.put("Password", "*88taxpro");
    Client client = clientBuilder.build();
    String url = "https://dsc.npstrust.net/api/AuthTokenV1/AuthToken";
    Response response = client.target(url).request(new String[] { "application/json" }).header("accept", "*/*").header("Content-type", "application/json").post(Entity.json(userCredential.toString()));
    String output = (String)response.readEntity(String.class);
    try {
      JSONObject outputJSON = JSONFactoryUtil.createJSONObject(output);
      if (outputJSON.getBoolean("IsSuccess")) {
        this._log.info("output" + output);
        this.jwtModel.setIsSuccess(outputJSON.getBoolean("IsSuccess"));
        this.jwtModel.setTxnOutcome(outputJSON.getString("TxnOutcome"));
        this._log.info("jwtModel" + JSONFactoryUtil.looseSerialize(this.jwtModel));
        return this.jwtModel;
      } 
    } catch (JSONException e) {
      this._log.error((Throwable)e);
    } 
    return null;
  }
}
