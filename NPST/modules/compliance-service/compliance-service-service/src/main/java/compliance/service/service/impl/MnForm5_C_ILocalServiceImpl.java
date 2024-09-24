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

import compliance.service.model.MnForm5_C_I;
import compliance.service.service.base.MnForm5_C_ILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm5_C_ILocalServiceImpl extends MnForm5_C_ILocalServiceBaseImpl {
	
	public void addMnForm5_C_I(List<MnForm5_C_I> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm5_C_I form = (MnForm5_C_I) iterator.next();
			mnForm5_C_ILocalService.addMnForm5_C_I(form);
		}
			
	}
	
	public void deleteMnForm5_C_IByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm5_C_I> deleteRepLog = mnForm5_C_IPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm5_C_I deleteReportLogs : deleteRepLog) {
			mnForm5_C_ILocalService.deleteMnForm5_C_I(deleteReportLogs);
		}
	}
	
}