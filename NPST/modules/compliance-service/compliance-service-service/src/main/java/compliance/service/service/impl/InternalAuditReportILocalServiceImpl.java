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

import compliance.service.model.InternalAuditReportI;
import compliance.service.service.base.InternalAuditReportILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InternalAuditReportILocalServiceImpl
	extends InternalAuditReportILocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InternalAuditReportI addInternalAuditReporti(String tablename_annexure,Date bordmeetingdate,Date borddate,
			Date committeemeeting,Date riskmeeting,String nameofdirectors,
			String designation,String attendance,Date createdate, long createdby) {
		
		InternalAuditReportI iari= internalAuditReportIPersistence.create(counterLocalService.increment(InternalAuditReportI.class.getName()));
		
		
		iari.setTablename_annexure(tablename_annexure);
		iari.setBordmeetingdate(bordmeetingdate);
		iari.setBorddate(borddate);
		iari.setCommitteemeeting(committeemeeting);
		iari.setRiskmeeting(riskmeeting);
		iari.setNameofdirectors(nameofdirectors);
		iari.setDesignation(designation);
		iari.setAttendance(attendance);
		iari.setCreatedate(createdate);
		iari.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iari.toString());
		return internalAuditReportIPersistence.update(iari) ;
}
}