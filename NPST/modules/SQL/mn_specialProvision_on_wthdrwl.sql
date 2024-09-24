
DROP TABLE IF EXISTS "mn_specialprovision_on_wthdrwl";
DROP SEQUENCE IF EXISTS mn_specialprovision_on_wthdrwl_id__seq1;
CREATE SEQUENCE mn_specialprovision_on_wthdrwl_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_specialprovision_on_wthdrwl" (
    "id_" integer DEFAULT nextval('mn_specialprovision_on_wthdrwl_id__seq1') NOT NULL,
    "date_" date,
    "cra" character varying(100),
    "sector" character varying(100),
    "partial_withdrawal_app_submit" numeric NOT NULL,
    "gsec_subs_exercise_family_pnsn" numeric NOT NULL,
    "subscriber_defered_lumpsum_amt" numeric NOT NULL,
    "subscriber_who_defered_annuity" numeric NOT NULL,
    "subs_optd_for_nps_post_superan" numeric NOT NULL, 
    "createdon" date,
    "createdby" bigint,
    "reportuploadlogid" bigint NULL,
  CONSTRAINT "mn_specialprovision_on_wthdrwl_id_" PRIMARY KEY ("id_")
) WITH (oids = false);
