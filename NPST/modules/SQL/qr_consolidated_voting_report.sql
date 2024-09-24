DROP TABLE IF EXISTS "qr_consolidated_voting_report";
DROP SEQUENCE IF EXISTS qr_consolidated_voting_report_id__seq1;
CREATE SEQUENCE qr_consolidated_voting_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.qr_consolidated_voting_report
(
    id_ bigint NOT NULL,
	sno integer,
	company_name character varying,
	isin character varying,
	meeting_date date,
	majority_vote character varying,
	no_of_resolution numeric(15,2),
	lic_pf_for numeric(15,2),
	lic_pf_abstain numeric(15,2),
	lic_pf_against numeric(15,2),
	uti_pf_for numeric(15,2),
	uti_pf_abstain numeric(15,2),
	uti_pf_against numeric(15,2),
	sbi_pf_for numeric(15,2),
	sbi_pf_abstain numeric(15,2),
	sbi_pf_against numeric(15,2),
	hdfc_pf_for numeric(15,2),
	hdfc_pf_abstain numeric(15,2),
	hdfc_pf_against numeric(15,2),
	icici_pf_for numeric(15,2),
	icici_pf_abstain numeric(15,2),
	icici_pf_against numeric(15,2),
	kotak_pf_for numeric(15,2),
	kotak_pf_abstain numeric(15,2),
	kotak_pf_against numeric(15,2),
	birla_pf_for numeric(15,2),
	birla_pf_abstain numeric(15,2),
	birla_pf_against numeric(15,2),
    createdate date,
    createdby bigint,
    CONSTRAINT qr_consolidated_voting_report_pkey PRIMARY KEY (id_)
) WITH (oids = false);