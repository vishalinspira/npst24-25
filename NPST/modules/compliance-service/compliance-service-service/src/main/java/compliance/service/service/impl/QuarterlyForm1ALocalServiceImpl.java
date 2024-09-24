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

import compliance.service.model.QuarterlyForm1A;
import compliance.service.service.base.QuarterlyForm1ALocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlyForm1ALocalServiceImpl extends QuarterlyForm1ALocalServiceBaseImpl {
	
	public void addQuarterlyForm1A(List<QuarterlyForm1A> qrForm1A) {
		for(Iterator iterator = qrForm1A.iterator(); iterator.hasNext();) {
			QuarterlyForm1A quarterlyForm1A= (QuarterlyForm1A) iterator.next();
			quarterlyForm1ALocalService.addQuarterlyForm1A(quarterlyForm1A);
		}
	}
	
	public void deleteQuarterlyForm1AByReportUploadLogId(Long reportUploadLogId) {
		List<QuarterlyForm1A> deleteRepLog = quarterlyForm1APersistence.findByReportUploadLogId(reportUploadLogId);
		for(QuarterlyForm1A deleteReportLogs : deleteRepLog) {
			quarterlyForm1ALocalService.deleteQuarterlyForm1A(deleteReportLogs);
		}
	}
}