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

import com.daily.pfm.service.model.AnnexTenaSummery;
import com.daily.pfm.service.service.base.AnnexTenaSummeryLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexTenaSummeryLocalServiceImpl
	extends AnnexTenaSummeryLocalServiceBaseImpl {
	public void addAnnexTenaSummery(List<AnnexTenaSummery> annexTenas) {
		for(Iterator iterator = annexTenas.iterator();iterator.hasNext();) {
			AnnexTenaSummery annexuretensummery =(AnnexTenaSummery) iterator.next();
			annexTenaSummeryLocalService.addAnnexTenaSummery(annexuretensummery);
		}
	}
	public void deleteAnnexTenaSummeryByReportUploadLogId(Long reportUploadLogId) {
		List<AnnexTenaSummery> deleteRepLog = annexTenaSummeryPersistence.findByReportUploadLogId(reportUploadLogId);
		for(AnnexTenaSummery deleteReportLogs : deleteRepLog) {
			annexTenaSummeryLocalService.deleteAnnexTenaSummery(deleteReportLogs);
		}
	}
}