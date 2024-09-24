DROP TABLE IF EXISTS "mn_benchmark_return";
DROP SEQUENCE IF EXISTS mn_benchmark_return_id__seq1;
CREATE SEQUENCE mn_benchmark_return_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_benchmark_return" (
    id_ bigint NOT NULL,
    "returns" character varying(40) ,
    "cg" character varying(40) ,
    "sg" character varying(40) ,
    "ccg" character varying(40) ,
    "lite" character varying(40) ,
    "apy" character varying(40) ,
    "e" character varying(40) ,
    "c" character varying(40) ,
    "g" character varying(40) ,
    "e2" character varying(40) ,
    "c2" character varying(40) ,
    "g2" character varying(40) ,
    createdate date,
    createdby bigint
) WITH (oids = false);
