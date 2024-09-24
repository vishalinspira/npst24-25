DROP TABLE IF EXISTS "weekly_average_balance_report";
DROP SEQUENCE IF EXISTS weekly_average_balance_report_id__seq1;
CREATE SEQUENCE weekly_average_balance_report_id__seq1 INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE public.weekly_average_balance_report (
    id_ bigint NOT NULL,
    nps_acc_number bigint NOT NULL,
    cust_id bigint NOT NULL,
    cra character varying(20) NOT NULL,
    nps_acc_name character varying(100) NOT NULL,
    average_balance_for_the_week numeric(15,2) NOT NULL,
    week_date date NOT NULL,
    createdon date,
    createdby bigint,
	reportuploadlogid bigint NULL,
    CONSTRAINT weekly_average_balance_report_pkey PRIMARY KEY (id_)
) WITH (oids = false);

ALTER TABLE "weekly_average_balance_report"
ADD "reportuploadlogid" bigint NULL;
COMMENT ON TABLE "weekly_average_balance_report" IS '';