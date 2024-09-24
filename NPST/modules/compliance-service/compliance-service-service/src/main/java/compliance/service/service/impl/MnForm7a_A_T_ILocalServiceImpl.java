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

import compliance.service.model.MnForm7a_A_T_I;
import compliance.service.service.base.MnForm7a_A_T_ILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm7a_A_T_ILocalServiceImpl extends MnForm7a_A_T_ILocalServiceBaseImpl {
	
	public void addMnForm7a_A_T_I(List<MnForm7a_A_T_I> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm7a_A_T_I form = (MnForm7a_A_T_I) iterator.next();
			mnForm7a_A_T_ILocalService.addMnForm7a_A_T_I(form);
		}
			
	}
	
	public void deleteMnForm7a_A_T_IByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm7a_A_T_I> deleteRepLog = mnForm7a_A_T_IPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm7a_A_T_I deleteReportLogs : deleteRepLog) {
			mnForm7a_A_T_ILocalService.deleteMnForm7a_A_T_I(deleteReportLogs);
		}
	}
	
}