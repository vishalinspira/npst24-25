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

import compliance.service.model.AgendaIV;
import compliance.service.service.base.AgendaIVLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaIVLocalServiceImpl extends AgendaIVLocalServiceBaseImpl {
	
	public AgendaIV addTable4Details(String grievances, String q1, String q2,
			String q3, String q4, String q1One, String q2One, long createdBy, Date createDate) {
		
		AgendaIV agenda4 = agendaIVPersistence.create(counterLocalService.increment());
		
		agenda4.setGrievances(grievances);
		agenda4.setQ1(q1);
		agenda4.setQ2(q2);
		agenda4.setQ3(q3);
		agenda4.setQ4(q4);
		agenda4.setQ1_1(q1One);
		agenda4.setQ2_2(q2One);
		agenda4.setCreatedby(createdBy);
		agenda4.setCreatedon(createDate);
		
		agenda4 = agendaIVPersistence.update(agenda4);
		
		return agenda4;
	}
	
	
}