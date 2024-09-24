DROP TABLE IF EXISTS "internalauditreportv";
DROP SEQUENCE IF EXISTS internalauditreportv_id__seq1;
CREATE SEQUENCE internalauditreportv_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internalauditreportv
(
   id_ bigint NOT NULL,
    statusofnonperforming character varying(1000) COLLATE pg_catalog."default",
    assetsdate date,
    nameofcompany character varying(1000) COLLATE pg_catalog."default",
    statusasonjun character varying(1000) COLLATE pg_catalog."default",
    statusasonsep character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT internalauditreportv_pkey PRIMARY KEY (id_)
)
WITH (oids = false);