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

import compliance.service.model.MnForm12;
import compliance.service.service.base.MnForm12LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm12LocalServiceImpl extends MnForm12LocalServiceBaseImpl {
	
	public void addMnForm12(List<MnForm12> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm12 form = (MnForm12) iterator.next();
			mnForm12LocalService.addMnForm12(form);
		}
			
	}
	
	public void deleteMnForm12ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm12> deleteRepLog = mnForm12Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm12 deleteReportLogs : deleteRepLog) {
			mnForm12LocalService.deleteMnForm12(deleteReportLogs);
		}
	}
	
}