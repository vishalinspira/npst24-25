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

import com.daily.pfm.service.model.Asp_report;
import com.daily.pfm.service.service.base.Asp_reportLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class Asp_reportLocalServiceImpl extends Asp_reportLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public void addAsp_reports(List<Asp_report> asp_reports) {
		for(Iterator iterator = asp_reports.iterator();iterator.hasNext();) {
			Asp_report asp_report =(Asp_report) iterator.next();
			_log.info("Asp_report"+ asp_report);
			asp_reportLocalService.addAsp_report(asp_report);
		}
	}
	
	public void deleteAsp_reportByReportUploadLogId(Long reportUploadLogId) {
		List<Asp_report> deleteRepLog =  asp_reportPersistence.findByReportUploadLogId(reportUploadLogId);
		for(Asp_report deleteReportLogs : deleteRepLog) {
			asp_reportLocalService.deleteAsp_report(deleteReportLogs);
		}
	}
}