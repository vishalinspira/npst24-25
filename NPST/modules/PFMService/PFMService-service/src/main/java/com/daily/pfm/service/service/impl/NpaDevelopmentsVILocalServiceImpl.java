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

import com.daily.pfm.service.model.NpaDevelopmentsVI;
import com.daily.pfm.service.service.base.NpaDevelopmentsVILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsVILocalServiceImpl
	extends NpaDevelopmentsVILocalServiceBaseImpl {
	public void addNpaDevelopmentsVI(String namevi, String exposurevi, String originalratingvi, String currentratingvi, String statusvi, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsVI npaDevelopmentsVI=npaDevelopmentsVILocalService
				.createNpaDevelopmentsVI(CounterLocalServiceUtil.increment(NpaDevelopmentsVI.class.getName()));
		npaDevelopmentsVI.setNamevi(namevi);
		npaDevelopmentsVI.setExposurevi(exposurevi);
		npaDevelopmentsVI.setOriginalratingvi(originalratingvi);
		npaDevelopmentsVI.setCurrentratingvi(currentratingvi);
		npaDevelopmentsVI.setStatusvi(statusvi);
		npaDevelopmentsVI.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsVI.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsVI.setCreatedate(createdate);
		npaDevelopmentsVILocalService.addNpaDevelopmentsVI(npaDevelopmentsVI);
	}
}