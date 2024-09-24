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

import compliance.service.model.MnForm2;
import compliance.service.service.base.MnForm2LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm2LocalServiceImpl extends MnForm2LocalServiceBaseImpl {
	
	public void addMnForm2(List<MnForm2> formList) {
			
			for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
				MnForm2 form = (MnForm2) iterator.next();
				mnForm2LocalService.addMnForm2(form);
			}
				
		}
	
	public void deleteMnForm2ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm2> deleteRepLog = mnForm2Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm2 deleteReportLogs : deleteRepLog) {
			mnForm2LocalService.deleteMnForm2(deleteReportLogs);
		}
	}
	
}