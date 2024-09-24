-- Adminer 4.7.1 PostgreSQL dump

DROP TABLE IF EXISTS "weekly_con_coll_rpt_kcra";
DROP SEQUENCE IF EXISTS weekly_con_coll_rpt_kcra_id__seq;
CREATE SEQUENCE weekly_con_coll_rpt_kcra_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."weekly_con_coll_rpt_kcra" (
    "id_" integer DEFAULT nextval('weekly_con_coll_rpt_kcra_id__seq') NOT NULL,
    date_	date	not null,
	pao_funds_received	numeric(15,2)	not null,
	wrong_credit_return	numeric(15,2)	not null,
	received_cumulative_funds	numeric(15,2)	not null,
	pfm_funds_remitted	numeric(15,2)	not null,
	pfm_cumulative_funds_remitted	numeric(15,2)	not null,
	closing_balance	numeric(15,2)	not null,
	uncleared_funds	numeric(15,2)	not null,
	nps_acc_number	bigint	not null,
	"cra" character varying	not null,
    "createdate" date,
    "createdby" bigint,
    reportuploadlogid bigint
) WITH (oids = false);


-- 2022-05-06 23:09:54.239017+05:30
