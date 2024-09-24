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

package com.daily.average.service.service.impl;

import com.daily.average.service.model.MnSubsAnnuityPurchase;
import com.daily.average.service.service.base.MnSubsAnnuityPurchaseLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MnSubsAnnuityPurchaseLocalServiceImpl
	extends MnSubsAnnuityPurchaseLocalServiceBaseImpl {
	
	public void addMnSubsAnnuityPurchase(List<MnSubsAnnuityPurchase> form3List){
		for(Iterator iterator = form3List.iterator(); iterator.hasNext();) {
			MnSubsAnnuityPurchase form = (MnSubsAnnuityPurchase) iterator.next();
			mnSubsAnnuityPurchaseLocalService.addMnSubsAnnuityPurchase(form);
		}
	}
	
	public void deleteMnSubsAnnuityPurchaseByReportUploadLogId(Long reportUploadLogId) {
		List<MnSubsAnnuityPurchase> deleteRepLog =  mnSubsAnnuityPurchasePersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnSubsAnnuityPurchase deleteReportLogs : deleteRepLog) {
			mnSubsAnnuityPurchaseLocalService.deleteMnSubsAnnuityPurchase(deleteReportLogs);
		}
	}

}