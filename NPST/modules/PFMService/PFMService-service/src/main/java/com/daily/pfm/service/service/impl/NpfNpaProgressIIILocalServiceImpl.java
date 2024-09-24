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

import com.daily.pfm.service.model.NpfNpaProgressIII;
import com.daily.pfm.service.service.base.NpfNpaProgressIIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpfNpaProgressIIILocalServiceImpl
	extends NpfNpaProgressIIILocalServiceBaseImpl {
	/**
	 * 
	 * @param name
	 * @param expovalue
	 * @param originalrating
	 * @param currentrating
	 * @param status
	 * @param createdby
	 * @param pfmnpaprogressid 
	 */
	public void addNpfNpaProgressIII(String name, Double expovalue, String originalrating, String currentrating, String status, long createdby, long pfmnpaprogressid) {
		NpfNpaProgressIII npfNpaProgressIII = npfNpaProgressIIILocalService.createNpfNpaProgressIII(CounterLocalServiceUtil.increment(NpfNpaProgressIII.class.getName()));
		npfNpaProgressIII.setName(name);
		npfNpaProgressIII.setExpovalue(expovalue);
		npfNpaProgressIII.setOriginalrating(originalrating);
		npfNpaProgressIII.setCurrentrating(currentrating);
		npfNpaProgressIII.setPfmnpaprogressid(pfmnpaprogressid);
		npfNpaProgressIII.setStatus(status);
		Date createdate = new Date();
		npfNpaProgressIII.setCreatedate(createdate );
		npfNpaProgressIII.setCreatedby(createdby);
		npfNpaProgressIIILocalService.addNpfNpaProgressIII(npfNpaProgressIII);
	}
}