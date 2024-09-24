DROP TABLE IF EXISTS "internalauditreportii";
DROP SEQUENCE IF EXISTS internalauditreportii_id__seq1;
CREATE SEQUENCE internalauditreportii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internalauditreportii
(
   id_ bigint NOT NULL,
    annexuremonthend character varying(1000) COLLATE pg_catalog."default",
    monthdatei date,
    monthdateii date,
    monthdateiii date,
    scheme character varying(1000) COLLATE pg_catalog."default",
    issuer character varying(1000) COLLATE pg_catalog."default",
    classification character varying(1000) COLLATE pg_catalog."default",
    marketvalue bigint,
    maxpermissable bigint,
    deviation bigint,
    percentagedeviation bigint,
    createdate date,
    createdby bigint,
    CONSTRAINT internalauditreportii_pkey PRIMARY KEY (id_)
)
WITH (oids = false);