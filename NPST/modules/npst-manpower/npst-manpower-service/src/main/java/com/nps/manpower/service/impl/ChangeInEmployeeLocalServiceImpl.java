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

package com.nps.manpower.service.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.nps.manpower.model.ChangeInEmployee;
import com.nps.manpower.service.ChangeInEmployeeLocalServiceUtil;
import com.nps.manpower.service.base.ChangeInEmployeeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the change in employee local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.nps.manpower.service.ChangeInEmployeeLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeInEmployeeLocalServiceBaseImpl
 */
/*
 * @Component( property =
 * "model.class.name=com.nps.manpower.model.ChangeInEmployee", service =
 * AopService.class )
 */
public class ChangeInEmployeeLocalServiceImpl
	extends ChangeInEmployeeLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.nps.manpower.service.ChangeInEmployeeLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.nps.manpower.service.ChangeInEmployeeLocalServiceUtil</code>.
	 */
	
	
	/**
	 * @param String designation
	 * @param long manpowerEmployeeId
	 * @param int committeeMembershipType
	 * 
	 */
	public List<ChangeInEmployee> getAllDesignationAndCommitteeTypeAndEmployeeId(String designation,long manpowerEmployeeId,int committeeMembershipType) {
		return changeInEmployeePersistence.findByDesignationAndCommitteeTypeAndEmployeeId(designation, manpowerEmployeeId, committeeMembershipType);
	}
	
	/**
	 * @param String designation
	 * @param long manpowerEmployeeId
	 */
	public List<ChangeInEmployee> getAllByDesignationAndEmployeeId(String designation,long manpowerEmployeeId) {
		return changeInEmployeePersistence.findByDesignationAndEmployeeId(designation, manpowerEmployeeId);
	}
	
	
	/**
	 * @param int isDirector
	 * @param long manpowerEmployeeId
	 * 
	 */
	public List<ChangeInEmployee> getAllByIsDirectorAndEmployeeId(int isDirector,long manpowerEmployeeId) {
		return changeInEmployeePersistence.findByIsDirectorAndEmployeeId(isDirector, manpowerEmployeeId);
	}
	
	/**
	 * @param int isDirector
	 * @param String membershipType
	 * @param long manpowerEmployeeId
	 * 
	 */
	public List<ChangeInEmployee> getAllByIsDirectorAndMembershipTypeAndEmployeeId(int isDirector,String membershipType,long manpowerEmployeeId) {
		return changeInEmployeePersistence.findByIsDirectorAndMembershipTypeAndEmployeeId(isDirector, membershipType, manpowerEmployeeId);
	}
	
	/**
	 * @param String membershipType
	 * @param long manpowerEmployeeId
	 * 
	 */
	public List<ChangeInEmployee> getAllByMembershipTypeAndEmployeeId(String membershipType,long manpowerEmployeeId) {
		return changeInEmployeePersistence.findByMembershipTypeAndEmployeeId(membershipType, manpowerEmployeeId);
	}
	
	/**
	 * @param int committeeMembershipType
	 * @param long manpowerEmployeeId
	 * 
	 */
	public List<ChangeInEmployee> getAllByCommitteeTypeAndEmployeeId(int committeeMembershipType,long manpowerEmployeeId) {
		return changeInEmployeePersistence.findByCommitteeTypeAndEmployeeId(committeeMembershipType, manpowerEmployeeId);
	}
		
	
	/**
	 * @param long manpowerEmployeeId
	 * 
	 */
	public List<ChangeInEmployee> getAllByEmployeeId(long manpowerEmployeeId) {
		return changeInEmployeePersistence.findByEmployeeId(manpowerEmployeeId );
	}
	
	/**
	 * @param int status
	 */
	public List<ChangeInEmployee> getAllByStatus(int status) {
		return changeInEmployeePersistence.findByStatus(status);
	}

	/**
	 * 
	 * @param long manpowerEmployeeId
	 * @param String designation
	 * @param String name
	 * @param Date appointmentDate
	 * @param String contactNo
	 * @param String email
	 * @param String qualification
	 * @param String experience
	 * @param String deputation
	 * @param String linkedinId
	 * @param Date approvingAppointmentDate
	 * @param long biodataFileId
	 * @param int committeeMembershipType
	 * @param String din
	 * @param String directorType
	 * @param String membershipType
	 * @param String dependency
	 * @param String formMbp
	 * @param int status
	 * @param int isDirector
	 * @param Date fromDate
	 * @param Date toDate
	 * @param Date resignationDate
	 * 
	 * 
	 */
	public ChangeInEmployee saveChangeInEmployee(long manpowerEmployeeId,String designation,String name,Date appointmentDate,String contactNo,String email,String qualification,
			String experience,String deputation,String linkedinId,Date approvingAppointmentDate,long biodataFileId,int committeeMembershipType,String din,String directorType,
			String membershipType,String dependency,long formMbp,int status,int isDirector,Date resignationDate){
		try {
			Date date = new Date();
			ChangeInEmployee changeInEmployee = null;
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

			changeInEmployee = ChangeInEmployeeLocalServiceUtil.createChangeInEmployee(CounterLocalServiceUtil.increment(ChangeInEmployee.class.getName()));
			changeInEmployee.setModifiedDate(date);
			changeInEmployee.setCreateDate(date);
			changeInEmployee.setManpowerEmployeeId(manpowerEmployeeId);
			changeInEmployee.setUserId(serviceContext.getUserId());
			changeInEmployee.setGroupId(serviceContext.getScopeGroupId());
			changeInEmployee.setCompanyId(serviceContext.getCompanyId());
			changeInEmployee.setDesignation(designation);
			changeInEmployee.setName(name);
			changeInEmployee.setAppointmentDate(appointmentDate);
			changeInEmployee.setContactNo(contactNo);
			changeInEmployee.setEmail(email);
			changeInEmployee.setQualification(qualification);
			changeInEmployee.setExperience(experience);
			changeInEmployee.setDeputation(deputation);
			changeInEmployee.setLinkedinId(linkedinId);
			changeInEmployee.setApprovingAppointmentDate(approvingAppointmentDate);
			changeInEmployee.setBiodataFileId(biodataFileId);
			changeInEmployee.setCommitteeMembershipType(committeeMembershipType);
			changeInEmployee.setDin(din);
			changeInEmployee.setDirectorType(directorType);
			changeInEmployee.setMembershipType(membershipType);
			changeInEmployee.setDependency(dependency);
			changeInEmployee.setFormMbp(formMbp);
			changeInEmployee.setStatus(status);
			changeInEmployee.setIsDirector(isDirector);
			changeInEmployee.setResignationDate(resignationDate);
			
	
			 return ChangeInEmployeeLocalServiceUtil.updateChangeInEmployee(changeInEmployee);
		} catch (Exception e) {
			logger.error(e.getCause() + " : " + e.getMessage());
		}
		return null;
		
	}

	Log logger = LogFactoryUtil.getLog(ChangeInEmployee.class);
	
}