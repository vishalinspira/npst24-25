package GrievanceTopFiveData_Monthly.constants;

import java.util.ArrayList;
import java.util.List;

public class GrievTop5MasterConstant {

	public static List<String> sectorTypes=new ArrayList<String>();
	public static List<String> sectors=new ArrayList<String>();
	public static List<String> broadCategories=new ArrayList<String>();
	//public static List<String> schemes=new ArrayList<String>();
	
	static {
		sectorTypes.add("UOS");
		sectorTypes.add("SAB");
		sectorTypes.add("CAB");
		sectorTypes.add("CG");
		sectorTypes.add("SG");
		sectorTypes.add("CORP");
		sectorTypes.add("CRA");
		sectorTypes.add("ENPS");
		sectorTypes.add("NPS TRUST");
		sectorTypes.add("TB");
		sectorTypes.add("NPS LITE");
		sectorTypes.add("APY");
	
		

		sectors.add("UOS");
		sectors.add("CG");
		sectors.add("SG");
		sectors.add("CORP");
		sectors.add("CRA");
		sectors.add("ENPS");
		sectors.add("NPS TRUST");
		sectors.add("TB");
		sectors.add("NPS LITE");
		sectors.add("APY");
		sectors.add("CAB");
		sectors.add("SAB");

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

	}
}
