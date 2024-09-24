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


import com.daily.average.service.model.valuationRepSg;
import com.daily.average.service.service.base.valuationRepSgLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class valuationRepSgLocalServiceImpl extends valuationRepSgLocalServiceBaseImpl {
	
public void addValuationRepSg(List<valuationRepSg> valuatioRepSgList) {
		
		for(Iterator iterator = valuatioRepSgList.iterator(); iterator.hasNext();) {
			valuationRepSg valuationRepSg = (valuationRepSg) iterator.next();
			valuationRepSgLocalService.addvaluationRepSg(valuationRepSg);
		}
		
	}
	
public void deletevaluationRepSgByReportUploadLogId(Long reportUploadLogId) {
	  List<valuationRepSg> valuationRepSgs =
			  valuationRepSgPersistence.findByReportUploadLogId(reportUploadLogId);
	  for(valuationRepSg valuationRepSg : valuationRepSgs) {
		  valuationRepSgLocalService.deletevaluationRepSg(valuationRepSg); } }
}