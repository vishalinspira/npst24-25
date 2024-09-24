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

import compliance.service.model.AnnexureC;
import compliance.service.service.base.AnnexureCLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexureCLocalServiceImpl extends AnnexureCLocalServiceBaseImpl {
	
	public AnnexureC addAnnexureC(String subscriber_class,String mar_12, String mar_20,String mar_21,String sep_20,String sep_21,String annual_yoy_mar20_numbers,String annual_yoy_mar20_percent,
			String yoy_same_month_numbers,String yoy_same_month_percent,String growth_over_mar21_numbers,String growth_over_mar21_percent, Date createdon, long createdby) {
		
		AnnexureC ac = annexureCPersistence.create(counterLocalService.increment(AnnexureC.class.getName()));
		ac.setSubscriber_class(subscriber_class);
		ac.setMar_12(mar_12);
		ac.setMar_20(mar_20);
		ac.setMar_21(mar_21);
		ac.setSep_20(sep_20);
		ac.setSep_21(sep_21);
		ac.setAnnual_yoy_mar20_numbers(annual_yoy_mar20_numbers);
		ac.setAnnual_yoy_mar20_percent(annual_yoy_mar20_percent);
		ac.setYoy_same_month_numbers(yoy_same_month_numbers);
		ac.setYoy_same_month_percent(yoy_same_month_percent);
		ac.setGrowth_over_mar21_numbers(growth_over_mar21_numbers);
		ac.setGrowth_over_mar21_percent(growth_over_mar21_percent);
		ac.setCreatedon(createdon);
		ac.setCreatedby(createdby);
		
		return annexureCPersistence.update(ac);
		}

}