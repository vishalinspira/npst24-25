DROP TABLE IF EXISTS "entity_wise_outstanding";
DROP SEQUENCE IF EXISTS entity_wise_outstanding_id__seq1;
CREATE SEQUENCE entity_wise_outstanding_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.entity_wise_outstanding (
    id_ bigint NOT NULL,
    entity character varying(100),
    q1 character varying(50),
    q2 character varying(50),
    q3 character varying(50),
    q4 character varying(50),
    q1_1 character varying(50),
    q2_2 character varying(50),
    createdon date,
    createdby bigint,
    CONSTRAINT entity_wise_outstanding_pkey PRIMARY KEY (id_)
)WITH (oids = false);