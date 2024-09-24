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

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.AnnualCompCertificateScrutiny;
import com.daily.average.service.service.base.AnnualCompCertificateScrutinyLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnualCompCertificateScrutinyLocalServiceImpl
	extends AnnualCompCertificateScrutinyLocalServiceBaseImpl {
	
	public AnnualCompCertificateScrutiny saveAnnualCompCertificateScrutiny(long reportUploadLogId,String username,double version,long userid,String eligibilityia_rem,String eligibilityib_rem,String eligibilityic_rem,String eligibilityid_rem,String eligibilityie_rem,String eligibilityif_rem,
			String eligibilityig_rem,String eligibilityih_rem,String eligibilityii_rem,String eligibilityij_rem,String eligibilityik_rem,String eligibilityil_rem,String eligibilityim_rem,String eligibilityin_rem,String eligibilityio_rem,
			String eligibilityip_rem,String eligibilityiq_rem,String eligibilityir_rem,String eligibilityis_rem,String booka_rem,String bookb_rem,String bookc_rem,String audita_rem,String auditb_rem,String auditc_rem,
			String stewardshipa_rem,String stewardshipb_rem,String stewardshipc_rem,String othersa_rem,String othersb_rem,String othersc_rem,String othersd_rem,String otherse_rem,String othersf_rem,
			String annexurei_rem,String annexureii_rem,String annexureiii_rem,String annexureiv_rem,String annexurev_rem,Date createdate,long createdby,String npsRemark) {
		 
		AnnualCompCertificateScrutiny annualCompS = annualCompCertificateScrutinyPersistence.create(counterLocalService.increment());
		long id_ = counterLocalService.increment(AnnualCompCertificate.class.getName());
		
		//annualComp.setReportUploadLogId(id_);
		
		
		annualCompS.setReportUploadLogId(reportUploadLogId);
		annualCompS.setUserid(userid);
		annualCompS.setUsername(username);
		annualCompS.setVersion(version);
		
		//eligibility::::::::::::::::
		annualCompS.setEligibilityia_rem(eligibilityia_rem);
		annualCompS.setEligibilityib_rem(eligibilityib_rem);
		annualCompS.setEligibilityic_rem(eligibilityic_rem);
		annualCompS.setEligibilityid_rem(eligibilityid_rem);
		annualCompS.setEligibilityie_rem(eligibilityie_rem);
		annualCompS.setEligibilityif_rem(eligibilityif_rem);
		annualCompS.setEligibilityig_rem(eligibilityig_rem);
		annualCompS.setEligibilityih_rem(eligibilityih_rem);
		annualCompS.setEligibilityii_rem(eligibilityii_rem);
		annualCompS.setEligibilityij_rem(eligibilityij_rem);
		annualCompS.setEligibilityik_rem(eligibilityik_rem);
		annualCompS.setEligibilityil_rem(eligibilityil_rem);
		annualCompS.setEligibilityim_rem(eligibilityim_rem);
		annualCompS.setEligibilityin_rem(eligibilityin_rem);
		annualCompS.setEligibilityio_rem(eligibilityio_rem);
		annualCompS.setEligibilityip_rem(eligibilityip_rem);
		annualCompS.setEligibilityiq_rem(eligibilityiq_rem);
		annualCompS.setEligibilityir_rem(eligibilityir_rem);
		annualCompS.setEligibilityis_rem(eligibilityis_rem);
		
		//book
		annualCompS.setBooka_rem(booka_rem);
		annualCompS.setBookb_rem(bookb_rem);
		annualCompS.setBookc_rem(bookc_rem);
		
		//audit
		annualCompS.setAudita_rem(audita_rem);
		annualCompS.setAuditb_rem(auditb_rem);
		annualCompS.setAuditc_rem(auditc_rem);
		
		//stewardshipa
		annualCompS.setStewardshipa_rem(stewardshipa_rem);
		annualCompS.setStewardshipb_rem(stewardshipb_rem);
		annualCompS.setStewardshipc_rem(stewardshipc_rem);
		
		//othres 
		annualCompS.setOthersa_rem(othersa_rem);
		annualCompS.setOthersb_rem(othersb_rem);
		annualCompS.setOthersc_rem(othersc_rem);
		annualCompS.setOthersd_rem(othersd_rem);
		annualCompS.setOtherse_rem(otherse_rem);
		annualCompS.setOthersf_rem(othersf_rem);		
		//documents
		annualCompS.setAnnexurei_rem(annexurei_rem);
		annualCompS.setAnnexureii_rem(annexureii_rem);
		annualCompS.setAnnexureiii_rem(annexureiii_rem);
		annualCompS.setAnnexureiv_rem(annexureiv_rem);
		annualCompS.setAnnexurev_rem(annexurev_rem);
		annualCompS.setNpsRemark(npsRemark);
		
		annualCompS.setCreatedate(createdate);
		annualCompS.setCreatedby(createdby);
		
		_log.info("annualCompS:::::::::::::::::::::::::::::"+annualCompS.toString());
		
		return annualCompCertificateScrutinyPersistence.update(annualCompS);
	}
 public List<AnnualCompCertificateScrutiny> findAnnualCompCertificateScrutinyByReportUplaodLogId(long reportUploadLogId){
	return annualCompCertificateScrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	 
 }
 Log _log = LogFactoryUtil.getLog(getClass());
}