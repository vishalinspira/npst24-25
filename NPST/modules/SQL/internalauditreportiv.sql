DROP TABLE IF EXISTS "internalauditreportiv";
DROP SEQUENCE IF EXISTS internalauditreportiv_id__seq1;
CREATE SEQUENCE internalauditreportiv_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internalauditreportiv
(
  id_ bigint NOT NULL,
    detailsofaveragebank character varying(1000) COLLATE pg_catalog."default",
    quarterdate date,
    schemeiv character varying(1000) COLLATE pg_catalog."default",
    july bigint,
    august bigint,
    september bigint,
    createdate date,
    createdby bigint,
    CONSTRAINT internalauditreportiv_pkey PRIMARY KEY (id_)
)
WITH (oids = false);