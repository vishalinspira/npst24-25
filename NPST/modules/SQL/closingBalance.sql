DROP TABLE IF EXISTS "closingbalance";
DROP SEQUENCE IF EXISTS closingbalance_id_seq1;
CREATE SEQUENCE closingbalance_id_seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.closingbalance (
    exitid integer NOT NULL,
    closingdate date,
    addressline1 character varying(100),
    addressline2 character varying(100),
    addressline3 character varying(100),
    confirmdate date,
    accountdetails text,
    yoursfaithfully character varying(50),
    designation character varying(20),
	CONSTRAINT closingbalance_pkey PRIMARY KEY (exitid)
) WITH (oids = false);