DROP TABLE IF EXISTS "mn_npa_development";
DROP SEQUENCE IF EXISTS mn_npa_development_id__seq1;
CREATE SEQUENCE mn_npa_development_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_npa_development (
	reportuploadlogid bigint NOT NULL,
	companies character varying,
	date_1 character varying,
	tabledetails character varying,
	
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
    
    CONSTRAINT mn_npa_development_pkey PRIMARY KEY (reportuploadlogid)
)WITH (oids = false);

ALTER TABLE "mn_npa_development"
ADD "fileentryid" bigint null;
COMMENT ON TABLE "mn_npa_development" IS '';