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

import com.daily.average.service.model.Annexureivc;

import com.daily.average.service.service.base.AnnexureivcLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexureivcLocalServiceImpl
	extends AnnexureivcLocalServiceBaseImpl {
	public void addAnnexureivcs(List<Annexureivc> annexureivcs) {
		for(Iterator iterator = annexureivcs.iterator();iterator.hasNext();) {
			Annexureivc annexureivac =(Annexureivc) iterator.next();
			annexureivcLocalService.addAnnexureivc(annexureivac);
		}
	}
	
	
	  public void deleteAnnexureivcByReportUploadLogId(Long reportUploadLogId) {
	  List<Annexureivc> annexureivacs =
	  annexureivcPersistence.findByReportUploadLogId(reportUploadLogId);
	  for(Annexureivc annexureivac : annexureivacs) {
	  annexureivcLocalService.deleteAnnexureivc(annexureivac); } }
	 
}