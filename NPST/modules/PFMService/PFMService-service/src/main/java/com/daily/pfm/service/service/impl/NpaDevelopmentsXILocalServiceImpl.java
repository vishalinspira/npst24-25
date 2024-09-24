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

import com.daily.pfm.service.model.NpaDevelopmentsXI;
import com.daily.pfm.service.service.base.NpaDevelopmentsXILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsXILocalServiceImpl
	extends NpaDevelopmentsXILocalServiceBaseImpl {
	public void addNpaDevelopmentsXI(String nameofcompanyxi, String facevaluexi, String purchasevaluexi, String provisionheldxi, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsXI npaDevelopmentsXI=npaDevelopmentsXILocalService
				.createNpaDevelopmentsXI(CounterLocalServiceUtil.increment(NpaDevelopmentsXI.class.getName()));
		npaDevelopmentsXI.setNameofcompanyxi(nameofcompanyxi);
		npaDevelopmentsXI.setFacevaluexi(facevaluexi);
		npaDevelopmentsXI.setPurchasevaluexi(purchasevaluexi);
		npaDevelopmentsXI.setProvisionheldxi(provisionheldxi);
		npaDevelopmentsXI.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsXI.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsXI.setCreatedate(createdate);
		npaDevelopmentsXILocalService.addNpaDevelopmentsXI(npaDevelopmentsXI);
	}
}