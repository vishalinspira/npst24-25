-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexvarejection";
DROP SEQUENCE IF EXISTS annexvarejection_id__seq;
CREATE SEQUENCE annexvarejection_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexvarejection" (
    "id_" integer DEFAULT nextval('annexvarejection_id__seq') NOT NULL,
    "valuedate" character varying(100),
    "transactiondate" character varying(100),
    "paymentid" character varying(100),
    "returnreference" character varying(100),
    "messagetype" character varying(100),
    "sendername" character varying(100),
    "utrnumber" character varying(100),
    "senderifsc" character varying(100),
    "senderacnumber" character varying(100),
    "beneficiaryifsc" character varying(100),
    "beneficiaryacnumber" character varying(100),
    "creditacnumber" character varying(100),
    "corporatecode" character varying(100),
    "beneficiaryacname" character varying(100),
    "senderinformation" character varying(100),
    "amount" character varying(100),
    "beneficiaryactype" character varying(100),
    "paymentstatus" character varying(100),
    "sendertoreceverinfo" character varying(100),
    "popregno" character varying(100),
    "popspregno" character varying(100),
    "fillerrefi" character varying(100),
    "fillerrefii" character varying(100),
    "fillerrefiii" character varying(100),
    "fillerrefiv" character varying(100),
    "comments" character varying(100),
    "craid" character varying(100),
    "returnreasone" character varying(100),
    "cra" character varying(10),
    "createdate" date NOT NULL,
    "createdby" bigint NOT NULL,
    "returndate" character varying
) WITH (oids = false);


-- 2022-04-26 19:33:52.007948+05:30
