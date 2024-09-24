-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexureivc";
DROP SEQUENCE IF EXISTS annexureivc_id__seq;
CREATE SEQUENCE annexureivc_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexureivc" (
    "id_" integer DEFAULT nextval('annexureivc_id__seq') NOT NULL,
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


-- 2022-04-26 19:33:36.553592+05:30
