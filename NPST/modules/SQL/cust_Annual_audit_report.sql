DROP TABLE IF EXISTS "cust_Annual_audit_report";
DROP SEQUENCE IF EXISTS cust_Annual_audit_report_id__seq1;
CREATE SEQUENCE cust_Annual_audit_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cust_Annual_audit_report (
    reportUploadLogId bigint NOT NULL,
	audit_pdf_fileentry_id bigint,
	cust_report_details character varying,
	name character varying,
	designation character varying,
	date_ character varying,
	place character varying,
	
	reportmasterid bigint,
    reportdate timestamp,
	
	status integer,
    statusbyuserid bigint,
    statusbyusername character varying,
    statusdate timestamp,
	workflowcontext text,
    remarks text,
	uuid_ character varying,
    
    modifyby bigint,
    modifydate timestamp,
	createdon timestamp,
    createdby bigint,
	
    CONSTRAINT cust_Annual_audit_report_pkey PRIMARY KEY (reportUploadLogId)
)WITH (oids = false);

ALTER TABLE "cust_Annual_audit_report"
ADD "fileentryid" bigint NULL;
COMMENT ON TABLE "cust_Annual_audit_report" IS '';


DROP TABLE IF EXISTS "cust_Annual_report_scrutiny";
DROP SEQUENCE IF EXISTS cust_Annual_report_scrutiny_id__seq1;
CREATE SEQUENCE cust_Annual_report_scrutiny_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cust_Annual_report_scrutiny (
    scrutinyid bigint NOT NULL,
	reportUploadLogId bigint,
	username character varying,
	version numeric,
	userid bigint,
	cust_report_remarks character varying,
	audit_pdf_file_remarks character varying,
	createdon date,
    createdby bigint,
	
    CONSTRAINT cust_Annual_report_scrutiny_pkey PRIMARY KEY (scrutinyid)
)WITH (oids = false);