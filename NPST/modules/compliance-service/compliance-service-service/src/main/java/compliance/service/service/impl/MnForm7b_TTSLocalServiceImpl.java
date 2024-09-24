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

import compliance.service.model.MnForm1;
import compliance.service.model.MnForm7b_TTS;
import compliance.service.service.base.MnForm7b_TTSLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class MnForm7b_TTSLocalServiceImpl extends MnForm7b_TTSLocalServiceBaseImpl {
	
	public void addMnForm7b_TTS(List<MnForm7b_TTS> formList) {
		
		for(Iterator iterator = formList.iterator(); iterator.hasNext();) {
			MnForm7b_TTS form = (MnForm7b_TTS) iterator.next();
			mnForm7b_TTSLocalService.addMnForm7b_TTS(form);
		}
			
	}
	
	public void deleteMnForm7b_TTSByReportUploadLogId(Long reportUploadLogId) {
		List<MnForm7b_TTS> deleteRepLog = mnForm7b_TTSPersistence.findByReportUploadLogId(reportUploadLogId);
		for(MnForm7b_TTS deleteReportLogs : deleteRepLog) {
			mnForm7b_TTSLocalService.deleteMnForm7b_TTS(deleteReportLogs);
		}
	}
	
}