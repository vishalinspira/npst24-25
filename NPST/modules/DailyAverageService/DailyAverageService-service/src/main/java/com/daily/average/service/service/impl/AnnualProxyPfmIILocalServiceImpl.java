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

import com.daily.average.service.model.AnnualProxyPfmII;

import com.daily.average.service.service.base.AnnualProxyPfmIILocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnualProxyPfmIILocalServiceImpl
	extends AnnualProxyPfmIILocalServiceBaseImpl {
	public void addAnnualProxyPfmIIs(List<AnnualProxyPfmII> annualProxyPfmIIs) {
		for (Iterator iterator = annualProxyPfmIIs.iterator(); iterator.hasNext();) {
			AnnualProxyPfmII annualProxyPfmII = (AnnualProxyPfmII) iterator.next();
			annualProxyPfmIILocalService.addAnnualProxyPfmII(annualProxyPfmII);
		}
	}
	
	public void deleteAnnualProxyPfmIIByReportUploadLogId(Long reportUploadLogId) {
		  List<AnnualProxyPfmII> annualProxyPfmIIs =
				  annualProxyPfmIIPersistence.findByReportUploadLogId(reportUploadLogId);
		  for(AnnualProxyPfmII annualProxyPfmII : annualProxyPfmIIs) {
			  annualProxyPfmIILocalService.deleteAnnualProxyPfmII(annualProxyPfmII); } }
}