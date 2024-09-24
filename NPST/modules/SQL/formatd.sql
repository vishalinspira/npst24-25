DROP TABLE IF EXISTS "formatd";
DROP SEQUENCE IF EXISTS formatd_id__seq1;
CREATE SEQUENCE formatd_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.formatd
(
    id_ bigint NOT NULL,
    grievancestatisticsforcgms character varying COLLATE pg_catalog."default",
    formatnumber character varying COLLATE pg_catalog."default",
    remarksifany character varying COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT formatd_pkey PRIMARY KEY (id_)
) WITH (oids = false);