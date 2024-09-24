DROP TABLE IF EXISTS "monthlyreportiv";
DROP SEQUENCE IF EXISTS monthlyreportiv_id__seq1;
CREATE SEQUENCE monthlyreportiv_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.monthlyreportiv
(
    id_ bigint NOT NULL,
    referralshandledbycras character varying COLLATE pg_catalog."default",
    referralsreceived character varying COLLATE pg_catalog."default",
    referralsinnatureof character varying COLLATE pg_catalog."default",
    netgrievances character varying COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT monthlyreportiv_pkey PRIMARY KEY (id_)
) WITH (oids = false);