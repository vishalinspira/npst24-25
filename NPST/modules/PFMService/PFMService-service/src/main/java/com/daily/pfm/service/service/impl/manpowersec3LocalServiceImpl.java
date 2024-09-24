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

import com.daily.pfm.service.model.manpowersec3;
import com.daily.pfm.service.service.base.manpowersec3LocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class manpowersec3LocalServiceImpl
	extends manpowersec3LocalServiceBaseImpl {
	
	public  manpowersec3 addmanpowersec3(String name, String typeofdirector, String chairperson_member, String Independent_nonindependent, String directoridentificationnumber, Date dateofappointment, String highestqualification, long totalprofessionalexperience, long resume_biodata, String formMBP1ofthedirector, long reportUploadLogId) {
		
		manpowersec3 manpowersec3 = manpowersec3Persistence.create(CounterLocalServiceUtil.increment(getModelClassName()));
		
		manpowersec3.setName(name);
		manpowersec3.setTypeofdirector(typeofdirector);
		manpowersec3.setChairperson_member(chairperson_member);
		manpowersec3.setIndependent_nonindependent(Independent_nonindependent);
		manpowersec3.setDirectoridentificationnumber(directoridentificationnumber);
		manpowersec3.setDateofappointment(dateofappointment);
		manpowersec3.setHighestqualification(highestqualification);
		manpowersec3.setTotalprofessionalexperience(totalprofessionalexperience);
		manpowersec3.setResume_biodata(resume_biodata);
		manpowersec3.setFormMBP1ofthedirector(formMBP1ofthedirector);
			
			return manpowersec3Persistence.update(manpowersec3);
		}
}