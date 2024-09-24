DROP TABLE IF EXISTS "qr_quarterly_non_unanimous";
DROP SEQUENCE IF EXISTS qr_quarterly_non_unanimous_id__seq1;
CREATE SEQUENCE qr_quarterly_non_unanimous_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.qr_quarterly_non_unanimous (
    id_ bigint NOT NULL,
	sno numeric,
	name_of_company character varying,
	meeting_date date,
    resolution character varying,
    ses_recomm character varying,
    ses_reason_for_recomm character varying,
    pf_voted_for character varying,
    pf_voted_abstain character varying,
    pf_voted_against character varying,
    final_vote character varying,
	rationale_of_pfms character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT qr_quarterly_non_unanimous_pkey PRIMARY KEY (id_)
) WITH (oids = false);