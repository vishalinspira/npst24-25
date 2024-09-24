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

import com.daily.pfm.service.model.NpsTrustFeePfmOne;
import com.daily.pfm.service.service.base.NpsTrustFeePfmOneLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class NpsTrustFeePfmOneLocalServiceImpl
	extends NpsTrustFeePfmOneLocalServiceBaseImpl {
	public void addNpsTrustFeePfmOnes(List<NpsTrustFeePfmOne> npsTrustFeePfmOnes) {
		for(Iterator iterator = npsTrustFeePfmOnes.iterator();iterator.hasNext();) {
			NpsTrustFeePfmOne npsTrustFeePfmOne =(NpsTrustFeePfmOne) iterator.next();
			npsTrustFeePfmOneLocalService.addNpsTrustFeePfmOne(npsTrustFeePfmOne);
		}
	}
	public void deleteNpsTrustFeePfmOneByReportUploadLogId(Long reportUploadLogId) {
		List<NpsTrustFeePfmOne> deleteRepLog =  npsTrustFeePfmOnePersistence.findByReportUploadLogId(reportUploadLogId);
		for(NpsTrustFeePfmOne deleteReportLogs : deleteRepLog) {
			npsTrustFeePfmOneLocalService.deleteNpsTrustFeePfmOne(deleteReportLogs);
	}

	}
	
	
}