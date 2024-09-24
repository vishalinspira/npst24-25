DROP TABLE IF EXISTS "quaeterlyfromfour";
DROP SEQUENCE IF EXISTS quaeterlyfromfour_id__seq1;
CREATE SEQUENCE quaeterlyfromfour_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.quaeterlyfromfour
(
    id_ bigint NOT NULL,
    srno character varying(100),
    nameofscheme character varying(100),
    marketvaluecq character varying(100),
    marketvaluepq character varying(100),
    marketvaluesecondpq character varying(100),
    marketvaluethirdpq character varying(100),
    thirdyrollingcagr character varying(100),
    preturn character varying(100),
    createdate date,
    createdby bigint,
    CONSTRAINT quaeterlyfromfour_pkey PRIMARY KEY (id_)
)
WITH (
    OIDS = FALSE
);