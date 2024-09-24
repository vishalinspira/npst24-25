DROP TABLE IF EXISTS "cust_internal_audit_report";
DROP SEQUENCE IF EXISTS cust_internal_audit_report_id__seq1;
CREATE SEQUENCE cust_internal_audit_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cust_internal_audit_report (
    reportuploadlogid bigint NOT NULL,
	iar_file_id bigint,
	date_1 date,
	date_2 date,
	iar_details character varying,
	name_ character varying,
	designation character varying,
	date_3 character varying,
	
	reportmasterid bigint,
    reportdate timestamp,
	
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
    
    CONSTRAINT cust_internal_audit_report_pkey PRIMARY KEY (reportuploadlogid)
)WITH (oids = false);

ALTER TABLE "cust_internal_audit_report"
ADD "fileentryid" bigint NULL;
COMMENT ON TABLE "cust_internal_audit_report" IS '';

DROP TABLE IF EXISTS "cust_internal_audit_scrutiny";
DROP SEQUENCE IF EXISTS cust_internal_audit_scrutiny_id__seq1;
CREATE SEQUENCE cust_internal_audit_scrutiny_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cust_internal_audit_scrutiny (
    scrutinyid bigint NOT NULL,
	reportUploadLogId bigint,
	username character varying,
	version numeric,
	userid bigint,
	iar_file_rem character varying,
	nps_trust_comments character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT cust_internal_audit_scrutiny_pkey PRIMARY KEY (scrutinyid)
)WITH (oids = false);