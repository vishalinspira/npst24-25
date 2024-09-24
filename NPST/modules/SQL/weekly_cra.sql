DROP TABLE IF EXISTS "weekly_nav_kcra";
DROP SEQUENCE IF EXISTS weekly_nav_kcra_id__seq1;
CREATE SEQUENCE weekly_nav_kcra_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.weekly_nav_kcra (
    id_ bigint NOT NULL,
    sr_no numeric(5,0) NOT NULL,
	date_of_latest_nav date NOT NULL,
	pension_fund_code character varying(40) NOT NULL,
	pension_fund_name character varying(200) NOT NULL,
	scheme_code character varying(40) NOT NULL,
	scheme_name character varying(200) NOT NULL,
	sector character varying(100) NOT NULL,
	no_of_subscribers numeric(15,2) NOT NULL,
	aum numeric(15,2) NOT NULL,
	latest_nav numeric(15,2) NOT NULL,
	prior_one_year_nav character varying(40) NOT NULL,
	prior_three_year_nav character varying(40) NOT NULL,
	prior_five_year_nav character varying(40) NOT NULL,
    createdate date,
    createdby bigint,
    CONSTRAINT weekly_nav_kcra_pkey PRIMARY KEY (id_)
)WITH (oids = false);