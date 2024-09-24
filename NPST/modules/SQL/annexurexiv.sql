-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "annexurexiv";
DROP SEQUENCE IF EXISTS annexurexiv_id__seq;
CREATE SEQUENCE annexurexiv_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexurexiv" (
    "id_" integer DEFAULT nextval('annexurexiv_id__seq') NOT NULL,
    "srno" bigint,
    "branchcode" character varying,
    "dateofopening" character varying,
    "branchname" character varying,
    "address" character varying,
    "district" character varying,
    "state_" character varying,
    "centreasper" character varying,
    "weatherrelocated" character varying,
    "addressrelocation" character varying,
    "dateofrelcation" character varying,
    "uniformcodei" character varying,
    "uniformcodeii" character varying,
    "boardlinenumber" character varying,
    "faxnumber" character varying,
    "mobileno" bigint,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-04-26 19:33:47.280049+05:30
