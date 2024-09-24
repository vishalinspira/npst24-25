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

import compliance.service.model.ElectronicAccepted;
import compliance.service.service.base.ElectronicAcceptedLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ElectronicAcceptedLocalServiceImpl extends ElectronicAcceptedLocalServiceBaseImpl {
	
	public void addElectronicsAccepted(List<ElectronicAccepted> eleAccepted) {
		
		for(Iterator iterator = eleAccepted.iterator(); iterator.hasNext();) {
			ElectronicAccepted electronicAccepted = (ElectronicAccepted) iterator.next();
			electronicAcceptedLocalService.addElectronicAccepted(electronicAccepted);
		}
		
	}
	
	public void deleteElectronicAcceptedByReportUploadLogId(Long reportUploadLogId) {
		List<ElectronicAccepted> deleteRepLog =  electronicAcceptedPersistence.findByReportUploadLogId(reportUploadLogId);
		for(ElectronicAccepted deleteReportLogs : deleteRepLog) {
			electronicAcceptedLocalService.deleteElectronicAccepted(deleteReportLogs);
		}
	}
	
	
}