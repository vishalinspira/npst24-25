DROP TABLE IF EXISTS "summary";
DROP SEQUENCE IF EXISTS summary_id__seq1;
CREATE SEQUENCE summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.summary (
    id_ bigint NOT NULL,
	mounth character varying,
	pension_fund_name character varying,
	scheme_name character varying,
	nps_trust_fee numeric,
	createdate date,
    createdby bigint,
    reportuploadlogid  bigint,
    CONSTRAINT summary_pkey PRIMARY KEY (id_)
)WITH (oids = false);	