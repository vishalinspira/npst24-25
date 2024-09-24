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

import com.daily.average.service.model.Qr_Report;

import com.daily.average.service.service.base.Qr_ReportLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class Qr_ReportLocalServiceImpl extends Qr_ReportLocalServiceBaseImpl {
	
	public void addQr_Reports(List<Qr_Report> qr_Reports) {
		for (Iterator iterator = qr_Reports.iterator(); iterator.hasNext();) {
			Qr_Report qr_Report = (Qr_Report) iterator.next();
			qr_ReportLocalService.addQr_Report(qr_Report);
		}
	}
	
	public void deleteQr_ReportByReportUploadLogId(Long reportUploadLogId) {
		  List<Qr_Report> qr_Reports =
				  qr_ReportPersistence.findByReportUploadLogId(reportUploadLogId);
		  for(Qr_Report qr_Report : qr_Reports) {
			  qr_ReportLocalService.deleteQr_Report(qr_Report); } }
	
}