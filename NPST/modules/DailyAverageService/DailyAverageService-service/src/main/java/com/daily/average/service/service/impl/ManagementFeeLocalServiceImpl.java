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

import com.daily.average.service.model.ManagementFee;

import com.daily.average.service.service.base.ManagementFeeLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ManagementFeeLocalServiceImpl
	extends ManagementFeeLocalServiceBaseImpl {
	
	public void addManagementFees(List<ManagementFee> managementFees) {
		for(Iterator iterator = managementFees.iterator();iterator.hasNext();) {
			ManagementFee managementFee =(ManagementFee) iterator.next();
			managementFeeLocalService.addManagementFee(managementFee);
		}
	}
	public void deleteManagementFeeByReportUploadLogId(Long reportUploadLogId) {
		  List<ManagementFee> managementFees =
				  managementFeePersistence.findByReportUploadLogId(reportUploadLogId);
		  for(ManagementFee managementFee : managementFees) {
			  managementFeeLocalService.deleteManagementFee(managementFee); } }
}