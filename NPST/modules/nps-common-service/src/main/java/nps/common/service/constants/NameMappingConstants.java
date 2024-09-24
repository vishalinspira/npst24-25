package nps.common.service.constants;

import java.util.HashMap;
import java.util.Map;

public class NameMappingConstants {
	
	public static Map<String, String> CRA_NAME_MAP=new HashMap<String, String>();
	public static Map<String, String> CRA_OLD_NAME_MAP=new HashMap<String, String>();
	public static String NCRA_OLD="NCRA";
	public static String NCRA_OLD1="NSDL";
	public static String NCRA_NEW="PCRA";
	public static String CAMS_OLD="CAMS";
	public static String CAMS_NEW="CCRA";
	public static String KCRA_OLD="Kfintech";
	public static String KCRA_NEW="KCRA";
	static {
		CRA_NAME_MAP.put("", "");
		CRA_NAME_MAP.put("NCRA", "PCRA");
		CRA_NAME_MAP.put("NSDL", "PCRA");
		//CRA_NAME_MAP.put("NSDL", "PCRA");
		CRA_NAME_MAP.put("KCRA", "KCRA");
		CRA_NAME_MAP.put("Kfintech", "KCRA");
		CRA_NAME_MAP.put("CAMS", "CCRA");
		
		CRA_OLD_NAME_MAP.put("PCRA", "NCRA");
		CRA_OLD_NAME_MAP.put("KCRA", "KCRA");
		CRA_OLD_NAME_MAP.put("", "");
		CRA_OLD_NAME_MAP.put("CCRA", "CAMS");
	}
}
