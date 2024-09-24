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

import com.daily.pfm.service.model.NpaDevelopmentsX;
import com.daily.pfm.service.service.base.NpaDevelopmentsXLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsXLocalServiceImpl
	extends NpaDevelopmentsXLocalServiceBaseImpl {
	public void addNpaDevelopmentsX(String nameofsecurityx, String legalcasex, String currentstatusx, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsX npaDevelopmentsX=npaDevelopmentsXLocalService
				.createNpaDevelopmentsX(CounterLocalServiceUtil.increment(NpaDevelopmentsX.class.getName()));
		npaDevelopmentsX.setNameofsecurityx(nameofsecurityx);
		npaDevelopmentsX.setLegalcasex(legalcasex);
		npaDevelopmentsX.setCurrentstatusx(currentstatusx);
		npaDevelopmentsX.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsX.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsX.setCreatedate(createdate);
		npaDevelopmentsXLocalService.addNpaDevelopmentsX(npaDevelopmentsX);
	}
}