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

import compliance.service.model.InternalAuditReportIV;
import compliance.service.service.base.InternalAuditReportIVLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InternalAuditReportIVLocalServiceImpl
	extends InternalAuditReportIVLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InternalAuditReportIV addInternalAuditReportiv(String detailsofaveragebank,Date quarterdate,String schemeiv,
			long july,long august,long september ,Date createdate, long createdby) {
		
		InternalAuditReportIV iariv= internalAuditReportIVPersistence.create(counterLocalService.increment(InternalAuditReportIV.class.getName()));
		
		iariv.setDetailsofaveragebank(detailsofaveragebank);
		iariv.setQuarterdate(quarterdate);
		iariv.setSchemeiv(schemeiv);
		iariv.setJuly(july);
		iariv.setAugust(august);
		iariv.setSeptember(september);
		iariv.setCreatedate(createdate);
		iariv.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iariv.toString());
		return internalAuditReportIVPersistence.update(iariv) ;
}
}