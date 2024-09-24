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

import com.daily.average.service.model.AnnexureOneBTableOne;
import com.daily.average.service.service.base.AnnexureOneBTableOneLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexureOneBTableOneLocalServiceImpl
	extends AnnexureOneBTableOneLocalServiceBaseImpl {
	
	public AnnexureOneBTableOne addAnnexureOneBTableOne(String name_of_pension_fund, String auc_as_per_market_valuation, String aum, String aum_under_coustody, String asset_not_under_coustody, Date date, long userid, Date as_on_date) {
		
		AnnexureOneBTableOne aob = annexureOneBTableOnePersistence.create(counterLocalService.increment(AnnexureOneBTableOne.class.getName()));
		
		aob.setName_of_pension_fund(name_of_pension_fund);
		aob.setAuc_as_per_market_valuation(auc_as_per_market_valuation);
		aob.setAum(aum);
		aob.setAum_under_coustody(aum_under_coustody);
		aob.setAsset_not_under_coustody(asset_not_under_coustody);
		aob.setAs_on_date(as_on_date);
		aob.setCreatedate(date);
		aob.setCreatedby(userid);
		
		return annexureOneBTableOnePersistence.update(aob);
		
	}
}