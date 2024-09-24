package com.nps.manpower.constants;

import java.util.HashMap;
import java.util.Map;

public class DropdownValues {
	public static Map<Integer, String> COMMITTEE_MEMBERSHIP_TYPE_MAP;
	public static Map<String, String> MEMBERSHIP_TYPE_MAP;
	public static Map<String, String> DIRECTOR_TYPE_MAP;
	public static Map<String, String> DEPENDENCY_MAP;
	public static Map<Integer, String> STATUS_MAP;
	public static Map<String, String> DESIGNATION_MAP;
	public static Map<String, String> FILTER_DESIGNATION_MAP;
	public static Map<String, String> COMMITTEE_DESIGNATION_MAP;
	public static Map<String, String> FILTER_EMPLOYEE_MAP;
	
	static {
		COMMITTEE_MEMBERSHIP_TYPE_MAP=new HashMap<Integer, String>();
		COMMITTEE_MEMBERSHIP_TYPE_MAP.put(0,"NaN");
		COMMITTEE_MEMBERSHIP_TYPE_MAP.put(1, "Investment Committee Composition");
		COMMITTEE_MEMBERSHIP_TYPE_MAP.put(2, "Risk Management Committee Composition");
		COMMITTEE_MEMBERSHIP_TYPE_MAP.put(3, "Both");
		
		//Membership Type values
		MEMBERSHIP_TYPE_MAP=new HashMap<String, String>();
		MEMBERSHIP_TYPE_MAP.put("Chairperson", "Chairperson");
		MEMBERSHIP_TYPE_MAP.put("Member", "Member");
		
		//Director type values
		DIRECTOR_TYPE_MAP=new HashMap<String, String>();
		DIRECTOR_TYPE_MAP.put("Executive", "Executive");
		DIRECTOR_TYPE_MAP.put("Non-Exceutive", "Non-Exceutive");
		
		//Dependency Values
		DEPENDENCY_MAP=new HashMap<String, String>();
		DEPENDENCY_MAP.put("Independent", "Independent");
		DEPENDENCY_MAP.put("Non-independent", "Non-independent");
		
		//Status values
		STATUS_MAP=new HashMap<Integer, String>();
		STATUS_MAP.put(0, "Active");
		//STATUS_MAP.put(1, "InActive");
		
		DESIGNATION_MAP=new HashMap<String, String>();
		DESIGNATION_MAP.put("","Choose Any" );
		DESIGNATION_MAP.put("Principal Officer","Principal Officer" );
		DESIGNATION_MAP.put("Chief Executive Officer (CEO)","Chief Executive Officer (CEO)" );
		DESIGNATION_MAP.put("Chief Investment Officer (CIO)","Chief Investment Officer (CIO)" );
		DESIGNATION_MAP.put("Chief Risk Officer (CRO)","Chief Risk Officer (CRO)" );
		DESIGNATION_MAP.put("Chief Information and Security Officer (CISO)","Chief Information and Security Officer (CISO)" );
		//DESIGNATION_MAP.put("Operations Manager","Operations Manager" );
		DESIGNATION_MAP.put("Head of Operations","Head of Operations" );
		//DESIGNATION_MAP.put("Fund manager - Equity","Fund manager - Equity" );
		//DESIGNATION_MAP.put("Fund manager - Debt","Fund manager - Debt" );
		DESIGNATION_MAP.put("Compliance Officer", "Compliance Officer");
		
		
		COMMITTEE_DESIGNATION_MAP=new HashMap<String, String>();
		COMMITTEE_DESIGNATION_MAP.put("Chief Executive Officer","Chief Executive Officer" );
		COMMITTEE_DESIGNATION_MAP.put("Chief Investment Officer","Chief Investment Officer" );
		COMMITTEE_DESIGNATION_MAP.put("Independent Director","Independent Director" );
		COMMITTEE_DESIGNATION_MAP.put("Fund Manager","Fund Manager" );
		COMMITTEE_DESIGNATION_MAP.put("Dependent Director","Dependent Director" );
		COMMITTEE_DESIGNATION_MAP.put("Any Other","Any Other" );
		
		FILTER_EMPLOYEE_MAP=new HashMap<String, String>();
		FILTER_EMPLOYEE_MAP.put(ManpowerStatusConstant.ALL_EMPLOYEE, "All");
		FILTER_EMPLOYEE_MAP.put(ManpowerStatusConstant.ACTIVE_EMPLOYEE, "Active");
		FILTER_EMPLOYEE_MAP.put(ManpowerStatusConstant.NON_ACTIVE_EMPLOYEE, "InActive");
		//FILTER_EMPLOYEE_MAP.put(ManpowerStatusConstant.DELETED_EMPLOYEE, "Deleted");
		
		FILTER_DESIGNATION_MAP=new HashMap<String, String>();
		FILTER_DESIGNATION_MAP.put("Principal Officer","Principal Officer" );
		FILTER_DESIGNATION_MAP.put("Chief Executive Officer (CEO)","Chief Executive Officer (CEO)" );
		FILTER_DESIGNATION_MAP.put("Chief Investment Officer (CIO)","Chief Investment Officer (CIO)" );
		FILTER_DESIGNATION_MAP.put("Chief Risk Officer (CRO)","Chief Risk Officer (CRO)" );
		FILTER_DESIGNATION_MAP.put("Chief Information and Security Officer (CISO)","Chief Information and Security Officer (CISO)" );
		//FILTER_DESIGNATION_MAP.put("Operations Manager","Operations Manager" );
		FILTER_DESIGNATION_MAP.put("Head of Operations","Head of Operations" );
		FILTER_DESIGNATION_MAP.put("Fund manager - Equity","Fund manager - Equity" );
		FILTER_DESIGNATION_MAP.put("Fund manager - Debt","Fund manager - Debt" );
		FILTER_DESIGNATION_MAP.put("Compliance Officer", "Compliance Officer");
	
	}

	
	
}
