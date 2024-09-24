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

import com.daily.pfm.service.model.Pfm_Qr_IARScrutiny;
import com.daily.pfm.service.service.base.Pfm_Qr_IARScrutinyLocalServiceBaseImpl;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class Pfm_Qr_IARScrutinyLocalServiceImpl
	extends Pfm_Qr_IARScrutinyLocalServiceBaseImpl {
	
	

	public Pfm_Qr_IARScrutiny addPfm_Qr_IARScrutiny(long reportUploadLogId, String username, double mnversion, long userid,String annex_comp_certificate_rem,
			 String annex_comp_certificate_rem_i, String annex_comp_certificate_rem_ii, String annex_comp_certificate_rem_iii, String extracts_board_pdf_rem, String nps_comments) {
		
		Pfm_Qr_IARScrutiny  pfm_Qr_IARScrutiny = pfm_Qr_IARScrutinyPersistence.create(counterLocalService.increment(Pfm_Qr_IARScrutiny.class.getName()));
		
		
		pfm_Qr_IARScrutiny.setReportUploadLogId(reportUploadLogId);
//		darScrutiny.setScrutinyid(scrutinyid);
		pfm_Qr_IARScrutiny.setUsername(username);
		pfm_Qr_IARScrutiny.setVersion(mnversion);
		pfm_Qr_IARScrutiny.setUserid(userid);
		
		pfm_Qr_IARScrutiny.setAnnex_comp_certificate_rem(annex_comp_certificate_rem);
		pfm_Qr_IARScrutiny.setAnnex_comp_certificate_rem_i(annex_comp_certificate_rem_i);
		pfm_Qr_IARScrutiny.setAnnex_comp_certificate_rem_ii(annex_comp_certificate_rem_ii);
		pfm_Qr_IARScrutiny.setAnnex_comp_certificate_rem_iii(annex_comp_certificate_rem_iii);
		pfm_Qr_IARScrutiny.setExtracts_board_pdf_rem(extracts_board_pdf_rem);
		pfm_Qr_IARScrutiny.setNps_comments(nps_comments);
		
		return pfm_Qr_IARScrutinyPersistence.update(pfm_Qr_IARScrutiny);
	}
	
	public List<Pfm_Qr_IARScrutiny> findByReportUploadLogId(long reportUploadLogId){
		
		return pfm_Qr_IARScrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	}
}