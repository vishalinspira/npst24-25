package com.nps.cust.fee.letter.constants;

import java.util.HashMap;
import java.util.Map;

public class PFMConstants {

	public static final String DSP_ADDRESS="DSP PENSION FUND MANAGERS PRIVATE LIMITED";
	public static final String MAX_ADDRESS="MAX LIFE PENSION FUND MANAGEMENT LIMITED";
	//public static final String ABS_ADDRESS="ADITYA BIRLA SUN LIFE PENSION MANAGEMENT LIMITED";
	
	public static final String ABS_ADDRESS="ADITYA BIRLA SUN LIFE PENSION FUND MANAGEMENT LIMITED";
	//public static final String TATA_ADDRESS="TATA PENSION MANAGEMENT LIMITED";
	public static final String TATA_ADDRESS="TATA PENSION FUND MANAGEMENT PRIVATE LIMITED";
	
	public static final String APF_ADDRESS="AXIS PENSION FUND MANAGEMENT LIMITED";
	public static final String KOTAK_ADDRESS="KOTAK MAHINDRA PENSION FUND LIMITED";
	public static final String ICICI_ADDRESS="ICICI PRUDENTIAL PENSION FUNDS MANAGEMENT COMPANY LIMITED";
	public static final String HDFC_ADDRESS="HDFC PENSION FUND MANAGEMENT LIMITED";
	public static final String UTI_ADDRESS="UTI PENSION FUND LIMITED";
	public static final String LIC_ADDRESS="LIC PENSION FUND LIMITED";
	public static final String SBI_ADDRESS="SBI PENSION FUNDS PRIVATE LIMITED";
	
	public static final String DSP_NAME="Shri. Rahul Bhagat";
	public static final String MAX_NAME="Shri. Ranbheer Dhariwal";
	public static final String ABS_NAME="Shri. Vikash Seth";
	public static final String TATA_NAME=" Shri. Kurian Jose";
	public static final String APF_NAME="Shri Sumit Shukla";
	public static final String KOTAK_NAME="Shri Subhasis Ghosh";
	public static final String ICICI_NAME="Shri. Sumit Mohindra";
	public static final String HDFC_NAME="Shri Sriram Iyer";
	public static final String UTI_NAME="Shri Balram Bhagat";
	public static final String LIC_NAME="Smt. Anju Bala Purushottam";
	public static final String SBI_NAME="Shri. Pranay Ranjan Dwivedi";
	
public static Map<String, String> SIGNETURIES_MAP;

	
	static {
		SIGNETURIES_MAP=new HashMap<String, String>();
		SIGNETURIES_MAP.put("Suparna Tandon","Chief Executive Officer");
		SIGNETURIES_MAP.put("Praveen Singh", "General Manager");
		SIGNETURIES_MAP.put("Harit Setia","Deputy General Manager");
		SIGNETURIES_MAP.put("Anand Kumar", "Deputy General Manager");
	}	
}
