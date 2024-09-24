DROP TABLE IF EXISTS "mn_consolidated_collection_rpt";
DROP SEQUENCE IF EXISTS mn_consolidated_collection_rpt_id__seq1;
CREATE SEQUENCE mn_consolidated_collection_rpt_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_consolidated_collection_rpt (
    id_ bigint NOT NULL,
    file_name character varying(100) NOT NULL,
    channel character varying(10) NOT NULL,
    paofin character varying(50) NOT NULL,
    pao_reg_no bigint NOT NULL,
    trans_id bigint NOT NULL,
    amount numeric(15,2) NOT NULL,
    file_ref_no character varying(50) NOT NULL,
    record_no integer NOT NULL,
    receipt_date date NOT NULL,
    fund_realised_date date NOT NULL,
    frc_upload_date date NOT NULL,
    trustee_bank_tat character varying(50) NOT NULL,
    matching_type character varying(50) NOT NULL,
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_consolidated_collection_rpt_pkey PRIMARY KEY (id_)
) WITH (oids = false);