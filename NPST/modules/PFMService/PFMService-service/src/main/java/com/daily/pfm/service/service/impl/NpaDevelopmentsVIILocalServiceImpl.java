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

import com.daily.pfm.service.model.NpaDevelopmentsVII;
import com.daily.pfm.service.service.base.NpaDevelopmentsVIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsVIILocalServiceImpl
	extends NpaDevelopmentsVIILocalServiceBaseImpl {
	public void addNpaDevelopmentsVII(String nameofcompanyvii, String facevaluevii, String provisionheldprinciplevii, String provisionheldinterestvii, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsVII npaDevelopmentsVII  =npaDevelopmentsVIILocalService.createNpaDevelopmentsVII(CounterLocalServiceUtil.increment(NpaDevelopmentsVII.class.getName()));
		npaDevelopmentsVII.setNameofcompanyvii(nameofcompanyvii);
		npaDevelopmentsVII.setFacevaluevii(facevaluevii);
		npaDevelopmentsVII.setProvisionheldprinciplevii(provisionheldprinciplevii);
		npaDevelopmentsVII.setProvisionheldinterestvii(provisionheldinterestvii);
		npaDevelopmentsVII.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsVII.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsVII.setCreatedate(createdate);
		npaDevelopmentsVIILocalService.addNpaDevelopmentsVII(npaDevelopmentsVII);
	}
}