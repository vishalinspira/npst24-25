DROP TABLE IF EXISTS "qr_stewardship_report";
DROP SEQUENCE IF EXISTS qr_stewardship_report_id__seq1;
CREATE SEQUENCE qr_stewardship_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_stewardship_report (
    reportuploadlogid bigint NOT NULL,
	date_1 character varying,
	conflict_of_interest character varying,
	monitoring_situation character varying,
	resolutions_voted character varying,
	conflict_rem1 character varying,
	monitoring_rem1 character varying,
	resolutions_rem1 character varying,
	date_2 character varying,
	employee_name character varying,
	place character varying,
	roles character varying,
	annexure_a bigint,
	annexure_b_i bigint,
	annexure_b_ii bigint,
	annexure_c bigint,
	annexureA_rem character varying,
	annexureB_I_rem character varying,
	annexureB_II_rem character varying,
	annexureC_rem character varying,
	
	reportmasterid bigint,
    reportdate date,
    fileEntryId bigint,
	
	createdon timestamp,
    createdby bigint,
	modifyby bigint,
    modifydate timestamp,
	
	status integer,
    statusbyuserid bigint,
    statusbyusername character varying,
    statusdate timestamp,
    uuid_ character varying,
	workflowcontext text,
    remarks text,
 
    
    CONSTRAINT qr_stewardship_report_pkey PRIMARY KEY (reportuploadlogid)
)WITH (oids = false);


DROP TABLE IF EXISTS "qr_stewardship_report_scrutiny";
DROP SEQUENCE IF EXISTS qr_stewardship_report_scrutiny_id__seq1;
CREATE SEQUENCE qr_stewardship_report_scrutiny_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_stewardship_report_scrutiny (
    scrutinyid bigint NOT NULL,
	reportUploadLogId bigint,
	username character varying,
	version_ numeric,
	userid bigint,
	conflict_of_interest_rem character varying,
	monitoring_situation_rem character varying,
	resolutions_voted_rem character varying,
	annexure_a character varying,
	annexure_b_i character varying,
	annexure_b_ii character varying,
	annexure_c character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT qr_stewardship_report_scrutiny_pkey PRIMARY KEY (scrutinyid)
)WITH (oids = false);