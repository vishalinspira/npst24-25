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

import com.daily.pfm.service.model.CashFlow;
import com.daily.pfm.service.service.base.CashFlowLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CashFlowLocalServiceImpl extends CashFlowLocalServiceBaseImpl {
	
	public void addCashFlow(List<CashFlow> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			CashFlow form = (CashFlow) iterator.next();
			cashFlowLocalService.addCashFlow(form);
		}
			
	}
	
	public void deleteCashFlowByReportUploadLogId(Long reportUploadLogId) {
		List<CashFlow> deleteRepLog = cashFlowPersistence.findByReportUploadLogId(reportUploadLogId);
		for(CashFlow deleteReportLogs : deleteRepLog) {
			cashFlowLocalService.deleteCashFlow(deleteReportLogs);
		}
	}
	
	
}