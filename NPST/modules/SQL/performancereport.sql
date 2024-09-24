DROP TABLE IF EXISTS "performancereport";
DROP SEQUENCE IF EXISTS performancereport_id_seq1;
CREATE SEQUENCE performancereport_id_seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.performancereport (
    id integer NOT NULL,
    reportdate date,
    addressline1 character varying(100),
    addressline2 character varying(100),
    addressline3 character varying(100),
    trusteebankname character varying(100),
    monthendingdate date,
    officername character varying(50),
    submitdate date,
    place character varying(100),
	CONSTRAINT performancereport_pkey PRIMARY KEY (id)
) WITH (oids = false);