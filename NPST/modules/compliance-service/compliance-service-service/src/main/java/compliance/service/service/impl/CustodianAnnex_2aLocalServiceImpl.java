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

import compliance.service.model.CustodianAnnex_2a;
import compliance.service.service.base.CustodianAnnex_2aLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class CustodianAnnex_2aLocalServiceImpl extends CustodianAnnex_2aLocalServiceBaseImpl {
	
	public CustodianAnnex_2a addAnnexTwoA(String desciption, String remarks, long createdBy, Date createDate) {
		
		CustodianAnnex_2a annex_2a = custodianAnnex_2aPersistence.create(counterLocalService.increment());
		
		annex_2a.setDescription(desciption);
		annex_2a.setRemarks(remarks);
		annex_2a.setCreatedby(createdBy);
		annex_2a.setCreatedon(createDate);
		
		annex_2a = custodianAnnex_2aPersistence.update(annex_2a);
		
		return annex_2a;
	}
	
	
}