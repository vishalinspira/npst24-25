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

import com.daily.average.service.model.AccountStatement;
import com.daily.average.service.service.base.AccountStatementLocalServiceBaseImpl;
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
public class AccountStatementLocalServiceImpl extends AccountStatementLocalServiceBaseImpl {

	/**
	 * 
	 * @param accountname
	 * @param statement
	 * @param adressi
	 * @param adressii
	 * @param adressiii
	 * @param adressiv
	 * @param adressv
	 * @param pin
	 * @param customerno
	 * @param scheme
	 * @param curencyinr
	 * @param statementacno
	 * @param formdate
	 * @param todate
	 * @param todebit
	 * @param tocredit
	 * @param clbalance
	 * @param clinitbr
	 * @param createdBy 
	 * @param createDate 
	 * @param modifyBy 
	 * @param modifyDate 
	 * @param status 
	 * @return
	 */
	
	public AccountStatement addAccountStatement(String accountname, String statement, String adressi, String adressii, String adressiii, String adressiv, String adressv, 
			long pin, long customerno, String scheme, String curencyinr, long statementacno, Date formdate, Date todate, long todebit, long tocredit, long clbalance, 
			long clinitbr, long createdBy, Date createDate, long modifyBy, Date modifyDate, int status, String userName) {
		AccountStatement accountStatement = accountStatementPersistence.create(CounterLocalServiceUtil.increment(AccountStatement.class.getName()));

		// Audit Fields
		accountStatement.setCreatedBy(createdBy);
		accountStatement.setCreateDate(createDate);
		accountStatement.setModifyBy(modifyBy);
		accountStatement.setModifyDate(modifyDate);
		
		// Workflow Status Columns
		accountStatement.setStatus(status);
		accountStatement.setStatusByUserId(createdBy);
		accountStatement.setStatusByUserName(userName);
		accountStatement.setStatusDate(createDate);
		
		// Other Fields
		accountStatement.setAccountname(accountname);
		accountStatement.setStatement(statement);
		accountStatement.setAdressi(adressi);
		accountStatement.setAdressii(adressii);
		accountStatement.setAdressiii(adressiii);
		accountStatement.setAdressiv(adressiv);
		accountStatement.setAdressv(adressv);
		accountStatement.setPin(pin);
		accountStatement.setCustomerno(customerno);
		accountStatement.setScheme(scheme);
		accountStatement.setCurencyinr(curencyinr);
		accountStatement.setStatementacno(statementacno);
		accountStatement.setFormdate(formdate);
		accountStatement.setTodate(todate);
		accountStatement.setTodebit(todebit);
		accountStatement.setTocredit(tocredit);
		accountStatement.setClbalance(clbalance);
		accountStatement.setClinitbr(clinitbr);
		
		return accountStatementPersistence.update(accountStatement);
	}

	public AccountStatement updateAccountStatementStatus(long id, long userId, int status, ServiceContext serviceContext) {
		AccountStatement accountStatement = null;
		try {
			accountStatement = accountStatementPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(accountStatement)) {
			accountStatement.setStatus(status);
			accountStatement.setStatusByUserId(userId);
			accountStatement.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				accountStatement.setStatusByUserName(user.getFullName());
				accountStatement.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			accountStatement = accountStatementPersistence.update(accountStatement);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(AccountStatement.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(AccountStatement.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return accountStatement;
	}
	static Log _log = LogFactoryUtil.getLog(AccountStatementLocalServiceImpl.class);

}