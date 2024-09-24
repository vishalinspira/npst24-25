DROP TABLE IF EXISTS "formatc";
DROP SEQUENCE IF EXISTS formatc_id__seq1;
CREATE SEQUENCE formatc_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.formatc
(
    id_ bigint NOT NULL,
    formatc_sector character varying(100) COLLATE pg_catalog."default",
    withdrawalapplications character varying(100) COLLATE pg_catalog."default",
    purchaseofannuity character varying(100) COLLATE pg_catalog."default",
    paymenttoaspbycra character varying(100) COLLATE pg_catalog."default",
    premiumtoaspatcra character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT formatc_pkey PRIMARY KEY (id_)
) WITH (oids = false);