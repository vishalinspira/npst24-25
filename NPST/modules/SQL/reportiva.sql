-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "reportiva";
DROP SEQUENCE IF EXISTS reportiva_id__seq;
CREATE SEQUENCE reportiva_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."reportiva" (
    "id_" integer DEFAULT nextval('reportiva_id__seq') NOT NULL,
    "timelinesa" character varying,
    "noofinstancea" character varying,
    "amounta" character varying,
    "timelinesb" character varying,
    "noofinstanceb" character varying,
    "amountb" character varying,
    "timelinesc" character varying,
    "noofinstancec" character varying,
    "amountc" character varying,
    "timelinesd" character varying,
    "noofinstanced" character varying,
    "amountd" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:35:49.100289+05:30
