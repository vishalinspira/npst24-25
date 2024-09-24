DROP TABLE IF EXISTS "formatb";
DROP SEQUENCE IF EXISTS formatb_id__seq1;
CREATE SEQUENCE formatb_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.formatb
(
    id_ bigint NOT NULL,
    formatb_sector character varying(100) COLLATE pg_catalog."default",
    partialwithdrawal character varying(100) COLLATE pg_catalog."default",
    surrenderofnpsbenefits character varying(100) COLLATE pg_catalog."default",
    eligiblelumpsumwithdrawal character varying(100) COLLATE pg_catalog."default",
    purchaseofannuity character varying(100) COLLATE pg_catalog."default",
    allcitizenes character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT formatb_pkey PRIMARY KEY (id_)
) WITH (oids = false);