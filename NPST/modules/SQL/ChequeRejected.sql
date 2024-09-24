DROP TABLE IF EXISTS "chq_rejected_oct2021";
DROP SEQUENCE IF EXISTS chq_rejected_oct2021_id__seq1;
CREATE SEQUENCE chq_rejected_oct2021_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.chq_rejected_oct2021 (
    id_ integer NOT NULL,
    receipt_date character varying(20),
    rejection_date character varying(20),
    sol_id character varying(100),
    name_ character varying(100),
    pao_ain character varying(100),
    tran_id character varying(100),
    chq_no character varying(100),
    total_amt character varying(100),
    drawn_on_bank character varying(100),
    drawn_on_branch character varying(100),
    return_desc character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT chq_rejected_oct2021_pkey PRIMARY KEY (id_)
) WITH (oids = false);