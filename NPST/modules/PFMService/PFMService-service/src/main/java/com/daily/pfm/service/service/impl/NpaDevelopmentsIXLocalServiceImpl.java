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

import com.daily.pfm.service.model.NpaDevelopmentsIX;
import com.daily.pfm.service.service.base.NpaDevelopmentsIXLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsIXLocalServiceImpl
	extends NpaDevelopmentsIXLocalServiceBaseImpl {
	public void addNpaDevelopmentsIX(String nameofcompanyix, String facevalueix, String purchasevalueix, String provisionheldix, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsIX npaDevelopmentsIX=npaDevelopmentsIXLocalService
				.createNpaDevelopmentsIX(CounterLocalServiceUtil.increment(NpaDevelopmentsIX.class.getName()));
		npaDevelopmentsIX.setNameofcompanyix(nameofcompanyix);
		npaDevelopmentsIX.setFacevalueix(facevalueix);
		npaDevelopmentsIX.setPurchasevalueix(purchasevalueix);
		npaDevelopmentsIX.setProvisionheldix(provisionheldix);
		npaDevelopmentsIX.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsIX.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsIX.setCreatedate(createdate);
		npaDevelopmentsIXLocalService.addNpaDevelopmentsIX(npaDevelopmentsIX);
	}
}