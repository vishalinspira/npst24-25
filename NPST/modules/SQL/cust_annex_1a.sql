DROP TABLE IF EXISTS "cust_annex_1a";
DROP SEQUENCE IF EXISTS cust_annex_1a_id__seq1;
CREATE SEQUENCE cust_annex_1a_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cust_annex_1a (
    id_ bigint NOT NULL,
    custody_date date,
    pension_name character varying(100),
    auc_face_value numeric(15,2),
    auc_market_value numeric(15,2),
    aum numeric(15,2),
    percent_of_aum numeric(15,2),
    asset_not_under_custody numeric(15,2),
    createdon date,
    createdby bigint,
    CONSTRAINT cust_annex_1a_pkey PRIMARY KEY (id_)
)WITH (oids = false);