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

import com.daily.pfm.service.model.Form3RepByDirAndKP;
import com.daily.pfm.service.service.base.Form3RepByDirAndKPLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class Form3RepByDirAndKPLocalServiceImpl
	extends Form3RepByDirAndKPLocalServiceBaseImpl {
	
	public void addForm3RepByDirAndKP(List<Form3RepByDirAndKP> f3RepByDirAndKP) {
		for(Iterator iterator = f3RepByDirAndKP.iterator(); iterator.hasNext();) {
			Form3RepByDirAndKP form3RepByDirAndKP= (Form3RepByDirAndKP) iterator.next();
			form3RepByDirAndKPLocalService.addForm3RepByDirAndKP(form3RepByDirAndKP);
		}
	}
	
	public void deleteForm3RepByDirAndKPByReportUploadLogId(Long reportUploadLogId) {
		List<Form3RepByDirAndKP> deleteRepLog = form3RepByDirAndKPPersistence.findByReportUploadLogId(reportUploadLogId);
		for(Form3RepByDirAndKP deleteReportLogs : deleteRepLog) {
			form3RepByDirAndKPLocalService.deleteForm3RepByDirAndKP(deleteReportLogs);
		}
	}
}