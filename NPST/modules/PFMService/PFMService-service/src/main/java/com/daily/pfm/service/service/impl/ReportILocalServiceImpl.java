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

import com.daily.pfm.service.model.ReportI;
import com.daily.pfm.service.service.base.ReportILocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ReportILocalServiceImpl extends ReportILocalServiceBaseImpl {
	public void addReportis(List<ReportI> reportis) {
		for(Iterator iterator = reportis.iterator();iterator.hasNext();) {
			ReportI reporti =(ReportI) iterator.next();
			reportILocalService.addReportI(reporti);
		}
	}
	
	public void deleteReportIByReportUploadLogId(Long reportUploadLogId) {
		List<ReportI> deleteRepLog =  reportIPersistence.findByReportUploadLogId(reportUploadLogId);
		for(ReportI deleteReportLogs : deleteRepLog) {
			reportILocalService.deleteReportI(deleteReportLogs);
		}
	}
}