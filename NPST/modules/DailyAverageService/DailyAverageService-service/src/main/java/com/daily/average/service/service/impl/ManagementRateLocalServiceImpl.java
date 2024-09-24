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

import com.daily.average.service.model.ManagementRate;

import com.daily.average.service.service.base.ManagementRateLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ManagementRateLocalServiceImpl
	extends ManagementRateLocalServiceBaseImpl {
	
	public void addManagementRates(List<ManagementRate> managementRates) {
		for(Iterator iterator = managementRates.iterator();iterator.hasNext();) {
			ManagementRate managementRate =(ManagementRate) iterator.next();
			managementRateLocalService.addManagementRate(managementRate);
		}
	}
	public void deleteManagementRateByReportUploadLogId(Long reportUploadLogId) {
		  List<ManagementRate> managementRates =
				  managementRatePersistence.findByReportUploadLogId(reportUploadLogId);
		  for(ManagementRate managementRate : managementRates) {
			  managementRateLocalService.deleteManagementRate(managementRate); } }

}