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

import compliance.service.model.MnForm14;
import compliance.service.service.base.MnForm14LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm14LocalServiceImpl extends MnForm14LocalServiceBaseImpl {
	
	public void addMnForm14(List<MnForm14> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm14 form = (MnForm14) iterator.next();
			mnForm14LocalService.addMnForm14(form);
		}
			
	}
	
	public void deleteMnForm14ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm14> deleteRepLog = mnForm14Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm14 deleteReportLogs : deleteRepLog) {
			mnForm14LocalService.deleteMnForm14(deleteReportLogs);
		}
	}
	
}