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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;

import compliance.service.model.InternalAuditReportII;
import compliance.service.service.base.InternalAuditReportIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InternalAuditReportIILocalServiceImpl
	extends InternalAuditReportIILocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InternalAuditReportII addInternalAuditReportii(String annexuremonthend,Date monthdatei,Date monthdateii,Date monthdateiii,String Scheme,
			String Issuer,String Classification,long marketvalue,long maxpermissable,long deviation,long percentagedeviation,Date createdate, long createdby) {
		
		InternalAuditReportII iarii= internalAuditReportIIPersistence.create(counterLocalService.increment(InternalAuditReportII.class.getName()));
		
		iarii.setAnnexuremonthend(annexuremonthend);
		iarii.setMonthdatei(monthdatei);
		iarii.setMonthdateii(monthdateii);
		iarii.setMonthdateiii(monthdateiii);
		iarii.setScheme(Scheme);
		iarii.setIssuer(Issuer);
		iarii.setClassification(Classification);
		iarii.setMarketvalue(marketvalue);
		iarii.setMaxpermissable(maxpermissable);
		iarii.setDeviation(deviation);
		iarii.setPercentagedeviation(percentagedeviation);
		iarii.setCreatedate(createdate);
		iarii.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iarii.toString());
		return internalAuditReportIIPersistence.update(iarii) ;
}
}