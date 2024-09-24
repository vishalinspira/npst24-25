DROP TABLE IF EXISTS "mn_grievance_details";
DROP SEQUENCE IF EXISTS mn_grievance_details_id__seq1;
CREATE SEQUENCE mn_grievance_details_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.mn_grievance_details
(
    id_ bigint NOT NULL,
    sr_No integer,
    token_number character varying NOT NULL,
    pran  character varying,
	date_ date,
	status character varying,
	grievance_logging_date date,
	raised_by character varying,
	pao_reg_no character varying NOT NULL,
	pao_name character varying,
	pr_ao_reg_no character varying NOT NULL,
	pr_ao_name character varying, 
	ministry_name character varying,
	sector character varying,
	sector_Type character varying,
	state_name character varying,
	broad_category character varying,
	grievance_text character varying,
	cra character varying,
    createdate date,
    createdby bigint,
    reportuploadlogid bigint NULL,
    CONSTRAINT mn_grievance_details_pkey PRIMARY KEY (id_)
) WITH (oids = false);