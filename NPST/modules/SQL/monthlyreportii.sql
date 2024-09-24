DROP TABLE IF EXISTS "monthlyreportii";
DROP SEQUENCE IF EXISTS monthlyreportii_id__seq1;
CREATE SEQUENCE monthlyreportii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.monthlyreportii
(
    sec_entities character varying(100) COLLATE pg_catalog."default",
    osendingmonth character varying(100) COLLATE pg_catalog."default",
    zerotoseven character varying(100) COLLATE pg_catalog."default",
    fifteen_aug character varying(100) COLLATE pg_catalog."default",
    sixteentothirtyone character varying(100) COLLATE pg_catalog."default",
    thirtytwotoninety character varying(100) COLLATE pg_catalog."default",
    ninetyonetooneeighty character varying(100) COLLATE pg_catalog."default",
    oneeightyonetothreesixtyfive character varying(100) COLLATE pg_catalog."default",
    threesixtysixandabove character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    id_ bigint NOT NULL,
    CONSTRAINT monthlyreportii_pkey PRIMARY KEY (id_)
) WITH (oids = false);