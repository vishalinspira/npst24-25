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
import java.util.Iterator;
import java.util.List;

import compliance.service.model.Status_SG;
import compliance.service.service.base.Status_SGLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class Status_SGLocalServiceImpl extends Status_SGLocalServiceBaseImpl {
	
	public Status_SG addStatusSG(String state_government,String date_of_notification,String date_of_adoption, String date_of_contract_sign_cra, String date_of_contract_sign_nps, String reg_status_nodal_office,
			String reg_status_nodal_subscriber,String total_number_of_subscribers,String contribution_status,Date createdon,long createdby) {
			
		Status_SG sg = status_SGPersistence.create(counterLocalService.increment(Status_SG.class.getName()));
		sg.setState_government(state_government);
		sg.setDate_of_adoption(date_of_adoption);
		sg.setDate_of_contract_sign_cra(date_of_contract_sign_cra);
		sg.setDate_of_contract_sign_nps(date_of_contract_sign_nps);
		sg.setDate_of_notification(date_of_notification);
		sg.setReg_status_nodal_office(reg_status_nodal_office);
		sg.setReg_status_nodal_subscriber(reg_status_nodal_subscriber);
		sg.setTotal_number_of_subscribers(total_number_of_subscribers);
		sg.setContribution_status(contribution_status);
		sg.setCreatedon(createdon);
		sg.setCreatedby(createdby);
		
		return status_SGPersistence.update(sg);
		}
	
	
}