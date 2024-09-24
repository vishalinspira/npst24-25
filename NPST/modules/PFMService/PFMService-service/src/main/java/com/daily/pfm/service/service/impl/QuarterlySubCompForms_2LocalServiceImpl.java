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

import com.daily.pfm.service.model.QuarterlySubCompForms_2;
import com.daily.pfm.service.service.base.QuarterlySubCompForms_2LocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlySubCompForms_2LocalServiceImpl extends QuarterlySubCompForms_2LocalServiceBaseImpl {
	
	public void addQuarterlySubCompForms_2(List<QuarterlySubCompForms_2> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			QuarterlySubCompForms_2 form = (QuarterlySubCompForms_2) iterator.next();
			quarterlySubCompForms_2LocalService.addQuarterlySubCompForms_2(form);
		}
			
	}
	
	public void deleteQuarterlySubCompForms_2ByReportUploadLogId(Long reportUploadLogId) {
		List<QuarterlySubCompForms_2> deleteRepLog = quarterlySubCompForms_2Persistence.findByReportUploadLogId(reportUploadLogId);
		for(QuarterlySubCompForms_2 deleteReportLogs : deleteRepLog) {
			quarterlySubCompForms_2LocalService.deleteQuarterlySubCompForms_2(deleteReportLogs);
		}
	}
	
	
}