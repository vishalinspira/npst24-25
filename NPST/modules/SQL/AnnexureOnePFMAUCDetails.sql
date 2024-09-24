DROP TABLE IF EXISTS "mn_auc_summary";
DROP SEQUENCE IF EXISTS mn_auc_summary_id__seq1;
CREATE SEQUENCE mn_auc_summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_auc_summary (
    id_ bigint NOT NULL,
	sr_no integer NOT NULL,
	date_ date,
	pension_fund_name character varying,
	auc_fv_debt_and_pur_price_eq numeric,
	auc_as_per_market_valuation numeric,
	aum numeric,
	prcnt_of_aum_under_custody numeric,
	asset_not_under_custody numeric,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_auc_summary_pkey PRIMARY KEY (id_)
)WITH (oids = false);






DROP TABLE IF EXISTS "mn_asset_not_under_custody";
DROP SEQUENCE IF EXISTS mn_asset_not_under_custody_id__seq1;
CREATE SEQUENCE mn_asset_not_under_custody_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_asset_not_under_custody (
    id_ bigint NOT NULL,
	sr_no integer,
	date_ date,
	details_of_various_items character varying,
	market_value numeric,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_asset_not_under_custody_pkey PRIMARY KEY (id_)
)WITH (oids = false);