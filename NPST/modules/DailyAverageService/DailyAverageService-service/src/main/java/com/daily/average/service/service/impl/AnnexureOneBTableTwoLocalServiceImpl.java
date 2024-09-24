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

import com.daily.average.service.model.AnnexureOneBTableTwo;
import com.daily.average.service.service.base.AnnexureOneBTableTwoLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexureOneBTableTwoLocalServiceImpl
	extends AnnexureOneBTableTwoLocalServiceBaseImpl {
	
public AnnexureOneBTableTwo addAnnexureOneBTableTwo(String particulars, String amount_in_crores, Date date, long userid, Date as_on_date) {
		
	AnnexureOneBTableTwo aob = annexureOneBTableTwoPersistence.create(counterLocalService.increment(AnnexureOneBTableTwo.class.getName()));
	
	aob.setParticulars(particulars);
	aob.setAmount_in_crores(amount_in_crores);
	aob.setAs_on_date(as_on_date);
	aob.setCreatedate(date);
	aob.setCreatedby(userid);
	
	return annexureOneBTableTwoPersistence.update(aob);
		
	}
}
