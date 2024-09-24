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

import com.daily.pfm.service.model.manpowersec2b;
import com.daily.pfm.service.service.base.manpowersec2bLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class manpowersec2bLocalServiceImpl
	extends manpowersec2bLocalServiceBaseImpl {
	
public  manpowersec2b addmanpowersec2b(String designation,String name, String chairperson_member, Date dateofappointmentinthecommitte, long reportUploadLogId) {
		
		manpowersec2b manpowersec2b = manpowersec2bPersistence.create(CounterLocalServiceUtil.increment(getModelClassName()));
			manpowersec2b.setDesignation(designation);
			manpowersec2b.setName(name);
			manpowersec2b.setChairperson_member(chairperson_member);
			manpowersec2b.setDateofappointmentinthecommitte(dateofappointmentinthecommitte);
			
			
				
				return manpowersec2bPersistence.update(manpowersec2b);
			}
}