DROP TABLE IF EXISTS "quarterlyintervali";
DROP SEQUENCE IF EXISTS quarterlyintervali_id__seq1;
CREATE SEQUENCE quarterlyintervali_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.quarterlyintervali
(
    id_ bigint NOT NULL,
    quatertable_name character varying(100) COLLATE pg_catalog."default",
    nameofdirector character varying(100) COLLATE pg_catalog."default",
    dignation character varying(100) COLLATE pg_catalog."default",
    indepent character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT quarterlyintervali_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);