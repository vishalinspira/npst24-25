DROP TABLE IF EXISTS "grievancereceived";
DROP SEQUENCE IF EXISTS grievancereceived_id__seq1;
CREATE SEQUENCE grievancereceived_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.grievancereceived
(
    id_ bigint NOT NULL,
    gsno character varying(100) COLLATE pg_catalog."default",
    tokennumber character varying(100) COLLATE pg_catalog."default",
    raisedbyentity character varying(100) COLLATE pg_catalog."default",
    raisedbyentityid character varying(100) COLLATE pg_catalog."default",
    raisedagainstentity character varying(100) COLLATE pg_catalog."default",
    raisedagainstentityid character varying(100) COLLATE pg_catalog."default",
    grievancetype character varying(100) COLLATE pg_catalog."default",
    grievancecategory character varying(100) COLLATE pg_catalog."default",
    gstatus character varying(100) COLLATE pg_catalog."default",
    loggeddate character varying(100) COLLATE pg_catalog."default",
    resolveddate character varying(100) COLLATE pg_catalog."default",
    grievancesubtype character varying(100) COLLATE pg_catalog."default",
    grievancedescription character varying(100) COLLATE pg_catalog."default",
    gresolution character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT grievancereceived_pkey PRIMARY KEY (id_)
) WITH (oids = false);