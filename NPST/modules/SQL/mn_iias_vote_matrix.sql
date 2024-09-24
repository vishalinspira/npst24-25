DROP TABLE IF EXISTS "mn_iias_vote_matrix";
DROP SEQUENCE IF EXISTS mn_iias_vote_matrix_id__seq1;
CREATE SEQUENCE mn_iias_vote_matrix_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_iias_vote_matrix
(
    id_ bigint NOT NULL,
    meeting_date date,
    isin character varying,
    company_name character varying,
    meeting_type character varying,
    resolution_category character varying,
    resolution_dtls character varying,
    resolution_type character varying,
    iias_vote_recommendation character varying,
    iias_rationale_for_vote_recom character varying,
--    meetingdate character varying(1000),
--    isin character varying(1000),
--    cname character varying(1000),
--    typeofmeeting character varying(1000),
--    proposalby character varying(1000),
--    resolution character varying(1000),
--    pdescription character varying(5000),
--    imanagement character varying(1000),
--    votefor character varying(1000),
--    reasonvote character varying(10000),
    createdate date,
    createdby bigint,
    CONSTRAINT mn_iias_vote_matrix_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);