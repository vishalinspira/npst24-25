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

import compliance.service.model.InputQuarterlyIntervalI;
import compliance.service.service.base.InputQuarterlyIntervalILocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InputQuarterlyIntervalILocalServiceImpl
	extends InputQuarterlyIntervalILocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InputQuarterlyIntervalI addInputQuarterlyIntervalI(String Quatertable_name,
			String nameofdirector,String dignation,String indepent,Date createdate, long createdby) {
		
		InputQuarterlyIntervalI iauterlyi= inputQuarterlyIntervalIPersistence.create(counterLocalService.increment(InputQuarterlyIntervalI.class.getName()));
		
		iauterlyi.setQuatertable_name(Quatertable_name);
		iauterlyi.setNameofdirector(nameofdirector);
		iauterlyi.setDignation(dignation);
		iauterlyi.setIndepent(indepent);
		iauterlyi.setCreatedate(createdate);
		iauterlyi.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iauterlyi.toString());
		return inputQuarterlyIntervalIPersistence.update(iauterlyi);

}
}