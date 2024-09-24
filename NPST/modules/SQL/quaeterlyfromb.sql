DROP TABLE IF EXISTS "quaeterlyfromb";
DROP SEQUENCE IF EXISTS quaeterlyfromb_id__seq1;
CREATE SEQUENCE quaeterlyfromb_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.quaeterlyfromb
(
    id_ bigint NOT NULL,
    no character varying(100),
    categoryofinvestment character varying(1000),
    nameofsecurity_company character varying(1000),
    bookvalue character varying(1000),
    ofportfoliovalue character varying(1000),
    dateofpurchase_sale character varying(1000),
    createdate date,
    createdby bigint,
    CONSTRAINT quaeterlyfromb_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);