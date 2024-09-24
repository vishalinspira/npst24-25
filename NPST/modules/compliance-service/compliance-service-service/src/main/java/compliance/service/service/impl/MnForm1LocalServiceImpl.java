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

import compliance.service.model.MnForm1;
import compliance.service.service.base.MnForm1LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm1LocalServiceImpl extends MnForm1LocalServiceBaseImpl {
	
	public void addMnForm1(List<MnForm1> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm1 form = (MnForm1) iterator.next();
			mnForm1LocalService.addMnForm1(form);
		}
			
	}
	
	public void deleteMnForm1ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm1> deleteRepLog = mnForm1Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm1 deleteReportLogs : deleteRepLog) {
			mnForm1LocalService.deleteMnForm1(deleteReportLogs);
		}
	}
	
}