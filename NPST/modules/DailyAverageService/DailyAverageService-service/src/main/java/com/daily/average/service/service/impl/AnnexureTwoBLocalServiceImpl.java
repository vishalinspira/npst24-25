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

import com.daily.average.service.model.AnnexureTwoB;
import com.daily.average.service.service.base.AnnexureTwoBLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexureTwoBLocalServiceImpl
	extends AnnexureTwoBLocalServiceBaseImpl {
	
	public AnnexureTwoB addAnnexureTwoB(String file_number,String to_name, String to_address, String ended_date, String by_the_custodian, String quarter,Date createdate, long createdby, ArrayList<String> list) {
		
		AnnexureTwoB atb = annexureTwoBPersistence.create(counterLocalService.increment(AnnexureTwoB.class.getName()));
		
		atb.setFile_number(file_number);
		atb.setTo_name(to_name);
		atb.setTo_address(to_address);
		atb.setEnded_date(ended_date);
		atb.setBy_the_custodian(by_the_custodian);
		atb.setQuarter(quarter);
		
		if(!list.isEmpty() && list != null && list.size() == 26) {
			//table data
			atb.setRemarks_one(list.get(0));
			atb.setNps_trust_observation_one(list.get(1));
			atb.setRemarks_two(list.get(2));
			atb.setNps_trust_observation_two(list.get(3));
			atb.setRemarks_three(list.get(4));
			atb.setNps_trust_observation_three(list.get(5));
			atb.setRemarks_four(list.get(6));
			atb.setNps_trust_observation_four(list.get(7));
			atb.setRemarks_five(list.get(8));
			atb.setNps_trust_observation_five(list.get(9));
			atb.setRemarks_six(list.get(10));
			atb.setNps_trust_observation_six(list.get(11));
			atb.setRemarks_seven(list.get(12));
			atb.setNps_trust_observation_seven(list.get(13));
			atb.setRemarks_eight(list.get(14));
			atb.setNps_trust_observation_eight(list.get(15));
			atb.setRemarks_nine(list.get(16));
			atb.setNps_trust_observation_nine(list.get(17));
			atb.setRemarks_ten(list.get(18));
			atb.setNps_trust_observation_ten(list.get(19));
			atb.setRemarks_eleven(list.get(20));
			atb.setNps_trust_observation_eleven(list.get(21));
			atb.setRemarks_twelve(list.get(22));
			atb.setNps_trust_observation_twelve(list.get(23));
			atb.setRemarks_thirteen(list.get(24));
			atb.setNps_trust_observation_thirteen(list.get(25));
		}
		
		atb.setCreatedate(createdate);
		atb.setCreatedby(createdby);
		
		return annexureTwoBPersistence.update(atb);
	}
}