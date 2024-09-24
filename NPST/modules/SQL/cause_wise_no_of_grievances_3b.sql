DROP TABLE IF EXISTS "cause_wise_no_of_grievances_3b";
DROP SEQUENCE IF EXISTS cause_wise_no_of_grievances_3b_id__seq1;
CREATE SEQUENCE cause_wise_no_of_grievances_3b_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.cause_wise_no_of_grievances_3b (
    id_ bigint NOT NULL,
    category character varying(100),
    q1 character varying(50),
    q2 character varying(50),
    q3 character varying(50),
    q4 character varying(50),
    q1_1 character varying(50),
    q2_2 character varying(50),
    createdon date,
    createdby bigint,
    CONSTRAINT cause_wise_no_of_grievances_3b_pkey PRIMARY KEY (id_)
)WITH (oids = false);