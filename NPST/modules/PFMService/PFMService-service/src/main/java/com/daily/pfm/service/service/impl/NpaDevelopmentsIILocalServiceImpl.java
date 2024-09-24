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

import com.daily.pfm.service.model.NpaDevelopmentsII;
import com.daily.pfm.service.service.base.NpaDevelopmentsIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsIILocalServiceImpl
	extends NpaDevelopmentsIILocalServiceBaseImpl {
	public void addNpaDevelopmentsII(String nameofsecurityii, String legalcaseii, String currentstatusii, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsII npaDevelopmentsII = npaDevelopmentsIILocalService.createNpaDevelopmentsII(CounterLocalServiceUtil.increment(NpaDevelopmentsII.class.getName()));
		npaDevelopmentsII.setNameofsecurityii(nameofsecurityii);
		npaDevelopmentsII.setLegalcaseii(legalcaseii);
		npaDevelopmentsII.setCurrentstatusii(currentstatusii);
		npaDevelopmentsII.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsII.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsII.setCreatedate(createdate);
		npaDevelopmentsIILocalService.addNpaDevelopmentsII(npaDevelopmentsII);
	}
}