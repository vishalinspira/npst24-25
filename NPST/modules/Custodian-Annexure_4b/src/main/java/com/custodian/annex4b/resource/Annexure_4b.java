package com.custodian.annex4b.resource;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Custodian.Annexure_4b.constants.CustodianAnnexure_4bPortletKeys;
import compliance.service.model.CustodianAnnex_4b;
import compliance.service.service.CustodianAnnex_4bLocalService;
import compliance.service.service.InvalidAccLocalService;

@Component(property = { 
		"javax.portlet.name=" + CustodianAnnexure_4bPortletKeys.CUSTODIANANNEXURE_4B,
		"mvc.command.name=" + CustodianAnnexure_4bPortletKeys.annex_4b, 
		}, 
service = MVCResourceCommand.class)

public class Annexure_4b implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(Annexure_4b.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		try {
			annex4bDetails(resourceRequest, resourceResponse);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			 _log.error(e);
		}
		
		return false;
	}
	
	public void annex4bDetails(ResourceRequest resourceRequest , ResourceResponse resourceResponse) throws ParseException {
		
		CustodianAnnex_4b details = null;
		
		try {
			details = initialDetails(resourceRequest);
			_log.info("firstDetails " + details.getId_());
		} catch (Exception e) {
			 _log.error(e);
		}
		
		if(Validator.isNotNull(details)) {
			try {
				_log.info("firstDetails " + details.getId_());
				details = sectionA(resourceRequest, details);
				details = sectionB(resourceRequest, details);
				details = sectionC(resourceRequest, details);
				details = sectionD(resourceRequest, details);
				details = sectionE(resourceRequest, details);
				details = sectionF(resourceRequest, details);
				details = sectionG(resourceRequest, details);
				details = sectionH(resourceRequest, details);
				details = sectionI(resourceRequest, details);
				details = sectionJ(resourceRequest, details);
				details = sectionK(resourceRequest, details);
				details = lastDetails(resourceRequest, details);
			} catch (Exception e) {
				 _log.error(e);
			}
		}
		
		//initialDetails(resourceRequest, resourceResponse);
		
	}
	
	public CustodianAnnex_4b initialDetails(ResourceRequest resourceRequest) throws ParseException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String fileNumber = ParamUtil.getString(resourceRequest, "fileNumber");
		
		Date date_1 = ParamUtil.getDate(resourceRequest, "date_1", dateFormat);
		
		String custodianName = ParamUtil.getString(resourceRequest, "custodianName");
		String address = ParamUtil.getString(resourceRequest, "address");
		
		String strDate_2 = ParamUtil.getString(resourceRequest, "date_2");
		strDate_2 = strDate_2.replace(",", "-");
		_log.info(strDate_2);
		//Date date_2 = ParamUtil.getDate(resourceRequest, "date_2", dateFormat_2);
		//Date date_2 = new SimpleDateFormat("yyyy").parse(strDate_2);
		
//		String strDate_3 = ParamUtil.getString(resourceRequest, "date_3");
//		strDate_3 = strDate_3.replace("/", "-");
//		_log.info(strDate_3);
		//Date date_3 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate_3);
		
		String name = ParamUtil.getString(resourceRequest, "name");
