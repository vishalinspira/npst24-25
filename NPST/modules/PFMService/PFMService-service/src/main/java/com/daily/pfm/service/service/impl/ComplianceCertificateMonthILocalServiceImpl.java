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

import com.daily.pfm.service.model.ComplianceCertificateMonthI;
import com.daily.pfm.service.service.base.ComplianceCertificateMonthILocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ComplianceCertificateMonthILocalServiceImpl
	extends ComplianceCertificateMonthILocalServiceBaseImpl {
	/**
	 * 
	 * @param nonSponsorgroup
	 * @param dateii
	 * @param schemes
	 * @param limit
	 * @param exposer
	 * @param remarks
	 * @param compliancecertificatemonthid
	 * @param createdby
	 */
	public void addComplianceCertificateMonthI(String nonSponsorgroup, String dateii, String schemes, String limit, String exposer, String remarks, long compliancecertificatemonthid, long createdby) {
		ComplianceCertificateMonthI complianceCertificateMonthI =complianceCertificateMonthILocalService.createComplianceCertificateMonthI(CounterLocalServiceUtil.increment(ComplianceCertificateMonthI.class.getName()));
		complianceCertificateMonthI.setNonSponsorgroup(nonSponsorgroup);
		complianceCertificateMonthI.setDateii(dateii);
		complianceCertificateMonthI.setSchemes(schemes);
		complianceCertificateMonthI.setLimit(limit);
		complianceCertificateMonthI.setExposer(exposer);
		complianceCertificateMonthI.setRemarks(remarks);
		complianceCertificateMonthI.setCompliancecertificatemonthid(compliancecertificatemonthid);
		complianceCertificateMonthI.setCreatedby(createdby);
		Date createdate = new Date();
		complianceCertificateMonthI.setCreatedate(createdate);
		
		complianceCertificateMonthILocalService.addComplianceCertificateMonthI(complianceCertificateMonthI);
		
		
	}
}