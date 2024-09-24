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

import com.daily.average.service.model.AnnexureFourA;
import com.daily.average.service.model.AnnexureFourA;
import com.daily.average.service.service.base.AnnexureFourALocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnexureFourALocalServiceImpl extends AnnexureFourALocalServiceBaseImpl {

	public void addAnnexureFourA(List<AnnexureFourA> list) {

		for (Iterator<AnnexureFourA> iterator = list.iterator(); iterator.hasNext();) {
			AnnexureFourA annexureFourA = (AnnexureFourA) iterator.next();
			annexureFourALocalService.addAnnexureFourA(annexureFourA);
			_log.info("annexureFourA"+annexureFourA);
		}
	}

	public void deleteAnnexureFourAByReportUploadLogId(Long reportUploadLogId) {
		List<AnnexureFourA> annexureFourAs = annexureFourAPersistence.findByReportUploadLogId(reportUploadLogId);
		for (AnnexureFourA annexureFourA : annexureFourAs) {
			annexureFourALocalService.deleteAnnexureFourA(annexureFourA);
		}
	}
Log _log = LogFactoryUtil.getLog(getClass());
}