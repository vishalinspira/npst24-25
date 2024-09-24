DROP TABLE IF EXISTS "ses_non_unanimous";
DROP SEQUENCE IF EXISTS ses_non_unanimous_id__seq1;
CREATE SEQUENCE ses_non_unanimous_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.ses_non_unanimous (
    id_ bigint NOT NULL,
    company_name character varying(100),
    meeting_date date,
    resolution character varying(1000),
    voted_for character varying(1000),
    voted_abstain character varying(1000),
    voted_against character varying(1000),
    final_vote character varying(10),
    createdon date,
    createdby bigint,
    CONSTRAINT ses_non_unanimous_pkey PRIMARY KEY (id_)
)WITH (oids = false);