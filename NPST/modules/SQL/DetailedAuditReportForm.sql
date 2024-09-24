DROP TABLE IF EXISTS "detailed_audit_report";
DROP SEQUENCE IF EXISTS detailed_audit_report_id__seq1;
CREATE SEQUENCE detailed_audit_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.detailed_audit_report (
    reportuploadlogid bigint NOT NULL,
	from_ date,
	to_ date,
	dar_file_1_id bigint,
	dar_file_2_id bigint,
	dar_details character varying,
	
	reportMasterId bigint,
	reportdate timestamp,
	fileentryid bigint,
	
	createdon timestamp,
    createdby bigint,
	modifyby bigint,
    modifydate timestamp,
	
	status integer,
    statusbyuserid bigint,
    statusbyusername character varying,
    statusdate timestamp,
	workflowcontext text,
    remarks text,
	uuid_ character varying,
    
    CONSTRAINT detailed_audit_report_pkey PRIMARY KEY (reportuploadlogid)
)WITH (oids = false);

DROP TABLE IF EXISTS "detailed_audit_report_scrutiny";
DROP SEQUENCE IF EXISTS detailed_audit_report_scrutiny_id__seq1;
CREATE SEQUENCE detailed_audit_report_scrutiny_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.detailed_audit_report_scrutiny (
    scrutinyid bigint NOT NULL,
	reportUploadLogId bigint,
	username character varying,
	version numeric,
	userid bigint,
	dar_file_rem_1 character varying,
	dar_file_rem_2 character varying,
	management_comments character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT detailed_audit_report_scrutiny_pkey PRIMARY KEY (scrutinyid)
)WITH (oids = false);