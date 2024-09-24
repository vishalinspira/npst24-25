DROP TABLE IF EXISTS "mn_all_electronic_reject";
DROP SEQUENCE IF EXISTS mn_all_electronic_reject_id__seq1;
CREATE SEQUENCE mn_all_electronic_reject_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_all_electronic_reject (
    id_ bigint NOT NULL,
    sr_no int NOT NULL ,
    payment_id bigint NOT NULL ,
    ret_ref_no character varying(15) ,
    source_acc_no_nodal bigint NOT NULL,
    source_acc_no_ifsc character varying(15) ,
    payment_receipt_date date ,
    period_ character varying(10) ,
    utr_no character varying(50) ,
    mode_ character varying(5) ,
    utr_amount numeric(15,2) ,
    beneficiary_acc_no bigint NOT NULL,
    nps_virtual_acc_no int,
    nps_acc_name character varying(50),
    return_date date ,
    returned_utr character varying(50) ,
    tid bigint,
    reason_return character varying(100) ,
    additional_comments character varying(100),
    pao_name character varying(100),
    email_id character varying(50),
    delay_remarks character varying(100) ,
    cra character varying(10),
    createdon date,
    createdby integer,
    reportuploadlogid bigint,
    CONSTRAINT mn_all_electronic_reject_pkey PRIMARY KEY (id_)
)WITH (oids = false);