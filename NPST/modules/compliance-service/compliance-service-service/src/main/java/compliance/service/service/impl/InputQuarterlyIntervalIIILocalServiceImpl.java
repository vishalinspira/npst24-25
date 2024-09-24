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

import compliance.service.model.InputQuarterlyIntervalIII;
import compliance.service.service.base.InputQuarterlyIntervalIIILocalServiceBaseImpl;


/**
 * @author Brian Wing Shun Chan
 */
public class InputQuarterlyIntervalIIILocalServiceImpl
	extends InputQuarterlyIntervalIIILocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
public InputQuarterlyIntervalIII addInternalAuditReportiii(String position_,String nameofthepersonnel,Date createdate, long createdby) {
		
		InputQuarterlyIntervalIII iquaterlyiii= inputQuarterlyIntervalIIIPersistence.create(counterLocalService.increment(InputQuarterlyIntervalIII.class.getName()));
		
		iquaterlyiii.setPosition_(position_);
		iquaterlyiii.setNameofthepersonnel(nameofthepersonnel);
		iquaterlyiii.setCreatedate(createdate);
		iquaterlyiii.setCreatedby(createdby);

		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iquaterlyiii.toString());
		return inputQuarterlyIntervalIIIPersistence.update(iquaterlyiii);
}
}