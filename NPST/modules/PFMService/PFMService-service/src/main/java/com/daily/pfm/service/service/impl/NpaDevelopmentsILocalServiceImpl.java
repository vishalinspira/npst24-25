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

import com.daily.pfm.service.model.NpaDevelopmentsI;
import com.daily.pfm.service.service.base.NpaDevelopmentsILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpaDevelopmentsILocalServiceImpl
	extends NpaDevelopmentsILocalServiceBaseImpl {
	/**
	 * 
	 * @param nameofcompanyi
	 * @param facevaluei
	 * @param purchasevaluei
	 * @param provisionheldi
	 * @param createdby
	 * @return
	 */
	public NpaDevelopmentsI addNpaDevelopmentsI(String nameofcompanyi, String facevaluei, String purchasevaluei, String provisionheldi, long createdby) {
		NpaDevelopmentsI npaDevelopmentsI = npaDevelopmentsILocalService.createNpaDevelopmentsI(CounterLocalServiceUtil.increment(NpaDevelopmentsI.class.getName()));
		npaDevelopmentsI.setNameofcompanyi(nameofcompanyi);
		npaDevelopmentsI.setFacevaluei(facevaluei);
		npaDevelopmentsI.setPurchasevaluei(purchasevaluei);
		npaDevelopmentsI.setProvisionheldi(provisionheldi);
		npaDevelopmentsI.setCreatedby(createdby);
		Date createdate=new Date();
		npaDevelopmentsI.setCreatedate(createdate);
		
		return npaDevelopmentsILocalService.addNpaDevelopmentsI(npaDevelopmentsI);
	}
}