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

import compliance.service.model.MnForm4_SG;
import compliance.service.service.base.MnForm4_SGLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm4_SGLocalServiceImpl extends MnForm4_SGLocalServiceBaseImpl {
	
	public void addMnForm4_SG(List<MnForm4_SG> formList) {
			
			for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
				MnForm4_SG form = (MnForm4_SG) iterator.next();
				mnForm4_SGLocalService.addMnForm4_SG(form);
			}
				
		}
	
	public void deleteMnForm4_SGByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm4_SG> deleteRepLog = mnForm4_SGPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm4_SG deleteReportLogs : deleteRepLog) {
			mnForm4_SGLocalService.deleteMnForm4_SG(deleteReportLogs);
		}
	}
	
}