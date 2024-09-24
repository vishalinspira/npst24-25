DROP TABLE IF EXISTS "mn_annexure_4c";
DROP SEQUENCE IF EXISTS mn_annexure_4c_id_seq;
CREATE SEQUENCE mn_annexure_4c_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."mn_annexure_4c" (
    "id_" bigint DEFAULT nextval('mn_annexure_4c_id_seq') not null,
		"sno"	integer	,
		"std_pran"	bigint	,
		"type_"	character varying	,
		"subscriber_name"	character varying,	
		"beneficiary_name"	character varying	,
		"net_amt"	numeric(15,2)	,
		"pay_in_date"	date	,
		"execution_date"	date	,
		"utr_no"	character varying,	
		"initial_status"	character varying	,
		"return_reason"	character varying	,
		"remarks"	character varying	,
		"delay"	character varying	,
		"cra"character varying	,
    "createdate" date ,
	"createdby" bigint ,
	"reportuploadlogid" bigint,
    CONSTRAINT "mn_annexure_4c_id_" PRIMARY KEY ("id_")
) WITH (oids = false);