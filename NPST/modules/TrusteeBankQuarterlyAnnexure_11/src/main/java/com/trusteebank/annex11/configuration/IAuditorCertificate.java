package com.trusteebank.annex11.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.trusteebank.annex11.configuration.IAuditorCertificate")
public interface IAuditorCertificate {
	
	@Meta.AD(required = false)
    public String email();
	
	@Meta.AD(required = false)
    public String sms();
	
}
