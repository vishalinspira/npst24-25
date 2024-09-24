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

import com.daily.pfm.service.model.NpaDevelopmentsIV;
import com.daily.pfm.service.service.base.NpaDevelopmentsIVLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsIVLocalServiceImpl
	extends NpaDevelopmentsIVLocalServiceBaseImpl {
	public void addNpaDevelopmentsIV(String nameofsecurityiv, String legalcaseiv, String currentstatusiv, long createdby, long npadevelopmentsiid) {
		NpaDevelopmentsIV npaDevelopmentsIV = npaDevelopmentsIVLocalService
				.createNpaDevelopmentsIV(CounterLocalServiceUtil.increment(NpaDevelopmentsIV.class.getName()));
		npaDevelopmentsIV.setNameofsecurityiv(nameofsecurityiv);
		npaDevelopmentsIV.setLegalcaseiv(legalcaseiv);
		npaDevelopmentsIV.setCurrentstatusiv(currentstatusiv);
		npaDevelopmentsIV.setNpadevelopmentsiid(npadevelopmentsiid);
		npaDevelopmentsIV.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsIV.setCreatedate(createdate);
		npaDevelopmentsIVLocalService.addNpaDevelopmentsIV(npaDevelopmentsIV);
	}
}