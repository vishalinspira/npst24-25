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

import com.daily.pfm.service.model.QuarterlySubCompForms_3;
import com.daily.pfm.service.service.base.QuarterlySubCompForms_3LocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlySubCompForms_3LocalServiceImpl extends QuarterlySubCompForms_3LocalServiceBaseImpl {
	
	public void addQuarterlySubCompForms_3(List<QuarterlySubCompForms_3> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			QuarterlySubCompForms_3 form = (QuarterlySubCompForms_3) iterator.next();
			quarterlySubCompForms_3LocalService.addQuarterlySubCompForms_3(form);
		}
			
	}
	
	public void deleteQuarterlySubCompForms_3ByReportUploadLogId(Long reportUploadLogId) {
		List<QuarterlySubCompForms_3> deleteRepLog = quarterlySubCompForms_3Persistence.findByReportUploadLogId(reportUploadLogId);
		for(QuarterlySubCompForms_3 deleteReportLogs : deleteRepLog) {
			quarterlySubCompForms_3LocalService.deleteQuarterlySubCompForms_3(deleteReportLogs);
		}
	}
	
	
}