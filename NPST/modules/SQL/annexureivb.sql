-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexureivb";
DROP SEQUENCE IF EXISTS annexureivb_id__seq;
CREATE SEQUENCE annexureivb_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexureivb" (
    "id_" integer DEFAULT nextval('annexureivb_id__seq') NOT NULL,
    "srno" bigint,
    "paymentid" character varying,
    "accno" character varying,
    "ifscaccno" character varying,
    "utrdate" character varying,
    "utrno" character varying,
    "mode_" character varying,
    "utramount" character varying,
    "npsaccno" character varying,
    "npsaccname" character varying,
    "returndate" character varying,
    "returnutr" character varying,
    "tid" character varying,
    "reasonofreturn" character varying,
    "delayreason" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:33:23.538711+05:30
