DROP TABLE IF EXISTS "form_one_report";
DROP SEQUENCE IF EXISTS form_one_report_id__seq1;
CREATE SEQUENCE form_one_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."form_one_report" (
    "id_" integer DEFAULT nextval('form_one_report_id__seq1') NOT NULL,
    "purchase_sale" character varying(100) COLLATE pg_catalog."default",
    "category_of_investment" character varying(100) COLLATE pg_catalog."default",
    "name_of_company" character varying(100) COLLATE pg_catalog."default",
    "book_value_rs" character varying(100) COLLATE pg_catalog."default",
    "percentage_of_portfolio_value" character varying(1000) COLLATE pg_catalog."default",
    "date_of_purchase_sale" VARCHAR(100) COLLATE pg_catalog."default",
    "createdby" bigint,
    "createdate" date,
   
    CONSTRAINT "form_one_report_id_" PRIMARY KEY ("id_")
) WITH (oids = false);