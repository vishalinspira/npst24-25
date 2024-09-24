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

import compliance.service.model.InputQuarterlyIntervalIV;
import compliance.service.service.base.InputQuarterlyIntervalIVLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InputQuarterlyIntervalIVLocalServiceImpl extends InputQuarterlyIntervalIVLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
	public InputQuarterlyIntervalIV addInputQuarterlyIntervalIV(String intervaltableName,String schemetype,
			String occurrence,String submission,String dateoficmeeting,
			String boardmeeting,Date createdate, long createdby) {
		
		InputQuarterlyIntervalIV iauterlyiv= inputQuarterlyIntervalIVPersistence.create(counterLocalService.increment(InputQuarterlyIntervalIV.class.getName()));
		
		iauterlyiv.setIntervaltableName(intervaltableName);
		iauterlyiv.setSchemetype(schemetype);
		iauterlyiv.setOccurrence(occurrence);
		iauterlyiv.setSubmission(submission);
		iauterlyiv.setDateoficmeeting(dateoficmeeting);
		iauterlyiv.setBoardmeeting(boardmeeting);
		iauterlyiv.setCreatedate(createdate);
		iauterlyiv.setCreatedby(createdby);
		
		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iauterlyiv.toString());
		return inputQuarterlyIntervalIVPersistence.update(iauterlyiv);
	
}

}