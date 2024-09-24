-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annextena";
DROP SEQUENCE IF EXISTS annextena_id__seq;
CREATE SEQUENCE annextena_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annextena" (
    "id_" integer DEFAULT nextval('annextena_id__seq') NOT NULL,
    "srnoti" bigint,
    "particularsti" character varying,
    "amountti" character varying,
    "particularstii" character varying,
    "noofremeritii" character varying,
    "amounttii" character varying,
    "srnotiii" character varying,
    "reasoniii" character varying,
    "noofremeritiii" character varying,
    "periodiv" character varying,
    "noofremeritiv" character varying,
    "amounttiv" character varying,
    "periodv" character varying,
    "noofremeritv" character varying,
    "amounttv" character varying,
    "notes" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:32:33.859361+05:30
