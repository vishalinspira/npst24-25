DROP TABLE IF EXISTS "quaeterlyfromfive";
DROP SEQUENCE IF EXISTS quaeterlyfromfive_id__seq1;
CREATE SEQUENCE quaeterlyfromfive_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.quaeterlyfromfive
(
    id_ bigint NOT NULL,
    scheme character varying(1000),
    sname character varying(1000),
    itype character varying(1000),
    ir_persentage character varying(1000),
    ir_revision character varying(1000),
    total_bookvalue character varying(1000),
    dp_bookvalue character varying(1000),
    di_bookvalue character varying(1000),
    pdue_form character varying(1000),
    idue_form character varying(1000),
    dp character varying(1000),
    di character varying(1000),
    rolledover character varying(1000),
    pw_amount character varying(1000),
    pw_bareference character varying(1000),
    classification character varying(1000),
    provisionp character varying(1000),
    provisionrs character varying(1000),
    createdate date,
    createdby bigint,
    CONSTRAINT quaeterlyfromfive_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);