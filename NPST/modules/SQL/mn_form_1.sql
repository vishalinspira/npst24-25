DROP TABLE IF EXISTS "mn_form_1";
DROP SEQUENCE IF EXISTS mn_form_1_id__seq1;
CREATE SEQUENCE mn_form_1_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_form_1 (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	reporting_date date,
    scheme_name character varying,
    aum_pfm numeric(20,2),
    aum_cra numeric(20,2),
    aum_diff numeric(20,2),
    units_outstanding_pfm numeric(20,4),
    units_outstanding_cra numeric(20,4),
    units_outstanding_diff numeric(20,4),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_form_1_pkey PRIMARY KEY (id_)
) WITH (oids = false);