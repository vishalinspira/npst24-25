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

import compliance.service.model.MnForm6_E_I;
import compliance.service.service.base.MnForm6_E_ILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm6_E_ILocalServiceImpl extends MnForm6_E_ILocalServiceBaseImpl {
	
	public void addMnForm6_E_I(List<MnForm6_E_I> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm6_E_I form = (MnForm6_E_I) iterator.next();
			mnForm6_E_ILocalService.addMnForm6_E_I(form);
		}
			
	}
	
	public void deleteMnForm6_E_IByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm6_E_I> deleteRepLog = mnForm6_E_IPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm6_E_I deleteReportLogs : deleteRepLog) {
			mnForm6_E_ILocalService.deleteMnForm6_E_I(deleteReportLogs);
		}
	}
	
}