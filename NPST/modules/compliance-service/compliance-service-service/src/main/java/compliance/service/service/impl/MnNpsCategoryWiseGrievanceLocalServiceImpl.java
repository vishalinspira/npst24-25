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

import compliance.service.model.MnNpsCategoryWiseGrievance;
import compliance.service.service.base.MnNpsCategoryWiseGrievanceLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnNpsCategoryWiseGrievanceLocalServiceImpl extends MnNpsCategoryWiseGrievanceLocalServiceBaseImpl {
	
	public void addMnNpsCategoryWiseGrievance(List<MnNpsCategoryWiseGrievance> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnNpsCategoryWiseGrievance form = (MnNpsCategoryWiseGrievance) iterator.next();
			mnNpsCategoryWiseGrievanceLocalService.addMnNpsCategoryWiseGrievance(form);
		}
			
	}
	
	public void deleteMnNpsCategoryWiseGrievanceByReportUploadLogId(Long reportUploadLogId) {
		List<MnNpsCategoryWiseGrievance> deleteRepLog =  mnNpsCategoryWiseGrievancePersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnNpsCategoryWiseGrievance deleteReportLogs : deleteRepLog) {
			mnNpsCategoryWiseGrievanceLocalService.deleteMnNpsCategoryWiseGrievance(deleteReportLogs);
		}
	}

	
	
}