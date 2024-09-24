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
import com.nps.manpower.model.ManpowerDirectorHolding;
import com.nps.manpower.service.ChangeShareHoldingLocalService;
import com.nps.manpower.service.ChangeShareHoldingLocalServiceUtil;
import com.nps.manpower.service.ManpowerDirectorHoldingLocalServiceUtil;
import com.nps.manpower.service.base.ManpowerDirectorHoldingLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the manpower director holding local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.nps.manpower.service.ManpowerDirectorHoldingLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ManpowerDirectorHoldingLocalServiceBaseImpl
 */
/*
 * @Component( property =
 * "model.class.name=com.nps.manpower.model.ManpowerDirectorHolding", service =
 * AopService.class )
 */
public class ManpowerDirectorHoldingLocalServiceImpl
	extends ManpowerDirectorHoldingLocalServiceBaseImpl {

	/**
	 * @param int status
	 */
	public List<ManpowerDirectorHolding> getAllByStatus(int status) {
		return manpowerDirectorHoldingPersistence.findByStatus(status);
	}
	

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	public List<ManpowerDirectorHolding> getAllByManpowerAndStatus(long manpowerEmployeeId,int status) {
		return manpowerDirectorHoldingPersistence.findByManpowerIdAndStatus(manpowerEmployeeId, status);
	}
	
	/**
	 * @param long manpowerDirectorHoldingId
	 * @param int status
	 */
	public ManpowerDirectorHolding deleteShareHolding(long manpowerDirectorHoldingId,int status) throws PortalException {
		ManpowerDirectorHolding manpowerDirectorHolding=ManpowerDirectorHoldingLocalServiceUtil.getManpowerDirectorHolding(manpowerDirectorHoldingId);
		manpowerDirectorHolding.setStatus(status);
		return ManpowerDirectorHoldingLocalServiceUtil.updateManpowerDirectorHolding(manpowerDirectorHolding);
	}
	
	/**
	 * @param long manpowerEmployeeId
	 * @param int currentStatus
	 * @param int deletedStatus
	 */
	public ManpowerDirectorHolding deleteAllDirectorShareHolding(long manpowerEmployeeId,int currentStatus,int deletedStatus) throws PortalException {
		List<ManpowerDirectorHolding> manpowerDirectorHoldings=getAllByManpowerAndStatus(manpowerEmployeeId, currentStatus);
		for(ManpowerDirectorHolding manpowerDirectorHolding:manpowerDirectorHoldings) {
			deleteShareHolding(manpowerDirectorHolding.getManpowerDirectorHoldingId(), deletedStatus);
		}
		return null;
	}
	
	/**
	 * @param long manpowerDirectorHoldingId
	 * @param long manpowerEmployeeId
	 * @param String companyName
	 * @param String concern
	 * @param String shareHolding
	 * @param Date concernDate
	 * @param int status
	 * @param Date fromDate
	 * @param Date toDate
	 * 
	 */
	public ManpowerDirectorHolding saveShareHolding(long manpowerDirectorHoldingId,long manpowerEmployeeId,String companyName,String concern,String shareHolding,
		 Date concernDate,int status){
		try {
			Date date = new Date();
			ManpowerDirectorHolding manpowerDirectorHolding = null;
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if (manpowerDirectorHoldingId > 0) {
				manpowerDirectorHolding = ManpowerDirectorHoldingLocalServiceUtil.getManpowerDirectorHolding(manpowerDirectorHoldingId);
				manpowerDirectorHolding.setModifiedDate(date);
			} else {
				manpowerDirectorHolding = ManpowerDirectorHoldingLocalServiceUtil.createManpowerDirectorHolding(CounterLocalServiceUtil.increment(ManpowerDirectorHolding.class.getName()));
				manpowerDirectorHolding.setModifiedDate(date);
				manpowerDirectorHolding.setCreateDate(date);
			}
		
			manpowerDirectorHolding.setUserId(serviceContext.getUserId());
			manpowerDirectorHolding.setGroupId(serviceContext.getScopeGroupId());
			manpowerDirectorHolding.setCompanyId(serviceContext.getCompanyId());
			
			manpowerDirectorHolding.setManpowerEmployeeId(manpowerEmployeeId);
			manpowerDirectorHolding.setCompanyName(companyName);
			manpowerDirectorHolding.setConcern(concern);
			manpowerDirectorHolding.setShareHolding(shareHolding);
			
			manpowerDirectorHolding.setConcernDate(concernDate);
			manpowerDirectorHolding.setStatus(status);
			ChangeShareHoldingLocalServiceUtil.saveChangeInShareHolding(manpowerEmployeeId,companyName,concern,shareHolding,concernDate,status);
			 return manpowerDirectorHoldingLocalService.updateManpowerDirectorHolding(manpowerDirectorHolding);
		} catch (Exception e) {
			logger.error(e.getCause() + " : " + e.getMessage());
		}
		return null;
		
	}

	Log logger = LogFactoryUtil.getLog(ManpowerDirectorHoldingServiceImpl.class);
}