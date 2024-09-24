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

import com.daily.average.service.model.AccountStatementTransaction;
import com.daily.average.service.service.AccountStatementTransactionLocalServiceUtil;
import com.daily.average.service.service.base.AccountStatementTransactionLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountStatementTransactionLocalServiceImpl extends AccountStatementTransactionLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	/**
	 * 
	 * @param accid
	 * @param trandate
	 * @param chqno
	 * @param particulars
	 * @param debit
	 * @param credit
	 * @param balance
	 * @param initbr
	 * @return
	 */
	public AccountStatementTransaction addAccountStatementTransaction(long accid, Date trandate, long chqno,
			long particulars, long debit, long credit, long balance, long initbr) {
		
		AccountStatementTransaction accountStatementTransaction = AccountStatementTransactionLocalServiceUtil
				.createAccountStatementTransaction(
						CounterLocalServiceUtil.increment(AccountStatementTransaction.class.getName()));
		accountStatementTransaction.setAccid(accid);
		accountStatementTransaction.setTrandate(trandate);
		accountStatementTransaction.setChqno(chqno);
		accountStatementTransaction.setParticulars(particulars);
		accountStatementTransaction.setDebit(debit);
		accountStatementTransaction.setCredit(credit);
		accountStatementTransaction.setBalance(balance);
		accountStatementTransaction.setInitbr(initbr);
		AccountStatementTransactionLocalServiceUtil.addAccountStatementTransaction(accountStatementTransaction);
		_log.info(accountStatementTransaction);
		return accountStatementTransaction;
	}
}