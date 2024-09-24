DROP TABLE IF EXISTS "quaeterlyfromoneisin";
DROP SEQUENCE IF EXISTS quaeterlyfromoneisin_id__seq1;
CREATE SEQUENCE quaeterlyfromoneisin_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.quaeterlyfromoneisin
(
    id_ bigint NOT NULL,
    isin_no character varying(100),
    isin_categoryofinvestment character varying(100),
    isin_nameofsecurity_company character varying(100),
    fisin character varying(100),
    isin_bookvalue character varying(100),
    isin_ofportfoliovalue character varying(100),
    isin_dateofpurchase_sale character varying(100),
    createdate date,
    createdby bigint,
    CONSTRAINT quaeterlyfromoneisin_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);