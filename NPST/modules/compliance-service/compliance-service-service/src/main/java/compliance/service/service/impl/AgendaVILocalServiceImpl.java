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

import compliance.service.model.AgendaVI;
import compliance.service.service.base.AgendaVILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaVILocalServiceImpl extends AgendaVILocalServiceBaseImpl {
	
	public AgendaVI addTable6Details(String category, String q1, String q2,
			String q3, String q4, String q1One, String q2One, long createdBy, Date createDate) {
		
		AgendaVI agenda6 = agendaVIPersistence.create(counterLocalService.increment());
		
		agenda6.setCategory(category);
		agenda6.setQ1(q1);
		agenda6.setQ2(q2);
		agenda6.setQ3(q3);
		agenda6.setQ4(q4);
		agenda6.setQ1_1(q1One);
		agenda6.setQ2_2(q2One);
		agenda6.setCreatedby(createdBy);
		agenda6.setCreatedon(createDate);
		
		agenda6 = agendaVIPersistence.update(agenda6);
		
		return agenda6;
	}
	
	
}