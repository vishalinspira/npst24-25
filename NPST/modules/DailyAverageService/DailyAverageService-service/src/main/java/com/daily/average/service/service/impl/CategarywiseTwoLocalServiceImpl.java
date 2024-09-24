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

import com.daily.average.service.model.CategarywiseTwo;
import com.daily.average.service.service.base.CategarywiseTwoLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CategarywiseTwoLocalServiceImpl
	extends CategarywiseTwoLocalServiceBaseImpl {
	public void addCategarywiseTwos(List<CategarywiseTwo> categarywiseTwos) {
		for(Iterator iterator = categarywiseTwos.iterator();iterator.hasNext();) {
			CategarywiseTwo categarywiseTwo =(CategarywiseTwo) iterator.next();
			categarywiseTwoLocalService.addCategarywiseTwo(categarywiseTwo);
		}
	}
	
	public void deleteCategarywiseTwoByReportUploadLogId(long reportUploadLogId) {
		List<CategarywiseTwo> categarywiseTwos = categarywiseTwoPersistence.findByReportUploadLogId(reportUploadLogId);
		for (CategarywiseTwo categarywiseTwo : categarywiseTwos) {
			categarywiseTwoLocalService.deleteCategarywiseTwo(categarywiseTwo);
		}
	}
}