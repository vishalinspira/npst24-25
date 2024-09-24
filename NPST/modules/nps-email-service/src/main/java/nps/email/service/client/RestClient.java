package nps.email.service.client;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nps.email.api.props.PropValues;
import nps.email.service.constants.ErrorAPIKeys;

@SuppressWarnings("deprecation")
public class RestClient {

    private static final ClientBuilder clientBuilder;

    static {
        clientBuilder = ClientBuilder.newBuilder();
        clientBuilder.connectTimeout(600, TimeUnit.SECONDS);
        clientBuilder.readTimeout(600, TimeUnit.SECONDS);
        SSLContext sslContext;
        try {
        	  sslContext = SSLContext.getInstance("SSL");
        	  sslContext.init(null, new TrustManager[]{new X509TrustManager() {
        	      @Override
        	      public void checkClientTrusted(final X509Certificate[] x509Certificates, final String s) throws CertificateException {
        	          // no-op
        	      }

        	      @Override
        	      public void checkServerTrusted(final X509Certificate[] x509Certificates, final String s) throws CertificateException {
        	          // no-op
        	      }

        	      @Override
        	      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        	          return null;
        	      }

        	  }}, new java.security.SecureRandom());
        	} catch (final NoSuchAlgorithmException | KeyManagementException e) {
        	  throw new IllegalStateException(e);
        	}
        clientBuilder.hostnameVerifier((s, session) -> true);
        clientBuilder.sslContext(sslContext);
    }

    public String execute(String url, String body) {

        String output;

        final String name = PropValues.ENDPOINT_GIS_AUTH_USERNAME;
        final String password =PropValues.ENDPOINT_GIS_AUTH_PASSWORD;
        final String authString = name+":"+password;

        String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

        Client client = clientBuilder.build();

        Response response = client
                .target(url)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + authStringEnc)
                .post(Entity.json(body));

        output = response.readEntity(String.class);
        if (!(response.getStatus() >= 200 && response.getStatus() < 400)) {
            _log.error(" API Met error ::==> " + response.getStatus() + "----" + output);
        }

        return output;
    }
    public String execute2(String url, String body) throws WebApplicationException{

        String output;

        final String name = ErrorAPIKeys.NAME;//"sasdemo";
        final String password = ErrorAPIKeys.PASSWORD;//"sasdemo@222";
        final String authString = name+":"+password;

        String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

        Client client = clientBuilder.build();
_log.debug("xml file body : "+body);
        Response response = client
                .target(url)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + authStringEnc)
                .post(Entity.xml(body));
_log.info("API response "+response);
        output = response.readEntity(String.class);
        //_log.error(" API Met error ::==> " + response.getStatus() + "----" + output);
        if (response.getStatus() >= 400 ) {
        	_log.error(" API Met error ::==> " + response.getStatus() + "----" + output);
        	throw new WebApplicationException(response.getStatus());
        }

        return output;
    }
    public String sendSms(String url) {

        _log.info("URL----"+url);
        String output;
        Client client = clientBuilder.build();

        Response response = client
                .target(url)
                .request().method("GET");

        output = response.readEntity(String.class);
        _log.info(" API Met error ::==> " + response.getStatus() + "----" + output);
        if (!(response.getStatus() >= 200 && response.getStatus() < 400)) {
            return output;
        } else {
            _log.debug(" API Met error ::==> " + response.getStatus() + "----" + output);
        }

        return output;
    }

    private final static Log _log = LogFactoryUtil.getLog(RestClient.class);

}
