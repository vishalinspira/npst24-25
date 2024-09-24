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

import com.daily.pfm.service.model.Form3ReportByDirAndKp;
import com.daily.pfm.service.service.base.Form3ReportByDirAndKpLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class Form3ReportByDirAndKpLocalServiceImpl extends Form3ReportByDirAndKpLocalServiceBaseImpl {
	
	public void addForm3ReportByDirAndKp(List<Form3ReportByDirAndKp> form3Rep) {
		for(Iterator iterator = form3Rep.iterator();iterator.hasNext();) {
			Form3ReportByDirAndKp form3 =(Form3ReportByDirAndKp) iterator.next();
			form3ReportByDirAndKpLocalService.addForm3ReportByDirAndKp(form3);
		}
	}
	
	public void deleteForm3ReportByDirAndKpByReportUploadLogId(Long reportUploadLogId) {
		
		List<Form3ReportByDirAndKp> deleteRepLog =  form3ReportByDirAndKpPersistence.findByReportUploadLogId(reportUploadLogId);
		for(Form3ReportByDirAndKp deleteReportLogs : deleteRepLog) {
			form3ReportByDirAndKpLocalService.deleteForm3ReportByDirAndKp(deleteReportLogs);
			
		}
	}
}