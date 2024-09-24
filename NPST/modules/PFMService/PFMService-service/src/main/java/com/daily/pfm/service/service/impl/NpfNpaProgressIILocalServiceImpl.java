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

import com.daily.pfm.service.model.NpfNpaProgressII;
import com.daily.pfm.service.service.base.NpfNpaProgressIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpfNpaProgressIILocalServiceImpl
	extends NpfNpaProgressIILocalServiceBaseImpl {
	/**
	 * 
	 * @param nameofcompany
	 * @param bookvalue
	 * @param provisionheld
	 * @param createdby
	 * @param pfmnpaprogressid 
	 */
	public void addNpfNpaProgressII(String nameofcompany, Double bookvalue, Double provisionheld, long createdby, long pfmnpaprogressid) {
		NpfNpaProgressII npfNpaProgressII = npfNpaProgressIILocalService.createNpfNpaProgressII(CounterLocalServiceUtil.increment(NpfNpaProgressII.class.getName()));
		npfNpaProgressII.setNameofcompany(nameofcompany);
		npfNpaProgressII.setBookvalue(bookvalue);
		npfNpaProgressII.setProvisionheld(provisionheld);
		npfNpaProgressII.setPfmnpaprogressid(pfmnpaprogressid);
		Date createdate = new Date();
		npfNpaProgressII.setCreatedate(createdate );
		npfNpaProgressII.setCreatedby(createdby);
		npfNpaProgressIILocalService.addNpfNpaProgressII(npfNpaProgressII);
	}
}