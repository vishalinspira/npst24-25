DROP TABLE IF EXISTS "mn_chq_clearance_tat";
DROP SEQUENCE IF EXISTS mn_chq_clearance_tat_id__seq1;
CREATE SEQUENCE mn_chq_clearance_tat_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_chq_clearance_tat (
    id_ bigint NOT NULL,
    easypay_tran_id int,
    sol_id int,
    axis_bank_branch character varying(50),
    cra_tran_id bigint,
    beneficiary_name character varying(50),
    clearance_amt numeric(15,2),
    chq_no int,
    receipt_date date,
    clearance_date date,
    clearance_tat character varying(50),
    delay_period_above_5_days int,
    reason character varying(50),
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_chq_clearance_tat_pkey PRIMARY KEY (id_)
) WITH (oids = false);