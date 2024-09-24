-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "reportii";
DROP SEQUENCE IF EXISTS reportii_id__seq;
CREATE SEQUENCE reportii_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."reportii" (
    "id_" integer DEFAULT nextval('reportii_id__seq') NOT NULL,
    "srno" bigint,
    "foracid" character varying,
    "accname" character varying,
    "avgbalaweeki" character varying,
    "avgbalaweekii" character varying,
    "avgbalaweekiii" character varying,
    "avgbalaweekiv" character varying,
    "avgbalaweekv" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:35:41.993345+05:30
