DROP TABLE IF EXISTS "weekly_benchmark_return";
DROP SEQUENCE IF EXISTS weekly_benchmark_return_id__seq1;
CREATE SEQUENCE weekly_benchmark_return_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."weekly_benchmark_return" (
    id_ integer NOT NULL,
    "returns" character varying(100) COLLATE pg_catalog."default",
    "base_date" date,
    "cg" character varying(100) COLLATE pg_catalog."default",
    "sg" character varying(100) COLLATE pg_catalog."default",
    "ccg" character varying(100) COLLATE pg_catalog."default",
    "lite" character varying(100) COLLATE pg_catalog."default",
    "apy" character varying(100) COLLATE pg_catalog."default",
    "e" character varying(100) COLLATE pg_catalog."default",
    "c" character varying(100) COLLATE pg_catalog."default",
    "g" character varying(100) COLLATE pg_catalog."default",
    "e2" character varying(100) COLLATE pg_catalog."default",
    "c2" character varying(100) COLLATE pg_catalog."default",
    "g2" character varying(100) COLLATE pg_catalog."default",
	"tax_saver"character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby integer,
    CONSTRAINT "weekly_benchmark_return_id_" PRIMARY KEY ("id_")
) WITH (oids = false);
