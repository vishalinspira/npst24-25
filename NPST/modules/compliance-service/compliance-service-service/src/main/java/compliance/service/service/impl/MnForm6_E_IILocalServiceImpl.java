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

import compliance.service.model.MnForm6_E_II;
import compliance.service.service.base.MnForm6_E_IILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm6_E_IILocalServiceImpl extends MnForm6_E_IILocalServiceBaseImpl {
	
	public void addMnForm6_E_II(List<MnForm6_E_II> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm6_E_II form = (MnForm6_E_II) iterator.next();
			mnForm6_E_IILocalService.addMnForm6_E_II(form);
		}
			
	}
	
	public void deleteMnForm6_E_IIByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm6_E_II> deleteRepLog = mnForm6_E_IIPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm6_E_II deleteReportLogs : deleteRepLog) {
			mnForm6_E_IILocalService.deleteMnForm6_E_II(deleteReportLogs);
		}
	}
	
}