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

import compliance.service.model.MnForm4_AtalPensionYojana;
import compliance.service.service.base.MnForm4_AtalPensionYojanaLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm4_AtalPensionYojanaLocalServiceImpl extends MnForm4_AtalPensionYojanaLocalServiceBaseImpl {
	
	public void addMnForm4_AtalPensionYojana(List<MnForm4_AtalPensionYojana> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm4_AtalPensionYojana form = (MnForm4_AtalPensionYojana) iterator.next();
			mnForm4_AtalPensionYojanaLocalService.addMnForm4_AtalPensionYojana(form);
		}
			
	}
	
	public void deleteMnForm4_AtalPensionYojanaByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm4_AtalPensionYojana> deleteRepLog = mnForm4_AtalPensionYojanaPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm4_AtalPensionYojana deleteReportLogs : deleteRepLog) {
			mnForm4_AtalPensionYojanaLocalService.deleteMnForm4_AtalPensionYojana(deleteReportLogs);
		}
	}
	
}