DROP TABLE IF EXISTS "formatiia";
DROP SEQUENCE IF EXISTS formatiia_id__seq1;
CREATE SEQUENCE formatiia_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.formatiia
(
    id_ bigint NOT NULL,
    withdrawalsector character varying(100) COLLATE pg_catalog."default",
    format_type character varying(100) COLLATE pg_catalog."default",
    zerotothirty character varying(100) COLLATE pg_catalog."default",
    thirtyonetoninety character varying(100) COLLATE pg_catalog."default",
    formatninetyonetoonetenty character varying(100) COLLATE pg_catalog."default",
    formatonetooneeighty character varying(100) COLLATE pg_catalog."default",
    morethanthreesixtyfive character varying(100) COLLATE pg_catalog."default",
    morethenoneyear character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT formatiia_pkey PRIMARY KEY (id_)
) WITH (oids = false);