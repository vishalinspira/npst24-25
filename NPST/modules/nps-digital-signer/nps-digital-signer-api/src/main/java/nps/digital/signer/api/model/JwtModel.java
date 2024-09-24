package nps.digital.signer.api.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;

@ImplementationClassName(
		"nps.digital.signer.model.impl.JwtModelImpl"
	)
public interface JwtModel {
	
	public boolean isIsSuccess();
	
	public void setIsSuccess(boolean isSuccess) ;
	
	public String getTxnOutcome();
	
	public void setTxnOutcome(String txnOutcome);
}
