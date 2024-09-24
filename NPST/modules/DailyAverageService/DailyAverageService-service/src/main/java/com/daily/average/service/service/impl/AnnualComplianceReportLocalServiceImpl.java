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

import com.daily.average.service.model.AnnualComplianceReport;
import com.daily.average.service.service.base.AnnualComplianceReportLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnualComplianceReportLocalServiceImpl
	extends AnnualComplianceReportLocalServiceBaseImpl {
	
	public AnnualComplianceReport addAnnualComplianceReport(String annual_compliance_report_of, String details, String information_submitted,String comments,Date createdate, long createdby) {
		
		AnnualComplianceReport acr = annualComplianceReportPersistence.create(counterLocalService.increment(AnnualComplianceReport.class.getName()));
		
		acr.setAnnual_compliance_report_of(annual_compliance_report_of);
		acr.setDetails(details);
		acr.setInformation_submitted(information_submitted);
		acr.setComments(comments);
		acr.setCreatedate(createdate);
		acr.setCreatedby(createdby);
		
		return annualComplianceReportPersistence.update(acr);
	}
}