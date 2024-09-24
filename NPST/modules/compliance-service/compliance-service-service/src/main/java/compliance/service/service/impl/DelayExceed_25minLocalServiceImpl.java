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

import compliance.service.model.DelayExceed_25min;
import compliance.service.service.base.DelayExceed_25minLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class DelayExceed_25minLocalServiceImpl extends DelayExceed_25minLocalServiceBaseImpl {
	
public void addDelayExceed25min(List<DelayExceed_25min> delayExceed25minList) {
		
		for(Iterator iterator = delayExceed25minList.iterator(); iterator.hasNext();) {
			DelayExceed_25min delayExceed25min = (DelayExceed_25min) iterator.next();
			delayExceed_25minLocalService.addDelayExceed_25min(delayExceed25min);
		}
		
	}

public void deleteDelayExceed_25minByReportUploadLogId(Long reportUploadLogId) {
	List<DelayExceed_25min> deleteRepLog = delayExceed_25minPersistence.findByReportUploadLogId(reportUploadLogId);
	for(DelayExceed_25min deleteReportLogs : deleteRepLog) {
		delayExceed_25minLocalService.deleteDelayExceed_25min(deleteReportLogs);
	}
}
	
	
}