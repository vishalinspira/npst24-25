-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "dailyaverage";
DROP SEQUENCE IF EXISTS dailyaverage_id__seq;
CREATE SEQUENCE dailyaverage_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."dailyaverage" (
    "id_" integer DEFAULT nextval('dailyaverage_id__seq') NOT NULL,
    "customerid" bigint,
    "npsacnumber" bigint,
    "npsacname" character varying(100) NOT NULL,
    "trandate" character varying(100),
    "tranid" character varying(100),
    "parttransrno" bigint,
    "chqno" bigint,
    "chqdate" character varying(100),
    "drtxn" character varying(100),
    "crtxn" character varying(100),
    "runningbalance" character varying(100),
    "trannarpart" character varying(100),
    "transactionremarks" character varying(100),
    "initiatingsolid" bigint,
    "counterpartyacnumber" bigint,
    "createdate" date NOT NULL,
    "createdby" bigint,
    CONSTRAINT "dailyaverage_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 19:34:17.872636+05:30
