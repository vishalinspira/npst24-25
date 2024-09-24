DROP TABLE IF EXISTS "annexurevb";
DROP SEQUENCE IF EXISTS annexurevb_id__seq1;
CREATE SEQUENCE annexurevb_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.annexurevb
(
    id_ bigint NOT NULL,
    month character varying(100) COLLATE pg_catalog."default",
    billrefno character varying(100) COLLATE pg_catalog."default",
    amount character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT annexurevb_pkey PRIMARY KEY (id_)
) WITH (oids = false);