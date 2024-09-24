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

import compliance.service.model.CustodianAnnex_1a;
import compliance.service.service.base.CustodianAnnex_1aLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class CustodianAnnex_1aLocalServiceImpl extends CustodianAnnex_1aLocalServiceBaseImpl {
	
	public CustodianAnnex_1a addAnnexOneA(Date custodyDate, String pensionName, double aucFaceValue, 
			double aucMarketValue, double aum, double percentOfAum, 
			double assetNotUnderCustody, long createdBy, Date createDate) {
		
		CustodianAnnex_1a annex_1a = custodianAnnex_1aPersistence.create(counterLocalService.increment());
		
		annex_1a.setCustody_date(custodyDate);
		annex_1a.setPension_name(pensionName);
		annex_1a.setAuc_face_value(aucFaceValue);
		annex_1a.setAuc_market_value(aucMarketValue);
		annex_1a.setAum(aum);
		annex_1a.setPercent_of_aum(percentOfAum);
		annex_1a.setAsset_not_under_custody(assetNotUnderCustody);
		annex_1a.setCreatedby(createdBy);
		annex_1a.setCreatedon(createDate);
		
		annex_1a = custodianAnnex_1aPersistence.update(annex_1a);
		
		return annex_1a;
	}
	
	
}