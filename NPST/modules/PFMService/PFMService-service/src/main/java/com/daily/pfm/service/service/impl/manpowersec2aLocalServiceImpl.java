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

import com.daily.pfm.service.model.manpowersec2a;
import com.daily.pfm.service.service.base.manpowersec2aLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class manpowersec2aLocalServiceImpl
	extends manpowersec2aLocalServiceBaseImpl {
	
	public  manpowersec2a addmanpowersec2a(String designation,String name, String chairperson_member, Date dateofappointmentinthecommitte, long reportUploadLogId) {
		
		manpowersec2a manpowersec2a = manpowersec2aPersistence.create(CounterLocalServiceUtil.increment(getModelClassName()));
			manpowersec2a.setDesignation(designation);
			manpowersec2a.setName(name);
			manpowersec2a.setChairperson_member(chairperson_member);
			manpowersec2a.setDateofappointmentinthecommitte(dateofappointmentinthecommitte);
			
			
				
				return manpowersec2aPersistence.update(manpowersec2a);
			}
}