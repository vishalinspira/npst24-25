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

import com.daily.pfm.service.model.AnnexTenaII;
import com.daily.pfm.service.service.base.AnnexTenaIILocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexTenaIILocalServiceImpl
	extends AnnexTenaIILocalServiceBaseImpl {
	public void addannexTenaIIs(List<AnnexTenaII> annexTenaIIs) {
		for(Iterator iterator = annexTenaIIs.iterator();iterator.hasNext();) {
			AnnexTenaII annexTenaII =(AnnexTenaII) iterator.next();
			annexTenaIILocalService.addAnnexTenaII(annexTenaII);
		}
	}
	
	public void deleteAnnexTenaIIByReportUploadLogId(Long reportUploadLogId) {
		List<AnnexTenaII> deleteRepLog =  annexTenaIIPersistence.findByReportUploadLogId(reportUploadLogId);
		for(AnnexTenaII deleteReportLogs : deleteRepLog) {
			annexTenaIILocalService.deleteAnnexTenaII(deleteReportLogs);
		}
	}
}