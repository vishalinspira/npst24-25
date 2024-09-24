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

import compliance.service.model.ElectronicRejSummary;
import compliance.service.service.base.ElectronicRejSummaryLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ElectronicRejSummaryLocalServiceImpl extends ElectronicRejSummaryLocalServiceBaseImpl {
	
	public void addElectronicRejSummary(List<ElectronicRejSummary> eleRejSummaryList) {
		
		for(Iterator iterator = eleRejSummaryList.iterator(); iterator.hasNext();) {
			ElectronicRejSummary eleRejSummary = (ElectronicRejSummary) iterator.next();
			electronicRejSummaryLocalService.addElectronicRejSummary(eleRejSummary);
		}
		
	}
	
	public void deleteElectronicRejSummaryByReportUploadLogId(Long reportUploadLogId) {
		List<ElectronicRejSummary> deleteRepLog = electronicRejSummaryPersistence.findByReportUploadLogId(reportUploadLogId);
		for(ElectronicRejSummary deleteReportLogs : deleteRepLog) {
			electronicRejSummaryLocalService.deleteElectronicRejSummary(deleteReportLogs);
		}
	}
	
	
	
}