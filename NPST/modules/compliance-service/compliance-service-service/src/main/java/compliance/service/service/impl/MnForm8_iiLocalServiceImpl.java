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

import compliance.service.model.MnForm8_ii;
import compliance.service.service.base.MnForm8_iiLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm8_iiLocalServiceImpl extends MnForm8_iiLocalServiceBaseImpl {
public void addMnForm8_ii(List<MnForm8_ii> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm8_ii form = (MnForm8_ii) iterator.next();
			mnForm8_iiLocalService.addMnForm8_ii(form);
		}
			
	}
	
	public void deleteMnForm8_iiByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm8_ii> deleteRepLog = mnForm8_iiPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm8_ii deleteReportLogs : deleteRepLog) {
			mnForm8_iiLocalService.deleteMnForm8_ii(deleteReportLogs);
		}
	}
}