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

import com.daily.pfm.service.model.NpaDevelopmentsIII;
import com.daily.pfm.service.service.base.NpaDevelopmentsIIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsIIILocalServiceImpl
	extends NpaDevelopmentsIIILocalServiceBaseImpl {
	public void addNpaDevelopmentsIII(String nameofcompanyiii, String facevalueiii, String purchasevalueiii, String bookvalueiii, String provisionheldiii, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsIII npaDevelopmentsIII=npaDevelopmentsIIILocalService
				.createNpaDevelopmentsIII(CounterLocalServiceUtil.increment(NpaDevelopmentsIII.class.getName()));
		npaDevelopmentsIII.setNameofcompanyiii(nameofcompanyiii);
		npaDevelopmentsIII.setFacevalueiii(facevalueiii);
		npaDevelopmentsIII.setPurchasevalueiii(purchasevalueiii);
		npaDevelopmentsIII.setBookvalueiii(bookvalueiii);
		npaDevelopmentsIII.setProvisionheldiii(provisionheldiii);
		npaDevelopmentsIII.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsIII.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsIII.setCreatedate(createdate);
		npaDevelopmentsIIILocalService.addNpaDevelopmentsIII(npaDevelopmentsIII);
	}
}