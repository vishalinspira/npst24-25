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

import com.daily.pfm.service.model.NpaDevelopmentsXII;
import com.daily.pfm.service.service.base.NpaDevelopmentsXIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsXIILocalServiceImpl
	extends NpaDevelopmentsXIILocalServiceBaseImpl {
	public void addNpaDevelopmentsXII(String nameofsecurityxii, String legalcasexii, String currentstatusxii, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsXII npaDevelopmentsXII=npaDevelopmentsXIILocalService
				.createNpaDevelopmentsXII(CounterLocalServiceUtil.increment(NpaDevelopmentsXII.class.getName()));
		npaDevelopmentsXII.setNameofsecurityxii(nameofsecurityxii);
		npaDevelopmentsXII.setLegalcasexii(legalcasexii);
		npaDevelopmentsXII.setCurrentstatusxii(currentstatusxii);
		npaDevelopmentsXII.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsXII.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsXII.setCreatedate(createdate);
		npaDevelopmentsXIILocalService.addNpaDevelopmentsXII(npaDevelopmentsXII);
	}
}