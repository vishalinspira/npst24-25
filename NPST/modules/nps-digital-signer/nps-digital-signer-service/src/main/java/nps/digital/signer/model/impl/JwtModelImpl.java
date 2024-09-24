package nps.digital.signer.model.impl;

import nps.digital.signer.api.model.JwtModel;

public class JwtModelImpl implements JwtModel {
  boolean IsSuccess;
  
  String TxnOutcome;
  
  public boolean isIsSuccess() {
    return this.IsSuccess;
  }
  
  public void setIsSuccess(boolean isSuccess) {
    this.IsSuccess = isSuccess;
  }
  
  public String getTxnOutcome() {
    return this.TxnOutcome;
  }
  
  public void setTxnOutcome(String txnOutcome) {
    this.TxnOutcome = txnOutcome;
  }
}
