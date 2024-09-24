DROP TABLE IF EXISTS "all_electronic_accepted";
DROP SEQUENCE IF EXISTS all_electronic_accepted_id__seq1;
CREATE SEQUENCE all_electronic_accepted_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.all_electronic_accepted (
    id_ integer NOT NULL,
    file_name character varying(100),
    channel character varying(100),
    pao_fin character varying(100),
    pao_reg_no character varying(100),
    trans_id character varying(100),
    amt character varying(100),
    file_ref_no character varying(100),
    record_no character varying(100),
    receipt_date character varying(20),
    fund_realised_date character varying(20),
    frc_upload_date character varying(20),
    trustee_bank_tat character varying(100),
    matching_type character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT all_electronic_accepted_pkey PRIMARY KEY (id_)
) WITH (oids = false);