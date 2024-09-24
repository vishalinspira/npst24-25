DROP TABLE IF EXISTS "quaeterlyfromfivenpa";
DROP SEQUENCE IF EXISTS quaeterlyfromfivenpa_id__seq1;
CREATE SEQUENCE quaeterlyfromfivenpa_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.quaeterlyfromfivenpa
(
    id_ bigint,
    srno character varying(100),
    nameofnpa character varying(100),
    nameofsecurity character varying(100),
    dateofpurchase character varying(100),
    total_facevalue character varying(100),
    total_purchasevalue character varying(100),
    value character varying(100),
    npasince character varying(100),
    bookvalue character varying(100),
    provisionheld character varying(100),
    dd_interestpayment character varying(100),
    stepstakencomments character varying(100),
    createdate date,
    createdby bigint
)
WITH (
    OIDS = FALSE
);