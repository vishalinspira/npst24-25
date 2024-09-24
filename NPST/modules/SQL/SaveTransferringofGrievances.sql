DROP TABLE IF EXISTS "grievances_handler";
DROP SEQUENCE IF EXISTS grievances_handler_id__seq1;
CREATE SEQUENCE grievances_handler_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."grievances_handler" (
	id_ integer NOT NULL,
    "formnumber" integer NOT NULL,
    "filenumber" character varying(100) COLLATE pg_catalog."default",
    "name" character varying(100) COLLATE pg_catalog."default",
    "address" character varying(100) COLLATE pg_catalog."default",
    "npstrustmonthof" character varying(100) COLLATE pg_catalog."default",
    "referrals" character varying(100) COLLATE pg_catalog."default",
    "openingbalance" character varying(100) COLLATE pg_catalog."default",
    "escalatedtonpst" character varying(100) COLLATE pg_catalog."default",
    "receivedduringthemonth" character varying(100) COLLATE pg_catalog."default",
    "reviewed" character varying(100) COLLATE pg_catalog."default",
    "resolvedduringthemonth" character varying(100) COLLATE pg_catalog."default",
    "outstandingeom" character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby integer,
    CONSTRAINT "grievances_handler_id_" PRIMARY KEY ("id_")
) WITH (oids = false);