DROP TABLE IF EXISTS "mn_scheme_data";
DROP SEQUENCE IF EXISTS mn_scheme_data_id__seq1;
CREATE SEQUENCE mn_scheme_data_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_scheme_data
(
    id_ bigint NOT NULL,
    particulars character varying,
    scheme_cg character varying,
    scheme_sg character varying,
    scheme_e_tier_i character varying,
    scheme_c_tier_i character varying,
    scheme_g_tier_i character varying,
    scheme_e_tier_ii character varying,
    scheme_c_tier_ii character varying,
    scheme_g_tier_ii character varying,
    nps_lite character varying,
    corporate_cg_scheme character varying,
    apy character varying,
    scheme_a_tier_i character varying,
    scheme_a_tier_ii character varying,
    scheme_tax_saver_tier_ii character varying,
    nav_date date,
    pension_fund_name character varying,
    reporting_date date,
    pfm character varying(50),
    createdate date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_scheme_data_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);