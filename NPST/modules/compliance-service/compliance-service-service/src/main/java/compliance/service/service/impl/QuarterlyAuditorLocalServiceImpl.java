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

import compliance.service.model.QuarterlyAuditor;
import compliance.service.service.base.QuarterlyAuditorLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterlyAuditorLocalServiceImpl extends QuarterlyAuditorLocalServiceBaseImpl {
	
	public QuarterlyAuditor addQuarterlyAuditor(Date performantDate, String bankName, String numberOfCases, String amount, 
												Date listOfAmountDate, String companyName, String designation, String regNo, 
												String partnerCompanyName, String partnerMemNo, Date submitDate, String place, long createdBy, Date createDate, long modifyBy, Date modifyDate, int status, String userName) {
		

		QuarterlyAuditor quarterlyAuditor = quarterlyAuditorPersistence.create(counterLocalService.increment());
		
		// Audit Fields
		quarterlyAuditor.setCreatedBy(createdBy);
		quarterlyAuditor.setCreateDate(createDate);
		quarterlyAuditor.setModifyBy(modifyBy);
		quarterlyAuditor.setModifyDate(modifyDate);
		
		// Workflow Status Columns
		quarterlyAuditor.setStatus(status);
		quarterlyAuditor.setStatusByUserId(createdBy);
		quarterlyAuditor.setStatusByUserName(userName);
		quarterlyAuditor.setStatusDate(createDate);
		
		//  Other Fields
		quarterlyAuditor.setPerformantdate(performantDate);
		quarterlyAuditor.setBankname(bankName);
		quarterlyAuditor.setNumberofcases(numberOfCases);
		quarterlyAuditor.setAmount(amount);
		quarterlyAuditor.setListofamountdate(listOfAmountDate);
		quarterlyAuditor.setCompanyname(companyName);
		quarterlyAuditor.setDesignation(designation);
		quarterlyAuditor.setRegno(regNo);
		quarterlyAuditor.setPartnercompanyname(partnerCompanyName);
		quarterlyAuditor.setPartnermemno(partnerMemNo);
		quarterlyAuditor.setSubmitdate(submitDate);
		quarterlyAuditor.setPlace(place);
		
		quarterlyAuditor = quarterlyAuditorPersistence.update(quarterlyAuditor);
		
		return quarterlyAuditor;
		
	}
	
	public QuarterlyAuditor updateQuarterlyAuditorStatus(long id, long userId, int status, ServiceContext serviceContext) {
		QuarterlyAuditor quarterlyAuditor = null;
		try {
			quarterlyAuditor = quarterlyAuditorPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(quarterlyAuditor)) {
			quarterlyAuditor.setStatus(status);
			quarterlyAuditor.setStatusByUserId(userId);
			quarterlyAuditor.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				quarterlyAuditor.setStatusByUserName(user.getFullName());
				quarterlyAuditor.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			quarterlyAuditor = quarterlyAuditorPersistence.update(quarterlyAuditor);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(QuarterlyAuditor.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(QuarterlyAuditor.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return quarterlyAuditor;
	}
	static Log _log = LogFactoryUtil.getLog(QuarterlyAuditorLocalServiceImpl.class);

}