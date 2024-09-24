package com.nps.erm.modal;

import com.nps.erm.model.ErmInformation;

import java.util.List;

public class ErmModal {

	ErmInformation ermInformation;
	long ermId;
	String userName;
	String workflowTaskId;
	String remarks;
	String intermediaryName;
	String assignedTo;
	String dueDate;
	List<String>transitionNames;
	boolean isSelfAsignee;
	boolean isAssignable;
	boolean isCompleted;
	boolean subcriberCheck;
	boolean craCheck;
	
	public boolean isSelfAsignee() {
		return isSelfAsignee;
	}
	public void setSelfAsignee(boolean isSelfAsignee) {
		this.isSelfAsignee = isSelfAsignee;
	}
	public ErmInformation getErmInformation() {
		return ermInformation;
	}
	public void setErmInformation(ErmInformation ermInformation) {
		this.ermInformation = ermInformation;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkflowTaskId() {
		return workflowTaskId;
	}
	public void setWorkflowTaskId(String workflowTaskId) {
		this.workflowTaskId = workflowTaskId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIntermediaryName() {
		return intermediaryName;
	}
	public void setIntermediaryName(String intermediaryName) {
		this.intermediaryName = intermediaryName;
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
	public List<String> getTransitionNames() {
		return transitionNames;
	}
	public void setTransitionNames(List<String> transitionNames) {
		this.transitionNames = transitionNames;
	}
	public boolean isAssignable() {
		return isAssignable;
	}
	public void setAssignable(boolean isAssignable) {
		this.isAssignable = isAssignable;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public long getErmId() {
		return ermId;
	}
	public void setErmId(long ermId) {
		this.ermId = ermId;
	}
	public boolean isSubcriberCheck() {
		return subcriberCheck;
	}
	public void setSubcriberCheck(boolean subcriberCheck) {
		this.subcriberCheck = subcriberCheck;
	}
	public boolean isCraCheck() {
		return craCheck;
	}
	public void setCraCheck(boolean craCheck) {
		this.craCheck = craCheck;
	}


	
	
}
