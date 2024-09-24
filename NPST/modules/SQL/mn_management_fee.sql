DROP TABLE IF EXISTS "mn_management_fee";
DROP SEQUENCE IF EXISTS mn_management_fee_id__seq1;
CREATE SEQUENCE mn_management_fee_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_management_fee (
    id_ bigint NOT NULL,
    pension_fund_name character varying,
    scheme_name character varying,
    month character varying,
    date_ date,
    total_aum numeric(20,2),
    total_management_fees numeric(20,2),
    mutual_funds_except_liquid_mf numeric(20,2),
    exchange_traded_funds numeric(20,2),
    index_funds numeric(20,2),
    aum_excl_investments numeric(20,2),
    mgmt_fees_percentage numeric(20,4),
    gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
	reportuploadlogid bigint,
    CONSTRAINT mn_management_fee_pkey PRIMARY KEY (id_)
)WITH (oids = false);