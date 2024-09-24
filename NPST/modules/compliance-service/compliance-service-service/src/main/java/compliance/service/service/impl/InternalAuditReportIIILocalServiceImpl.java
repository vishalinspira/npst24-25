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

import compliance.service.model.InternalAuditReportIII;
import compliance.service.service.base.InternalAuditReportIIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InternalAuditReportIIILocalServiceImpl
	extends InternalAuditReportIIILocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InternalAuditReportIII addInternalAuditReportiii(String detailsofsubmissionof_monthly,String concernedperiod,
			Date disclosuredate,Date duedatedisclosure,Date delay,Date createdate, long createdby) {
		
		InternalAuditReportIII iariii= internalAuditReportIIIPersistence.create(counterLocalService.increment(InternalAuditReportIII.class.getName()));
		
		iariii.setDetailsofsubmissionof_monthly(detailsofsubmissionof_monthly);
		iariii.setConcernedperiod(concernedperiod);
		iariii.setDisclosuredate(disclosuredate);
		iariii.setDuedatedisclosure(duedatedisclosure);
		iariii.setDelay(delay);
		iariii.setCreatedate(createdate);
		iariii.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iariii.toString());
		return internalAuditReportIIIPersistence.update(iariii) ;
}
}