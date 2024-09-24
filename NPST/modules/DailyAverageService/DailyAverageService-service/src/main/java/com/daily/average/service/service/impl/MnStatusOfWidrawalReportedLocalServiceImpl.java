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

package com.daily.average.service.service.impl;

import com.daily.average.service.model.MnStatusOfWidrawalReported;
import com.daily.average.service.service.base.MnStatusOfWidrawalReportedLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MnStatusOfWidrawalReportedLocalServiceImpl
	extends MnStatusOfWidrawalReportedLocalServiceBaseImpl {
	
	public void addMnStatusOfWidrawalReported(List<MnStatusOfWidrawalReported> form1List) {
		for(Iterator iterator = form1List.iterator(); iterator.hasNext();) {
			MnStatusOfWidrawalReported form = (MnStatusOfWidrawalReported) iterator.next();
			mnStatusOfWidrawalReportedLocalService.addMnStatusOfWidrawalReported(form);
		}
	}
	
	public void deleteMnStatusOfWidrawalReportedByReportUploadLogId(Long reportUploadLogId) {
		List<MnStatusOfWidrawalReported> deleteRepLog =  mnStatusOfWidrawalReportedPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnStatusOfWidrawalReported deleteReportLogs : deleteRepLog) {
			mnStatusOfWidrawalReportedLocalService.deleteMnStatusOfWidrawalReported(deleteReportLogs);
		}
	}


}