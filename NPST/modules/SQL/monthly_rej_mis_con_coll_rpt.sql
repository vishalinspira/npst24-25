DROP TABLE IF EXISTS "monthly_rej_mis_con_coll_rpt";
DROP SEQUENCE IF EXISTS monthly_rej_mis_con_coll_rpt_id__seq1;
CREATE SEQUENCE monthly_rej_mis_con_coll_rpt_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.monthly_rej_mis_con_coll_rpt (
    id_ bigint NOT NULL,
    file_name character varying(100) NOT NULL,
    channel character varying(10) NOT NULL,
    paofin character varying(50) NOT NULL,
    pao_reg_no bigint NOT NULL,
    trans_id numeric(20,0) NOT NULL,
    amount numeric(15,2) NOT NULL,
    file_ref_no bigint NOT NULL,
    record_no bigint NOT NULL,
    receipt_date timestamp without time zone NOT NULL,
    fund_released_date timestamp without time zone NOT NULL,
    frc_upload_date timestamp without time zone NOT NULL,
    trustee_bank_tat character varying(50) NOT NULL,
    matching_type character varying(50) NOT NULL,
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT monthly_rej_mis_con_coll_rpt_pkey PRIMARY KEY (id_)
) WITH (oids = false);