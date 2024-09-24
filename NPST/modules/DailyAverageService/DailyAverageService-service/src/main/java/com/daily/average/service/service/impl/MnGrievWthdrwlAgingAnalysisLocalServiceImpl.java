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

import com.daily.average.service.model.MnGrievWthdrwlAgingAnalysis;
import com.daily.average.service.service.base.MnGrievWthdrwlAgingAnalysisLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MnGrievWthdrwlAgingAnalysisLocalServiceImpl
	extends MnGrievWthdrwlAgingAnalysisLocalServiceBaseImpl {
	
	public void addMnGrievWthdrwlAgingAnalysis(List<MnGrievWthdrwlAgingAnalysis> form5List) {
		for(Iterator iterator = form5List.iterator(); iterator.hasNext();) {
			MnGrievWthdrwlAgingAnalysis form = (MnGrievWthdrwlAgingAnalysis) iterator.next();
			mnGrievWthdrwlAgingAnalysisLocalService.addMnGrievWthdrwlAgingAnalysis(form);
		}
	}
	
	public void deleteMnGrievWthdrwlAgingAnalysisByReportUploadLogId(Long reportUploadLogId) {
		List<MnGrievWthdrwlAgingAnalysis> deleteRepLog =  mnGrievWthdrwlAgingAnalysisPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnGrievWthdrwlAgingAnalysis deleteReportLogs : deleteRepLog) {
			mnGrievWthdrwlAgingAnalysisLocalService.deleteMnGrievWthdrwlAgingAnalysis(deleteReportLogs);
		}
	}

}