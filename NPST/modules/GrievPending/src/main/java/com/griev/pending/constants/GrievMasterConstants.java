package com.griev.pending.constants;

import java.util.ArrayList;
import java.util.List;

public class GrievMasterConstants {

	public static List<String> sectorTypes=new ArrayList<String>();
	public static List<String> sectors=new ArrayList<String>();
	public static List<String> broadCategories=new ArrayList<String>();
	public static List<String> schemes=new ArrayList<String>();
	
	static {
		sectorTypes.add("UOS");
		sectorTypes.add("CG");
		sectorTypes.add("SG");
		sectorTypes.add("Corp");
		sectorTypes.add("CRA");
		sectorTypes.add("eNPS");
		sectorTypes.add("NPS Trust");
		sectorTypes.add("TB");
		sectorTypes.add("NPS Lite");
		sectorTypes.add("APY");
		sectorTypes.add("CAB");
		sectorTypes.add("SAB");

		sectors.add("UOS");
		sectors.add("CG");
		sectors.add("SG");
		sectors.add("Corp");
		sectors.add("CRA");
		sectors.add("eNPS");
		sectors.add("NPS Trust");
		sectors.add("TB");
		sectors.add("NPS Lite");
		sectors.add("APY");
		//sectors.add("CAB");
		//sectors.add("SAB");

		broadCategories.add("AGAINST NPS TRUST");
		broadCategories.add("CONTRIBUTION AMOUNT NOT REFLECTED IN ACCOUNT");
		broadCategories.add("DELAYS IN ISSUANCE OF PRAN CARDS");
		broadCategories.add("DELAYS IN UPLOADING OF CONTRIBUTION AMOUNTS");
		broadCategories.add("EMAIL/SMS ALERTS NOT RECEIVED");
		broadCategories.add("GENERAL QUERY");
		broadCategories.add("GRIEVANCE OF BANK AGAINST PAO/POP SP / CRA");
		broadCategories.add("INCORRECT CONTRIBUTION AMOUNT REFLECTED");
		broadCategories.add("INCORRECT PROCESSING OF SUBSCRIBER DETAILS");
		broadCategories.add("I-PIN, T-PIN RELATED - OTHERS");
		broadCategories.add("NOT PROCESSED/DELAY IN PROCESSING SUBSCRIBER CHANGES REQUEST");
		broadCategories.add("PRAN CARD RELATED");
		broadCategories.add("SOT RELATED");
		broadCategories.add("TIER II RELATED");
		broadCategories.add("WITHDRAWAL RELATED");
		broadCategories.add("DEATH WITHDRAWAL NOT INITIATED / NOT AUTHORISED / AMOUNT NOT RECEIVED");
		broadCategories.add("EXIT NOT INITIATED / NOT AUTHORISED / AMOUNT NOT RECEIVED");
		broadCategories.add("PARTIAL WITHDRAWAL NOT INITIATED / NOT AUTHORISED / AMOUNT NOT RECEIVED");
		broadCategories.add("PRE-MATURE WITHDRAWAL NOT INITIATED / NOT AUTHORISED / AMOUNT NOT RECEIVED");
		broadCategories.add("OTHER GRIEVANCES");
		broadCategories.add("CHANGE REQUEST- OTHERS");
		broadCategories.add("CHANGE REQUEST- PROCESSED INCORRECTLY");
		broadCategories.add("CONTRIBUTION-OTHERS");
		broadCategories.add("PROCESSING OF CHANGE REQUEST BY THE NODAL OFFICE");
		broadCategories.add("SERVICE NOT RECEIVED");
		broadCategories.add("GRIEVANCE OF BANK AGAINST PAO/POP-SP/CRA");
		
		schemes.add("APY");
		schemes.add("NPS");
		schemes.add("NPS Lite & APY");
		schemes.add("NPS Lite");
		
		
	}
}
