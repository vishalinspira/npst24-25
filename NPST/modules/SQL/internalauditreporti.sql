DROP TABLE IF EXISTS "internalauditreporti";
DROP SEQUENCE IF EXISTS internalauditreporti_id__seq1;
CREATE SEQUENCE internalauditreporti_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.internalauditreporti
(
    id_ bigint NOT NULL,
    tablename_annexure character varying(1000) COLLATE pg_catalog."default",
    bordmeetingdate date,
    borddate date,
    committeemeeting date,
    riskmeeting date,
    nameofdirectors character varying(1000) COLLATE pg_catalog."default",
    designation character varying(1000) COLLATE pg_catalog."default",
    attendance character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby bigint,
    CONSTRAINT internalauditreporti_pkey PRIMARY KEY (id_)
)
WITH (oids = false);