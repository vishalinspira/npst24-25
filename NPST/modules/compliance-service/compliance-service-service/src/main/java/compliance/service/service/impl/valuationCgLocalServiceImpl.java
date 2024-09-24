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

import java.util.Iterator;
import java.util.List;

import  compliance.service.model.valuationCg;
import compliance.service.service.base.valuationCgLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class valuationCgLocalServiceImpl
	extends valuationCgLocalServiceBaseImpl {
	
	public void addValuationCg(List<valuationCg>  valuationcg) {
		for(Iterator iterator = valuationcg.iterator(); iterator.hasNext();) {
			valuationCg Valuation_cgdata= (valuationCg) iterator.next();
			valuationCgLocalService.addvaluationCg(Valuation_cgdata);
		}
	}
	
	public void deletevaluationCgByReportUploadLogId(Long reportUploadLogId) {
		List<valuationCg> deleteRepLog = valuationCgPersistence.findByReportUploadLogId(reportUploadLogId);
		for(valuationCg deleteReportLogs : deleteRepLog) {
			valuationCgLocalService.deletevaluationCg(deleteReportLogs);
		}
	}
	
}