/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.daily.average.service.service.impl;

import com.daily.average.service.model.ReportStatusLog;
import com.daily.average.service.service.base.ReportStatusLogLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ReportStatusLogLocalServiceImpl
	extends ReportStatusLogLocalServiceBaseImpl {
	
	public ReportStatusLog findByReportUploadLogId(long reportUploadLogId) {
		
		return reportStatusLogPersistence.fetchByReportUploadLogId(reportUploadLogId);
	}
	
	public ReportStatusLog findByReportNameAndUploaderRole(String reportName,String uploaderRole) {
		return reportStatusLogPersistence.fetchByReportNameAndUploaderRole(reportName, uploaderRole);

	}
	
	public List<ReportStatusLog> findByReportType(String reportType) {
		return reportStatusLogPersistence.findByReportType(reportType);

	}
	
	public List<ReportStatusLog> findByUploaderRole(String uploaderRole) {
		return reportStatusLogPersistence.findByUploaderRole(uploaderRole);

	}
	
	public List<ReportStatusLog> findByReportTypeAndUploaderRole(String reportType, String uploaderRole) {
		return reportStatusLogPersistence.findByReportTypeAndUploaderRole(reportType, uploaderRole);

	}
	
	public List<ReportStatusLog> findByDepartment(String department) {
		
		return reportStatusLogPersistence.findByDepartment(department);
	}
	
public List<ReportStatusLog> findByDepartmentAndIntermediaryName(String department,String intermediaryName) {
		
		return reportStatusLogPersistence.findByDepartmentAndIntermediaryName(department,intermediaryName);
	}
	
	public List<ReportStatusLog> findByReportTypeAndDepartment(String reportType, String department) {
		return reportStatusLogPersistence.findByReportTypeAndDepartment(reportType, department);

	}
	
	public List<ReportStatusLog> findByIsApproved(int isApproved) {
		return reportStatusLogPersistence.findByIsApproved(isApproved);

	}
	
	public List<ReportStatusLog> findByUploaderRoleAndDepartment(String uploaderRole, String department) {
		return reportStatusLogPersistence.findByUploaderRoleAndDepartment(uploaderRole, department);

	}
	
	public List<ReportStatusLog> findByPfrdaReportAndDepartment(int pfrdaReport, String department) {
		return reportStatusLogPersistence.findByPfrdaReportAndDepartment(pfrdaReport, department);

	}
	public ReportStatusLog findByReportMasterIdAndReportDate(long reportMasterId, Date reportDate) {
		return reportStatusLogPersistence.fetchByReportMasterIdAndReportDate(reportMasterId, reportDate);

	}
	
	public ReportStatusLog findByMasterIdAndReportDateAndIntermediaryId(long reportMasterId, Date reportDate, int intermediaryid) {
		return reportStatusLogPersistence.fetchByReportMasterIdAndReportDateAndIntermediaryid(reportMasterId, reportDate, intermediaryid);

	}
	
	public ReportStatusLog findByReportMasterId(long reportMasterId) {
		return reportStatusLogPersistence.fetchByReportMasterId(reportMasterId);

	}
	
	public List<ReportStatusLog> findByReportMasterIdAndSubmitedToNPST(long reportMasterId, int submitedToNPST) {
		return reportStatusLogPersistence.findByReportMasterIdAndSubmitedToNPST(reportMasterId, submitedToNPST);

	}
	
	public ReportStatusLog findByFileEntryId(long fileEntryId) {
		return reportStatusLogPersistence.fetchByFileEntryId(fileEntryId);

	}
	
	
	public List<ReportStatusLog> findByDepartmentAndSubmittedToNPST(String department, int submittedToNpst) {
		
		return reportStatusLogPersistence.findByDepartmentAndSubmittedToNPST(department,submittedToNpst);
	}
	
public List<ReportStatusLog> findByDepartmentAndIntermediaryAndSubmittedToNPST(String department,String intermediaryName,int submittedToNpst) {
		
		return reportStatusLogPersistence.findByDepartmentAndIntermediaryAndSubmittedToNPST(department,intermediaryName,submittedToNpst);
	}

public List<ReportStatusLog> findByDeptmentAndSubmittedToNPSTAndReportType(String department, int submittedToNpst,String reportType) {
	
	return reportStatusLogPersistence.findByDepartmentAndSubmittedToNPSTAndReportType(department,submittedToNpst,reportType);
}

public List<ReportStatusLog> findByDeptAndIntermediaryAndSubmittedToNPSTAndReportType(String department,String intermediaryName,int submittedToNpst,String reportType) {
	
	return reportStatusLogPersistence.findByDepartmentAndIntermediaryAndSubmittedToNPSTAndReportType(department,intermediaryName,submittedToNpst,reportType);
}

public List<ReportStatusLog> findByDepartmentAndIsApproved(String department, int isApproved) {
	
	return reportStatusLogPersistence.findByDepartmentAndIsApproved(department,isApproved);
}

public List<ReportStatusLog> findByDepartmentAndIntermediaryAndIsApproved(String department,String intermediaryName,int isApproved) {
	
	return reportStatusLogPersistence.findByDepartmentAndIntermediaryAndIsApproved(department,intermediaryName,isApproved);
}


}