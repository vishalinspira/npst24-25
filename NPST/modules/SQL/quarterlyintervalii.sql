DROP TABLE IF EXISTS "quarterlyintervalii";
DROP SEQUENCE IF EXISTS quarterlyintervalii_id__seq1;
CREATE SEQUENCE quarterlyintervalii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.quarterlyintervalii
(
    id_ bigint NOT NULL,
    quatertablename character varying(100) COLLATE pg_catalog."default",
    noofmeetingsheld character varying(100) COLLATE pg_catalog."default",
    dateofmeetings date,
    noofattendmeeting character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT quarterlyintervalii_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);