package com.nps.dashboard.web.model;

public class DepartmentData {

	private long totalCount;
	private long pendingCount;
	private long submittedCount;
	private long approvedCount;
	private String departmentName;
	
	public long getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(long pendingCount) {
		this.pendingCount = pendingCount;
	}
	public long getSubmittedCount() {
		return submittedCount;
	}
	public void setSubmittedCount(long submittedCount) {
		this.submittedCount = submittedCount;
	}
	public long getApprovedCount() {
		return approvedCount;
	}
	public void setApprovedCount(long approvedCount) {
		this.approvedCount = approvedCount;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@Override
	public String toString() {
		return "DepartmentData [totalCount=" + totalCount + ", pendingCount=" + pendingCount + ", submittedCount="
				+ submittedCount + ", approvedCount=" + approvedCount + ", departmentName=" + departmentName + "]";
	}
	
}
