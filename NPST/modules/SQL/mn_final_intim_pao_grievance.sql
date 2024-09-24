DROP TABLE IF EXISTS "mn_final_intim_pao_grievance";
DROP SEQUENCE IF EXISTS mn_final_intim_pao_grievance_id__seq1;
CREATE SEQUENCE mn_final_intim_pao_grievance_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.mn_final_intim_pao_grievance
(
    id_ bigint NOT NULL,
	sr_no integer,
	prao_reg_no character varying NOT NULL,
	pao_reg_no character varying NOT NULL,
	pao_name character varying, 
    token_number character varying NOT NULL,
    grievance_logging_date date,
	pran bigint NOT NULL,
	subscriber_name character varying,
	cra character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    reportuploadlogid bigint NULL,
    CONSTRAINT mn_final_intim_pao_grievance_pkey PRIMARY KEY (id_)
) WITH (oids = false);