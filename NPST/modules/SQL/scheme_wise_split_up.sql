DROP TABLE IF EXISTS "scheme_wise_split_up";
DROP SEQUENCE IF EXISTS scheme_wise_split_up_id__seq1;
CREATE SEQUENCE scheme_wise_split_up_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.scheme_wise_split_up (
    id_ bigint NOT NULL,
    particulars character varying(100),
    nps character varying(50),
    npslite_and_apy character varying(50),
--    total character varying(50),
    createdon date,
    createdby bigint,
    CONSTRAINT scheme_wise_split_up_pkey PRIMARY KEY (id_)
)WITH (oids = false);