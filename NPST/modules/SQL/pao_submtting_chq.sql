DROP TABLE IF EXISTS "pao_submtting_chq";
DROP SEQUENCE IF EXISTS pao_submtting_chq_id__seq1;
CREATE SEQUENCE pao_submtting_chq_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.pao_submtting_chq (
    id_ integer NOT NULL,
    pao_id character varying(100),
    no_of_transactions character varying(100),
    pao_name character varying(100),
    email_id character varying(100),
    address_line1 character varying(100),
    address_line2 character varying(100),
    pin character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT pao_submtting_chq_pkey PRIMARY KEY (id_)
)WITH (oids = false);