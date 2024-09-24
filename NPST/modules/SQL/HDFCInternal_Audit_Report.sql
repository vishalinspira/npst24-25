DROP TABLE IF EXISTS "internal_audit_report";
DROP SEQUENCE IF EXISTS internal_audit_report_id__seq1;
CREATE SEQUENCE internal_audit_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internal_audit_report (
	uuid_ character varying,
    reportUploadLogId bigint NOT NULL,
    date_ date,
	period_from character varying,
	period_to character varying,
	companies character varying,
	certificate_pdf_file_id bigint,
	auditor_pdf_file_id bigint,
	intrnlAdtRprt_pdf_file_id bigint,
	extrctBrdMinAprvng_pdf_file_id bigint,
	iarcircular_excel_file_id bigint,
	hdfc_audit_report_contex character varying,
	
	reportMasterId bigint,
	reportDate date,
	fileentryid bigint,
	
	createdon date,
	createdby bigint,
	modifyBy bigint,
	modifyDate date,
	
	status bigint,
	statusByUserId bigint,
	statusByUserName character varying,
	statusDate date,
	workflowContext character varying,
	remarks character varying,
	
	
    CONSTRAINT internal_audit_report_pkey PRIMARY KEY (reportUploadLogId)
)WITH (oids = false);



DROP TABLE IF EXISTS "internal_audit_report_scrutiny";
DROP SEQUENCE IF EXISTS internal_audit_report_scrutiny_id__seq1;
CREATE SEQUENCE internal_audit_report_scrutiny_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internal_audit_report_scrutiny (
    id_ bigint NOT NULL,
    reportUploadLogId bigint,
    username character varying,
    version double precision,
    userid bigint,
    management_comments character varying,
    certificate_pdf_file_remarks character varying,
    auditor_pdf_file_remarks  character varying,
    iarcircular_pdf_file_remarks  character varying,
    
	createdon date,
	createdby bigint,
	
    CONSTRAINT internal_audit_report_scrutiny_pkey PRIMARY KEY (id_)
)WITH (oids = false);