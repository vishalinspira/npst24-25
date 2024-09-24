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

import com.daily.pfm.service.model.ComplianceCertificateMonthII;
import com.daily.pfm.service.service.base.ComplianceCertificateMonthIILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ComplianceCertificateMonthIILocalServiceImpl
	extends ComplianceCertificateMonthIILocalServiceBaseImpl {
	/**
	 * 
	 * @param limiti
	 * @param exposeri
	 * @param compliancecertificatemonthid
	 * @param createdby
	 * @param schemei
	 */
	public void addcomplianceCertificateMonthII(String limiti, String exposeri, long compliancecertificatemonthid, long createdby, String schemei) {
		ComplianceCertificateMonthII complianceCertificateMonthII = complianceCertificateMonthIILocalService.createComplianceCertificateMonthII(CounterLocalServiceUtil.increment(ComplianceCertificateMonthII.class.getName()));
		
		complianceCertificateMonthII.setSchemei(schemei);
		complianceCertificateMonthII.setLimiti(limiti);
		complianceCertificateMonthII.setExposeri(exposeri);
		complianceCertificateMonthII.setCompliancecertificatemonthid(compliancecertificatemonthid);
		complianceCertificateMonthII.setCreatedby(createdby);
		Date createdate = new Date();
		complianceCertificateMonthII.setCreatedate(createdate);
		complianceCertificateMonthIILocalService.addComplianceCertificateMonthII(complianceCertificateMonthII);
	}
}