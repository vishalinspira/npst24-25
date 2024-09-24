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

import com.daily.pfm.service.model.QrAnnuityRequestAgeing;
import com.daily.pfm.service.service.base.QrAnnuityRequestAgeingLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QrAnnuityRequestAgeingLocalServiceImpl
	extends QrAnnuityRequestAgeingLocalServiceBaseImpl {
	
	public void addQrAnnuityRequestAgeing(List<QrAnnuityRequestAgeing> qrAnnuityRequestAgeing) {
		for(Iterator iterator = qrAnnuityRequestAgeing.iterator();iterator.hasNext();) {
			QrAnnuityRequestAgeing qrAnnuityRequesta =(QrAnnuityRequestAgeing) iterator.next();
			qrAnnuityRequestAgeingLocalService.addQrAnnuityRequestAgeing(qrAnnuityRequesta);
		}
	}
	
	public void deleteQrAnnuityRequestAgeingByReportUploadLogId(Long reportUploadLogId) {
		List<QrAnnuityRequestAgeing> deleteRepLog =  qrAnnuityRequestAgeingPersistence.findByReportUploadLogId(reportUploadLogId);
		for(QrAnnuityRequestAgeing deleteReportLogs : deleteRepLog) {
			qrAnnuityRequestAgeingLocalService.deleteQrAnnuityRequestAgeing(deleteReportLogs);
		}
	}

}