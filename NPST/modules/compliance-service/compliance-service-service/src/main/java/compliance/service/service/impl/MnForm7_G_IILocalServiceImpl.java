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

import compliance.service.model.MnForm7_G_II;
import compliance.service.service.base.MnForm7_G_IILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm7_G_IILocalServiceImpl extends MnForm7_G_IILocalServiceBaseImpl {
	
	public void addMnForm7_G_II(List<MnForm7_G_II> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm7_G_II form = (MnForm7_G_II) iterator.next();
			mnForm7_G_IILocalService.addMnForm7_G_II(form);
		}
			
	}
	
	public void deleteMnForm7_G_IIByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm7_G_II> deleteRepLog = mnForm7_G_IIPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm7_G_II deleteReportLogs : deleteRepLog) {
			mnForm7_G_IILocalService.deleteMnForm7_G_II(deleteReportLogs);
		}
	}
	
}