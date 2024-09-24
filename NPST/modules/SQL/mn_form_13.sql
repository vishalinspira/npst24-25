DROP TABLE IF EXISTS "mn_form_13";
DROP SEQUENCE IF EXISTS mn_form_13_id__seq1;
CREATE SEQUENCE mn_form_13_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_13 (
    id_ bigint NOT NULL,
    pension_fund_name character varying,
    scheme_name character varying,
    tier_i_tier_ii character varying,
    reporting_date date,
    isin character varying,
    security_name character varying,
    issuer_name character varying,
    nic_code numeric,
    industry_classification character varying,
    security_type character varying,
    no_of_shares_units int,
    market_price_per_unit numeric,
    market_value numeric(20,2),
    aum numeric,
    percentage_to_nav character varying(50),
    coupon_rate character varying,
    coupon_payment_freq int,
    purchase_price numeric,
    amount_invested numeric,
    put_date date,
    call_date date,
	face_value numeric,
    maturity_date date,
	residual_maturity numeric(15,2),
	modified_duration numeric(15,2),
	purchase_ytm character varying(50),
	current_ytm character varying(50),
	nse_closing_price numeric(15,2),
    bse_closing_price numeric(15,2),
	lowest_rating character varying(50),
    lowest_rating_agency character varying(50),
    second_lowest_rating character varying(50),
    second_lowest_rating_agency character varying(50),
	code_ bigint,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_13_pkey PRIMARY KEY (id_)
) WITH (oids = false);