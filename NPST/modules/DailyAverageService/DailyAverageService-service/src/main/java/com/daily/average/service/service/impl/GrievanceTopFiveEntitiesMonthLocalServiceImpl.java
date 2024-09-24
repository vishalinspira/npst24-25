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

import com.daily.average.service.model.GrievanceTopFiveEntitiesMonth;
import com.daily.average.service.service.base.GrievanceTopFiveEntitiesMonthLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class GrievanceTopFiveEntitiesMonthLocalServiceImpl extends GrievanceTopFiveEntitiesMonthLocalServiceBaseImpl {
	
	@Override
	public GrievanceTopFiveEntitiesMonth addGrievanceTopFiveEntitiesMonth(String pendingWise, String grievanceOutstanding, String ageGroupOne, String ageGroupTwo, String ageGroupThree, String ageGroupFour, String ageGroupFive, String ageGroupSix, String ageGroupSeven, String wiseType) {
		GrievanceTopFiveEntitiesMonth grievanceTopFiveEntitiesMonth = grievanceTopFiveEntitiesMonthPersistence.create(counterLocalService.increment());
		grievanceTopFiveEntitiesMonth.setPendingWise(pendingWise);
		grievanceTopFiveEntitiesMonth.setGrievanceOutstanding(grievanceOutstanding);
		grievanceTopFiveEntitiesMonth.setAgeGroupOne(ageGroupOne);
		grievanceTopFiveEntitiesMonth.setAgeGroupTwo(ageGroupTwo);
		grievanceTopFiveEntitiesMonth.setAgeGroupThree(ageGroupThree);
		grievanceTopFiveEntitiesMonth.setAgeGroupFour(ageGroupFour);
		grievanceTopFiveEntitiesMonth.setAgeGroupFive(ageGroupFive);
		grievanceTopFiveEntitiesMonth.setAgeGroupSix(ageGroupSix);
		grievanceTopFiveEntitiesMonth.setAgeGroupSeven(ageGroupSeven);
		grievanceTopFiveEntitiesMonth.setWiseType(wiseType);
		
		return grievanceTopFiveEntitiesMonthPersistence.update(grievanceTopFiveEntitiesMonth);
	}
	
}