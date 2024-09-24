DROP TABLE IF EXISTS "form_14";
DROP SEQUENCE IF EXISTS form_14_id__seq1;
CREATE SEQUENCE form_14_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.form_14 (
    id_ bigint NOT NULL,
    pfm_name character varying(100),
    scheme_name character varying(100),
    tier_1_or_tier_2 character varying(100),
    transaction_type character varying(100),
    date_of_transaction date,
    isin character varying(100),
    security_name character varying(100),
    nic_code character varying(100),
    issuer_name character varying(100),
    industry character varying(100),
    security_type character varying(100),
    no_of_shares_as_applicable numeric(15,2),
    purchase_sale_price_per_unit numeric(15,2),
    amount_in_rs numeric(15,2),
    maturity_date date,
    modified_duration_in_years numeric(15,2),
    purchase_ytm_in_percent numeric(15,2),
    rating_1 character varying(100),
    rating_2 character varying(100),
    type_of_transaction character varying(100),
    nps_trust_code character varying(100),
    createdon date,
    createdby bigint,
    CONSTRAINT form_14_pkey PRIMARY KEY (id_)
) WITH (oids = false);