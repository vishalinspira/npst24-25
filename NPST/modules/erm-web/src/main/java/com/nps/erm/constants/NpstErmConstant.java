package com.nps.erm.constants;

public class NpstErmConstant {
	public static final int BATCH_PENDING = 1;
	public static final int FORWARD_TO_NPST = 1;
	public static final int BATCH_REJECTED = 2;
	public static final int BATCH_CANCELLED = 3;
	public static final int NPST_FIRST_LEVEL_APPROVED = 4;
	public static final String NPST_REJECT_TRANS_NAME = "Revert to Checker";
	public static final String NPST_APPROVED_TRANS_NAME = "Send to CRA";
	public static final String CHECKER_APPROVED_TRANS_NAME = "Forward to NPST";
	public static final String NON_NPST_SECOND_LEVEL_APPROVE_TRANS_NAME = "Send To Npst";
	public static final String RESUBMIT_TRANSTION_NAME = "Resubmit";
	
	public static final String DATE_ERROR_MESSAGE = "Date should not be greater than current date.";
	public static final String REMITTED_AMMOUNT_ERROR_MESSAGE = "Amount can not ne greater the remitted amount.";
	
	public static final String ALL_CRA = "-1";
	
	public static final String PROCESSED = "Processed";
	
	public static final String DATE_FORMAT="dd-MM-yyyy";

	public static final String NON_PROCESSED = "Not Processed";
}
