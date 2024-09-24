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


import com.daily.average.service.model.SrlForm;
import com.daily.average.service.service.base.SrlFormLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;


import java.util.Date;


/**
 * @author Brian Wing Shun Chan
 */
public class SrlFormLocalServiceImpl extends SrlFormLocalServiceBaseImpl {
	
	
		public SrlForm addSrlForm(String one, Date time_1, String remarks_1, String two,
									Date time_2, String remarks_2, String three, Date time_3,
									String remarks_3, String four, Date time_4, String remarks_4,
									String five, Date time_5, String remarks_5, String six,
									Date time_6, String remarks_6, String seven, Date time_7,
									String remarks_7, String eight, Date time_8, String remarks_8) {
			
		
		SrlForm srForm = srlFormPersistence.create(CounterLocalServiceUtil.increment(SrlForm.class.getName()));
		
		
		
		srForm.setOne(one);
		srForm.setTime_1(time_1);
		srForm.setRemarks_1(remarks_1);
		srForm.setTwo(two);
		srForm.setTime_2(time_2);
		srForm.setRemarks_2(remarks_2);
		srForm.setThree(three);
		srForm.setTime_3(time_3);
		srForm.setRemarks_3(remarks_3);
		srForm.setFour(four);
		srForm.setTime_4(time_4);
		srForm.setRemarks_4(remarks_4);
		srForm.setFive(five);
		srForm.setTime_5(time_5);
		srForm.setRemarks_5(remarks_5);
		srForm.setSix(six);
		srForm.setTime_6(time_6);
		srForm.setRemarks_6(remarks_6);
		srForm.setSeven(seven);
		srForm.setTime_7(time_7);
		srForm.setRemarks_7(remarks_7);
		srForm.setEight(eight);
		srForm.setTime_8(time_8);
		srForm.setRemarks_8(remarks_8);
		
		return srlFormPersistence.update(srForm);
	}

		/*@Override
		public SrlForm updateSrlFormStatus(long id, long userId, int status, ServiceContext serviceContext) {
			SrlForm srlForm =null;
			
			try {
				srlForm = srlFormPersistence.fetchByPrimaryKey(id);
			} catch (Exception e) {
				 _log.error(e);
			}
			if (Validator.isNotNull(srlForm)) {
				srlForm.setStatus(status);
				srlForm.setStatusByUserId(userId);
				srlForm.setStatusDate(new Date());

				User user;

				try {
					user = userLocalService.getUser(userId);
					srlForm.setStatusByUserName(user.getFullName());
					srlForm.setStatusByUserUuid(user.getUserUuid());
				} catch (PortalException e) {
					 _log.error(e);
				}

				srlForm = srlFormPersistence.update(srlForm);
			}
			
			try {
				if (status == WorkflowConstants.STATUS_APPROVED) {
					// Update the asset status to visibile
					assetEntryLocalService.updateEntry(ClosingBal.class.getName(), id, new Date(), null, true, true);
				} else {
					// Set applicationRegistration entity status to false
					assetEntryLocalService.updateVisible(ClosingBal.class.getName(), id, false);
				}
			} catch (Exception e) {
				 _log.error(e);
			}

			return closingBal;*/
		
		
}