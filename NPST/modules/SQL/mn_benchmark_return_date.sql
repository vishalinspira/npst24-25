-- Adminer 4.8.1 PostgreSQL 9.5.25 dump

DROP TABLE IF EXISTS "mn_benchmark_return_date";
CREATE TABLE "public"."mn_benchmark_return_date" (
    "id_" bigint NOT NULL,
    "returns_since" date,
	"return_as_on_date" date,
    "cg" character varying(40),
    "sg" character varying(40),
    "ccg" character varying(40),
    "lite" character varying(40),
    "apy" character varying(40),
    "e" character varying(40),
    "c" character varying(40),
    "g" character varying(40),
    "e2" character varying(40),
    "c2" character varying(40),
    "g2" character varying(40),
	"tax_saver" character varying,
    "createdate" date,
    reportUploadLogId bigint,
    "createdby" bigint,
    CONSTRAINT "mn_benchmark_return_date_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-08-04 06:19:43.304462+00
