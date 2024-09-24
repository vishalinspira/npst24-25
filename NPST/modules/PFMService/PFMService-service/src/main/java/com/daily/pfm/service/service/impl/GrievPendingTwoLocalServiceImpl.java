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

import com.daily.pfm.service.model.GrievPendingTwo;
import com.daily.pfm.service.service.base.GrievPendingTwoLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class GrievPendingTwoLocalServiceImpl
	extends GrievPendingTwoLocalServiceBaseImpl {
	public void addGrievPendingTwos(List<GrievPendingTwo> grievPendingTwos) {
		for(Iterator iterator = grievPendingTwos.iterator();iterator.hasNext();) {
			GrievPendingTwo grievPendingTwo =(GrievPendingTwo) iterator.next();
			grievPendingTwoLocalService.addGrievPendingTwo(grievPendingTwo);
		}
	}
	
	public void deleteGrievPendingTwoByReportUploadLogId(Long reportUploadLogId) {
		List<GrievPendingTwo> deleteRepLog =  grievPendingTwoPersistence.findByReportUploadLogId(reportUploadLogId);
		for(GrievPendingTwo deleteReportLogs : deleteRepLog) {
			grievPendingTwoLocalService.deleteGrievPendingTwo(deleteReportLogs);
		}
	}


}