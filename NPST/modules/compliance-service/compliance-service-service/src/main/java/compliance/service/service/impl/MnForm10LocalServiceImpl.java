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

import compliance.service.model.MnForm10;
import compliance.service.service.base.MnForm10LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm10LocalServiceImpl extends MnForm10LocalServiceBaseImpl {
	
	public void addMnForm10(List<MnForm10> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm10 form = (MnForm10) iterator.next();
			mnForm10LocalService.addMnForm10(form);
		}
			
	}
	
	public void deleteMnForm10ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm10> deleteRepLog = mnForm10Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm10 deleteReportLogs : deleteRepLog) {
			mnForm10LocalService.deleteMnForm10(deleteReportLogs);
		}
	}
	
}