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

import compliance.service.model.ClosingBal;
import compliance.service.service.base.ClosingBalLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ClosingBalLocalServiceImpl extends ClosingBalLocalServiceBaseImpl {
	
	public ClosingBal addClosingBal(Date closingdate, String addressLine1, String addressLine2, String addressLine3, 
			Date confirmDate, String accountDetails, String yoursFaithfully, String designation) {
				
		ClosingBal closingBal = closingBalPersistence.create(counterLocalService.increment());
		closingBal.setClosingdate(closingdate);
		closingBal.setAddressline1(addressLine1);
		closingBal.setAddressline2(addressLine2);
		closingBal.setAddressline3(addressLine3);
		closingBal.setConfirmdate(confirmDate);
		closingBal.setAccountdetails(accountDetails);
		closingBal.setYoursfaithfully(yoursFaithfully);
		closingBal.setDesignation(designation);
		
		closingBal = closingBalPersistence.update(closingBal);
		
		return closingBal;
	}

	@Override
	public ClosingBal updateClosingBalStatus(long id, long userId, int status, ServiceContext serviceContext) {
		ClosingBal closingBal = null;
		try {
			closingBal = closingBalPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(closingBal)) {
			closingBal.setStatus(status);
			closingBal.setStatusByUserId(userId);
			closingBal.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				closingBal.setStatusByUserName(user.getFullName());
				closingBal.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			closingBal = closingBalPersistence.update(closingBal);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(ClosingBal.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(ClosingBal.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return closingBal;
	}
	static Log _log = LogFactoryUtil.getLog(ClosingBalLocalServiceImpl.class);
	
}