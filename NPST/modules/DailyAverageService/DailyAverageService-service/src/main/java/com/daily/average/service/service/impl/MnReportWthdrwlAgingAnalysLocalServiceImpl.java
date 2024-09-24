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

import com.daily.average.service.model.MnReportWthdrwlAgingAnalys;
import com.daily.average.service.service.base.MnReportWthdrwlAgingAnalysLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MnReportWthdrwlAgingAnalysLocalServiceImpl
	extends MnReportWthdrwlAgingAnalysLocalServiceBaseImpl {
	
	public void addMnReportWthdrwlAgingAnalys(List<MnReportWthdrwlAgingAnalys> form5List) {
		for(Iterator iterator = form5List.iterator(); iterator.hasNext();) {
			MnReportWthdrwlAgingAnalys form = (MnReportWthdrwlAgingAnalys) iterator.next();
			mnReportWthdrwlAgingAnalysLocalService.addMnReportWthdrwlAgingAnalys(form);
		}
	}
	
	public void deleteMnReportWthdrwlAgingAnalysByReportUploadLogId(Long reportUploadLogId) {
		List<MnReportWthdrwlAgingAnalys> deleteRepLog =  mnReportWthdrwlAgingAnalysPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnReportWthdrwlAgingAnalys deleteReportLogs : deleteRepLog) {
			mnReportWthdrwlAgingAnalysLocalService.deleteMnReportWthdrwlAgingAnalys(deleteReportLogs);
		}
	}

}