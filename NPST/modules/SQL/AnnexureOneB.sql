DROP TABLE IF EXISTS "annexureoneb_table_one";
DROP SEQUENCE IF EXISTS annexureoneb_table_one_id__seq1;
CREATE SEQUENCE annexureoneb_table_one_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexureoneb_table_one" (
	id_ integer NOT NULL,
    "name_of_pension_fund" character varying(100) COLLATE pg_catalog."default",
    "auc_as_per_market_valuation" character varying(100) COLLATE pg_catalog."default",
    "aum" character varying(100) COLLATE pg_catalog."default",
    "aum_under_coustody" character varying(100) COLLATE pg_catalog."default",
    "asset_not_under_coustody" character varying(100) COLLATE pg_catalog."default",
    as_on_date date,
    createdate date,
    createdby integer,
    CONSTRAINT "annexureoneb_table_one_id_" PRIMARY KEY ("id_")
) WITH (oids = false);




DROP TABLE IF EXISTS "annexureoneb_table_two";
DROP SEQUENCE IF EXISTS annexureoneb_table_two_id__seq1;
CREATE SEQUENCE annexureoneb_table_two_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexureoneb_table_two" (
	id_ integer NOT NULL,
    "particulars" character varying(100) COLLATE pg_catalog."default",
    "amount_in_crores" character varying(100) COLLATE pg_catalog."default",
    as_on_date date,
    createdate date,
    createdby integer,
    CONSTRAINT "annexureoneb_table_two_id_" PRIMARY KEY ("id_")
) WITH (oids = false);
