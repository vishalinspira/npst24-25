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

import compliance.service.model.MnGrievanceOutstanding;
import compliance.service.service.base.MnGrievanceOutstandingLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnGrievanceOutstandingLocalServiceImpl extends MnGrievanceOutstandingLocalServiceBaseImpl {
	
	public void addMnGrievanceOutstanding(List<MnGrievanceOutstanding> formList) {
			
			for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
				MnGrievanceOutstanding form = (MnGrievanceOutstanding) iterator.next();
				mnGrievanceOutstandingLocalService.addMnGrievanceOutstanding(form);
			}
				
		}
	
	public void deleteMnGrievanceOutstandingByReportUploadLogId(Long reportUploadLogId) {
		List<MnGrievanceOutstanding> deleteRepLog =  mnGrievanceOutstandingPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnGrievanceOutstanding deleteReportLogs : deleteRepLog) {
			mnGrievanceOutstandingLocalService.deleteMnGrievanceOutstanding(deleteReportLogs);
		}
	}

	
}