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

import com.daily.average.service.model.NonUnanimousReportTableTwo;
import com.daily.average.service.service.base.NonUnanimousReportTableTwoLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class NonUnanimousReportTableTwoLocalServiceImpl
	extends NonUnanimousReportTableTwoLocalServiceBaseImpl {
	
	public NonUnanimousReportTableTwo addNonUnanimousReportTableTwo(String name_of_the_company,Date meeting_date,String resolution,String ses_recommendation,String ses_reason,String pfs_voted_for,String pfs_voted_abstain,String pfs_voted_against ,String final_vote,String pfs_reason, Date date, Long userId) {
		
		NonUnanimousReportTableTwo two = nonUnanimousReportTableTwoPersistence.create(CounterLocalServiceUtil.increment(NonUnanimousReportTableTwo.class.getName()));
		
		two.setName_of_the_company(name_of_the_company);
		two.setMeeting_date(meeting_date);
		two.setResolution(resolution);
		two.setSes_recommendation(ses_recommendation);
		two.setSes_reason(ses_reason);
		two.setPfs_voted_for(pfs_voted_for);
		two.setPfs_voted_abstain(pfs_voted_abstain);
		two.setPfs_voted_against(pfs_voted_against);
		two.setFinal_vote(final_vote);
		two.setPfs_reason(pfs_reason);
		two.setCreatedate(date);
		two.setCreatedby(userId);
		
		return nonUnanimousReportTableTwoPersistence.update(two);
	}
}