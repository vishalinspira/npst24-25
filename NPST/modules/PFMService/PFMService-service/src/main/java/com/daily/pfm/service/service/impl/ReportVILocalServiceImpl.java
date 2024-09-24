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

import com.daily.pfm.service.model.ReportVI;
import com.daily.pfm.service.service.base.ReportVILocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ReportVILocalServiceImpl extends ReportVILocalServiceBaseImpl {
	public void addReportvis(List<ReportVI> reportvis) {
		for(Iterator iterator = reportvis.iterator();iterator.hasNext();) {
			ReportVI reportvi =(ReportVI) iterator.next();
			reportVILocalService.addReportVI(reportvi);
		}
	}
	
	public void deleteReportVIByReportUploadLogId(Long reportUploadLogId) {
		List<ReportVI> deleteRepLog =  reportVIPersistence.findByReportUploadLogId(reportUploadLogId);
		for(ReportVI deleteReportLogs : deleteRepLog) {
			reportVILocalService.deleteReportVI(deleteReportLogs);
		}
	}
}