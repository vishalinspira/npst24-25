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

import com.daily.average.service.model.Annexureivb;

import com.daily.average.service.service.base.AnnexureivbLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexureivbLocalServiceImpl
	extends AnnexureivbLocalServiceBaseImpl {
	public void addAnnexureivbs(List<Annexureivb> annexureivbs) {
		for(Iterator iterator = annexureivbs.iterator();iterator.hasNext();) {
			Annexureivb annexureivb =(Annexureivb) iterator.next();
			annexureivbLocalService.addAnnexureivb(annexureivb);
		}
	}
	
	
	  public void deleteAnnexureivbByReportUploadLogId(Long reportUploadLogId) {
	  List<Annexureivb> annexureivbs =
			  annexureivbPersistence.findByReportUploadLogId(reportUploadLogId);
	  for(Annexureivb annexureivb : annexureivbs) {
	  annexureivbLocalService.deleteAnnexureivb(annexureivb); } }
	 
}