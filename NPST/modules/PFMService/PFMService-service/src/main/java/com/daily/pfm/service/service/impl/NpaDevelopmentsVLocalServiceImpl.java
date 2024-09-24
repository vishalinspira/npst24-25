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

import com.daily.pfm.service.model.NpaDevelopmentsV;
import com.daily.pfm.service.service.base.NpaDevelopmentsVLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsVLocalServiceImpl
	extends NpaDevelopmentsVLocalServiceBaseImpl {
	public void addNpaDevelopmentsV(String nameofcompanyv, String bookvaluev, String provisionheldv, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsV npaDevelopmentsV=npaDevelopmentsVLocalService
				.createNpaDevelopmentsV(CounterLocalServiceUtil.increment(NpaDevelopmentsV.class.getName()));
		npaDevelopmentsV.setNameofcompanyv(nameofcompanyv);
		npaDevelopmentsV.setBookvaluev(bookvaluev);
		npaDevelopmentsV.setProvisionheldv(provisionheldv);
		npaDevelopmentsV.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsV.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsV.setCreatedate(createdate);
		npaDevelopmentsVLocalService.addNpaDevelopmentsV(npaDevelopmentsV);
	}
}