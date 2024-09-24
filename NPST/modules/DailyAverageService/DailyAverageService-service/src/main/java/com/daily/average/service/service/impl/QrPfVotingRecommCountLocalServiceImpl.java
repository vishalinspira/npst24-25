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

import com.daily.average.service.model.QrFinalVoteCount;
import com.daily.average.service.model.QrPfVotingRecommCount;
import com.daily.average.service.service.base.QrPfVotingRecommCountLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QrPfVotingRecommCountLocalServiceImpl
	extends QrPfVotingRecommCountLocalServiceBaseImpl {
	
	public void deleteQrPfVotingRecommCountByReportUploadLogId(Long reportUploadLogId) {
		List<QrPfVotingRecommCount> reportDebits = qrPfVotingRecommCountPersistence.findByReportUploadLogId(reportUploadLogId);
		for (QrPfVotingRecommCount reportDebit : reportDebits) {
			qrPfVotingRecommCountLocalService.deleteQrPfVotingRecommCount(reportDebit);
		}
	}
}