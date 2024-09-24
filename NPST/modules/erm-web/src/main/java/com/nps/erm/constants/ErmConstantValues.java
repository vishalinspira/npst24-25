package com.nps.erm.constants;

import java.util.HashMap;
import java.util.Map;

public class ErmConstantValues {
	public static final String OLD_BATCH_TYPE = "Old";
	public static final String NEW_BATCH_TYPE = "New";
	
	public static Map<String, String> TRANSACTION_MODE_MAP;
	public static Map<String, String> RECTIFICATION_REQUEST_MODE_MAP;
	public static Map<String, String> TIER_TYPE_MAP;
	public static Map<String, String> TRANSACTION_TYPE_MAP;
	public static Map<String, String> RECTIFICATION_TYPE_MAP;
	public static Map<String, String> TYPE_OF_RECTIFICATION_MAP;
	public static Map<String, String> PROCESS_MAP;
	public static Map<String, String> RECOMMENDATION_MAP;
	public static Map<String, String> STIPULATED_TIME_MAP;
	public static Map<String, String> DOCUMENT_SUBMISSION_STIPULATED_TIME_MAP;

	
	static {
		TRANSACTION_MODE_MAP=new HashMap<String, String>();
		TRANSACTION_MODE_MAP.put("e-NPS portal","e-NPS portal");
		TRANSACTION_MODE_MAP.put("D-Remit", "D-Remit");
		
		RECTIFICATION_REQUEST_MODE_MAP=new HashMap<String, String>();
		RECTIFICATION_REQUEST_MODE_MAP.put("Email", "Email");
		RECTIFICATION_REQUEST_MODE_MAP.put("Other", "Other");
		RECTIFICATION_REQUEST_MODE_MAP.put("Token No", "Token No");
		
		
		TIER_TYPE_MAP=new HashMap<String, String>();
		TIER_TYPE_MAP.put("Tier1", "Tier1");
		TIER_TYPE_MAP.put("Tier2", "Tier2");
		
		
		TRANSACTION_TYPE_MAP=new HashMap<String, String>();
		TRANSACTION_TYPE_MAP.put("Refund", "Refund");
		TRANSACTION_TYPE_MAP.put("Transfer", "Transfer");
		
		RECTIFICATION_TYPE_MAP=new HashMap<String, String>();
		RECTIFICATION_TYPE_MAP.put("Type1", "Type1");
		RECTIFICATION_TYPE_MAP.put("Type2", "Type2");
		
		PROCESS_MAP=new HashMap<String, String>();
		PROCESS_MAP.put("Processed", "Processed");
		PROCESS_MAP.put("Not Processed", "Not Processed");
		
		RECOMMENDATION_MAP=new HashMap<String, String>();
		RECOMMENDATION_MAP.put("Accepted", "Accepted");
		RECOMMENDATION_MAP.put("Rejected", "Rejected");
		
		
		
		STIPULATED_TIME_MAP=new HashMap<String, String>();
		STIPULATED_TIME_MAP.put("Yes", "Yes");
		STIPULATED_TIME_MAP.put("No", "No");
		
		
		
		DOCUMENT_SUBMISSION_STIPULATED_TIME_MAP=new HashMap<String, String>();
		DOCUMENT_SUBMISSION_STIPULATED_TIME_MAP.put("Yes", "Yes");
		DOCUMENT_SUBMISSION_STIPULATED_TIME_MAP.put("No", "No");
		
		TYPE_OF_RECTIFICATION_MAP=new HashMap<String, String>();
		TYPE_OF_RECTIFICATION_MAP.put("Transfer of Excess amount (to any Tier)", "Transfer of Excess amount (to any Tier)");
		TYPE_OF_RECTIFICATION_MAP.put("Transfer of amount to incorrect Tier","Transfer of amount to incorrect Tier" );
		TYPE_OF_RECTIFICATION_MAP.put("Erroneously making multiple transactions", "Erroneously making multiple transactions");
		TYPE_OF_RECTIFICATION_MAP.put("Contribution to erroneous PRAN","Contribution to erroneous PRAN" );
		TYPE_OF_RECTIFICATION_MAP.put("Incorrect Virtual ID entered While Contributing through D-Remit", "Incorrect Virtual ID entered While Contributing through D-Remit");
		
		
	}
}
