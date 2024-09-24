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

import com.daily.average.service.model.AuditorCertificate;
import com.daily.average.service.service.AuditorCertificateLocalServiceUtil;
import com.daily.average.service.service.base.AuditorCertificateLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AuditorCertificateLocalServiceImpl
	extends AuditorCertificateLocalServiceBaseImpl {
	
	public AuditorCertificate addAuditorCertificate(String name, String amount,String compname, String characc, String regno, String compartner, String memberno, String date, String place,
			 long createdBy, Date createDate, long modifyBy, Date modifyDate, int status, String userName) {
		
		AuditorCertificate auditorCertificate = AuditorCertificateLocalServiceUtil.createAuditorCertificate
				(CounterLocalServiceUtil.increment(AuditorCertificate.class.getName()));
		
		// Audit Fields
		auditorCertificate.setCreatedBy(createdBy);
		auditorCertificate.setCreateDate(createDate);
		auditorCertificate.setModifyBy(modifyBy);
		auditorCertificate.setModifyDate(modifyDate);
				
		// Workflow Status Columns
		auditorCertificate.setStatus(status);
		auditorCertificate.setStatusByUserId(createdBy);
		auditorCertificate.setStatusByUserName(userName);
		auditorCertificate.setStatusDate(createDate);
		
		// Other Fields
		auditorCertificate.setName(name);
		auditorCertificate.setAmount(amount);
		auditorCertificate.setCompname(compname);
		auditorCertificate.setCharacc(characc);
		auditorCertificate.setRegno(regno);
		auditorCertificate.setCompartner(compartner);
		auditorCertificate.setMemberno(memberno);
		auditorCertificate.setDate(date);
		auditorCertificate.setPlace(place);
	
		return auditorCertificatePersistence.update(auditorCertificate);
	}
	
	public AuditorCertificate updateAuditorCertificateStatus(long id, long userId, int status, ServiceContext serviceContext) {
		AuditorCertificate auditorCertificate = null;
		try {
			auditorCertificate = auditorCertificatePersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(auditorCertificate)) {
			auditorCertificate.setStatus(status);
			auditorCertificate.setStatusByUserId(userId);
			auditorCertificate.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				auditorCertificate.setStatusByUserName(user.getFullName());
				auditorCertificate.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			auditorCertificate = auditorCertificatePersistence.update(auditorCertificate);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(AuditorCertificate.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(AuditorCertificate.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return auditorCertificate;
	}
	Log _log = LogFactoryUtil.getLog(getClass());
}