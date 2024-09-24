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

import com.daily.average.service.model.NonUnanimousReportTableOne;
import com.daily.average.service.service.base.NonUnanimousReportTableOneLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NonUnanimousReportTableOneLocalServiceImpl
	extends NonUnanimousReportTableOneLocalServiceBaseImpl {
	
	public NonUnanimousReportTableOne addNonUnanimousReportTableOne(String name_of_the_pfm, long no_of_resolutions,long voted_for, long abstained, long voted_against, Date date, Long userId) {
		
		NonUnanimousReportTableOne one = nonUnanimousReportTableOnePersistence.create(CounterLocalServiceUtil.increment(NonUnanimousReportTableOne.class.getName()));
		
		one.setName_of_the_pfm(name_of_the_pfm);
		one.setNo_of_resolutions(no_of_resolutions);
		one.setVoted_against(voted_against);
		one.setVoted_for(voted_for);
		one.setAbstained(abstained);
		one.setCreatedate(date);
		one.setCreatedby(userId);
		
		return nonUnanimousReportTableOnePersistence.update(one);
	}
}