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

import com.daily.pfm.service.model.QrGapAnalysis;
import com.daily.pfm.service.service.base.QrGapAnalysisLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QrGapAnalysisLocalServiceImpl
	extends QrGapAnalysisLocalServiceBaseImpl {
	
	public void addQrGapAnalysis(List<QrGapAnalysis> qrgapa) {
		for(Iterator iterator = qrgapa.iterator();iterator.hasNext();) {
			QrGapAnalysis qrgap =(QrGapAnalysis) iterator.next();
			qrGapAnalysisLocalService.addQrGapAnalysis(qrgap);
		}
	}
	
	public void deleteQrGapAnalysisByReportUploadLogId(Long reportUploadLogId) {
		List<QrGapAnalysis> deleteRepLog =  qrGapAnalysisPersistence.findByReportUploadLogId(reportUploadLogId);
		for(QrGapAnalysis deleteReportLogs : deleteRepLog) {
			qrGapAnalysisLocalService.deleteQrGapAnalysis(deleteReportLogs);
		}
	}

}