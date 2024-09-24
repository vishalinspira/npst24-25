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

import com.daily.pfm.service.model.QrCauseWiseAgeingAnalysis;
import com.daily.pfm.service.service.base.QrCauseWiseAgeingAnalysisLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QrCauseWiseAgeingAnalysisLocalServiceImpl
	extends QrCauseWiseAgeingAnalysisLocalServiceBaseImpl {
	
	public void addQrCauseWiseAgeingAnalysis(List<QrCauseWiseAgeingAnalysis> qrcwaa) {
		for(Iterator iterator = qrcwaa.iterator();iterator.hasNext();) {
			QrCauseWiseAgeingAnalysis qrcausewaa =(QrCauseWiseAgeingAnalysis) iterator.next();
			qrCauseWiseAgeingAnalysisLocalService.addQrCauseWiseAgeingAnalysis(qrcausewaa);
		}
	}
	
	public void deleteQrCauseWiseAgeingAnalysisByReportUploadLogId(Long reportUploadLogId) {
		List<QrCauseWiseAgeingAnalysis> deleteRepLog =  qrCauseWiseAgeingAnalysisPersistence.findByReportUploadLogId(reportUploadLogId);
		for(QrCauseWiseAgeingAnalysis deleteReportLogs : deleteRepLog) {
			qrCauseWiseAgeingAnalysisLocalService.deleteQrCauseWiseAgeingAnalysis(deleteReportLogs);
		}
	}

}