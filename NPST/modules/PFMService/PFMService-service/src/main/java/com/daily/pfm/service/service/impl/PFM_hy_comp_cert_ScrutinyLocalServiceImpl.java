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

package com.daily.pfm.service.service.impl;

import com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny;
import com.daily.pfm.service.service.base.PFM_hy_comp_cert_ScrutinyLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PFM_hy_comp_cert_ScrutinyLocalServiceImpl
	extends PFM_hy_comp_cert_ScrutinyLocalServiceBaseImpl {
	
	public PFM_hy_comp_cert_Scrutiny addPFM_hy_comp_cert_Cert_Scrutiny(long reportUploadLogId, String username, double mnversion, long userid, String nps_comments) {
		
		PFM_hy_comp_cert_Scrutiny  pfm_hy_comp_cert_Scrutiny =pfm_hy_comp_cert_ScrutinyPersistence.create(counterLocalService.increment(PFM_hy_comp_cert_Scrutiny.class.getName()));
		
		
		pfm_hy_comp_cert_Scrutiny.setReportUploadLogId(reportUploadLogId);
//		darScrutiny.setScrutinyid(scrutinyid);
		pfm_hy_comp_cert_Scrutiny.setUsername(username);
		pfm_hy_comp_cert_Scrutiny.setVersion(mnversion);
		pfm_hy_comp_cert_Scrutiny.setUserid(userid);
		
		
		pfm_hy_comp_cert_Scrutiny.setNps_comments(nps_comments);
		
		return pfm_hy_comp_cert_ScrutinyPersistence.update(pfm_hy_comp_cert_Scrutiny);
	}
	
	public List<PFM_hy_comp_cert_Scrutiny> findByReportUploadLogId(long reportUploadLogId){
		
		return pfm_hy_comp_cert_ScrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	}
}