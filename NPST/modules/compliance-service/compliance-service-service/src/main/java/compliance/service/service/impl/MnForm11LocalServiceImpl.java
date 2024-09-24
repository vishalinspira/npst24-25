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

import compliance.service.model.MnForm11;
import compliance.service.service.base.MnForm11LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm11LocalServiceImpl extends MnForm11LocalServiceBaseImpl {
	
	public void addMnForm11(List<MnForm11> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm11 form = (MnForm11) iterator.next();
			mnForm11LocalService.addMnForm11(form);
		}
			
	}
	
	public void deleteMnForm11ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm11> deleteRepLog = mnForm11Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm11 deleteReportLogs : deleteRepLog) {
			mnForm11LocalService.deleteMnForm11(deleteReportLogs);
		}
	}
	
}