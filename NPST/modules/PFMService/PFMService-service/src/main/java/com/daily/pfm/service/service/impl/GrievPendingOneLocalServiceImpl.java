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

import com.daily.pfm.service.model.GrievPendingOne;
import com.daily.pfm.service.service.base.GrievPendingOneLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class GrievPendingOneLocalServiceImpl
	extends GrievPendingOneLocalServiceBaseImpl {
	public void addGrievPendingOnes(List<GrievPendingOne> grievPendingOnes) {
		for(Iterator iterator = grievPendingOnes.iterator();iterator.hasNext();) {
			GrievPendingOne grievPendingOne =(GrievPendingOne) iterator.next();
			grievPendingOneLocalService.addGrievPendingOne(grievPendingOne);
		}
	}
	
	public void deleteGrievPendingOneByReportUploadLogId(Long reportUploadLogId) {
		List<GrievPendingOne> deleteRepLog =  grievPendingOnePersistence.findByReportUploadLogId(reportUploadLogId);
		for(GrievPendingOne deleteReportLogs : deleteRepLog) {
			grievPendingOneLocalService.deleteGrievPendingOne(deleteReportLogs);
		}
	}


}