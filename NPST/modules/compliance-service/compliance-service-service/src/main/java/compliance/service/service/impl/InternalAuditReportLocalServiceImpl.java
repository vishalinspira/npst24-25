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

import compliance.service.model.InternalAuditReport;
import compliance.service.service.base.InternalAuditReportLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InternalAuditReportLocalServiceImpl
	extends InternalAuditReportLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InternalAuditReport addInternalAuditReport(String reportname,Date reportDate,String tablename,String sampling,String particulars,
			String auditorscomments,String status,String riskrating,String managementresponses,Date createdate, long createdby) {
		
		InternalAuditReport iar= internalAuditReportPersistence.create(counterLocalService.increment(InternalAuditReport.class.getName()));
		

		iar.setReportname(reportname);
		iar.setReportDate(reportDate);
		iar.setTablename(tablename);
		iar.setSampling(sampling);
		iar.setParticulars(particulars);
		iar.setAuditorscomments(auditorscomments);
		iar.setStatus(status);
		iar.setRiskrating(riskrating);
		iar.setManagementresponses(managementresponses);
		iar.setCreatedate(createdate);
		iar.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iar.toString());
		return internalAuditReportPersistence.update(iar) ;
	}
}