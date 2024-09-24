DROP TABLE IF EXISTS "cust_annex_2a";
DROP SEQUENCE IF EXISTS cust_annex_2a_id__seq1;
CREATE SEQUENCE cust_annex_2a_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cust_annex_2a (
    id_ bigint NOT NULL,
    description character varying(1000),
    remarks character varying(1000),
    createdon date,
    createdby bigint,
    CONSTRAINT cust_annex_2a_pkey PRIMARY KEY (id_)
)WITH (oids = false);