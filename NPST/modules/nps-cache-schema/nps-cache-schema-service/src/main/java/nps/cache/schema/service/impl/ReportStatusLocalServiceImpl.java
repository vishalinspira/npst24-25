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

package nps.cache.schema.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

import nps.cache.schema.model.ReportStatus;
import nps.cache.schema.service.base.ReportStatusLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=nps.cache.schema.model.ReportStatus",
	service = AopService.class
)
public class ReportStatusLocalServiceImpl
	extends ReportStatusLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	/**
	 * 
	 * @param skey
	 * @param data_
	 */
	public void saveCache(String skey, String data_) {
		_log.info(skey);
		ReportStatus reportStatus = reportStatusLocalService.fetchReportStatus(skey);
		//_log.info(reportStatus);
		if(Validator.isNotNull(reportStatus)) {
			reportStatus.setModifiedDate(new Date());
		}else {
			reportStatus = reportStatusLocalService.createReportStatus(skey);
			reportStatus.setCreateDate(new Date());
		}
		reportStatus.setData_(data_);
		reportStatusLocalService.updateReportStatus(reportStatus);
	}
}