DROP TABLE IF EXISTS "quarterlyintervaliv";
DROP SEQUENCE IF EXISTS quarterlyintervaliv_id__seq1;
CREATE SEQUENCE quarterlyintervaliv_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.quarterlyintervaliv
(
    id_ bigint,
    intervaltablename character varying(100) COLLATE pg_catalog."default",
    schemetype character varying(100) COLLATE pg_catalog."default",
    occurrence character varying(100) COLLATE pg_catalog."default",
    submission character varying(100) COLLATE pg_catalog."default",
    dateoficmeeting character varying(100) COLLATE pg_catalog."default",
    boardmeeting character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint
)
WITH (
    OIDS = FALSE
);