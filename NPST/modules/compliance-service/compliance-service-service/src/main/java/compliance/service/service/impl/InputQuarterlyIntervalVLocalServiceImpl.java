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

package compliance.service.service.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;

import compliance.service.model.InputQuarterlyIntervalV;
import compliance.service.service.base.InputQuarterlyIntervalVLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InputQuarterlyIntervalVLocalServiceImpl extends InputQuarterlyIntervalVLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InputQuarterlyIntervalV addInputQuarterlyIntervalV(String nameofdirector,String dignation,
			String indepent,Date Dateofsubmission,Date createdate, long createdby) {
		
		InputQuarterlyIntervalV iauterlyv= inputQuarterlyIntervalVPersistence.create(counterLocalService.increment(InputQuarterlyIntervalV.class.getName()));
		
		iauterlyv.setNameofdirector(nameofdirector);
		iauterlyv.setDignation(dignation);
		iauterlyv.setIndepent(indepent);
		iauterlyv.setDateofsubmission(Dateofsubmission);
		iauterlyv.setCreatedate(createdate);
		iauterlyv.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iauterlyv.toString());
		return inputQuarterlyIntervalVPersistence.update(iauterlyv);
	
}
	

}