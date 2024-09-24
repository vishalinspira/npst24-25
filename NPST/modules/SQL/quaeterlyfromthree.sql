DROP TABLE IF EXISTS "quaeterlyfromthree";
DROP SEQUENCE IF EXISTS quaeterlyfromthree_id__seq1;
CREATE SEQUENCE quaeterlyfromthree_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE IF NOT EXISTS public.quaeterlyfromthree
(
    id_ bigint NOT NULL,
    particulars character varying(100),
    openingb_units character varying(100),
    openingb_amount character varying(100),
    pfq_units character varying(100),
    pfq_bookvalue character varying(100),
    pfq_oftotalportfolio character varying(100),
    sfq_units character varying(100),
    sfq_bookvalue character varying(100),
    sfq_oftotalportfolio character varying(100),
    closingb_units character varying(100),
    closingb_bookvalue character varying(100),
    closingb_marketvalue character varying(100),
    closingb_oftotalportfolio character varying(100),
    createdate date,
    createdby bigint,
    CONSTRAINT quaeterlyfromthree_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);