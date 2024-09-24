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

import com.daily.pfm.service.model.manpowersec1;
import com.daily.pfm.service.service.base.manpowersec1LocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class manpowersec1LocalServiceImpl
	extends manpowersec1LocalServiceBaseImpl {
	
	public  manpowersec1 addmanpowersec1(String destination,String name, Date dateOfAppoinment, long contactNo, String email, String highestQualification, long totalProfExp, String deputation, long linkedinId, String position, Date dateOfBoardMeetngApprove, long biodata, long reportUploadLogId) {
			
			manpowersec1 manpowersec1 = manpowersec1Persistence.create(CounterLocalServiceUtil.increment(getModelClassName()));
			manpowersec1.setDestination(destination);
			manpowersec1.setName(name);
			manpowersec1.setDateOfAppoinment(dateOfAppoinment);
			manpowersec1.setContactNo(contactNo);
			manpowersec1.setEmail(email);
			manpowersec1.setHighestQualification(highestQualification);
			manpowersec1.setTotalProfExp(totalProfExp);
			manpowersec1.setDeputation(deputation);
			manpowersec1.setLinkedinId(linkedinId);
			manpowersec1.setPosition(position);
			manpowersec1.setDateOfBoardMeetngApprove(dateOfBoardMeetngApprove);
			manpowersec1.setBiodata(biodata);
			
				
				return manpowersec1Persistence.update(manpowersec1);
			}
}