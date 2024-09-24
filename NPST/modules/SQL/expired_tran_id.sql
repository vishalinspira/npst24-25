DROP TABLE IF EXISTS "expired_tran_id";
DROP SEQUENCE IF EXISTS expired_tran_id_id__seq1;
CREATE SEQUENCE expired_tran_id_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.expired_tran_id (
    id_ bigint NOT NULL,
    sr_no bigint NOT NULL,
    payment_id character varying(50) NOT NULL,
    ret_ref_no character varying(50) NOT NULL,
    source_acc_no_nodal character varying(50),
    ifsc_source_no character varying(50) NOT NULL,
    payment_receipt_date date NOT NULL,
    period_ character varying(50) NOT NULL,
    original_utr character varying(50) NOT NULL,
    mode_ character varying(50) NOT NULL,
    utr_amount numeric(15,2) NOT NULL,
    beneficiary_acc_no character varying(50),
    nps_virtual_acc_no character varying(50),
    nps_acc_name character varying(50),
    return_date date NOT NULL,
    returned_utr character varying(50) NOT NULL,
    tid character varying(50),
    reason_return character varying(50) NOT NULL,
    additional_comments character varying(50),
    pao_name character varying(50),
    email_id character varying(50),
    cra character varying(10),
    createdon date,
    createdby bigint,
    CONSTRAINT expired_tran_id_pkey PRIMARY KEY (id_)
)WITH (oids = false);