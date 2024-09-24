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
import com.nps.manpower.model.CompositionCommittee;
import com.nps.manpower.service.CompositionCommitteeLocalServiceUtil;
import com.nps.manpower.service.ManpowerEmployeeLocalService;
import com.nps.manpower.service.base.CompositionCommitteeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the composition committee local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.nps.manpower.service.CompositionCommitteeLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompositionCommitteeLocalServiceBaseImpl
 */
/*
 * @Component( property =
 * "model.class.name=com.nps.manpower.model.CompositionCommittee", service =
 * AopService.class )
 */
public class CompositionCommitteeLocalServiceImpl
	extends CompositionCommitteeLocalServiceBaseImpl {

	/*
	  * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.nps.manpower.service.CompositionCommitteeLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.nps.manpower.service.CompositionCommitteeLocalServiceUtil</code>.
	 */
	
	/**
	 * @param int status
	 */
	public List<CompositionCommittee> getAllByStatus(int status) {
		return compositionCommitteePersistence.findByStatus(status);
	}
	

	/**
	 * @param String designation
	 */
	public List<CompositionCommittee> getAllByDesignation(String designation) {
		return compositionCommitteePersistence.findByCommitteeDesignation(designation);
	}
	
	/**
	 * @param long compositionCommitteeId
	 * @param int status
	 */
	public CompositionCommittee deleteShareHolding(long compositionCommitteeId,int status) throws PortalException {
		CompositionCommittee compositionCommittee=CompositionCommitteeLocalServiceUtil.getCompositionCommittee(compositionCommitteeId);
		compositionCommittee.setStatus(status);
		return CompositionCommitteeLocalServiceUtil.updateCompositionCommittee(compositionCommittee);
	}
	
	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	public List<CompositionCommittee> getAllByManpowerAndStatus(long manpowerEmployeeId,int status){
		
		return compositionCommitteePersistence.findByManpowerIdAndStatus(manpowerEmployeeId, status);
		
	}
	
	/**
	 * @param long manpowerEmployeeId
	 * @param int currentStatus
	 * @param int deletedStatus
	 */
	public CompositionCommittee deleteAllCompositionCommittee(long manpowerEmployeeId,int currentStatus,int deletedStatus) throws PortalException {
		List<CompositionCommittee> compositionCommittees=getAllByManpowerAndStatus(manpowerEmployeeId, currentStatus);
		for(CompositionCommittee compositionCommittee:compositionCommittees) {
			deleteShareHolding(compositionCommittee.getCompositionCommitteeId(), deletedStatus);
		}
		return null;
	}
	

	/**
	 * @param long compositionCommitteeId
	 * @param long manpowerEmployeeId
	 * @param designation
	 * @param name
	 * @param String email
	 * @param membershipType
	 * @param committeeAppointmentDate
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * 
	 */
	public CompositionCommittee saveCompositionCommittee(long compositionCommitteeId,long manpowerEmployeeId,long committeeMemberShipType,String designation,String name,String email,String membershipType,Date committeeAppointmentDate,int status){
		try {
			Date date = new Date();
			CompositionCommittee compositionCommittee = null;
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if (compositionCommitteeId > 0) {
				compositionCommittee = CompositionCommitteeLocalServiceUtil.getCompositionCommittee(compositionCommitteeId);
				compositionCommittee.setModifiedDate(date);
			} else {
				compositionCommittee = CompositionCommitteeLocalServiceUtil.createCompositionCommittee(CounterLocalServiceUtil.increment(CompositionCommittee.class.getName()));
				compositionCommittee.setModifiedDate(date);
				compositionCommittee.setCreateDate(date);
			}
		
			compositionCommittee.setUserId(serviceContext.getUserId());
			compositionCommittee.setGroupId(serviceContext.getScopeGroupId());
			compositionCommittee.setCompanyId(serviceContext.getCompanyId());
			
			compositionCommittee.setManpowerEmployeeId(manpowerEmployeeId);
			compositionCommittee.setCommitteeDesignation(designation);
			compositionCommittee.setName(name);
			compositionCommittee.setEmail(email);
			compositionCommittee.setMembershipType(membershipType);
			compositionCommittee.setCommitteeMemberShipType(committeeMemberShipType);
			
			compositionCommittee.setCommitteeAppointmentDate(committeeAppointmentDate);;
			compositionCommittee.setStatus(status);

			compositionCommittee= CompositionCommitteeLocalServiceUtil.updateCompositionCommittee(compositionCommittee);
			
			return compositionCommittee;

		} catch (Exception e) {
			logger.error(e.getCause() + " : " + e.getMessage());
		}
		return null;
		
	}
	
	Log logger = LogFactoryUtil.getLog(CompositionCommitteeLocalServiceImpl.class);
	
}