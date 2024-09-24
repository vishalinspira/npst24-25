DROP TABLE IF EXISTS "comparision_of_overall_summary";
DROP SEQUENCE IF EXISTS comparision_of_overall_summary_id__seq1;
CREATE SEQUENCE comparision_of_overall_summary_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.comparision_of_overall_summary (
    id_ bigint NOT NULL,
    grievances character varying(100),
    q1 character varying(50),
    q2 character varying(50),
    q3 character varying(50),
    q4 character varying(50),
    q1_1 character varying(50),
    q2_2 character varying(50),
    percentage_change character varying(50),
    createdon date,
    createdby bigint,
    CONSTRAINT comparision_of_overall_summary_pkey PRIMARY KEY (id_)
)WITH (oids = false);