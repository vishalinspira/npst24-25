DROP TABLE IF EXISTS "weekly_benchmark_return_date";
DROP SEQUENCE IF EXISTS weekly_benchmark_return_date_id__seq1;
CREATE SEQUENCE weekly_benchmark_return_date_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.weekly_benchmark_return_date (
    id_ integer NOT NULL,
    "returns_since" character varying(100),
    "return_as_on_date" date,
    "cg" character varying(100),
    "sg" character varying(100),
    "ccg" character varying(100),
    "lite" character varying(100),
    "apy" character varying(100),
    "e" character varying(100),
    "c" character varying(100),
    "g" character varying(100),
    "e2" character varying(100),
    "c2" character varying(100),
    "g2" character varying(100),
	"tax_saver"character varying(100),
    "createdate" date,
	reportUploadlogid bigint,
    "createdby" bigint,
     CONSTRAINT "weekly_benchmark_return_date_id_" PRIMARY KEY ("id_")
)WITH (oids = false);