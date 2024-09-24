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

import compliance.service.model.InputQuarterlyIntervalII;
import compliance.service.service.base.InputQuarterlyIntervalIILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InputQuarterlyIntervalIILocalServiceImpl
	extends InputQuarterlyIntervalIILocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InputQuarterlyIntervalII addInputQuarterlyIntervalII(String quaterTableName, String description, String qeJun21,
			String qeMar21, String qeDec20, String qeSep20, Date createdate, long createdby) {
		
		InputQuarterlyIntervalII iquaterlyii= inputQuarterlyIntervalIIPersistence.create(counterLocalService.increment(InputQuarterlyIntervalII.class.getName()));
		
		iquaterlyii.setQuarter_table_name(quaterTableName);
		iquaterlyii.setDescription(description);
		iquaterlyii.setQuarter_end_jun_21(qeJun21);
		iquaterlyii.setQuarter_end_mar_21(qeMar21);
		iquaterlyii.setQuarter_end_dec_20(qeDec20);
		iquaterlyii.setQuarter_end_sep_20(qeSep20);
		iquaterlyii.setCreatedate(createdate);
		iquaterlyii.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iquaterlyii.toString());
		return inputQuarterlyIntervalIIPersistence.update(iquaterlyii);
}
}