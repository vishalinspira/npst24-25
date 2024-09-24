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

import compliance.service.model.ENPSServChanges;
import compliance.service.service.base.ENPSServChangesLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ENPSServChangesLocalServiceImpl extends ENPSServChangesLocalServiceBaseImpl {
	
public void addENPSServChanges(List<ENPSServChanges> enpsServChangesList) {
		
		for(Iterator iterator = enpsServChangesList.iterator(); iterator.hasNext();) {
			ENPSServChanges enpsServChanges = (ENPSServChanges) iterator.next();
			enpsServChangesLocalService.addENPSServChanges(enpsServChanges);
		}
		
	}

public void deleteENPSServChangesByReportUploadLogId(Long reportUploadLogId) {
	List<ENPSServChanges> deleteRepLog = enpsServChangesPersistence.findByReportUploadLogId(reportUploadLogId);
	for(ENPSServChanges deleteReportLogs : deleteRepLog) {
		enpsServChangesLocalService.deleteENPSServChanges(deleteReportLogs);
	}
}
	
	
}