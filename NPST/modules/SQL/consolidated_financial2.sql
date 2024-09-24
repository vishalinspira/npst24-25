DROP TABLE IF EXISTS "consolidated_financial2";
DROP SEQUENCE IF EXISTS consolidated_financial2_id__seq1;
CREATE SEQUENCE consolidated_financial2_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."consolidated_financial2" (
    "id_" bigint NOT NULL,
    "sheet_name" character varying ,
    "as_on_date" character varying ,
    "pension_fund_name" character varying ,
    "scheme_name" character varying ,
    "sl_no" character varying ,
    "particulars" character varying,
    "current_year" character varying ,
    "previous_year" character varying,
    "createdate" timestamp,
    "createdby" bigint,
    "reportuploadlogid" bigint,
    CONSTRAINT "consolidated_financial2_id_" PRIMARY KEY ("id_")
) WITH (oids = false);