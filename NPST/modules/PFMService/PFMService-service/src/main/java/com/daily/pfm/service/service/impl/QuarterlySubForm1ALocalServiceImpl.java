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

import com.daily.pfm.service.model.QuarterlySubForm1A;
import com.daily.pfm.service.service.base.QuarterlySubForm1ALocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;



/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlySubForm1ALocalServiceImpl
	extends QuarterlySubForm1ALocalServiceBaseImpl {
	
	public void addQuarterlySubForm1A(List<QuarterlySubForm1A> qsForm1A) {
		for(Iterator iterator = qsForm1A.iterator(); iterator.hasNext();) {
			QuarterlySubForm1A quarterlySubForm1A= (QuarterlySubForm1A) iterator.next();
			quarterlySubForm1ALocalService.addQuarterlySubForm1A(quarterlySubForm1A);
		}
	}
	
	public void deleteQuarterlyForm1AByReportUploadLogId(Long reportUploadLogId) {
		List<QuarterlySubForm1A> deleteRepLog = quarterlySubForm1APersistence.findByReportUploadLogId(reportUploadLogId);
		for(QuarterlySubForm1A deleteReportLogs : deleteRepLog) {
			quarterlySubForm1ALocalService.deleteQuarterlySubForm1A(deleteReportLogs);
		}
	}
	
}