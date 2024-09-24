package com.trusteebank.annex9.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.trusteebank.annex9.configuration.IPerformanceRepConfiguration")
public interface IPerformanceRepConfiguration {

	@Meta.AD(required = false)
    public String email();
	
	@Meta.AD(required = false)
    public String sms();
	
	
}
