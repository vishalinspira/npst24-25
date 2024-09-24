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

import com.daily.average.service.model.Grievanceshandler;
import com.daily.average.service.service.base.GrievanceshandlerLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class GrievanceshandlerLocalServiceImpl
	extends GrievanceshandlerLocalServiceBaseImpl {
	
	public Grievanceshandler addGrievanceshandler(String fileNumber, String name, String address, String npsTrustMonthOf, String referrals, String openingBalance, String escalatedToNpst, String receivedDuringTheMonth, String reviewed, String resolvedDuringTheMonth, String outstandingEoM, Date createdate,long createdby ) {
		
		Grievanceshandler gh = grievanceshandlerPersistence.create(counterLocalService.increment(Grievanceshandler.class.getName()));
		
		gh.setFormNumber(gh.getId_());
		gh.setFileNumber(fileNumber);
		gh.setName(name);
		gh.setAddress(address);
		gh.setNpsTrustMonthOf(npsTrustMonthOf);
		gh.setReferrals(referrals);
		gh.setOpeningBalance(openingBalance);
		gh.setEscalatedToNpst(escalatedToNpst);
		gh.setReceivedDuringTheMonth(receivedDuringTheMonth);
		gh.setReferrals(referrals);
		gh.setResolvedDuringTheMonth(resolvedDuringTheMonth);
		gh.setOutstandingEoM(outstandingEoM);
		gh.setCreatedate(createdate);
		gh.setCreatedby(createdby);
		
		
		return grievanceshandlerPersistence.update(gh);
	}
}