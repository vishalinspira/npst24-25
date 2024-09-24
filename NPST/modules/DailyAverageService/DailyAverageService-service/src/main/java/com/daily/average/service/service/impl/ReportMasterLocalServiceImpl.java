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

import com.daily.average.service.exception.NoSuchReportMasterException;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.impl.ReportMasterImpl;
import com.daily.average.service.service.base.ReportMasterLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ReportMasterLocalServiceImpl
	extends ReportMasterLocalServiceBaseImpl {
	
	public ReportMaster getReportMasterByReportName(String reportName, String uploaderRole) throws NoSuchReportMasterException {
		return reportMasterPersistence.findByReportNameAndUploaderRole(reportName, uploaderRole);
	}
	
	
	public List<ReportMaster> getByReportType(String reportType){
		return reportMasterPersistence.findByReportType(reportType);
	}
	
	public List<ReportMaster> getByUploaderRole(String uploaderRole){
		return reportMasterPersistence.findByUploaderRole(uploaderRole);
	}
	
	public List<ReportMaster> getByReportTypeAndUploaderRole(String reportType, String uploaderRole){
		return reportMasterPersistence.findByReportTypeAndUploaderRole(reportType, uploaderRole);
	}
	
	public List<ReportMaster> getByDepartment(String department){
		return reportMasterPersistence.findByDepartment(department);
	}
	
	public int countByDepartment(String department) {
		return reportMasterPersistence.countByDepartment(department);
	}
	
	public List<ReportMaster> getByUploaderRoleAndDepartment(String uploaderRole, String department){
		return reportMasterPersistence.findByUploaderRoleAndDepartment(uploaderRole, department);
	}
	
	public List<ReportMaster> getByReportTypeAndDepartment(String reportType, String department) {
		return reportMasterPersistence.findByReportTypeAndDepartment(reportType, department);
	}
	
	public List<Long> getIntermediartTypeByUploaderRole(String uploaderRole) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ReportMasterImpl.class, PortalClassLoaderUtil.getClassLoader());
		dynamicQuery.add(RestrictionsFactoryUtil.eq("uploaderRole", uploaderRole));
		dynamicQuery.setProjection(ProjectionFactoryUtil.distinct(ProjectionFactoryUtil.property("intermediarytype")));
		
		return reportMasterLocalService.dynamicQuery(dynamicQuery);
	}
	
	/**
	 * 
	 * @param pfrdaReport
	 * @param department
	 * @return
	 */
	public List<ReportMaster> getByPfrdaReportAndDepartment(int pfrdaReport, String department) {
		return reportMasterPersistence.findByPfrdaReportAndDepartment(pfrdaReport, department);
	}
}