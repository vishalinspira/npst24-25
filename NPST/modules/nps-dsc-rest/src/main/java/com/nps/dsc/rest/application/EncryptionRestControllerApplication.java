package com.nps.dsc.rest.application;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import nps.email.api.api.EncryptionServiceApi;


@Component(property = { JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/pk",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Encryption.Rest" }, service = Application.class)
public class EncryptionRestControllerApplication  extends Application {
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}
	
	/*
	 * commented for VAPT
	 * 
	 * @GET
	 * 
	 * @Produces("text/plain") public String working() { LOG.info("working"); return
	 * encryptionServiceApi.getPK(); //return prik; }
	 */
	
	@GET
	@Path("/morning/{companyId}/{emailAddress}")
	@Produces("text/plain")
	public String getHash(@PathParam("companyId")long companyId, @PathParam("emailAddress") String emailAddress) {
		User user = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, emailAddress);
		return user.getUserUuid();
	}
	
	Log LOG = LogFactoryUtil.getLog(getClass());
	
	String prik = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEowIBAAKCAQEAspkCc5HuDFcMmBJG4eQs4jButGQt2CB6sTU0RhQBpxyTEL7j\r\n" + 
			"Z9/7ZH+at8SaJVA5vJkBIHK0eQUPf1KdB1ViH1kD/9aWCTXGgfjr9vh0Qx9Yyskp\r\n" + 
			"Lin4wfq8sQ4ACoEWbm68/Ruksj5klvF3OVoi9YpB1SyKCGlExGMp/E0/b2NKH9LW\r\n" + 
			"lFMJFnM0o9TgKznVeCmYOSbWm8qM0r5rwk13uI7fjgdHe0pWrxqATt3dEQXz5bvZ\r\n" + 
			"Mr6PpZNzVTvprJiDNXv+H9nyGFGHntcpkwCq4pvK3m9wPRgTrktuNl8wsB+tYMrT\r\n" + 
			"wvFQL7vs8GsHTE9XpPmTh8DnQdmxAg+3CLEBdwIDAQABAoIBABEIOo2e+x6+JntS\r\n" + 
			"MuRUCFvB1qzpaN3Vqx2/Py73tXikZvoP2kwLtkPNT4n6++BXGEp+JPddOQzZiLLR\r\n" + 
			"xBvw7pyitpjtc/2I3VeDz60L4LqOAL/GE2FNq2WEbf5MoYBe8y6w7OEOg9Rj0t/6\r\n" + 
			"Qy57BB56gvC7gKZws5gvBNAE5enPWuCPB6eJf0t060i/o+P9SvuWys/PPtoA02fI\r\n" + 
			"G5alBXTnuHXyBCIyCi0XQQIaH8aCSjpgCYNHIEfWvvN40ztbFWf2rSwxJpi0XIcy\r\n" + 
			"mvf0ajla+a/l5Rz31CjTTl5JOZbCc6HQLjsNPipcx3xJDgXf8H+NCaU0rdozNObi\r\n" + 
			"tILe7fECgYEA/W3YkLKM9Poj+p7TiYNPLRw9lWdavzJh9B0livbAniV64Y2agMGq\r\n" + 
			"AwXxf20SQTD+iq5phoGerBhe2OHXdjriZLg1HQ1jC6vHfOOw3vzdkIFVVV4EabnB\r\n" + 
			"VqG2guwJJVHPW0sX4IvESqVJNhYEEGtX6a9SKKidVjKxcsQ/Y4SVmm0CgYEAtGjT\r\n" + 
			"rg+aDa6HJcyGRLsJlWZAyCPg9DeIUuN+CL3DPZoTMu+HpI5Ik0QtGGtf45xxOLE1\r\n" + 
			"KgF2QCngW3ICSC7aDP9cyYaIJyWOqS6xOIqHnI9t0d8ifB1kSn+JkLQciqw3z35R\r\n" + 
			"i55AtuntmbpZb1hTVBf/Wb2J12R5dm9PPrnJnPMCgYA0ArkxaRN4q2V65+GuFR7I\r\n" + 
			"uUUCRoXfO23S9bBgxqddTX/cjn0o85X6rNuVNUNL0cxLlePeUPfJhe7hP+hCQW95\r\n" + 
			"ED0GS1sKaCS2wtvnx6mJIKHstTDeqdq99s5ILDzsRc/ygoTR9p1AXI+b5ATNgdo3\r\n" + 
			"AV4xLfmsWRcF4LTJrcouOQKBgQCk+5xheJkcW/fx5EkBv74y5zDlFpKZI9PCYZKX\r\n" + 
			"sdpDkoxuL0jvfsL2EgnfZApr31xXSQ9iW7XlVaZ8WhWmJ1p3OmyDS0ll6128cBvX\r\n" + 
			"pjAAR9ZZcGJb+JEH4/xM3bmgQ4BCe7VdJhrua51C0X0Ka07A/7UgP0o4NQUy/vYM\r\n" + 
			"WeD7awKBgDvJPzXAJe2nV991aCXc7FviqJhMPz++Xo2DHAyvadhGKfy3/p0sDl2A\r\n" + 
			"LpU5dGu9UvNVM5+I/4z6L5zHdQW7yCASEnag/rQsY8vx6/HRM4R47HECB30RojRC\r\n" + 
			"bt1zP81nhHvaSl41Xa5QEAq8DRfaSHJ9yPuNHs/9ECHicP8mhkna\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	@Reference
	EncryptionServiceApi encryptionServiceApi;
}
