package com.trusteebank.annex13.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.trusteebank.annex13.configuration.IComplianceCertificate")
public interface IComplianceCertificate {
	
	@Meta.AD(required = false)
    public String email();
	
	@Meta.AD(required = false)
    public String sms();

}
