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

import com.daily.average.service.model.AccountStatement;
import com.daily.average.service.model.DailyAverage;
import com.daily.average.service.service.base.DailyAverageLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DailyAverageLocalServiceImpl
	extends DailyAverageLocalServiceBaseImpl {
	

	public void addDailyAverages(List<DailyAverage> dailyAverages) {
		for (Iterator iterator = dailyAverages.iterator(); iterator.hasNext();) {
			DailyAverage dailyAverage = (DailyAverage) iterator.next();
			dailyAverageLocalService.addDailyAverage(dailyAverage);
		}
	}
	
	@Override
	public DailyAverage updateDailyAverageStatus(long id, long userId, int status, ServiceContext serviceContext) {
		DailyAverage dailyAverage = null;
		try {
			dailyAverage = dailyAveragePersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(dailyAverage)) {
			dailyAverage.setStatus(status);
			dailyAverage.setStatusByUserId(userId);
			dailyAverage.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				dailyAverage.setStatusByUserName(user.getFullName());
				dailyAverage.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			dailyAverage = dailyAveragePersistence.update(dailyAverage);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(AccountStatement.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(AccountStatement.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return dailyAverage;
	}
	
	/**
	 * 
	 * @param reportUploadLogId
	 */
	public void deleteDailyAverageByReportUploadLogId(Long reportUploadLogId) {
		List<DailyAverage> dailyAverages =  dailyAveragePersistence.findByReportUploadLogId(reportUploadLogId);
		for(DailyAverage dailyAverage : dailyAverages) {
			dailyAverageLocalService.deleteDailyAverage(dailyAverage);
		}
	}
	Log _log = LogFactoryUtil.getLog(getClass());
}

