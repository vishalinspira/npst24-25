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

import compliance.service.service.base.InputQuarterlyIntervalLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class InputQuarterlyIntervalLocalServiceImpl
	extends InputQuarterlyIntervalLocalServiceBaseImpl {
	Log _log = LogFactoryUtil.getLog(getClass());
//	public InputQuarterlyInterval addInputQuarterlyInterval(Date quarterdate,String ioneIa ,String ioneIb,String ioneIIa,
//			String ioneIIb ,String ioneIIc ,String ioneIId,String ioneIIIa,String ioneIIIb,String ioneIVa ,String ioneV,
//			String itwoIa,String itwoIb,String itwoIc,String itwoII,String ithreeA,
//			String ithreeB,String ithreeC,String ithreeCi,String ithreeCii,String ithreeCiii,String ithreeCiv,String ifourA,String ifourB,String ifourC,
//			String ifiveI,String ifiveII,String ifiveIII,String ifiveIV,String ifiveVa,
//			String ifiveVb,String ifiveVc,String ifiveVIa,String ifiveVIb,String isixI,String isixIIa,String isixIIb,String isevenIa,
//			String isevenIb,String isevenIc,String isevenId,String isevenIe,String ieightIa,String ieightIb,String ieightII,
//			String ieightIII,String ieightIV,String ieightV,String ieightVIa,String ieightVIb,String ieightVIIa,String ieightVIIb,String ieightVIII,
//			String inineA,String inineB,String iten,String ielevenA,
//			String ielevenB,String psignature,String place,Date date,Date createdate,long createdby) {
//		
//		InputQuarterlyInterval iquarterly= inputQuarterlyIntervalPersistence.create(counterLocalService.increment(InputQuarterlyInterval.class.getName()));
//	
//		iquarterly.setQuarterdate(quarterdate);
//		iquarterly.setIoneIa(ioneIa);
//		iquarterly.setIoneIb(ioneIb);
//		iquarterly.setIoneIIa(ioneIIa);
//		iquarterly.setIoneIIb(ioneIIb);
//		iquarterly.setIoneIIc(ioneIIc);
//		iquarterly.setIoneIId(ioneIId);
//		iquarterly.setIoneIIIa(ioneIIIa);
//		iquarterly.setIoneIIIb(ioneIIIb);
//		iquarterly.setIoneIVa(ioneIVa);
//		iquarterly.setIoneV(ioneV);
//		iquarterly.setItwoIa(itwoIa);
//		iquarterly.setItwoIb(itwoIb);
//		iquarterly.setItwoIc(itwoIc);
//		iquarterly.setItwoII(itwoII);
//		iquarterly.setIthreeA(ithreeA);
//		iquarterly.setIthreeB(ithreeB);
//		iquarterly.setIthreeC(ithreeC);
//		iquarterly.setIthreeCi(ithreeCi);
//		iquarterly.setIthreeCii(ithreeCii);
//		iquarterly.setIthreeCiii(ithreeCiii);
//		iquarterly.setIthreeCiv(ithreeCiv);
//		iquarterly.setIfourA(ifourA);
//		iquarterly.setIfourB(ifourB);
//		iquarterly.setIfourC(ifourC);
//		iquarterly.setIfiveI(ifiveI);
//		iquarterly.setIfiveII(ifiveII);
//		iquarterly.setIfiveIII(ifiveIII);
//		iquarterly.setIfiveIV(ifiveIV);
//		iquarterly.setIfiveVa(ifiveVa);
//		iquarterly.setIfiveVb(ifiveVb);
//		iquarterly.setIfiveVc(ifiveVc);
//		iquarterly.setIfiveVIa(ifiveVIa);
//		iquarterly.setIfiveVIb(ifiveVIb);
//		iquarterly.setIsixI(isixI);
//		iquarterly.setIsixIIa(isixIIa);
//		iquarterly.setIsixIIb(isixIIb);
//		iquarterly.setIsevenIa(isevenIa);
//		iquarterly.setIsevenIb(isevenIb);
//		iquarterly.setIsevenIc(isevenIc);
//		iquarterly.setIsevenId(isevenId);
//		iquarterly.setIsevenIe(isevenIe);
//		iquarterly.setIeightIa(ieightIa);
//		iquarterly.setIeightIb(ieightIb);
//		iquarterly.setIeightII(ieightII);
//		iquarterly.setIeightIII(ieightIII);
//		iquarterly.setIeightIV(ieightIV);
//		iquarterly.setIeightV(ieightV);
//		iquarterly.setIeightVIa(ieightVIa);
//		iquarterly.setIeightVIb(ieightVIb);
//		iquarterly.setIeightVIIa(ieightVIIa);
//		iquarterly.setIeightVIIb(ieightVIIb);
//		iquarterly.setIeightVIII(ieightVIII);
//		iquarterly.setInineA(inineA);
//		iquarterly.setInineB(inineB);
//		iquarterly.setIten(iten);
//		iquarterly.setIelevenA(ielevenA);
//		iquarterly.setIelevenB(ielevenB);
//		iquarterly.setPsignature(psignature);
//		iquarterly.setPlace(place);
//		iquarterly.setDate_(date);
//		iquarterly.setCreatedate(createdate);
//		iquarterly.setCreatedby(createdby);
//		
//		_log.info("internalAuditReport:::::::::::::::::::::::::::::::::"+iquarterly.toString());
//		return inputQuarterlyIntervalPersistence.update(iquarterly);
//}
}