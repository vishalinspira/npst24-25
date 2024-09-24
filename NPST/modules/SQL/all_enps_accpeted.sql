DROP TABLE IF EXISTS "all_enps_accpeted";
DROP SEQUENCE IF EXISTS all_enps_accpeted_id__seq1;
CREATE SEQUENCE all_enps_accpeted_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.all_enps_accpeted (
   	id_ bigint NOT NULL,
    file_name character varying(50) NOT NULL,
    channel character varying(50) NOT NULL,
    pao_fin character varying(50) NOT NULL,
    pao_reg_no bigint NOT NULL,
    trans_id bigint NOT NULL,
    amt numeric(15,2) NOT NULL,
    file_ref_no character varying(50) NOT NULL,
    record_no integer NOT NULL,
    receipt_date date NOT NULL,
    fund_realised_date date NOT NULL,
    frc_upload_date date NOT NULL,
    trustee_bank_tat character varying(50) NOT NULL,
    matching_type character(2) NOT NULL,
    cra character varying(10),
    createdon date,
    createdby bigint,
    CONSTRAINT all_enps_accpeted_pkey PRIMARY KEY (id_)
) WITH (oids = false);