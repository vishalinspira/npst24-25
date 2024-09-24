DROP TABLE IF EXISTS "mn_npst_acc_amt";
DROP SEQUENCE IF EXISTS mn_npst_acc_amt_id_seq;
CREATE SEQUENCE mn_npst_acc_amt_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_npst_acc_amt" (
    "id_" bigint DEFAULT nextval('mn_npst_acc_amt_id_seq') NOT NULL,
		"sno"	integer	,
		"nps_acc_number"	bigint	,
		"nps_acc_name"	character varying	,
		"total_debit"	numeric(15,2)	,
		"total_credit"	numeric(15,2)	,
		"closing_bal"	numeric(15,2)	,
		"cra" character varying	,
		"month" timestamp NOT NULL, --date
    "createdate" date ,
	"createdby" bigint ,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_npst_acc_amt_id_" PRIMARY KEY ("id_")
) WITH (oids = false);

ALTER TABLE "mn_npst_acc_amt"
ADD "month" timestamp; --date
COMMENT ON TABLE "mn_npst_acc_amt" IS '';