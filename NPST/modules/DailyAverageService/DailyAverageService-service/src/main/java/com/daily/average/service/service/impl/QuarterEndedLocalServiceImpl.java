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


import com.daily.average.service.model.AccountStatement;
import com.daily.average.service.model.QuarterEnded;
import com.daily.average.service.service.QuarterEndedLocalServiceUtil;
import com.daily.average.service.service.base.QuarterEndedLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class QuarterEndedLocalServiceImpl
	extends QuarterEndedLocalServiceBaseImpl {
	
	public QuarterEnded addQuarterEnded(String ElevenB, String ElevenA, String Ten, String NineB, String NineA,
			String EightVIIb, String EightVIII, String EightVIIa, String EightVIb, String EightVIa, String EightV,
			String EightIV, String EightIII, String EightII, String EightIb, String EightIa, String SevenIe,
			String SevenId, String SevenIc, String SevenIb, String SevenIa, String SixIIb, String SixIIa, String SixI,
			String FiveVIb, String FiveVIa, String FiveVc, String FiveVb, String FiveVa, String FiveIV, String FiveIII,
			String FiveII, String FiveI, String FourC, String FourB, String FourA, String ThreeCiv, String ThreeCiii,
			String ThreeCii, String ThreeCi, String ThreeC, String ThreeB, String ThreeA, String TwoII, String TwoIc,
			String TwoIb, String TwoIa, String OneV, String OneIVa, String OneIIIb, String OneIIIa, String OneIId,
			String OneIIc, String OneIIb, String OneIIa, String OneIb, String OneIa) {
		long id = CounterLocalServiceUtil.increment(QuarterEnded.class.getName());
		_log.info(id);
		QuarterEnded quaterended = QuarterEndedLocalServiceUtil.createQuarterEnded(id);
		_log.info("gfshdhdjdfjjhdjhdfj");
		quaterended.setOneIa(OneIa);
		quaterended.setOneIb(OneIb);
		quaterended.setOneIIa(OneIIa);
		quaterended.setOneIIb(OneIIb);
		quaterended.setOneIIc(OneIIc);
		quaterended.setOneIId(OneIId);
		quaterended.setOneIIIa(OneIIIa);
		quaterended.setOneIIIb(OneIIIb);
		quaterended.setOneIVa(OneIVa);
		quaterended.setOneV(OneV);
		quaterended.setTwoIa(TwoIa);
		quaterended.setTwoIb(TwoIb);
		quaterended.setTwoIc(TwoIc);
		quaterended.setTwoII(TwoII);
		quaterended.setThreeA(ThreeA);
		quaterended.setThreeB(ThreeB);
		quaterended.setThreeC(ThreeC);
		quaterended.setThreeCi(ThreeCi);
		quaterended.setThreeCii(ThreeCii);
		quaterended.setThreeCiii(ThreeCiii);
		quaterended.setThreeCiv(ThreeCiv);
		quaterended.setFourA(FourA);
		quaterended.setFourB(FourB);
		quaterended.setFourC(FourC);
		quaterended.setFiveI(FiveI);
		quaterended.setFiveII(FiveII);
		quaterended.setFiveIII(FiveIII);
		quaterended.setFiveIV(FiveIV);
		quaterended.setFiveVa(FiveVa);
		quaterended.setFiveVb(FiveVb);
		quaterended.setFiveVc(FiveVc);
		quaterended.setFiveVIa(FiveVIa);
		quaterended.setFiveVIb(FiveVIb);
		quaterended.setSixI(SixI);
		quaterended.setSixIIa(SixIIa);
		quaterended.setSixIIb(SixIIb);
		quaterended.setSevenIa(SevenIa);
		quaterended.setSevenIb(SevenIb);
		quaterended.setSevenIc(SevenIc);
		quaterended.setSevenId(SevenId);
		quaterended.setSevenIe(SevenIe);
		quaterended.setEightIa(EightIa);
		quaterended.setEightIb(EightIb);
		quaterended.setEightII(EightII);
		quaterended.setEightIII(EightIII);
		quaterended.setEightIV(EightIV);
		quaterended.setEightV(EightV);
		quaterended.setEightVIa(EightVIa);
		quaterended.setEightVIb(EightVIb);
		quaterended.setEightVIIa(EightVIIa);
		quaterended.setEightVIIb(EightVIIb);
		quaterended.setEightVIII(EightVIII);
		quaterended.setNineA(NineA);
		quaterended.setNineB(NineB);
		quaterended.setTen(Ten);
		quaterended.setElevenA(ElevenA);
		quaterended.setElevenB(ElevenB);
		_log.info("gfshdhdjdfjjhdjhdfj");
		_log.info("add to db" + QuarterEndedLocalServiceUtil.addQuarterEnded(quaterended));
		return quaterended;
	}

	
	@Override
	public QuarterEnded updateQuarterEndedStatus(long id, long userId, int status, ServiceContext serviceContext) {
		QuarterEnded quarterEnded = null;
		try {
			quarterEnded = quarterEndedPersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			 _log.error(e);
		}

		if (Validator.isNotNull(quarterEnded)) {
			quarterEnded.setStatus(status);
			quarterEnded.setStatusByUserId(userId);
			quarterEnded.setStatusDate(new Date());

			User user;

			try {
				user = userLocalService.getUser(userId);
				quarterEnded.setStatusByUserName(user.getFullName());
				quarterEnded.setStatusByUserUuid(user.getUserUuid());
			} catch (PortalException e) {
				 _log.error(e);
			}

			quarterEnded = quarterEndedPersistence.fetchByPrimaryKey(quarterEnded);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				// Update the asset status to visibile
				assetEntryLocalService.updateEntry(AccountStatement.class.getName(), id, new Date(), null, true, true);
			} else {
				// Set applicationRegistration entity status to false
				assetEntryLocalService.updateVisible(AccountStatement.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e);
		}

		return quarterEnded;
	}
	Log _log = LogFactoryUtil.getLog(getClass());
}
