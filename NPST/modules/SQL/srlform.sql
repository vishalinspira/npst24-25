
DROP TABLE IF EXISTS "srlform";
DROP SEQUENCE IF EXISTS srlform_id_seq1;
CREATE SEQUENCE srlform_id_seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."srlform" (
    "id_" integer DEFAULT nextval('srlform_id_seq1') NOT NULL,
    "one" character varying(100),
    "time_1" timestamp,
    "remarks_1" character varying(100),
    "two" character varying(100),
    "time_2" timestamp,
    "remarks_2" character varying(100),
    "three" character varying(100),
    "time_3" timestamp,
    "remarks_3" character varying(100),
    "four" character varying(100),
    "time_4" timestamp,
    "remarks_4" character varying(100),
    "five" character varying(100),
    "time_5" timestamp,
    "remarks_5" character varying(100),
    "six" character varying(100),
    "time_6" timestamp,
    "remarks_6" character varying(100),
    "seven" character varying(100),
    "time_7" timestamp,
    "remarks_7" character varying(100),
    "eight" character varying(100),
    "time_8" timestamp,
    "remarks_8" character varying(100),
    "createdby" bigint,
    "createdate" date,
    CONSTRAINT "srlform_pkey" PRIMARY KEY ("id_")
) WITH (oids = false);