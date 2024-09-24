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

import java.util.Date;

import compliance.service.model.CustodianAnnex_4b;
import compliance.service.service.base.CustodianAnnex_4bLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class CustodianAnnex_4bLocalServiceImpl extends CustodianAnnex_4bLocalServiceBaseImpl {
	
	public CustodianAnnex_4b addInitialDetails(String fileNumber, Date date_1, 
			String custodianName, String address, String date_2, String name) {
		
		CustodianAnnex_4b initialDetails = custodianAnnex_4bPersistence.create(counterLocalService.increment());
		
		initialDetails.setFile_number(fileNumber);
		initialDetails.setDate_1(date_1);
		initialDetails.setCustodian_name(custodianName);
		initialDetails.setAddress(address);
		initialDetails.setDate_2(date_2);
		//initialDetails.setDate_3(date_3);
		initialDetails.setName_(name);
		//initialDetails.setDate_4(date_4);
		
		return initialDetails;
		
	}
	
	public CustodianAnnex_4b addSectionADetails(CustodianAnnex_4b sectionADetails, String regulatory_comments_1, String regulatory_remarks_1, 
			String regulatory_sample_1, String regulatory_nps_1, 
			String regulatory_comments_2, String regulatory_remarks_2,
			String regulatory_sample_2, String regulatory_nps_2, 
			String regulatory_comments_3, String regulatory_remarks_3,
			String regulatory_sample_3, String regulatory_nps_3,
			String regulatory_comments_4, String regulatory_remarks_4,
			String regulatory_sample_4, String regulatory_nps_4,
			String regulatory_comments_5, String regulatory_remarks_5,
			String regulatory_sample_5, String regulatory_nps_5,
			String regulatory_comments_6, String regulatory_remarks_6,
			String regulatory_sample_6, String regulatory_nps_6,
			String regulatory_comments_7, String regulatory_remarks_7,
			String regulatory_sample_7, String regulatory_nps_7,
			String regulatory_comments_8, String regulatory_remarks_8,
			String regulatory_sample_8, String regulatory_nps_8,
			String regulatory_comments_9, String regulatory_remarks_9,
			String regulatory_sample_9, String regulatory_nps_9) {
		
		sectionADetails.setRegulatory_comments_1(regulatory_comments_1);
		sectionADetails.setRegulatory_remarks_1(regulatory_remarks_1);
		sectionADetails.setRegulatory_sample_1(regulatory_sample_1);
		sectionADetails.setRegulatory_nps_1(regulatory_nps_1);
		
		sectionADetails.setRegulatory_comments_2(regulatory_comments_2);
		sectionADetails.setRegulatory_remarks_2(regulatory_remarks_2);
		sectionADetails.setRegulatory_sample_2(regulatory_sample_2);
		sectionADetails.setRegulatory_nps_2(regulatory_nps_2);
		
		sectionADetails.setRegulatory_comments_3(regulatory_comments_3);
		sectionADetails.setRegulatory_remarks_3(regulatory_remarks_3);
		sectionADetails.setRegulatory_sample_3(regulatory_sample_3);
		sectionADetails.setRegulatory_nps_3(regulatory_nps_3);
		
		sectionADetails.setRegulatory_comments_4(regulatory_comments_4);
		sectionADetails.setRegulatory_remarks_4(regulatory_remarks_4);
		sectionADetails.setRegulatory_sample_4(regulatory_sample_4);
		sectionADetails.setRegulatory_nps_4(regulatory_nps_4);
		
		sectionADetails.setRegulatory_comments_5(regulatory_comments_5);
		sectionADetails.setRegulatory_remarks_5(regulatory_remarks_5);
		sectionADetails.setRegulatory_sample_5(regulatory_sample_5);
		sectionADetails.setRegulatory_nps_5(regulatory_nps_5);
		
		sectionADetails.setRegulatory_comments_6(regulatory_comments_6);
		sectionADetails.setRegulatory_remarks_6(regulatory_remarks_6);
		sectionADetails.setRegulatory_sample_6(regulatory_sample_6);
		sectionADetails.setRegulatory_nps_6(regulatory_nps_6);
		
		sectionADetails.setRegulatory_comments_7(regulatory_comments_7);
		sectionADetails.setRegulatory_remarks_7(regulatory_remarks_7);
		sectionADetails.setRegulatory_sample_7(regulatory_sample_7);
		sectionADetails.setRegulatory_nps_7(regulatory_nps_7);
		
		sectionADetails.setRegulatory_comments_8(regulatory_comments_8);
		sectionADetails.setRegulatory_remarks_8(regulatory_remarks_8);
		sectionADetails.setRegulatory_sample_8(regulatory_sample_8);
		sectionADetails.setRegulatory_nps_8(regulatory_nps_8);
		
		sectionADetails.setRegulatory_comments_9(regulatory_comments_9);
		sectionADetails.setRegulatory_remarks_9(regulatory_remarks_9);
		sectionADetails.setRegulatory_sample_9(regulatory_sample_9);
		sectionADetails.setRegulatory_nps_9(regulatory_nps_9);
		
		return custodianAnnex_4bPersistence.update(sectionADetails);
		
	}
	
	public CustodianAnnex_4b addSectionBDetails(CustodianAnnex_4b sectionBDetails, String operational_comments_1, String operational_remarks_1,
			String operational_sample_1, String operational_nps_1, 
			String operational_comments_2, String operational_remarks_2, String operational_sample_2, String operational_nps_2,
			String operational_comments_3, String operational_remarks_3, String operational_sample_3, String operational_nps_3,
			String operational_comments_4, String operational_remarks_4, String operational_sample_4, String operational_nps_4,
			String operational_comments_5, String operational_remarks_5, String operational_sample_5, String operational_nps_5,
			String operational_comments_6, String operational_remarks_6, String operational_sample_6, String operational_nps_6,
			String operational_comments_7, String operational_remarks_7, String operational_sample_7, String operational_nps_7,
			String operational_comments_8, String operational_remarks_8, String operational_sample_8, String operational_nps_8,
			String operational_comments_9, String operational_remarks_9, String operational_sample_9, String operational_nps_9,
			String operational_comments_10, String operational_remarks_10, String operational_sample_10, String operational_nps_10,
			String operational_comments_11, String operational_remarks_11, String operational_sample_11, String operational_nps_11,
			String operational_comments_12, String operational_remarks_12, String operational_sample_12, String operational_nps_12,
			String operational_comments_13, String operational_remarks_13, String operational_sample_13, String operational_nps_13,
			String operational_comments_14, String operational_remarks_14, String operational_sample_14, String operational_nps_14,
			String operational_comments_15, String operational_remarks_15, String operational_sample_15, String operational_nps_15,
			String operational_comments_16, String operational_remarks_16, String operational_sample_16, String operational_nps_16,
			String operational_comments_17, String operational_remarks_17, String operational_sample_17, String operational_nps_17,
			String operational_comments_18, String operational_remarks_18, String operational_sample_18, String operational_nps_18,
			String operational_comments_19, String operational_remarks_19, String operational_sample_19, String operational_nps_19) {
		
		sectionBDetails.setOperational_comments_1(operational_comments_1);
		sectionBDetails.setOperational_remarks_1(operational_remarks_1);
		sectionBDetails.setOperational_sample_1(operational_sample_1);
		sectionBDetails.setOperational_nps_1(operational_nps_1);
		
		sectionBDetails.setOperational_comments_2(operational_comments_2);
		sectionBDetails.setOperational_remarks_2(operational_remarks_2);
		sectionBDetails.setOperational_sample_2(operational_sample_2);
		sectionBDetails.setOperational_nps_2(operational_nps_2);
		
		sectionBDetails.setOperational_comments_3(operational_comments_3);
		sectionBDetails.setOperational_remarks_3(operational_remarks_3);
		sectionBDetails.setOperational_sample_3(operational_sample_3);
		sectionBDetails.setOperational_nps_3(operational_nps_3);
		
		sectionBDetails.setOperational_comments_4(operational_comments_4);
		sectionBDetails.setOperational_remarks_4(operational_remarks_4);
		sectionBDetails.setOperational_sample_4(operational_sample_4);
		sectionBDetails.setOperational_nps_4(operational_nps_4);
		
		sectionBDetails.setOperational_comments_5(operational_comments_5);
		sectionBDetails.setOperational_remarks_5(operational_remarks_5);
		sectionBDetails.setOperational_sample_5(operational_sample_5);
		sectionBDetails.setOperational_nps_5(operational_nps_5);
		
		sectionBDetails.setOperational_comments_6(operational_comments_6);
		sectionBDetails.setOperational_remarks_6(operational_remarks_6);
		sectionBDetails.setOperational_sample_6(operational_sample_6);
		sectionBDetails.setOperational_nps_6(operational_nps_6);
		
		sectionBDetails.setOperational_comments_7(operational_comments_7);
		sectionBDetails.setOperational_remarks_7(operational_remarks_7);
		sectionBDetails.setOperational_sample_7(operational_sample_7);
		sectionBDetails.setOperational_nps_7(operational_nps_7);
		
		sectionBDetails.setOperational_comments_8(operational_comments_8);
		sectionBDetails.setOperational_remarks_8(operational_remarks_8);
		sectionBDetails.setOperational_sample_8(operational_sample_8);
		sectionBDetails.setOperational_nps_8(operational_nps_8);
		
		sectionBDetails.setOperational_comments_9(operational_comments_9);
		sectionBDetails.setOperational_remarks_9(operational_remarks_9);
		sectionBDetails.setOperational_sample_9(operational_sample_9);
		sectionBDetails.setOperational_nps_9(operational_nps_9);
		
		sectionBDetails.setOperational_comments_10(operational_comments_10);
		sectionBDetails.setOperational_remarks_10(operational_remarks_10);
		sectionBDetails.setOperational_sample_10(operational_sample_10);
		sectionBDetails.setOperational_nps_10(operational_nps_10);
		
		sectionBDetails.setOperational_comments_11(operational_comments_11);
		sectionBDetails.setOperational_remarks_11(operational_remarks_11);
		sectionBDetails.setOperational_sample_11(operational_sample_11);
		sectionBDetails.setOperational_nps_11(operational_nps_11);
		
		sectionBDetails.setOperational_comments_12(operational_comments_12);
		sectionBDetails.setOperational_remarks_12(operational_remarks_12);
		sectionBDetails.setOperational_sample_12(operational_sample_12);
		sectionBDetails.setOperational_nps_12(operational_nps_12);
		
		sectionBDetails.setOperational_comments_13(operational_comments_13);
		sectionBDetails.setOperational_remarks_13(operational_remarks_13);
		sectionBDetails.setOperational_sample_13(operational_sample_13);
		sectionBDetails.setOperational_nps_13(operational_nps_13);
		
		sectionBDetails.setOperational_comments_14(operational_comments_14);
		sectionBDetails.setOperational_remarks_14(operational_remarks_14);
		sectionBDetails.setOperational_sample_14(operational_sample_14);
		sectionBDetails.setOperational_nps_14(operational_nps_14);
		
		sectionBDetails.setOperational_comments_15(operational_comments_15);
		sectionBDetails.setOperational_remarks_15(operational_remarks_15);
		sectionBDetails.setOperational_sample_15(operational_sample_15);
		sectionBDetails.setOperational_nps_15(operational_nps_15);
		
		sectionBDetails.setOperational_comments_16(operational_comments_16);
		sectionBDetails.setOperational_remarks_16(operational_remarks_16);
		sectionBDetails.setOperational_sample_16(operational_sample_16);
		sectionBDetails.setOperational_nps_16(operational_nps_16);
		
		sectionBDetails.setOperational_comments_17(operational_comments_17);
		sectionBDetails.setOperational_remarks_17(operational_remarks_17);
		sectionBDetails.setOperational_sample_17(operational_sample_17);
		sectionBDetails.setOperational_nps_17(operational_nps_17);
		
		sectionBDetails.setOperational_comments_18(operational_comments_18);
		sectionBDetails.setOperational_remarks_18(operational_remarks_18);
		sectionBDetails.setOperational_sample_18(operational_sample_18);
		sectionBDetails.setOperational_nps_18(operational_nps_18);
		
		sectionBDetails.setOperational_comments_19(operational_comments_19);
		sectionBDetails.setOperational_remarks_19(operational_remarks_19);
		sectionBDetails.setOperational_sample_19(operational_sample_19);
		sectionBDetails.setOperational_nps_19(operational_nps_19);
		
		
		return custodianAnnex_4bPersistence.update(sectionBDetails);
		
	}
	
	public CustodianAnnex_4b addSectionCDetails(CustodianAnnex_4b sectionCDetails, String timely_comments_1, String timely_remarks_1,
			String timely_sample_1, String timely_nps_1,
			String timely_comments_2, String timely_remarks_2, String timely_sample_2, String timely_nps_2,
			String timely_comments_3, String timely_remarks_3, String timely_sample_3, String timely_nps_3,
			String timely_comments_4, String timely_remarks_4, String timely_sample_4, String timely_nps_4,
			String timely_comments_5, String timely_remarks_5, String timely_sample_5, String timely_nps_5,
			String timely_comments_6, String timely_remarks_6, String timely_sample_6, String timely_nps_6,
			String timely_comments_7, String timely_remarks_7, String timely_sample_7, String timely_nps_7,
			String timely_comments_8, String timely_remarks_8, String timely_sample_8, String timely_nps_8,
			String timely_comments_9, String timely_remarks_9, String timely_sample_9, String timely_nps_9,
			String timely_comments_10, String timely_remarks_10, String timely_sample_10, String timely_nps_10,
			String timely_comments_11, String timely_remarks_11, String timely_sample_11, String timely_nps_11,
			String timely_comments_12, String timely_remarks_12, String timely_sample_12, String timely_nps_12,
			String timely_comments_13, String timely_remarks_13, String timely_sample_13, String timely_nps_13) {
		
		
		sectionCDetails.setTimely_comments_1(timely_comments_1);
		sectionCDetails.setTimely_remarks_1(timely_remarks_1);
		sectionCDetails.setTimely_sample_1(timely_sample_1);
		sectionCDetails.setTimely_nps_1(timely_nps_1);
		
		sectionCDetails.setTimely_comments_2(timely_comments_2);
		sectionCDetails.setTimely_remarks_2(timely_remarks_2);
		sectionCDetails.setTimely_sample_2(timely_sample_2);
		sectionCDetails.setTimely_nps_2(timely_nps_2);
		
		sectionCDetails.setTimely_comments_3(timely_comments_3);
		sectionCDetails.setTimely_remarks_3(timely_remarks_3);
		sectionCDetails.setTimely_sample_3(timely_sample_3);
		sectionCDetails.setTimely_nps_3(timely_nps_3);
		
		sectionCDetails.setTimely_comments_4(timely_comments_4);
		sectionCDetails.setTimely_remarks_4(timely_remarks_4);
		sectionCDetails.setTimely_sample_4(timely_sample_4);
		sectionCDetails.setTimely_nps_4(timely_nps_4);
		
		sectionCDetails.setTimely_comments_5(timely_comments_5);
		sectionCDetails.setTimely_remarks_5(timely_remarks_5);
		sectionCDetails.setTimely_sample_5(timely_sample_5);
		sectionCDetails.setTimely_nps_5(timely_nps_5);
		
		sectionCDetails.setTimely_comments_6(timely_comments_6);
		sectionCDetails.setTimely_remarks_6(timely_remarks_6);
		sectionCDetails.setTimely_sample_6(timely_sample_6);
		sectionCDetails.setTimely_nps_6(timely_nps_6);
		
		sectionCDetails.setTimely_comments_7(timely_comments_7);
		sectionCDetails.setTimely_remarks_7(timely_remarks_7);
		sectionCDetails.setTimely_sample_7(timely_sample_7);
		sectionCDetails.setTimely_nps_7(timely_nps_7);
		
		sectionCDetails.setTimely_comments_8(timely_comments_8);
		sectionCDetails.setTimely_remarks_8(timely_remarks_8);
		sectionCDetails.setTimely_sample_8(timely_sample_8);
		sectionCDetails.setTimely_nps_8(timely_nps_8);
		
		sectionCDetails.setTimely_comments_9(timely_comments_9);
		sectionCDetails.setTimely_remarks_9(timely_remarks_9);
		sectionCDetails.setTimely_sample_9(timely_sample_9);
		sectionCDetails.setTimely_nps_9(timely_nps_9);
		
		sectionCDetails.setTimely_comments_10(timely_comments_10);
		sectionCDetails.setTimely_remarks_10(timely_remarks_10);
		sectionCDetails.setTimely_sample_10(timely_sample_10);
		sectionCDetails.setTimely_nps_10(timely_nps_10);
		
		sectionCDetails.setTimely_comments_11(timely_comments_11);
		sectionCDetails.setTimely_remarks_11(timely_remarks_11);
		sectionCDetails.setTimely_sample_11(timely_sample_11);
		sectionCDetails.setTimely_nps_11(timely_nps_11);
		
		sectionCDetails.setTimely_comments_12(timely_comments_12);
		sectionCDetails.setTimely_remarks_12(timely_remarks_12);
		sectionCDetails.setTimely_sample_12(timely_sample_12);
		sectionCDetails.setTimely_nps_12(timely_nps_12);
		
		sectionCDetails.setTimely_comments_13(timely_comments_13);
		sectionCDetails.setTimely_remarks_13(timely_remarks_13);
		sectionCDetails.setTimely_sample_13(timely_sample_13);
		sectionCDetails.setTimely_nps_13(timely_nps_13);
		
		return custodianAnnex_4bPersistence.update(sectionCDetails);
		
	}
	
	public CustodianAnnex_4b addSectionDDetails(CustodianAnnex_4b sectionDDetails, String custodian_comments_1, String custodian_remarks_1,
			String custodian_sample_1, String custodian_nps_1, 
			String custodian_comments_2, String custodian_remarks_2, String custodian_sample_2, String custodian_nps_2) {
		
		sectionDDetails.setCustodian_comments_1(custodian_comments_1);
		sectionDDetails.setCustodian_remarks_1(custodian_remarks_1);
		sectionDDetails.setCustodian_sample_1(custodian_sample_1);
		sectionDDetails.setCustodian_nps_1(custodian_nps_1);
		
		sectionDDetails.setCustodian_comments_2(custodian_comments_2);
		sectionDDetails.setCustodian_remarks_2(custodian_remarks_2);
		sectionDDetails.setCustodian_sample_2(custodian_sample_2);
		sectionDDetails.setCustodian_nps_2(custodian_nps_2);
		
		
		return custodianAnnex_4bPersistence.update(sectionDDetails);
		
	}
	
	public CustodianAnnex_4b addSectionEDetails(CustodianAnnex_4b sectionEDetails, String admin_comments_1, String admin_remarks_1,
			String admin_sample_1, String admin_nps_1, 
			String admin_comments_2, String admin_remarks_2, String admin_sample_2, String admin_nps_2,
			String admin_comments_3, String admin_remarks_3, String admin_sample_3, String admin_nps_3,
			String admin_comments_4, String admin_remarks_4, String admin_sample_4, String admin_nps_4,
			String admin_comments_5, String admin_remarks_5, String admin_sample_5, String admin_nps_5) {
		
		sectionEDetails.setAdmin_comments_1(admin_comments_1);
		sectionEDetails.setAdmin_remarks_1(admin_remarks_1);
		sectionEDetails.setAdmin_sample_1(admin_sample_1);
		sectionEDetails.setAdmin_nps_1(admin_nps_1);
		
		sectionEDetails.setAdmin_comments_2(admin_comments_2);
		sectionEDetails.setAdmin_remarks_2(admin_remarks_2);
		sectionEDetails.setAdmin_sample_2(admin_sample_2);
		sectionEDetails.setAdmin_nps_2(admin_nps_2);
		
		sectionEDetails.setAdmin_comments_3(admin_comments_3);
		sectionEDetails.setAdmin_remarks_3(admin_remarks_3);
		sectionEDetails.setAdmin_sample_3(admin_sample_3);
		sectionEDetails.setAdmin_nps_3(admin_nps_3);
		
		sectionEDetails.setAdmin_comments_4(admin_comments_4);
		sectionEDetails.setAdmin_remarks_4(admin_remarks_4);
		sectionEDetails.setAdmin_sample_4(admin_sample_4);
		sectionEDetails.setAdmin_nps_4(admin_nps_4);
		
		sectionEDetails.setAdmin_comments_5(admin_comments_5);
		sectionEDetails.setAdmin_remarks_5(admin_remarks_5);
		sectionEDetails.setAdmin_sample_5(admin_sample_5);
		sectionEDetails.setAdmin_nps_5(admin_nps_5);

		return custodianAnnex_4bPersistence.update(sectionEDetails);
		
	}
	
	public CustodianAnnex_4b addSectionFDetails(CustodianAnnex_4b sectionFDetails, String infra_comments_1, String infra_remarks_1,
			String infra_sample_1, String infra_nps_1, 
			String infra_comments_2, String infra_remarks_2, String infra_sample_2, String infra_nps_2,
			String infra_comments_3, String infra_remarks_3, String infra_sample_3, String infra_nps_3,
			String infra_comments_4, String infra_remarks_4, String infra_sample_4, String infra_nps_4,
			String infra_comments_5, String infra_remarks_5, String infra_sample_5, String infra_nps_5,
			String infra_comments_6, String infra_remarks_6, String infra_sample_6, String infra_nps_6,
			String infra_comments_7, String infra_remarks_7, String infra_sample_7, String infra_nps_7,
			String infra_comments_8, String infra_remarks_8, String infra_sample_8, String infra_nps_8,
			String infra_comments_9, String infra_remarks_9, String infra_sample_9, String infra_nps_9,
			String infra_comments_10, String infra_remarks_10, String infra_sample_10, String infra_nps_10,
			String infra_comments_11, String infra_remarks_11, String infra_sample_11, String infra_nps_11,
			String infra_comments_12, String infra_remarks_12, String infra_sample_12, String infra_nps_12) {
		
		sectionFDetails.setInfra_comments_1(infra_comments_1);
		sectionFDetails.setInfra_remarks_1(infra_remarks_1);
		sectionFDetails.setInfra_sample_1(infra_sample_1);
		sectionFDetails.setInfra_nps_1(infra_nps_1);
		
		sectionFDetails.setInfra_comments_2(infra_comments_2);
		sectionFDetails.setInfra_remarks_2(infra_remarks_2);
		sectionFDetails.setInfra_sample_2(infra_sample_2);
		sectionFDetails.setInfra_nps_2(infra_nps_2);
		
		sectionFDetails.setInfra_comments_3(infra_comments_3);
		sectionFDetails.setInfra_remarks_3(infra_remarks_3);
		sectionFDetails.setInfra_sample_3(infra_sample_3);
		sectionFDetails.setInfra_nps_3(infra_nps_3);
		
		sectionFDetails.setInfra_comments_4(infra_comments_4);
		sectionFDetails.setInfra_remarks_4(infra_remarks_4);
		sectionFDetails.setInfra_sample_4(infra_sample_4);
		sectionFDetails.setInfra_nps_4(infra_nps_4);
		
		sectionFDetails.setInfra_comments_5(infra_comments_5);
		sectionFDetails.setInfra_remarks_5(infra_remarks_5);
		sectionFDetails.setInfra_sample_5(infra_sample_5);
		sectionFDetails.setInfra_nps_5(infra_nps_5);
		
		sectionFDetails.setInfra_comments_6(infra_comments_6);
		sectionFDetails.setInfra_remarks_6(infra_remarks_6);
		sectionFDetails.setInfra_sample_6(infra_sample_6);
		sectionFDetails.setInfra_nps_6(infra_nps_6);
		
		sectionFDetails.setInfra_comments_7(infra_comments_7);
		sectionFDetails.setInfra_remarks_7(infra_remarks_7);
		sectionFDetails.setInfra_sample_7(infra_sample_7);
		sectionFDetails.setInfra_nps_7(infra_nps_7);
		
		sectionFDetails.setInfra_comments_8(infra_comments_8);
		sectionFDetails.setInfra_remarks_8(infra_remarks_8);
		sectionFDetails.setInfra_sample_8(infra_sample_8);
		sectionFDetails.setInfra_nps_8(infra_nps_8);
		
		sectionFDetails.setInfra_comments_9(infra_comments_9);
		sectionFDetails.setInfra_remarks_9(infra_remarks_9);
		sectionFDetails.setInfra_sample_9(infra_sample_9);
		sectionFDetails.setInfra_nps_9(infra_nps_9);
		
		sectionFDetails.setInfra_comments_10(infra_comments_10);
		sectionFDetails.setInfra_remarks_10(infra_remarks_10);
		sectionFDetails.setInfra_sample_10(infra_sample_10);
		sectionFDetails.setInfra_nps_10(infra_nps_10);
		
		sectionFDetails.setInfra_comments_11(infra_comments_11);
		sectionFDetails.setInfra_remarks_11(infra_remarks_11);
		sectionFDetails.setInfra_sample_11(infra_sample_11);
		sectionFDetails.setInfra_nps_11(infra_nps_11);
		
		sectionFDetails.setInfra_comments_12(infra_comments_12);
		sectionFDetails.setInfra_remarks_12(infra_remarks_12);
		sectionFDetails.setInfra_sample_12(infra_sample_12);
		sectionFDetails.setInfra_nps_12(infra_nps_12);
		
		return custodianAnnex_4bPersistence.update(sectionFDetails);
		
	}
	
	public CustodianAnnex_4b addSectionGDetails(CustodianAnnex_4b sectionGDetails, String protection_comments_1, String protection_remarks_1,
			String protection_sample_1, String protection_nps_1, 
			String protection_comments_2, String protection_remarks_2, String protection_sample_2, String protection_nps_2,
			String protection_comments_3, String protection_remarks_3, String protection_sample_3, String protection_nps_3,
			String protection_comments_4, String protection_remarks_4, String protection_sample_4, String protection_nps_4,
			String protection_comments_5, String protection_remarks_5, String protection_sample_5, String protection_nps_5,
			String protection_comments_6, String protection_remarks_6, String protection_sample_6, String protection_nps_6) {
		
		sectionGDetails.setProtection_comments_1(protection_comments_1);
		sectionGDetails.setProtection_remarks_1(protection_remarks_1);
		sectionGDetails.setProtection_sample_1(protection_sample_1);
		sectionGDetails.setProtection_nps_1(protection_nps_1);
		
		sectionGDetails.setProtection_comments_2(protection_comments_2);
		sectionGDetails.setProtection_remarks_2(protection_remarks_2);
		sectionGDetails.setProtection_sample_2(protection_sample_2);
		sectionGDetails.setProtection_nps_2(protection_nps_2);
		
		sectionGDetails.setProtection_comments_3(protection_comments_3);
		sectionGDetails.setProtection_remarks_3(protection_remarks_3);
		sectionGDetails.setProtection_sample_3(protection_sample_3);
		sectionGDetails.setProtection_nps_3(protection_nps_3);
		
		sectionGDetails.setProtection_comments_4(protection_comments_4);
		sectionGDetails.setProtection_remarks_4(protection_remarks_4);
		sectionGDetails.setProtection_sample_4(protection_sample_4);
		sectionGDetails.setProtection_nps_4(protection_nps_4);
		
		sectionGDetails.setProtection_comments_5(protection_comments_5);
		sectionGDetails.setProtection_remarks_5(protection_remarks_5);
		sectionGDetails.setProtection_sample_5(protection_sample_5);
		sectionGDetails.setProtection_nps_5(protection_nps_5);
		
		sectionGDetails.setProtection_comments_6(protection_comments_6);
		sectionGDetails.setProtection_remarks_6(protection_remarks_6);
		sectionGDetails.setProtection_sample_6(protection_sample_6);
		sectionGDetails.setProtection_nps_6(protection_nps_6);
		
		return custodianAnnex_4bPersistence.update(sectionGDetails);
		
	}
	
	public CustodianAnnex_4b addSectionHDetails(CustodianAnnex_4b sectionHDetails, String record_comments_1, String record_remarks_1,
			String record_sample_1, String record_nps_1, 
			String record_comments_2, String record_remarks_2, String record_sample_2, String record_nps_2,
			String record_comments_3, String record_remarks_3, String record_sample_3, String record_nps_3,
			String record_comments_4, String record_remarks_4, String record_sample_4, String record_nps_4) {
		
		sectionHDetails.setRecord_comments_1(record_comments_1);
		sectionHDetails.setRecord_remarks_1(record_remarks_1);
		sectionHDetails.setRecord_sample_1(record_sample_1);
		sectionHDetails.setRecord_nps_1(record_nps_1);
		
		sectionHDetails.setRecord_comments_2(record_comments_2);
		sectionHDetails.setRecord_remarks_2(record_remarks_2);
		sectionHDetails.setRecord_sample_2(record_sample_2);
		sectionHDetails.setRecord_nps_2(record_nps_2);
		
		sectionHDetails.setRecord_comments_3(record_comments_3);
		sectionHDetails.setRecord_remarks_3(record_remarks_3);
		sectionHDetails.setRecord_sample_3(record_sample_3);
		sectionHDetails.setRecord_nps_3(record_nps_3);
		
		sectionHDetails.setRecord_comments_4(record_comments_4);
		sectionHDetails.setRecord_remarks_4(record_remarks_4);
		sectionHDetails.setRecord_sample_4(record_sample_4);
		sectionHDetails.setRecord_nps_4(record_nps_4);
		
		return custodianAnnex_4bPersistence.update(sectionHDetails);
		
	}
	
	public CustodianAnnex_4b addSectionIDetails(CustodianAnnex_4b sectionIDetails, String grievance_comments_1, String grievance_remarks_1,
			String grievance_sample_1, String grievance_nps_1, 
			String grievance_comments_2, String grievance_remarks_2, String grievance_sample_2, String grievance_nps_2) {
		
		sectionIDetails.setGrievance_comments_1(grievance_comments_1);
		sectionIDetails.setGrievance_remarks_1(grievance_remarks_1);
		sectionIDetails.setGrievance_sample_1(grievance_sample_1);
		sectionIDetails.setGrievance_nps_1(grievance_nps_1);
		
		sectionIDetails.setGrievance_comments_2(grievance_comments_2);
		sectionIDetails.setGrievance_remarks_2(grievance_remarks_2);
		sectionIDetails.setGrievance_sample_2(grievance_sample_2);
		sectionIDetails.setGrievance_nps_2(grievance_nps_2);
		
		
		return custodianAnnex_4bPersistence.update(sectionIDetails);
		
	}
	
	public CustodianAnnex_4b addSectionJDetails(CustodianAnnex_4b sectionJDetails, String other_comments_1, String other_remarks_1,
			String other_sample_1, String other_nps_1, 
			String other_comments_2, String other_remarks_2, String other_sample_2, String other_nps_2) {
		
		sectionJDetails.setOther_comments_1(other_comments_1);
		sectionJDetails.setOther_remarks_1(other_remarks_1);
		sectionJDetails.setOther_sample_1(other_sample_1);
		sectionJDetails.setOther_nps_1(other_nps_1);
		
		sectionJDetails.setOther_comments_2(other_comments_2);
		sectionJDetails.setOther_remarks_2(other_remarks_2);
		sectionJDetails.setOther_sample_2(other_sample_2);
		sectionJDetails.setOther_nps_2(other_nps_2);
		
		return custodianAnnex_4bPersistence.update(sectionJDetails);
		
	}
	
	public CustodianAnnex_4b addSectionKDetails(CustodianAnnex_4b sectionKDetails, String governance_comments_1, String governance_remarks_1,
			String governance_sample_1, String governance_nps_1, 
			String governance_comments_2, String governance_remarks_2, String governance_sample_2, String governance_nps_2,
			String governance_comments_3, String governance_remarks_3, String governance_sample_3, String governance_nps_3,
			String governance_comments_4, String governance_remarks_4, String governance_sample_4, String governance_nps_4) {
		
		sectionKDetails.setGovernance_comments_1(governance_comments_1);
		sectionKDetails.setGovernance_remarks_1(governance_remarks_1);
		sectionKDetails.setGovernance_sample_1(governance_sample_1);
		sectionKDetails.setGovernance_nps_1(governance_nps_1);
		
		sectionKDetails.setGovernance_comments_2(governance_comments_2);
		sectionKDetails.setGovernance_remarks_2(governance_remarks_2);
		sectionKDetails.setGovernance_sample_2(governance_sample_2);
		sectionKDetails.setGovernance_nps_2(governance_nps_2);
		
		sectionKDetails.setGovernance_comments_3(governance_comments_3);
		sectionKDetails.setGovernance_remarks_3(governance_remarks_3);
		sectionKDetails.setGovernance_sample_3(governance_sample_3);
		sectionKDetails.setGovernance_nps_3(governance_nps_3);
		
		sectionKDetails.setGovernance_comments_4(governance_comments_4);
		sectionKDetails.setGovernance_remarks_4(governance_remarks_4);
		sectionKDetails.setGovernance_sample_4(governance_sample_4);
		sectionKDetails.setGovernance_nps_4(governance_nps_4);
		
		return custodianAnnex_4bPersistence.update(sectionKDetails);
		
	}
	
	public CustodianAnnex_4b addLastDetails(CustodianAnnex_4b lastDetails, String manager, long createdBy, Date createDate) {
		
		lastDetails.setManager(manager);
		lastDetails.setCreatedby(createdBy);
		lastDetails.setCreatedon(createDate);
		
		return custodianAnnex_4bPersistence.update(lastDetails);
		
	}
	
	
}