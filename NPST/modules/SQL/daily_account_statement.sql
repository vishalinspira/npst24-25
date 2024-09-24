DROP TABLE IF EXISTS "daily_account_statement";
DROP SEQUENCE IF EXISTS daily_account_statement_id__seq;
CREATE SEQUENCE daily_account_statement_id__seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."daily_account_statement" (
    "id_" integer DEFAULT nextval('daily_account_statement_id__seq') NOT NULL,
	
	"cust_id"	bigint	 not null,
	"nps_acc_number"	bigint	 not null,
	"nps_acc_name"	character varying	 not null,
	"tran_date"	date not null, --timestamp without time zone
	"tran_id"	character varying	 not null,
	"part_tran_sno_psrl"	integer null,
	"chq_no" bigint null,	 
	"chq_date"	date null,	 --timestamp without time zone
	"debit_trans"	numeric(15,2)	 not null,
	"credit_trans"	numeric(15,2)	 not null,
	"running_bal"	numeric(15,2)	 not null,
	"trans_particulars"	character varying(100)	 null,
	"trans_remarks"	character varying(100) null,	 
	"sol_id"	integer null,
	"counter_party_acc_number"	bigint null,	
    "createdate" date NOT NULL,
    "createdby" bigint,
    "uuid_" character varying,
    "modifyby" bigint,
    "modifydate" timestamp,
    "status" integer,
    "statusbyuserid" bigint,
    "statusbyusername" character varying,
    "statusdate" character varying,
    "reportuploadlogid" bigint,
    CONSTRAINT "daily_account_statement_id_" PRIMARY KEY ("id_")
) WITH (oids = false);





