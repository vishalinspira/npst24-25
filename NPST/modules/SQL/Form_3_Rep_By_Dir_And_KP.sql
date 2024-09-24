DROP TABLE IF EXISTS "form_3_rep_by_dir_and_kp";
DROP SEQUENCE IF EXISTS form_3_rep_by_dir_and_kp;
CREATE SEQUENCE form_3_rep_by_dir_and_kp_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.form_3_rep_by_dir_and_kp (
    id_ bigint NOT NULL,
	date_ date,
	pension_fund_name character varying,
    sno numeric,
    type_purchase_or_sale character varying,
    name_of_security character varying,
    isin character varying,
    name_of_issuer_or_company character varying,
    transaction_cost numeric,
    date_of_transaction date,
    name_ character varying,
    designation character varying,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT form_3_rep_by_dir_and_kp_pkey PRIMARY KEY (id_)
) WITH (oids = false);