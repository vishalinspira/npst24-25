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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

import compliance.service.model.Compliance_Cert;
import compliance.service.service.base.Compliance_CertLocalServiceBaseImpl;

/**
 * The implementation of the compliance_ cert local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>compliance.service.service.Compliance_CertLocalService</code>
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Compliance_CertLocalServiceBaseImpl
 */
public class Compliance_CertLocalServiceImpl extends Compliance_CertLocalServiceBaseImpl {
	
	public Compliance_Cert addComplIanceInfo(String certNumber, Date complianceDate, Date compfyendingDate, Date tbfyendingDate, 
			String tb_PFRDA_Act_complied, String tb_PFRDA_Act_remarks,
			String tb_review_complied, String tb_review_remarks, 
			String tb_investment_complied, String tb_investment_remarks, 
			String tb_communication_complied, String tb_communication_remarks,
			String tb_submitted_complied, String tb_submitted_remarks, 
			String tb_observations_complied, String tb_observations_remarks, 
			String tb_subscribers_complied, String tb_subscribers_remarks,
			String ma_auditors_complied, String ma_auditors_remarks,
			String ma_compliance_complied, String ma_compliance_remarks, 
			String ma_observations_complied, String ma_observations_remarks,
			String mo_agreements_complied, String mo_agreements_remarks,
			String mo_found_complied, String mo_found_remarks, 
			String mo_segregated_complied, String mo_segregated_remarks, 
			String mo_diligence_complied, String mo_diligence_remarks,
			String pa_custody_complied, String pa_custody_remarks,
			String pa_protected_complied, String pa_protected_remarks, 
			String pa_supervising_complied, String pa_supervising_remarks, 
			String pa_accounts_complied, String pa_accounts_remarks,
			String nto_maintaining_complied, String nto_maintaining_remarks,
			String nto_adequate_steps_complied, String nto_adequate_steps_remarks, 
			String nto_information_complied, String nto_information_remarks, 
			String nto_grievances_complied, String nto_grievances_remarks,
			String nto_exit_complied, String nto_exit_remarks,
			String aa_annual_report_complied, String aa_annual_report_remarks,
			String aa_proper_books_complied, String aa_proper_books_remarks, 
			String aa_quarterly_info_complied, String aa_quarterly_info_remarks, 
			String aa_rules_complied, String aa_rules_remarks,
			String compOfficerName1, Date signDate1, String place1,
			String compOfficerName2, Date signDate2, String place2, long createdBy, Date createDate, long modifyBy, Date modifyDate, int status, String userName) {
				
		Compliance_Cert compCert = compliance_CertPersistence.create(counterLocalService.increment());
		compCert.setCertnumber(certNumber);
		compCert.setCompliancedate(complianceDate);
		compCert.setCompfyendingdate(compfyendingDate);
		compCert.setTbfyendingdate(tbfyendingDate);
		
		compCert.setTbpfrdacomp(tb_PFRDA_Act_complied);
		compCert.setTbpfrdarem(tb_PFRDA_Act_remarks);
		compCert.setTbreviewcomp(tb_review_complied);
		compCert.setTbreviewrem(tb_review_remarks);
		compCert.setTbinvestcomp(tb_investment_complied);
		compCert.setTbinvestrem(tb_investment_remarks);
		compCert.setTbcommcomp(tb_communication_complied);
		compCert.setTbcommrem(tb_communication_remarks);
		compCert.setTbsubmitcomp(tb_submitted_complied);
		compCert.setTbsubmitrem(tb_submitted_remarks);
		compCert.setTbobservecomp(tb_observations_complied);
		compCert.setTbobserverem(tb_observations_remarks);
		compCert.setTbsubscribecomp(tb_subscribers_complied);
		compCert.setTbsubscriberem(tb_subscribers_remarks);
		
		compCert.setMaauditorscomp(ma_auditors_complied);
		compCert.setMaauditorsrem(ma_auditors_remarks);
		compCert.setMacompliancecomp(ma_compliance_complied);
		compCert.setMacompliancerem(ma_compliance_remarks);
		compCert.setMaobservecomp(ma_observations_complied);
		compCert.setMaobserverem(ma_observations_remarks);
		
		compCert.setMoagreecomp(mo_agreements_complied);
		compCert.setMoagreerem(mo_agreements_remarks);
		compCert.setMofoundcomp(mo_found_complied);
		compCert.setMofoundrem(mo_found_remarks);
		compCert.setMosegcomp(mo_segregated_complied);
		compCert.setMosegrem(mo_segregated_remarks);
		compCert.setModilicomp(mo_diligence_complied);
		compCert.setModilirem(mo_diligence_remarks);
		
		compCert.setPacustodycomp(pa_custody_complied);
		compCert.setPacustodyrem(pa_custody_remarks);
		compCert.setPaprotectedcomp(pa_protected_complied);
		compCert.setPaprotectedrem(pa_protected_remarks);
		compCert.setPasupervisecomp(pa_supervising_complied);
		compCert.setPasuperviserem(pa_supervising_remarks);
		compCert.setPaaccountscomp(pa_accounts_complied);
		compCert.setPaaccountsrem(pa_accounts_remarks);
		
		compCert.setPacustodycomp(pa_custody_complied);
		compCert.setPacustodyrem(pa_custody_remarks);
		compCert.setPaprotectedcomp(pa_protected_complied);
		compCert.setPaprotectedrem(pa_protected_remarks);
		compCert.setPasupervisecomp(pa_supervising_complied);
		compCert.setPasuperviserem(pa_supervising_remarks);
		compCert.setPaaccountscomp(pa_accounts_complied);
		compCert.setPaaccountsrem(pa_accounts_remarks);
		
		compCert.setNtomaintaincomp(nto_maintaining_complied);
		compCert.setNtomaintainrem(nto_maintaining_remarks);
		compCert.setNtoadequatecomp(nto_adequate_steps_complied);
		compCert.setNtoadequaterem(nto_adequate_steps_remarks);
		compCert.setNtoinformcomp(nto_information_complied);
		compCert.setNtoinformrem(nto_information_remarks);
		compCert.setNtogrievancescomp(nto_grievances_complied);
		compCert.setNtogrievancesrem(nto_grievances_remarks);
		compCert.setNtoexitcomp(nto_exit_complied);
		compCert.setNtoexitrem(nto_exit_remarks);
		
		compCert.setAaannualcomp(aa_annual_report_complied);
		compCert.setAaannualrem(aa_annual_report_remarks);
		compCert.setAabookscomp(aa_proper_books_complied);
		compCert.setAabooksrem(aa_proper_books_remarks);
		compCert.setAaquarterlycomp(aa_quarterly_info_complied);
		compCert.setAaquarterlyrem(aa_quarterly_info_remarks);
		compCert.setAarulescomp(aa_rules_complied);
		compCert.setAarulesrem(aa_rules_remarks);
		
		compCert.setCompofficername1(compOfficerName1);
		compCert.setSigndate1(signDate1);
		compCert.setPlace1(place1);
		compCert.setCompofficername2(compOfficerName2);
		compCert.setSigndate2(signDate2);
		compCert.setPlace2(place2);
		
		// Audit Fields
		compCert.setCreatedBy(createdBy);
		compCert.setCreateDate(createDate);
		compCert.setModifyBy(modifyBy);
		compCert.setModifyDate(modifyDate);
				
		// Workflow Status Columns
		compCert.setStatus(status);
		compCert.setStatusByUserId(createdBy);
		compCert.setStatusByUserName(userName);
		compCert.setStatusDate(createDate);
				
		
		compCert = compliance_CertPersistence.update(compCert);
		
		return compCert;
		
	}

	@Override
	public Compliance_Cert updateComplianceCertStatus(long id, long userId, int status, ServiceContext serviceContext) {
		Compliance_Cert compliance_Cert = null;
		try {
			compliance_Cert = compliance_CertPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(compliance_Cert)) {
			compliance_Cert.setStatus(status);
			compliance_Cert.setStatusByUserId(userId);
			compliance_Cert.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				compliance_Cert.setStatusByUserName(user.getFullName());
				compliance_Cert.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			compliance_Cert = compliance_CertPersistence.update(compliance_Cert);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(Compliance_Cert.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(Compliance_Cert.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return compliance_Cert;
	}
	static Log _log = LogFactoryUtil.getLog(Compliance_CertLocalServiceImpl.class);

}