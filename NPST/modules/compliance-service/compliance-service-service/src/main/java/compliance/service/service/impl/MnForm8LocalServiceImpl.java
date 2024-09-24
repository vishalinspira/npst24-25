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

import compliance.service.model.MnForm8;
import compliance.service.model.MnForm8;
import compliance.service.service.base.MnForm8LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm8LocalServiceImpl extends MnForm8LocalServiceBaseImpl {
	
	public void addMnForm8(List<MnForm8> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm8 form = (MnForm8) iterator.next();
			mnForm8LocalService.addMnForm8(form);
		}
			
	}
	
	public void deleteMnForm8ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm8> deleteRepLog = mnForm8Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm8 deleteReportLogs : deleteRepLog) {
			mnForm8LocalService.deleteMnForm8(deleteReportLogs);
		}
	}
	
}