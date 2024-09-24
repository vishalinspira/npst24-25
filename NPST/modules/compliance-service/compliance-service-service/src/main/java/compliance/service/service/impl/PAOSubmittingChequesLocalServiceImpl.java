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

import java.util.Iterator;
import java.util.List;

import compliance.service.model.PAOSubmittingCheques;
import compliance.service.service.base.PAOSubmittingChequesLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class PAOSubmittingChequesLocalServiceImpl extends PAOSubmittingChequesLocalServiceBaseImpl {
	
public void addPaoSubmittingCheques(List<PAOSubmittingCheques> paoSubmittingChequesList) {
		
		for(Iterator iterator = paoSubmittingChequesList.iterator(); iterator.hasNext();) {
			PAOSubmittingCheques paoSubmittingCheques = (PAOSubmittingCheques) iterator.next();
			paoSubmittingChequesLocalService.addPAOSubmittingCheques(paoSubmittingCheques);
		}
		
	}

public void deletePAOSubmittingChequesByReportUploadLogId(Long reportUploadLogId) {
	List<PAOSubmittingCheques> deleteRepLog =  paoSubmittingChequesPersistence.findByReportUploadLogId(reportUploadLogId);
	for(PAOSubmittingCheques deleteReportLogs : deleteRepLog) {
		paoSubmittingChequesLocalService.deletePAOSubmittingCheques(deleteReportLogs);
	}
}
	
}