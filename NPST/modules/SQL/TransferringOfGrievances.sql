DROP TABLE IF EXISTS "transferring_of_grievances";
DROP SEQUENCE IF EXISTS transferring_of_grievances_id__seq1;
CREATE SEQUENCE transferring_of_grievances_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."transferring_of_grievances" (
	id_ integer NOT NULL,
    "supervisory_action" character varying(100) COLLATE pg_catalog."default",
    "letter_to" character varying(100) COLLATE pg_catalog."default",
    "nodal_offices" character varying(100) COLLATE pg_catalog."default",
    "pending_as_on" character varying(100) COLLATE pg_catalog."default",
    "logged_in_till" character varying(100) COLLATE pg_catalog."default",
	escalated_month_of date,
    issuance_letter_month_of date,
    createdate date,
    createdby integer,
    CONSTRAINT "transferring_of_grievances_id_" PRIMARY KEY ("id_")
) WITH (oids = false);