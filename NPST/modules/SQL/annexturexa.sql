-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexturexa";
DROP SEQUENCE IF EXISTS annexturexa_id__seq;
CREATE SEQUENCE annexturexa_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexturexa" (
    "id_" integer DEFAULT nextval('annexturexa_id__seq') NOT NULL,
    "srno" bigint,
    "receiptdate" character varying,
    "from_" character varying,
    "subject" character varying,
    "gistofthecase" character varying,
    "revertcontaint" character varying,
    "resolveddate" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:32:38.192421+05:30
