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

import compliance.service.model.AgendaXI;
import compliance.service.service.base.AgendaXILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaXILocalServiceImpl extends AgendaXILocalServiceBaseImpl {
	
	public AgendaXI addTable11Details(String enquiriesCategory, String t4d_referrals, String t4d_0_7,
			String t4d_8_15, String t4d_16_31, String t4d_32_90, String t4d_91_180, 
			String t4d_181_365, String t4d_366_and_above, long createdBy, Date createDate) {
		
		AgendaXI agenda11 = agendaXIPersistence.create(counterLocalService.increment());
		
		agenda11.setEnquiries_category(enquiriesCategory);
		agenda11.setRef_at_the_end_of_quarter(t4d_referrals);
		agenda11.set_0_7(t4d_0_7);
		agenda11.set_8_15(t4d_8_15);
		agenda11.set_16_31(t4d_16_31);
		agenda11.set_32_90(t4d_32_90);
		agenda11.set_91_180(t4d_91_180);
		agenda11.set_181_365(t4d_181_365);
		agenda11.set_366_and_above(t4d_366_and_above);
		agenda11.setCreatedby(createdBy);
		agenda11.setCreatedon(createDate);
		
		agenda11 = agendaXIPersistence.update(agenda11);
		
		return agenda11;
	}
	
	
}