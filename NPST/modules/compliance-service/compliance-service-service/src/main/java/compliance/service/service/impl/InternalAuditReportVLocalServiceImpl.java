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

import compliance.service.model.InternalAuditReportV;
import compliance.service.service.base.InternalAuditReportVLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InternalAuditReportVLocalServiceImpl
	extends InternalAuditReportVLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InternalAuditReportV addInternalAuditReportv(String statusofnonperforming,Date assetsdate,String nameofcompany,
			String statusasonjun,String statusasonsep,Date createdate, long createdby) {
		
		InternalAuditReportV iarv= internalAuditReportVPersistence.create(counterLocalService.increment(InternalAuditReportV.class.getName()));
		
		iarv.setStatusofnonperforming(statusofnonperforming);
		iarv.setAssetsdate(assetsdate);
		iarv.setNameofcompany(nameofcompany);
		iarv.setStatusasonjun(statusasonjun);
        iarv.setStatusasonsep(statusasonsep);
		iarv.setCreatedate(createdate);
		iarv.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iarv.toString());
		return internalAuditReportVPersistence.update(iarv) ;
}
}