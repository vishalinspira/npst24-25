

DROP TABLE IF EXISTS "mn_benchmark_return";
CREATE TABLE "public"."mn_benchmark_return" (
    "id_" bigint NOT NULL,
    "returns" character varying(40),
	"return_as_on_date" date,
	"base_date" date,
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
	"tax_saver"character varying(100),
	reportuploadlogid bigint,
    "createdate" date,
    "createdby" bigint
) WITH (oids = false);


-- 2022-08-04 06:15:20.171077+00
