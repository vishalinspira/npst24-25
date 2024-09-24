package com.nps.dsc.rest.application;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JSONRequired;

import nps.digital.signer.api.api.JwtAuthenticationApi;
import nps.digital.signer.api.api.NpsDigitalSignerApi;
import nps.digital.signer.api.model.JwtModel;

/**
 * @author ASUS
 */
@Component(property = { JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/dsc",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Greetings.Rest" }, service = Application.class)
public class DscRestControllerApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@GET
	@Produces("text/plain")
	public String working() {
		return "It works!";
	}

	@GET
	@Path("/morning")
	@Produces("text/plain")
	public String hello() {
		return "Good morning!";
	}

	@POST
	@Path("/morning/{name}")
	@Produces("text/plain")
	public String morning(@PathParam("name") String name, @FormParam ("drink") String drink) {

		String greeting = "Good Morning " + name;

		if (drink != null) {
			greeting += ". Would you like some " + drink + "?";
		}

		return greeting;
	}

	@GET
	@Path("/getAuthToken")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAuthToken() {
		JwtModel jwtModel = jwtAuthenticationApi.getAuthToken();
		String json = JSONFactoryUtil.looseSerialize(jwtModel);
		return json;
	}

	@POST
	@Path("/signPDF")
	@Produces(MediaType.APPLICATION_JSON)
	public String signPDF(@FormParam("fileEntryId") String fileEntryIdString,
			@FormParam("sDHubConnectionIdFromBrowser") String sDHubConnectionIdFromBrowser,
			@FormParam("certificateFromBrowser") String certificateFromBrowser, 
			@FormParam ("certificateJSON") String certificateJSON) {
		
		_log.info("fileEntryIdString"+fileEntryIdString+" sDHubConnectionIdFromBrowser " + sDHubConnectionIdFromBrowser + " certificateFromBrowser "+certificateFromBrowser);
		Long fileEntryId = Long.parseLong(fileEntryIdString);
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(fileEntryId);
		// DLFileVersionLocalServiceUtil.getFileVersion(fileEntryId,
		// dlFileEntry.getVersion());
		try {

			InputStream fileInputStream = DLFileEntryLocalServiceUtil.getFileAsStream(fileEntryId,
					dlFileEntry.getVersion());

			byte[] sourceBytes = FileUtil.getBytes(fileInputStream);
			String encodedString = Base64.getEncoder().encodeToString(sourceBytes);

			npsDigitalSignerApi.SignPdfWithSettingName(encodedString, sDHubConnectionIdFromBrowser, certificateFromBrowser, certificateJSON, fileEntryId);

		} catch (PortalException e) {
			 _log.error(e);
		} catch (IOException e) {
			 _log.error(e);
		}
		
		return "{\"Status\":true}";
	}

	@Reference
	JwtAuthenticationApi jwtAuthenticationApi;

	@Reference
	NpsDigitalSignerApi npsDigitalSignerApi;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}