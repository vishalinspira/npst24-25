DROP TABLE IF EXISTS "mn_all_chq_failed_tid_expired";
DROP SEQUENCE IF EXISTS mn_all_chq_failed_tid_expired_id__seq1;
CREATE SEQUENCE mn_all_chq_failed_tid_expired_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_all_chq_failed_tid_expired (
    id_ bigint NOT NULL,
    transaction_no character varying(50) NOT NULL,
    sol_id int,
    branch_name character varying(50) NOT NULL,
    name_ character varying(50) NOT NULL,
    pao_ain int,
    tran_id bigint NOT NULL,
    total_amt numeric(10,2) NOT NULL,
    cheque_no int,
    receipt_date date NOT NULL,
    clearance_date date NOT NULL,
    credit_acc_no bigint NOT NULL,
    cra character varying(10), 
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_all_chq_failed_tid_expired_pkey PRIMARY KEY (id_)
) WITH (oids = false);