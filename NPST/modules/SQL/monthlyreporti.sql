DROP TABLE IF EXISTS "monthlyreporti";
DROP SEQUENCE IF EXISTS monthlyreporti_id__seq1;
CREATE SEQUENCE monthlyreporti_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.monthlyreporti
(
    id_ bigint NOT NULL,
    entities character varying(100) COLLATE pg_catalog."default",
    osmonth character varying(100) COLLATE pg_catalog."default",
    npstob character varying(100) COLLATE pg_catalog."default",
    rrduringmonth character varying(100) COLLATE pg_catalog."default",
    rsnpst character varying(100) COLLATE pg_catalog."default",
    rrmonth character varying(100) COLLATE pg_catalog."default",
    osendofmonth character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT monthlyreporti_pkey PRIMARY KEY (id_)
) WITH (oids = false);