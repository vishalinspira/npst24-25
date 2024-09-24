DROP TABLE IF EXISTS "formatia";
DROP SEQUENCE IF EXISTS formatia_id__seq1;
CREATE SEQUENCE formatia_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.formatia
(
    id_ bigint NOT NULL,
    formatiasector character varying(100) COLLATE pg_catalog."default",
    withdrawaltype character varying(100) COLLATE pg_catalog."default",
    pendingbeginningofthemonth character varying(100) COLLATE pg_catalog."default",
    claimsreceivedduringmonth character varying(100) COLLATE pg_catalog."default",
    claimssettledduring character varying(100) COLLATE pg_catalog."default",
    claimsrejectedduring character varying(100) COLLATE pg_catalog."default",
    claimsoutstandingendofmonth character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT formatia_pkey PRIMARY KEY (id_)
) WITH (oids = false);