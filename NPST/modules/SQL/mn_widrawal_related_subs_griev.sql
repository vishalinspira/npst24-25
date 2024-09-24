DROP TABLE IF EXISTS "mn_widrawal_related_subs_griev";
DROP SEQUENCE IF EXISTS mn_widrawal_related_subs_griev_id__seq1;
CREATE SEQUENCE mn_widrawal_related_subs_griev_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_widrawal_related_subs_griev" (
    "id_" integer DEFAULT nextval('mn_widrawal_related_subs_griev_id__seq1') NOT NULL,
    "date_" date,
    "cra" character varying(100),
    "grievance_statistics_for_cgms" character varying(100) NOT NULL,
    "grievances_count" numeric NOT NULL,
    "remarks" character varying(100) NOT NULL,
    "createdon" date,
    "createdby" bigint,
    "reportuploadlogid" bigint NULL,
  CONSTRAINT "mn_widrawal_related_subs_griev_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 18:29:33.935563+05:30
