DROP TABLE IF EXISTS "an_final_annual_vote_count";
DROP SEQUENCE IF EXISTS an_final_annual_vote_count_id__seq1;
CREATE SEQUENCE an_final_annual_vote_count_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.an_final_annual_vote_count (
    id_ bigint NOT NULL,
	pension_fund_name character varying,
	financial_year character varying,
	quarter character varying,
	total_number_of_resolution numeric,
	for_ numeric,
	against numeric,
	abstain numeric,
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT an_final_annual_vote_count_pkey PRIMARY KEY (id_)
)WITH (oids = false);