DROP TABLE IF EXISTS "mn_form_2";
DROP SEQUENCE IF EXISTS mn_form_2_id__seq1;
CREATE SEQUENCE mn_form_2_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_2 (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
    particulars character varying,
    scheme_cg numeric,
    scheme_sg numeric,
    scheme_e_tier_i numeric,
    scheme_c_tier_i numeric,
    scheme_g_tier_i numeric,
	scheme_a_tier_i numeric,
    scheme_e_tier_ii numeric,
    scheme_c_tier_ii numeric,
    scheme_g_tier_ii numeric,
	scheme_tax_saver_tier_ii numeric,
    nps_lite numeric,
    corporate_cg_scheme numeric,
    apy numeric,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_2_pkey PRIMARY KEY (id_)
) WITH (oids = false);