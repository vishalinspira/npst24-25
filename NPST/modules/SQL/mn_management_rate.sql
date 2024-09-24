DROP TABLE IF EXISTS "mn_management_rate";
DROP SEQUENCE IF EXISTS mn_management_rate_id__seq1;
CREATE SEQUENCE mn_management_rate_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_management_rate (
    id_ bigint NOT NULL,
    pension_fund_name character varying,
    date_ date,
    pfm_aum numeric(20,2),
    management_rate numeric(20,4),
    createdate date,
    createdby bigint,
	
	reportuploadlogid bigint,
    CONSTRAINT mn_management_rate_pkey PRIMARY KEY (id_)
)WITH (oids = false);