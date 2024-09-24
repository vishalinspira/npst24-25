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

import com.daily.average.service.model.Rejectionandreturn;
import com.daily.average.service.service.base.RejectionandreturnLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RejectionandreturnLocalServiceImpl
	extends RejectionandreturnLocalServiceBaseImpl {
	public void addRejectionandreturns(List<Rejectionandreturn> rejectionandreturns) {
		for (Iterator iterator = rejectionandreturns.iterator(); iterator.hasNext();) {
			Rejectionandreturn rejectionandreturn = (Rejectionandreturn) iterator.next();
			rejectionandreturnLocalService.addRejectionandreturn(rejectionandreturn);
		}
	}
	
	public void deleteRejectionandreturnByReportUploadLogId(Long reportUploadLogId) {
		List<Rejectionandreturn> rejectionandreturns = rejectionandreturnPersistence.findByReportUploadLogId(reportUploadLogId);
		for (Rejectionandreturn rejectionandreturn : rejectionandreturns) {
			rejectionandreturnLocalService.deleteRejectionandreturn(rejectionandreturn);
		}
	}
}