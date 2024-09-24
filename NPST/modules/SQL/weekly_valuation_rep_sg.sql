DROP TABLE IF EXISTS "weekly_valuation_rep_sg";
DROP SEQUENCE IF EXISTS weekly_valuation_rep_sg_id__seq1;
CREATE SEQUENCE weekly_valuation_rep_sg_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.weekly_valuation_rep_sg (
    id_ bigint NOT NULL,
    client_code character varying(40),
    isin character varying(40),
    isin_desc character varying(100),
    maturity_date date,
    free_securities_rs bigint,
    pledge_value_held_rs bigint,
    securities_in_transit_rs bigint,
    total_face_value_held_rs bigint,
    rate numeric(15,4),
    value_ numeric(15,4),
    createdate date,
    createdby bigint,
    reportuploadlogid bigint,
    CONSTRAINT weekly_valuation_rep_sg_pkey PRIMARY KEY (id_)
)WITH (oids = false);