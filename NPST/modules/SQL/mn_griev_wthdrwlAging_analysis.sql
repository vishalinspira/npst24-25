DROP TABLE IF EXISTS "mn_griev_wthdrwlaging_analysis";
DROP SEQUENCE IF EXISTS mn_griev_wthdrwlaging_analysis_id__seq1;
CREATE SEQUENCE mn_griev_wthdrwlaging_analysis_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_griev_wthdrwlaging_analysis" (
    "id_" integer DEFAULT nextval('mn_griev_wthdrwlaging_analysis_id__seq1') NOT NULL,
    "date_" date,
    "cra" character varying(100),
    "sno" bigint,
    "withdrawal_sector" character varying(100),
    "zero_to_therty_days" numeric NOT NULL,
    "thertyone_to_ninty_days" numeric NOT NULL,
    "more_than_oneeighty_days" numeric NOT NULL,
    "total" numeric NOT NULL,
    "createdon" date,
    "createdby" bigint,
    "reportuploadlogid" bigint NULL,
  CONSTRAINT "mn_griev_wthdrwlaging_analysis_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


