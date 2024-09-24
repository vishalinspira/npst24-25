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

import compliance.service.model.AgendaX;
import compliance.service.service.base.AgendaXLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaXLocalServiceImpl extends AgendaXLocalServiceBaseImpl {
	
	public AgendaX addTable8Details(String entity, String t4c_referrals, String t4c_0_7,
			String t4c_8_15, String t4c_16_31, String t4c_32_90, String t4c_91_180, 
			String t4c_181_365, String t4c_366_and_above, long createdBy, Date createDate) {
		
		AgendaX agenda10 = agendaXPersistence.create(counterLocalService.increment());
		
		agenda10.setEntity(entity);
		agenda10.setRef_at_the_end_of_quarter(t4c_referrals);
		agenda10.set_0_7(t4c_0_7);
		agenda10.set_8_15(t4c_8_15);
		agenda10.set_16_31(t4c_16_31);
		agenda10.set_32_90(t4c_32_90);
		agenda10.set_91_180(t4c_91_180);
		agenda10.set_181_365(t4c_181_365);
		agenda10.set_366_and_above(t4c_366_and_above);
		agenda10.setCreatedby(createdBy);
		agenda10.setCreatedon(createDate);
		
		agenda10 = agendaXPersistence.update(agenda10);
		
		return agenda10;
	}
	
	
}