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

import compliance.service.model.AgendaII;
import compliance.service.service.base.AgendaIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AgendaIILocalServiceImpl extends AgendaIILocalServiceBaseImpl {
	
	public AgendaII addTable2Details(String entitites, String openingBalance, String escalatedToNpst,
			String grievancesReceived, String grievancesAssigned, String grievancesResolved, 
			String outstandingGrievances, long createdBy, Date createDate) {
		
		AgendaII agenda2 = agendaIIPersistence.create(counterLocalService.increment());
		
		agenda2.setEntitites(entitites);
		agenda2.setOpening_balance(openingBalance);
		agenda2.setEscalated_to_npst(escalatedToNpst);
		agenda2.setGrievances_received(grievancesReceived);
		agenda2.setGrievances_assigned(grievancesAssigned);
		agenda2.setGrievances_resolved(grievancesResolved);
		agenda2.setOutstanding_grievances(outstandingGrievances);
		agenda2.setCreatedby(createdBy);
		agenda2.setCreatedon(createDate);
		
		agenda2 = agendaIIPersistence.update(agenda2);
		
		return agenda2;
	}
	
	
	
}