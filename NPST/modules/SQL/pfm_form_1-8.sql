CREATE TABLE PUBLIC.OVERALL_STATUS_SECTOR_WISE(
    ID_ BIGINT NOT NULL PRIMARY KEY,
    AS_ON_DATE DATE,
    SECTOR CHARACTER VARYING,
    NUMBER_OF_SUBSCRIBER INTEGER,
    SUBSCRIBER_REG_DURING_WEEK INTEGER,
	CONTRIBUTION_M_AND_B NUMERIC,
	CHANGE_IN_CONTRIB_DURING_WEEK NUMERIC,
	PENDING_FOR_M_AND_B NUMERIC,
	TOTAL NUMERIC,
	NET_AMOUNT_INVESTED NUMERIC,
	TOTAL_ASSETS_UNDER_MGMT NUMERIC,
	CREATEDON DATE,
	CREATEDBY BIGINT,
	REPORTUPLOADLOGID BIGINT);
	
	CREATE TABLE PUBLIC.OVERALL_STATUS_OF_STATE_GOVT(
    ID_ BIGINT NOT NULL PRIMARY KEY,
    AS_ON_DATE DATE,
    STATE_GOVT CHARACTER VARYING,
    DATE_OF_NOTIFICATION DATE,
    DATE_OF_ADOPTION DATE,
	TOTAL_NO_OF_SUBSCRIBER NUMERIC,
	NO_OF_NON_IRA_SUBSCRIBERS NUMERIC,
	REG_DURING_THIS_WEEK NUMERIC,
	TOTAL_CONTRIBUTION NUMERIC,
	CONTRIBUTION_M_B NUMERIC,
	CHANGE_CONTRIB_M_B_DURING_WEEK NUMERIC,
	AUM NUMERIC,
	CREATEDON DATE,
	CREATEDBY BIGINT,
	REPORTUPLOADLOGID BIGINT);
	
	CREATE TABLE PUBLIC.STATUS_OF_CENTRAL_GOVERNMENT(
    ID_ BIGINT NOT NULL PRIMARY KEY,
    AS_ON_DATE DATE,
    ACCOUNTING_FORMATION CHARACTER VARYING,
    CHANGE_SUBS_DURING_PAST_WEEK INTEGER,
    TOTAL_NO_OF_SUBSCRIBERS INTEGER,
	NO_OF_NON_IRA_COMPLIANT_SUBS INTEGER,
	NON_IRA_SUBS_FLAGGED_IN_SYSTEM INTEGER,
	CONTRIBUTION_AMT_FOR_SCFS_M_B NUMERIC,
	CONTRIB_AMT_FOR_SCFS_PEND_M_B NUMERIC,
	AUM NUMERIC,
	CREATEDON DATE,
	CREATEDBY BIGINT,
	REPORTUPLOADLOGID BIGINT);
	
	CREATE TABLE PUBLIC.STATUS_OF_INDIVI_SUBSCRIBER(
    ID_ BIGINT NOT NULL PRIMARY KEY,
    AS_ON_DATE DATE,
    TOTAL_NO_OF_REG_POPS_IN_CRA CHARACTER VARYING,
    TOT_NO_OF_REG_POP_SPS_IN_CRA INTEGER,
    TOTAL_NO_OF_SUBSCRIBERS INTEGER,
	TOTAL_NO_OF_SUBS INTEGER,
	TOT_NO_OF_ACTIVE_ACCOUNT_SUBS INTEGER,
	TOTAL_AMT_OF_SUBS_CONTRIB_M_B NUMERIC,
	TIERI_TIERII CHARACTER VARYING,
	CREATEDON DATE,
	CREATEDBY BIGINT,
	REPORTUPLOADLOGID BIGINT);
	
	CREATE TABLE PUBLIC.STATUS_OF_CORPORATE_SECTOR(
    ID_ BIGINT NOT NULL PRIMARY KEY,
    TOT_NO_OF_SUB_REG_CORP_SECTOR INTEGER,
    TOTAL_CONTRIB_SCF_UPLOADED NUMERIC,
	CONTRIB_AMT_MATCHED_BOOKED NUMERIC,
	TOT_AMT_PEND_MATCHING_BOOKING NUMERIC,
	TOTAL_AUM NUMERIC,
	NO_OF_CORP_CONTRIB_REMITTED INTEGER,
	CREATEDON DATE,
	CREATEDBY BIGINT,
	REPORTUPLOADLOGID BIGINT);
	
	CREATE TABLE PUBLIC.STATUS_OF_NPS_LITE(
    ID_ BIGINT NOT NULL PRIMARY KEY,
	AS_ON_DATE DATE,
    TOTAL_NUMBER_OF_AGGREGATORS INTEGER,
    TOTAL_NO_OF_SUBSCRIBERS_REG INTEGER,
	TOTAL_CONTRIBUTION_AMOUNT NUMERIC,
	CONTRIB_AMT_MATCHED_BOOKED NUMERIC,
	AMT_PEND_MATCHING_BOOKING NUMERIC,
	TOTAL_AUM NUMERIC,
	CREATEDON DATE,
	CREATEDBY BIGINT,
	REPORTUPLOADLOGID BIGINT);
	