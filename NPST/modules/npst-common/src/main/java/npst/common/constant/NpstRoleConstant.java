package npst.common.constant;


public class NpstRoleConstant {
	
	public static final String NPST_USER = "NPST User";
	public static final String NPST = "NPST";
	public static final String CHECKER = "Checker";
	public static final String SUPERVISOR = "Supervisor";
	public static final String MAKER = "Maker";
	public static final String NPST_AM = "NPSTAM";
	public static final String NPST_DGM = "NPSTDGM";
	public static final String NPST_GM = "NPSTGM";
	public static final String NPST_CEO = "NPSTCEO";
	public static final String NPST_MGR = "NPSTManager";
	public static final String NPSTMGR_GRIVANCES="NPSTMGR-PFM";
	public static final String NPSTMGR_PFM="NPSTMGR-PFM";
	public static final String PFRDA_TB = "PFRDA-TB";
	
	/**
	 * PFM Roles
	 */
	public static final String PFM = "PFM";
	public static final String PFM_MAKER = "PFM-Maker";
	public static final String PFM_CHECKER = "PFM-Checker";
	public static final String PFM_SUPERVISOR = "PFM-Supervisor";
	public static final String PFM_AM = "NPSTAM-PFM";
	public static final String PFM_MGR = "NPSTMGR-PFM";
	public static final String PFM_DGM = "NPSTDGM-PFM";
	public static final String PFM_GM = "NPSTGM-PFM";
	public static final String PFM_PFRDA = "PFRDA-PFM";
	
	
	
	/**
	 * Custodian Roles
	 */
	public static final String CUSTODIAN = "Custodian";
	public static final String CUSTODIAN_MAKER = "Custodian-Maker";
	public static final String CUSTODIAN_CHECKER = "Custodian-Checker";
	public static final String CUSTODIAN_SUPERVISOR = "Custodian-Supervisor";
	public static final String CUSTODIAN_AM = "NPSTAM-Custodian";
	public static final String CUSTODIAN_MGR = "NPSTMGR-Custodian";
	public static final String CUSTODIAN_DGM = "NPSTDGM-Custodian";
	public static final String CUSTODIAN_GM = "NPSTGM-Custodian";
	public static final String CUSTODIAN_PFRDA = "PFRDA-Custodian";
	public static final String CUSTODIAN_FA = "NPSTFA-Custodian";
	
	/**
	 * CRA Roles
	 */
	public static final String CRA = "CRA";
	public static final String CRA_MAKER = "CRA-Maker";
	public static final String CRA_CHECKER = "CRA-Checker";
	public static final String CRA_SUPERVISOR = "CRA-Supervisor";
	public static final String CRA_AM = "NPSTAM-CRA";
	public static final String CRA_DGM = "NPSTDGM-CRA";
	public static final String CRA_GM = "NPSTGM-CRA";
	public static final String CRA_PFRDA = "PFRDA-CRA";
	
	/**
	 * Grievances Roles
	 */
	public static final String GRIEVANCES = "Grievances";
	public static final String GRIEVANCES_MAKER = "Grievances-Maker";
	public static final String GRIEVANCES_CHECKER = "Grievances-Checker";
	public static final String GRIEVANCES_SUPERVISOR = "Grievances-Supervisor";
	public static final String GRIEVANCES_AM = "NPSTAM-Grievances";
	public static final String GRIEVANCES_MGR = "NPSTMGR-Grievances";
	public static final String GRIEVANCES_DGM = "NPSTDGM-Grievances";
	public static final String GRIEVANCES_GM = "NPSTGM-Grievances";
	public static final String GRIEVANCES_PFRDA = "PFRDA-Grievances";
	
	
	
	
//	public static final String MAKER = "Maker";
//	public static final String Checker = "Checker";
//	public static final String NPST_AM = "AM";
//	public static final String NPST_DGM = "DGM";
//	public static final String NPST_GM = "GM";
//	public static final String NPST_MGR = "MGR";
	
	public static final String NPST_ROLES[]= {NPST_AM,NPST_DGM,NPST_GM,NPST_CEO,NPST_MGR,NPSTMGR_GRIVANCES,NPSTMGR_PFM,PFM_AM,PFM_DGM,PFM_GM,PFM_MGR,CUSTODIAN_AM,CUSTODIAN_GM,CUSTODIAN_DGM,CUSTODIAN_MGR,CRA_AM,CRA_GM,CRA_DGM,GRIEVANCES_AM,GRIEVANCES_DGM,GRIEVANCES_GM,GRIEVANCES_MGR};
	public static final String NON_NPST_ROLES[]= {MAKER,CHECKER,CRA_CHECKER};
	public static final String CRA_ROLES[]= {GRIEVANCES,GRIEVANCES_MAKER,GRIEVANCES_CHECKER,GRIEVANCES_SUPERVISOR,CRA,CRA_MAKER,CRA_CHECKER,CRA_SUPERVISOR};
	//public static final String ERM_FORM_CREATION_ROLES1[]= {CRA_MAKER};
	
//	public static final String NPST_ROLES[]= {NPST_AM,NPST_DGM,NPST_GM,NPST_MGR};
//	public static final String NON_NPST_ROLES[]= {MAKER,Checker};
//	public static final String CRA_ROLES[]= {MAKER,Checker};
	public static final String ERM_FORM_CREATION_ROLES[]= {MAKER,GRIEVANCES_MAKER,CRA_MAKER};
	public static final String MANPOWER_CREATION_ROLES[]= {MAKER,PFM_MAKER};
	
	public static final String PFM_ROLES[]= {PFM,PFM_MAKER,PFM_CHECKER};
	
}

