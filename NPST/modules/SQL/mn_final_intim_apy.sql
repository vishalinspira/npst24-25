DROP TABLE IF EXISTS "mn_final_intim_apy";
DROP SEQUENCE IF EXISTS mn_final_intim_apy_id__seq1;
CREATE SEQUENCE mn_final_intim_apy_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_final_intim_apy (
    id_ bigint NOT NULL,
	apy_sp_reg_no numeric,
	apy_sp_name character varying,
	add_line1 character varying,
	add_line2 character varying,
	add_line3 character varying,
	add_line4 character varying,
	pin_code numeric,
	count_of_grievances_pending int,
	cra character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint NULL,
    CONSTRAINT mn_final_intim_apy_pkey PRIMARY KEY (id_)
)WITH (oids = false);