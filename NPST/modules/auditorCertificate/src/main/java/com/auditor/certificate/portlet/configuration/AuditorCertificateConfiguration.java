package com.auditor.certificate.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;


@Meta.OCD(id = "com.auditor.certificate.portlet.configuration.AuditorCertificateConfiguration")
public interface AuditorCertificateConfiguration {

	@Meta.AD(required = false)
    public String email();
	
	@Meta.AD(required = false)
    public String sms();
}
