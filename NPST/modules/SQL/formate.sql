DROP TABLE IF EXISTS "formate";
DROP SEQUENCE IF EXISTS formate_id__seq1;
CREATE SEQUENCE formate_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.formate
(
    id_ bigint NOT NULL,
    formate_sector character varying(100) COLLATE pg_catalog."default",
    quarteraspercradatabase character varying(100) COLLATE pg_catalog."default",
    lastdateofthequarter character varying(100) COLLATE pg_catalog."default",
    actualreportedwithdrawals character varying(100) COLLATE pg_catalog."default",
    remarksofcra character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT formate_pkey PRIMARY KEY (id_)
) WITH (oids = false);