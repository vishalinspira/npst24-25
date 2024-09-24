DROP TABLE IF EXISTS "weekly_nav_cra";
DROP SEQUENCE IF EXISTS weekly_nav_cra_id__seq1;
CREATE SEQUENCE weekly_nav_cra_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.weekly_nav_cra (
    id_ bigint NOT NULL,
    sr_no bigint NOT NULL,
    date_of_latest_nav date,
    pension_fund_code character varying(40),
    pension_fund_name character varying(200),
    scheme_code character varying(40),
    scheme_name character varying(200),
    sector character varying(100),
    no_of_subscriber bigint,
    aum numeric(15,2),
    lastest_nav numeric(15,2),
    nav_prior_1_year numeric(15,4),
    nav_prior_2_year numeric(15,4),
    nav_prior_3_year numeric(15,4),
    nav_prior_5_year numeric(15,4),
    nav_prior_7_year numeric(15,4),
    nav_prior_10_year numeric(15,4),
    cra character varying(40),
    createdon date,
    createdby bigint,
    CONSTRAINT weekly_nav_cra_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);
