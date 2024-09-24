-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "reporti";
DROP SEQUENCE IF EXISTS reporti_id__seq;
CREATE SEQUENCE reporti_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."reporti" (
    "id_" integer DEFAULT nextval('reporti_id__seq') NOT NULL,
    "noofacceptedtran" bigint,
    "noofrejectedtran" character varying,
    "totaltran" character varying,
    "rejectionage" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:35:35.635596+05:30
