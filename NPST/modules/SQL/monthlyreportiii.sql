DROP TABLE IF EXISTS "monthlyreportiii";
DROP SEQUENCE IF EXISTS monthlyreportiii_id__seq1;
CREATE SEQUENCE monthlyreportiii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.monthlyreportiii
(
    id_ bigint NOT NULL,
    referrals character varying(100) COLLATE pg_catalog."default",
    obnpst character varying(100) COLLATE pg_catalog."default",
    escalatedtonpst character varying(100) COLLATE pg_catalog."default",
    receivedduring character varying(100) COLLATE pg_catalog."default",
    rassignedbynpst character varying(100) COLLATE pg_catalog."default",
    resolvedduringmonth character varying(100) COLLATE pg_catalog."default",
    osattheendofthemonth character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT monthlyreportiii_pkey PRIMARY KEY (id_)
) WITH (oids = false);