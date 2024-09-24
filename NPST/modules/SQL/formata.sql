DROP TABLE IF EXISTS "formata";
DROP SEQUENCE IF EXISTS formata_id__seq1;
CREATE SEQUENCE formata_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.formata
(
    formatiaisector character varying(100) COLLATE pg_catalog."default",
    formatiawithdrawaltype character varying(100) COLLATE pg_catalog."default",
    formatiapendingbeginning character varying(100) COLLATE pg_catalog."default",
    formatiaclaimsreceivedduring character varying(100) COLLATE pg_catalog."default",
    formatiaclaimsauthorisedduring character varying(100) COLLATE pg_catalog."default",
    formatiaclaimsrejectedduring character varying(100) COLLATE pg_catalog."default",
    formatiaclaimsoutstanding character varying(100) COLLATE pg_catalog."default",
    formataiclaimssettledduring character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    id_ bigint NOT NULL,
    CONSTRAINT formata_pkey PRIMARY KEY (id_)
) WITH (oids = false);