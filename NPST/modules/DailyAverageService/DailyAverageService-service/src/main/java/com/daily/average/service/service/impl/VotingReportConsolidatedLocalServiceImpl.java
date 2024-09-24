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

import com.daily.average.service.model.VotingReportConsolidated;

import com.daily.average.service.service.base.VotingReportConsolidatedLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VotingReportConsolidatedLocalServiceImpl
	extends VotingReportConsolidatedLocalServiceBaseImpl {
	
	public void addVotingReportConsolidated(List<VotingReportConsolidated> list) {
		for(Iterator<VotingReportConsolidated> iterator = list.iterator(); iterator.hasNext();) {
			VotingReportConsolidated vrconsolidated = (VotingReportConsolidated)iterator.next();
		     votingReportConsolidatedLocalService.addVotingReportConsolidated(vrconsolidated);
		}
	}
	
	public void deleteVotingReportConsolidatedByReportUploadLogId(Long reportUploadLogId) {
		  List<VotingReportConsolidated> vrconsolidateds =
				  votingReportConsolidatedPersistence.findByReportUploadLogId(reportUploadLogId);
		  for(VotingReportConsolidated vrconsolidated : vrconsolidateds) {
			  votingReportConsolidatedLocalService.deleteVotingReportConsolidated(vrconsolidated); } }
}