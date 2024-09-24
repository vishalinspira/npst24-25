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

import com.daily.pfm.service.model.ComplianceCertificateMonthIII;
import com.daily.pfm.service.service.base.ComplianceCertificateMonthIIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ComplianceCertificateMonthIIILocalServiceImpl
	extends ComplianceCertificateMonthIIILocalServiceBaseImpl {
	/**
	 * 
	 * @param portfolio
	 * @param isin
	 * @param description
	 * @param rupee
	 * @param compliancecertificatemonthid
	 * @param createdby
	 */
	public void addComplianceCertificateMonthIII(String portfolio, String isin, String description, String rupee, long compliancecertificatemonthid, long createdby) {
		ComplianceCertificateMonthIII complianceCertificateMonthIII =complianceCertificateMonthIIILocalService.createComplianceCertificateMonthIII(CounterLocalServiceUtil.increment(ComplianceCertificateMonthIII.class.getName()));
		complianceCertificateMonthIII.setPortfolio(portfolio);
		complianceCertificateMonthIII.setIsin(isin);
		complianceCertificateMonthIII.setDescription(description);
		complianceCertificateMonthIII.setRupee(rupee);
		complianceCertificateMonthIII.setCompliancecertificatemonthid(compliancecertificatemonthid);
		complianceCertificateMonthIII.setCreatedby(createdby);
		Date createdate = new Date();
		complianceCertificateMonthIII.setCreatedate(createdate);
		complianceCertificateMonthIIILocalService.addComplianceCertificateMonthIII(complianceCertificateMonthIII);
	}
}