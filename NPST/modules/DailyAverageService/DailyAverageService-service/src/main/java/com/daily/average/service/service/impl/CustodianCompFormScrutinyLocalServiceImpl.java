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


import com.daily.average.service.model.CustodianCompFormScrutiny;
import com.daily.average.service.service.base.CustodianCompFormScrutinyLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class CustodianCompFormScrutinyLocalServiceImpl
	extends CustodianCompFormScrutinyLocalServiceBaseImpl {
	
	/**
	 * 
	 * @param reportUploadLogId
	 * @return
	 */
	public CustodianCompFormScrutiny fetchCustodianCompFormScrutinyByReportUploadLogId(long reportUploadLogId) {
		return custodianCompFormScrutinyPersistence.fetchByReportUploadLogId(reportUploadLogId);
	}
	
	public CustodianCompFormScrutiny addCustodianCompFormScrutiny(long reportUploadLogId, String observe_i_i, String Observe_ii, String Observe_iii, String Observe_iv,
			String Observe_v, String Observe_vi, String Observe_vii, String Observe_viii, String Observe_ix, String Observe_x, String Observe_xi, String Observe_xii, String Observe_xiii,
			String Observe_xiv,String Observe_xv,String Observe_xvi,String Observe_xvii,String Observe_xviii) {
		
		CustodianCompFormScrutiny custodianCompFormScrutiny = custodianCompFormScrutinyPersistence.create(CounterLocalServiceUtil.increment(CustodianCompFormScrutiny.class.getName()));
		
		custodianCompFormScrutiny.setReportUploadLogId(reportUploadLogId);
		custodianCompFormScrutiny.setObserve_i_i(observe_i_i);
		//custodianCompFormScrutiny.setObserve_i_ii(Observe_i_ii);
		custodianCompFormScrutiny.setObserve_ii(Observe_ii);
		custodianCompFormScrutiny.setObserve_iii(Observe_iii);
		custodianCompFormScrutiny.setObserve_iv(Observe_iv);
		custodianCompFormScrutiny.setObserve_v(Observe_v);
		custodianCompFormScrutiny.setObserve_vi(Observe_vi);
		custodianCompFormScrutiny.setObserve_vii(Observe_vii);
		custodianCompFormScrutiny.setObserve_viii(Observe_viii);
		custodianCompFormScrutiny.setObserve_ix(Observe_ix);
		custodianCompFormScrutiny.setObserve_x(Observe_x);
		custodianCompFormScrutiny.setObserve_xi(Observe_xi);
		custodianCompFormScrutiny.setObserve_xii(Observe_xii);
		custodianCompFormScrutiny.setObserve_xiii(Observe_xiii);
		custodianCompFormScrutiny.setObserve_xiv(Observe_xiv);
		custodianCompFormScrutiny.setObserve_xv(Observe_xv);
		custodianCompFormScrutiny.setObserve_xvi(Observe_xvi);
		custodianCompFormScrutiny.setObserve_xvii(Observe_xvii);
		custodianCompFormScrutiny.setObserve_xviii(Observe_xviii);
		
		return custodianCompFormScrutinyPersistence.update(custodianCompFormScrutiny);
	}
}