//		String strDate_4 = ParamUtil.getString(resourceRequest, "date_4");
//		strDate_4 = strDate_4.replace("/", "-");
//		_log.info(strDate_4);
		//Date date_4 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate_4);
		
		_log.info("fileNumber " + fileNumber + " date_1 " + date_1 + " custodianName " + custodianName);
		_log.info("address " + address + " date_2 " + strDate_2);
		_log.info("name " + name);
		
		CustodianAnnex_4b firstDetails = annex_4bLocalService.addInitialDetails(fileNumber, date_1, 
				custodianName, address, strDate_2, name);
		
		return firstDetails;
		
	}
	
	public CustodianAnnex_4b sectionA(ResourceRequest resourceRequest, CustodianAnnex_4b sectionADetails) {
		
		String section_a_1 = ParamUtil.getString(resourceRequest, "section_a_1");
		String regulatory_remarks_1 = ParamUtil.getString(resourceRequest, "regulatory_remarks_1");
		String regulatory_sample_1 = ParamUtil.getString(resourceRequest, "regulatory_sample_1");
		String regulatory_nps_1 = ParamUtil.getString(resourceRequest, "regulatory_nps_1");
		
		_log.info("section_a_1 " + section_a_1 + " regulatory_remarks_1 " + regulatory_remarks_1);
		_log.info("regulatory_sample_1 " + regulatory_sample_1 + " regulatory_nps_1 " + regulatory_nps_1);
		
		String section_a_2 = ParamUtil.getString(resourceRequest, "section_a_2");
		String regulatory_remarks_2 = ParamUtil.getString(resourceRequest, "regulatory_remarks_2");
		String regulatory_sample_2 = ParamUtil.getString(resourceRequest, "regulatory_sample_2");
		String regulatory_nps_2 = ParamUtil.getString(resourceRequest, "regulatory_nps_2");
		
		_log.info("section_a_2 " + section_a_2 + " regulatory_remarks_2 " + regulatory_remarks_2);
		_log.info("regulatory_sample_2 " + regulatory_sample_2 + " regulatory_nps_2 " + regulatory_nps_2);
		
		String section_a_3 = ParamUtil.getString(resourceRequest, "section_a_3");
		String regulatory_remarks_3 = ParamUtil.getString(resourceRequest, "regulatory_remarks_3");
		String regulatory_sample_3 = ParamUtil.getString(resourceRequest, "regulatory_sample_3");
		String regulatory_nps_3 = ParamUtil.getString(resourceRequest, "regulatory_nps_3");
		
		_log.info("section_a_3 " + section_a_3 + " regulatory_remarks_3 " + regulatory_remarks_3);
		_log.info("regulatory_sample_3 " + regulatory_sample_3 + " regulatory_nps_3 " + regulatory_nps_3);
		
		String section_a_4 = ParamUtil.getString(resourceRequest, "section_a_4");
		String regulatory_remarks_4 = ParamUtil.getString(resourceRequest, "regulatory_remarks_4");
		String regulatory_sample_4 = ParamUtil.getString(resourceRequest, "regulatory_sample_4");
		String regulatory_nps_4 = ParamUtil.getString(resourceRequest, "regulatory_nps_4");
		
		_log.info("section_a_4 " + section_a_4 + " regulatory_remarks_4 " + regulatory_remarks_4);
		_log.info("regulatory_sample_4 " + regulatory_sample_4 + " regulatory_nps_4 " + regulatory_nps_4);
		
		String section_a_5 = ParamUtil.getString(resourceRequest, "section_a_5");
		String regulatory_remarks_5 = ParamUtil.getString(resourceRequest, "regulatory_remarks_5");
		String regulatory_sample_5 = ParamUtil.getString(resourceRequest, "regulatory_sample_5");
		String regulatory_nps_5 = ParamUtil.getString(resourceRequest, "regulatory_nps_5");
		
		_log.info("section_a_5 " + section_a_5 + " regulatory_remarks_5 " + regulatory_remarks_5);
		_log.info("regulatory_sample_5 " + regulatory_sample_5 + " regulatory_nps_5 " + regulatory_nps_5);
		
		String section_a_6 = ParamUtil.getString(resourceRequest, "section_a_6");
		String regulatory_remarks_6 = ParamUtil.getString(resourceRequest, "regulatory_remarks_6");
		String regulatory_sample_6 = ParamUtil.getString(resourceRequest, "regulatory_sample_6");
		String regulatory_nps_6 = ParamUtil.getString(resourceRequest, "regulatory_nps_6");
		
		_log.info("section_a_6 " + section_a_6 + " regulatory_remarks_6 " + regulatory_remarks_6);
		_log.info("regulatory_sample_6 " + regulatory_sample_6 + " regulatory_nps_6 " + regulatory_nps_6);
		
		String section_a_7 = ParamUtil.getString(resourceRequest, "section_a_7");
		String regulatory_remarks_7 = ParamUtil.getString(resourceRequest, "regulatory_remarks_7");
		String regulatory_sample_7 = ParamUtil.getString(resourceRequest, "regulatory_sample_7");
		String regulatory_nps_7 = ParamUtil.getString(resourceRequest, "regulatory_nps_7");
		
		_log.info("section_a_7 " + section_a_7 + " regulatory_remarks_7 " + regulatory_remarks_7);
		_log.info("regulatory_sample_7 " + regulatory_sample_7 + " regulatory_nps_6 " + regulatory_nps_7);
		
		String section_a_8 = ParamUtil.getString(resourceRequest, "section_a_8");
		String regulatory_remarks_8 = ParamUtil.getString(resourceRequest, "regulatory_remarks_8");
		String regulatory_sample_8 = ParamUtil.getString(resourceRequest, "regulatory_sample_8");
		String regulatory_nps_8 = ParamUtil.getString(resourceRequest, "regulatory_nps_8");
		
		_log.info("section_a_8 " + section_a_8 + " regulatory_remarks_8 " + regulatory_remarks_8);
		_log.info("regulatory_sample_8 " + regulatory_sample_8 + " regulatory_nps_8 " + regulatory_nps_8);
		
		String section_a_9 = ParamUtil.getString(resourceRequest, "section_a_9");
		String regulatory_remarks_9 = ParamUtil.getString(resourceRequest, "regulatory_remarks_9");
		String regulatory_sample_9 = ParamUtil.getString(resourceRequest, "regulatory_sample_9");
		String regulatory_nps_9 = ParamUtil.getString(resourceRequest, "regulatory_nps_9");
		
		_log.info("section_a_9 " + section_a_9 + " regulatory_remarks_9 " + regulatory_remarks_9);
		_log.info("regulatory_sample_9 " + regulatory_sample_9 + " regulatory_nps_9 " + regulatory_nps_9);
		
		CustodianAnnex_4b a_details = annex_4bLocalService.addSectionADetails(sectionADetails, section_a_1, regulatory_remarks_1, regulatory_sample_1, regulatory_nps_1, 
				section_a_2, regulatory_remarks_2, regulatory_sample_2, regulatory_nps_2, 
				section_a_3, regulatory_remarks_3, regulatory_sample_3, regulatory_nps_3, 
				section_a_4, regulatory_remarks_4, regulatory_sample_4, regulatory_nps_4, 
				section_a_5, regulatory_remarks_5, regulatory_sample_5, regulatory_nps_5, 
				section_a_6, regulatory_remarks_6, regulatory_sample_6, regulatory_nps_6, 
				section_a_7, regulatory_remarks_7, regulatory_sample_7, regulatory_nps_7, 
				section_a_8, regulatory_remarks_8, regulatory_sample_8, regulatory_nps_8, 
				section_a_9, regulatory_remarks_9, regulatory_sample_9, regulatory_nps_9);
		
		_log.info(a_details.toString());
		
		return a_details;

	}
	
	public CustodianAnnex_4b sectionB(ResourceRequest resourceRequest, CustodianAnnex_4b sectionBDetails) {
		
		String section_b_1 = ParamUtil.getString(resourceRequest, "section_b_1");
		String operational_remarks_1 = ParamUtil.getString(resourceRequest, "operational_remarks_1");
		String operational_sample_1 = ParamUtil.getString(resourceRequest, "operational_sample_1");
		String operational_nps_1 = ParamUtil.getString(resourceRequest, "operational_nps_1");
		
		_log.info("section_b_1 " + section_b_1 + " operational_remarks_1 " + operational_remarks_1);
		_log.info("operational_sample_1 " + operational_sample_1 + " operational_nps_1 " + operational_nps_1);
		
		String section_b_2 = ParamUtil.getString(resourceRequest, "section_b_2");
		String operational_remarks_2 = ParamUtil.getString(resourceRequest, "operational_remarks_2");
		String operational_sample_2 = ParamUtil.getString(resourceRequest, "operational_sample_2");
		String operational_nps_2 = ParamUtil.getString(resourceRequest, "operational_nps_2");
		
		_log.info("section_b_2 " + section_b_2 + " operational_remarks_2 " + operational_remarks_2);
		_log.info("operational_sample_2 " + operational_sample_2 + " operational_nps_2 " + operational_nps_2);
		
		String section_b_3 = ParamUtil.getString(resourceRequest, "section_b_3");
		String operational_remarks_3 = ParamUtil.getString(resourceRequest, "operational_remarks_3");
		String operational_sample_3 = ParamUtil.getString(resourceRequest, "operational_sample_3");
		String operational_nps_3 = ParamUtil.getString(resourceRequest, "operational_nps_3");
		
		_log.info("section_b_3 " + section_b_3 + " operational_remarks_1 " + operational_remarks_3);
		_log.info("operational_sample_1 " + operational_sample_3 + " operational_nps_1 " + operational_nps_3);
		
		String section_b_4 = ParamUtil.getString(resourceRequest, "section_b_4");
		String operational_remarks_4 = ParamUtil.getString(resourceRequest, "operational_remarks_4");
		String operational_sample_4 = ParamUtil.getString(resourceRequest, "operational_sample_4");
		String operational_nps_4 = ParamUtil.getString(resourceRequest, "operational_nps_4");
		
		_log.info("section_b_4 " + section_b_4 + " operational_remarks_1 " + operational_remarks_4);
		_log.info("operational_sample_1 " + operational_sample_4 + " operational_nps_1 " + operational_nps_4);
		
		String section_b_5 = ParamUtil.getString(resourceRequest, "section_b_5");
		String operational_remarks_5 = ParamUtil.getString(resourceRequest, "operational_remarks_5");
		String operational_sample_5 = ParamUtil.getString(resourceRequest, "operational_sample_5");
		String operational_nps_5 = ParamUtil.getString(resourceRequest, "operational_nps_5");
		
		_log.info("section_b_5 " + section_b_5 + " operational_remarks_1 " + operational_remarks_5);
		_log.info("operational_sample_1 " + operational_sample_5 + " operational_nps_1 " + operational_nps_5);
		
		String section_b_6 = ParamUtil.getString(resourceRequest, "section_b_6");
		String operational_remarks_6 = ParamUtil.getString(resourceRequest, "operational_remarks_6");
		String operational_sample_6 = ParamUtil.getString(resourceRequest, "operational_sample_6");
		String operational_nps_6 = ParamUtil.getString(resourceRequest, "operational_nps_6");
		
		_log.info("section_b_6 " + section_b_6 + " operational_remarks_1 " + operational_remarks_6);
		_log.info("operational_sample_6 " + operational_sample_6 + " operational_nps_1 " + operational_nps_6);
		
		String section_b_7 = ParamUtil.getString(resourceRequest, "section_b_7");
		String operational_remarks_7 = ParamUtil.getString(resourceRequest, "operational_remarks_7");
		String operational_sample_7 = ParamUtil.getString(resourceRequest, "operational_sample_7");
		String operational_nps_7 = ParamUtil.getString(resourceRequest, "operational_nps_7");
		
		_log.info("section_b_7 " + section_b_7 + " operational_remarks_7 " + operational_remarks_7);
		_log.info("operational_sample_7 " + operational_sample_7 + " operational_nps_1 " + operational_nps_7);
		
		String section_b_8 = ParamUtil.getString(resourceRequest, "section_b_8");
		String operational_remarks_8 = ParamUtil.getString(resourceRequest, "operational_remarks_8");
		String operational_sample_8 = ParamUtil.getString(resourceRequest, "operational_sample_8");
		String operational_nps_8 = ParamUtil.getString(resourceRequest, "operational_nps_8");
		
		_log.info("section_b_8 " + section_b_8 + " operational_remarks_1 " + operational_remarks_8);
		_log.info("operational_sample_1 " + operational_sample_8 + " operational_nps_1 " + operational_nps_8);
		
		String section_b_9 = ParamUtil.getString(resourceRequest, "section_b_9");
		String operational_remarks_9 = ParamUtil.getString(resourceRequest, "operational_remarks_9");
		String operational_sample_9 = ParamUtil.getString(resourceRequest, "operational_sample_9");
		String operational_nps_9 = ParamUtil.getString(resourceRequest, "operational_nps_9");
		
		_log.info("section_b_9 " + section_b_9 + " operational_remarks_1 " + operational_remarks_9);
		_log.info("operational_sample_1 " + operational_sample_9 + " operational_nps_1 " + operational_nps_9);
		
		String section_b_10 = ParamUtil.getString(resourceRequest, "section_b_10");
		String operational_remarks_10 = ParamUtil.getString(resourceRequest, "operational_remarks_10");
		String operational_sample_10 = ParamUtil.getString(resourceRequest, "operational_sample_10");
		String operational_nps_10 = ParamUtil.getString(resourceRequest, "operational_nps_10");
		
		_log.info("section_b_10 " + section_b_10 + " operational_remarks_1 " + operational_remarks_10);
		_log.info("operational_sample_1 " + operational_sample_10 + " operational_nps_1 " + operational_nps_10);
		
		String section_b_11 = ParamUtil.getString(resourceRequest, "section_b_11");
		String operational_remarks_11 = ParamUtil.getString(resourceRequest, "operational_remarks_11");
		String operational_sample_11 = ParamUtil.getString(resourceRequest, "operational_sample_11");
		String operational_nps_11 = ParamUtil.getString(resourceRequest, "operational_nps_11");
		
		_log.info("section_b_11 " + section_b_11 + " operational_remarks_1 " + operational_remarks_11);
		_log.info("operational_sample_1 " + operational_sample_11 + " operational_nps_1 " + operational_nps_11);
		
		String section_b_12 = ParamUtil.getString(resourceRequest, "section_b_12");
		String operational_remarks_12 = ParamUtil.getString(resourceRequest, "operational_remarks_12");
		String operational_sample_12 = ParamUtil.getString(resourceRequest, "operational_sample_12");
		String operational_nps_12 = ParamUtil.getString(resourceRequest, "operational_nps_12");
		
		_log.info("section_b_12 " + section_b_12 + " operational_remarks_1 " + operational_remarks_12);
		_log.info("operational_sample_1 " + operational_sample_12 + " operational_nps_1 " + operational_nps_12);
		
		String section_b_13 = ParamUtil.getString(resourceRequest, "section_b_13");
		String operational_remarks_13 = ParamUtil.getString(resourceRequest, "operational_remarks_13");
		String operational_sample_13 = ParamUtil.getString(resourceRequest, "operational_sample_13");
		String operational_nps_13 = ParamUtil.getString(resourceRequest, "operational_nps_13");
		
		_log.info("section_b_13 " + section_b_13 + " operational_remarks_1 " + operational_remarks_13);
		_log.info("operational_sample_1 " + operational_sample_13 + " operational_nps_1 " + operational_nps_13);
		
		String section_b_14 = ParamUtil.getString(resourceRequest, "section_b_14");
		String operational_remarks_14 = ParamUtil.getString(resourceRequest, "operational_remarks_14");
		String operational_sample_14 = ParamUtil.getString(resourceRequest, "operational_sample_14");
		String operational_nps_14 = ParamUtil.getString(resourceRequest, "operational_nps_14");
		
		_log.info("section_b_14 " + section_b_14 + " operational_remarks_1 " + operational_remarks_14);
		_log.info("operational_sample_1 " + operational_sample_14 + " operational_nps_1 " + operational_nps_14);
		
		String section_b_15 = ParamUtil.getString(resourceRequest, "section_b_15");
		String operational_remarks_15 = ParamUtil.getString(resourceRequest, "operational_remarks_15");
		String operational_sample_15 = ParamUtil.getString(resourceRequest, "operational_sample_15");
		String operational_nps_15 = ParamUtil.getString(resourceRequest, "operational_nps_15");
		
		_log.info("section_b_15 " + section_b_15 + " operational_remarks_1 " + operational_remarks_15);
		_log.info("operational_sample_1 " + operational_sample_15 + " operational_nps_1 " + operational_nps_15);
		
		String section_b_16 = ParamUtil.getString(resourceRequest, "section_b_16");
		String operational_remarks_16 = ParamUtil.getString(resourceRequest, "operational_remarks_16");
		String operational_sample_16 = ParamUtil.getString(resourceRequest, "operational_sample_16");
		String operational_nps_16 = ParamUtil.getString(resourceRequest, "operational_nps_16");
		
		_log.info("section_b_16 " + section_b_16 + " operational_remarks_1 " + operational_remarks_16);
		_log.info("operational_sample_1 " + operational_sample_16 + " operational_nps_1 " + operational_nps_16);
		
		String section_b_17 = ParamUtil.getString(resourceRequest, "section_b_17");
		String operational_remarks_17 = ParamUtil.getString(resourceRequest, "operational_remarks_17");
		String operational_sample_17 = ParamUtil.getString(resourceRequest, "operational_sample_17");
		String operational_nps_17 = ParamUtil.getString(resourceRequest, "operational_nps_17");
		
		_log.info("section_b_17 " + section_b_17 + " operational_remarks_1 " + operational_remarks_17);
		_log.info("operational_sample_1 " + operational_sample_17 + " operational_nps_1 " + operational_nps_17);
		
		String section_b_18 = ParamUtil.getString(resourceRequest, "section_b_18");
		String operational_remarks_18 = ParamUtil.getString(resourceRequest, "operational_remarks_18");
		String operational_sample_18 = ParamUtil.getString(resourceRequest, "operational_sample_18");
		String operational_nps_18 = ParamUtil.getString(resourceRequest, "operational_nps_18");
		
		_log.info("section_b_18 " + section_b_18 + " operational_remarks_1 " + operational_remarks_18);
		_log.info("operational_sample_1 " + operational_sample_18 + " operational_nps_1 " + operational_nps_18);
		
		String section_b_19 = ParamUtil.getString(resourceRequest, "section_b_19");
		String operational_remarks_19 = ParamUtil.getString(resourceRequest, "operational_remarks_19");
		String operational_sample_19 = ParamUtil.getString(resourceRequest, "operational_sample_19");
		String operational_nps_19 = ParamUtil.getString(resourceRequest, "operational_nps_19");
		
		_log.info("section_b_19 " + section_b_19 + " operational_remarks_1 " + operational_remarks_19);
		_log.info("operational_sample_1 " + operational_sample_19 + " operational_nps_1 " + operational_nps_19);
		
		CustodianAnnex_4b b_details = annex_4bLocalService.addSectionBDetails(sectionBDetails, section_b_1, operational_remarks_1, operational_sample_1, operational_nps_1, 
				section_b_2, operational_remarks_2, operational_sample_2, operational_nps_2, 
				section_b_3, operational_remarks_3, operational_sample_3, operational_nps_3, 
				section_b_4, operational_remarks_4, operational_sample_4, operational_nps_4, 
				section_b_5, operational_remarks_5, operational_sample_5, operational_nps_5, 
				section_b_6, operational_remarks_6, operational_sample_6, operational_nps_6, 
				section_b_7, operational_remarks_7, operational_sample_7, operational_nps_7, 
				section_b_8, operational_remarks_8, operational_sample_8, operational_nps_8, 
				section_b_9, operational_remarks_9, operational_sample_9, operational_nps_9, 
				section_b_10, operational_remarks_10, operational_sample_10, operational_nps_10, 
				section_b_11, operational_remarks_11, operational_sample_11, operational_nps_11, 
				section_b_12, operational_remarks_12, operational_sample_12, operational_nps_12, 
				section_b_13, operational_remarks_13, operational_sample_13, operational_nps_13, 
				section_b_14, operational_remarks_14, operational_sample_14, operational_nps_14, 
				section_b_15, operational_remarks_15, operational_sample_15, operational_nps_15, 
				section_b_16, operational_remarks_16, operational_sample_16, operational_nps_16, 
				section_b_17, operational_remarks_17, operational_sample_17, operational_nps_17, 
				section_b_18, operational_remarks_18, operational_sample_18, operational_nps_18, 
				section_b_19, operational_remarks_19, operational_sample_19, operational_nps_19);
		
		return b_details;

	}
	
	public CustodianAnnex_4b sectionC(ResourceRequest resourceRequest, CustodianAnnex_4b sectionCDetails) {
		
		String section_c_1 = ParamUtil.getString(resourceRequest, "section_c_1");
		String timely_remarks_1 = ParamUtil.getString(resourceRequest, "timely_remarks_1");
		String timely_sample_1 = ParamUtil.getString(resourceRequest, "timely_sample_1");
		String timely_nps_1 = ParamUtil.getString(resourceRequest, "timely_nps_1");
		
		String section_c_2 = ParamUtil.getString(resourceRequest, "section_c_2");
		String timely_remarks_2 = ParamUtil.getString(resourceRequest, "timely_remarks_2");
		String timely_sample_2 = ParamUtil.getString(resourceRequest, "timely_sample_2");
		String timely_nps_2 = ParamUtil.getString(resourceRequest, "timely_nps_2");
		
		String section_c_3 = ParamUtil.getString(resourceRequest, "section_c_3");
		String timely_remarks_3 = ParamUtil.getString(resourceRequest, "timely_remarks_3");
		String timely_sample_3 = ParamUtil.getString(resourceRequest, "timely_sample_3");
		String timely_nps_3 = ParamUtil.getString(resourceRequest, "timely_nps_3");
		
		String section_c_4 = ParamUtil.getString(resourceRequest, "section_c_4");
		String timely_remarks_4 = ParamUtil.getString(resourceRequest, "timely_remarks_4");
		String timely_sample_4 = ParamUtil.getString(resourceRequest, "timely_sample_4");
		String timely_nps_4 = ParamUtil.getString(resourceRequest, "timely_nps_4");
		
		String section_c_5 = ParamUtil.getString(resourceRequest, "section_c_5");
		String timely_remarks_5 = ParamUtil.getString(resourceRequest, "timely_remarks_5");
		String timely_sample_5 = ParamUtil.getString(resourceRequest, "timely_sample_5");
		String timely_nps_5 = ParamUtil.getString(resourceRequest, "timely_nps_5");
		
		String section_c_6 = ParamUtil.getString(resourceRequest, "section_c_6");
		String timely_remarks_6 = ParamUtil.getString(resourceRequest, "timely_remarks_6");
		String timely_sample_6 = ParamUtil.getString(resourceRequest, "timely_sample_6");
		String timely_nps_6 = ParamUtil.getString(resourceRequest, "timely_nps_6");
		
		String section_c_7 = ParamUtil.getString(resourceRequest, "section_c_7");
		String timely_remarks_7 = ParamUtil.getString(resourceRequest, "timely_remarks_7");
		String timely_sample_7 = ParamUtil.getString(resourceRequest, "timely_sample_7");
		String timely_nps_7 = ParamUtil.getString(resourceRequest, "timely_nps_7");
		
		String section_c_8 = ParamUtil.getString(resourceRequest, "section_c_8");
		String timely_remarks_8 = ParamUtil.getString(resourceRequest, "timely_remarks_8");
		String timely_sample_8 = ParamUtil.getString(resourceRequest, "timely_sample_8");
		String timely_nps_8 = ParamUtil.getString(resourceRequest, "timely_nps_8");
		
		String section_c_9 = ParamUtil.getString(resourceRequest, "section_c_9");
		String timely_remarks_9 = ParamUtil.getString(resourceRequest, "timely_remarks_9");
		String timely_sample_9 = ParamUtil.getString(resourceRequest, "timely_sample_9");
		String timely_nps_9 = ParamUtil.getString(resourceRequest, "timely_nps_9");
		
		String section_c_10 = ParamUtil.getString(resourceRequest, "section_c_10");
		String timely_remarks_10 = ParamUtil.getString(resourceRequest, "timely_remarks_10");
		String timely_sample_10 = ParamUtil.getString(resourceRequest, "timely_sample_10");
		String timely_nps_10 = ParamUtil.getString(resourceRequest, "timely_nps_10");
		
		String section_c_11 = ParamUtil.getString(resourceRequest, "section_c_11");
		String timely_remarks_11 = ParamUtil.getString(resourceRequest, "timely_remarks_11");
		String timely_sample_11 = ParamUtil.getString(resourceRequest, "timely_sample_11");
		String timely_nps_11 = ParamUtil.getString(resourceRequest, "timely_nps_11");
		
		String section_c_12 = ParamUtil.getString(resourceRequest, "section_c_12");
		String timely_remarks_12 = ParamUtil.getString(resourceRequest, "timely_remarks_12");
		String timely_sample_12 = ParamUtil.getString(resourceRequest, "timely_sample_12");
		String timely_nps_12 = ParamUtil.getString(resourceRequest, "timely_nps_12");
		
		String section_c_13 = ParamUtil.getString(resourceRequest, "section_c_13");
		String timely_remarks_13 = ParamUtil.getString(resourceRequest, "timely_remarks_13");
		String timely_sample_13 = ParamUtil.getString(resourceRequest, "timely_sample_13");
		String timely_nps_13 = ParamUtil.getString(resourceRequest, "timely_nps_13");
		
		CustodianAnnex_4b c_details = annex_4bLocalService.addSectionCDetails(sectionCDetails, section_c_1, timely_remarks_1, timely_sample_1, timely_nps_1, 
				section_c_2, timely_remarks_2, timely_sample_2, timely_nps_2, 
				section_c_3, timely_remarks_3, timely_sample_3, timely_nps_3, 
				section_c_4, timely_remarks_4, timely_sample_4, timely_nps_4, 
				section_c_5, timely_remarks_5, timely_sample_5, timely_nps_5, 
				section_c_6, timely_remarks_6, timely_sample_6, timely_nps_6, 
				section_c_7, timely_remarks_7, timely_sample_7, timely_nps_7, 
				section_c_8, timely_remarks_8, timely_sample_8, timely_nps_8, 
				section_c_9, timely_remarks_9, timely_sample_9, timely_nps_9, 
				section_c_10, timely_remarks_10, timely_sample_10, timely_nps_10, 
				section_c_11, timely_remarks_11, timely_sample_11, timely_nps_11, 
				section_c_12, timely_remarks_12, timely_sample_12, timely_nps_12, 
				section_c_13, timely_remarks_13, timely_sample_13, timely_nps_13);
		
		return c_details;

	}
	
	public CustodianAnnex_4b sectionD(ResourceRequest resourceRequest, CustodianAnnex_4b sectionDDetails) {
		
		String section_d_1 = ParamUtil.getString(resourceRequest, "section_d_1");
		String custodian_remarks_1 = ParamUtil.getString(resourceRequest, "custodian_remarks_1");
		String custodian_sample_1 = ParamUtil.getString(resourceRequest, "custodian_sample_1");
		String custodian_nps_1 = ParamUtil.getString(resourceRequest, "custodian_nps_1");
		
		String section_d_2 = ParamUtil.getString(resourceRequest, "section_d_2");
		String custodian_remarks_2 = ParamUtil.getString(resourceRequest, "custodian_remarks_2");
		String custodian_sample_2 = ParamUtil.getString(resourceRequest, "custodian_sample_2");
		String custodian_nps_2 = ParamUtil.getString(resourceRequest, "custodian_nps_2");
		
		CustodianAnnex_4b d_details = annex_4bLocalService.addSectionDDetails(sectionDDetails, section_d_1, custodian_remarks_1, custodian_sample_1, custodian_nps_1, 
				section_d_2, custodian_remarks_2, custodian_sample_2, custodian_nps_2);
				
		return d_details;

	}
	
	public CustodianAnnex_4b sectionE(ResourceRequest resourceRequest, CustodianAnnex_4b sectionEDetails) {
		
		String section_e_1 = ParamUtil.getString(resourceRequest, "section_e_1");
		String admin_remarks_1 = ParamUtil.getString(resourceRequest, "admin_remarks_1");
		String admin_sample_1 = ParamUtil.getString(resourceRequest, "admin_sample_1");
		String admin_nps_1 = ParamUtil.getString(resourceRequest, "admin_nps_1");
		
		String section_e_2 = ParamUtil.getString(resourceRequest, "section_e_2");
		String admin_remarks_2 = ParamUtil.getString(resourceRequest, "admin_remarks_2");
		String admin_sample_2 = ParamUtil.getString(resourceRequest, "admin_sample_2");
		String admin_nps_2 = ParamUtil.getString(resourceRequest, "admin_nps_2");
		
		String section_e_3 = ParamUtil.getString(resourceRequest, "section_e_3");
		String admin_remarks_3 = ParamUtil.getString(resourceRequest, "admin_remarks_3");
		String admin_sample_3 = ParamUtil.getString(resourceRequest, "admin_sample_3");
		String admin_nps_3 = ParamUtil.getString(resourceRequest, "admin_nps_3");
		
		String section_e_4 = ParamUtil.getString(resourceRequest, "section_e_4");
		String admin_remarks_4 = ParamUtil.getString(resourceRequest, "admin_remarks_4");
		String admin_sample_4 = ParamUtil.getString(resourceRequest, "admin_sample_4");
		String admin_nps_4 = ParamUtil.getString(resourceRequest, "admin_nps_4");
		
		String section_e_5 = ParamUtil.getString(resourceRequest, "section_e_5");
		String admin_remarks_5 = ParamUtil.getString(resourceRequest, "admin_remarks_5");
		String admin_sample_5 = ParamUtil.getString(resourceRequest, "admin_sample_5");
		String admin_nps_5 = ParamUtil.getString(resourceRequest, "admin_nps_5");
		
		CustodianAnnex_4b e_details = annex_4bLocalService.addSectionEDetails(sectionEDetails, section_e_1, admin_remarks_1, admin_sample_1, admin_nps_1, 
				section_e_2, admin_remarks_2, admin_sample_2, admin_nps_2, 
				section_e_3, admin_remarks_3, admin_sample_3, admin_nps_3, 
				section_e_4, admin_remarks_4, admin_sample_4, admin_nps_4, 
				section_e_5, admin_remarks_5, admin_sample_5, admin_nps_5);
		
		return e_details;

	}
	
	public CustodianAnnex_4b sectionF(ResourceRequest resourceRequest, CustodianAnnex_4b sectionFDetails) {
		
		String section_f_1 = ParamUtil.getString(resourceRequest, "section_f_1");
		String infra_remarks_1 = ParamUtil.getString(resourceRequest, "infra_remarks_1");
		String infra_sample_1 = ParamUtil.getString(resourceRequest, "infra_sample_1");
		String infra_nps_1 = ParamUtil.getString(resourceRequest, "infra_nps_1");
		
		String section_f_2 = ParamUtil.getString(resourceRequest, "section_f_2");
		String infra_remarks_2 = ParamUtil.getString(resourceRequest, "infra_remarks_2");
		String infra_sample_2 = ParamUtil.getString(resourceRequest, "infra_sample_2");
		String infra_nps_2 = ParamUtil.getString(resourceRequest, "infra_nps_2");
		
		String section_f_3 = ParamUtil.getString(resourceRequest, "section_f_3");
		String infra_remarks_3 = ParamUtil.getString(resourceRequest, "infra_remarks_3");
		String infra_sample_3 = ParamUtil.getString(resourceRequest, "infra_sample_3");
		String infra_nps_3 = ParamUtil.getString(resourceRequest, "infra_nps_3");
		
		String section_f_4 = ParamUtil.getString(resourceRequest, "section_f_4");
		String infra_remarks_4 = ParamUtil.getString(resourceRequest, "infra_remarks_4");
		String infra_sample_4 = ParamUtil.getString(resourceRequest, "infra_sample_4");
		String infra_nps_4 = ParamUtil.getString(resourceRequest, "infra_nps_4");
		
		String section_f_5 = ParamUtil.getString(resourceRequest, "section_f_5");
		String infra_remarks_5 = ParamUtil.getString(resourceRequest, "infra_remarks_5");
		String infra_sample_5 = ParamUtil.getString(resourceRequest, "infra_sample_5");
		String infra_nps_5 = ParamUtil.getString(resourceRequest, "infra_nps_5");
		
		String section_f_6 = ParamUtil.getString(resourceRequest, "section_f_6");
		String infra_remarks_6 = ParamUtil.getString(resourceRequest, "infra_remarks_6");
		String infra_sample_6 = ParamUtil.getString(resourceRequest, "infra_sample_6");
		String infra_nps_6 = ParamUtil.getString(resourceRequest, "infra_nps_6");
		
		String section_f_7 = ParamUtil.getString(resourceRequest, "section_f_7");
		String infra_remarks_7 = ParamUtil.getString(resourceRequest, "infra_remarks_7");
		String infra_sample_7 = ParamUtil.getString(resourceRequest, "infra_sample_7");
		String infra_nps_7 = ParamUtil.getString(resourceRequest, "infra_nps_7");
		
		String section_f_8 = ParamUtil.getString(resourceRequest, "section_f_8");
		String infra_remarks_8 = ParamUtil.getString(resourceRequest, "infra_remarks_8");
		String infra_sample_8 = ParamUtil.getString(resourceRequest, "infra_sample_8");
		String infra_nps_8 = ParamUtil.getString(resourceRequest, "infra_nps_8");
		
		String section_f_9 = ParamUtil.getString(resourceRequest, "section_f_9");
		String infra_remarks_9 = ParamUtil.getString(resourceRequest, "infra_remarks_9");
		String infra_sample_9 = ParamUtil.getString(resourceRequest, "infra_sample_9");
		String infra_nps_9 = ParamUtil.getString(resourceRequest, "infra_nps_9");
		
		String section_f_10 = ParamUtil.getString(resourceRequest, "section_f_10");
		String infra_remarks_10 = ParamUtil.getString(resourceRequest, "infra_remarks_10");
		String infra_sample_10 = ParamUtil.getString(resourceRequest, "infra_sample_10");
		String infra_nps_10 = ParamUtil.getString(resourceRequest, "infra_nps_10");
		
		String section_f_11 = ParamUtil.getString(resourceRequest, "section_f_11");
		String infra_remarks_11 = ParamUtil.getString(resourceRequest, "infra_remarks_11");
		String infra_sample_11 = ParamUtil.getString(resourceRequest, "infra_sample_11");
		String infra_nps_11 = ParamUtil.getString(resourceRequest, "infra_nps_11");
		
		String section_f_12 = ParamUtil.getString(resourceRequest, "section_f_12");
		String infra_remarks_12 = ParamUtil.getString(resourceRequest, "infra_remarks_12");
		String infra_sample_12 = ParamUtil.getString(resourceRequest, "infra_sample_12");
		String infra_nps_12 = ParamUtil.getString(resourceRequest, "infra_nps_12");
		
		CustodianAnnex_4b f_details = annex_4bLocalService.addSectionFDetails(sectionFDetails, section_f_1, infra_remarks_1, infra_sample_1, infra_nps_1, 
				section_f_2, infra_remarks_2, infra_sample_2, infra_nps_2, 
				section_f_3, infra_remarks_3, infra_sample_3, infra_nps_3, 
				section_f_4, infra_remarks_4, infra_sample_4, infra_nps_4, 
				section_f_5, infra_remarks_5, infra_sample_5, infra_nps_5, 
				section_f_6, infra_remarks_6, infra_sample_6, infra_nps_6, 
				section_f_7, infra_remarks_7, infra_sample_7, infra_nps_7, 
				section_f_8, infra_remarks_8, infra_sample_8, infra_nps_8, 
				section_f_9, infra_remarks_9, infra_sample_9, infra_nps_9, 
				section_f_10, infra_remarks_10, infra_sample_10, infra_nps_10, 
				section_f_11, infra_remarks_11, infra_sample_11, infra_nps_11, 
				section_f_12, infra_remarks_12, infra_sample_12, infra_nps_12);
		
		return f_details;

	}
	
	public CustodianAnnex_4b sectionG(ResourceRequest resourceRequest, CustodianAnnex_4b sectionGDetails) {
		
		String section_g_1 = ParamUtil.getString(resourceRequest, "section_g_1");
		String protection_remarks_1 = ParamUtil.getString(resourceRequest, "protection_remarks_1");
		String protection_sample_1 = ParamUtil.getString(resourceRequest, "protection_sample_1");
		String protection_nps_1 = ParamUtil.getString(resourceRequest, "protection_nps_1");
		
		String section_g_2 = ParamUtil.getString(resourceRequest, "section_g_2");
		String protection_remarks_2 = ParamUtil.getString(resourceRequest, "protection_remarks_2");
		String protection_sample_2 = ParamUtil.getString(resourceRequest, "protection_sample_2");
		String protection_nps_2 = ParamUtil.getString(resourceRequest, "protection_nps_2");
		
		String section_g_3 = ParamUtil.getString(resourceRequest, "section_g_3");
		String protection_remarks_3 = ParamUtil.getString(resourceRequest, "protection_remarks_3");
		String protection_sample_3 = ParamUtil.getString(resourceRequest, "protection_sample_3");
		String protection_nps_3 = ParamUtil.getString(resourceRequest, "protection_nps_3");
		
		String section_g_4 = ParamUtil.getString(resourceRequest, "section_g_4");
		String protection_remarks_4 = ParamUtil.getString(resourceRequest, "protection_remarks_4");
		String protection_sample_4 = ParamUtil.getString(resourceRequest, "protection_sample_4");
		String protection_nps_4 = ParamUtil.getString(resourceRequest, "protection_nps_4");
		
		String section_g_5 = ParamUtil.getString(resourceRequest, "section_g_5");
		String protection_remarks_5 = ParamUtil.getString(resourceRequest, "protection_remarks_5");
		String protection_sample_5 = ParamUtil.getString(resourceRequest, "protection_sample_5");
		String protection_nps_5 = ParamUtil.getString(resourceRequest, "protection_nps_5");
		
		String section_g_6 = ParamUtil.getString(resourceRequest, "section_g_6");
		String protection_remarks_6 = ParamUtil.getString(resourceRequest, "protection_remarks_6");
		String protection_sample_6 = ParamUtil.getString(resourceRequest, "protection_sample_6");
		String protection_nps_6 = ParamUtil.getString(resourceRequest, "protection_nps_6");
		
		CustodianAnnex_4b g_details = annex_4bLocalService.addSectionGDetails(sectionGDetails, section_g_1, protection_remarks_1, protection_sample_1, protection_nps_1, 
				section_g_2, protection_remarks_2, protection_sample_2, protection_nps_2, 
				section_g_3, protection_remarks_3, protection_sample_3, protection_nps_3, 
				section_g_4, protection_remarks_4, protection_sample_4, protection_nps_4, 
				section_g_5, protection_remarks_5, protection_sample_5, protection_nps_5, 
				section_g_6, protection_remarks_6, protection_sample_6, protection_nps_6);
		
		
		return g_details;
		
		
	}
	
	public CustodianAnnex_4b sectionH(ResourceRequest resourceRequest, CustodianAnnex_4b sectionHDetails) {
		
		String section_h_1 = ParamUtil.getString(resourceRequest, "section_h_1");
		String record_remarks_1 = ParamUtil.getString(resourceRequest, "record_remarks_1");
		String record_sample_1 = ParamUtil.getString(resourceRequest, "record_sample_1");
		String record_nps_1 = ParamUtil.getString(resourceRequest, "record_nps_1");
		
		String section_h_2 = ParamUtil.getString(resourceRequest, "section_h_2");
		String record_remarks_2 = ParamUtil.getString(resourceRequest, "record_remarks_2");
		String record_sample_2 = ParamUtil.getString(resourceRequest, "record_sample_2");
		String record_nps_2 = ParamUtil.getString(resourceRequest, "record_nps_2");
		
		String section_h_3 = ParamUtil.getString(resourceRequest, "section_h_3");
		String record_remarks_3 = ParamUtil.getString(resourceRequest, "record_remarks_3");
		String record_sample_3 = ParamUtil.getString(resourceRequest, "record_sample_3");
		String record_nps_3 = ParamUtil.getString(resourceRequest, "record_nps_3");
		
		String section_h_4 = ParamUtil.getString(resourceRequest, "section_h_4");
		String record_remarks_4 = ParamUtil.getString(resourceRequest, "record_remarks_4");
		String record_sample_4 = ParamUtil.getString(resourceRequest, "record_sample_4");
		String record_nps_4 = ParamUtil.getString(resourceRequest, "record_nps_4");
		
		CustodianAnnex_4b h_details = annex_4bLocalService.addSectionHDetails(sectionHDetails, section_h_1, record_remarks_1, record_sample_1, record_nps_1, 
				section_h_2, record_remarks_2, record_sample_2, record_nps_2, 
				section_h_3, record_remarks_3, record_sample_3, record_nps_3, 
				section_h_4, record_remarks_4, record_sample_4, record_nps_4);
		
		return h_details;

	}
	
	public CustodianAnnex_4b sectionI(ResourceRequest resourceRequest, CustodianAnnex_4b sectionIDetails) {
		
		String section_i_1 = ParamUtil.getString(resourceRequest, "section_i_1");
		String grievance_remarks_1 = ParamUtil.getString(resourceRequest, "grievance_remarks_1");
		String grievance_sample_1 = ParamUtil.getString(resourceRequest, "grievance_sample_1");
		String grievance_nps_1 = ParamUtil.getString(resourceRequest, "grievance_nps_1");
		
		String section_i_2 = ParamUtil.getString(resourceRequest, "section_i_2");
		String grievance_remarks_2 = ParamUtil.getString(resourceRequest, "grievance_remarks_2");
		String grievance_sample_2 = ParamUtil.getString(resourceRequest, "grievance_sample_2");
		String grievance_nps_2 = ParamUtil.getString(resourceRequest, "grievance_nps_2");
		
		CustodianAnnex_4b i_details = annex_4bLocalService.addSectionIDetails(sectionIDetails, 
				section_i_1, grievance_remarks_1, grievance_sample_1, grievance_nps_1, 
				section_i_2, grievance_remarks_2, grievance_sample_2, grievance_nps_2);
		
		return i_details;

	}
	
	public CustodianAnnex_4b sectionJ(ResourceRequest resourceRequest, CustodianAnnex_4b sectionJDetails) {
		
		String section_j_1 = ParamUtil.getString(resourceRequest, "section_j_1");
		String other_remarks_1 = ParamUtil.getString(resourceRequest, "other_remarks_1");
		String other_sample_1 = ParamUtil.getString(resourceRequest, "other_sample_1");
		String other_nps_1 = ParamUtil.getString(resourceRequest, "other_nps_1");
		
		String section_j_2 = ParamUtil.getString(resourceRequest, "section_j_2");
		String other_remarks_2 = ParamUtil.getString(resourceRequest, "other_remarks_2");
		String other_sample_2 = ParamUtil.getString(resourceRequest, "other_sample_2");
		String other_nps_2 = ParamUtil.getString(resourceRequest, "other_nps_2");
		
		CustodianAnnex_4b j_details = annex_4bLocalService.addSectionJDetails(sectionJDetails, 
				section_j_1, other_remarks_1, other_sample_1, other_nps_1, 
				section_j_2, other_remarks_2, other_sample_2, other_nps_2);
		
		return j_details;
		
	}
	
	public CustodianAnnex_4b sectionK(ResourceRequest resourceRequest, CustodianAnnex_4b sectionKDetails) {
		
		String section_k_1 = ParamUtil.getString(resourceRequest, "section_k_1");
		String governance_remarks_1 = ParamUtil.getString(resourceRequest, "governance_remarks_1");
		String governance_sample_1 = ParamUtil.getString(resourceRequest, "governance_sample_1");
		String governance_nps_1 = ParamUtil.getString(resourceRequest, "governance_nps_1");
		
		String section_k_2 = ParamUtil.getString(resourceRequest, "section_k_2");
		String governance_remarks_2 = ParamUtil.getString(resourceRequest, "governance_remarks_2");
		String governance_sample_2 = ParamUtil.getString(resourceRequest, "governance_sample_2");
		String governance_nps_2 = ParamUtil.getString(resourceRequest, "governance_nps_2");
		
		String section_k_3 = ParamUtil.getString(resourceRequest, "section_k_3");
		String governance_remarks_3 = ParamUtil.getString(resourceRequest, "governance_remarks_3");
		String governance_sample_3 = ParamUtil.getString(resourceRequest, "governance_sample_3");
		String governance_nps_3 = ParamUtil.getString(resourceRequest, "governance_nps_3");
		
		String section_k_4 = ParamUtil.getString(resourceRequest, "section_k_4");
		String governance_remarks_4 = ParamUtil.getString(resourceRequest, "governance_remarks_4");
		String governance_sample_4 = ParamUtil.getString(resourceRequest, "governance_sample_4");
		String governance_nps_4 = ParamUtil.getString(resourceRequest, "governance_nps_4");
		
		CustodianAnnex_4b k_details = annex_4bLocalService.addSectionKDetails(sectionKDetails, 
				section_k_1, governance_remarks_1, governance_sample_1, governance_nps_1, 
				section_k_2, governance_remarks_2, governance_sample_2, governance_nps_2, 
				section_k_3, governance_remarks_3, governance_sample_3, governance_nps_3, 
				section_k_4, governance_remarks_4, governance_sample_4, governance_nps_4);
		
		return k_details;
		
	}
	
	public CustodianAnnex_4b lastDetails(ResourceRequest resourceRequest, CustodianAnnex_4b lastDetails) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String manager = ParamUtil.getString(resourceRequest, "manager");
		
		CustodianAnnex_4b l_details = annex_4bLocalService.addLastDetails(lastDetails, 
				manager, themeDisplay.getUserId(), new Date());
		
		return l_details;
		
	}
	
	@Reference
	CustodianAnnex_4bLocalService annex_4bLocalService;

}
