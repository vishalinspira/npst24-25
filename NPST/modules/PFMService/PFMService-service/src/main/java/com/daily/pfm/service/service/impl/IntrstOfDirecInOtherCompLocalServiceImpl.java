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

package com.daily.pfm.service.service.impl;

import com.daily.pfm.service.model.IntrstOfDirecInOtherComp;
import com.daily.pfm.service.service.base.IntrstOfDirecInOtherCompLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class IntrstOfDirecInOtherCompLocalServiceImpl extends IntrstOfDirecInOtherCompLocalServiceBaseImpl {
	
	public IntrstOfDirecInOtherComp addIntrstOfDirecInOtherComp(String nameCompanyFirmAssociation, String natureInterestConcernInterest, 
			String shareholding, Date dateOnInterestOrConcernArose, Date createDate, long createdBy, long reportUploadLogId) {
		
		IntrstOfDirecInOtherComp intrstOfDirecInOtherComp = intrstOfDirecInOtherCompPersistence.create(counterLocalService.increment());
		
		intrstOfDirecInOtherComp.setNameCompanyFirmAssociation(nameCompanyFirmAssociation);
		intrstOfDirecInOtherComp.setNatureInterestConcernInterest(natureInterestConcernInterest);
		intrstOfDirecInOtherComp.setShareholding(shareholding);
		intrstOfDirecInOtherComp.setDateOnInterestOrConcernArose(dateOnInterestOrConcernArose);
		intrstOfDirecInOtherComp.setCreatedon(createDate);
		intrstOfDirecInOtherComp.setCreatedby(createdBy);
		intrstOfDirecInOtherComp.setReportUploadLogId(reportUploadLogId);
		
		return intrstOfDirecInOtherCompPersistence.update(intrstOfDirecInOtherComp);
		
	}
}