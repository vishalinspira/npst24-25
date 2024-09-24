package com.trusteebank.annex8.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.trusteebank.annex8.configuration.IClosingBalConfiguration")
public interface IClosingBalConfiguration {

	@Meta.AD(required = false)
    public String email();
	
	@Meta.AD(required = false)
    public String sms();
	
}
