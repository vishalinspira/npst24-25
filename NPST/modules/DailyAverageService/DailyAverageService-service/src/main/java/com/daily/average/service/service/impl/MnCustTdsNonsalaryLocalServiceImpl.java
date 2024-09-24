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

import com.daily.average.service.model.MnCustTdsNonsalary;

import com.daily.average.service.service.base.MnCustTdsNonsalaryLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MnCustTdsNonsalaryLocalServiceImpl
	extends MnCustTdsNonsalaryLocalServiceBaseImpl {
	
	public void addMnCustTdsNonsalary(List<MnCustTdsNonsalary> form2List) {
		for(Iterator iterator = form2List.iterator(); iterator.hasNext();) {
			MnCustTdsNonsalary form = (MnCustTdsNonsalary) iterator.next();
			mnCustTdsNonsalaryLocalService.addMnCustTdsNonsalary(form);
		}
	}
	
	public void deleteMnCustTdsNonsalaryByReportUploadLogId(Long reportUploadLogId) {
		  List<MnCustTdsNonsalary> forms =
				  mnCustTdsNonsalaryPersistence.findByReportUploadLogId(reportUploadLogId);
		  for(MnCustTdsNonsalary form : forms) {
			  mnCustTdsNonsalaryLocalService.deleteMnCustTdsNonsalary(form); } }
	
}