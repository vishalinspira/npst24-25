package com.DailyAverage.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder("inputds")
public class DailyAverageXMLModel {
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCUST_ID() {
		return CUST_ID;
	}
	public void setCUST_ID(long cUST_ID) {
		CUST_ID = cUST_ID;
	}
	public long getNPS_Acc_No() {
		return NPS_Acc_No;
	}
	public void setNPS_Acc_No(long nPS_Acc_No) {
		NPS_Acc_No = nPS_Acc_No;
	}
	public String getNPS_Acc_Name() {
		return NPS_Acc_Name;
	}
	public void setNPS_Acc_Name(String nPS_Acc_Name) {
		NPS_Acc_Name = nPS_Acc_Name;
	}
	public String getTran_Date() {
		return Tran_Date;
	}
	public void setTran_Date(String tran_Date) {
		Tran_Date = tran_Date;
	}
	public String getTran_ID() {
		return Tran_ID;
	}
	public void setTran_ID(String tran_ID) {
		Tran_ID = tran_ID;
	}
	public long getPart_Tran_SNo() {
		return Part_Tran_SNo;
	}
	public void setPart_Tran_SNo(long part_Tran_SNo) {
		Part_Tran_SNo = part_Tran_SNo;
	}
	public long getChq_No() {
		return Chq_No;
	}
	public void setChq_No(long chq_No) {
		Chq_No = chq_No;
	}
	public String getChq_Date() {
		return Chq_Date;
	}
	public void setChq_Date(String chq_Date) {
		Chq_Date = chq_Date;
	}
	public String getDebit_Trans() {
		return Debit_Trans;
	}
	public void setDebit_Trans(String debit_Trans) {
		Debit_Trans = debit_Trans;
	}
	public String getCredit_Trans() {
		return Credit_Trans;
	}
	public void setCredit_Trans(String credit_Trans) {
		Credit_Trans = credit_Trans;
	}
	public String getRunning_Bal() {
		return Running_Bal;
	}
	public void setRunning_Bal(String running_Bal) {
		Running_Bal = running_Bal;
	}
	public String getTrans_Particulars() {
		return Trans_Particulars;
	}
	public void setTrans_Particulars(String trans_Particulars) {
		Trans_Particulars = trans_Particulars;
	}
	public String getTrans_Remarks() {
		return Trans_Remarks;
	}
	public void setTrans_Remarks(String trans_Remarks) {
		Trans_Remarks = trans_Remarks;
	}
	public long getSOL_ID() {
		return SOL_ID;
	}
	public void setSOL_ID(long sOL_ID) {
		SOL_ID = sOL_ID;
	}
	public long getCP_Acc_No() {
		return CP_Acc_No;
	}
	public void setCP_Acc_No(long cP_Acc_No) {
		CP_Acc_No = cP_Acc_No;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public long getCreatedby() {
		return createdby;
	}
	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}
	private long CUST_ID;
	private long NPS_Acc_No;
	private String NPS_Acc_Name;
	private String Tran_Date;
	private String Tran_ID;
	private long Part_Tran_SNo;
	private long Chq_No;
	private String Chq_Date;
	private String Debit_Trans;
	private String Credit_Trans;
	private String Running_Bal;
	private String Trans_Particulars;
	private String Trans_Remarks;
	private long SOL_ID;
	private long CP_Acc_No;
	private Date CreateDate;
	private long createdby;
}
	