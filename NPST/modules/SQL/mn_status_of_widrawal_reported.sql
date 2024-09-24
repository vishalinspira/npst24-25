DROP TABLE IF EXISTS "mn_status_of_widrawal_reported";
DROP SEQUENCE IF EXISTS mn_status_of_widrawal_reported_id__seq1;
CREATE SEQUENCE mn_status_of_widrawal_reported_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_status_of_widrawal_reported" (
    "id_" integer DEFAULT nextval('mn_status_of_widrawal_reported_id__seq1') NOT NULL,
    "date_" date,
    "cra" character varying(100),
    "sector" character varying(100),
    "withdrawal_type" character varying(100) ,
    "wthdrwl_pending_at_bg_of_month" numeric NOT NULL,
    "wthdrwl_received_during_month" numeric NOT NULL,
    "wthdrwl_settled_during_month" numeric NOT NULL,
    "wthdrwl_rejected_during_month" numeric NOT NULL,
    "wthdrwl_outstand_aten_of_month" numeric NOT NULL, 
    "createdon" date NOT NULL,
    "createdby" bigint,
    "reportuploadlogid" bigint NULL,
  CONSTRAINT "mn_status_of_widrawal_reported_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 18:29:33.935563+05:30
