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


import com.daily.average.service.model.VotingReportSes;
import com.daily.average.service.service.base.VotingReportSesLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VotingReportSesLocalServiceImpl
	extends VotingReportSesLocalServiceBaseImpl {
	
	public void addVotingReportSes(List<VotingReportSes> list) {
		for(Iterator<VotingReportSes> iterator = list.iterator(); iterator.hasNext();) {
			VotingReportSes vrses = (VotingReportSes)iterator.next();
			votingReportSesLocalService.addVotingReportSes(vrses);
		}
	}
	
	public void deleteVotingReportSesByReportUploadLogId(Long reportUploadLogId) {
		  List<VotingReportSes> vrsess =
				  votingReportSesPersistence.findByReportUploadLogId(reportUploadLogId);
		  for(VotingReportSes vrses : vrsess) {
			  votingReportSesLocalService.deleteVotingReportSes(vrses); } }
}