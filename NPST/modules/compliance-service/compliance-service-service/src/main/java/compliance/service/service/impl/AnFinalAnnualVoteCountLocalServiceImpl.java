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

import compliance.service.model.AnFinalAnnualVoteCount;
import compliance.service.service.base.AnFinalAnnualVoteCountLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AnFinalAnnualVoteCountLocalServiceImpl extends AnFinalAnnualVoteCountLocalServiceBaseImpl {
	
	public void addAnFinalAnnualVoteCount(List<AnFinalAnnualVoteCount> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			AnFinalAnnualVoteCount form = (AnFinalAnnualVoteCount) iterator.next();
			anFinalAnnualVoteCountLocalService.addAnFinalAnnualVoteCount(form);
		}
			
	}
	public void deleteAnFinalAnnualVoteCountByReportUploadLogId(Long reportUploadLogId) {
		List<AnFinalAnnualVoteCount> deleteRepLog =  anFinalAnnualVoteCountPersistence.findByReportUploadLogId(reportUploadLogId);
		for(AnFinalAnnualVoteCount deleteReportLogs : deleteRepLog) {
			anFinalAnnualVoteCountLocalService.deleteAnFinalAnnualVoteCount(deleteReportLogs);
	}

}
}