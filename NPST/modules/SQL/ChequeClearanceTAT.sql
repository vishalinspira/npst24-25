DROP TABLE IF EXISTS "chq_clearance_tat";
DROP SEQUENCE IF EXISTS chq_clearance_tat_id__seq1;
CREATE SEQUENCE chq_clearance_tat_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.chq_clearance_tat (
    id_ integer NOT NULL,
    easypay_tran_id character varying(100),
    sol_id character varying(100),
    axis_bank_branch character varying(100),
    cra_tran_id character varying(100),
    beneficiary_name character varying(100),
    clearance_amt character varying(100),
    chq_no character varying(100),
    receipt_date character varying(20),
    clearance_date character varying(20),
    clearance_tat character varying(100),
    delay_period_above_5_days character varying(100),
    reason character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT chq_clearance_tat_pkey PRIMARY KEY (id_)
) WITH (oids = false);