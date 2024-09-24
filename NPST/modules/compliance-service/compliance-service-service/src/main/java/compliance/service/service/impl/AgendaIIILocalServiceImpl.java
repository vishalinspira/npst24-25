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

import compliance.service.model.AgendaIII;
import compliance.service.service.base.AgendaIIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaIIILocalServiceImpl extends AgendaIIILocalServiceBaseImpl {
	
	public AgendaIII addTable3Details(String particulars, String nps, String npslite_and_apy,
			long createdBy, Date createDate) {
		
		AgendaIII agenda3 = agendaIIIPersistence.create(counterLocalService.increment());
		
		agenda3.setParticulars(particulars);
		agenda3.setNps(nps);
		agenda3.setNpslite_and_apy(npslite_and_apy);
		agenda3.setCreatedby(createdBy);
		agenda3.setCreatedon(createDate);
		
		agenda3 = agendaIIIPersistence.update(agenda3);
		
		return agenda3;
	}
	
	
	
}