DROP TABLE IF EXISTS "invalid_acc_no";
DROP SEQUENCE IF EXISTS invalid_acc_no_id__seq1;
CREATE SEQUENCE invalid_acc_no_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.invalid_acc_no (
    id_ integer NOT NULL,
    sno character varying(100),
    payment_id character varying(100),
    ret_ref_no character varying(100),
    source_acc_no_nodal character varying(100),
    ifsc_source_no character varying(100),
    payment_receipt_date character varying(20),
    period_ character varying(20),
    original_utr character varying(100),
    mode_ character varying(100),
    utr_amt character varying(100),
    beneficiary_acc_no character varying(100),
    nps_virtual_acc_no character varying(100),
    nps_acc_name character varying(100),
    return_date character varying(20),
    returned_utr character varying(100),
    tid character varying(100),
    return_reason character varying(100),
    additional_comments character varying(100),
    pao_name character varying(100),
    email_id character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT invalid_acc_no_pkey PRIMARY KEY (id_)
)WITH (oids = false);