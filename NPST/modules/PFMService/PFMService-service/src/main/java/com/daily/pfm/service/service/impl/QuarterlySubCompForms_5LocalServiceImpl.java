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

import com.daily.pfm.service.model.QuarterlySubCompForms_5;
import com.daily.pfm.service.service.base.QuarterlySubCompForms_5LocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlySubCompForms_5LocalServiceImpl extends QuarterlySubCompForms_5LocalServiceBaseImpl {
	
	public void addQuarterlySubCompForms_5(List<QuarterlySubCompForms_5> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			QuarterlySubCompForms_5 form = (QuarterlySubCompForms_5) iterator.next();
			quarterlySubCompForms_5LocalService.addQuarterlySubCompForms_5(form);
		}
			
	}
	
	public void deleteQuarterlySubCompForms_5ByReportUploadLogId(Long reportUploadLogId) {
		List<QuarterlySubCompForms_5> deleteRepLog = quarterlySubCompForms_5Persistence.findByReportUploadLogId(reportUploadLogId);
		for(QuarterlySubCompForms_5 deleteReportLogs : deleteRepLog) {
			quarterlySubCompForms_5LocalService.deleteQuarterlySubCompForms_5(deleteReportLogs);
		}
	}
	
	
}