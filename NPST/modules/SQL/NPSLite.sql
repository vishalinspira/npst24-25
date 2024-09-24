DROP TABLE IF EXISTS "npslite";
DROP SEQUENCE IF EXISTS npslite_id__seq1;
CREATE SEQUENCE npslite_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.npslite (
    id_ integer NOT NULL,
    pensionfund character varying(100),
    inceptiondate character varying(20),
    aum character varying(20),
    subscribers character varying(20),
    nav character varying(20),
    return_1year character varying(20),
    return_3year character varying(20),
    return_5year character varying(20),
    return_7year character varying(20),
    return_10year character varying(20),
    returninception character varying(20),
    createdon date,
    createdby integer,
    CONSTRAINT npslite_pkey PRIMARY KEY (id_)
) WITH (oids = false);