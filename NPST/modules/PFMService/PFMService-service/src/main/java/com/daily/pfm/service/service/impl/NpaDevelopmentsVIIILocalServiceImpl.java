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

import com.daily.pfm.service.model.NpaDevelopmentsVIII;
import com.daily.pfm.service.service.base.NpaDevelopmentsVIIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsVIIILocalServiceImpl
	extends NpaDevelopmentsVIIILocalServiceBaseImpl {
	public void addNpaDevelopmentsVIII(String nameofsecurityviii, String legalcaseviii, String currentstatusviii, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsVIII npaDevelopmentsVIII=npaDevelopmentsVIIILocalService
				.createNpaDevelopmentsVIII(CounterLocalServiceUtil.increment(NpaDevelopmentsVIII.class.getName()));
		npaDevelopmentsVIII.setNameofsecurityviii(nameofsecurityviii);
		npaDevelopmentsVIII.setLegalcaseviii(legalcaseviii);
		npaDevelopmentsVIII.setCurrentstatusviii(currentstatusviii);
		npaDevelopmentsVIII.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsVIII.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsVIII.setCreatedate(createdate);
		npaDevelopmentsVIIILocalService.addNpaDevelopmentsVIII(npaDevelopmentsVIII);
	}
}