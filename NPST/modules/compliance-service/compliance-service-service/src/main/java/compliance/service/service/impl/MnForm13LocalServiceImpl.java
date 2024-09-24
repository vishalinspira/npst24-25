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

import compliance.service.model.MnForm13;
import compliance.service.service.base.MnForm13LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm13LocalServiceImpl extends MnForm13LocalServiceBaseImpl {
	
	public void addMnForm13(List<MnForm13> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm13 form = (MnForm13) iterator.next();
			mnForm13LocalService.addMnForm13(form);
		}
			
	}
	
	public void deleteMnForm13ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm13> deleteRepLog = mnForm13Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm13 deleteReportLogs : deleteRepLog) {
			mnForm13LocalService.deleteMnForm13(deleteReportLogs);
		}
	}
	
}