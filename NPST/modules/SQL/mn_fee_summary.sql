DROP TABLE IF EXISTS "mn_fee_summary";
DROP SEQUENCE IF EXISTS mn_fee_summary_id__seq1;
CREATE SEQUENCE mn_fee_summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_fee_summary (
    id_ bigint NOT NULL,
	month character varying,
	pension_fund_name character varying,
	scheme_name character varying,
    management_fee numeric(20,2),
	gst numeric(20,2),
    total_management_fee numeric(20,2),
	tds numeric(20,2),
    createdate date,
    createdby bigint,
	reportuploadlogid bigint,
    CONSTRAINT mn_fee_summary_pkey PRIMARY KEY (id_)
)WITH (oids = false);