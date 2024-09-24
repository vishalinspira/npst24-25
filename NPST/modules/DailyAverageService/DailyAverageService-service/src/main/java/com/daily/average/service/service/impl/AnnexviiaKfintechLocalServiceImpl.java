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

import com.daily.average.service.model.AnnexviiaKfintech;
import com.daily.average.service.model.AnnexviiaKfintech;
import com.daily.average.service.service.base.AnnexviiaKfintechLocalServiceBaseImpl;


import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexviiaKfintechLocalServiceImpl
	extends AnnexviiaKfintechLocalServiceBaseImpl {
	public void addAnnexviiaKfintechs(List<AnnexviiaKfintech> annexviiaKfintechs) {
		for (Iterator iterator = annexviiaKfintechs.iterator();iterator.hasNext();) {
			AnnexviiaKfintech annexviiaKfintech = (AnnexviiaKfintech) iterator.next();
			annexviiaKfintechLocalService.addAnnexviiaKfintech(annexviiaKfintech);
		}
	}
	
	  public void deleteAnnexviiaKfintechByReportUploadLogId(Long
	  reportUploadLogId) { List<AnnexviiaKfintech> annexviiaKfintechs =
	  annexviiaKfintechPersistence.findByReportUploadLogId(reportUploadLogId);
	  for(AnnexviiaKfintech annexviiaKfintech : annexviiaKfintechs) {
	  annexviiaKfintechLocalService.deleteAnnexviiaKfintech(annexviiaKfintech); } }
	 
}