-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexureivd";
DROP SEQUENCE IF EXISTS annexureivd_id__seq;
CREATE SEQUENCE annexureivd_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexureivd" (
    "id_" integer DEFAULT nextval('annexureivd_id__seq') NOT NULL,
    "srno" bigint,
    "stdpran" character varying,
    "type_" character varying,
    "pranname" character varying,
    "beneficiaryname" character varying,
    "netamount" character varying,
    "payindate" character varying,
    "executiondate" character varying,
    "utrno" character varying,
    "initialstatus" character varying,
    "returnreason" character varying,
    "comment_" character varying,
    "delay" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:33:41.337556+05:30
