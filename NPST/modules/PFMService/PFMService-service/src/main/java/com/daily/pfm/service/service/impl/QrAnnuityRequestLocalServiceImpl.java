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

import com.daily.pfm.service.model.QrAnnuityRequest;
import com.daily.pfm.service.service.base.QrAnnuityRequestLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QrAnnuityRequestLocalServiceImpl
	extends QrAnnuityRequestLocalServiceBaseImpl {
	
	public void addQrAnnuityRequest(List<QrAnnuityRequest> qrAnnuityRequest) {
		for(Iterator iterator = qrAnnuityRequest.iterator();iterator.hasNext();) {
			QrAnnuityRequest qrAnnuityReq =(QrAnnuityRequest) iterator.next();
			qrAnnuityRequestLocalService.addQrAnnuityRequest(qrAnnuityReq);
		}
	}
	
	public void deleteQrAnnuityRequestByReportUploadLogId(Long reportUploadLogId) {
		List<QrAnnuityRequest> deleteRepLog =  qrAnnuityRequestPersistence.findByReportUploadLogId(reportUploadLogId);
		for(QrAnnuityRequest deleteReportLogs : deleteRepLog) {
			qrAnnuityRequestLocalService.deleteQrAnnuityRequest(deleteReportLogs);
		}
	}

}