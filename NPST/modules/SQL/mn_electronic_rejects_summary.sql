DROP TABLE IF EXISTS "mn_electronic_rejects_summary";
DROP SEQUENCE IF EXISTS mn_electronic_rejects_summary_id__seq1;
CREATE SEQUENCE mn_electronic_rejects_summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_electronic_rejects_summary (
    id_ bigint NOT NULL,
    payment_date date NOT NULL,
    nps_acc_name character varying(50) NOT NULL,
    count_payment_id integer NOT NULL,
    sum_utr_amt numeric(15,2) NOT NULL,
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_electronic_rejects_summary_pkey PRIMARY KEY (id_)
) WITH (oids = false);