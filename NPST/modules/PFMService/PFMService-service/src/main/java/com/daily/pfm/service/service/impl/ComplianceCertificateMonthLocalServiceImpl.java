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

import com.daily.pfm.service.model.ComplianceCertificateMonth;
import com.daily.pfm.service.service.base.ComplianceCertificateMonthLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ComplianceCertificateMonthLocalServiceImpl
	extends ComplianceCertificateMonthLocalServiceBaseImpl {
	/**
	 * 
	 * @param rslno
	 * @param adressi
	 * @param adressii
	 * @param adressiii
	 * @param adressiv
	 * @param pfm
	 * @param rgno
	 * @param ione
	 * @param itwo
	 * @param ithree
	 * @param ifour
	 * @param ifive
	 * @param isix
	 * @param iione
	 * @param iitwo
	 * @param iiione
	 * @param ivone
	 * @param vone
	 * @param vtwo
	 * @param tarikh
	 * @param namei
	 * @param place
	 * @param nameii
	 * @param createdby
	 * @param ioner 
	 * @param itwor 
	 * @param ifourr 
	 * @param ifiver 
	 * @param isixr 
	 * @param iioner 
	 * @param iitwor 
	 * @param iiioner 
	 * @param ivoner 
	 * @param voner 
	 * @param vtwor 
	 * @return
	 */
		public ComplianceCertificateMonth addComplianceCertificateMonth(String rslno,String adressi, String adressii, String adressiii, String adressiv, String pfm, String rgno, String ione, String itwo, String ithree, String ifour, String ifive, String isix, String iione, String iitwo, String iiione, String ivone, String vone, String vtwo, String tarikh, String namei, String place, String nameii, long createdby, String ioner, String itwor, String ifourr, String ifiver, String isixr, String iioner, String iitwor, String iiioner, String ivoner, String voner, String vtwor) {
			ComplianceCertificateMonth complianceCertificateMonth = complianceCertificateMonthLocalService.createComplianceCertificateMonth(CounterLocalServiceUtil.increment(ComplianceCertificateMonth.class.getName()));
			complianceCertificateMonth.setRslno(rslno);
			complianceCertificateMonth.setAdressi(adressi);
			complianceCertificateMonth.setAdressii(adressii);
			complianceCertificateMonth.setAdressii(adressiii);
			complianceCertificateMonth.setAdressii(adressiv);
			complianceCertificateMonth.setPfm(pfm);
			complianceCertificateMonth.setRgno(rgno);
			complianceCertificateMonth.setIone(ione);
			complianceCertificateMonth.setIoner(ioner);
			complianceCertificateMonth.setItwo(itwo);
			complianceCertificateMonth.setItwor(itwor);
			complianceCertificateMonth.setIthree(ithree);
			complianceCertificateMonth.setIfour(ifour);
			complianceCertificateMonth.setIfourr(ifourr);
			complianceCertificateMonth.setIfive(ifive);
			complianceCertificateMonth.setIfiver(ifiver);
			complianceCertificateMonth.setIsix(isix);
			complianceCertificateMonth.setIsixr(isixr);
			complianceCertificateMonth.setIione(iione);
			complianceCertificateMonth.setIioner(iioner);
			complianceCertificateMonth.setIitwo(iitwo);
			complianceCertificateMonth.setIitwor(iitwor);
			complianceCertificateMonth.setIiione(iiione);
			complianceCertificateMonth.setIiioner(iiioner);
			complianceCertificateMonth.setIvone(ivone);
			complianceCertificateMonth.setIvoner(ivoner);
			complianceCertificateMonth.setVone(vone);
			complianceCertificateMonth.setVoner(voner);
			complianceCertificateMonth.setVtwo(vtwo);
			complianceCertificateMonth.setVtwor(vtwor);
			complianceCertificateMonth.setTarikh(tarikh);
			complianceCertificateMonth.setNamei(namei);
			complianceCertificateMonth.setPlace(place);
			complianceCertificateMonth.setNameii(nameii);
			complianceCertificateMonth.setCreatedby(createdby);
			Date createdate=new Date();
			complianceCertificateMonth.setCreatedate(createdate);
			
			return complianceCertificateMonthLocalService.addComplianceCertificateMonth(complianceCertificateMonth);
		}
}