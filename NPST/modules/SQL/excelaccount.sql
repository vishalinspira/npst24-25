-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "excelaccount";
DROP SEQUENCE IF EXISTS excelaccountstatement_id__seq;
CREATE SEQUENCE excelaccountstatement_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."excelaccount" (
    "id_" integer DEFAULT nextval('excelaccountstatement_id__seq') NOT NULL,
    "customerid" bigint NOT NULL,
    "foracid" bigint NOT NULL,
    "acname" character varying NOT NULL,
    "openingbalance" character varying NOT NULL,
    "totalcredits" character varying NOT NULL,
    "totaldebit" character varying NOT NULL,
    "closeingbalance" character varying,
    "createdate" date NOT NULL,
    "createdby" bigint NOT NULL
) WITH (oids = false);


-- 2022-04-26 19:34:28.829115+05:30
