DROP TABLE IF EXISTS "annexureva";
DROP SEQUENCE IF EXISTS annexureva_id__seq1;
CREATE SEQUENCE annexureva_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.annexureva
(
    id_ bigint NOT NULL,
    namefbill character varying(100) COLLATE pg_catalog."default",
    noofbill character varying(100) COLLATE pg_catalog."default",
    pfs_schemesname character varying(100) COLLATE pg_catalog."default",
    pfs_ascharges character varying(100) COLLATE pg_catalog."default",
    pfs_custodycharges character varying(100) COLLATE pg_catalog."default",
    pfs_ccilcharges character varying(100) COLLATE pg_catalog."default",
    pfs_cbricscharges character varying(100) COLLATE pg_catalog."default",
    pfs_nsdlcharges character varying(100) COLLATE pg_catalog."default",
    pfs_sebicharges character varying(100) COLLATE pg_catalog."default",
    totalcharges character varying(100) COLLATE pg_catalog."default",
    shcil_ascharges character varying(100) COLLATE pg_catalog."default",
    shcil_custodycharges character varying(100) COLLATE pg_catalog."default",
    shcil_ccilcharges character varying(100) COLLATE pg_catalog."default",
    shcil_cbricscharges character varying(100) COLLATE pg_catalog."default",
    shcil_nsdlcharges character varying(100) COLLATE pg_catalog."default",
    shcil_sebicharges character varying(100) COLLATE pg_catalog."default",
    grosspaybleshcil bigint,
    createdate date,
    createdby bigint,
    CONSTRAINT annexureva_pkey PRIMARY KEY (id_)
) WITH (oids = false);