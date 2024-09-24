DROP TABLE IF EXISTS "mn_nps_final_summary";
DROP SEQUENCE IF EXISTS mn_nps_final_summary_id__seq1;
CREATE SEQUENCE mn_nps_final_summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_nps_final_summary" (
    "id_" integer DEFAULT nextval('mn_nps_final_summary_id__seq1') NOT NULL,
    "sr_no" integer NOT NULL,
    "date_" date,
    "pension_fund_name" character varying(100),
    "auc_fv_debt_and_pur_price_eq" numeric ,
    "auc_as_per_market_valuation" numeric ,
    "aum" numeric ,
    "prcnt_of_aum_under_custody" numeric ,
    "asset_not_under_custody" numeric ,
    "createdon" date ,
    "createdby" bigint,
    "reportuploadlogid" bigint,
  CONSTRAINT "mn_nps_final_summary_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 18:29:33.935563+05:30
