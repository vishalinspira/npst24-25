package com.quarter.ended.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.quarter.ended.portlet.configuration.QuaterEndedConfiguration")
public interface QuaterEndedConfiguration {
	@Meta.AD(required = false)
    public String email();
	
	@Meta.AD(required = false)
    public String sms();


}
