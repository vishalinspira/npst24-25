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

import com.daily.average.service.model.GrievanceTopFiveMinistrywise;
import com.daily.average.service.service.base.GrievanceTopFiveMinistrywiseLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class GrievanceTopFiveMinistrywiseLocalServiceImpl
	extends GrievanceTopFiveMinistrywiseLocalServiceBaseImpl {
	
	public void addGrievanceTopFiveMinistrywise(List<GrievanceTopFiveMinistrywise> list) {
		for(Iterator<GrievanceTopFiveMinistrywise> iterator = list.iterator(); iterator.hasNext();) {
			GrievanceTopFiveMinistrywise grievanceMinistrywise = (GrievanceTopFiveMinistrywise)iterator.next();
			grievanceTopFiveMinistrywiseLocalService.addGrievanceTopFiveMinistrywise(grievanceMinistrywise);
		}
	}
	
	public void deleteGrievanceTopFiveMinistrywiseByReportUploadLogId(Long reportUploadLogId) {
		List<GrievanceTopFiveMinistrywise> deleteRepLog =  grievanceTopFiveMinistrywisePersistence.findByReportUploadLogId(reportUploadLogId);
		for(GrievanceTopFiveMinistrywise deleteReportLogs : deleteRepLog) {
			grievanceTopFiveMinistrywiseLocalService.deleteGrievanceTopFiveMinistrywise(deleteReportLogs);
		}
	}

}