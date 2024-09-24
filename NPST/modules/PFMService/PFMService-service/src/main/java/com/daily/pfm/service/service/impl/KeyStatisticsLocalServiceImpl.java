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

package com.daily.pfm.service.service.impl;

import com.daily.pfm.service.model.KeyStatistics;
import com.daily.pfm.service.service.base.KeyStatisticsLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KeyStatisticsLocalServiceImpl
	extends KeyStatisticsLocalServiceBaseImpl {
	
	public void addKeyStatistics(List<KeyStatistics> formList) {
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			KeyStatistics form = (KeyStatistics) iterator.next();
			keyStatisticsLocalService.addKeyStatistics(form);
		}
			
	}
	
	public void deleteKeyStatisticsByReportUploadLogId(Long reportUploadLogId) {
		List<KeyStatistics> deleteRepLog = keyStatisticsPersistence.findByReportUploadLogId(reportUploadLogId);
		for(KeyStatistics deleteReportLogs : deleteRepLog) {
			keyStatisticsLocalService.deleteKeyStatistics(deleteReportLogs);
		}
	}
}