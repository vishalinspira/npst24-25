DROP TABLE IF EXISTS "mn_pao_submtting_chq_kcra";
DROP SEQUENCE IF EXISTS mn_pao_submtting_chq_kcra_id__seq1;
CREATE SEQUENCE mn_pao_submtting_chq_kcra_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.mn_pao_submtting_chq_kcra (
    id_ bigint NOT NULL,
    pao_id character varying(50) NOT NULL,
    no_of_transactions integer NOT NULL,
    pao_name character varying(50) NOT NULL,
    email_id character varying(50) NOT NULL,
    address_line1 character varying(50) NOT NULL,
    address_line2 character varying(50) NOT NULL,
    pin bigint NOT NULL,
    cra character varying(10),
    createdon date,
    createdby bigint,
    CONSTRAINT mn_pao_submtting_chq_kcra_pkey PRIMARY KEY (id_)
)WITH (oids = false);