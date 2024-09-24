DROP TABLE IF EXISTS "unanimous_one_report";
DROP SEQUENCE IF EXISTS unanimous_one_report_id__seq1;
CREATE SEQUENCE unanimous_one_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."unanimous_one_report" (
	id_ integer NOT NULL,
    "name_of_the_pfm" character varying(100) COLLATE pg_catalog."default",
    "no_of_resolutions" bigint NOT NULL,
    "voted_for" bigint NOT NULL,
    "abstained" bigint NOT NULL,
    "voted_against" bigint NOT NULL,
    createdate date,
    createdby integer,
    CONSTRAINT "unanimous_one_report_id_" PRIMARY KEY ("id_")
) WITH (oids = false);



DROP TABLE IF EXISTS "unanimous_tow_report";
DROP SEQUENCE IF EXISTS unanimous_tow_report_id__seq1;
CREATE SEQUENCE unanimous_tow_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."unanimous_tow_report" (
	id_ integer NOT NULL,
    "name_of_the_company" character varying(100) COLLATE pg_catalog."default",
    "meeting_date" date NOT NULL,
    "resolution" character varying(1000) COLLATE pg_catalog."default",
    "ses_recommendation" character varying(100) COLLATE pg_catalog."default",
    "ses_reason" character varying(1000) COLLATE pg_catalog."default",
    "pfs_voted_for" character varying(100) COLLATE pg_catalog."default",
    "pfs_voted_abstain" character varying(100) COLLATE pg_catalog."default",
    "pfs_voted_against" character varying(100) COLLATE pg_catalog."default",
    "final_vote" character varying(100) COLLATE pg_catalog."default",
    "pfs_reason" character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby integer,
    CONSTRAINT "unanimous_tow_report_id_" PRIMARY KEY ("id_")
) WITH (oids = false);
