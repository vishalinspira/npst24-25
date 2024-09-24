DROP TABLE IF EXISTS "quarterlyintervalv";
DROP SEQUENCE IF EXISTS quarterlyintervalv_id__seq1;
CREATE SEQUENCE quarterlyintervalv_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.quarterlyintervalv
(
    id_ bigint,
    nameofdirector character varying(100) COLLATE pg_catalog."default",
    dignation character varying(100) COLLATE pg_catalog."default",
    indepent character varying(100) COLLATE pg_catalog."default",
    dateofsubmission date,
    createdate date,
    createdby bigint
)
WITH (
    OIDS = FALSE
);