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

import compliance.service.model.MnForm4_CG;
import compliance.service.service.base.MnForm4_CGLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm4_CGLocalServiceImpl extends MnForm4_CGLocalServiceBaseImpl {
	
	public void addMnForm4_CG(List<MnForm4_CG> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm4_CG form = (MnForm4_CG) iterator.next();
			mnForm4_CGLocalService.addMnForm4_CG(form);
		}
			
	}
	
	public void deleteMnForm4_CGByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm4_CG> deleteRepLog = mnForm4_CGPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm4_CG deleteReportLogs : deleteRepLog) {
			mnForm4_CGLocalService.deleteMnForm4_CG(deleteReportLogs);
		}
	}
	
}