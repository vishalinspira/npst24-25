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

import com.daily.pfm.service.model.QuarterlySubCompForms;
import com.daily.pfm.service.service.base.QuarterlySubCompFormsLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlySubCompFormsLocalServiceImpl
	extends QuarterlySubCompFormsLocalServiceBaseImpl {
	
	public void addQuarterlySubCompForms(List<QuarterlySubCompForms> qrSubCompForms) {
		for(Iterator iterator = qrSubCompForms.iterator(); iterator.hasNext();) {
			QuarterlySubCompForms quarterlySubCompForms= (QuarterlySubCompForms) iterator.next();
			quarterlySubCompFormsLocalService.addQuarterlySubCompForms(quarterlySubCompForms);
		}
	}
	
	public void deleteQuarterlySubCompFormsByReportUploadLogId(Long reportUploadLogId) {
		List<QuarterlySubCompForms> deleteRepLog = quarterlySubCompFormsPersistence.findByReportUploadLogId(reportUploadLogId);
		for(QuarterlySubCompForms deleteReportLogs : deleteRepLog) {
			quarterlySubCompFormsLocalService.deleteQuarterlySubCompForms(deleteReportLogs);
		}
	}
	
	
}