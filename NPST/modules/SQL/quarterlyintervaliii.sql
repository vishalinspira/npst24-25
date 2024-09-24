DROP TABLE IF EXISTS "quarterlyintervaliii";
DROP SEQUENCE IF EXISTS quarterlyintervaliii_id__seq1;
CREATE SEQUENCE quarterlyintervaliii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.quarterlyintervaliii
(
    id_ bigint NOT NULL,
    position_ character varying(100) COLLATE pg_catalog."default",
    nameofthepersonnel character varying(100) COLLATE pg_catalog."default",
    createdate character varying(100) COLLATE pg_catalog."default",
    createdby character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT quarterlyintervaliii_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);