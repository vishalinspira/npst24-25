DROP TABLE IF EXISTS "weekly_outstand_with_truste";
DROP SEQUENCE IF EXISTS weekly_outstand_with_truste_id__seq1;
CREATE SEQUENCE weekly_outstand_with_truste_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."weekly_outstand_with_truste" (
	id_ integer NOT NULL,
    "nps_acc_number" integer NOT NULL,
    "nps_acc_name" character varying,
    "date1" date NOT NULL,
    "date2" date NOT NULL,
    "funds_received_upto_date1" numeric,
    "funds_received_upto_date2" numeric,
    "funds_transfer_on_date1" numeric,
    "funds_transfer_on_date2" numeric,
    "trustee_bank_outstanding" numeric,
    "trustee_bank_comments" character varying,
    createdate date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT "weekly_outstand_with_truste_id_" PRIMARY KEY ("id_")
) WITH (oids = false);
