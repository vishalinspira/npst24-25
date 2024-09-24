DROP TABLE IF EXISTS "manpowerform_ii";
DROP SEQUENCE IF EXISTS manpowerform_ii_id__seq1;
CREATE SEQUENCE manpowerform_ii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.manpowerform_ii (
	reportuploadlogid bigint NOT NULL,
	mp_iiReportFileId bigint NOT NULL,
	
    reportmasterid bigint,
    reportdate date,
	fileentryid bigint,
	createdon date,
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
    
    CONSTRAINT manpowerform_ii_pkey PRIMARY KEY (reportuploadlogid)
)WITH (oids = false);