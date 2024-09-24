DROP TABLE IF EXISTS "quaeterlyfromtwo";
DROP SEQUENCE IF EXISTS quaeterlyfromtwo_id__seq1;
CREATE SEQUENCE quaeterlyfromtwo_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.quaeterlyfromtwo
(
    id_ bigint NOT NULL,
    particulars character varying(100),
    ob_bookvalue character varying(100),
    ob_marketvalue character varying(100),
    p_bookvalue character varying(100),
    p_oftotalportfolio character varying(100),
    sales_bookvalue character varying(100),
    sales_oftotalportfolio character varying(100),
    a_bookvalue character varying(100),
    a_oftotalportfolio character varying(100),
    cb_marketvalue character varying(100),
    cb_oftotalportfolio character varying(100),
    createdate character varying(100),
    createdby bigint,
    CONSTRAINT quaeterlyfromtwo_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);