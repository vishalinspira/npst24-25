DROP TABLE IF EXISTS "mn_subs_annuity_purchase";
DROP SEQUENCE IF EXISTS mn_subs_annuity_purchase_id__seq1;
CREATE SEQUENCE mn_subs_annuity_purchase_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_subs_annuity_purchase" (
    "id_" integer DEFAULT nextval('mn_subs_annuity_purchase_id__seq1') NOT NULL,
    "date_" date,
    "cra" character varying(100),
    "sector" character varying(100),
    "subs_submitted_wthdrwl_appl" character varying(100) NOT NULL,
    "subs_opted_for_anuity_purchas" numeric NOT NULL,
    "asp_purchased_annuity_by_cra" numeric NOT NULL,
    "asp_wthdrwl_prem_paymnt_at_cra" numeric NOT NULL,
    "createdon" date,
    "createdby" bigint,
    "reportuploadlogid" bigint NULL,
  CONSTRAINT "mn_subs_annuity_purchase_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


-- 2022-04-26 18:29:33.935563+05:30
