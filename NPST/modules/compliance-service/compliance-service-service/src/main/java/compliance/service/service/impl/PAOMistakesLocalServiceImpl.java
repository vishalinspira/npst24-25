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

import compliance.service.model.PAOMistakes;
import compliance.service.service.base.PAOMistakesLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class PAOMistakesLocalServiceImpl extends PAOMistakesLocalServiceBaseImpl {
	
public void addPaoMistakes(List<PAOMistakes> paoMistakesList) {
		
		for(Iterator iterator = paoMistakesList.iterator(); iterator.hasNext();) {
			PAOMistakes paoMistakes = (PAOMistakes) iterator.next();
			paoMistakesLocalService.addPAOMistakes(paoMistakes);
		}
		
	}

public void deletePAOMistakesByReportUploadLogId(Long reportUploadLogId) {
	List<PAOMistakes> deleteRepLog = paoMistakesPersistence.findByReportUploadLogId(reportUploadLogId);
	for(PAOMistakes deleteReportLogs : deleteRepLog) {
		paoMistakesLocalService.deletePAOMistakes(deleteReportLogs);
	}
}
	
	
}