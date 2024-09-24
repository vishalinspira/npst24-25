DROP TABLE IF EXISTS "mn_final_intim_apy_grievance";
DROP SEQUENCE IF EXISTS mn_final_intim_apy_grievance_id__seq1;
CREATE SEQUENCE mn_final_intim_apy_grievance_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_final_intim_apy_grievance (
    id_ bigint NOT NULL,
	sr_no int,
	apy_sp_reg_no character varying NOT NULL,
	apy_sp_name character varying,
	token_number character varying NOT NULL,
	grievance_logging_date date,
	pran bigint NOT NULL,
	subscriber_name character varying ,
	cra character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint NULL,
    CONSTRAINT mn_final_intim_apy_grievance_pkey PRIMARY KEY (id_)
)WITH (oids = false);