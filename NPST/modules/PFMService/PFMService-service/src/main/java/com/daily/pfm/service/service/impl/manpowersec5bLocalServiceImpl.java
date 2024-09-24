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


import com.daily.pfm.service.model.manpowersec5b;
import com.daily.pfm.service.service.base.manpowersec5bLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public  class manpowersec5bLocalServiceImpl
	extends manpowersec5bLocalServiceBaseImpl {
	
public  manpowersec5b addmanpowersec5b(String designation, String name, String chairperson_member, Date dateofappointmentinthecommitte, long reportUploadLogId) {
		
		
	manpowersec5b manpowersec5b = manpowersec5bPersistence.create(CounterLocalServiceUtil.increment(getModelClassName()));
			manpowersec5b.setDesignation(designation);
			manpowersec5b.setName(name);
			manpowersec5b.setChairperson_member(chairperson_member);
			manpowersec5b.setDateofappointmentinthecommitte(dateofappointmentinthecommitte);
			
			
				
				return  manpowersec5bPersistence.update(manpowersec5b);
			}


}