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

import compliance.service.model.Quaeterlyfromtwo;
import compliance.service.service.base.QuaeterlyfromtwoLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class QuaeterlyfromtwoLocalServiceImpl
	extends QuaeterlyfromtwoLocalServiceBaseImpl {
	public void addQuarterlyformtwo(List<Quaeterlyfromtwo>  formtwo) {
		for(Iterator iterator = formtwo.iterator(); iterator.hasNext();) {
			Quaeterlyfromtwo formtwodata= (Quaeterlyfromtwo) iterator.next();
			quaeterlyfromtwoLocalService.addQuaeterlyfromtwo(formtwodata);
		}
	}
	public void deleteQuaeterlyfromtwoByReportUploadLogId(Long reportUploadLogId) {
		List<Quaeterlyfromtwo> deleteRepLog =  quaeterlyfromtwoPersistence.findByReportUploadLogId(reportUploadLogId);
		for(Quaeterlyfromtwo deleteReportLogs : deleteRepLog) {
			quaeterlyfromtwoLocalService.deleteQuaeterlyfromtwo(deleteReportLogs);
		}
	}

}