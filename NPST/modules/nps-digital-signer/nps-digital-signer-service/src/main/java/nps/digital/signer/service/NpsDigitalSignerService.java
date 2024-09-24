package nps.digital.signer.service;

import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import nps.digital.signer.api.api.JwtAuthenticationApi;
import nps.digital.signer.api.api.NpsDigitalSignerApi;
import nps.digital.signer.api.model.JwtModel;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = {}, service = {NpsDigitalSignerApi.class})
public class NpsDigitalSignerService implements NpsDigitalSignerApi {
  @Reference
  JwtAuthenticationApi jwtAuthenticationApi;
  
  Log _log = LogFactoryUtil.getLog(NpsDigitalSignerService.class);
  
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
  
  public void SignPdfWithSettingName(String pdfData, String sDHubConnectionIdFromBrowser, String certificateFromBrowser, String certificateJSON, Long fileEntryId) {
    DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(fileEntryId.longValue());
    ReportUploadFileLog reportUploadFileLog = ReportUploadFileLogLocalServiceUtil.findByFileEntryId(fileEntryId.longValue());
    reportUploadFileLog.setSignature(certificateJSON);
    if(!dlFileEntry.getExtension().equalsIgnoreCase("pdf")) {
    	ReportUploadFileLogLocalServiceUtil.updateReportUploadFileLog(reportUploadFileLog);
    }
    
    JSONObject payload = JSONFactoryUtil.createJSONObject();
    JwtModel jwtModel = this.jwtAuthenticationApi.getAuthToken();
    String tokenPin = jwtModel.getTxnOutcome();
    String signSettingName = "New";
    
    _log.info("File Entry Id : "+fileEntryId +"  report upload log id : "+ reportUploadFileLog.getReportUploadLogId());
    try {
      JSONObject certificateJSONObject = JSONFactoryUtil.createJSONObject(certificateJSON);
      certificateFromBrowser = certificateJSONObject.getString("Cert");
    } catch (JSONException e) {
      this._log.error(e);
    } 
    
    _log.info("PDF DATA:  "+pdfData);
    payload.put("PdfData", pdfData);
    payload.put("SignSettingName", signSettingName);
    payload.put("SDHubConnectionIdFromBrowser", sDHubConnectionIdFromBrowser);
    payload.put("CertificateFromBrowser", certificateFromBrowser);
    String url = "https://dsc.npstrust.net/api/SignPdfV1/SignPdfWithSettingName";
    Client client = clientBuilder.build();
    this._log.info("certificateJSON" + certificateJSON);
    this._log.debug("payload.toString()" + payload.getString("CertificateFromBrowser"));
    this._log.debug("AuthorizationBasic " + tokenPin);
    Response response = client.target(url).request(new String[] { "application/json" }).header("accept", "*/*").header("Content-type", "application/json-patch+json").header("Authorization", "bearer " + tokenPin).post(Entity.json(payload.toString()));
    String responseString = (String)response.readEntity(String.class);
    this._log.info("responseString and file entryid " +fileEntryId +":"+ responseString);
    if (Validator.isNotNull(responseString)) {
      try {
        JSONObject responseJson = JSONFactoryUtil.createJSONObject(responseString);
        _log.info("Is success : "+responseJson.getBoolean("IsSuccess") +" file entry id  : "+fileEntryId);
        if (responseJson.getBoolean("IsSuccess")) {
          String signedFile = responseJson.getString("SignedFile");
          _log.info(" signed pdf file : "+signedFile);
          byte[] signedFileByteArray = com.liferay.portal.kernel.util.Base64.decode(signedFile);
          ServiceContext serviceContext = new ServiceContext();
          DLVersionNumberIncrease dlVersionNumberIncrease = DLVersionNumberIncrease.AUTOMATIC;
          DLAppLocalServiceUtil.updateFileEntry(dlFileEntry.getUserId(), fileEntryId.longValue(), dlFileEntry.getFileName(), dlFileEntry.getMimeType(), dlFileEntry
              .getTitle(), "Document Signed", "Document Signed", dlVersionNumberIncrease, signedFileByteArray, serviceContext);
          ReportUploadFileLogLocalServiceUtil.updateReportUploadFileLog(reportUploadFileLog);
        } 
//        else {
//          this._log.info("reponse IsSuccess is false");
//        } 
      } catch (JSONException e) {
        this._log.error((Throwable)e);
      } catch (PortalException e) {
        this._log.error((Throwable)e);
      } 
    } else {
      this._log.info("response is bank");
    } 
    this._log.info("response.getStatusInfo() " + response.getStatusInfo().toString() + " response.getStatus() " + response.getStatus());
  }
  
  public void SignDLFileWithSettingName(Long fileEntryId, String sDHubConnectionIdFromBrowser, String certificateFromBrowser, String certificateJSON) {
    this._log.info("fileEntryIdString" + fileEntryId + " sDHubConnectionIdFromBrowser " + sDHubConnectionIdFromBrowser + " certificateFromBrowser " + certificateFromBrowser);
    DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(fileEntryId.longValue());
    try {
      InputStream fileInputStream = DLFileEntryLocalServiceUtil.getFileAsStream(fileEntryId.longValue(), dlFileEntry.getVersion());
      byte[] sourceBytes = FileUtil.getBytes(fileInputStream);
      String encodedString = Base64.getEncoder().encodeToString(sourceBytes);
      SignPdfWithSettingName(encodedString, sDHubConnectionIdFromBrowser, certificateFromBrowser, certificateJSON, fileEntryId);
    } catch (PortalException e) {
      this._log.error((Throwable)e);
    } catch (IOException e) {
      this._log.error(e);
    } 
  }
}
