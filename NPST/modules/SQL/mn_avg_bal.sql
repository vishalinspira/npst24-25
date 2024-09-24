DROP TABLE IF EXISTS "mn_avg_bal";
DROP SEQUENCE IF EXISTS mn_avg_bal_id_seq;
CREATE SEQUENCE mn_avg_bal_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_avg_bal" (
    "id_" bigint DEFAULT nextval('mn_avg_bal_id_seq') NOT NULL,
	"sr_no"	integer	,
		"nps_acc_number"	bigint	,
		"nps_acc_name"	character varying	,
		"avg_bal_week1"	numeric(15,2)	,
		"avg_bal_week2"	numeric(15,2)	,
		"avg_bal_week3"	numeric(15,2)	,
		"avg_bal_week4"	numeric(15,2)	,
		"avg_bal_week5"	numeric(15,2)	,
		"avg_bal_week6"	numeric(15,2)	,
		"cra"character varying	,
		"month" timestamp NOT NULL, --date
    "createdate" date ,
	"createdby" bigint ,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_avg_bal_id_" PRIMARY KEY ("id_")
) WITH (oids = false);


ALTER TABLE "mn_avg_bal"
ADD "month" timestamp; --date
COMMENT ON TABLE "mn_avg_bal" IS '';