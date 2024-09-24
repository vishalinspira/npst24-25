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

import compliance.service.model.AgendaI;
import compliance.service.service.base.AgendaILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaILocalServiceImpl extends AgendaILocalServiceBaseImpl {
	
	public AgendaI addTable1Details(String fileNumber, String bNumber, String itemNumber,
			String date1, String date2, String date3, long createdBy, Date createDate) {
		
		AgendaI agenda1 = agendaIPersistence.create(counterLocalService.increment());
		
		agenda1.setFile_number(fileNumber);
		agenda1.setB_number(bNumber);
		agenda1.setItem_number(itemNumber);
		agenda1.setDate_1(date1);
		agenda1.setDate_2(date2);
		agenda1.setDate_3(date3);
		agenda1.setCreatedby(createdBy);
		agenda1.setCreatedon(createDate);
		
		agenda1 = agendaIPersistence.update(agenda1);
		
		return agenda1;
	}
	
}