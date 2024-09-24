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

import com.daily.pfm.service.model.NpfNpaProgress;
import com.daily.pfm.service.service.base.NpfNpaProgressLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NpfNpaProgressLocalServiceImpl
	extends NpfNpaProgressLocalServiceBaseImpl {
	/**
	 * 
	 * @param pensionfundname
	 * @param npadate
	 * @param createdby
	 */
	public NpfNpaProgress addNpfNpaProgress(String pensionfundname, Date npadate, long createdby) {
		NpfNpaProgress npaProgress = npfNpaProgressLocalService.createNpfNpaProgress(CounterLocalServiceUtil.increment(NpfNpaProgress.class.getName()));
		npaProgress.setPensionfundname(pensionfundname);
		npaProgress.setNpadate(npadate);
		npaProgress.setCreatedby(createdby);
		Date createdate=new Date();
		npaProgress.setCreatedate(createdate);
		return npfNpaProgressLocalService.addNpfNpaProgress(npaProgress);
	}
}