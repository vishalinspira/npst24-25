-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexureiva";
DROP SEQUENCE IF EXISTS annexureiva_id__seq;
CREATE SEQUENCE annexureiva_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexureiva" (
    "id_" integer DEFAULT nextval('annexureiva_id__seq') NOT NULL,
    "srno" bigint,
    "paoregno" character varying,
    "transactionid" character varying,
    "amount" character varying,
    "filereferno" character varying,
    "recordno" character varying,
    "receiptdate" character varying,
    "fundrealisedate" character varying,
    "frcuploaddate" character varying,
    "trusteebanktat" character varying,
    "matchingtype" character varying,
    "days" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:33:15.580321+05:30
