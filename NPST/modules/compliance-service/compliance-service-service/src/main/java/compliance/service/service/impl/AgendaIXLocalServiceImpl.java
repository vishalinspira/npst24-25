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

import compliance.service.model.AgendaIX;
import compliance.service.service.base.AgendaIXLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaIXLocalServiceImpl extends AgendaIXLocalServiceBaseImpl {
	
	public AgendaIX addTable9Details(String category, String t4b_referrals, String t4b_0_7,
			String t4b_8_15, String t4b_16_31, String t4b_32_90, String t4b_91_180, 
			String t4b_181_365, String t4b_366_and_above, long createdBy, Date createDate) {
		
		AgendaIX agenda9 = agendaIXPersistence.create(counterLocalService.increment());
		
		agenda9.setCategory(category);
		agenda9.setRef_at_the_end_of_quarter(t4b_referrals);
		agenda9.set_0_7(t4b_0_7);
		agenda9.set_8_15(t4b_8_15);
		agenda9.set_16_31(t4b_16_31);
		agenda9.set_32_90(t4b_32_90);
		agenda9.set_91_180(t4b_91_180);
		agenda9.set_181_365(t4b_181_365);
		agenda9.set_366_and_above(t4b_366_and_above);
		agenda9.setCreatedby(createdBy);
		agenda9.setCreatedon(createDate);
		
		agenda9 = agendaIXPersistence.update(agenda9);
		
		return agenda9;
	}
	
	
}