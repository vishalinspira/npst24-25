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

import com.daily.pfm.service.model.IncomeStatement;
import com.daily.pfm.service.service.base.IncomeStatementLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class IncomeStatementLocalServiceImpl
	extends IncomeStatementLocalServiceBaseImpl {
	
	
	public void addIncomeStatement(List<IncomeStatement> formList) {
		for (IncomeStatement incomeStatement : formList) {
			incomeStatementLocalService.addIncomeStatement(incomeStatement);
		}
	}
	
	public void deleteIncomeStatementByReportUploadLogId(Long reportUploadLogId) {
		List<IncomeStatement> deleteRepLog = incomeStatementPersistence.findByReportUploadLogId(reportUploadLogId);
		for(IncomeStatement deleteReportLogs : deleteRepLog) {
			incomeStatementLocalService.deleteIncomeStatement(deleteReportLogs);
		}
	}
}