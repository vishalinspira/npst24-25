package com.account.statement.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.account.statement.portlet.configuration.AccountStatementConfiguration")
public interface AccountStatementConfiguration {
	@Meta.AD(required = false)
    public String email();
	
	@Meta.AD(required = false)
    public String sms();
}
