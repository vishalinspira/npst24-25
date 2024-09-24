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

package com.daily.average.service.service.impl;


import com.daily.average.service.model.DARScrutiny;
import com.daily.average.service.service.base.DARScrutinyLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DARScrutinyLocalServiceImpl extends DARScrutinyLocalServiceBaseImpl {
	
	public DARScrutiny addDARScrutiny(String username,double mnversion,long userid, 
			String dar_file_1_rem, String dar_file_2_rem, String management_comments, Date createdon, long createdby, long reportUploadLogId, String dar_file_rem_3, String dar_file_rem_4, String dar_file_rem_5) {
		
		DARScrutiny darScrutiny = darScrutinyPersistence.create(counterLocalService.increment(DARScrutiny.class.getName()));
		
//		long scrutinyid = counterLocalService.increment(CustIARScrutiny.class.getName());
		
		darScrutiny.setReportUploadLogId(reportUploadLogId);
//		darScrutiny.setScrutinyid(scrutinyid);
		darScrutiny.setUsername(username);
		darScrutiny.setVersion(mnversion);
		darScrutiny.setUserid(userid);
		
		darScrutiny.setDar_file_rem_1(dar_file_1_rem);
		darScrutiny.setDar_file_rem_2(dar_file_2_rem);
		darScrutiny.setDar_file_rem_3(dar_file_rem_3);
		darScrutiny.setDar_file_rem_4(dar_file_rem_4);
		darScrutiny.setDar_file_rem_5(dar_file_rem_5);
		darScrutiny.setManagement_comments(management_comments);
		
		darScrutiny.setCreatedon(createdon);
		darScrutiny.setCreatedby(createdby);
		
		return darScrutinyPersistence.update(darScrutiny);

	}
	
	public List<DARScrutiny> findByReportUploadLogId(long reportUploadLogId) {
		return darScrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	}
	
	
}