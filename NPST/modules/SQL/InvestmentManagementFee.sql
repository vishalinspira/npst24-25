DROP TABLE IF EXISTS "mn_fee_summary";
DROP SEQUENCE IF EXISTS mn_fee_summary_id__seq1;
CREATE SEQUENCE mn_fee_summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_fee_summary (
    id_ bigint NOT NULL,
	month_ character varying,
	pension_fund_name character varying,
	scheme_name character varying,
    management_fee numeric(20,2),
	gst numeric(20,2),
    total_management_fee numeric(20,2),
	nps_trust_fee numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_fee_summary_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_management_fee";
DROP SEQUENCE IF EXISTS mn_management_fee_id__seq1;
CREATE SEQUENCE mn_management_fee_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_management_fee (
    id_ bigint NOT NULL,
    pension_fund_name character varying,
    scheme_name character varying,
    month_ character varying,
    date_ date,
    total_aum numeric(20,2),
    total_management_fees numeric(20,2),
    mutual_funds numeric(20,2),
    exchange_traded_funds numeric(20,2),
    index_funds numeric(20,2),
    aum numeric(20,2),
    mgmt_fees_percentage numeric(20,4),
    gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_management_fee_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_nps_trust_fee";
DROP SEQUENCE IF EXISTS mn_nps_trust_fee_id__seq1;
CREATE SEQUENCE mn_nps_trust_fee_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_nps_trust_fee (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	date_ date,
	total_aum numeric(20,2),
	nps_trust_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_nps_trust_fee_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_management_rate";
DROP SEQUENCE IF EXISTS mn_management_rate_id__seq1;
CREATE SEQUENCE mn_management_rate_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_management_rate (
    id_ bigint NOT NULL,
    pension_fund_name character varying,
    date_ date,
    aum numeric(20,2),
    management_rate numeric(20,4),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_management_rate_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_e_t_i";
DROP SEQUENCE IF EXISTS mn_imf_scheme_e_t_i_id__seq1;
CREATE SEQUENCE mn_imf_scheme_e_t_i_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_e_t_i (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_e_t_i_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_c_t_i";
DROP SEQUENCE IF EXISTS mn_imf_scheme_c_t_i_id__seq1;
CREATE SEQUENCE mn_imf_scheme_c_t_i_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_c_t_i (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_c_t_i_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_g_t_i";
DROP SEQUENCE IF EXISTS mn_imf_scheme_g_t_i_id__seq1;
CREATE SEQUENCE mn_imf_scheme_g_t_i_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_g_t_i (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_g_t_i_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_e_t_ii";
DROP SEQUENCE IF EXISTS mn_imf_scheme_e_t_ii_id__seq1;
CREATE SEQUENCE mn_imf_scheme_e_t_ii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_e_t_ii (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_e_t_ii_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_c_t_ii";
DROP SEQUENCE IF EXISTS mn_imf_scheme_c_t_ii_id__seq1;
CREATE SEQUENCE mn_imf_scheme_c_t_ii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_c_t_ii (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_c_t_ii_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_g_t_ii";
DROP SEQUENCE IF EXISTS mn_imf_scheme_g_t_ii_id__seq1;
CREATE SEQUENCE mn_imf_scheme_g_t_ii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_g_t_ii (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_g_t_ii_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_a_t_i";
DROP SEQUENCE IF EXISTS mn_imf_scheme_a_t_i_id__seq1;
CREATE SEQUENCE mn_imf_scheme_a_t_i_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_a_t_i (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_a_t_i_pkey PRIMARY KEY (id_)
)WITH (oids = false);

DROP TABLE IF EXISTS "mn_imf_scheme_tax_t_ii";
DROP SEQUENCE IF EXISTS mn_imf_scheme_tax_t_ii_id__seq1;
CREATE SEQUENCE mn_imf_scheme_tax_t_ii_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_imf_scheme_tax_t_ii (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	scheme_name character varying,
	month_ character varying,
	date_ date,
	total_aum numeric(20,2),
	total_management_fees numeric(20,2),
	mutual_funds numeric(20,2),
	exchange_traded_funds numeric(20,2),
	index_funds numeric(20,2),
	aum numeric(20,2),
	mgmt_fees numeric(20,2),
	gst numeric(20,2),
    total_mgmt_fees numeric(20,2),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_imf_scheme_tax_t_ii_pkey PRIMARY KEY (id_)
)WITH (oids = false);






/*DROP TABLE IF EXISTS "management_rate";
DROP SEQUENCE IF EXISTS management_rate_id__seq1;
CREATE SEQUENCE management_rate_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."management_rate" (
	id_ integer NOT NULL,
    "date_" character varying(100) COLLATE pg_catalog."default",
    "aumount" character varying(100) COLLATE pg_catalog."default",
    "mgmt_rate" character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby integer,
    CONSTRAINT "weekly_outstand_with_trustee_id_" PRIMARY KEY ("id_")
) WITH (oids = false);

DROP TABLE IF EXISTS "management_fee";
DROP SEQUENCE IF EXISTS management_fee_id__seq1;
CREATE SEQUENCE management_fee_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."management_fee" (
	id_ integer NOT NULL,
    "month" character varying(100) COLLATE pg_catalog."default",
    "date_" character varying(100) COLLATE pg_catalog."default",
    "total_aum" character varying(100) COLLATE pg_catalog."default",
    "mutual_funds" character varying(100) COLLATE pg_catalog."default",
    "exchange_traded_funds" character varying(100) COLLATE pg_catalog."default",
    "index_funds" character varying(100) COLLATE pg_catalog."default",
    "aum_excluding_investments" character varying(100) COLLATE pg_catalog."default",
    "management_fees" character varying(100) COLLATE pg_catalog."default",
    "gst" character varying(100) COLLATE pg_catalog."default",
    "total_mgmt_fees" character varying(100) COLLATE pg_catalog."default",
    createdate date,
    createdby integer,
    CONSTRAINT "management_fee_id_" PRIMARY KEY ("id_")
) WITH (oids = false);*/