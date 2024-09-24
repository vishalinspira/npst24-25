DROP TABLE IF EXISTS "annexure_a_proxy_advisors";
DROP SEQUENCE IF EXISTS annexure_a_proxy_advisors_id__seq1;
CREATE SEQUENCE annexure_a_proxy_advisors_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."annexure_a_proxy_advisors" (
    "id_" integer DEFAULT nextval('annexure_a_proxy_advisors_id__seq1') NOT NULL,
    "name_of_company" character varying(100) COLLATE pg_catalog."default",
    "resolution_category" character varying(500) COLLATE pg_catalog."default",
    "resolution_detail" character varying(500) COLLATE pg_catalog."default",
    "nps_holding" character varying(100) COLLATE pg_catalog."default",
    "lic_pf" character varying(1000) COLLATE pg_catalog."default",
    "uti_pf" VARCHAR(1000) COLLATE pg_catalog."default",
    "sbi_pf" VARCHAR(1000) COLLATE pg_catalog."default",
    "hdfc_pf" character varying(1000) COLLATE pg_catalog."default",
    "icici_pf" character varying(1000) COLLATE pg_catalog."default",
    "kotak_pf" character varying(1000) COLLATE pg_catalog."default",
    "birla_pf" VARCHAR(1000) COLLATE pg_catalog."default",
    "pfm_majority_view" character varying(100) COLLATE pg_catalog."default",
    "ses_recommendation" character varying(100) COLLATE pg_catalog."default",
    "ses_rationale" VARCHAR(1000) COLLATE pg_catalog."default",
    "iias_recommendation" character varying(100) COLLATE pg_catalog."default",
    "iias_rationale" VARCHAR(1000) COLLATE pg_catalog."default",
    "institutional_holding" character varying(100) COLLATE pg_catalog."default",
    "institutional_majority_vote" character varying(100) COLLATE pg_catalog."default",
    "promoter_holding" character varying(100) COLLATE pg_catalog."default",
    "outcome" character varying(100) COLLATE pg_catalog."default",
    "createdby" bigint,
    "createdate" date,
   
    CONSTRAINT "annexure_a_proxy_advisors_id_" PRIMARY KEY ("id_")
) WITH (oids = false);