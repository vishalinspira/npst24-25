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

import com.daily.pfm.service.model.manpowersec5;
import com.daily.pfm.service.service.base.manpowersec5LocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class manpowersec5LocalServiceImpl
	extends manpowersec5LocalServiceBaseImpl {
	
	public  manpowersec5 addmanpowersec5(long reportUploadLogId, String iccstatusi, String rmccstatusi) {
		
		manpowersec5 manpowersec5 = manpowersec5Persistence.create(CounterLocalServiceUtil.increment(getModelClassName()));
			
			manpowersec5.setIccstatusi(iccstatusi);
			manpowersec5.setRmccstatusi(rmccstatusi);
		
				return manpowersec5Persistence.update(manpowersec5);
			}
}