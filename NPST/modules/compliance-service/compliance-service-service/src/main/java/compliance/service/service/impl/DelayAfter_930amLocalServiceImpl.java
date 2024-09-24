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

import compliance.service.model.DelayAfter_930am;
import compliance.service.service.base.DelayAfter_930amLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class DelayAfter_930amLocalServiceImpl extends DelayAfter_930amLocalServiceBaseImpl {
	
public void addDelayAfter930am(List<DelayAfter_930am> delayAfter930amList) {
		
		for(Iterator iterator = delayAfter930amList.iterator(); iterator.hasNext();) {
			DelayAfter_930am delayAfter930am = (DelayAfter_930am) iterator.next();
			delayAfter_930amLocalService.addDelayAfter_930am(delayAfter930am);
		}
		
	}
	
public void deleteDelayAfter_930amByReportUploadLogId(Long reportUploadLogId) {
	List<DelayAfter_930am> deleteRepLog = delayAfter_930amPersistence.findByReportUploadLogId(reportUploadLogId);
	for(DelayAfter_930am deleteReportLogs : deleteRepLog) {
		delayAfter_930amLocalService.deleteDelayAfter_930am(deleteReportLogs);
	}
}
	
	
}