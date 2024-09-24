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

import com.daily.pfm.service.model.QuarterlySubCompForms_4;
import com.daily.pfm.service.service.base.QuarterlySubCompForms_4LocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlySubCompForms_4LocalServiceImpl extends QuarterlySubCompForms_4LocalServiceBaseImpl {
	
	public void addQuarterlySubCompForms_4(List<QuarterlySubCompForms_4> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			QuarterlySubCompForms_4 form = (QuarterlySubCompForms_4) iterator.next();
			quarterlySubCompForms_4LocalService.addQuarterlySubCompForms_4(form);
		}
			
	}
	
	public void deleteQuarterlySubCompForms_4ByReportUploadLogId(Long reportUploadLogId) {
		List<QuarterlySubCompForms_4> deleteRepLog = quarterlySubCompForms_4Persistence.findByReportUploadLogId(reportUploadLogId);
		for(QuarterlySubCompForms_4 deleteReportLogs : deleteRepLog) {
			quarterlySubCompForms_4LocalService.deleteQuarterlySubCompForms_4(deleteReportLogs);
		}
	}
	
	
}