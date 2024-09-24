
DROP TABLE IF EXISTS "collections_tid_tobe_enabled";
DROP SEQUENCE IF EXISTS collections_tid_tobe_enabled_id__seq1;
CREATE SEQUENCE collections_tid_tobe_enabled_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.collections_tid_tobe_enabled (
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
    email_id character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT collections_tid_tobe_enabled_pkey PRIMARY KEY (id_)
)WITH (oids = false);