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

import com.daily.average.service.model.AnnexVI;
import com.daily.average.service.model.DailyAverage;
import com.daily.average.service.service.base.AnnexVILocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexVILocalServiceImpl extends AnnexVILocalServiceBaseImpl {
	public void addannexVIs(List<AnnexVI> annexVIs) {
		for(Iterator iterator = annexVIs.iterator();iterator.hasNext();) {
			AnnexVI annexVI =(AnnexVI) iterator.next();
			annexVILocalService.addAnnexVI(annexVI);
		}
	}
	
	/**
	 * 
	 * @param reportUploadLogId
	 */
	
	  public void deleteAnnexVIByReportUploadLogId(Long reportUploadLogId) {
	  List<AnnexVI> annexVIs =
	  annexVIPersistence.findByReportUploadLogId(reportUploadLogId); for(AnnexVI
	  annexVI : annexVIs) { annexVILocalService.deleteAnnexVI(annexVI); } }
	 
}