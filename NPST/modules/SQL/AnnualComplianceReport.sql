DROP TABLE IF EXISTS "annual_compliance_report";
DROP SEQUENCE IF EXISTS annual_compliance_report_id__seq1;
CREATE SEQUENCE annual_compliance_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annual_compliance_report" (
	id_ integer NOT NULL,
    "annual_compliance_report_of" character varying(100) COLLATE pg_catalog."default",
    "details" character varying(1000) COLLATE pg_catalog."default",
    "information_submitted" character varying(1000) COLLATE pg_catalog."default",
    "comments" character varying(1000) COLLATE pg_catalog."default",
    createdate date,
    createdby integer,
    CONSTRAINT "annual_compliance_report_id_" PRIMARY KEY ("id_")
) WITH (oids = false);
