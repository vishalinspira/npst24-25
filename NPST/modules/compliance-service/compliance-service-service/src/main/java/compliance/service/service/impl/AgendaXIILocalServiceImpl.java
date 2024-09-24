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

package compliance.service.service.impl;

import java.util.Date;

import compliance.service.model.AgendaXII;
import compliance.service.service.base.AgendaXIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaXIILocalServiceImpl extends AgendaXIILocalServiceBaseImpl {
	
	public AgendaXII addTable12Details(String date_5, String numberOfGriev_1, String numberOfGriev_2,
			String numberOfGriev_3, String numberOfGriev_4, String numberOfGriev_5, 
			String numberOfGriev_6, String grievances_1, String grievances_2,
			String grievances_3, String grievances_4, String grievances_5,
			String grievances_6, String grievances_7, String grievances_8, 
			String grievances_9, String grievances_10, String grievances_11,
			String grievances_12, String grievances_13, String grievances_14,
			String grievances_15, String grievances_16, String grievances_17,
			String grievances_18, String grievances_19, String grievances_20,
			String grievances_21, String grievances_22, String grievances_23,
			String grievances_24, String grievances_25, String grievances_26,
			String grievances_27, String grievances_28, String grievances_29,
			String grievances_30, String activity, String grievances_31,
			String grievances_32, String pfrda_policy, String date_6,
			Date date_7, long createdBy, Date createDate) {
		
		AgendaXII agenda12 = agendaXIIPersistence.create(counterLocalService.increment());
		
		agenda12.setDate_5(date_5);
		agenda12.setNumber_of_griev_1(numberOfGriev_1);
		agenda12.setNumber_of_griev_2(numberOfGriev_2);
		agenda12.setNumber_of_griev_3(numberOfGriev_3);
		agenda12.setNumber_of_griev_4(numberOfGriev_4);
		agenda12.setNumber_of_griev_5(numberOfGriev_5);
		agenda12.setNumber_of_griev_6(numberOfGriev_6);
		
		agenda12.setGrievances_1(grievances_1);
		agenda12.setGrievances_2(grievances_2);
		agenda12.setGrievances_3(grievances_3);
		agenda12.setGrievances_4(grievances_4);
		agenda12.setGrievances_5(grievances_5);
		agenda12.setGrievances_6(grievances_6);
		agenda12.setGrievances_7(grievances_7);
		agenda12.setGrievances_8(grievances_8);
		agenda12.setGrievances_9(grievances_9);
		agenda12.setGrievances_10(grievances_10);
		
		agenda12.setGrievances_11(grievances_11);
		agenda12.setGrievances_12(grievances_12);
		agenda12.setGrievances_13(grievances_13);
		agenda12.setGrievances_14(grievances_14);
		agenda12.setGrievances_15(grievances_15);
		agenda12.setGrievances_16(grievances_16);
		agenda12.setGrievances_17(grievances_17);
		agenda12.setGrievances_18(grievances_18);
		agenda12.setGrievances_19(grievances_19);
		agenda12.setGrievances_20(grievances_20);
		
		agenda12.setGrievances_21(grievances_21);
		agenda12.setGrievances_22(grievances_22);
		agenda12.setGrievances_23(grievances_23);
		agenda12.setGrievances_24(grievances_24);
		agenda12.setGrievances_25(grievances_25);
		agenda12.setGrievances_26(grievances_26);
		agenda12.setGrievances_27(grievances_27);
		agenda12.setGrievances_28(grievances_28);
		agenda12.setGrievances_29(grievances_29);
		agenda12.setGrievances_30(grievances_30);
		
		agenda12.setActivity(activity);
		agenda12.setGrievances_31(grievances_31);
		agenda12.setGrievances_32(grievances_32);
		agenda12.setPfrda_policy(pfrda_policy);
		agenda12.setDate_6(date_6);
		agenda12.setDate_7(date_7);
		
		agenda12.setCreatedby(createdBy);
		agenda12.setCreatedon(createDate);
		
		agenda12 = agendaXIIPersistence.update(agenda12);
		
		return agenda12;
	}
	
	
}