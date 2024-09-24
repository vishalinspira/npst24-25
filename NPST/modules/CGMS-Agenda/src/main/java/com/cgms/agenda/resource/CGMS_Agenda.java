package com.cgms.agenda.resource;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import CGMS.Agenda.constants.CGMSAgendaPortletKeys;
import compliance.service.service.AgendaIIILocalService;
import compliance.service.service.AgendaIILocalService;
import compliance.service.service.AgendaILocalService;
import compliance.service.service.AgendaIVLocalService;
import compliance.service.service.AgendaIXLocalService;
import compliance.service.service.AgendaVIIILocalService;
import compliance.service.service.AgendaVIILocalService;
import compliance.service.service.AgendaVILocalService;
import compliance.service.service.AgendaVLocalService;
import compliance.service.service.AgendaXIILocalService;
import compliance.service.service.AgendaXILocalService;
import compliance.service.service.AgendaXLocalService;

@Component(property = { 
		"javax.portlet.name=" + CGMSAgendaPortletKeys.CGMSAGENDA,
		"mvc.command.name=" + CGMSAgendaPortletKeys.agenda, 
		}, 
service = MVCResourceCommand.class)

public class CGMS_Agenda implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(CGMS_Agenda.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		cgmsInitialDetails(resourceRequest, resourceResponse);
		table1Details(resourceRequest, resourceResponse);
		schemeWiseDetails(resourceRequest, resourceResponse);
		table2Details(resourceRequest, resourceResponse);
		table3aDetails(resourceRequest, resourceResponse);
		table3bDetails(resourceRequest, resourceResponse);
		table4Details(resourceRequest, resourceResponse);
		table4aDetails(resourceRequest, resourceResponse);
		table4bDetails(resourceRequest, resourceResponse);
		table4cDetails(resourceRequest, resourceResponse);
		table4dDetails(resourceRequest, resourceResponse);
		cgmsLastDetails(resourceRequest, resourceResponse);
		
		return false;
	}
	
	public void cgmsInitialDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside cgmsInitialDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String fileNumber = ParamUtil.getString(resourceRequest, "fileNumber");
		String bNumber = ParamUtil.getString(resourceRequest, "bNumber");
		String itemNumber = ParamUtil.getString(resourceRequest, "itemNumber");
		String date_1 = ParamUtil.getString(resourceRequest, "date_1");
		String date_2 = ParamUtil.getString(resourceRequest, "date_2");
		String date_3 = ParamUtil.getString(resourceRequest, "date_3");
		
		//_log.info("fileNumber" + fileNumber + "bNumber " + bNumber + " itemNumber " + itemNumber + " date_1 " + date_1);
		//_log.info("date_2 " + date_2 + " date_3 " + date_3);
		
		agendaILocalService.addTable1Details(fileNumber, bNumber, 
				itemNumber, date_1, date_2, date_3, themeDisplay.getUserId(), new Date());
		
	}
	
	public void table1Details(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table1Details");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t1List = new ArrayList<String>();
		t1List.addAll(Arrays.asList("CG Nodal Office", "SG Nodal Office", "PoP", "Corporate Nodal Office", 
				"CRA", "Trustee Bank", "NPS Trust", "Other (e-NPS)", "NPS Lite (NLAO/NLCC)", "APY- SP"));
		
		String date_4 = ParamUtil.getString(resourceRequest, "date_4");
		//_log.info("date_4 " + date_4);
		
		for(int i=0; i<10; i++) {
			String opening_balance = ParamUtil.getString(resourceRequest, "opening_balance_"+String.valueOf(i));
			String escalated_to_npst = ParamUtil.getString(resourceRequest, "escalated_to_npst_"+String.valueOf(i));
			String grievances_received = ParamUtil.getString(resourceRequest, "grievances_received_"+String.valueOf(i));
			String grievances_assigned = ParamUtil.getString(resourceRequest, "grievances_assigned_"+String.valueOf(i));
			String grievances_resolved = ParamUtil.getString(resourceRequest, "grievances_resolved_"+String.valueOf(i));
			String outstanding_grievances = ParamUtil.getString(resourceRequest, "outstanding_grievances_"+String.valueOf(i));
			
			//_log.info("opening_balance " + opening_balance + " escalated_to_npst " + escalated_to_npst);
			//_log.info("grievances_received " + grievances_received + " grievances_assigned " + grievances_assigned);
			//_log.info("grievances_resolved " + grievances_resolved + " outstanding_grievances " + outstanding_grievances);
			
			agendaIILocalService.addTable2Details(t1List.get(i), opening_balance, escalated_to_npst, grievances_received, grievances_assigned, grievances_resolved, outstanding_grievances, themeDisplay.getUserId(), new Date());
			
		}

	}
	
	public void schemeWiseDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside schemeWiseDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t2List = new ArrayList<String>();
		t2List.addAll(Arrays.asList("Opening balance", "Received", "Resolved", "Outstanding"));
		
		for(int i=0; i<4; i++) {
			String nps = ParamUtil.getString(resourceRequest, "nps_"+String.valueOf(i));
			String npsLite = ParamUtil.getString(resourceRequest, "npsLite_"+String.valueOf(i));
			//String total = ParamUtil.getString(resourceRequest, "total_"+String.valueOf(i));
			
			//_log.info("nps " + nps + " npsLite " + npsLite + " total " + total);
			//_log.info("nps " + nps + " npsLite " + npsLite);
			
			agendaIIILocalService.addTable3Details(t2List.get(i), nps, npsLite, themeDisplay.getUserId(), new Date());
			
		}
		
	}
	
	public void table2Details(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table2Details");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t3List = new ArrayList<String>();
		t3List.addAll(Arrays.asList("Opening balance", "Received", "Resolved", "Outstanding"));
		
		for(int i=0; i<4; i++) {
			String overall_summary_q1 = ParamUtil.getString(resourceRequest, "overall_summary_q1_"+String.valueOf(i));
			String overall_summary_q2 = ParamUtil.getString(resourceRequest, "overall_summary_q2_"+String.valueOf(i));
			String overall_summary_q3 = ParamUtil.getString(resourceRequest, "overall_summary_q3_"+String.valueOf(i));
			String overall_summary_q4 = ParamUtil.getString(resourceRequest, "overall_summary_q4_"+String.valueOf(i));
			String overall_summary_q1One = ParamUtil.getString(resourceRequest, "overall_summary_q1One_"+String.valueOf(i));
			String overall_summary_q2One = ParamUtil.getString(resourceRequest, "overall_summary_q2One_"+String.valueOf(i));
			String percentage_change = ParamUtil.getString(resourceRequest, "percentage_change_"+String.valueOf(i));
			
			//_log.info("overall_summary_q1 " + overall_summary_q1 + " overall_summary_q2 " + overall_summary_q2);
			//_log.info("overall_summary_q3 " + overall_summary_q3 + " overall_summary_q4 " + overall_summary_q4);
			//_log.info("overall_summary_q1One " + overall_summary_q1One + " overall_summary_q2One " + overall_summary_q2One);
			//_log.info("percentage_change " + percentage_change);
			
			agendaIVLocalService.addTable4Details(t3List.get(i), overall_summary_q1, 
					overall_summary_q2, overall_summary_q3, overall_summary_q4, 
					overall_summary_q1One, overall_summary_q2One, themeDisplay.getUserId(), new Date());
			
		}
		
	}
	
	public void table3aDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table3aDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t4List = new ArrayList<String>();
		t4List.addAll(Arrays.asList("Against NPS Trust", "Contribution amount not reflected in account", 
				"Delays in Issuance of PRAN Cards", "Delays in Uploading of Contribution Amounts",
				"Email/SMS alerts not received", "General Query",
				"Grievance of BANK against PAO/POP SP / CRA", "Incorrect contribution amount reflected",
				"Incorrect Processing of Subscriber Details", "I-PIN, T-PIN Related - Others",
				"Not Processed/Delay in Processing Subscriber Changes Request", "PRAN Card related",
				"SOT Related", "Tier II related", "Withdrawal Related", 
				"Death withdrawal not initiated / not authorised / amount not received",
				"Exit not initiated / not authorised / amount not received",
				"Partial withdrawal not initiated / not authorised / amount not received",
				"Pre-mature withdrawal not initiated / not authorised / amount not received",
				"Other grievances", "Change Request- Others", "Change Request- Processed Incorrectly", "Contribution-others"));
		
		for(int i=0; i<23; i++) {
			String cause_wise_q1 = ParamUtil.getString(resourceRequest, "cause_wise_q1_"+String.valueOf(i));
			String cause_wise_q2 = ParamUtil.getString(resourceRequest, "cause_wise_q2_"+String.valueOf(i));
			String cause_wise_q3 = ParamUtil.getString(resourceRequest, "cause_wise_q3_"+String.valueOf(i));
			String cause_wise_q4 = ParamUtil.getString(resourceRequest, "cause_wise_q4_"+String.valueOf(i));
			
			//_log.info("cause_wise_q1 " + cause_wise_q1 + " cause_wise_q2 " + cause_wise_q2);
			//_log.info("cause_wise_q3 " + cause_wise_q3 + " cause_wise_q4 " + cause_wise_q4);
			
			agendaVLocalService.addTable5Details(t4List.get(i), cause_wise_q1, cause_wise_q2, 
					cause_wise_q3, cause_wise_q4, themeDisplay.getUserId(), new Date());
			
		}
		
	}
	
	public void table3bDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table3bDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t5List = new ArrayList<String>();
		t5List.addAll(Arrays.asList("Against NPS Trust", "Contribution amount not reflected in account", 
				"General Query", "Incorrect contribution amount reflected",
				"Incorrect Processing of Subscriber Details", "Other Grievances",
				"PRAN Card Related", "Processing of change request by Nodal Office",
				"Service not received", "SOT Related", "Withdrawal Related"));
		
		for(int i=0; i<11; i++) {
			String grievances_3b_q1 = ParamUtil.getString(resourceRequest, "grievances_3b_q1_"+String.valueOf(i));
			String grievances_3b_q2 = ParamUtil.getString(resourceRequest, "grievances_3b_q2_"+String.valueOf(i));
			String grievances_3b_q3 = ParamUtil.getString(resourceRequest, "grievances_3b_q3_"+String.valueOf(i));
			String grievances_3b_q4 = ParamUtil.getString(resourceRequest, "grievances_3b_q4_"+String.valueOf(i));
			String grievances_3b_q1One = ParamUtil.getString(resourceRequest, "grievances_3b_q1One_"+String.valueOf(i));
			String grievances_3b_q2One = ParamUtil.getString(resourceRequest, "grievances_3b_q2One_"+String.valueOf(i));
			
			//_log.info("grievances_3b_q1 " + grievances_3b_q1 + " grievances_3b_q2 " + grievances_3b_q2);
			//_log.info("grievances_3b_q3 " + grievances_3b_q3 + " grievances_3b_q4 " + grievances_3b_q4);
			//_log.info("grievances_3b_q1One " + grievances_3b_q1One + " grievances_3b_q2One " + grievances_3b_q2One);
			
			agendaVILocalService.addTable6Details(t5List.get(i), grievances_3b_q1, grievances_3b_q2, 
					grievances_3b_q3, grievances_3b_q4, grievances_3b_q1One, 
					grievances_3b_q2One, themeDisplay.getUserId(), new Date());
			
		}

	}
	
	public void table4Details(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table4Details");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t6List = new ArrayList<String>();
		t6List.addAll(Arrays.asList("CG Nodal Office", "SG Nodal Office", "PoP", "Corporate Nodal Office", 
				"CRA", "Trustee Bank", "NPS Trust", "Other (e-NPS)", "NPS Lite (NLAO/NLCC)", "APY- SP"));
		
		for(int i=0; i<10; i++) {
			String entity_wise_outstanding_q1 = ParamUtil.getString(resourceRequest, "entity_wise_outstanding_q1_"+String.valueOf(i));
			String entity_wise_outstanding_q2 = ParamUtil.getString(resourceRequest, "entity_wise_outstanding_q2_"+String.valueOf(i));
			String entity_wise_outstanding_q3 = ParamUtil.getString(resourceRequest, "entity_wise_outstanding_q3_"+String.valueOf(i));
			String entity_wise_outstanding_q4 = ParamUtil.getString(resourceRequest, "entity_wise_outstanding_q4_"+String.valueOf(i));
			String entity_wise_outstanding_q1One = ParamUtil.getString(resourceRequest, "entity_wise_outstanding_q1One_"+String.valueOf(i));
			String entity_wise_outstanding_q2One = ParamUtil.getString(resourceRequest, "entity_wise_outstanding_q2One_"+String.valueOf(i));
			
			//_log.info("entity_wise_outstanding_q1 " + entity_wise_outstanding_q1 + " entity_wise_outstanding_q2 " + entity_wise_outstanding_q2);
			//_log.info("entity_wise_outstanding_q3 " + entity_wise_outstanding_q3 + " entity_wise_outstanding_q4 " + entity_wise_outstanding_q4);
			//_log.info("entity_wise_outstanding_q1One " + entity_wise_outstanding_q1One + " entity_wise_outstanding_q2One " + entity_wise_outstanding_q2One);
			
			agendaVIILocalService.addTable7Details(t6List.get(i), entity_wise_outstanding_q1, entity_wise_outstanding_q2, 
					entity_wise_outstanding_q3, entity_wise_outstanding_q4, entity_wise_outstanding_q1One, 
					entity_wise_outstanding_q2One, themeDisplay.getUserId(), new Date());
			
			
		}

	}
	
	public void table4aDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table4aDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t7List = new ArrayList<String>();
		t7List.addAll(Arrays.asList("CRA", "CG Nodal Office", "SG Nodal Office", "POP-SP", "Corporate", 
				 "Trustee Bank", "NPS Trust", "Other (e-NPS)"));
		
		for(int i=0; i<8; i++) {
			String t4a_referrals = ParamUtil.getString(resourceRequest, "t4a_referrals_"+String.valueOf(i));
			String t4a_0_7 = ParamUtil.getString(resourceRequest, "t4a_0_7_"+String.valueOf(i));
			String t4a_8_15 = ParamUtil.getString(resourceRequest, "t4a_8_15_"+String.valueOf(i));
			String t4a_16_31 = ParamUtil.getString(resourceRequest, "t4a_16_31_"+String.valueOf(i));
			String t4a_32_90 = ParamUtil.getString(resourceRequest, "t4a_32_90_"+String.valueOf(i));
			String t4a_91_180 = ParamUtil.getString(resourceRequest, "t4a_91_180_"+String.valueOf(i));
			String t4a_181_365 = ParamUtil.getString(resourceRequest, "t4a_185_365_"+String.valueOf(i));
			String t4a_366_and_above = ParamUtil.getString(resourceRequest, "t4a_366_and_above_"+String.valueOf(i));
			
			//_log.info("t4a_referrals " + t4a_referrals + " t4a_0_7 " + t4a_0_7);
			//_log.info("t4a_8_15 " + t4a_8_15 + " t4a_16_31 " + t4a_16_31);
			//_log.info("t4a_32_90 " + t4a_32_90 + " t4a_91_180 " + t4a_91_180);
			//_log.info("t4a_185_365 " + t4a_181_365 + " t4a_366_and_above " + t4a_366_and_above);
			
			agendaVIIILocalService.addTable8Details(t7List.get(i), t4a_referrals, t4a_0_7, t4a_8_15, 
					t4a_16_31, t4a_32_90, t4a_91_180, t4a_181_365, 
					t4a_366_and_above, themeDisplay.getUserId(), new Date());
			
		}

	}
	
	public void table4bDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table4bDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t8List = new ArrayList<String>();
		t8List.addAll(Arrays.asList("Against NPS Trust", "Contribution amount not reflected in account", 
				"Delays in Issuance of PRAN Cards", "Delays in Uploading of Contribution Amounts",
				"Email/SMS alerts not received", "General Query",
				"Grievance of BANK against PAO/POP SP / CRA", "Incorrect contribution amount reflected",
				"Incorrect Processing of Subscriber Details", "I-PIN, T-PIN Related - Others",
				"Not Processed/Delay in Processing Subscriber Changes Request", "PRAN Related-Error in Details",
				"PRAN Card Related-PRAN card not received", "PRAN Related-Account Related(Tier I & Tier II)",
				"PRAN Card Related - Other","SOT Related", "Tier II related", "Withdrawal Related", 
				"Death withdrawal not initiated / not authorised / amount not received",
				"Exit not initiated / not authorised / amount not received",
				"Partial withdrawal not initiated / not authorised / amount not received",
				"Pre-mature withdrawal not initiated / not authorised / amount not received",
				"Other grievances", "Change Request- Others", "Change Request- Processed Incorrectly", "Contribution-others"));
		
		for(int i=0; i<26; i++) {
			String t4b_referrals = ParamUtil.getString(resourceRequest, "t4b_referrals_"+String.valueOf(i));
			String t4b_0_7 = ParamUtil.getString(resourceRequest, "t4b_0_7_"+String.valueOf(i));
			String t4b_8_15 = ParamUtil.getString(resourceRequest, "t4b_8_15_"+String.valueOf(i));
			String t4b_16_31 = ParamUtil.getString(resourceRequest, "t4b_16_31_"+String.valueOf(i));
			String t4b_32_90 = ParamUtil.getString(resourceRequest, "t4b_32_90_"+String.valueOf(i));
			String t4b_91_180 = ParamUtil.getString(resourceRequest, "t4b_91_180_"+String.valueOf(i));
			String t4b_181_365 = ParamUtil.getString(resourceRequest, "t4b_185_365_"+String.valueOf(i));
			String t4b_366_and_above = ParamUtil.getString(resourceRequest, "t4b_366_and_above_"+String.valueOf(i));
			
			//_log.info("t4b_referrals " + t4b_referrals + " t4b_0_7 " + t4b_0_7);
			//_log.info("t4b_8_15 " + t4b_8_15 + " t4b_16_31 " + t4b_16_31);
			//_log.info("t4b_32_90 " + t4b_32_90 + " t4b_91_180 " + t4b_91_180);
			//_log.info("t4b_181_365 " + t4b_181_365 + " t4b_366_and_above " + t4b_366_and_above);
			
			agendaIXLocalService.addTable9Details(t8List.get(i), t4b_referrals, t4b_0_7, t4b_8_15, 
					t4b_16_31, t4b_32_90, t4b_91_180, t4b_181_365, 
					t4b_366_and_above, themeDisplay.getUserId(), new Date());
			
		}

	}
	
	public void table4cDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table4cDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t9List = new ArrayList<String>();
		t9List.addAll(Arrays.asList("CRA", "NLOO", "NLAO", "APY-SP", "NLCC", 
				 "Trustee Bank", "NPS Trust", "Others"));
		
		for(int i=0; i<8; i++) {
			String t4c_referrals = ParamUtil.getString(resourceRequest, "t4c_referrals_"+String.valueOf(i));
			String t4c_0_7 = ParamUtil.getString(resourceRequest, "t4c_0_7_"+String.valueOf(i));
			String t4c_8_15 = ParamUtil.getString(resourceRequest, "t4c_8_15_"+String.valueOf(i));
			String t4c_16_31 = ParamUtil.getString(resourceRequest, "t4c_16_31_"+String.valueOf(i));
			String t4c_32_90 = ParamUtil.getString(resourceRequest, "t4c_32_90_"+String.valueOf(i));
			String t4c_91_180 = ParamUtil.getString(resourceRequest, "t4c_91_180_"+String.valueOf(i));
			String t4c_181_365 = ParamUtil.getString(resourceRequest, "t4c_185_365_"+String.valueOf(i));
			String t4c_366_and_above = ParamUtil.getString(resourceRequest, "t4c_366_and_above_"+String.valueOf(i));
			
			//_log.info("t4c_referrals " + t4c_referrals + " t4c_0_7 " + t4c_0_7);
			//_log.info("t4c_8_15 " + t4c_8_15 + " t4c_16_31 " + t4c_16_31);
			//_log.info("t4c_32_90 " + t4c_32_90 + " t4c_91_180 " + t4c_91_180);
			//_log.info("t4c_181_365 " + t4c_181_365 + " t4c_366_and_above " + t4c_366_and_above);
			
			agendaXLocalService.addTable8Details(t9List.get(i), t4c_referrals, t4c_0_7, t4c_8_15, 
					t4c_16_31, t4c_32_90, t4c_91_180, t4c_181_365, 
					t4c_366_and_above, themeDisplay.getUserId(), new Date());
			
		}

	}
	
	public void table4dDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside table4dDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<String> t10List = new ArrayList<String>();
		t10List.addAll(Arrays.asList("Against NPS Trust", "Contribution amount not reflected in account", 
				"General Query", "Incorrect contribution amount reflected",
				"Incorrect Processing of Subscriber Details", "Other Grievances",
				"PRAN Card Related", "Processing of change request by Nodal Office",
				"Service not received", "SOT Related", "Withdrawal Related"));
		
		for(int i=0; i<11; i++) {
			String t4d_referrals = ParamUtil.getString(resourceRequest, "t4d_referrals_"+String.valueOf(i));
			String t4d_0_7 = ParamUtil.getString(resourceRequest, "t4d_0_7_"+String.valueOf(i));
			String t4d_8_15 = ParamUtil.getString(resourceRequest, "t4d_8_15_"+String.valueOf(i));
			String t4d_16_31 = ParamUtil.getString(resourceRequest, "t4d_16_31_"+String.valueOf(i));
			String t4d_32_90 = ParamUtil.getString(resourceRequest, "t4d_32_90_"+String.valueOf(i));
			String t4d_91_180 = ParamUtil.getString(resourceRequest, "t4d_91_180_"+String.valueOf(i));
			String t4d_181_365 = ParamUtil.getString(resourceRequest, "t4d_185_365_"+String.valueOf(i));
			String t4d_366_and_above = ParamUtil.getString(resourceRequest, "t4d_366_and_above_"+String.valueOf(i));
			
			//_log.info("t4d_referrals " + t4d_referrals + " t4d_0_7 " + t4d_0_7);
			//_log.info("t4d_8_15 " + t4d_8_15 + " t4d_16_31 " + t4d_16_31);
			//_log.info("t4d_32_90 " + t4d_32_90 + " t4d_91_180 " + t4d_91_180);
			//_log.info("t4d_181_365 " + t4d_181_365 + " t4d_366_and_above " + t4d_366_and_above);
			
			agendaXILocalService.addTable11Details(t10List.get(i), t4d_referrals, t4d_0_7, t4d_8_15, 
					t4d_16_31, t4d_32_90, t4d_91_180, t4d_181_365, 
					t4d_366_and_above, themeDisplay.getUserId(), new Date());
			
		}

	}
	
	public void cgmsLastDetails(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		_log.info("Inside cgmsLastDetails");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String date_5 = ParamUtil.getString(resourceRequest, "date_5");
		String numberOfGriev_1 = ParamUtil.getString(resourceRequest, "numberOfGriev_1");
		String numberOfGriev_2 = ParamUtil.getString(resourceRequest, "numberOfGriev_2");
		String numberOfGriev_3 = ParamUtil.getString(resourceRequest, "numberOfGriev_3");
		String numberOfGriev_4 = ParamUtil.getString(resourceRequest, "numberOfGriev_4");
		String numberOfGriev_5 = ParamUtil.getString(resourceRequest, "numberOfGriev_5");
		String numberOfGriev_6 = ParamUtil.getString(resourceRequest, "numberOfGriev_6");
		
		//_log.info("date_5 " + date_5 + " numberOfGriev_1 " + numberOfGriev_1 + " numberOfGriev_2 " + numberOfGriev_2);
		//_log.info("numberOfGriev_3 " + numberOfGriev_3 + " numberOfGriev_4 " + numberOfGriev_4 + " numberOfGriev_5 " + numberOfGriev_5);
		//_log.info("numberOfGriev_6 " + numberOfGriev_6);
		
		String grievances = ParamUtil.getString(resourceRequest, "grievances");
		String grievances_2 = ParamUtil.getString(resourceRequest, "grievances_2");
		String grievances_3 = ParamUtil.getString(resourceRequest, "grievances_3");
		String grievances_4 = ParamUtil.getString(resourceRequest, "grievances_4");
		String grievances_5 = ParamUtil.getString(resourceRequest, "grievances_5");
		
		//_log.info(grievances + " " + grievances_2 + " " + grievances_3 + " " + grievances_4 + " " + grievances_5);
		
		String grievances_6 = ParamUtil.getString(resourceRequest, "grievances_6");
		String grievances_7 = ParamUtil.getString(resourceRequest, "grievances_7");
		String grievances_8 = ParamUtil.getString(resourceRequest, "grievances_8");
		String grievances_9 = ParamUtil.getString(resourceRequest, "grievances_9");
		String grievances_10 = ParamUtil.getString(resourceRequest, "grievances_10");
		
		//_log.info(grievances_6 + " " + grievances_7 + " " + grievances_8 + " " + grievances_9 + " " + grievances_10);
		
		String grievances_11 = ParamUtil.getString(resourceRequest, "grievances_11");
		String grievances_12 = ParamUtil.getString(resourceRequest, "grievances_12");
		String grievances_13 = ParamUtil.getString(resourceRequest, "grievances_13");
		String grievances_14 = ParamUtil.getString(resourceRequest, "grievances_14");
		String grievances_15 = ParamUtil.getString(resourceRequest, "grievances_15");
		
		//_log.info(grievances_11 + " " + grievances_12 + " " + grievances_13 + " " + grievances_14 + " " + grievances_15);
		
		String grievances_16 = ParamUtil.getString(resourceRequest, "grievances_16");
		String grievances_17 = ParamUtil.getString(resourceRequest, "grievances_17");
		String grievances_18 = ParamUtil.getString(resourceRequest, "grievances_18");
		String grievances_19 = ParamUtil.getString(resourceRequest, "grievances_19");
		String grievances_20 = ParamUtil.getString(resourceRequest, "grievances_20");
		
		//_log.info(grievances_16 + " " + grievances_17 + " " + grievances_18 + " " + grievances_19 + " " + grievances_20);
		
		String grievances_21 = ParamUtil.getString(resourceRequest, "grievances_21");
		String grievances_22 = ParamUtil.getString(resourceRequest, "grievances_22");
		String grievances_23 = ParamUtil.getString(resourceRequest, "grievances_23");
		String grievances_24 = ParamUtil.getString(resourceRequest, "grievances_24");
		String grievances_25 = ParamUtil.getString(resourceRequest, "grievances_25");
		
		//_log.info(grievances_21 + " " + grievances_22 + " " + grievances_23 + " " + grievances_24 + " " + grievances_25);
		
		String grievances_26 = ParamUtil.getString(resourceRequest, "grievances_26");
		String grievances_27 = ParamUtil.getString(resourceRequest, "grievances_27");
		String grievances_28 = ParamUtil.getString(resourceRequest, "grievances_28");
		String grievances_29 = ParamUtil.getString(resourceRequest, "grievances_29");
		String grievances_30 = ParamUtil.getString(resourceRequest, "grievances_30");
		
		//_log.info(grievances_26 + " " + grievances_27 + " " + grievances_28 + " " + grievances_29 + " " + grievances_30);
		
		String activity = ParamUtil.getString(resourceRequest, "activity");
		String grievances_31 = ParamUtil.getString(resourceRequest, "grievances_31");
		String grievances_32 = ParamUtil.getString(resourceRequest, "grievances_32");
		String pfrdaPolicy = ParamUtil.getString(resourceRequest, "pfrdaPolicy");
		String date_6 = ParamUtil.getString(resourceRequest, "date_6");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date_7 = ParamUtil.getDate(resourceRequest, "date_7", dateFormat);
		
		//_log.info(activity + " " + grievances_31 + " " + grievances_32 + " " + pfrdaPolicy + " " + date_6);
		//_log.info(date_7);
		
		agendaXIILocalService.addTable12Details(date_5, numberOfGriev_1, numberOfGriev_2, numberOfGriev_3, 
				numberOfGriev_4, numberOfGriev_5, numberOfGriev_6, grievances, 
				grievances_2, grievances_3, grievances_4, grievances_5, 
				grievances_6, grievances_7, grievances_8, grievances_9, 
				grievances_10, grievances_11, grievances_12, grievances_13, 
				grievances_14, grievances_15, grievances_16, grievances_17, 
				grievances_18, grievances_19, grievances_20, grievances_21, 
				grievances_22, grievances_23, grievances_24, grievances_25, 
				grievances_26, grievances_27, grievances_28, grievances_29, 
				grievances_30, activity, grievances_31, grievances_32, 
				pfrdaPolicy, date_6, date_7, themeDisplay.getUserId(), new Date());
		
	}
	
	@Reference
	AgendaILocalService agendaILocalService;
	
	@Reference
	AgendaIILocalService agendaIILocalService;
	
	@Reference
	AgendaIIILocalService agendaIIILocalService;
	
	@Reference
	AgendaIVLocalService agendaIVLocalService;
	
	@Reference
	AgendaVLocalService agendaVLocalService;
	
	@Reference
	AgendaVILocalService agendaVILocalService;
	
	@Reference
	AgendaVIILocalService agendaVIILocalService;
	
	@Reference
	AgendaVIIILocalService agendaVIIILocalService;
	
	@Reference
	AgendaIXLocalService agendaIXLocalService;
	
	@Reference
	AgendaXLocalService agendaXLocalService;
	
	@Reference
	AgendaXILocalService agendaXILocalService;
	
	@Reference
	AgendaXIILocalService agendaXIILocalService;

}
