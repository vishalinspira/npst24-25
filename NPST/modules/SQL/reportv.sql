-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "reportv";
DROP SEQUENCE IF EXISTS reportv_id__seq;
CREATE SEQUENCE reportv_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."reportv" (
    "id_" integer DEFAULT nextval('reportv_id__seq') NOT NULL,
    "srno" bigint,
    "nameofac" character varying,
    "amount" character varying,
    "delayindays" character varying,
    "compensation" character varying,
    "remarks" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:35:54.96366+05:30
