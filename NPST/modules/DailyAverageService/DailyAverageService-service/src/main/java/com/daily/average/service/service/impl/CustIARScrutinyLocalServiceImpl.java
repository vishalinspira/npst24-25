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

import com.daily.average.service.model.CustIARScrutiny;
import com.daily.average.service.service.base.CustIARScrutinyLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CustIARScrutinyLocalServiceImpl
	extends CustIARScrutinyLocalServiceBaseImpl {
	
	public CustIARScrutiny saveCustodianIARScrutiny(String username,double mnversion,long userid, 
			String iar_file_rem, String nps_trust_comments,Date createdon, long createdby, long reportUploadLogId) {
				
		CustIARScrutiny custIARScrutiny = custIARScrutinyPersistence.create(counterLocalService.increment(CustIARScrutiny.class.getName()));
		
//		long scrutinyid = counterLocalService.increment(CustIARScrutiny.class.getName());
		
		custIARScrutiny.setReportUploadLogId(reportUploadLogId);
//		custIARScrutiny.setScrutinyid(scrutinyid);
		custIARScrutiny.setUsername(username);
		custIARScrutiny.setVersion(mnversion);
		custIARScrutiny.setUserid(userid);
		
		custIARScrutiny.setIar_file_rem(iar_file_rem);
		custIARScrutiny.setNps_trust_comments(nps_trust_comments);
		
		custIARScrutiny.setCreatedon(createdon);
		custIARScrutiny.setCreatedby(createdby);
		
		return custIARScrutinyPersistence.update(custIARScrutiny);
		
	}
	
	public List<CustIARScrutiny> findByReportUploadLogId(long reportUploadLogId) {
		return custIARScrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	}
	
	
	
}