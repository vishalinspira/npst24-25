DROP TABLE IF EXISTS "mn_non_unanimous_voting";
DROP SEQUENCE IF EXISTS mn_non_unanimous_voting_id__seq1;
CREATE SEQUENCE mn_non_unanimous_voting_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_non_unanimous_voting (
    id_ bigint NOT NULL,
	s_no integer,
	name_of_the_company character varying,
	meeting_date date,
    resolution character varying,
    ses_recommendation character varying,
    ses_reason_for_recommendation character varying,
    pfs_voted_for character varying,
    pfs_voted_abstain character varying,
    pfs_voted_against character varying,
    final_vote character varying,
    createdon date,
    createdby bigint,
    CONSTRAINT mn_non_unanimous_voting_pkey PRIMARY KEY (id_)
) WITH (oids = false);