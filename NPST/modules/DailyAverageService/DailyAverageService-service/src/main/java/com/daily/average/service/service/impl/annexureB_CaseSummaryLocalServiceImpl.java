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
import com.daily.average.service.model.annexureB_CaseSummary;
import com.daily.average.service.service.base.annexureB_CaseSummaryLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class annexureB_CaseSummaryLocalServiceImpl
	extends annexureB_CaseSummaryLocalServiceBaseImpl {
	
public annexureB_CaseSummary addannexureB_CaseSummary(String form,int amount,int pranNum,Date pranDate,String oneiia,Date creditedDate,int tokenNum,String one,
		Date emailDate,int remittedAmnt ,Date remittedDate, String oneiic,String subreq, int requestedAmnt, Date documentDate,int carriedAmnt,String casenum1,int pranNum1,
		String name1,String oneIIay1,Date trnsctnDate1,String refnoid1,Date craDate1,int trnsamount1,String OneIIcy1,int rectType1,
		int reqAmount1,String reqMode1,Date rcvdDate1,Date cgmsDate1,String grievTxt1, long intermediaryid) {
		
	annexureB_CaseSummary annexureB_CaseSummary = annexureB_CaseSummaryPersistence.create(CounterLocalServiceUtil.increment(annexureB_CaseSummary.class.getName()));
	annexureB_CaseSummary.setForm(form);
	annexureB_CaseSummary.setAmount(amount);
	annexureB_CaseSummary.setPranNum(pranNum);
	annexureB_CaseSummary.setPranDate(pranDate);
	annexureB_CaseSummary.setOneiia(oneiia);
	annexureB_CaseSummary.setCreditedDate(creditedDate);
	annexureB_CaseSummary.setTokenNum(tokenNum);
	annexureB_CaseSummary.setOne(one);
	annexureB_CaseSummary.setEmailDate(emailDate);
	annexureB_CaseSummary.setRemittedAmnt(remittedAmnt);
	annexureB_CaseSummary.setRemittedDate(remittedDate);
	annexureB_CaseSummary.setOneiic(oneiic);
	annexureB_CaseSummary.setSubreq(subreq);
	annexureB_CaseSummary.setRequestedAmnt(requestedAmnt);
	annexureB_CaseSummary.setDocumentDate(documentDate);
	annexureB_CaseSummary.setCarriedAmnt(carriedAmnt);
	annexureB_CaseSummary.setCasenum1(casenum1);
	annexureB_CaseSummary.setPranNum1(pranNum1);
	annexureB_CaseSummary.setName1(name1);
	annexureB_CaseSummary.setOneIIay1(oneIIay1);
	annexureB_CaseSummary.setTrnsctnDate1(trnsctnDate1);
	annexureB_CaseSummary.setRefnoid1(refnoid1);
	annexureB_CaseSummary.setCraDate1(craDate1);
	annexureB_CaseSummary.setTrnsamount1(trnsamount1);
	annexureB_CaseSummary.setOneIIcy1(OneIIcy1);
	annexureB_CaseSummary.setRectType1(rectType1);
	annexureB_CaseSummary.setReqAmount1(reqAmount1);
	annexureB_CaseSummary.setReqMode1(reqMode1);
	annexureB_CaseSummary.setRcvdDate1(rcvdDate1);
	annexureB_CaseSummary.setCgmsDate1(cgmsDate1);
	annexureB_CaseSummary.setGrievTxt1(grievTxt1);
	//annexureB_CaseSummary.setIntermediaryid(intermediaryid);
		
		return annexureB_CaseSummaryPersistence.update(annexureB_CaseSummary);
	}
	
	/*public List<annexureB_CaseSummary>  findannexureB_CaseSummaryByIntermediaryid( long intermediaryid) {
		_log.info("intermediaryid"+intermediaryid);
		return annexureB_CaseSummaryPersistence.findByIntermediaryid(intermediaryid);
	}*/
	Log _log =LogFactoryUtil.getLog(getClass());
}
