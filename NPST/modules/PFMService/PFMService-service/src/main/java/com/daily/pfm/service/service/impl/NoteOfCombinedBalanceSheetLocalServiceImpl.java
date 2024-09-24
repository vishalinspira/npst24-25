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

package com.daily.pfm.service.service.impl;

import com.daily.pfm.service.model.NoteOfCombinedBalanceSheet;
import com.daily.pfm.service.service.base.NoteOfCombinedBalanceSheetLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class NoteOfCombinedBalanceSheetLocalServiceImpl
	extends NoteOfCombinedBalanceSheetLocalServiceBaseImpl {
	
	public void addNoteOfCombinedBalanceSheet(List<NoteOfCombinedBalanceSheet> formList) {
		for (NoteOfCombinedBalanceSheet noteOfCombinedBalanceSheet : formList) {
			noteOfCombinedBalanceSheetLocalService.addNoteOfCombinedBalanceSheet(noteOfCombinedBalanceSheet);
		}
	}
	
	public void deleteBalanceSheetByReportUploadLogId(Long reportUploadLogId) {
		List<NoteOfCombinedBalanceSheet> deleteRepLog = noteOfCombinedBalanceSheetPersistence.findByReportUploadLogId(reportUploadLogId);
		for(NoteOfCombinedBalanceSheet deleteReportLogs : deleteRepLog) {
			noteOfCombinedBalanceSheetLocalService.deleteNoteOfCombinedBalanceSheet(deleteReportLogs);
		}
	}
}