DROP TABLE IF EXISTS "annexure_two_b";
DROP SEQUENCE IF EXISTS annexure_two_b_id__seq1;
CREATE SEQUENCE annexure_two_b_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexure_two_b" (
	id_ integer NOT NULL,
    "file_number" character varying(100) COLLATE pg_catalog."default",
    "to_name" character varying(100) COLLATE pg_catalog."default",
    "to_address" character varying(100) COLLATE pg_catalog."default",
    "ended_date" character varying(100) COLLATE pg_catalog."default",
    "by_the_custodian" character varying(100) COLLATE pg_catalog."default",
    "quarter" character varying(100) COLLATE pg_catalog."default",
    
--    table data
	"remarks_one" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_one" character varying(1000) COLLATE pg_catalog."default",
	"remarks_two" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_two" character varying(1000) COLLATE pg_catalog."default",
	"remarks_three" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_three" character varying(1000) COLLATE pg_catalog."default",
	"remarks_four" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_four" character varying(1000) COLLATE pg_catalog."default",
	"remarks_five" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_five" character varying(1000) COLLATE pg_catalog."default",
	"remarks_six" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_six" character varying(1000) COLLATE pg_catalog."default",
	"remarks_seven" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_seven" character varying(1000) COLLATE pg_catalog."default",
	"remarks_eight" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_eight" character varying(1000) COLLATE pg_catalog."default",
	"remarks_nine" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_nine" character varying(1000) COLLATE pg_catalog."default",
	"remarks_ten" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_ten" character varying(1000) COLLATE pg_catalog."default",
	"remarks_eleven" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_eleven" character varying(1000) COLLATE pg_catalog."default",
	"remarks_twelve" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_twelve" character varying(1000) COLLATE pg_catalog."default",
	"remarks_thirteen" character varying(1000) COLLATE pg_catalog."default",
	"nps_trust_observation_thirteen" character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby integer,
    CONSTRAINT "annexure_two_b_id_" PRIMARY KEY ("id_")
) WITH (oids = false);