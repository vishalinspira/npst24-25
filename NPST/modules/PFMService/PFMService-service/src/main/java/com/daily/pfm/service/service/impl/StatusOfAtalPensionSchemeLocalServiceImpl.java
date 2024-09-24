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

package com.daily.pfm.service.service.impl;

import com.daily.pfm.service.model.StatusOfAtalPensionScheme;
import com.daily.pfm.service.service.base.StatusOfAtalPensionSchemeLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class StatusOfAtalPensionSchemeLocalServiceImpl
		extends StatusOfAtalPensionSchemeLocalServiceBaseImpl {

	public void addOverallStatusOfAtalPensionSchemeForm(List<StatusOfAtalPensionScheme> statusOfAtalPensionSchemeList) {

		for (Iterator iterator = statusOfAtalPensionSchemeList.iterator(); iterator.hasNext();) {
			StatusOfAtalPensionScheme form = (StatusOfAtalPensionScheme) iterator.next();
			statusOfAtalPensionSchemeLocalService.addStatusOfAtalPensionScheme(form);
		}

	}
	
}