DROP TABLE IF EXISTS "mn_pao_repeated_mistakes";
DROP SEQUENCE IF EXISTS mn_pao_repeated_mistakes_id__seq1;
CREATE SEQUENCE mn_pao_repeated_mistakes_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_pao_repeated_mistakes (
    id_ bigint NOT NULL,
    row_labels int,
    count_nps_virtual_account_no integer,
    pao_name character varying(50),
    email_id character varying(50),
    address_line1 character varying(50),
    address_line2 character varying(50),
    pin int,
    cra character varying(10),
    createdon date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT mn_pao_repeated_mistakes_pkey PRIMARY KEY (id_)
)WITH (oids = false);