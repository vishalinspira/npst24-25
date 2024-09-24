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

import com.daily.pfm.service.model.manpowersec5a;
import com.daily.pfm.service.service.base.manpowersec5aLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class manpowersec5aLocalServiceImpl
	extends manpowersec5aLocalServiceBaseImpl {
	
public  manpowersec5a addmanpowersec5a(String designation,String name, String chairperson_member, Date dateofappointmentinthecommitte, long reportUploadLogId) {
		
		manpowersec5a manpowersec5a = manpowersec5aPersistence.create(CounterLocalServiceUtil.increment(getModelClassName()));
			manpowersec5a.setDesignation(designation);
			manpowersec5a.setName(name);
			manpowersec5a.setChairperson_member(chairperson_member);
			manpowersec5a.setDateofappointmentinthecommitte(dateofappointmentinthecommitte);
			
			
				
				return manpowersec5aPersistence.update(manpowersec5a);
			}
}