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

import compliance.service.model.Quaeterlyfromfive;
import compliance.service.service.base.QuaeterlyfromfiveLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan Quaeterlyfromfive
 */
public class QuaeterlyfromfiveLocalServiceImpl
	extends QuaeterlyfromfiveLocalServiceBaseImpl {
	
	public void addQuarterlyformfive(List<Quaeterlyfromfive>  formfive) {
		for(Iterator iterator = formfive.iterator(); iterator.hasNext();) {
			Quaeterlyfromfive formfivedata= (Quaeterlyfromfive) iterator.next();
			quaeterlyfromfiveLocalService.addQuaeterlyfromfive(formfivedata);
		}
	}
	public void deleteQuaeterlyfromfiveByReportUploadLogId(Long reportUploadLogId) {
		List<Quaeterlyfromfive> deleteRepLog =  quaeterlyfromfivePersistence.findByReportUploadLogId(reportUploadLogId);
		for(Quaeterlyfromfive deleteReportLogs : deleteRepLog) {
			quaeterlyfromfiveLocalService.deleteQuaeterlyfromfive(deleteReportLogs);
		}
	}

}