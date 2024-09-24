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

import com.daily.average.service.model.CustAnnualReportscrutiny;
import com.daily.average.service.service.base.CustAnnualReportscrutinyLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CustAnnualReportscrutinyLocalServiceImpl
	extends CustAnnualReportscrutinyLocalServiceBaseImpl {
	
	public CustAnnualReportscrutiny saveCustAnnualReportscrutiny(long reportUploadLogId,String username,double version,long userid,String cust_report_remarks,String audit_pdf_file_remarks,Date createdon,long createdby) {
		 
		CustAnnualReportscrutiny custannualScrutiny = custAnnualReportscrutinyPersistence.create(counterLocalService.increment());
		long id_ = counterLocalService.increment(CustAnnualReportscrutiny.class.getName());
		
		//annualComp.setReportUploadLogId(id_);
		
		
		custannualScrutiny.setReportUploadLogId(reportUploadLogId);
		custannualScrutiny.setUserid(userid);
		custannualScrutiny.setUsername(username);
		custannualScrutiny.setVersion(version);
	
		custannualScrutiny.setCust_report_remarks(cust_report_remarks);
		custannualScrutiny.setAudit_pdf_file_remarks(audit_pdf_file_remarks);
		
		_log.info("custannualScrutiny:::::::::::::::::::::::::::::"+custannualScrutiny.toString());
		
		return custAnnualReportscrutinyPersistence.update(custannualScrutiny);
	}
 public List<CustAnnualReportscrutiny> findCustAnnualReportscrutinyByReportUplaodLogId(long reportUploadLogId){
	return custAnnualReportscrutinyPersistence.findByReportUploadLogId(reportUploadLogId);
	 
 }
 Log _log = LogFactoryUtil.getLog(getClass());
}