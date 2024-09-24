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

import compliance.service.model.AgendaVII;
import compliance.service.service.base.AgendaVIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaVIILocalServiceImpl extends AgendaVIILocalServiceBaseImpl {
	
	public AgendaVII addTable7Details(String entity, String q1, String q2,
			String q3, String q4, String q1One, String q2One, long createdBy, Date createDate) {
		
		AgendaVII agenda7 = agendaVIIPersistence.create(counterLocalService.increment());
		
		agenda7.setEntity(entity);
		agenda7.setQ1(q1);
		agenda7.setQ2(q2);
		agenda7.setQ3(q3);
		agenda7.setQ4(q4);
		agenda7.setQ1_1(q1One);
		agenda7.setQ2_2(q2One);
		agenda7.setCreatedby(createdBy);
		agenda7.setCreatedon(createDate);
		
		agenda7 = agendaVIIPersistence.update(agenda7);
		
		return agenda7;
	}
	
	
}