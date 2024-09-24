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

import com.daily.average.service.model.GrievanceTopFivestatewise;
import com.daily.average.service.service.base.GrievanceTopFivestatewiseLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class GrievanceTopFivestatewiseLocalServiceImpl
	extends GrievanceTopFivestatewiseLocalServiceBaseImpl {
	
	public void addGrievanceTopFivestatewise(List<GrievanceTopFivestatewise> list) {
		for(Iterator<GrievanceTopFivestatewise> iterator = list.iterator(); iterator.hasNext();) {
			GrievanceTopFivestatewise grievancestate = (GrievanceTopFivestatewise)iterator.next();
			grievanceTopFivestatewiseLocalService.addGrievanceTopFivestatewise(grievancestate);
		}
	}
	
	public void deleteGrievanceTopFivestatewiseByReportUploadLogId(Long reportUploadLogId) {
		List<GrievanceTopFivestatewise> deleteRepLog =  grievanceTopFivestatewisePersistence.findByReportUploadLogId(reportUploadLogId);
		for(GrievanceTopFivestatewise deleteReportLogs : deleteRepLog) {
			grievanceTopFivestatewiseLocalService.deleteGrievanceTopFivestatewise(deleteReportLogs);
		}
	}

}