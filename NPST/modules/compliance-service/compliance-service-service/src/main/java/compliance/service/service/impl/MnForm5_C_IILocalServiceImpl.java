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

import compliance.service.model.MnForm5_C_II;
import compliance.service.service.base.MnForm5_C_IILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm5_C_IILocalServiceImpl extends MnForm5_C_IILocalServiceBaseImpl {
	
	public void addMnForm5_C_II(List<MnForm5_C_II> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm5_C_II form = (MnForm5_C_II) iterator.next();
			mnForm5_C_IILocalService.addMnForm5_C_II(form);
		}
			
	}
	
	public void deleteMnForm5_C_IIByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm5_C_II> deleteRepLog = mnForm5_C_IIPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm5_C_II deleteReportLogs : deleteRepLog) {
			mnForm5_C_IILocalService.deleteMnForm5_C_II(deleteReportLogs);
		}
	}
	
}