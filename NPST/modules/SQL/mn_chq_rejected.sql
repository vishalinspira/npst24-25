DROP TABLE IF EXISTS "mn_chq_rejected";
DROP SEQUENCE IF EXISTS mn_chq_rejected_id__seq1;
CREATE SEQUENCE mn_chq_rejected_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_chq_rejected (
    id_ bigint NOT NULL,
    receipt_date date,
    rejection_date date,
    sol_id int,
    name_ character varying(50),
    pao_ain int,
    tran_id bigint,
    chq_no bigint,
    total_amt numeric(15,2),
    drawn_on_bank character varying(50),
    drawn_on_branch character varying(50),
    return_desc character varying(50),
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_chq_rejected_pkey PRIMARY KEY (id_)
) WITH (oids = false);