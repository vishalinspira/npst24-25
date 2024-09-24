DROP TABLE IF EXISTS "all_chq_failed_tid_expired";
DROP SEQUENCE IF EXISTS all_chq_failed_tid_expired_id__seq1;
CREATE SEQUENCE all_chq_failed_tid_expired_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.all_chq_failed_tid_expired (
    id_ integer NOT NULL,
    transaction_no character varying(100),
    sol_id character varying(100),
    branch_name character varying(100),
    name_ character varying(100),
    pao_ain character varying(100),
    tran_id character varying(100),
    total_amt character varying(100),
    cheque_no character varying(100),
    receipt_date character varying(20),
    clearance_date character varying(20),
    credit_acc_no character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT all_chq_failed_tid_expired_pkey PRIMARY KEY (id_)
) WITH (oids = false);