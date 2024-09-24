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

import com.daily.pfm.service.model.ACSummeryX;
import com.daily.pfm.service.service.base.ACSummeryXLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ACSummeryXLocalServiceImpl extends ACSummeryXLocalServiceBaseImpl {
	public void addACSummeryX(List<ACSummeryX> annexten) {
		for(Iterator iterator = annexten.iterator();iterator.hasNext();) {
			ACSummeryX acsummeryx =(ACSummeryX) iterator.next();
			acSummeryXLocalService.addACSummeryX(acsummeryx);
		}
	}
	
	public void deleteACSummeryXByReportUploadLogId(Long reportUploadLogId) {
		List<ACSummeryX> deleteRepLog =  acSummeryXPersistence.findByReportUploadLogId(reportUploadLogId);
		for(ACSummeryX deleteReportLogs : deleteRepLog) {
			acSummeryXLocalService.deleteACSummeryX(deleteReportLogs);
		}
	}
}