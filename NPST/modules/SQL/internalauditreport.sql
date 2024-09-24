DROP TABLE IF EXISTS "internalauditreport";
DROP SEQUENCE IF EXISTS internalauditreport_id__seq1;
CREATE SEQUENCE internalauditreport_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internalauditreport 
(
    id_ bigint NOT NULL,
    reportname character varying(1000) COLLATE pg_catalog."default",
    reportdate date,
    tablename character varying(1000) COLLATE pg_catalog."default",
    sampling character varying(1000) COLLATE pg_catalog."default",
    particulars character varying(1000) COLLATE pg_catalog."default",
    auditorscomments character varying(1000) COLLATE pg_catalog."default",
    status character varying(1000) COLLATE pg_catalog."default",
    riskrating character varying(1000) COLLATE pg_catalog."default",
    managementresponses character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT internalauditreport_pkey PRIMARY KEY (id_)
)
WITH (oids = false);