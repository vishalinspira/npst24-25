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

import com.daily.pfm.service.model.GrowthDataTwo;
import com.daily.pfm.service.service.base.GrowthDataTwoLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class GrowthDataTwoLocalServiceImpl
	extends GrowthDataTwoLocalServiceBaseImpl {
	public void addGrowthDataTwos(List<GrowthDataTwo> growthDataTwos) {
		for(Iterator iterator = growthDataTwos.iterator();iterator.hasNext();) {
			GrowthDataTwo growthDataTwo =(GrowthDataTwo) iterator.next();
			growthDataTwoLocalService.addGrowthDataTwo(growthDataTwo);
		}
	}
	
	public void deleteGrowthDataTwoByReportUploadLogId(Long reportUploadLogId) {
		List<GrowthDataTwo> deleteRepLog =  growthDataTwoPersistence.findByReportUploadLogId(reportUploadLogId);
		for(GrowthDataTwo deleteReportLogs : deleteRepLog) {
			growthDataTwoLocalService.deleteGrowthDataTwo(deleteReportLogs);
		}
	}

}