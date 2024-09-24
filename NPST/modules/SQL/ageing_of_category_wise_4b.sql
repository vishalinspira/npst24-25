DROP TABLE IF EXISTS "ageing_of_category_wise_4b";
DROP SEQUENCE IF EXISTS ageing_of_category_wise_4b_id__seq1;
CREATE SEQUENCE ageing_of_category_wise_4b_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.ageing_of_category_wise_4b (
    id_ bigint NOT NULL,
    category character varying(100),
    ref_at_the_end_of_quarter character varying(50),
    _0_7 character varying(50),
    _8_15 character varying(50),
    _16_31 character varying(50),
    _32_90 character varying(50),
    _91_180 character varying(50),
    _181_365 character varying(50),
    _366_and_above character varying(50),
    createdon date,
    createdby bigint,
    CONSTRAINT ageing_of_category_wise_4b_pkey PRIMARY KEY (id_)
)WITH (oids = false);