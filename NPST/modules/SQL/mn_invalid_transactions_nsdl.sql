DROP TABLE IF EXISTS "mn_invalid_transactions_nsdl";
DROP SEQUENCE IF EXISTS mn_invalid_transactions_nsdl_id__seq1;
CREATE SEQUENCE mn_invalid_transactions_nsdl_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_invalid_transactions_nsdl (
    id_ bigint NOT NULL,
    sr_no int  ,
    payment_id int NOT NULL,
    ret_ref_no character varying  ,
    source_acc_no_nodal NOT NULL,
    ifsc_source_no character varying  ,
    payment_receipt_date date  ,
    period_ character varying  ,
    original_utr character varying  ,
    mode_ character varying  ,
    utr_amount numeric(15,2)  ,
    beneficiary_acc_no int NOT NULL,
    nps_virtual_acc_no int,
    nps_acc_name character varying,
    return_date date  ,
    returned_utr character varying  ,
    tid int,
    reason_return character varying  ,
    additional_comments character varying,
    pao_name character varying,
    email_id character varying,
    return_tat_remarks character varying NOT NULL,
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_invalid_transactions_nsdl_pkey PRIMARY KEY (id_)
)WITH (oids = false);