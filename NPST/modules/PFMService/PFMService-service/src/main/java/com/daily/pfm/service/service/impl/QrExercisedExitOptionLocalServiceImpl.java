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

import com.daily.pfm.service.model.QrCauseWisePatternAnalysis;
import com.daily.pfm.service.model.QrExercisedExitOption;
import com.daily.pfm.service.service.base.QrExercisedExitOptionLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QrExercisedExitOptionLocalServiceImpl
	extends QrExercisedExitOptionLocalServiceBaseImpl {
	
	public void addQrExercisedExitOption(List<QrExercisedExitOption> qreeo) {
		for(Iterator iterator = qreeo.iterator();iterator.hasNext();) {
			QrExercisedExitOption qreeoption =(QrExercisedExitOption) iterator.next();
			qrExercisedExitOptionLocalService.addQrExercisedExitOption(qreeoption);
		}
	}
	
	public void deleteQrExercisedExitOptionByReportUploadLogId(Long reportUploadLogId) {
		List<QrExercisedExitOption> deleteRepLog =  qrExercisedExitOptionPersistence.findByReportUploadLogId(reportUploadLogId);
		for(QrExercisedExitOption deleteReportLogs : deleteRepLog) {
			qrExercisedExitOptionLocalService.deleteQrExercisedExitOption(deleteReportLogs);
		}
	}

}