-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "dailyclosingx";
DROP SEQUENCE IF EXISTS dailyclosingx_id__seq;
CREATE SEQUENCE dailyclosingx_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."dailyclosingx" (
    "id_" integer DEFAULT nextval('dailyclosingx_id__seq') NOT NULL,
    "foracidi" character varying,
    "cust_id" character varying,
    "cra" character varying,
    "foracidii" character varying,
    "acname" character varying,
    "octi" character varying,
    "octii" character varying,
    "octiii" character varying,
    "avgbali" character varying,
    "octiv" character varying,
    "octv" character varying,
    "octvi" character varying,
    "octvii" character varying,
    "octviii" character varying,
    "octix" character varying,
    "octx" character varying,
    "avgbalii" character varying,
    "octxi" character varying,
    "octxii" character varying,
    "octxiii" character varying,
    "octxiv" character varying,
    "octxv" character varying,
    "octxvi" character varying,
    "octxvii" character varying,
    "avgbaliii" character varying,
    "octxviii" character varying,
    "octxix" character varying,
    "octxx" character varying,
    "octxxi" character varying,
    "octxxii" character varying,
    "octxxiii" character varying,
    "octxxiv" character varying,
    "avgbaliv" character varying,
    "octxxv" character varying,
    "octxxvi" character varying,
    "octxxvii" character varying,
    "octxxviii" character varying,
    "octxxix" character varying,
    "octxxx" character varying,
    "octxxxi" character varying,
    "avgbalv" character varying,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:34:23.255843+05:30
