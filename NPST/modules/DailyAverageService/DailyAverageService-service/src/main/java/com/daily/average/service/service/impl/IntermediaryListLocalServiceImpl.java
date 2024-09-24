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

import com.daily.average.service.exception.NoSuchReportUploadLogException;
import com.daily.average.service.model.IntermediaryList;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.IntermediaryListImpl;
import com.daily.average.service.service.base.IntermediaryListLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class IntermediaryListLocalServiceImpl
	extends IntermediaryListLocalServiceBaseImpl {
	public List<IntermediaryList> fetchIntermediaryListByTypeId(Long intermediarytype) {
		return intermediaryListPersistence.findByIntermediarytype(intermediarytype);
	}
	
	public List<IntermediaryList> getIntermediaryListByType(List<Long> intermediarytypes) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IntermediaryListImpl.class, PortalClassLoaderUtil.getClassLoader());
		dynamicQuery.add(RestrictionsFactoryUtil.in("intermediaryType", intermediarytypes));
		
		return intermediaryListPersistence.findWithDynamicQuery(dynamicQuery);
	}
	
	public String fetchIntermediaryByReportLogId(long reportUploadLogId) {
		if(reportUploadLogId > 0) {
			ReportUploadLog reportUploadLog = reportUploadLogPersistence.fetchByPrimaryKey(reportUploadLogId);
			IntermediaryList intermediaryList = intermediaryListPersistence.fetchByPrimaryKey(reportUploadLog.getIntermediaryid());
			return intermediaryList.getIntermediaryname();
		}
		return StringPool.BLANK;
	}
	
	Log _log = LogFactoryUtil.getLog(getClass());
}