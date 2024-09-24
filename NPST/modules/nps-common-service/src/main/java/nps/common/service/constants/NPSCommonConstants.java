package nps.common.service.constants;

import java.util.Arrays;
import java.util.List;

public class NPSCommonConstants {
	
	public static final List<String> allReportTypes = Arrays.asList("Daily", "Weekly", "Monthly", "Quarterly", "Periodically", "Half-Yearly", "Annually");

	public static final List<String> makerReportTypes = Arrays.asList("Daily", "Weekly", "Monthly");
	
	public static final List<String> pfmMakerReportTypes = Arrays.asList("Weekly", "Monthly", "Quarterly","Half-Yearly", "Annually");
	
	public static final List<String> grievanseMakerReportTypes = Arrays.asList("Monthly", "Quarterly");
	
	public static final List<String> custodianMakerReportTypes = Arrays.asList("Monthly");
	
	public static final long PFM_INTERMEDIARY_TYPE = 2;
	public static final long CRA_INTERMEDIARY_TYPE = 1;
	public static final long CUSTODIAN_INTERMEDIARY_TYPE = 3;
}

