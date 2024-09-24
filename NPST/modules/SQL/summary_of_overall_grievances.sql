DROP TABLE IF EXISTS "summary_of_overall_grievances";
DROP SEQUENCE IF EXISTS summary_of_overall_grievances_id__seq1;
CREATE SEQUENCE summary_of_overall_grievances_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.summary_of_overall_grievances (
    id_ bigint NOT NULL,
    entitites character varying(100),
    opening_balance character varying(50),
    escalated_to_npst character varying(50),
    grievances_received character varying(50),
    grievances_assigned character varying(50),
    grievances_resolved character varying(50),
    outstanding_grievances character varying(50),
    createdon date,
    createdby bigint,
    CONSTRAINT summary_of_overall_grievances_pkey PRIMARY KEY (id_)
)WITH (oids = false);