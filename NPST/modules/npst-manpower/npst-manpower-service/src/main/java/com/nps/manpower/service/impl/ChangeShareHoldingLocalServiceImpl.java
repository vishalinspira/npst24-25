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
import com.nps.manpower.model.ChangeShareHolding;
import com.nps.manpower.service.ChangeShareHoldingLocalServiceUtil;
import com.nps.manpower.service.base.ChangeShareHoldingLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the change share holding local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.nps.manpower.service.ChangeShareHoldingLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeShareHoldingLocalServiceBaseImpl
 */
/*
 * @Component( property =
 * "model.class.name=com.nps.manpower.model.ChangeShareHolding", service =
 * AopService.class )
 */
public class ChangeShareHoldingLocalServiceImpl
	extends ChangeShareHoldingLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.nps.manpower.service.ChangeShareHoldingLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.nps.manpower.service.ChangeShareHoldingLocalServiceUtil</code>.
	 */
	
	/**
	 * @param int status
	 */
	public List<ChangeShareHolding> getAllByStatus(int status) {
		return changeShareHoldingPersistence.findByStatus(status);
	}
	

	/**
	 * @param long manpowerEmployeeId
	 * @param int status
	 */
	public List<ChangeShareHolding> getAllByManpowerAndStatus(long manpowerEmployeeId,int status) {
		return changeShareHoldingPersistence.findByManpowerIdAndStatus(manpowerEmployeeId, status);
	}
	
	/**
	 * @param long manpowerEmployeeId
	 */
	public List<ChangeShareHolding> getAllByManpowerId(long manpowerEmployeeId) {
		return changeShareHoldingPersistence.findByManpowerId(manpowerEmployeeId);
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
	public ChangeShareHolding saveChangeInShareHolding(long manpowerEmployeeId,String companyName,String concern,String shareHolding,
		 Date concernDate,int status){
		try {
			Date date = new Date();
			ChangeShareHolding changeShareHolding = null;
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

			changeShareHolding = ChangeShareHoldingLocalServiceUtil.createChangeShareHolding(CounterLocalServiceUtil.increment(ChangeShareHolding.class.getName()));
			changeShareHolding.setModifiedDate(date);
			changeShareHolding.setCreateDate(date);
		
			changeShareHolding.setUserId(serviceContext.getUserId());
			changeShareHolding.setGroupId(serviceContext.getScopeGroupId());
			changeShareHolding.setCompanyId(serviceContext.getCompanyId());
			
			changeShareHolding.setManpowerEmployeeId(manpowerEmployeeId);
			changeShareHolding.setCompanyName(companyName);
			changeShareHolding.setConcern(concern);
			changeShareHolding.setShareHolding(shareHolding);
			
			changeShareHolding.setConcernDate(concernDate);
			changeShareHolding.setStatus(status);
	
			 return ChangeShareHoldingLocalServiceUtil.updateChangeShareHolding(changeShareHolding);
		} catch (Exception e) {
			logger.error(e.getCause() + " : " + e.getMessage());
		}
		return null;
		
	}
	Log logger = LogFactoryUtil.getLog(ChangeShareHoldingLocalServiceImpl.class);
}