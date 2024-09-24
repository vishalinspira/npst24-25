DROP TABLE IF EXISTS "benchreturnssince_monthly";
DROP SEQUENCE IF EXISTS benchreturnssince_monthly_id__seq1;
CREATE SEQUENCE benchreturnssince_monthly_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.benchreturnssince_monthly (
    id_ bigint NOT NULL,
    "returns_since" character varying,
    "return_as_on_date" character varying,
    "cg" character varying,
    "sg" character varying,
    "ccg" character varying,
    "lite" character varying,
    "apy" character varying,
    "e" character varying,
    "c" character varying,
    "g" character varying,
    "e2" character varying,
    "c2" character varying,
    "g2" character varying,
    "createdate" date,
    "createdby" integer,
	reportuploadlogid bigint
     CONSTRAINT "benchreturnssince_monthly_id_" PRIMARY KEY ("id_")
)WITH (oids = false);