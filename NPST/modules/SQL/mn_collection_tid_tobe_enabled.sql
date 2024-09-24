DROP TABLE IF EXISTS "mn_collection_tid_tobe_enabled";
DROP SEQUENCE IF EXISTS mn_collection_tid_tobe_enabled_id__seq1;
CREATE SEQUENCE mn_collection_tid_tobe_enabled_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_collection_tid_tobe_enabled (
    id_ bigint NOT NULL,
    transaction_no character varying(50) ,
    sol_id int ,
    branch_name character varying(50) ,
    name_ character varying(50) ,
    pao_ain int ,
    tran_id bigint ,
    total_amt numeric(10,2) ,
    cheque_no int ,
    receipt_date date ,
    clearance_date date ,
    credit_acc_no bigint ,
    email_id character varying(50) ,
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_collection_tid_tobe_enabled_pkey PRIMARY KEY (id_)
)WITH (oids = false);