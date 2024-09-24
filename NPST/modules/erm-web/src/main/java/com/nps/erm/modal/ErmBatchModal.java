package com.nps.erm.modal;

import com.nps.erm.model.ErmBatchInformation;

import java.util.List;

public class ErmBatchModal {
	ErmBatchInformation ermBatchInformation;
	String userName;
	String assignedTo;
	String dueDate;
	boolean isSelfAsignee;
	List<String>transitionNames;
	int receivedCaseNo;
	long batchNo;
	String batchStatus;
	
	public int getReceivedCaseNo() {
		return receivedCaseNo;
	}
	public void setReceivedCaseNo(int receivedCaseNo) {
		this.receivedCaseNo = receivedCaseNo;
	}
	public List<String> getTransitionNames() {
		return transitionNames;
	}
	public void setTransitionNames(List<String> transitionNames) {
		this.transitionNames = transitionNames;
	}
	public ErmBatchInformation getErmBatchInformation() {
		return ermBatchInformation;
	}
	public void setErmBatchInformation(ErmBatchInformation ermBatchInformation) {
		this.ermBatchInformation = ermBatchInformation;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public boolean isSelfAsignee() {
		return isSelfAsignee;
	}
	public void setSelfAsignee(boolean isSelfAsignee) {
		this.isSelfAsignee = isSelfAsignee;
	}
	public long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	
	
}
