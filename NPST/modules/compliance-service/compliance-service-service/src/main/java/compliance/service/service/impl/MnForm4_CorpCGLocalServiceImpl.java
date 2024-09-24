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

import compliance.service.model.MnForm4_CorpCG;
import compliance.service.service.base.MnForm4_CorpCGLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm4_CorpCGLocalServiceImpl extends MnForm4_CorpCGLocalServiceBaseImpl {
	
	public void addMnForm4_CorpCG(List<MnForm4_CorpCG> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm4_CorpCG form = (MnForm4_CorpCG) iterator.next();
			mnForm4_CorpCGLocalService.addMnForm4_CorpCG(form);
		}
			
	}
	
	public void deleteMnForm4_CorpCGByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm4_CorpCG> deleteRepLog = mnForm4_CorpCGPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm4_CorpCG deleteReportLogs : deleteRepLog) {
			mnForm4_CorpCGLocalService.deleteMnForm4_CorpCG(deleteReportLogs);
		}
	}
	
}