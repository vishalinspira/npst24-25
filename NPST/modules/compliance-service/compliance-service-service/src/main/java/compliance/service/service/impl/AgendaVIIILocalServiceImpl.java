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

import compliance.service.model.AgendaVIII;
import compliance.service.service.base.AgendaVIIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaVIIILocalServiceImpl extends AgendaVIIILocalServiceBaseImpl {
	
	public AgendaVIII addTable8Details(String entity, String t4a_referrals, String t4a_0_7,
			String t4a_8_15, String t4a_16_31, String t4a_32_90, String t4a_91_180, 
			String t4a_181_365, String t4a_366_and_above, long createdBy, Date createDate) {
		
		AgendaVIII agenda8 = agendaVIIIPersistence.create(counterLocalService.increment());
		
		agenda8.setEntity(entity);
		agenda8.setRef_at_the_end_of_quarter(t4a_referrals);
		agenda8.set_0_7(t4a_0_7);
		agenda8.set_8_15(t4a_8_15);
		agenda8.set_16_31(t4a_16_31);
		agenda8.set_32_90(t4a_32_90);
		agenda8.set_91_180(t4a_91_180);
		agenda8.set_181_365(t4a_181_365);
		agenda8.set_366_and_above(t4a_366_and_above);
		agenda8.setCreatedby(createdBy);
		agenda8.setCreatedon(createDate);
		
		agenda8 = agendaVIIIPersistence.update(agenda8);
		
		return agenda8;
	}
	
	
}