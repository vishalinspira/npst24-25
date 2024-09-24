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

import compliance.service.model.MnForm3;
import compliance.service.service.base.MnForm3LocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm3LocalServiceImpl extends MnForm3LocalServiceBaseImpl {
	
	public void addMnForm3(List<MnForm3> formList) {
			
			for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
				MnForm3 form = (MnForm3) iterator.next();
				mnForm3LocalService.addMnForm3(form);
			}
				
		}
	
	public void deleteMnForm3ByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm3> deleteRepLog = mnForm3Persistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm3 deleteReportLogs : deleteRepLog) {
			mnForm3LocalService.deleteMnForm3(deleteReportLogs);
		}
	}
	
}