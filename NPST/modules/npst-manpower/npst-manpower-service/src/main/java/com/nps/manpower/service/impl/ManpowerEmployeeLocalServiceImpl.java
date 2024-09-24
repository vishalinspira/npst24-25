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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.nps.manpower.model.ManpowerEmployee;
import com.nps.manpower.service.ChangeInEmployeeLocalService;
import com.nps.manpower.service.ChangeInEmployeeLocalServiceUtil;
import com.nps.manpower.service.ManpowerDirectorHoldingLocalService;
import com.nps.manpower.service.ManpowerDirectorHoldingLocalServiceUtil;
import com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil;
import com.nps.manpower.service.base.ManpowerEmployeeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the manpower employee local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.nps.manpower.service.ManpowerEmployeeLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ManpowerEmployeeLocalServiceBaseImpl
 */
/*
 * @Component( property =
 * "model.class.name=com.nps.manpower.model.ManpowerEmployee", service =
 * AopService.class )
 */
public class ManpowerEmployeeLocalServiceImpl
	extends ManpowerEmployeeLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.nps.manpower.service.ManpowerEmployeeLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil</code>.
	 */
	
	/**
	 * @param int status
	 */
	public List<ManpowerEmployee> getAllByStatus(int status) {
		return manpowerEmployeePersistence.findByStatus(status);
	}
	
	/**
	 * @param String din
	 * 
	 */
	public List<ManpowerEmployee> getAllByDIN(String din) {
		return manpowerEmployeePersistence.findByDIN(din);
	}
	
	/**
	 * @param String email
	 * 
	 */
	public List<ManpowerEmployee> getAllByEmail(String email) {
		return manpowerEmployeePersistence.findByEmail(email);
	}
	
	/**
	 * @param isDirector
	 * 
	 */
	public List<ManpowerEmployee> getAllByIsDirector(int isDirector) {
		return manpowerEmployeePersistence.findByIsDirector(isDirector);
	}
	
	/**
	 * 
	 * @param isDirector
	 * @param pfmName
	 * @return
	 */
	public List<ManpowerEmployee> findByIsDirectorAndPfmName(int isDirector,String pfmName) {
		return manpowerEmployeePersistence.findByIsDirectorAndPfmName(isDirector, pfmName);
	}
	
	/**
	 * 
	 * @param isDirector
	 * @param pfmName
	 * @param status
	 * @return
	 */
	public List<ManpowerEmployee> findByIsDirectorAndPfmNameAndStatus(int isDirector,String pfmName,int status) {
		return manpowerEmployeePersistence.findByIsDirectorAndPfmNameAndStatus(isDirector, pfmName, status);
	}
	
	/**
	 * 
	 * @param committeeMembershipType
	 * @param pfmName
	 * @return
	 */
	public List<ManpowerEmployee> findByPfmAndCommitteeMembershipType(int committeeMembershipType,String pfmName) {
		return manpowerEmployeePersistence.findByPfmNameAndCommitteeMembershipType(committeeMembershipType, pfmName);
	}
	
	/**
	 * 
	 * @param committeeMembershipType
	 * @param pfmName
	 * @param status
	 * @return
	 */
	public List<ManpowerEmployee> findByPfmAndCommitteeMembershipTypeAndStatus(int committeeMembershipType,String pfmName,int status) {
		return manpowerEmployeePersistence.findByPfmaNameAndStatusAndCommitteeMembershipType(status, committeeMembershipType, pfmName);
	}
	/**
	 * @param int isDirector
	 * @param int status
	 * 
	 */
	public List<ManpowerEmployee> getAllByIsDirectorAndStatus(int isDirector,int status) {
		return manpowerEmployeePersistence.findByIsDirectorAndStatus(isDirector, status);
	}
	
	public List<ManpowerEmployee> getAllByStatusAndCommitteeMembershipType(int status,int committeeMembershipType){
		return manpowerEmployeePersistence.findByStatusAndCommitteeMembershipType(status, committeeMembershipType);
	}
	
	public  List<ManpowerEmployee> getAllByCommitteeMembershipType(int committeeMembershipType) {
		return manpowerEmployeePersistence.findByCommitteeMembershipType(committeeMembershipType);

	}
	
	public  List<ManpowerEmployee> getAllByDesignation(String designation) {
		return manpowerEmployeePersistence.findByDesignation(designation);

	}
	
	public  List<ManpowerEmployee> getAllByDesignationAndPFM(String designation,String pfm) {
		return manpowerEmployeePersistence.findByDesignationAndPFM(designation, pfm);

	}
	
	public  List<ManpowerEmployee> getAllByDependencyAndPFM(String dependency,String pfm) {
		return manpowerEmployeePersistence.findByDependencyAndPFM(dependency, pfm);

	}
	
	/**
	 * @param long manpowerEmployeeId
	 * @param int deletedStatus
	 * 
	 */
	public ManpowerEmployee deleteManpowerEmployee(long manpowerEmployeeId,int deletedStatus,Date resignationDate) throws PortalException {
		// TODO Auto-generated method stub
		ManpowerEmployee manpowerEmployee=ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
		ManpowerDirectorHoldingLocalServiceUtil.deleteAllDirectorShareHolding(manpowerEmployeeId, manpowerEmployee.getStatus(), deletedStatus);
		manpowerEmployee.setResignationDate(resignationDate);
		manpowerEmployee.setStatus(deletedStatus);
		return ManpowerEmployeeLocalServiceUtil.updateManpowerEmployee(manpowerEmployee);
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
	public ManpowerEmployee saveManpowerEmployee(long manpowerEmployeeId,String designation,String name,Date appointmentDate,String contactNo,String email,String qualification,
			String experience,String deputation,String linkedinId,Date approvingAppointmentDate,long biodataFileId,int committeeMembershipType,String din,String directorType,
			String membershipType,String dependency,long formMbp,int status,int isDirector,Date resignationDate,String pfmName){
		try {
			Date date = new Date();
			ManpowerEmployee manpowerEmployee = null;
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if (manpowerEmployeeId > 0) {
				manpowerEmployee = ManpowerEmployeeLocalServiceUtil.getManpowerEmployee(manpowerEmployeeId);
				manpowerEmployee.setModifiedDate(date);
			} else {
				manpowerEmployee = ManpowerEmployeeLocalServiceUtil.createManpowerEmployee(
						CounterLocalServiceUtil.increment(ManpowerEmployee.class.getName()));
				manpowerEmployee.setModifiedDate(date);
				manpowerEmployee.setCreateDate(date);
			}
		
			manpowerEmployee.setUserId(serviceContext.getUserId());
			manpowerEmployee.setGroupId(serviceContext.getScopeGroupId());
			manpowerEmployee.setCompanyId(serviceContext.getCompanyId());
			
			manpowerEmployee.setDesignation(designation);
			manpowerEmployee.setName(name);
			manpowerEmployee.setAppointmentDate(appointmentDate);
			manpowerEmployee.setContactNo(contactNo);
			manpowerEmployee.setEmail(email);
			manpowerEmployee.setQualification(qualification);
			manpowerEmployee.setExperience(experience);
			manpowerEmployee.setDeputation(deputation);
			manpowerEmployee.setLinkedinId(linkedinId);
			manpowerEmployee.setApprovingAppointmentDate(approvingAppointmentDate);
			manpowerEmployee.setBiodataFileId(biodataFileId);
			manpowerEmployee.setCommitteeMembershipType(committeeMembershipType);
			manpowerEmployee.setDin(din);
			manpowerEmployee.setDirectorType(directorType);
			manpowerEmployee.setMembershipType(membershipType);
			manpowerEmployee.setDependency(dependency);
			manpowerEmployee.setFormMbp(formMbp);
			manpowerEmployee.setStatus(status);
			manpowerEmployee.setIsDirector(isDirector);
			manpowerEmployee.setResignationDate(resignationDate);
			manpowerEmployee.setPfmName(pfmName);
			
			ChangeInEmployeeLocalServiceUtil.saveChangeInEmployee(manpowerEmployee.getManpowerEmployeeId(),designation,name, appointmentDate, contactNo, email, qualification,
					 experience, deputation, linkedinId, approvingAppointmentDate, biodataFileId, committeeMembershipType, din, directorType,
					 membershipType, dependency, formMbp, status, isDirector, resignationDate);
			 return ManpowerEmployeeLocalServiceUtil.updateManpowerEmployee(manpowerEmployee);
		} catch (Exception e) {
			logger.error(e.getCause() + " : " + e.getMessage());
		}
		return null;
		
	}
	
	Log logger = LogFactoryUtil.getLog(ManpowerEmployeeServiceImpl.class);
	
}