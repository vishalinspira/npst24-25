-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "reportvi";
DROP SEQUENCE IF EXISTS reportvi_id__seq;
CREATE SEQUENCE reportvi_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."reportvi" (
    "id_" integer DEFAULT nextval('reportvi_id__seq') NOT NULL,
    "srno" bigint,
    "raisedforentity" character varying,
    "tokenno" character varying,
    "logdate" character varying,
    "resolutiondate" character varying,
    "particulars" character varying,
    "tat" character varying,
    "salutionprovided" character varying,
    "remarks" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:35:59.977225+05:30
