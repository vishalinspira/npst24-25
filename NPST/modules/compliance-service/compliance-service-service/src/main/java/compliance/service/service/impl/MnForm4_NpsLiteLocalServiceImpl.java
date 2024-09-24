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

import compliance.service.model.MnForm4_NpsLite;
import compliance.service.service.base.MnForm4_NpsLiteLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm4_NpsLiteLocalServiceImpl extends MnForm4_NpsLiteLocalServiceBaseImpl {
	
	public void addMnForm4_NpsLite(List<MnForm4_NpsLite> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm4_NpsLite form = (MnForm4_NpsLite) iterator.next();
			mnForm4_NpsLiteLocalService.addMnForm4_NpsLite(form);
		}
			
	}
	
	public void deleteMnForm4_NpsLiteByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm4_NpsLite> deleteRepLog = mnForm4_NpsLitePersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm4_NpsLite deleteReportLogs : deleteRepLog) {
			mnForm4_NpsLiteLocalService.deleteMnForm4_NpsLite(deleteReportLogs);
		}
	}
	
}