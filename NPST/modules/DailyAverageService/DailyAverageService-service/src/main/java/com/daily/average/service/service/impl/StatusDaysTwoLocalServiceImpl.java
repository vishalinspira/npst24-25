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

package com.daily.average.service.service.impl;

import com.daily.average.service.model.StatusDaysTwo;
import com.daily.average.service.service.base.StatusDaysTwoLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class StatusDaysTwoLocalServiceImpl
	extends StatusDaysTwoLocalServiceBaseImpl {
	public void addStatusDaysTwos(List<StatusDaysTwo> statusDaysTwos) {
		for(Iterator iterator = statusDaysTwos.iterator();iterator.hasNext();) {
			StatusDaysTwo statusDaysTwo =(StatusDaysTwo) iterator.next();
			statusDaysTwoLocalService.addStatusDaysTwo(statusDaysTwo);
		}
	}
}