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

package com.daily.average.service.service.impl;

import com.daily.average.service.model.BenchSchemeReturnsMonthly;

import com.daily.average.service.service.base.BenchSchemeReturnsMonthlyLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BenchSchemeReturnsMonthlyLocalServiceImpl
	extends BenchSchemeReturnsMonthlyLocalServiceBaseImpl {
	
public void addBenchSchemeReturnsMonthly(List<BenchSchemeReturnsMonthly> list) {
		
		for(Iterator<BenchSchemeReturnsMonthly> iterator = list.iterator(); iterator.hasNext();) {
			BenchSchemeReturnsMonthly benchSchemeReturnsMonthly = (BenchSchemeReturnsMonthly)iterator.next();
			benchSchemeReturnsMonthlyLocalService.addBenchSchemeReturnsMonthly(benchSchemeReturnsMonthly);
		}
	}

public void deleteBenchSchemeReturnsMonthlyByReportUploadLogId(Long reportUploadLogId) {
	  List<BenchSchemeReturnsMonthly> benchSchemeReturnsMonthlys =
			  benchSchemeReturnsMonthlyPersistence.findByReportUploadLogId(reportUploadLogId);
	  for(BenchSchemeReturnsMonthly benchSchemeReturnsMonthly : benchSchemeReturnsMonthlys) {
		  benchSchemeReturnsMonthlyLocalService.deleteBenchSchemeReturnsMonthly(benchSchemeReturnsMonthly); } }
}