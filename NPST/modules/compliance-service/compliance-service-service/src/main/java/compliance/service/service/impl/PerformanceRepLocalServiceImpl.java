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

package compliance.service.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

import compliance.service.model.PerformanceRep;
import compliance.service.service.base.PerformanceRepLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class PerformanceRepLocalServiceImpl extends PerformanceRepLocalServiceBaseImpl {
	
	public PerformanceRep addPerformanceRep(Date reportDate, String addressLine1, String addressLine2, String addressLine3, 
			String trusteebankname, Date date2, String officername, Date submitdate, 
			String place) {
				
		PerformanceRep performanceRep = performanceRepPersistence.create(counterLocalService.increment());
		performanceRep.setReportdate(reportDate);
		performanceRep.setAddressline1(addressLine1);
		performanceRep.setAddressline2(addressLine2);
		performanceRep.setAddressline3(addressLine3);
		performanceRep.setTrusteebankname(trusteebankname);
		performanceRep.setMonthendingdate(date2);
		performanceRep.setOfficername(officername);
		performanceRep.setSubmitdate(submitdate);
		performanceRep.setPlace(place);
		
		performanceRep = performanceRepPersistence.update(performanceRep);
		
		return performanceRep;
		
	}

	@Override
	public PerformanceRep updatePerformanceRepStatus(long id, long userId, int status, ServiceContext serviceContext) {
		PerformanceRep performanceRep = null;
		try {
			performanceRep = performanceRepPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(performanceRep)) {
			performanceRep.setStatus(status);
			performanceRep.setStatusByUserId(userId);
			performanceRep.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				performanceRep.setStatusByUserName(user.getFullName());
				performanceRep.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			performanceRep = performanceRepPersistence.update(performanceRep);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(PerformanceRep.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(PerformanceRep.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return performanceRep;
	}
	static Log _log = LogFactoryUtil.getLog(PerformanceRepLocalServiceImpl.class);
}
	