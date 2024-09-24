package com.nps.dsc.rest.application;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

@Component(property = { JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/user",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=User.Rest" }, service = Application.class)
public class UserRestControllerApplication extends Application {
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}
	
	@GET
	@Produces("text/plain")
	public String working() {
		return "It works!";
	}
	
	@GET
	@Path("/email/{email}/compid/{compid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String emailVrify(@PathParam("email") String email, @PathParam("compid") String compid) {

		JSONObject greeting = JSONFactoryUtil.createJSONObject();
		Long companyId = Long.parseLong(compid);
		User user =  UserLocalServiceUtil.fetchUserByEmailAddress(companyId, email);
		if(Validator.isNotNull(user)) {
			greeting.put("emailReged", true);
		}else {
			greeting.put("emailReged", false);
		}
		return greeting.toString();
	}
}
