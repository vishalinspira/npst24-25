DROP TABLE IF EXISTS "pao_repeated_mistakes";
DROP SEQUENCE IF EXISTS pao_repeated_mistakes_id__seq1;
CREATE SEQUENCE pao_repeated_mistakes_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.pao_repeated_mistakes (
    id_ integer NOT NULL,
    row_labels character varying(100),
    count_nps_virtual_account_no character varying(100),
    pao_name character varying(100),
    email_id character varying(100),
    address_line1 character varying(100),
    address_line2 character varying(100),
    pin character varying(100),
    createdon date,
    createdby integer,
    CONSTRAINT pao_repeated_mistakes_pkey PRIMARY KEY (id_)
)WITH (oids = false);