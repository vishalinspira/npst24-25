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

import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.base.ReportUploadFileLogLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ReportUploadFileLogLocalServiceImpl
	extends ReportUploadFileLogLocalServiceBaseImpl {
	
	/**
	 * 
	 * @param reportUploadLogId
	 * @return
	 */
	public List<ReportUploadFileLog> findByReportUploadLogId(Long reportUploadLogId) {
		return reportUploadFileLogPersistence.findByReportUploadLogId(reportUploadLogId);
	}
	
	/**
	 * 
	 * @param reportUploadLogId
	 * @param fileEntryId
	 * @param createdBy
	 * @return
	 */
	public ReportUploadFileLog addReportUploadFileLog(long reportUploadFileLogId, long fileEntryId, long createdBy) {
		ReportUploadFileLog reportUploadFileLog = reportUploadFileLogLocalService.createReportUploadFileLog(CounterLocalServiceUtil.increment(ReportUploadFileLog.class.getName()));
		reportUploadFileLog.setReportUploadLogId(reportUploadFileLogId);
		reportUploadFileLog.setFileEntryId(fileEntryId);
		reportUploadFileLog.setCreateDate(new Date());
		reportUploadFileLog.setCreatedBy(createdBy);
		
		return reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadFileLog);
	}
	
	/**
	 * @param fileEntryId
	 * @return ReportUploadFileLog
	 */
	public ReportUploadFileLog findByFileEntryId(long fileEntryId) {
		return reportUploadFileLogPersistence.fetchByFileEntryId(fileEntryId);
	}
	
	/**
	 * 
	 * @param reportUploadFileLogId
	 * @param fileEntryId
	 * @param createdBy
	 * @param fileList
	 * @return
	 */
	public ReportUploadFileLog addReportUploadFileLog(long reportUploadFileLogId, long fileEntryId, long createdBy, String fileList) {
		ReportUploadFileLog reportUploadFileLog = reportUploadFileLogLocalService.createReportUploadFileLog(CounterLocalServiceUtil.increment(ReportUploadFileLog.class.getName()));
		reportUploadFileLog.setReportUploadLogId(reportUploadFileLogId);
		reportUploadFileLog.setFileEntryId(fileEntryId);
		reportUploadFileLog.setCreateDate(new Date());
		reportUploadFileLog.setCreatedBy(createdBy);
		reportUploadFileLog.setFileList(fileList);
		
		return reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadFileLog);
	}
}