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

import compliance.service.model.AgendaV;
import compliance.service.service.base.AgendaVLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaVLocalServiceImpl extends AgendaVLocalServiceBaseImpl {
	
	public AgendaV addTable5Details(String category, String q1, String q2,
			String q3, String q4, long createdBy, Date createDate) {
		
		AgendaV agenda5 = agendaVPersistence.create(counterLocalService.increment());
		
		agenda5.setCategory(category);
		agenda5.setQ1(q1);
		agenda5.setQ2(q2);
		agenda5.setQ3(q3);
		agenda5.setQ4(q4);
		agenda5.setCreatedby(createdBy);
		agenda5.setCreatedon(createDate);
		
		agenda5 = agendaVPersistence.update(agenda5);
		
		return agenda5;
	}
	
	
}