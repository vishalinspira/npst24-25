DROP TABLE IF EXISTS "mn_report_wthdrwl_aging_analys";
DROP SEQUENCE IF EXISTS mn_report_wthdrwl_aging_analys_id__seq1;
CREATE SEQUENCE mn_report_wthdrwl_aging_analys_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_report_wthdrwl_aging_analys" (
    "id_" integer DEFAULT nextval('mn_report_wthdrwl_aging_analys_id__seq1') NOT NULL,
    "date_" date,
    "cra" character varying(100),
    "withdrawal_sector" character varying(100),
    "type_" character varying(100),
    "zero_to_therty_days" numeric NOT NULL,
    "thertyone_to_ninty_days" numeric NOT NULL,
    "nintyone_to_onetwenty_days" numeric NOT NULL,
    "onetwentyone_to_oneeighty_days" numeric NOT NULL,
    "oneeighty_to_three65_days" numeric NOT NULL, 
    "more_than_one_year" numeric NOT NULL,
    "wthdrwl_outstand_ated_of_month" numeric NOT NULL, 
    "createdon" date,
    "createdby" bigint,
    "reportuploadlogid" bigint NULL,
  CONSTRAINT "mn_report_wthdrwl_aging_analys_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 18:29:33.935563+05:30
